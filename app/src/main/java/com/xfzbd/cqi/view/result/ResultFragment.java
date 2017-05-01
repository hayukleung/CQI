package com.xfzbd.cqi.view.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.CommonUtils;
import com.xfzbd.cqi.contract.ContractResult;
import com.xfzbd.cqi.database.Category;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.di.HasComponent;
import com.xfzbd.cqi.di.component.ComponentResult;
import com.xfzbd.cqi.di.component.DaggerComponentResult;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.model.Result;
import com.xfzbd.cqi.presenter.PresenterResult;
import com.xfzbd.cqi.view.UIUtils;
import com.xfzbd.cqi.view.XFragment;
import com.xfzbd.cqi.widget.FilterLayout;
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
    implements ResultView, HasComponent<ComponentResult>, FilterInterface {

  public int mIntCategory = 0;
  @Inject protected PresenterResult mPresenterResult;
  @BindView(R.id.keywords) EditText mKeywords;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.filter_layout) FilterLayout mFilterLayout;
  private String mStrKeyword = "";

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

  @Override public void onResume() {
    super.onResume();
    onFilterSure();
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

  @Override public void onFilterSure() {
    mPresenterResult.query(getActivity(), mStrKeyword, mIntCategory,
        bindUntilEvent(FragmentEvent.PAUSE));
    mFilterLayout.setName(Category.CATEGORY.get(mIntCategory));
    mFilterLayout.dismissCurrentFilter();
  }

  @Override public void onFilterCancel() {
    mFilterLayout.dismissCurrentFilter();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Toolbar toolbar = getToolbar();
    toolbar.setVisibility(View.GONE);
    // toolbar.setBackgroundColor(Color.WHITE);
    // toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_black);
    // toolbar.setNavigationOnClickListener(new View.OnClickListener() {
    // @Override public void onClick(View v) {
    // getActivity().onBackPressed();
    // }
    // });

    UIUtils.setCenterTitle(getActivity(), toolbar, "搜索结果")
        .setTextColor(getActivity().getResources().getColor(android.R.color.black));

    mKeywords.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {

        mStrKeyword = s.toString();
        onFilterSure();
      }
    });

    mFilterLayout.setOnSelectedListener(new FilterLayout.OnSelectedListener() {
      @Override public void onSelected(String tag) {
        // TODO
      }
    });
    mFilterLayout.init(getChildFragmentManager());
    mFilterLayout.setVisibility(FilterLayout.TAG, View.VISIBLE);

    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    mRecyclerView.setAdapter(new ResultAdapter(this, mProductList));
    mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()
            || MotionEvent.ACTION_MOVE == event.getAction()) {
          View focus = getActivity().getCurrentFocus();
          if (null != focus) {
            CommonUtils.hideInputMethod(getActivity(), getActivity().getCurrentFocus());
          }
        }
        return false;
      }
    });
  }

  @Override public void onDestroy() {
    mPresenterResult.detachView();
    super.onDestroy();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public boolean onBackPressed() {
    if (mFilterLayout.dismissCurrentFilter()) {
      return true;
    }
    return super.onBackPressed();
  }

  @OnClick({ R.id.search }) void onClick() {
    onFilterSure();
  }
}
