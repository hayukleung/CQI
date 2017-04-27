package com.gdgs.cqi.view.result;

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
import com.gdgs.cqi.contract.ContractResult;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.di.HasComponent;
import com.gdgs.cqi.di.component.ComponentResult;
import com.gdgs.cqi.di.component.DaggerComponentResult;
import com.gdgs.cqi.di.module.ActivityModule;
import com.gdgs.cqi.model.Result;
import com.gdgs.cqi.presenter.PresenterResult;
import com.gdgs.cqi.view.UIUtils;
import com.gdgs.cqi.view.XFragment;
import com.trello.rxlifecycle2.android.FragmentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * CQI
 * com.gdgs.cqi.view.result
 * ResultFragment.java
 *
 * by hayukleung
 * at 2017-04-27 13:01
 */

public class ResultFragment extends XFragment<Result, ContractResult.IPresenterResult>
    implements ResultView, HasComponent<ComponentResult> {

  @Inject protected PresenterResult mPresenterResult;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  private List<Product> mProductList = new ArrayList<>();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    UIUtils.requestStatusBarLight(this, true);
    getComponent().inject(this);
    mPresenterResult.attachView(this);
  }

  @Override public ComponentResult getComponent() {
    return DaggerComponentResult.builder()
        .appComponent(getBaseActivity().getAppComponent())
        .activityModule(new ActivityModule(getBaseActivity()))
        .build();
  }

  @Override protected int getContentView() {
    return R.layout.content_result;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void showContent(Result data) {
    super.showContent(data);
    mProductList.clear();
    mProductList.addAll(data.getProductList());
    mRecyclerView.getAdapter().notifyDataSetChanged();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar = getToolbar();
    toolbar.setBackgroundColor(Color.WHITE);
    toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_black);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    UIUtils.setCenterTitle(getActivity(), toolbar, "搜索结果")
        .setTextColor(getActivity().getResources().getColor(android.R.color.black));

    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setAdapter(new ResultAdapter(mProductList));
  }

  @Override public void onDestroy() {
    mPresenterResult.detachView();
    super.onDestroy();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mPresenterResult.query(getActivity(), getArguments().getString("key"),
        getArguments().getInt("category"), bindUntilEvent(FragmentEvent.PAUSE));
  }
}
