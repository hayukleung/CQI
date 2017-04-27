package com.zbar.core;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.List;

public class CameraTexturePreview extends TextureView
    implements TextureView.SurfaceTextureListener {
  private static final String TAG = "CameraPreview";

  private Camera mCamera;
  private Handler mAutoFocusHandler;
  private boolean mPreviewing = true;
  private boolean mAutoFocus = true;
  private boolean mSurfaceCreated = false;
  private SurfaceTexture mSurfaceTexture;
  private Camera.PreviewCallback mPreviewCallback;
  private Runnable doAutoFocus = new Runnable() {
    public void run() {
      if (mCamera != null && mPreviewing && mAutoFocus && mSurfaceCreated) {
        safeAutoFocus();
      }
    }
  };
  // Mimic continuous auto-focusing
  Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
    public void onAutoFocus(boolean success, Camera camera) {
      scheduleAutoFocus();
    }
  };

  public CameraTexturePreview(Context context, Camera camera,
      Camera.PreviewCallback previewCallback) {
    super(context);
    init(camera, previewCallback);
  }

  public CameraTexturePreview(Context context, AttributeSet attrs, Camera camera,
      Camera.PreviewCallback previewCallback) {
    super(context, attrs);
    init(camera, previewCallback);
  }

  public void init(Camera camera, Camera.PreviewCallback previewCallback) {
    this.setSurfaceTextureListener(this);
    setCamera(camera, previewCallback);
    mAutoFocusHandler = new Handler();
  }

  public void setCamera(Camera camera, Camera.PreviewCallback previewCallback) {
    mCamera = camera;
    mPreviewCallback = previewCallback;
  }

  public void showCameraPreview() {
    if (mCamera != null) {
      try {
        mPreviewing = true;
        setupCameraParameters();
        mCamera.setPreviewTexture(mSurfaceTexture);
        mCamera.setDisplayOrientation(getDisplayOrientation());
        mCamera.setOneShotPreviewCallback(mPreviewCallback);
        mCamera.startPreview();
        if (mAutoFocus) {
          if (mSurfaceCreated) { // check if surface created before using autofocus
            safeAutoFocus();
          } else {
            scheduleAutoFocus(); // wait 1 sec and then do check again
          }
        }
      } catch (Exception e) {
        Log.e(TAG, e.toString(), e);
      }
    }
  }

  public void safeAutoFocus() {
    try {
      mCamera.autoFocus(autoFocusCB);
    } catch (RuntimeException re) {
      // Horrible hack to deal with autofocus errors on Sony devices
      // See https://github.com/dm77/barcodescanner/issues/7 for example
      scheduleAutoFocus(); // wait 1 sec and then do check again
    }
  }

  public void stopCameraPreview() {
    if (mCamera != null) {
      try {
        mPreviewing = false;
        mCamera.cancelAutoFocus();
        mCamera.setOneShotPreviewCallback(null);
        mCamera.stopPreview();
      } catch (Exception e) {
        Log.e(TAG, e.toString(), e);
      }
    }
  }

  public void setupCameraParameters() {
    Camera.Size optimalSize = getOptimalPreviewSize();
    Camera.Parameters parameters = mCamera.getParameters();
    parameters.setPreviewSize(optimalSize.width, optimalSize.height);
    mCamera.setParameters(parameters);
    adjustViewSize(optimalSize);
  }

  private void adjustViewSize(Camera.Size cameraSize) {
    Point previewSize = convertSizeToLandscapeOrientation(new Point(getWidth(), getHeight()));
    float cameraRatio = ((float) cameraSize.width) / cameraSize.height;
    float screenRatio = ((float) previewSize.x) / previewSize.y;

    if (screenRatio > cameraRatio) {
      setViewSize((int) (previewSize.y * cameraRatio), previewSize.y);
    } else {
      setViewSize(previewSize.x, (int) (previewSize.x / cameraRatio));
    }
  }

  private Point convertSizeToLandscapeOrientation(Point size) {
    if (getDisplayOrientation() % 180 == 0) {
      return size;
    } else {
      return new Point(size.y, size.x);
    }
  }

  private void setViewSize(int width, int height) {
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (getDisplayOrientation() % 180 == 0) {
      layoutParams.width = width;
      layoutParams.height = height;
    } else {
      layoutParams.width = height;
      layoutParams.height = width;
    }
    setLayoutParams(layoutParams);
  }

  public int getDisplayOrientation() {
    Camera.CameraInfo info = new Camera.CameraInfo();
    Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();

    int rotation = display.getRotation();
    int degrees = 0;
    switch (rotation) {
      case Surface.ROTATION_0:
        degrees = 0;
        break;
      case Surface.ROTATION_90:
        degrees = 90;
        break;
      case Surface.ROTATION_180:
        degrees = 180;
        break;
      case Surface.ROTATION_270:
        degrees = 270;
        break;
    }

    int result;
    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
      result = (info.orientation + degrees) % 360;
      result = (360 - result) % 360;  // compensate the mirror
    } else {  // back-facing
      result = (info.orientation - degrees + 360) % 360;
    }
    return result;
  }

  private Camera.Size getOptimalPreviewSize() {
    if (mCamera == null) {
      return null;
    }

    List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
    int w = getWidth();
    int h = getHeight();
    if (DisplayUtils.getScreenOrientation(getContext()) == Configuration.ORIENTATION_PORTRAIT) {
      int portraitWidth = h;
      h = w;
      w = portraitWidth;
    }

    final double ASPECT_TOLERANCE = 0.1;
    double targetRatio = (double) w / h;
    if (sizes == null) return null;

    Camera.Size optimalSize = null;
    double minDiff = Double.MAX_VALUE;

    int targetHeight = h;

    // Try to find an size match aspect ratio and size
    for (Camera.Size size : sizes) {
      double ratio = (double) size.width / size.height;
      if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
      if (Math.abs(size.height - targetHeight) < minDiff) {
        optimalSize = size;
        minDiff = Math.abs(size.height - targetHeight);
      }
    }

    // Cannot find the one match the aspect ratio, ignore the requirement
    if (optimalSize == null) {
      minDiff = Double.MAX_VALUE;
      for (Camera.Size size : sizes) {
        if (Math.abs(size.height - targetHeight) < minDiff) {
          optimalSize = size;
          minDiff = Math.abs(size.height - targetHeight);
        }
      }
    }
    return optimalSize;
  }

  public void setAutoFocus(boolean state) {
    if (mCamera != null && mPreviewing) {
      if (state == mAutoFocus) {
        return;
      }
      mAutoFocus = state;
      if (mAutoFocus) {
        if (mSurfaceCreated) { // check if surface created before using autofocus
          Log.v(TAG, "Starting autofocus");
          safeAutoFocus();
        } else {
          scheduleAutoFocus(); // wait 1 sec and then do check again
        }
      } else {
        Log.v(TAG, "Cancelling autofocus");
        mCamera.cancelAutoFocus();
      }
    }
  }

  private void scheduleAutoFocus() {
    mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
  }

  @Override public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
    mSurfaceCreated = true;
    mSurfaceTexture = surface;
    stopCameraPreview();
    showCameraPreview();
  }

  @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

  }

  @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
    mSurfaceCreated = false;
    stopCameraPreview();
    return true;
  }

  @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {

  }
}
