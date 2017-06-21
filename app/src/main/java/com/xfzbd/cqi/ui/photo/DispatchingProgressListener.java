package com.xfzbd.cqi.ui.photo;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import okhttp3.HttpUrl;

class DispatchingProgressListener implements OkHttpProgressGlideModule.ResponseProgressListener {
  private static final Map<String, Set<OkHttpProgressGlideModule.UIProgressListener>> LISTENERS =
      new HashMap<>();

  private final Handler mHandler;

  DispatchingProgressListener() {
    this.mHandler = new Handler(Looper.getMainLooper());
  }

  static void expect(String url, OkHttpProgressGlideModule.UIProgressListener listener) {
    Set<OkHttpProgressGlideModule.UIProgressListener> listeners;
    if (LISTENERS.containsKey(url)) {
      listeners = LISTENERS.get(url);
    } else {
      listeners = new HashSet<>();
      LISTENERS.put(url, listeners);
    }
    listeners.add(listener);
  }

  @Override public void update(HttpUrl url, final long bytesRead, final long contentLength) {
    String key = url.toString();
    final Set<OkHttpProgressGlideModule.UIProgressListener> listeners = LISTENERS.get(key);
    if (listeners == null) {
      return;
    }
    if (contentLength <= bytesRead) {
      forget(key);
    }
    for (final OkHttpProgressGlideModule.UIProgressListener listener : listeners) {
      if (needsDispatch(key, bytesRead, contentLength, listener)) {
        mHandler.post(new Runnable() {
          @Override public void run() {
            listener.onProgress(bytesRead, contentLength);
          }
        });
      }
    }
  }

  static void forget(String url) {
    LISTENERS.remove(url);
  }

  private boolean needsDispatch(String key, long current, long total,
      OkHttpProgressGlideModule.UIProgressListener listener) {
    float granularity = listener.getGranualityPercentage();
    if (granularity == 0 || current == 0 || total == current) {
      return true;
    }
    float percent = 100f * current / total;
    long currentProgress = (long) (percent / granularity);
    Long lastProgress = listener.getProgress();
    if (lastProgress == null || currentProgress != lastProgress) {
      listener.saveProgress(currentProgress);
      return true;
    } else {
      return false;
    }
  }
}