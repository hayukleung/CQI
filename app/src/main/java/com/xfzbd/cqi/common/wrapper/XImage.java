package com.xfzbd.cqi.common.wrapper;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * XGitHub
 * com.hayukleung.xgithub.common.wrapper
 * XImage.java
 *
 * by hayukleung
 * at 2017-03-31 15:56
 */

public class XImage {

  /**
   * @param context
   * @param source may be Uri, String, File, ResourceId
   * @param imageView
   */
  public static void load(Context context, Object source, ImageView imageView) {
    Glide.with(context).load(source).centerCrop().into(imageView);
  }
}
