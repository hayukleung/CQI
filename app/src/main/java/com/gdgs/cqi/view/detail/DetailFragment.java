package com.gdgs.cqi.view.detail;

import android.view.View;
import com.gdgs.cqi.R;
import com.gdgs.cqi.contract.ContractDetail;
import com.gdgs.cqi.di.HasComponent;
import com.gdgs.cqi.di.component.ComponentDetail;
import com.gdgs.cqi.model.Stub;
import com.gdgs.cqi.view.XFragment;

/**
 * CQI
 * com.gdgs.cqi.view.detail
 * DetailFragment.java
 *
 * by hayukleung
 * at 2017-04-27 19:01
 */

public class DetailFragment extends XFragment<Stub, ContractDetail.IPresenterDetail>
    implements DetailView, HasComponent<ComponentDetail> {

  @Override public ComponentDetail getComponent() {
    return null;
  }

  @Override protected int getContentView() {
    return R.layout.content_detail;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }
}
