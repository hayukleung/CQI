package com.hayukleung.mvp;

import android.support.annotation.UiThread;

/**
 * XGitHub
 * com.hayukleung.mvp
 * MVPPresenter.java
 *
 * by hayukleung
 * at 2017-04-02 14:52
 */

public interface MVPPresenter<V extends MVPView> {
  /**
   * Set or attach the view to this presenter
   */
  @UiThread void attachView(V view);

  /**
   * Will be called if the view has been destroyed. Typically this method will be invoked from
   * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
   */
  @UiThread void detachView();
}
