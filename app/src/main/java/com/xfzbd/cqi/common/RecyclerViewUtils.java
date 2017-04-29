package com.xfzbd.cqi.common;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * XGitHub
 * com.hayukleung.xgithub.common
 * RecyclerViewUtils.java
 *
 * by hayukleung
 * at 2017-04-07 15:43
 */

public class RecyclerViewUtils {

  /**
   * 获取RecyclerView垂直方向移动的距离
   *
   * @param recyclerView RecyclerView
   * @return 距离px
   */
  public static int getRecyclerViewScrollY(RecyclerView recyclerView) {
    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    int position = layoutManager.findFirstVisibleItemPosition();
    View firstVisibleChildView = layoutManager.findViewByPosition(position);
    int itemHeight = firstVisibleChildView.getHeight();
    return (position) * itemHeight - firstVisibleChildView.getTop();
  }

  public static void stopRefresh(SwipeRefreshLayout refreshLayout) {
    if (refreshLayout != null && refreshLayout.isRefreshing()) {
      refreshLayout.setRefreshing(false);
    }
  }
}
