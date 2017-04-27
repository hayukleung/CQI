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
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import com.gdgs.cqi.common.wrapper.XLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * Fancy progress indicator for Material theme.
 */
class ShyaringanProgressDrawable extends Drawable implements Animatable {
  public static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
  // Maps to ProgressBar.Large style
  static final int LARGE = 0;
  // Maps to ProgressBar default style
  static final int DEFAULT = 1;
  private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
  private static final float FULL_ROTATION = 1080.0f;
  // Maps to ProgressBar default style
  private static final int CIRCLE_DIAMETER = 40;
  private static final float CENTER_RADIUS = 8.75f; //should add up to 10 when + stroke_width
  private static final float STROKE_WIDTH = 2.5f;
  // Maps to ProgressBar.Large style
  private static final int CIRCLE_DIAMETER_LARGE = 56;
  private static final float CENTER_RADIUS_LARGE = 12.5f;
  private static final float STROKE_WIDTH_LARGE = 3f;
  private static final int[] COLORS = new int[] {
      Color.BLACK
  };
  /**
   * The value in the linear interpolator for animating the drawable at which
   * the color transition should start
   */
  private static final float COLOR_START_DELAY_OFFSET = 0.75f;
  private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
  private static final float START_TRIM_DURATION_OFFSET = 0.5f;
  /** The duration of a single progress spin in milliseconds. */
  private static final int ANIMATION_DURATION = 1332;
  /** The number of points in the progress "star". */
  private static final float NUM_POINTS = 5f;
  /** Layout info for the arrowhead in dp */
  private static final int ARROW_WIDTH = 10;
  private static final int ARROW_HEIGHT = 5;
  private static final float ARROW_OFFSET_ANGLE = 5;
  /** Layout info for the arrowhead for the large spinner in dp */
  private static final int ARROW_WIDTH_LARGE = 12;
  private static final int ARROW_HEIGHT_LARGE = 6;
  private static final float MAX_PROGRESS_ARC = .8f;
  /** The list of animators operating on this drawable. */
  private final ArrayList<Animation> mAnimators = new ArrayList<Animation>();
  /** The indicator ring, used to manage animation state. */
  private final Shyaringan mRing;
  private final Callback mCallback = new Callback() {

    @Override public void invalidateDrawable(Drawable d) {
      invalidateSelf();
    }

    @Override public void scheduleDrawable(Drawable d, Runnable what, long when) {
      scheduleSelf(what, when);
    }

    @Override public void unscheduleDrawable(Drawable d, Runnable what) {
      unscheduleSelf(what);
    }
  };
  float mRotationCount;
  boolean mFinishing;
  /** Canvas rotation in degrees. */
  // private float mRotation;
  private Resources mResources;
  private View mParent;
  private Animation mAnimation;
  private double mWidth;
  private double mHeight;

  public ShyaringanProgressDrawable(Context context, View parent) {
    mParent = parent;
    mResources = context.getResources();

    mRing = new Shyaringan(mCallback);
    mRing.setColors(COLORS);

    updateSizes(DEFAULT);
    setupAnimators();
  }

  private void setSizeParameters(double progressCircleWidth, double progressCircleHeight,
      double centerRadius, double strokeWidth, float arrowWidth, float arrowHeight) {
    final Shyaringan ring = mRing;
    final DisplayMetrics metrics = mResources.getDisplayMetrics();
    final float screenDensity = metrics.density;

    mWidth = progressCircleWidth * screenDensity;
    mHeight = progressCircleHeight * screenDensity;
    ring.setStrokeWidth((float) strokeWidth * screenDensity);
    ring.setCenterRadius(centerRadius * screenDensity);
    ring.setColorIndex(0);
    // ring.setArrowDimensions(arrowWidth * screenDensity, arrowHeight * screenDensity);
    ring.setInsets((int) mWidth, (int) mHeight);
  }

