package com.hayukleung.mvp.lce;

import com.hayukleung.mvp.MVPPresenter;

/**
 * XGitHub
 * com.hayukleung.mvp
 * LCEPresenter.java
 *
 * by hayukleung
 * at 2017-04-02 14:55
 */

public class LCEPresenter<V extends LCEView> implements MVPPresenter<V> {

  private V mMVPView;

  @Override public void attachView(V view) {
    mMVPView = view;
  }

  @Override public void detachView() {
    mMVPView = null;
  }

  public V getMVPView() {
    return mMVPView;
  }

  public void checkViewAttached() {
    if (!isViewAttached()) throw new MVPViewNotAttachedException();
  }

  public boolean isViewAttached() {
    return mMVPView != null;
  }

  public static class MVPViewNotAttachedException extends RuntimeException {

    public MVPViewNotAttachedException() {
      super("Please call Presenter.attachView(MVPView) before requesting data to the Presenter");
    }
  }
}
