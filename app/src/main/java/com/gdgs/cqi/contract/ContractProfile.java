package com.gdgs.cqi.contract;

import com.hayukleung.mvp.lce.LCEView;
import com.gdgs.cqi.model.GitHub;
import com.gdgs.cqi.presenter.RXMVPPresenter;

/**
 * XGitHub
 * com.hayukleung.xgithub.contract
 * ContractMain.java
 *
 * by hayukleung
 * at 2017-04-06 11:30
 */

public interface ContractProfile {

  public interface IViewProfile extends LCEView<GitHub, IPresenterProfile> {

  }

  public abstract class IPresenterProfile extends RXMVPPresenter<IViewProfile> {

  }
}
