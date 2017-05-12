package com.xfzbd.cqi.common.wrapper;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.xfzbd.cqi.BuildConfig;

/**
 * XGitHub
 * com.hayukleung.xgithub.common.wrapper
 * XLog.java
 *
 * by hayukleung
 * at 2017-03-31 15:42
 */

public class XLog {

  private static final String TAG = "XGitHub";

  public static void init() {
    Logger
        // default PRETTYLOGGER or use just init()
        .init(TAG)
        // default 2
        .methodCount(1)
        // default shown
        .hideThreadInfo()
        // default LogLevel.FULL
        .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
        // default 0
        .methodOffset(2);
    // default AndroidLogAdapter
    // .logAdapter();
  }

  public static void i(String message) {
    i(message, "");
  }

  public static void i(String message, Object... args) {
    Logger.i(message, args);
  }

  public static void d(String message) {
    d(message, "");
  }

  public static void d(String message, Object... args) {
    Logger.d(message, args);
  }

  public static void w(String message) {
    w(message, "");
  }

  public static void w(String message, Object... args) {
    Logger.w(message, args);
  }

  public static void e(String message) {
    e(message, "");
  }

  public static void e(String message, Object... args) {
    Logger.e(message, args);
  }

  public static void e(Throwable throwable) {
    e(throwable, "");
  }

  public static void e(Throwable throwable, String message) {
    e(throwable, message, "");
  }

  public static void e(Throwable throwable, String message, Object... args) {
    Logger.e(throwable, message, args);
  }
}
