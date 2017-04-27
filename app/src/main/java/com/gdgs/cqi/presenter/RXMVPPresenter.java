package com.gdgs.cqi.presenter;

import com.hayukleung.mvp.lce.LCEPresenter;
import com.hayukleung.mvp.lce.LCEView;

/**
 * XGitHub
 * com.hayukleung.xgithub.presenter
 * RXMVPPresenter.java
 *
 * by hayukleung
 * at 2017-04-02 15:06
 */

public class RXMVPPresenter<V extends LCEView> extends LCEPresenter<V> {

  // protected CompositeDisposable mCompositeDisposable;

  @Override public void attachView(V view) {
    super.attachView(view);
    // mCompositeDisposable = new CompositeDisposable();
  }

  @Override public void detachView() {
    super.detachView();
    // mCompositeDisposable.clear();
    // mCompositeDisposable = null;
  }
}
