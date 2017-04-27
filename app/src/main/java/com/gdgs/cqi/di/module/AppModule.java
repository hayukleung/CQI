package com.gdgs.cqi.di.module;

import com.gdgs.cqi.App;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * XGitHub
 * com.hayukleung.xgithub
 * AppModule.java
 *
 * by hayukleung
 * at 2017-03-31 10:24
 */

// @Module: Modules 类里面的方法专门提供依赖
// 所以我们定义一个类用@Module注解
// 这样Dagger在构造类的实例的时候就知道从哪里去找到需要的依赖
@Module public class AppModule {

  private final App mApp;

  public AppModule(App app) {
    mApp = app;
  }

  // 在 modules 中我们定义的方法是用这个注解
  // 以此来告诉 Dagger 我们想要构造对象并提供这些依赖
  @Provides @Singleton public App providesApp() {
    return mApp;
  }
}
