package com.xfzbd.cqi.common;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * CQI
 * com.gdgs.cqi.common
 * CommonUtils.java
 *
 * by hayukleung
 * at 2017-04-29 11:47
 */

public class CommonUtils {

  public static void hideInputMethod(Context context, View v) {
    hideInputMethod(context, v.getWindowToken());
  }

  public static void hideInputMethod(Context context, IBinder token) {
    InputMethodManager manager =
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
    if (manager != null) {
      manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}
