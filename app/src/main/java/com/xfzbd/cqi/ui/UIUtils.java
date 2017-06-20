package com.xfzbd.cqi.ui;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.xfzbd.cqi.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * XGitHub
 * com.hayukleung.xgithub.view
 * UIUtils.java
 *
 * by hayukleung
 * at 2017-04-05 15:37
 */

public class UIUtils {

  public static TextView setCenterTitle(Activity activity, Toolbar toolbar, int titleRes) {
    return setCenterTitle(activity, toolbar, activity.getString(titleRes));
  }

  public static TextView setCenterTitle(Activity activity, Toolbar toolbar, String title) {
    TextView titleView = (TextView) activity.getLayoutInflater()
        .inflate(R.layout.layout_toolbar_title, toolbar, false);
    titleView.setSingleLine();
    titleView.setEllipsize(TextUtils.TruncateAt.END);
    titleView.setText(title);
    Toolbar.LayoutParams lp = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,
        Toolbar.LayoutParams.WRAP_CONTENT);
    lp.gravity = Gravity.CENTER;
    toolbar.addView(titleView, lp);
    return titleView;
  }

  public static boolean isWindowTranslucentStatus(FragmentActivity activity) {
    Window window = activity.getWindow();
    WindowManager.LayoutParams params = window.getAttributes();
    View decorView = window.getDecorView();
    return ((params.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) || (Build.VERSION.SDK_INT
        >= Build.VERSION_CODES.LOLLIPOP
        && (decorView.getSystemUiVisibility() & (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN));
  }

  /**
   * @param fragment
   * @param isLight true: 白色背景, 深色文字
   */
  public static void requestStatusBarLight(XFragment fragment, boolean isLight) {
    requestStatusBarLight(fragment, isLight, isLight ? 0xffcccccc : 0xffffffff);
  }

  /**
   * @param fragment
   * @param isLight 6.0及以上系统生效
   * @param color 6.0以下系统生效
   */
  public static void requestStatusBarLight(XFragment fragment, boolean isLight, int color) {
    View decorView = fragment.getActivity().getWindow().getDecorView();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (isLight) {
        decorView.setSystemUiVisibility(
            decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      } else {
        decorView.setSystemUiVisibility(
            decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      }
      processPrivateAPI(fragment.getActivity().getWindow(), isLight);
    } else {
      // Android 6.0 以下系统适配
      View view = fragment.getStatusBar();
      if (null != view) {
        view.setBackgroundColor(color);
      }
    }
  }

  private static void processPrivateAPI(Window window, boolean lightStatusBar) {
    try {
      processFlyMe(window, lightStatusBar);
    } catch (Exception e) {
      try {
        processMIUI(window, lightStatusBar);
      } catch (Exception e2) {
        //
      }
    }
  }

  /**
   * 改变魅族的状态栏字体为黑色，要求 FlyMe 4 以上
   *
   * @param window
   * @param isLightStatusBar
   * @throws Exception
   */
  private static void processFlyMe(Window window, boolean isLightStatusBar) throws Exception {
    WindowManager.LayoutParams lp = window.getAttributes();
    Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
    int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
    Field field = instance.getDeclaredField("meizuFlags");
    field.setAccessible(true);
    int origin = field.getInt(lp);
    if (isLightStatusBar) {
      field.set(lp, origin | value);
    } else {
      field.set(lp, (~value) & origin);
    }
  }

  /**
   * 改变小米的状态栏字体颜色为黑色, 要求 MIUI 6 以上
   * Tested on: MIUI V7 5.0 Redmi-Note3
   *
   * @param window
   * @param lightStatusBar
   * @throws Exception
   */
  private static void processMIUI(Window window, boolean lightStatusBar) throws Exception {
    Class<? extends Window> clazz = window.getClass();
    int darkModeFlag;
    Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
    darkModeFlag = field.getInt(layoutParams);
    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
    extraFlagField.invoke(window, lightStatusBar ? darkModeFlag : 0, darkModeFlag);
  }
}
