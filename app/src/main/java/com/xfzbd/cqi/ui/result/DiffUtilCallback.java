package com.xfzbd.cqi.ui.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import com.xfzbd.cqi.common.wrapper.XLog;
import com.xfzbd.cqi.database.Product;
import java.util.List;

import static com.xfzbd.cqi.common.CommonUtils.isStringTheSame;

public class DiffUtilCallback extends DiffUtil.Callback {

  private int oldCategory = 0;
  private int newCategory = 0;
  private List<Product> oldDataList;
  private List<Product> newDataList;

  public DiffUtilCallback(int oldCategory, int newCategory, List<Product> oldDataList,
      List<Product> newDataList) {
    this.oldCategory = oldCategory;
    this.newCategory = newCategory;
    this.oldDataList = oldDataList;
    this.newDataList = newDataList;
  }

  @Override public int getOldListSize() {
    return null == oldDataList ? 0 : oldDataList.size();
  }

  @Override public int getNewListSize() {
    return null == newDataList ? 0 : newDataList.size();
  }

  @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    XLog.e("oldSize --> " + getOldListSize() + " oldItemPosition --> " + oldItemPosition);
    XLog.e("newSize --> " + getNewListSize() + " newItemPosition --> " + newItemPosition);
    return oldDataList.get(oldItemPosition).equals(newDataList.get(newItemPosition));
  }

  @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

    if (oldCategory != newCategory) {
      return false;
    }

    Product oldProduct = oldDataList.get(oldItemPosition);
    Product newProduct = newDataList.get(newItemPosition);

    if (oldProduct.getCategory() != newProduct.getCategory()) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getCategoryName(), newProduct.getCategoryName())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getReportCode(), newProduct.getReportCode())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getProductName(), newProduct.getProductName())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getProducerName(), newProduct.getProducerName())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getProducerAddress(), newProduct.getProducerAddress())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getBrand(), newProduct.getBrand())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getType(), newProduct.getType())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getProducerArea(), newProduct.getProducerArea())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getThirdPartPlatform(), newProduct.getThirdPartPlatform())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getOnlineSellerWebsite(),
        newProduct.getOnlineSellerWebsite())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getSeller(), newProduct.getSeller())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getSellerAddress(), newProduct.getSellerAddress())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getUnqualifiedItem(), newProduct.getUnqualifiedItem())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getJudge(), newProduct.getJudge())) {
      return false;
    }
    if (!isStringTheSame(oldProduct.getDealing(), newProduct.getDealing())) {
      return false;
    }
    return true;
  }

  @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    Bundle bundle = null;
    if (areItemsTheSame(oldItemPosition, newItemPosition) && !areContentsTheSame(oldItemPosition,
        newItemPosition)) {

      Product newProduct = newDataList.get(newItemPosition);
      bundle = new Bundle();
      bundle.putInt("category", newProduct.getCategory());
      bundle.putString("categoryName", newProduct.getCategoryName());
      bundle.putString("reportCode", newProduct.getReportCode());
      bundle.putString("productName", newProduct.getProductName());
      bundle.putString("producerName", newProduct.getProducerName());
      bundle.putString("producerAddress", newProduct.getProducerAddress());
      bundle.putString("brand", newProduct.getBrand());
      bundle.putString("type", newProduct.getType());
      bundle.putString("producerArea", newProduct.getProducerArea());
      bundle.putString("thirdPartPlatform", newProduct.getThirdPartPlatform());
      bundle.putString("onlineSellerWebsite", newProduct.getOnlineSellerWebsite());
      bundle.putString("seller", newProduct.getSeller());
      bundle.putString("sellerAddress", newProduct.getSellerAddress());
      bundle.putString("unqualifiedItem", newProduct.getUnqualifiedItem());
      bundle.putString("judge", newProduct.getJudge());
      bundle.putString("dealing", newProduct.getDealing());
    }
    return bundle;
  }
}
