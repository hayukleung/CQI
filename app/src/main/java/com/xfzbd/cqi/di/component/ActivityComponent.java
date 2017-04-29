package com.xfzbd.cqi.di.component;

import com.xfzbd.cqi.di.PerActivity;
import com.xfzbd.cqi.di.module.ActivityModule;
import com.xfzbd.cqi.di.module.GitHubApiModule;
import com.xfzbd.cqi.view.BaseActivity;
import dagger.Component;

/**
 * XGitHub
 * com.hayukleung.xgithub.di.component
 * ActivityComponent.java
 *
 * by hayukleung
 * at 2017-03-31 16:11
 */
@PerActivity @Component(dependencies = AppComponent.class, modules = {
    ActivityModule.class, GitHubApiModule.class
}) public interface ActivityComponent {

  BaseActivity getBaseActivity();
}