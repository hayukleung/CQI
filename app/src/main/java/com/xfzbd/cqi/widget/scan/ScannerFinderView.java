package com.xfzbd.cqi.widget.scan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import com.hayukleung.absolutescreenmatch.Screen;
import com.zbar.core.ViewFinderView;

/**
 * 扫描框和镭射线
 */
public class ScannerFinderView extends ViewFinderView {

  public ScannerFinderView(Context context) {
    super(context);
  }

  public ScannerFinderView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public void drawLaser(Canvas canvas) {
    // Draw a red "laser scanner" line through the middle to show decoding is active
    mLaserPaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
    scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
    int[] colors = new int[] { 0x00000000, 0xffff0000, 0x00000000 };
    LinearGradient gradient =
        new LinearGradient(mFrameRect.left, mSlideTop, mFrameRect.right, mSlideTop + 4, colors,
            null, Shader.TileMode.CLAMP);
    mLaserPaint.setShader(gradient);

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

  @Override public synchronized void updateFramingRect() {
    Point viewResolution = new Point(getWidth(), getHeight());
    int width;
    int height;

    width = height = Screen.getInstance(getContext()).dp2px(220);

    int leftOffset = (viewResolution.x - width) / 2;
    int topOffset = (viewResolution.y - height) / 2 - Screen.getInstance(getContext()).dp2px(40);
    mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);

    float borderWidth = mBorderPaint.getStrokeWidth();
    mFrameRect = new RectF(mFramingRect.left + borderWidth, mFramingRect.top + borderWidth,
        mFramingRect.right - borderWidth, mFramingRect.bottom - borderWidth);
  }
}
