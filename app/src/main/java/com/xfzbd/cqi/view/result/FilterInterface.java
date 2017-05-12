package com.xfzbd.cqi.view.result;

/**
 * CQI
 * com.gdgs.cqi.view.result
 * FilterInterface.java
 *
 * by hayukleung
 * at 2017-04-29 11:11
 */

public interface FilterInterface {

  /**
   * 确定过滤条件
   *
   * @param useDiffUtil 是否使用 DiffUtil
   */
  void onFilterSure(boolean useDiffUtil);

  /**
   * 退出
   */
  void onFilterCancel();
}
