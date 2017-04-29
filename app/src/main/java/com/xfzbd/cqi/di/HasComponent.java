package com.xfzbd.cqi.di;

/**
 * XGitHub
 * com.hayukleung.xgithub.di
 * HasComponent.java
 *
 * by hayukleung
 * at 2017-03-31 16:28
 */
// Interface representing a contract for clients that contains a component for dependency injection.
public interface HasComponent<C> {
  C getComponent();
}
