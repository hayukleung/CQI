package com.xfzbd.cqi.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xfzbd.cqi.App;
import com.xfzbd.cqi.di.component.AppComponent;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.di.module.GitHubApiModule;
import javax.inject.Inject;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.Activity
 * BaseActivity.java
 *
 * by hayukleung
 * at 2017-03-31 16:17
 */

public class BaseActivity extends RxAppCompatActivity {


  // @Inject 告诉 Dagger 说 mGitHubApi 需要依赖注入
  // 于是 Dagger 就会构造一个 GitHubApi 的实例并满足它
  @Inject GitHubApiModule mGitHubApiModule;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getAppComponent().inject(this);
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return
   */
  public AppComponent getAppComponent() {
    return ((App) getApplicationContext()).getAppComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
