/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gdgs.cqi.widget.srl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.animation.Animation;

/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 */
class CircleImageView extends AppCompatImageView {

  private static final int KEY_SHADOW_COLOR = 0x1E000000;
  private static final int FILL_SHADOW_COLOR = 0x3D000000;
  // PX
  private static final float X_OFFSET = 0f;
  private static final float Y_OFFSET = 1.75f;
  private static final float SHADOW_RADIUS = 3.5f;
  private static final int SHADOW_ELEVATION = 4;
  int mShadowRadius;
  private Animation.AnimationListener mListener;

  CircleImageView(Context context, int color) {
    super(context);
    final float density = getContext().getResources().getDisplayMetrics().density;
    final int shadowYOffset = (int) (density * Y_OFFSET);
    final int shadowXOffset = (int) (density * X_OFFSET);

    mShadowRadius = (int) (density * SHADOW_RADIUS);

    ShapeDrawable circle;
    if (elevationSupported()) {
      circle = new ShapeDrawable(new OvalShape());
      ViewCompat.setElevation(this, SHADOW_ELEVATION * density);
    } else {
      OvalShape oval = new OvalShadow(mShadowRadius);
      circle = new ShapeDrawable(oval);
      ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
      circle.getPaint()
          .setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset, KEY_SHADOW_COLOR);
      final int padding = mShadowRadius;
      // set padding so the inner image sits correctly within the shadow.
      setPadding(padding, padding, padding, padding);
    }
    circle.getPaint().setColor(color);
    ViewCompat.setBackground(this, circle);
  }

  private boolean elevationSupported() {
    return android.os.Build.VERSION.SDK_INT >= 21;
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (!elevationSupported()) {
      setMeasuredDimension(getMeasuredWidth() + mShadowRadius * 2,
          getMeasuredHeight() + mShadowRadius * 2);
    }
  }

  public void setAnimationListener(Animation.AnimationListener listener) {
    mListener = listener;
  }

  /**
   * Update the background color of the circle image view.
   *
   * @param colorRes Id of a color resource.
   */
  public void setBackgroundColorRes(int colorRes) {
    setBackgroundColor(ContextCompat.getColor(getContext(), colorRes));
  }

  @Override public void setBackgroundColor(int color) {
    if (getBackground() instanceof ShapeDrawable) {
      ((ShapeDrawable) getBackground()).getPaint().setColor(color);
    }
  }

  @Override public void onAnimationStart() {
    super.onAnimationStart();
    if (mListener != null) {
      mListener.onAnimationStart(getAnimation());
    }
  }

  @Override public void onAnimationEnd() {
    super.onAnimationEnd();
    if (mListener != null) {
      mListener.onAnimationEnd(getAnimation());
    }
  }

  private class OvalShadow extends OvalShape {
    private RadialGradient mRadialGradient;
    private Paint mShadowPaint;

    OvalShadow(int shadowRadius) {
      super();
      mShadowPaint = new Paint();
      mShadowRadius = shadowRadius;
      updateRadialGradient((int) rect().width());
    }

    private void updateRadialGradient(int diameter) {
      mRadialGradient = new RadialGradient(diameter / 2, diameter / 2, mShadowRadius,
          new int[] { FILL_SHADOW_COLOR, Color.TRANSPARENT }, null, Shader.TileMode.CLAMP);
      mShadowPaint.setShader(mRadialGradient);
    }

    @Override protected void onResize(float width, float height) {
      super.onResize(width, height);
      updateRadialGradient((int) width);
    }

    @Override public void draw(Canvas canvas, Paint paint) {
      final int viewWidth = CircleImageView.this.getWidth();
      final int viewHeight = CircleImageView.this.getHeight();
      canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2, mShadowPaint);
      canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2 - mShadowRadius, paint);
    }
  }
}