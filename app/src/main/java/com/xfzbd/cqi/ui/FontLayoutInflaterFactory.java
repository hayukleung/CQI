package com.xfzbd.cqi.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 字体
 */
public class FontLayoutInflaterFactory implements LayoutInflaterFactory {

  private static Typeface sTypeface;
  private AppCompatDelegate mAppCompatDelegate;

  public FontLayoutInflaterFactory(Context context, AppCompatDelegate appCompatDelegate) {
    if (null == sTypeface) {
      sTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/FZHTK.TTF");
    }
    mAppCompatDelegate = appCompatDelegate;
  }

  @Override
  public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
    View view = mAppCompatDelegate.createView(parent, name, context, attrs);
    if (view instanceof TextView) {
      ((TextView) view).setTypeface(sTypeface);
    }
    return view;
  }
}
