package com.xfzbd.cqi.contract;

import com.hayukleung.mvp.lce.LCEView;
import com.xfzbd.cqi.model.Result;
import com.xfzbd.cqi.presenter.RXMVPPresenter;

/**
 * XGitHub
 * com.hayukleung.xgithub.contract
 * ContractMain.java
 *
 * by hayukleung
 * at 2017-04-06 11:30
 */

public interface ContractResult {

  public interface IViewResult extends LCEView<Result, IPresenterResult> {

  }

  public abstract class IPresenterResult extends RXMVPPresenter<IViewResult> {
  }
}
