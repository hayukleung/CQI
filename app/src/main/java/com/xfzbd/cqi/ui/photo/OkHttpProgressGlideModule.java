package com.xfzbd.cqi.ui.photo;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import java.io.InputStream;
import okhttp3.HttpUrl;

import static com.xfzbd.cqi.ui.photo.HttpClient.getDefaultHttpClient;

public class OkHttpProgressGlideModule implements GlideModule {

  public static void forget(String url) {
    DispatchingProgressListener.forget(url);
  }

  public static void expect(String url, UIProgressListener listener) {
    DispatchingProgressListener.expect(url, listener);
  }

  @Override public void applyOptions(Context context, GlideBuilder builder) {
  }

  @Override public void registerComponents(Context context, Glide glide) {
    glide.register(GlideUrl.class, InputStream.class,
        new OkHttpUrlLoader.Factory(getDefaultHttpClient()));
  }

  public interface UIProgressListener {
    void onProgress(long bytesRead, long expectedLength);

    /**
     * Control how often the listener needs an update. 0% and 100% will always be dispatched.
     *
     * @return in percentage (0.2 = call {@link #onProgress} around every 0.2 percent of progress)
     */
    float getGranualityPercentage();

    void saveProgress(long progress);

    Long getProgress();
  }

  public interface ResponseProgressListener {
    void update(HttpUrl url, long bytesRead, long contentLength);
  }
}