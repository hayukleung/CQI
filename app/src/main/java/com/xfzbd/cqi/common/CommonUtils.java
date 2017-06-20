package com.xfzbd.cqi.common;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.Locale;

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

  public static void showInputMethod(Context context, View v) {
    showInputMethod(context, v.getWindowToken());
  }

  public static void showInputMethod(Context context, IBinder token) {
    InputMethodManager manager =
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
    if (manager != null) {
      manager.showSoftInputFromInputMethod(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  /**
   * 给 string 里的 toColor 染色
   *
   * @param context
   * @param string
   * @param toColor
   * @param colorRes
   * @return
   */
  public static String color(Context context, String string, String toColor,
      @ColorRes int colorRes) {

    if (null == string) {
      return "";
    }

    if (null == toColor) {
      return string;
    }

    return string.replace(toColor, String.format(Locale.CHINA, "<font color='%d'>%s</font>",
        ContextCompat.getColor(context, colorRes), toColor));
  }

  /**
   * 判断两个字符串对象是否一致
   *
   * @param a
   * @param b
   * @return
   */
  public static boolean isStringTheSame(String a, String b) {
    if (null != a && null != b && a.equals(b)) {
      return true;
    }
    if (null == a && null == b) {
      return true;
    }
    return false;
  }
}
