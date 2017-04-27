package com.gdgs.cqi.di.module;

import com.gdgs.cqi.di.PerActivity;
import com.gdgs.cqi.view.BaseActivity;
import dagger.Module;
import dagger.Provides;

/**
 * XGitHub
 * com.hayukleung.xgithub.di.module
 * ActivityModule.java
 *
 * by hayukleung
 * at 2017-03-31 16:11
 */

@Module public class ActivityModule {

  private final BaseActivity mBaseActivity;

  public ActivityModule(BaseActivity baseActivity) {
    mBaseActivity = baseActivity;
  }

  @Provides @PerActivity BaseActivity providesActivity() {
    return mBaseActivity;
  }
}
