package com.xfzbd.cqi.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.CommonUtils;
import com.xfzbd.cqi.contract.ContractDetail;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.di.HasComponent;
import com.xfzbd.cqi.di.component.ComponentDetail;
import com.xfzbd.cqi.di.component.DaggerComponentDetail;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.presenter.PresenterDetail;
import com.xfzbd.cqi.ui.UIUtils;
import com.xfzbd.cqi.ui.XFragment;
import javax.inject.Inject;

/**
 * CQI
 * com.gdgs.cqi.view.detail
 * DetailFragment.java
 *
 * by hayukleung
 * at 2017-04-27 19:01
 */

public class DetailFragment extends XFragment<Product, ContractDetail.IPresenterDetail>
    implements DetailView, HasComponent<ComponentDetail> {

  @Inject protected PresenterDetail mPresenterDetail;

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UIUtils.requestStatusBarLight(this, true);
    getComponent().inject(this);
    mPresenterDetail.attachView(this);
  }

  @Override public ComponentDetail getComponent() {
    return DaggerComponentDetail.builder()
        .appComponent(getBaseActivity().getAppComponent())
        .activityModule(new ActivityModule(getBaseActivity()))
        .build();
  }

  @Override protected int getContentView() {
    return R.layout.content_detail;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar = getToolbar();
    toolbar.setVisibility(View.VISIBLE);
    toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_black);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    UIUtils.setCenterTitle(getActivity(), toolbar, "商品详情")
        .setTextColor(getActivity().getResources().getColor(android.R.color.black));

    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setAdapter(new DetailAdapter(getActivity(),
        (Product) getArguments().getSerializable(Product.class.getSimpleName())));

    mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()
            || MotionEvent.ACTION_MOVE == event.getAction()) {
          CommonUtils.hideInputMethod(getActivity(), getActivity().getCurrentFocus());
        }
        return false;
      }
    });
  }

  @Override public void onDestroy() {
    mPresenterDetail.detachView();
    super.onDestroy();
  }

  @OnClick({ R.id.save }) void onClick() {
    mPresenterDetail.save(getActivity(), ((DetailAdapter) mRecyclerView.getAdapter()).getProduct(),
        mRecyclerView);
    CommonUtils.hideInputMethod(getActivity(), getActivity().getCurrentFocus());
  }
}
