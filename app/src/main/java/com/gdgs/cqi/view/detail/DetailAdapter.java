package com.gdgs.cqi.view.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.gdgs.cqi.R;
import com.gdgs.cqi.database.Product;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarAdapter.java
 *
 * by hayukleung
 * at 2017-04-07 12:26
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailHolder> {

  private Product mProduct;

  public DetailAdapter(Product product) {
    mProduct = product;
  }

  public Product getProduct() {
    return mProduct;
  }

  @Override public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new DetailHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false),
        mProduct);
  }

  @Override public void onBindViewHolder(DetailHolder holder, int position) {
    holder.colReportCode.setText(mProduct.getReportCode());
    holder.colProductName.setText(mProduct.getProductName());
    holder.colProducerName.setText(mProduct.getProducerName());
    holder.colProducerAddress.setText(mProduct.getProducerAddress());
    holder.colBrand.setText(mProduct.getBrand());
    holder.colType.setText(mProduct.getType());
    holder.colProducerArea.setText(mProduct.getProducerArea());
    holder.colThirdPartPlatform.setText(mProduct.getThirdPartPlatform());
    holder.colOnlineSeller.setText(mProduct.getOnlineSellerWebsite());
    holder.colSeller.setText(mProduct.getSeller());
    holder.colSellerAddress.setText(mProduct.getSellerAddress());
    holder.colUnqualifiedItem.setText(mProduct.getUnqualifiedItem());
    holder.colJudge.setText(mProduct.getJudge());
    holder.colDealing.setText(mProduct.getDealing());
  }

  @Override public int getItemCount() {
    return 1;
  }
}
