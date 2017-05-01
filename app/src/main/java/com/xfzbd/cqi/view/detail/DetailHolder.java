package com.xfzbd.cqi.view.detail;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.database.Product;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarHolder.java
 *
 * by hayukleung
 * at 2017-04-07 12:27
 */

public class DetailHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.col_report_code) EditText colReportCode;
  @BindView(R.id.col_product_name) EditText colProductName;
  @BindView(R.id.col_producer_name) EditText colProducerName;
  @BindView(R.id.col_producer_address) EditText colProducerAddress;
  @BindView(R.id.col_brand) EditText colBrand;
  @BindView(R.id.col_type) EditText colType;
  @BindView(R.id.col_producer_area) EditText colProducerArea;
  @BindView(R.id.col_third_part_platform) EditText colThirdPartPlatform;
  @BindView(R.id.col_online_seller) EditText colOnlineSeller;
  @BindView(R.id.col_seller) EditText colSeller;
  @BindView(R.id.col_seller_address) EditText colSellerAddress;
  @BindView(R.id.col_unqualified_item) EditText colUnqualifiedItem;
  @BindView(R.id.col_judge) EditText colJudge;
  @BindView(R.id.col_dealing) EditText colDealing;

  private Product mProduct;

  public DetailHolder(View itemView, Product product) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    avoidEmpty(0, colReportCode);
    avoidEmpty(1, colProductName);
    avoidEmpty(2, colProducerName);
    avoidEmpty(3, colProducerAddress);
    avoidEmpty(4, colBrand);
    avoidEmpty(5, colType);
    avoidEmpty(6, colProducerArea);
    avoidEmpty(7, colThirdPartPlatform);
    avoidEmpty(8, colOnlineSeller);
    avoidEmpty(9, colSeller);
    avoidEmpty(10, colSellerAddress);
    avoidEmpty(11, colUnqualifiedItem);
    avoidEmpty(12, colJudge);
    avoidEmpty(13, colDealing);

    mProduct = product;
  }

  private void avoidEmpty(final int index, final EditText edit) {
    edit.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
          edit.setText("--");
        }
        switch (index) {
          case 0: {
            mProduct.setReportCode(s.toString());
            break;
          }
          case 1: {
            mProduct.setProductName(s.toString());
            break;
          }
          case 2: {
            mProduct.setProducerName(s.toString());
            break;
          }
          case 3: {
            mProduct.setProducerAddress(s.toString());
            break;
          }
          case 4: {
            mProduct.setBrand(s.toString());
            break;
          }
          case 5: {
            mProduct.setType(s.toString());
            break;
          }
          case 6: {
            mProduct.setProducerArea(s.toString());
            break;
          }
          case 7: {
            mProduct.setThirdPartPlatform(s.toString());
            break;
          }
          case 8: {
            mProduct.setOnlineSellerWebsite(s.toString());
            break;
          }
          case 9: {
            mProduct.setSeller(s.toString());
            break;
          }
          case 10: {
            mProduct.setSellerAddress(s.toString());
            break;
          }
          case 11: {
            mProduct.setUnqualifiedItem(s.toString());
            break;
          }
          case 12: {
            mProduct.setJudge(s.toString());
            break;
          }
          case 13: {
            mProduct.setDealing(s.toString());
            break;
          }
        }
      }
    });
  }
}
