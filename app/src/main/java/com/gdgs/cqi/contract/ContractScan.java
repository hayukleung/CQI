package com.gdgs.cqi.contract;

import com.gdgs.cqi.model.GitHub;
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

public interface ContractScan {

  public interface IViewScan extends LCEView<GitHub, IPresenterScan> {

  }

  public abstract class IPresenterScan extends RXMVPPresenter<IViewScan> {

  }
}
