package com.gdgs.cqi.presenter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.gdgs.cqi.common.wrapper.XDao;
import com.gdgs.cqi.contract.ContractDetail;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.database.ProductDao;
import javax.inject.Inject;

/**
 * XGitHub
 * com.hayukleung.xgithub.presenter.main
 * PresenterMain.java
 *
 * by hayukleung
 * at 2017-04-02 17:03
 */

public class PresenterDetail extends ContractDetail.IPresenterDetail {

  @Inject public PresenterDetail() {
  }

  public void save(Context context, Product product, View view) {
    XDao xDao = XDao.getInstance(context.getApplicationContext());
    ProductDao productDao = xDao.getDaoSession().getProductDao();
    productDao.update(product);
    Snackbar.make(view, "保存成功", Snackbar.LENGTH_SHORT).show();
  }
}
