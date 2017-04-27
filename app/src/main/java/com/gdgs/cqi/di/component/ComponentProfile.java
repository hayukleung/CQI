package com.gdgs.cqi.di.component;

import com.gdgs.cqi.di.PerActivity;
import com.gdgs.cqi.di.module.ActivityModule;
import com.gdgs.cqi.view.profile.ProfileFragment;
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
}) public interface ComponentProfile extends ActivityComponent {
  void inject(ProfileFragment profileFragment);
}
