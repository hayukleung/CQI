package com.gdgs.cqi.view.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.gdgs.cqi.R;
import com.gdgs.cqi.contract.ContractDetail;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.di.HasComponent;
import com.gdgs.cqi.di.component.ComponentDetail;
import com.gdgs.cqi.di.component.DaggerComponentDetail;
import com.gdgs.cqi.di.module.ActivityModule;
import com.gdgs.cqi.presenter.PresenterDetail;
import com.gdgs.cqi.view.UIUtils;
import com.gdgs.cqi.view.XFragment;
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
    toolbar.setBackgroundColor(Color.WHITE);
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
    mRecyclerView.setAdapter(
        new DetailAdapter((Product) getArguments().getSerializable(Product.class.getSimpleName())));
  }

  @Override public void onDestroy() {
    mPresenterDetail.detachView();
    super.onDestroy();
  }
}
