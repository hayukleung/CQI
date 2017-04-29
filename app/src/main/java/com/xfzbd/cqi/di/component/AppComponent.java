package com.xfzbd.cqi.di.component;

import com.xfzbd.cqi.di.module.AppModule;
import com.xfzbd.cqi.view.BaseActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * XGitHub
 * com.hayukleung.xgithub
 * AppComponent.java
 *
 * by hayukleung
 * at 2017-03-31 10:23
 */

// @Component: Components 从根本上来说就是一个注入器
// 也可以说是 @Inject 和 @Module 的桥梁
// 它的主要作用就是连接这两个部分
// Components 可以提供所有定义了的类型的实例
// 比如：我们必须用@Component注解一个接口然后列出所有的@Modules组成该组件
// 如果缺失了任何一块都会在编译的时候报错
// 所有的组件都可以通过它的 modules 知道依赖的范围
@Singleton @Component(modules = AppModule.class) public interface AppComponent {

  /**
   * 注入
   *
   * @param baseActivity Activity
   */
  void inject(BaseActivity baseActivity);
}
