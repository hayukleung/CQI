package com.xfzbd.cqi.ui.photo;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.ui.SystemUiHider;
import com.xfzbd.cqi.ui.XFragment;
import com.xfzbd.cqi.widget.RoundProgressBar;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 单张图片展示页面
 */
public class PictureFragment extends XFragment {

  @BindView(R.id.animation) PhotoView photoView;
  @BindView(R.id.back_animation) PhotoView backView;
  @BindView(R.id.loading) RoundProgressBar spinner;
  private SystemUiHider systemUiHider;
  private String lUrl;

  /**
   * @param sKey small image key from image cache.
   * @param lUrl large image url
   * @param rect small image position
   * @param animationIn run animation
   * @param isPortrait 是否为头像
   */
  public static PictureFragment newInstance(String sKey, String lUrl, Rect rect,
      boolean animationIn, boolean isPortrait, SystemUiHider mSystemUiHider) {
    PictureFragment fragment = new PictureFragment();
    fragment.setSystemUiHider(mSystemUiHider);
    Bundle bundle = new Bundle();
    bundle.putString("s_key", sKey);
    bundle.putString("l_url", lUrl);
    bundle.putParcelable("rect", rect);
    bundle.putBoolean("animationIn", animationIn);
    bundle.putBoolean("isPortrait", isPortrait);
    fragment.setArguments(bundle);
    return fragment;
  }

  public void setSystemUiHider(SystemUiHider systemUiHider) {
    this.systemUiHider = systemUiHider;
  }

  /**
   * @param isPortrait 是否为头像
   */
  public static PictureFragment newInstance(String sKey, String lUrl, String sUrl, Rect rect,
      boolean animationIn, boolean isPortrait, SystemUiHider mSystemUiHider) {
    PictureFragment fragment = new PictureFragment();
    fragment.setSystemUiHider(mSystemUiHider);
    Bundle bundle = new Bundle();
    bundle.putString("s_key", sKey);
    bundle.putString("l_url", lUrl);
    bundle.putString("s_url", sUrl);
    bundle.putParcelable("rect", rect);
    bundle.putBoolean("animationIn", animationIn);
    bundle.putBoolean("isPortrait", isPortrait);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override protected int getContentView() {
    return R.layout.content_gallery_general_layout;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  // @Override public void showWriteStorage() {
  // if (!TextUtils.isEmpty(lUrl)) {
  // SettingDialogs.savePictureDialog(getActivity(), lUrl);
  // }
  // }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    spinner.setVisibility(View.VISIBLE);
    spinner.setMax(100);
    spinner.setProgress(0);
    photoView.setScaleType(ImageView.ScaleType.CENTER);
    photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
      @Override public void onViewTap(View view, float x, float y) {
        if (systemUiHider != null) {
          systemUiHider.toggle();
        }
      }
    });
    Bundle bundle = getArguments();
    final String sKey = bundle.getString("s_key");
    final String lUrl = bundle.getString("l_url");
    final String sUrl = bundle.getString("s_url");
    final Rect rect = bundle.getParcelable("rect");
    boolean animateIn = bundle.getBoolean("animationIn");
    boolean isPortrait = bundle.getBoolean("isPortrait");
    this.lUrl = lUrl;

    if (TextUtils.isEmpty(lUrl)) {
      spinner.setVisibility(View.INVISIBLE);
    } else {
      spinner.setVisibility(View.VISIBLE);
    }

    Glide.with(this)
        .load(lUrl)
        .asBitmap()
        .fitCenter()
        .listener(new RequestListener<String, Bitmap>() {
          @Override public boolean onException(Exception e, String model, Target<Bitmap> target,
              boolean isFirstResource) {
            if (spinner != null && getActivity() != null) {
              spinner.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
              spinner.setVisibility(View.INVISIBLE);
              Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
            }
            return false;
          }

          @Override
          public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target,
              boolean isFromMemoryCache, boolean isFirstResource) {
            if (spinner != null && getActivity() != null) {
              spinner.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
              spinner.setVisibility(View.INVISIBLE);
            }
            backView.setVisibility(View.INVISIBLE);
            return false;
          }
        })
        .into(new ProgressTarget<String, Bitmap>(lUrl, new BitmapImageViewTarget(photoView)) {
          @Override protected void onConnecting() {
          }

          @Override protected void onDownloaded() {
          }

          @Override protected void onDownloading(long bytesRead, long expectedLength) {
            int progress = (int) (((double) bytesRead / (double) expectedLength) * 100);
            if (spinner != null && getActivity() != null) {
              spinner.setProgress(progress);
            }
          }

          @Override protected void onDelivered() {
          }
        });

    if (sUrl != null) {
      Glide.with(getActivity()).load(sUrl).into(backView);
    }

    photoView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View view) {
        // 相当于调用这一句,只是加了权限判断 SettingDialogs.savePictureDialog(getActivity(), lUrl);
        // showWriteStorageWithCheck();
        return true;
      }
    });
  }

  @Override public void showContent(Object data) {

  }
}