  /**
   * Set the overall size for the progress spinner. This updates the radius
   * and stroke width of the ring.
   *
   * @param size One of {@link ShyaringanProgressDrawable.LARGE} or
   * {@link ShyaringanProgressDrawable.DEFAULT}
   */
  public void updateSizes(@ProgressDrawableSize int size) {
    if (size == LARGE) {
      setSizeParameters(CIRCLE_DIAMETER_LARGE, CIRCLE_DIAMETER_LARGE, CENTER_RADIUS_LARGE,
          STROKE_WIDTH_LARGE, ARROW_WIDTH_LARGE, ARROW_HEIGHT_LARGE);
    } else {
      setSizeParameters(CIRCLE_DIAMETER, CIRCLE_DIAMETER, CENTER_RADIUS, STROKE_WIDTH, ARROW_WIDTH,
          ARROW_HEIGHT);
    }
  }

  /**
   * @param scale Set the scale of the arrowhead for the spinner.
   */
  public void setArrowScale(float scale) {
    mRing.setArrowScale(scale);
  }

  /**
   * Set the start and end trim for the progress spinner arc.
   *
   * @param startAngle start angle
   * @param endAngle end angle
   */
  public void setStartEndTrim(float startAngle, float endAngle) {
    mRing.setStartTrim(startAngle);
    mRing.setEndTrim(endAngle);
  }

  /**
   * Set the amount of rotation to apply to the progress spinner.
   *
   * @param rotation Rotation is from [0..1]
   */
  public void setProgressRotation(float rotation) {
    mRing.setRotation(rotation);
  }

  /**
   * Update the background color of the circle image view.
   */
  public void setBackgroundColor(int color) {
    mRing.setBackgroundColor(color);
  }

  /**
   * Set the colors used in the progress animation from color resources.
   * The first color will also be the color of the bar that grows in response
   * to a user swipe gesture.
   *
   * @param colors
   */
  public void setColorSchemeColors(int... colors) {
    mRing.setColors(colors);
    mRing.setColorIndex(0);
  }

  @Override public void draw(Canvas c) {
    final Rect bounds = getBounds();
    final int saveCount = c.save();
    c.rotate(0f, bounds.exactCenterX(), bounds.exactCenterY());
    mRing.draw(c, bounds);
    c.restoreToCount(saveCount);
  }

  @Override public void start() {
    mAnimation.reset();
    mRing.storeOriginals();
    // Already showing some part of the ring
    if (mRing.getEndTrim() != mRing.getStartTrim()) {
      mFinishing = true;
      // mAnimation.setDuration(ANIMATION_DURATION / 2);
      mAnimation.setDuration(0);
      mParent.startAnimation(mAnimation);
    } else {
      mRing.setColorIndex(0);
      mRing.resetOriginals();
      mAnimation.setDuration(ANIMATION_DURATION);
      mParent.startAnimation(mAnimation);
    }
  }

  @Override public void stop() {
    mParent.clearAnimation();
    invalidateSelf();
    mRing.setColorIndex(0);
    mRing.resetOriginals();
  }

  @Override public boolean isRunning() {
    final ArrayList<Animation> animators = mAnimators;
    final int N = animators.size();
    for (int i = 0; i < N; i++) {
      final Animation animator = animators.get(i);
      if (animator.hasStarted() && !animator.hasEnded()) {
        return true;
      }
    }
    return false;
  }

  float getMinProgressArc(Shyaringan ring) {
    return (float) Math.toRadians(ring.getStrokeWidth() / (2 * Math.PI * ring.getCenterRadius()));
  }

  @Override public int getIntrinsicHeight() {
    return (int) mHeight;
  }

  // Adapted from ArgbEvaluator.java
  private int evaluateColorChange(float fraction, int startValue, int endValue) {
    int startInt = (Integer) startValue;
    int startA = (startInt >> 24) & 0xff;
    int startR = (startInt >> 16) & 0xff;
    int startG = (startInt >> 8) & 0xff;
    int startB = startInt & 0xff;

    int endInt = (Integer) endValue;
    int endA = (endInt >> 24) & 0xff;
    int endR = (endInt >> 16) & 0xff;
    int endG = (endInt >> 8) & 0xff;
    int endB = endInt & 0xff;

    return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
        | (int) ((startR + (int) (fraction
        * (endR - startR))) << 16)
        | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
        | (int) ((startB + (int) (fraction * (endB - startB))));
  }

