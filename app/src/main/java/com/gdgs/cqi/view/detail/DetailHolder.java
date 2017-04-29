package com.gdgs.cqi.view.detail;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.gdgs.cqi.R;

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

  public DetailHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    avoidEmpty(colReportCode);
    avoidEmpty(colProductName);
    avoidEmpty(colProducerName);
    avoidEmpty(colProducerAddress);
    avoidEmpty(colBrand);
    avoidEmpty(colType);
    avoidEmpty(colProducerArea);
    avoidEmpty(colThirdPartPlatform);
    avoidEmpty(colOnlineSeller);
    avoidEmpty(colSeller);
    avoidEmpty(colSellerAddress);
    avoidEmpty(colUnqualifiedItem);
    avoidEmpty(colJudge);
    avoidEmpty(colDealing);
  }

  private void avoidEmpty(final TextInputEditText edit) {
    edit.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
          edit.setText("--");
        }
      }
    });
  }
}
