package com.gdgs.cqi.contract;

import com.gdgs.cqi.model.Result;
import com.gdgs.cqi.presenter.RXMVPPresenter;
import com.hayukleung.mvp.lce.LCEView;

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
