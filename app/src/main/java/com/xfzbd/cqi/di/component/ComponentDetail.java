package com.xfzbd.cqi.di.component;

import com.xfzbd.cqi.di.PerActivity;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.ui.detail.DetailFragment;
import dagger.Component;

/**
 * XGitHub
 * com.hayukleung.xgithub.di.component
 * MainComponent.java
 *
 * by hayukleung
 * at 2017-04-02 17:38
 */
@PerActivity @Component(dependencies = AppComponent.class, modules = {
    ActivityModule.class
}) public interface ComponentDetail extends ActivityComponent {

  void inject(DetailFragment detailFragment);
}
