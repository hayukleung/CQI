package com.xfzbd.cqi.view;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xfzbd.cqi.di.module.GitHubApiModule;

public class BaseFragment extends RxFragment {

  // protected <C> C getComponent(Class<C> componentType) {
  // FragmentActivity activity = getActivity();
  // if (activity instanceof HasComponent) {
  // HasComponent hasComponent = (HasComponent) activity;
  // return componentType.cast(hasComponent.getComponent());
  // }
  // return null;
  // }

  public boolean onBackPressed() {
    return false;
  }

  protected GitHubApiModule getGitHubApiModule() {
    return getBaseActivity().mGitHubApiModule;
  }

  protected BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }
}
