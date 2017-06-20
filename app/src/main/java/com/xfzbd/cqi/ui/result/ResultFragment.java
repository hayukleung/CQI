package com.xfzbd.cqi.ui.result;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.CommonUtils;
import com.xfzbd.cqi.common.wrapper.XLog;
import com.xfzbd.cqi.contract.ContractResult;
import com.xfzbd.cqi.database.Category;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.di.HasComponent;
import com.xfzbd.cqi.di.component.ComponentResult;
import com.xfzbd.cqi.di.component.DaggerComponentResult;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.model.Result;
import com.xfzbd.cqi.presenter.PresenterResult;
import com.xfzbd.cqi.ui.UIUtils;
import com.xfzbd.cqi.ui.XFragment;
import com.xfzbd.cqi.widget.FilterLayout;
import com.xfzbd.cqi.widget.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

/**
 * 搜索主页
 */
public class ResultFragment extends XFragment<Result, ContractResult.IPresenterResult>
    implements ResultView, HasComponent<ComponentResult>, FilterInterface {

  public int mIntCategory = 0;
  public int mIntCategoryNew = 0;
  @Inject protected PresenterResult mPresenterResult;
  @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbarLayout;
  @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
  // @BindView(R.id.toolbar_result) Toolbar mToolbarResult;
  @BindView(R.id.keywords) EditText mKeywords;
  @BindView(R.id.flow_layout_keywords) FlowLayout mFlowLayoutKeywords;
  @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.filter_layout) FilterLayout mFilterLayout;
  private boolean mUseDiffUtil = false;
  private String mStrKeyword = "";
  private List<Product> mProductList = new ArrayList<>();
  private List<Product> mProductListNew = new ArrayList<>();
  private long mBackTime = 0L;

  public String getStrKeyword() {
    return mStrKeyword;
  }

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
    onFilterSure(false);
  }

  @Override protected int getContentView() {
    return R.layout.content_result;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override protected Toolbar getToolbar() {
    // return mToolbarResult;
    return super.getToolbar();
  }

  @Override public void showContent(Result data) {
    super.showContent(data);

    if (mUseDiffUtil) {
      mProductListNew.clear();
      mProductListNew.addAll(data.getProductList());

      DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
          new DiffUtilCallback(mIntCategory, mIntCategoryNew, mProductList, mProductListNew),
          false);

      diffResult.dispatchUpdatesTo(mRecyclerView.getAdapter());

      mProductList.clear();
      mProductList.addAll(mProductListNew);
    } else {
      mProductList.clear();
      mProductList.addAll(data.getProductList());

      mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    mFilterLayout.setName(String.format(Locale.CHINA, "%s（%d）", Category.CATEGORY.get(mIntCategory),
        data.getProductList().size()));
  }

  @Override public void onFilterSure(boolean useDiffUtil) {
    mUseDiffUtil = useDiffUtil;
    mPresenterResult.query(getActivity(), mStrKeyword, mIntCategory,
        bindUntilEvent(FragmentEvent.PAUSE));
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
        .setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));

    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (0 == verticalOffset) {
          getToolbar().setVisibility(View.GONE);
        } else if (getContext().getResources().getDimensionPixelOffset(R.dimen.xp10_0)
            <= -verticalOffset) {
          // getToolbar().setVisibility(View.VISIBLE);
          getToolbar().setVisibility(View.GONE);
        }
        XLog.e("offset --> " + verticalOffset);
      }
    });

    mKeywords.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {

        mStrKeyword = s.toString();
        onFilterSure(false);
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

    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        onFilterSure(false);
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
    long time = SystemClock.elapsedRealtime();
    if (time - mBackTime > 2000) {
      Toast.makeText(getActivity(), "再按一次返回退出", Toast.LENGTH_SHORT).show();
      mBackTime = time;
      return true;
    }
    return super.onBackPressed();
  }

  @Override public void showQueryLoading() {
    if (mSwipeRefreshLayout.isRefreshing()) {
      mSwipeRefreshLayout.setRefreshing(false);
    }
    mSwipeRefreshLayout.setRefreshing(true);
  }

  @Override public void stopQueryLoading() {
    mSwipeRefreshLayout.setRefreshing(false);
  }

  // @OnTouch({ R.id.keywords }) public boolean onViewTouch() {
  // CommonUtils.showInputMethod(getActivity(), mKeywords);
  // return true;
  // }
}
