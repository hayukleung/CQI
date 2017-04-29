package com.xfzbd.cqi.contract;

import com.hayukleung.mvp.lce.LCEView;
import com.xfzbd.cqi.model.GitHub;
import com.xfzbd.cqi.presenter.RXMVPPresenter;

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
