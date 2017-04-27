package com.zbar.core;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.zbar.R;

public class ViewFinderView extends View implements IViewFinder {
  protected static final int SPEED = 5;
  protected static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192, 128, 64 };
  protected static final int POINT_SIZE = 10;
  protected static final long ANIMATION_DELAY = 80L;
  private static final String TAG = "ViewFinderView";
  private static final int MIN_FRAME_WIDTH = 240;
  private static final int MIN_FRAME_HEIGHT = 240;
  private static final float LANDSCAPE_WIDTH_RATIO = 5f / 8;
  private static final float LANDSCAPE_HEIGHT_RATIO = 5f / 8;
  private static final int LANDSCAPE_MAX_FRAME_WIDTH = (int) (1920 * LANDSCAPE_WIDTH_RATIO);
  // = 5/8 * 1080
  // = 5/8 * 1920
  private static final int LANDSCAPE_MAX_FRAME_HEIGHT = (int) (1080 * LANDSCAPE_HEIGHT_RATIO);
  private static final float PORTRAIT_WIDTH_RATIO = 7f / 8;
  private static final float PORTRAIT_HEIGHT_RATIO = 3f / 8;
  private static final int PORTRAIT_MAX_FRAME_WIDTH = (int) (1080 * PORTRAIT_WIDTH_RATIO);
  // = 3/8 * 1920
  // = 7/8 * 1080
  private static final int PORTRAIT_MAX_FRAME_HEIGHT = (int) (1920 * PORTRAIT_HEIGHT_RATIO);
  private final int mDefaultLaserColor = getResources().getColor(R.color.viewfinder_laser);
  private final int mDefaultMaskColor = getResources().getColor(R.color.viewfinder_mask);
  private final int mDefaultBorderColor = getResources().getColor(R.color.viewfinder_border);
  private final int mDefaultBorderStrokeWidth =
      getResources().getInteger(R.integer.viewfinder_border_width);
  private final int mDefaultBorderLineLength =
      getResources().getInteger(R.integer.viewfinder_border_length);
  protected Rect mFramingRect;
  protected RectF mFrameRect;
  protected int scannerAlpha;
  protected float mSlideTop;
  protected Paint mLaserPaint;
  protected Paint mFinderMaskPaint;
  protected Paint mBorderPaint;
  protected int mBorderLineLength;

  public ViewFinderView(Context context) {
    super(context);
    init();
  }

  public ViewFinderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private static int findDesiredDimensionInRange(float ratio, int resolution, int hardMin,
      int hardMax) {
    int dim = (int) (ratio * resolution);
    if (dim < hardMin) {
      return hardMin;
    }
    if (dim > hardMax) {
      return hardMax;
    }
    return dim;
  }

  private void init() {
    //set up laser paint
    mLaserPaint = new Paint();
    mLaserPaint.setColor(mDefaultLaserColor);
    mLaserPaint.setStyle(Paint.Style.FILL);

    //finder mask paint
    mFinderMaskPaint = new Paint();
    mFinderMaskPaint.setColor(mDefaultMaskColor);

    //border paint
    mBorderPaint = new Paint();
    mBorderPaint.setColor(mDefaultBorderColor);
    mBorderPaint.setStyle(Paint.Style.STROKE);
    mBorderPaint.setStrokeWidth(mDefaultBorderStrokeWidth);

    mBorderLineLength = mDefaultBorderLineLength;
  }

  public void setLaserColor(int laserColor) {
    mLaserPaint.setColor(laserColor);
  }

  public void setMaskColor(int maskColor) {
    mFinderMaskPaint.setColor(maskColor);
  }

  public void setBorderColor(int borderColor) {
    mBorderPaint.setColor(borderColor);
  }

  public void setBorderStrokeWidth(int borderStrokeWidth) {
    mBorderPaint.setStrokeWidth(borderStrokeWidth);
  }

  public void setBorderLineLength(int borderLineLength) {
    mBorderLineLength = borderLineLength;
  }

  public void setupViewFinder() {
    updateFramingRect();
    invalidate();
  }

  public Rect getFramingRect() {
    return mFramingRect;
  }

  @Override public void onDraw(Canvas canvas) {
    if (mFramingRect == null) {
      return;
    }

    drawViewFinderMask(canvas);
    drawViewFinderBorder(canvas);
    drawLaser(canvas);
  }

  public void drawViewFinderMask(Canvas canvas) {
    int width = canvas.getWidth();
    int height = canvas.getHeight();

    canvas.drawRect(0, 0, width, mFramingRect.top, mFinderMaskPaint);
    canvas.drawRect(0, mFramingRect.top, mFramingRect.left, mFramingRect.bottom, mFinderMaskPaint);
    canvas.drawRect(mFramingRect.right, mFramingRect.top, width, mFramingRect.bottom,
        mFinderMaskPaint);
    canvas.drawRect(0, mFramingRect.bottom, width, height, mFinderMaskPaint);
  }

  public void drawViewFinderBorder(Canvas canvas) {
    float width = mBorderPaint.getStrokeWidth();
    canvas.drawLine(mFramingRect.left + width / 2, mFramingRect.top, mFramingRect.left + width / 2,
        mFramingRect.top + mBorderLineLength, mBorderPaint);
    canvas.drawLine(mFramingRect.left, mFramingRect.top + width / 2,
        mFramingRect.left + mBorderLineLength, mFramingRect.top + width / 2, mBorderPaint);

    canvas.drawLine(mFramingRect.left + width / 2, mFramingRect.bottom,
        mFramingRect.left + width / 2, mFramingRect.bottom - mBorderLineLength, mBorderPaint);
    canvas.drawLine(mFramingRect.left, mFramingRect.bottom - width / 2,
        mFramingRect.left + mBorderLineLength, mFramingRect.bottom - width / 2, mBorderPaint);

    canvas.drawLine(mFramingRect.right - width / 2, mFramingRect.top,
        mFramingRect.right - width / 2, mFramingRect.top + mBorderLineLength, mBorderPaint);
    canvas.drawLine(mFramingRect.right, mFramingRect.top + width / 2,
        mFramingRect.right - mBorderLineLength, mFramingRect.top + width / 2, mBorderPaint);

    canvas.drawLine(mFramingRect.right - width / 2, mFramingRect.bottom,
        mFramingRect.right - width / 2, mFramingRect.bottom - mBorderLineLength, mBorderPaint);
    canvas.drawLine(mFramingRect.right, mFramingRect.bottom - width / 2,
        mFramingRect.right - mBorderLineLength, mFramingRect.bottom - width / 2, mBorderPaint);
  }

  public void drawLaser(Canvas canvas) {
    // Draw a red "laser scanner" line through the middle to show decoding is active
    mLaserPaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
    scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;

    final int height = 4;
    if (mFrameRect.contains(mFramingRect.centerX(), mSlideTop) && mFrameRect.contains(
        mFramingRect.centerX(), mSlideTop + height)) {
      mSlideTop += SPEED;
    } else {
      mSlideTop = mFrameRect.top;
    }
    canvas.drawRect(mFrameRect.left, mSlideTop, mFrameRect.right, mSlideTop + height, mLaserPaint);

    postInvalidateDelayed(ANIMATION_DELAY, mFramingRect.left - POINT_SIZE,
        mFramingRect.top - POINT_SIZE, mFramingRect.right + POINT_SIZE,
        mFramingRect.bottom + POINT_SIZE);
  }

  @Override protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
    updateFramingRect();
  }

  public synchronized void updateFramingRect() {
    Point viewResolution = new Point(getWidth(), getHeight());
    int width;
    int height;
    int orientation = DisplayUtils.getScreenOrientation(getContext());

    if (orientation != Configuration.ORIENTATION_PORTRAIT) {
      width = findDesiredDimensionInRange(LANDSCAPE_WIDTH_RATIO, viewResolution.x, MIN_FRAME_WIDTH,
          LANDSCAPE_MAX_FRAME_WIDTH);
      height =
          findDesiredDimensionInRange(LANDSCAPE_HEIGHT_RATIO, viewResolution.y, MIN_FRAME_HEIGHT,
              LANDSCAPE_MAX_FRAME_HEIGHT);
    } else {
      width = findDesiredDimensionInRange(PORTRAIT_WIDTH_RATIO, viewResolution.x, MIN_FRAME_WIDTH,
          PORTRAIT_MAX_FRAME_WIDTH);
      height =
          findDesiredDimensionInRange(PORTRAIT_HEIGHT_RATIO, viewResolution.y, MIN_FRAME_HEIGHT,
              PORTRAIT_MAX_FRAME_HEIGHT);
    }

    int leftOffset = (viewResolution.x - width) / 2;
    int topOffset = (viewResolution.y - height) / 2;
    mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);

    float borderWidth = mBorderPaint.getStrokeWidth();
    mFrameRect = new RectF(mFramingRect.left + borderWidth, mFramingRect.top + borderWidth,
        mFramingRect.right - borderWidth, mFramingRect.bottom - borderWidth);
  }
}
