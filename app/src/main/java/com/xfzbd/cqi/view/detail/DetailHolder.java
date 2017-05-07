package com.xfzbd.cqi.view.detail;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.database.Product;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  @BindView(R.id.col_image_1) ImageView colImage1;
  @BindView(R.id.col_image_2) ImageView colImage2;
  @BindView(R.id.col_image_3) ImageView colImage3;

  private Product mProduct;

  public DetailHolder(View itemView, Product product) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    modify(0, colReportCode);
    modify(1, colProductName);
    modify(2, colProducerName);
    modify(3, colProducerAddress);
    modify(4, colBrand);
    modify(5, colType);
    modify(6, colProducerArea);
    modify(7, colThirdPartPlatform);
    modify(8, colOnlineSeller);
    modify(9, colSeller);
    modify(10, colSellerAddress);
    modify(11, colUnqualifiedItem);
    modify(12, colJudge);
    modify(13, colDealing);

    mProduct = product;
  }

  /**
   * 库表内容优化
   *
   * @param index
   * @param edit
   */
  private void modify(final int index, final EditText edit) {
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
            String before = s.toString();
            String after = forMultiLines(s);
            if (!after.equals(before)) {
              edit.setText(after);
            }
            mProduct.setUnqualifiedItem(after);
            break;
          }
          case 12: {
            String before = s.toString();
            String after = forMultiLines(s);
            if (!after.equals(before)) {
              edit.setText(after);
            }
            mProduct.setJudge(after);
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

  /**
   * from
   *
   * 1.xxxxxx2.xxxxxx3.xxxxxx...
   *
   * to
   *
   * 1.xxxxxx
   * 2.xxxxxx
   * 3.xxxxxx
   * ...
   *
   * @param before
   * @return
   */
  private String forMultiLines(Editable before) {
    String after = before.toString().trim();
    Pattern pattern = Pattern.compile("\\d.\\D");
    Matcher matcher = pattern.matcher(after);
    while (matcher.find()) {
      String temp = matcher.group();
      after = after.replace(temp, "\n" + temp);
    }
    if (after.startsWith("\n")) {
      after = after.replaceFirst("\n", "");
    }
    after = after.replace("\n\n", "\n");
    return after;
  }
}
