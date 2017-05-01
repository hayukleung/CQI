package com.xfzbd.cqi.view.result;

import com.xfzbd.cqi.contract.ContractResult;

/**
 * XGitHub
 * com.hayukleung.xgithub.view
 * MainView.java
 *
 * by hayukleung
 * at 2017-04-02 17:12
 */

public interface ResultView extends ContractResult.IViewResult {

  void showQueryLoading();

  void stopQueryLoading();
}
