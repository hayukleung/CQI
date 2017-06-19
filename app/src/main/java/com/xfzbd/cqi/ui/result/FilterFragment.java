package com.xfzbd.cqi.ui.result;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import butterknife.BindView;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.CommonUtils;
import com.xfzbd.cqi.ui.XFragment;
import com.xfzbd.cqi.widget.XRadioGroup;

/**
 * CQI
 * com.gdgs.cqi.view.result
 * FilterFragment.java
 *
 * by hayukleung
 * at 2017-04-29 10:09
 */

public class FilterFragment extends XFragment {

  @BindView(R.id.category_00) RadioButton mCategory00;
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
  @BindView(R.id.category) XRadioGroup mCategory;
  @BindView(R.id.scroll_view) ScrollView mScrollView;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mCategory.setOnCheckedChangeListener(new XRadioGroup.OnCheckedChangeListener() {

      @Override public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        ((ResultFragment) getParentFragment()).mIntCategoryNew =
            ((ResultFragment) getParentFragment()).mIntCategory;
        switch (checkedId) {
          case R.id.category_00: {
            ((ResultFragment) getParentFragment()).mIntCategory = 0;
            break;
          }
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
        if (((ResultFragment) getParentFragment()).mIntCategoryNew
            != ((ResultFragment) getParentFragment()).mIntCategory) {
          ((FilterInterface) getParentFragment()).onFilterSure(false);
        }
      }
    });

    switch (((ResultFragment) getParentFragment()).mIntCategory) {
      case 0: {
        mCategory00.setChecked(true);
        break;
      }
      case 1: {
        mCategory01.setChecked(true);
        break;
      }
      case 2: {
        mCategory02.setChecked(true);
        break;
      }
      case 3: {
        mCategory03.setChecked(true);
        break;
      }
      case 4: {
        mCategory04.setChecked(true);
        break;
      }
      case 5: {
        mCategory05.setChecked(true);
        break;
      }
      case 6: {
        mCategory06.setChecked(true);
        break;
      }
      case 7: {
        mCategory07.setChecked(true);
        break;
      }
      case 8: {
        mCategory08.setChecked(true);
        break;
      }
      case 9: {
        mCategory09.setChecked(true);
        break;
      }
      case 10: {
        mCategory10.setChecked(true);
        break;
      }
      case 11: {
        mCategory11.setChecked(true);
        break;
      }
      case 12: {
        mCategory12.setChecked(true);
        break;
      }
      case 13: {
        mCategory13.setChecked(true);
        break;
      }
      case 14: {
        mCategory14.setChecked(true);
        break;
      }
      case 15: {
        mCategory15.setChecked(true);
        break;
      }
      case 16: {
        mCategory16.setChecked(true);
        break;
      }
      case 17: {
        mCategory17.setChecked(true);
        break;
      }
      case 18: {
        mCategory18.setChecked(true);
        break;
      }
      case 19: {
        mCategory19.setChecked(true);
        break;
      }
      case 20: {
        mCategory20.setChecked(true);
        break;
      }
      case 21: {
        mCategory21.setChecked(true);
        break;
      }
      case 22: {
        mCategory22.setChecked(true);
        break;
      }
      case 23: {
        mCategory23.setChecked(true);
        break;
      }
      case 24: {
        mCategory24.setChecked(true);
        break;
      }
      case 25: {
        mCategory25.setChecked(true);
        break;
      }
    }

    mScrollView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()
            || MotionEvent.ACTION_MOVE == event.getAction()) {
          CommonUtils.hideInputMethod(getActivity(),
              ((ResultFragment) getParentFragment()).mKeywords);
        }
        return false;
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
