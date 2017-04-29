package com.gdgs.cqi.view.result;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import butterknife.BindView;
import com.gdgs.cqi.R;
import com.gdgs.cqi.view.XFragment;

/**
 * CQI
 * com.gdgs.cqi.view.result
 * FilterFragment.java
 *
 * by hayukleung
 * at 2017-04-29 10:09
 */

public class FilterFragment extends XFragment {

  @BindView(R.id.category_01) RadioButton mCategory01;
  @BindView(R.id.category_02) RadioButton mCategory02;
  @BindView(R.id.category_03) RadioButton mCategory03;
  @BindView(R.id.category_04) RadioButton mCategory04;
  @BindView(R.id.category_05) RadioButton mCategory05;
  @BindView(R.id.category_06) RadioButton mCategory06;
  @BindView(R.id.category_07) RadioButton mCategory07;
  @BindView(R.id.category_08) RadioButton mCategory08;
  @BindView(R.id.category_09) RadioButton mCategory09;
  @BindView(R.id.category_10) RadioButton mCategory10;
  @BindView(R.id.category_11) RadioButton mCategory11;
  @BindView(R.id.category_12) RadioButton mCategory12;
  @BindView(R.id.category_13) RadioButton mCategory13;
  @BindView(R.id.category_14) RadioButton mCategory14;
  @BindView(R.id.category_15) RadioButton mCategory15;
  @BindView(R.id.category_16) RadioButton mCategory16;
  @BindView(R.id.category_17) RadioButton mCategory17;
  @BindView(R.id.category_18) RadioButton mCategory18;
  @BindView(R.id.category_19) RadioButton mCategory19;
  @BindView(R.id.category_20) RadioButton mCategory20;
  @BindView(R.id.category_21) RadioButton mCategory21;
  @BindView(R.id.category_22) RadioButton mCategory22;
  @BindView(R.id.category_23) RadioButton mCategory23;
  @BindView(R.id.category_24) RadioButton mCategory24;
  @BindView(R.id.category_25) RadioButton mCategory25;
  @BindView(R.id.category) RadioGroup mCategory;
  @BindView(R.id.scroll_view) ScrollView mScrollView;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int pre = ((ResultFragment) getParentFragment()).mIntCategory;
        switch (checkedId) {
          case R.id.category_01: {
            ((ResultFragment) getParentFragment()).mIntCategory = 1;
            break;
          }
          case R.id.category_02: {
            ((ResultFragment) getParentFragment()).mIntCategory = 2;
            break;
          }
          case R.id.category_03: {
            ((ResultFragment) getParentFragment()).mIntCategory = 3;
            break;
          }
          case R.id.category_04: {
            ((ResultFragment) getParentFragment()).mIntCategory = 4;
            break;
          }
          case R.id.category_05: {
            ((ResultFragment) getParentFragment()).mIntCategory = 5;
            break;
          }
          case R.id.category_06: {
            ((ResultFragment) getParentFragment()).mIntCategory = 6;
            break;
          }
          case R.id.category_07: {
            ((ResultFragment) getParentFragment()).mIntCategory = 7;
            break;
          }
          case R.id.category_08: {
            ((ResultFragment) getParentFragment()).mIntCategory = 8;
            break;
          }
          case R.id.category_09: {
            ((ResultFragment) getParentFragment()).mIntCategory = 9;
            break;
          }
          case R.id.category_10: {
            ((ResultFragment) getParentFragment()).mIntCategory = 10;
            break;
          }
          case R.id.category_11: {
            ((ResultFragment) getParentFragment()).mIntCategory = 11;
            break;
          }
          case R.id.category_12: {
            ((ResultFragment) getParentFragment()).mIntCategory = 12;
            break;
          }
          case R.id.category_13: {
            ((ResultFragment) getParentFragment()).mIntCategory = 13;
            break;
          }
          case R.id.category_14: {
            ((ResultFragment) getParentFragment()).mIntCategory = 14;
            break;
          }
          case R.id.category_15: {
            ((ResultFragment) getParentFragment()).mIntCategory = 15;
            break;
          }
          case R.id.category_16: {
            ((ResultFragment) getParentFragment()).mIntCategory = 16;
            break;
          }
          case R.id.category_17: {
            ((ResultFragment) getParentFragment()).mIntCategory = 17;
            break;
          }
          case R.id.category_18: {
            ((ResultFragment) getParentFragment()).mIntCategory = 18;
            break;
          }
          case R.id.category_19: {
            ((ResultFragment) getParentFragment()).mIntCategory = 19;
            break;
          }
          case R.id.category_20: {
            ((ResultFragment) getParentFragment()).mIntCategory = 20;
            break;
          }
          case R.id.category_21: {
            ((ResultFragment) getParentFragment()).mIntCategory = 21;
            break;
          }
          case R.id.category_22: {
            ((ResultFragment) getParentFragment()).mIntCategory = 22;
            break;
          }
          case R.id.category_23: {
            ((ResultFragment) getParentFragment()).mIntCategory = 23;
            break;
          }
          case R.id.category_24: {
            ((ResultFragment) getParentFragment()).mIntCategory = 24;
            break;
          }
          case R.id.category_25: {
            ((ResultFragment) getParentFragment()).mIntCategory = 25;
            break;
          }
        }
        if (pre != ((ResultFragment) getParentFragment()).mIntCategory) {
          ((FilterInterface) getParentFragment()).onFilterSure();
        }
      }
    });
  }

  @Override public void showContent(Object data) {

  }

  @Override protected int getContentView() {
    return R.layout.content_filter;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }
}
