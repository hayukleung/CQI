package com.gdgs.cqi;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.gdgs.cqi.common.wrapper.XLog;
import com.gdgs.cqi.di.component.AppComponent;
import com.gdgs.cqi.di.component.DaggerAppComponent;
import com.gdgs.cqi.di.module.AppModule;

/**
 * XGitHub
 * com.hayukleung.xgithub
 * App.java
 *
 * by hayukleung
 * at 2017-03-31 09:25
 */

public class App extends MultiDexApplication {

  private AppComponent mAppComponent;

  public static App getInstance(Context context) {
    return (App) context.getApplicationContext();
  }

  public AppComponent getAppComponent() {
    if (null == mAppComponent) {
      initComponent();
    }
    return mAppComponent;
  }

  private void initComponent() {
    mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }

  @Override public void onCreate() {
    super.onCreate();
    initComponent();
    XLog.init();
  }
}