  /**
   * Update the ring color if this is within the last 25% of the animation.
   * The new ring color will be a translation from the starting ring color to
   * the next color.
   */
  void updateRingColor(float interpolatedTime, Shyaringan ring) {
    if (interpolatedTime > COLOR_START_DELAY_OFFSET) {
      // scale the interpolatedTime so that the full
      // transformation from 0 - 1 takes place in the
      // remaining time
      ring.setColor(evaluateColorChange(
          (interpolatedTime - COLOR_START_DELAY_OFFSET) / (1.0f - COLOR_START_DELAY_OFFSET),
          ring.getStartingColor(), ring.getNextColor()));
    }
  }

  void applyFinishTranslation(float interpolatedTime, Shyaringan ring) {
    // shrink back down and complete a full rotation before
    // starting other circles
    // Rotation goes between [0..1].
    updateRingColor(interpolatedTime, ring);
    float targetRotation = (float) (Math.floor(ring.getStartingRotation() / MAX_PROGRESS_ARC) + 1f);
    final float minProgressArc = getMinProgressArc(ring);
    final float startTrim = ring.getStartingStartTrim()
        + (ring.getStartingEndTrim() - minProgressArc - ring.getStartingStartTrim())
        * interpolatedTime;
    ring.setStartTrim(startTrim);
    ring.setEndTrim(ring.getStartingEndTrim());
    final float rotation =
        ring.getStartingRotation() + ((targetRotation - ring.getStartingRotation())
            * interpolatedTime);
    XLog.e("srl --> ring " + rotation);
    ring.setRotation(rotation);
  }

  private void setupAnimators() {
    final Shyaringan ring = mRing;
    final Animation animation = new Animation() {
      @Override public void applyTransformation(float interpolatedTime, Transformation t) {
        if (mFinishing) {
          applyFinishTranslation(interpolatedTime, ring);
        } else {
          // The minProgressArc is calculated from 0 to create an
          // angle that matches the stroke width.
          final float minProgressArc = getMinProgressArc(ring);
          final float startingEndTrim = ring.getStartingEndTrim();
          final float startingTrim = ring.getStartingStartTrim();
          final float startingRotation = ring.getStartingRotation();

          updateRingColor(interpolatedTime, ring);

          // Moving the start trim only occurs in the first 50% of a
          // single ring animation
          // if (interpolatedTime <= START_TRIM_DURATION_OFFSET) {
          // scale the interpolatedTime so that the full
          // transformation from 0 - 1 takes place in the
          // remaining time
          // final float scaledTime = (interpolatedTime) / (1.0f - START_TRIM_DURATION_OFFSET);
          // final float startTrim = startingTrim + ((MAX_PROGRESS_ARC - minProgressArc)
          // * MATERIAL_INTERPOLATOR.getInterpolation(scaledTime));
          // ring.setStartTrim(startTrim);
          // }

          // Moving the end trim starts after 50% of a single ring
          // animation completes
          // if (interpolatedTime > END_TRIM_START_DELAY_OFFSET) {
          // scale the interpolatedTime so that the full
          // transformation from 0 - 1 takes place in the
          // remaining time
          // final float minArc = MAX_PROGRESS_ARC - minProgressArc;
          // float scaledTime = (interpolatedTime - START_TRIM_DURATION_OFFSET) / (1.0f - START_TRIM_DURATION_OFFSET);
          // final float endTrim = startingEndTrim + (minArc * MATERIAL_INTERPOLATOR.getInterpolation(scaledTime));
          // ring.setEndTrim(endTrim);
          // }

          final float rotation = (startingRotation + (500f * interpolatedTime)) % 360f;
          XLog.e("srl --> rotation - %f - setRotation - shyaringan", rotation);
          ring.setRotation(rotation);

          // float groupRotation = ((FULL_ROTATION / NUM_POINTS) * interpolatedTime) + (FULL_ROTATION * (mRotationCount / NUM_POINTS));
          // setRotation(groupRotation);
        }
      }
    };
    animation.setRepeatCount(Animation.INFINITE);
    animation.setRepeatMode(Animation.RESTART);
    animation.setInterpolator(LINEAR_INTERPOLATOR);
    animation.setAnimationListener(new Animation.AnimationListener() {

      @Override public void onAnimationStart(Animation animation) {
        XLog.e("srl --> refresh 1 - " + System.currentTimeMillis());
        mRotationCount = 0;
      }

      @Override public void onAnimationEnd(Animation animation) {
        // do nothing
      }

      @Override public void onAnimationRepeat(Animation animation) {
        ring.storeOriginals();
        ring.goToNextColor();
        ring.setStartTrim(ring.getEndTrim());
        if (mFinishing) {
          // finished closing the last ring from the swipe gesture; go
          // into progress mode
          mFinishing = false;
          animation.setDuration(ANIMATION_DURATION);
        } else {
          mRotationCount = (mRotationCount + 1) % (NUM_POINTS);
        }
      }
    });
    mAnimation = animation;
  }

