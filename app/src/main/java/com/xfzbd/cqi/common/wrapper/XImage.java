package com.xfzbd.cqi.common.wrapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xfzbd.cqi.common.Constants;

/**
 * Glide 方法封装
 */
public class XImage {

  /**
   *
   * @param context
   * @param source
   * @param imageView
   * @param placeHolder
   * @param transformations
   */
  public static void load(Context context, Object source, ImageView imageView,
      @DrawableRes int placeHolder,
      Transformation<Bitmap>... transformations) {
    load(context, source, imageView, placeHolder, new RequestListener<Object, GlideDrawable>() {
      @Override public boolean onException(Exception e, Object model, Target<GlideDrawable> target,
          boolean isFirstResource) {
        return false;
      }

      @Override public boolean onResourceReady(GlideDrawable resource, Object model,
          Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
      }
    }, transformations);
  }

  /**
   * @param context
   * @param source may be Uri, String, File, ResourceId
   * @param imageView
   * @param placeHolder
   * @param requestListener
   * @param transformations Crop - CropTransformation, CropCircleTransformation,
   * CropSquareTransformation, RoundedCornersTransformation
   * Color - ColorFilterTransformation, GrayscaleTransformation
   * Blur - BlurTransformation
   * Mask - MaskTransformation
   */
  public static void load(Context context, Object source, final ImageView imageView,
      @DrawableRes int placeHolder,
      RequestListener<Object, GlideDrawable> requestListener,
      Transformation<Bitmap>... transformations) {
    Glide.with(context)
        .load(source).bitmapTransform(transformations).placeholder(placeHolder)
        .listener(requestListener)
        .into(imageView);
  }

  /**
   * @param context
   * @param source
   * @param imageView
   * @param placeHolder
   * @param requestListener
   */
  public static void load(Context context, Object source, ImageView imageView,
      @DrawableRes int placeHolder,
      RequestListener<Object, GlideDrawable> requestListener) {
    load(context, source, imageView, placeHolder, requestListener, new CenterCrop(context));
  }

  /**
   * @param context
   * @param source
   * @param imageView
   * @param placeHolder
   */
  public static void load(Context context, Object source, ImageView imageView,
      @DrawableRes int placeHolder) {
    load(context, source, imageView, placeHolder, new RequestListener<Object, GlideDrawable>() {
      @Override public boolean onException(Exception e, Object model, Target<GlideDrawable> target,
          boolean isFirstResource) {
        return false;
      }

      @Override public boolean onResourceReady(GlideDrawable resource, Object model,
          Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
      }
    }, new CenterCrop(context));
  }

  /**
   * 生成图片链接地址
   *
   * @param suffix
   * @return
   */
  public static String url0(String suffix) {
    return String.format("%s/%s.jpg", Constants.HOST_IMAGE, suffix);
  }

  public static String url1(String suffix) {
    return String.format("%s/%s-001.jpg", Constants.HOST_IMAGE, suffix);
  }

  public static String url2(String suffix) {
    return String.format("%s/%s-002.jpg", Constants.HOST_IMAGE, suffix);
  }

  public static String url3(String suffix) {
    return String.format("%s/%s-003.jpg", Constants.HOST_IMAGE, suffix);
  }
}
