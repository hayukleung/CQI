package com.gdgs.cqi.model;

import com.gdgs.cqi.database.Product;
import java.util.List;

/**
 * CQI
 * com.gdgs.cqi.model
 * Result.java
 *
 * by hayukleung
 * at 2017-04-27 21:06
 */

public class Result extends BaseBean {

  private List<Product> productList;

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }
}