  @Retention(RetentionPolicy.SOURCE) @IntDef({ LARGE, DEFAULT }) @interface ProgressDrawableSize {
  }

  @Override public int getIntrinsicWidth() {
    return (int) mWidth;
  }

  /**
   * 写轮眼
   */
  private static class Shyaringan {

    private static final int GOGOK_COUNT = 3;
    private final RectF mTempBounds = new RectF();
    // private final Paint mArrowPaint = new Paint();
    private final Paint mPaint = new Paint();
    private final Callback mCallback;
    private final Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintCircle;
    private Paint mPaintGogok;
    // private int mAngle = -6;
    // private int mAngle = 0;
    private RectF mRectFGogok;
    private Path mPathGogok;
    private float mStartTrim = 0.0f;
    private float mEndTrim = 0.0f;
    private float mRotation = 0.0f;
    private float mStrokeWidth = 5.0f;
    private float mStrokeInset = 2.5f;
    private int[] mColors;
    // mColorIndex represents the offset into the available mColors that the
    // progress circle should currently display. As the progress circle is
    // animating, the mColorIndex moves by one to the next available color.
    private int mColorIndex;
    private float mStartingStartTrim;
    private float mStartingEndTrim;
    private float mStartingRotation;
    // private Path mArrow;
    private float mArrowScale;
    private double mRingCenterRadius;
    // private int mArrowWidth;
    // private int mArrowHeight;
    private int mAlpha;
    private int mBackgroundColor;
    private int mCurrentColor;

    Shyaringan(Callback callback) {
      mCallback = callback;

      mPaint.setStrokeCap(Paint.Cap.SQUARE);
      mPaint.setAntiAlias(true);
      mPaint.setStyle(Style.STROKE);

      // mArrowPaint.setStyle(Paint.Style.FILL);
      // mArrowPaint.setAntiAlias(true);

      mPaintCircle = new Paint();
      mPaintCircle.setAntiAlias(true);

      mPaintGogok = new Paint();
      mPaintGogok.setAntiAlias(true);
      mPaintGogok.setStyle(Paint.Style.FILL);
      mPaintGogok.setColor(Color.argb(255, 22, 22, 22));

      mPathGogok = new Path();
    }

    void setBackgroundColor(int color) {
      mBackgroundColor = color;
    }

    /**
     * Draw the progress spinner
     */
    void draw(Canvas c, Rect bounds) {
      final RectF arcBounds = mTempBounds;
      arcBounds.set(bounds);
      arcBounds.inset(mStrokeInset, mStrokeInset);

      // final float startAngle = (mStartTrim + mRotation) * 360;
      // final float endAngle = (mEndTrim + mRotation) * 360;
      // float sweepAngle = endAngle - startAngle;

      mPaint.setColor(mCurrentColor);
      // c.drawArc(arcBounds, startAngle, sweepAngle, false, mPaint);
      // c.rotate(startAngle + sweepAngle - ARROW_OFFSET_ANGLE, bounds.exactCenterX(), bounds.exactCenterY());
      c.rotate(mRotation, bounds.exactCenterX(), bounds.exactCenterY());

      if (mAlpha < 255) {
        mCirclePaint.setColor(mBackgroundColor);
        mCirclePaint.setAlpha(255 - mAlpha);

        mPaintCircle.setAlpha(255 - mAlpha);
        // mPaintGogok.setAlpha(255 - mAlpha);
        c.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), bounds.width() / 2,
            mCirclePaint);
      }

