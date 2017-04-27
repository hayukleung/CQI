package com.gdgs.cqi.view.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.gdgs.cqi.R;
import com.gdgs.cqi.common.wrapper.XDao;
import com.gdgs.cqi.contract.ContractProfile;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.database.ProductDao;
import com.gdgs.cqi.di.HasComponent;
import com.gdgs.cqi.di.component.ComponentProfile;
import com.gdgs.cqi.di.component.DaggerComponentProfile;
import com.gdgs.cqi.di.module.ActivityModule;
import com.gdgs.cqi.model.GitHub;
import com.gdgs.cqi.presenter.PresenterProfile;
import com.gdgs.cqi.view.Activities;
import com.gdgs.cqi.view.UIUtils;
import com.gdgs.cqi.view.XFragment;
import com.gdgs.cqi.view.scan.ScanFragment;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.profile
 * ProfileFragment.java
 *
 * by hayukleung
 * at 2017-04-03 20:53
 */

public class ProfileFragment extends XFragment<GitHub, ContractProfile.IPresenterProfile>
    implements ProfileView, HasComponent<ComponentProfile> {

  @Inject protected PresenterProfile mPresenterProfile;

  @BindView(R.id.text_input_layout) TextInputLayout mTextInputLayout;
  @BindView(R.id.text_input_edit_text) TextInputEditText mTextInputEditText;
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
  @BindView(R.id.search) Button mSearch;

  private int mIntCategory = 0;
  private String mStrKeyword = "";

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);
    mPresenterProfile.attachView(this);
  }

  @Override public ComponentProfile getComponent() {
    return DaggerComponentProfile.builder()
        .appComponent(getBaseActivity().getAppComponent())
        .activityModule(new ActivityModule(getBaseActivity()))
        .build();
  }

  @Override public void onResume() {
    super.onResume();
    UIUtils.requestStatusBarLight(this, true, getResources().getColor(R.color.colorPrimary));
  }

  @Override protected int getContentView() {
    return R.layout.content_profile;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void showContent(GitHub data) {
    super.showContent(data);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    // ViewGroup.MarginLayoutParams params =
    // (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
    // params.topMargin = getSystemBarConfig().getStatusBarHeight();
    // mToolbar.setLayoutParams(params);
    // } else {
    // ViewGroup.LayoutParams params = getStatusBar().getLayoutParams();
    // params.height = getSystemBarConfig().getStatusBarHeight();
    // getStatusBar().setLayoutParams(params);
    // }

    // TODO

    Toolbar toolbar = getToolbar();
    toolbar.setBackgroundColor(Color.WHITE);
    toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_black);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    UIUtils.setCenterTitle(getActivity(), toolbar, "Profile")
        .setTextColor(getActivity().getResources().getColor(android.R.color.black));

    TextView right = (TextView) getActivity().getLayoutInflater()
        .inflate(R.layout.layout_toolbar_right_text, toolbar, false);
    ((Toolbar.LayoutParams) right.getLayoutParams()).gravity = Gravity.END;
    right.setText("扫码");
    toolbar.addView(right);
    right.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Activities.startActivity(ProfileFragment.this, ScanFragment.class);
      }
    });

    mTextInputEditText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override public void afterTextChanged(Editable s) {
        mStrKeyword = s.toString();
      }
    });

    mCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
          case R.id.category_01: {
            mIntCategory = 1;
            break;
          }
          case R.id.category_02: {
            mIntCategory = 2;
            break;
          }
          case R.id.category_03: {
            mIntCategory = 3;
            break;
          }
          case R.id.category_04: {
            mIntCategory = 4;
            break;
          }
          case R.id.category_05: {
            mIntCategory = 5;
            break;
          }
          case R.id.category_06: {
            mIntCategory = 6;
            break;
          }
          case R.id.category_07: {
            mIntCategory = 7;
            break;
          }
          case R.id.category_08: {
            mIntCategory = 8;
            break;
          }
          case R.id.category_09: {
            mIntCategory = 9;
            break;
          }
          case R.id.category_10: {
            mIntCategory = 10;
            break;
          }
          case R.id.category_11: {
            mIntCategory = 11;
            break;
          }
          case R.id.category_12: {
            mIntCategory = 12;
            break;
          }
          case R.id.category_13: {
            mIntCategory = 13;
            break;
          }
          case R.id.category_14: {
            mIntCategory = 14;
            break;
          }
          case R.id.category_15: {
            mIntCategory = 15;
            break;
          }
          case R.id.category_16: {
            mIntCategory = 16;
            break;
          }
          case R.id.category_17: {
            mIntCategory = 17;
            break;
          }
          case R.id.category_18: {
            mIntCategory = 18;
            break;
          }
          case R.id.category_19: {
            mIntCategory = 19;
            break;
          }
          case R.id.category_20: {
            mIntCategory = 20;
            break;
          }
          case R.id.category_21: {
            mIntCategory = 21;
            break;
          }
          case R.id.category_22: {
            mIntCategory = 22;
            break;
          }
          case R.id.category_23: {
            mIntCategory = 23;
            break;
          }
          case R.id.category_24: {
            mIntCategory = 24;
            break;
          }
          case R.id.category_25: {
            mIntCategory = 25;
            break;
          }
        }
      }
    });

    // mPresenterProfile.request(getGitHubApiModule(), bindUntilEvent(FragmentEvent.PAUSE));
    // mPresenterProfile.queryCategory(getActivity());
  }

  @Override public void onDestroy() {
    mPresenterProfile.detachView();
    super.onDestroy();
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @OnClick(R.id.search) public void onViewClicked() {
    XDao xDao = XDao.getInstance(getActivity().getApplicationContext());
    ProductDao productDao = xDao.getDaoSession().getProductDao();
    QueryBuilder queryBuilder = productDao.queryBuilder();
    queryBuilder.where(ProductDao.Properties.Category.eq(mIntCategory),
        queryBuilder.or(
            ProductDao.Properties.ReportCode.like(getStrKeyword()),
            ProductDao.Properties.ProductName.like(getStrKeyword()),
            ProductDao.Properties.ProducerName.like(getStrKeyword()),
            ProductDao.Properties.ProducerAddress.like(getStrKeyword()),
            ProductDao.Properties.Brand.like(getStrKeyword()),
            ProductDao.Properties.Type.like(getStrKeyword()),
            ProductDao.Properties.ProducerArea.like(getStrKeyword()),
            ProductDao.Properties.ThirdPartPlatform.like(getStrKeyword()),
            ProductDao.Properties.OnlineSellerWebsite.like(getStrKeyword()),
            ProductDao.Properties.Seller.like(getStrKeyword()),
            ProductDao.Properties.SellerAddress.like(getStrKeyword()),
            ProductDao.Properties.UnqualifiedItem.like(getStrKeyword()),
            ProductDao.Properties.Judge.like(getStrKeyword()),
            ProductDao.Properties.Dealing.like(getStrKeyword())
            ));
    List<Product> result = queryBuilder.list();
  }

  private String getStrKeyword() {
    return String.format("%%%s%%", mStrKeyword);
  }
}
