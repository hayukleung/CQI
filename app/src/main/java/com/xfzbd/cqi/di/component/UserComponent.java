package com.xfzbd.cqi.di.component;

import com.xfzbd.cqi.di.PerActivity;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.di.module.UserModule;
import com.xfzbd.cqi.ui.BaseFragment;
import dagger.Component;

/**
 * XGitHub
 * com.hayukleung.xgithub.di.component
 * UserComponent.java
 *
 * by hayukleung
 * at 2017-03-31 16:31
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, UserModule.class })
public interface UserComponent extends ActivityComponent {

  void inject(BaseFragment baseFragment);
}
