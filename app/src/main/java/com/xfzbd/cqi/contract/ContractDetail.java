package com.xfzbd.cqi.contract;

import com.hayukleung.mvp.lce.LCEView;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.presenter.RXMVPPresenter;

/**
 * XGitHub
 * com.hayukleung.xgithub.contract
 * ContractMain.java
 *
 * by hayukleung
 * at 2017-04-06 11:30
 */

public interface ContractDetail {

  public interface IViewDetail extends LCEView<Product, IPresenterDetail> {

  }

  public abstract class IPresenterDetail extends RXMVPPresenter<IViewDetail> {

  }
}
