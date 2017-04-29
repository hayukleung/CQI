package com.xfzbd.cqi.view.detail;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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

  @BindView(R.id.col_report_code) TextInputEditText colReportCode;
  @BindView(R.id.col_product_name) TextInputEditText colProductName;
  @BindView(R.id.col_producer_name) TextInputEditText colProducerName;
  @BindView(R.id.col_producer_address) TextInputEditText colProducerAddress;
  @BindView(R.id.col_brand) TextInputEditText colBrand;
  @BindView(R.id.col_type) TextInputEditText colType;
  @BindView(R.id.col_producer_area) TextInputEditText colProducerArea;
  @BindView(R.id.col_third_part_platform) TextInputEditText colThirdPartPlatform;
  @BindView(R.id.col_online_seller) TextInputEditText colOnlineSeller;
  @BindView(R.id.col_seller) TextInputEditText colSeller;
  @BindView(R.id.col_seller_address) TextInputEditText colSellerAddress;
  @BindView(R.id.col_unqualified_item) TextInputEditText colUnqualifiedItem;
  @BindView(R.id.col_judge) TextInputEditText colJudge;
  @BindView(R.id.col_dealing) TextInputEditText colDealing;

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

  private void avoidEmpty(final int index, final TextInputEditText edit) {
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
