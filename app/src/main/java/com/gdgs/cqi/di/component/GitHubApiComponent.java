package com.gdgs.cqi.di.component;

import com.gdgs.cqi.di.PerActivity;
import com.gdgs.cqi.di.module.GitHubApiModule;
import com.gdgs.cqi.view.BaseActivity;
import com.gdgs.cqi.di.module.ActivityModule;
import dagger.Component;

/**
 * XGitHub
 * com.hayukleung.xgithub.di.component
 * UserComponent.java
 *
 * by hayukleung
 * at 2017-03-31 16:31
 */
@PerActivity @Component(dependencies = AppComponent.class, modules = {
    ActivityModule.class, GitHubApiModule.class
}) public interface GitHubApiComponent extends ActivityComponent {

  void inject(BaseActivity baseActivity);
}
