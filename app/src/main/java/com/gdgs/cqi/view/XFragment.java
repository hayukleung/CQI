package com.gdgs.cqi.view;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gdgs.cqi.PausedHandler;
import com.gdgs.cqi.R;
import com.gdgs.cqi.model.BaseBean;
import com.gdgs.cqi.presenter.RXMVPPresenter;
import com.hayukleung.mvp.lce.LCEView;
import java.lang.ref.WeakReference;

public abstract class XFragment<M extends BaseBean, P extends RXMVPPresenter> extends BaseFragment
    implements LCEView<M, P> {

  private SystemBarConfig mSystemBarConfig;

  private FrameLayout mLayoutRoot;

  private Toolbar mToolbar;
  private View mStatusBar;
  private FrameLayout mLayoutContent;
  private RelativeLayout mLayoutEmpty;
  private RelativeLayout mLayoutError;
  private RelativeLayout mLayoutLoading;

  private PausedHandler mHandler;

  private Unbinder mUnbinder;

  public XFragment() {
    mHandler = new XHandler(this);
    mHandler.pause();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    mHandler.resume();
  }

  @Override public void onPause() {
    mHandler.pause();
    super.onPause();
  }

  @Override public void onDestroyView() {
    mStatusBar = null;
    mLayoutContent = null;
    mLayoutEmpty = null;
    mLayoutError = null;
    mLayoutLoading = null;
    mUnbinder.unbind();
    super.onDestroyView();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View baseView = inflater.inflate(R.layout.fragment_x, container, false);
    mLayoutRoot = (FrameLayout) baseView.findViewById(R.id.layout_root);

    mLayoutContent = (FrameLayout) baseView.findViewById(R.id.layout_content);
    mLayoutContent.removeAllViews();
    mLayoutContent.addView(inflater.inflate(getContentView(), null),
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    mStatusBar = inflater.inflate(R.layout.layout_status_bar, null);
    mLayoutContent.addView(mStatusBar,
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));

    mLayoutEmpty = (RelativeLayout) baseView.findViewById(R.id.layout_empty_root);
    mLayoutEmpty.setVisibility(View.GONE);

    mLayoutError = (RelativeLayout) baseView.findViewById(R.id.layout_error_root);
    mLayoutError.findViewById(R.id.retry_btn).setOnClickListener(getRetryListener());
    mLayoutError.setVisibility(View.GONE);

    mLayoutLoading = (RelativeLayout) baseView.findViewById(R.id.layout_loading_root);
    mLayoutLoading.setVisibility(View.GONE);

    mUnbinder = ButterKnife.bind(this, mLayoutContent);

    mToolbar = (Toolbar) baseView.findViewById(R.id.toolbar);
    mToolbar.setVisibility(View.GONE);
    // AppCompatActivity activity = (AppCompatActivity) getActivity();
    // activity.setSupportActionBar(mToolbar);

    return baseView;
  }

  abstract protected int getContentView();

  abstract protected View.OnClickListener getRetryListener();

  protected View getStatusBar() {
    return this.mStatusBar;
  }

  protected Toolbar getToolbar() {
    return this.mToolbar;
  }

  protected FrameLayout getLayoutContent() {
    return this.mLayoutContent;
  }

  @Override public void showLoading() {
    mLayoutContent.setVisibility(View.GONE);
    mLayoutEmpty.setVisibility(View.GONE);
    mLayoutError.setVisibility(View.GONE);
    mLayoutLoading.setVisibility(View.VISIBLE);
  }

  @Override public void dismissLoading() {
    mLayoutLoading.setVisibility(View.GONE);
  }

  @CallSuper @Override public void showContent(M data) {
    dismissLoading();
    mLayoutEmpty.setVisibility(View.GONE);
    mLayoutError.setVisibility(View.GONE);
    mLayoutContent.setVisibility(View.VISIBLE);
  }

  @Override public void showError(Throwable e) {
    dismissLoading();
    mLayoutContent.setVisibility(View.GONE);
    mLayoutEmpty.setVisibility(View.GONE);
    mLayoutError.setVisibility(View.VISIBLE);
  }

  @Override public void showEmpty() {
    dismissLoading();
    mLayoutContent.setVisibility(View.GONE);
    mLayoutError.setVisibility(View.GONE);
    mLayoutEmpty.setVisibility(View.VISIBLE);
  }

  protected SystemBarConfig getSystemBarConfig() {
    if (null == mSystemBarConfig) {
      mSystemBarConfig = new SystemBarConfig(getActivity());
    }
    return this.mSystemBarConfig;
  }

  /**
   * @see PausedHandler#storeMessage(Message)
   */
  protected boolean storeMessage(Message message) {
    return true;
  }

  /**
   * @see PausedHandler#processMessage(Message)
   */
  protected void processMessage(Message message) {

  }

  public PausedHandler getHandler() {
    return mHandler;
  }

  private static class XHandler extends PausedHandler {

    private WeakReference<XFragment> mFragment;

    public XHandler(XFragment fragment) {
      mFragment = new WeakReference<>(fragment);
    }

    @Override protected boolean storeMessage(Message message) {
      XFragment fragment = mFragment.get();
      if (fragment == null) {
        return false;
      } else {
        return fragment.storeMessage(message);
      }
    }

    @Override protected void processMessage(Message message) {
      XFragment fragment = mFragment.get();
      if (fragment != null) {
        fragment.processMessage(message);
      }
    }
  }
}
