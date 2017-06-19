package com.xfzbd.cqi.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.database.Product;

import static com.xfzbd.cqi.common.wrapper.XImage.load;
import static com.xfzbd.cqi.common.wrapper.XImage.url1;
import static com.xfzbd.cqi.common.wrapper.XImage.url2;
import static com.xfzbd.cqi.common.wrapper.XImage.url3;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarAdapter.java
 *
 * by hayukleung
 * at 2017-04-07 12:26
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailHolder> {

  private Context mContext;
  private Product mProduct;

  public DetailAdapter(Context context, Product product) {
    mContext = context;
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

  @Override public void onBindViewHolder(final DetailHolder holder, int position) {
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

    load(mContext, url1(mProduct.getReportCode()), holder.colImage1,
        R.drawable.ic_image_stub_detail);
    load(mContext, url2(mProduct.getReportCode()), holder.colImage2,
        R.drawable.ic_image_stub_detail);
    load(mContext, url3(mProduct.getReportCode()), holder.colImage3,
        R.drawable.ic_image_stub_detail);
  }

  @Override public int getItemCount() {
    return 1;
  }
}
