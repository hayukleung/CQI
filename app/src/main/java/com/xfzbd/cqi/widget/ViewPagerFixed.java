package com.xfzbd.cqi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.xfzbd.cqi.common.wrapper.XLog;

public class ViewPagerFixed extends android.support.v4.view.ViewPager {

  public ViewPagerFixed(Context context) {
    super(context);
  }

  public ViewPagerFixed(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    try {
      return super.onInterceptTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      XLog.e(ex);
    }
    return false;
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    try {
      return super.onTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      XLog.e(ex);
    }
    return false;
  }
}