      int cx = bounds.width() / 2;
      int cy = bounds.height() / 2;
      int radius = bounds.width() / 2 / 10 * 9;
      int strokeWidthUnit = bounds.width() / 200;

      // 绘制背景颜色
      {
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setColor(mCurrentColor);
        c.drawCircle(cx, cy, radius, mPaintCircle);
      }
      // 绘制黑环
      {
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.argb(255, 22, 22, 22));
        mPaintCircle.setStrokeWidth(strokeWidthUnit * 2);
        c.drawCircle(cx, cy, radius, mPaintCircle);
        mPaintCircle.setStrokeWidth(strokeWidthUnit);
        c.drawCircle(cx, cy, radius / 5 * 3, mPaintCircle);
      }
      // 绘制眼珠
      {
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setColor(Color.argb(255, 22, 22, 22));
        c.drawCircle(cx, cy, radius / 5, mPaintCircle);
      }
      // c.rotate(0, cx, cy);
      // 绘制勾玉
      {
        int count = GOGOK_COUNT;
        while (count-- > 0) {

          mPaintGogok.setStrokeWidth(strokeWidthUnit);

          float rGogokUnit = (float) bounds.width() / 1000f;
          if (null == mRectFGogok) {
            mRectFGogok = new RectF();
          }
          mPathGogok.reset();
          mRectFGogok.set(
              // l
              cx - rGogokUnit * 50,
              // t
              cy - radius / 5 * 3 - rGogokUnit * 50,
              // r
              cx + rGogokUnit * 50,
              // b
              cy - radius / 5 * 3 + rGogokUnit * 50);
          mPathGogok.arcTo(mRectFGogok, 90, 180);
          mRectFGogok.set(
              // l
              cx - rGogokUnit * 15,
              // t
              cy - radius / 5 * 3 - rGogokUnit * 50,
              // r
              cx + rGogokUnit * 15,
              // b
              cy - radius / 5 * 3 - rGogokUnit * 20);
          mPathGogok.arcTo(mRectFGogok, 270, -180);
          mRectFGogok.set(
              // l
              cx - rGogokUnit * 35,
              // t
              cy - radius / 5 * 3 - rGogokUnit * 20,
              // r
              cx + rGogokUnit * 35,
              // b
              cy - radius / 5 * 3 + rGogokUnit * 50);
          mPathGogok.arcTo(mRectFGogok, 270, 180);
          mPathGogok.close();

          c.save();
          c.rotate(360 / GOGOK_COUNT * count, cx, cy);
          c.rotate(30, cx, cy - radius / 5 * 3);
          c.drawPath(mPathGogok, mPaintGogok);
          c.restore();
        }
      }
      // postInvalidateDelayed(30);
    }

    /**
     * Set the colors the progress spinner alternates between.
     *
     * @param colors Array of integers describing the colors. Must be non-<code>null</code>.
     */
    void setColors(@NonNull int[] colors) {
      mColors = colors;
      // if colors are reset, make sure to reset the color index as well
      setColorIndex(0);
    }

    /**
     * @param index Index into the color array of the color to display in
     * the progress spinner.
     */
    void setColorIndex(int index) {
      mColorIndex = index;
      mCurrentColor = mColors[mColorIndex];
    }

    /**
     * Set the absolute color of the progress spinner. This is should only
     * be used when animating between current and next color when the
     * spinner is rotating.
     *
     * @param color int describing the color.
     */
    public void setColor(int color) {
      mCurrentColor = color;
    }

    /**
     * @return int describing the next color the progress spinner should use when drawing.
     */
    int getNextColor() {
      return mColors[getNextColorIndex()];
    }

    private int getNextColorIndex() {
      return (mColorIndex + 1) % (mColors.length);
    }

    /**
     * Proceed to the next available ring color. This will automatically
     * wrap back to the beginning of colors.
     */
    void goToNextColor() {
      setColorIndex(getNextColorIndex());
    }

    void setColorFilter(ColorFilter filter) {
      mPaint.setColorFilter(filter);
      mPaintCircle.setColorFilter(filter);
      mPaintGogok.setColorFilter(filter);
      invalidateSelf();
    }

    private void invalidateSelf() {
      mCallback.invalidateDrawable(null);
    }

    /**
     * @return Current alpha of the progress spinner and arrowhead.
     */
    public int getAlpha() {
      return mAlpha;
    }

    /**
     * @param alpha Set the alpha of the progress spinner and associated arrowhead.
     */
    public void setAlpha(int alpha) {
      mAlpha = alpha;
    }

    float getStrokeWidth() {
      return mStrokeWidth;
    }

    /**
     * @param strokeWidth Set the stroke width of the progress spinner in pixels.
     */
    void setStrokeWidth(float strokeWidth) {
      mStrokeWidth = strokeWidth;
      // mPaint.setStrokeWidth(strokeWidth);
      invalidateSelf();
    }

    float getStartTrim() {
      return mStartTrim;
    }

    void setStartTrim(float startTrim) {
      mStartTrim = startTrim;
      invalidateSelf();
    }

    float getStartingStartTrim() {
      return mStartingStartTrim;
    }

    float getStartingEndTrim() {
      return mStartingEndTrim;
    }

    int getStartingColor() {
      return mColors[mColorIndex];
    }

    float getEndTrim() {
      return mEndTrim;
    }

    void setEndTrim(float endTrim) {
      mEndTrim = endTrim;
      invalidateSelf();
    }

    public float getRotation() {
      return mRotation;
    }

    void setRotation(float rotation) {
      mRotation = rotation;
      invalidateSelf();
    }

    void setInsets(int width, int height) {
      final float minEdge = (float) Math.min(width, height);
      float insets;
      if (mRingCenterRadius <= 0 || minEdge < 0) {
        insets = (float) Math.ceil(mStrokeWidth / 2.0f);
      } else {
        insets = (float) (minEdge / 2.0f - mRingCenterRadius);
      }
      mStrokeInset = insets;
    }

    double getCenterRadius() {
      return mRingCenterRadius;
    }

    /**
     * @param centerRadius Inner radius in px of the circle the progress
     * spinner arc traces.
     */
    void setCenterRadius(double centerRadius) {
      mRingCenterRadius = centerRadius;
    }

    /**
     * @param scale Set the scale of the arrowhead for the spinner.
     */
    void setArrowScale(float scale) {
      if (scale != mArrowScale) {
        mArrowScale = scale;
        invalidateSelf();
      }
    }

    /**
     * @return The amount the progress spinner is currently rotated, between [0..1].
     */
    float getStartingRotation() {
      return mStartingRotation;
    }

    /**
     * If the start / end trim are offset to begin with, store them so that
     * animation starts from that offset.
     */
    void storeOriginals() {
      mStartingStartTrim = mStartTrim;
      mStartingEndTrim = mEndTrim;
      mStartingRotation = mRotation;
    }

    /**
     * Reset the progress spinner to default rotation, start and end angles.
     */
    void resetOriginals() {
      mStartingStartTrim = 0;
      mStartingEndTrim = 0;
      mStartingRotation = 0;
      setStartTrim(0);
      setEndTrim(0);
      setRotation(0);
    }
  }

  @Override public void setAlpha(int alpha) {
    mRing.setAlpha(alpha);
  }

  public int getAlpha() {
    return mRing.getAlpha();
  }

  @Override public void setColorFilter(ColorFilter colorFilter) {
    mRing.setColorFilter(colorFilter);
  }

  @Override public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }
}
