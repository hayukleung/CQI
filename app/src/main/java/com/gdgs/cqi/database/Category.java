package com.gdgs.cqi.database;

import android.util.SparseArray;
import com.gdgs.cqi.model.BaseBean;

/**
 * CQI
 * com.gdgs.cqi.database
 * Category.java
 *
 * by hayukleung
 * at 2017-04-27 10:58
 */

public class Category extends BaseBean {

  public static final SparseArray<String> CATEGORY = new SparseArray<>(25);
  static {
    CATEGORY.put(0, "全部类别");
    CATEGORY.put(1, "汽车儿童安全坐椅");
    CATEGORY.put(2, "汽油、柴油");
    CATEGORY.put(3, "针织内衣、文胸");
    CATEGORY.put(4, "消防器材");
    CATEGORY.put(5, "家用燃气具");
    CATEGORY.put(6, "玩具");
    CATEGORY.put(7, "汽车轮胎");
    CATEGORY.put(8, "电冰箱");
    CATEGORY.put(9, "电线电缆");
    CATEGORY.put(10, "润滑油、制动液");
    CATEGORY.put(11, "婴幼儿食具");
    CATEGORY.put(12, "童车");
    CATEGORY.put(13, "电动自行车");
    CATEGORY.put(14, "移动电源、USB插座");
    CATEGORY.put(15, "儿童及婴幼儿服装");
    CATEGORY.put(16, "人造板");
    CATEGORY.put(17, "蓄电池");
    CATEGORY.put(18, "文具");
    CATEGORY.put(19, "鞋");
    CATEGORY.put(20, "小家电");
    CATEGORY.put(21, "纸制品");
    CATEGORY.put(22, "箱包皮具");
    CATEGORY.put(23, "洗涤用品");
    CATEGORY.put(24, "家电");
    CATEGORY.put(25, "服装");
  }
}
