package com.xfzbd.cqi.database;

import com.xfzbd.cqi.model.BaseBean;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * CQI
 * com.gdgs.cqi.model
 * Product.java
 *
 * by hayukleung
 * at 2017-04-26 19:11
 */
@Entity public class Product extends BaseBean {

  @Id(autoincrement = true) private long id;
  /** 类别 */
  private int category;
  /** 检验报告编号 */
  private String reportCode;
  /** 商品名称 */
  private String productName;
  /** 生产单位名称 */
  private String producerName;
  /** 生产单位地址 */
  private String producerAddress;
  /** 商标 */
  private String brand;
  /** 规格型号 */
  private String type;
  /** 生产单位所在地区 */
  private String producerArea;
  /** 第三方交易平台名称 */
  private String thirdPartPlatform;
  /** 网络经营者网址 */
  private String onlineSellerWebsite;
  /** 被抽样经营者名称 */
  private String seller;
  /** 被抽样经营者地址 */
  private String sellerAddress;
  /** 不合格项目 */
  private String unqualifiedItem;
  /** 综合判定 */
  private String judge;
  /** 处理情况 */
  private String dealing;
  @Generated(hash = 1417859771)
public Product(long id, int category, String reportCode, String productName,
        String producerName, String producerAddress, String brand, String type,
        String producerArea, String thirdPartPlatform,
        String onlineSellerWebsite, String seller, String sellerAddress,
        String unqualifiedItem, String judge, String dealing) {
    this.id = id;
    this.category = category;
    this.reportCode = reportCode;
    this.productName = productName;
    this.producerName = producerName;
    this.producerAddress = producerAddress;
    this.brand = brand;
    this.type = type;
    this.producerArea = producerArea;
    this.thirdPartPlatform = thirdPartPlatform;
    this.onlineSellerWebsite = onlineSellerWebsite;
    this.seller = seller;
    this.sellerAddress = sellerAddress;
    this.unqualifiedItem = unqualifiedItem;
    this.judge = judge;
    this.dealing = dealing;
}
@Generated(hash = 1890278724)
  public Product() {
  }
  public long getId() {
      return this.id;
  }
  public void setId(long id) {
      this.id = id;
  }
  public int getCategory() {
      return this.category;
  }
  public void setCategory(int category) {
      this.category = category;
  }
  public String getReportCode() {
      return this.reportCode;
  }
  public void setReportCode(String reportCode) {
      this.reportCode = reportCode;
  }
  public String getProductName() {
      return this.productName;
  }
  public void setProductName(String productName) {
      this.productName = productName;
  }
  public String getProducerName() {
      return this.producerName;
  }
  public void setProducerName(String producerName) {
      this.producerName = producerName;
  }
  public String getProducerAddress() {
      return this.producerAddress;
  }
  public void setProducerAddress(String producerAddress) {
      this.producerAddress = producerAddress;
  }
  public String getBrand() {
      return this.brand;
  }
  public void setBrand(String brand) {
      this.brand = brand;
  }
  public String getType() {
      return this.type;
  }
  public void setType(String type) {
      this.type = type;
  }
  public String getSeller() {
      return this.seller;
  }
  public void setSeller(String seller) {
      this.seller = seller;
  }
  public String getUnqualifiedItem() {
      return this.unqualifiedItem;
  }
  public void setUnqualifiedItem(String unqualifiedItem) {
      this.unqualifiedItem = unqualifiedItem;
  }
  public String getJudge() {
      return this.judge;
  }
  public void setJudge(String judge) {
      this.judge = judge;
  }
  public String getDealing() {
      return this.dealing;
  }
  public void setDealing(String dealing) {
      this.dealing = dealing;
  }
public String getProducerArea() {
    return this.producerArea;
}
public void setProducerArea(String producerArea) {
    this.producerArea = producerArea;
}
public String getThirdPartPlatform() {
    return this.thirdPartPlatform;
}
public void setThirdPartPlatform(String thirdPartPlatform) {
    this.thirdPartPlatform = thirdPartPlatform;
}
public String getOnlineSellerWebsite() {
    return this.onlineSellerWebsite;
}
public void setOnlineSellerWebsite(String onlineSellerWebsite) {
    this.onlineSellerWebsite = onlineSellerWebsite;
}
public String getSellerAddress() {
    return this.sellerAddress;
}
public void setSellerAddress(String sellerAddress) {
    this.sellerAddress = sellerAddress;
}
}
