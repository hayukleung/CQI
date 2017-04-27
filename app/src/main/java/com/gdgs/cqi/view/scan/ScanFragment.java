package com.gdgs.cqi.view.scan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.OnClick;
import com.gdgs.cqi.R;
import com.gdgs.cqi.common.wrapper.XLog;
import com.gdgs.cqi.contract.ContractScan;
import com.gdgs.cqi.di.HasComponent;
import com.gdgs.cqi.di.component.ComponentScan;
import com.gdgs.cqi.di.component.DaggerComponentScan;
import com.gdgs.cqi.di.module.ActivityModule;
import com.gdgs.cqi.model.GitHub;
import com.gdgs.cqi.presenter.PresenterScan;
import com.gdgs.cqi.view.TranslucentStatusCompat;
import com.gdgs.cqi.view.UIUtils;
import com.gdgs.cqi.view.XFragment;
import com.gdgs.cqi.widget.scan.ScannerView;
import com.zbar.Result;
import com.zbar.ZBarScannerView;
import java.lang.ref.WeakReference;
import javax.inject.Inject;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.profile
 * ProfileFragment.java
 *
 * by hayukleung
 * at 2017-04-03 20:53
 */

public class ScanFragment extends XFragment<GitHub, ContractScan.IPresenterScan>
    implements ScanView, HasComponent<ComponentScan>, ZBarScannerView.ResultHandler {

  @Inject protected PresenterScan mPresenterScan;
  @BindView(R.id.scanner_view) ScannerView mScannerView;
  private Runnable mRunnable;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
    Window window = getActivity().getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    getComponent().inject(this);
    mPresenterScan.attachView(this);

    mRunnable = new ResumeCameraPreviewRunnable(this);
  }

  @Override public ComponentScan getComponent() {
    return DaggerComponentScan.builder()
        .appComponent(getBaseActivity().getAppComponent())
        .activityModule(new ActivityModule(getBaseActivity()))
        .build();
  }

  @Override public void onResume() {
    super.onResume();
    UIUtils.requestStatusBarLight(this, true, getResources().getColor(R.color.colorPrimary));
    try {
      mScannerView.setResultHandler(this);
      mScannerView.startCamera();
    } catch (Throwable throwable) {
      XLog.e("[qrcode] " + throwable.getMessage());
    }
  }

  @Override public void onPause() {
    super.onPause();
    try {
      getHandler().removeCallbacks(mRunnable);
      // 下面这一行如果某些6.x以下机型禁用了相机权限，会抛异常
      mScannerView.stopCamera();
    } catch (Throwable throwable) {
      XLog.e("[qrcode] " + throwable.getMessage());
    }
  }

  @Override protected int getContentView() {
    return R.layout.content_scan;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void showContent(GitHub data) {
    super.showContent(data);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    // ViewGroup.MarginLayoutParams params =
    // (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
    // params.topMargin = getSystemBarConfig().getStatusBarHeight();
    // mToolbar.setLayoutParams(params);
    // } else {
    // ViewGroup.LayoutParams params = getStatusBar().getLayoutParams();
    // params.height = getSystemBarConfig().getStatusBarHeight();
    // getStatusBar().setLayoutParams(params);
    // }

    Toolbar toolbar = getToolbar();
    toolbar.setVisibility(View.GONE);
  }

  @Override public void onDestroy() {
    mPresenterScan.detachView();
    super.onDestroy();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @OnClick({ R.id.back }) void onClick(View view) {
    getActivity().onBackPressed();
  }

  @Override public void handleResult(Result rawResult) {
    getHandler().postDelayed(mRunnable, 2000);

    mScannerView.stopCameraPreview();
    String content = rawResult.getContents();
    XLog.e("[qrcode] --> " + content);
  }

  private static class ResumeCameraPreviewRunnable implements Runnable {

    private WeakReference<ScanFragment> mReference;

    ResumeCameraPreviewRunnable(ScanFragment fragment) {
      mReference = new WeakReference<>(fragment);
    }

    @Override public void run() {
      ScanFragment fragment = mReference.get();
      if (fragment != null) {
        fragment.mScannerView.resumeCameraPreview(fragment);
      }
    }
  }
}
