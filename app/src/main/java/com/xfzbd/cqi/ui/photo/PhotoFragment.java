package com.xfzbd.cqi.ui.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.ui.SystemUiHider;
import com.xfzbd.cqi.ui.TranslucentStatusCompat;
import com.xfzbd.cqi.ui.XFragment;
import java.util.List;

/**
 * 图片展示页面
 */
public class PhotoFragment extends XFragment {
  public static final String URLS = "urls";
  /**
   * 是否为头像
   */
  public static final String IsPortrait = "isPortrait";
  public static final String SMALL_URLS = "smallurls";
  public static final String POSITION = "position";
  /**
   * 是否是自己的头像
   */
  public static final String IsMyself = "isMyself";
  private static final int REQUEST_CODE_SELECT_IMAGE = 101;
  @BindView(R.id.pager) ViewPager mPager;
  //@Bind(R.id.animation)
  //ImageView animationView;
  @BindView(R.id.position_layout) View currentViewPositionLayout;
  @BindView(R.id.position) TextView position;
  @BindView(R.id.save_button) LinearLayout mSaveButton;
  @BindView(R.id.sum) TextView sum;
  @BindView(R.id.footer) View mFooter;
  private List<String> mUrls;
  private List<String> mSmallUrls;
  private int mPoisition;
  private boolean isPortrait;
  private SystemUiHider mSystemUiHider;
  private int mShortAnimTime;
  private boolean isMyself;

  public static PhotoFragment newInstance(Bundle extras) {
    PhotoFragment fragment = new PhotoFragment();
    fragment.setArguments(extras);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TranslucentStatusCompat.requestTranslucentStatus(getActivity());
    Bundle bundle = getArguments();
    mUrls = bundle.getStringArrayList(URLS);
    mSmallUrls = bundle.getStringArrayList(SMALL_URLS);
    mPoisition = bundle.getInt(POSITION);
    isPortrait = bundle.getBoolean(IsPortrait, false);
    isMyself = bundle.getBoolean(IsMyself, false);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mSystemUiHider = null;
  }

  // @Override public void showWriteStorage() {
  // SettingDialogs.savePictureDialog(getActivity(), mUrls.get(mPager.getCurrentItem()));
  // }

  // @Override protected View onCreateContentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
  // return inflater.inflate(R.layout.content_photo, parent, false);
  // }

  @Override protected int getContentView() {
    return R.layout.content_photo;
  }

  @Override protected View.OnClickListener getRetryListener() {
    return null;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // requestHasStatusBar();
    // requestHeadBarOverlay(true);
    getStatusBar().setBackgroundColor(getResources().getColor(android.R.color.black));
    getToolbar().setBackgroundColor(getResources().getColor(android.R.color.black));
    // getToolBarShadow().setVisibility(View.GONE);

    mSystemUiHider = SystemUiHider.getInstance(getActivity(), SystemUiHider.FLAG_HIDE_NAVIGATION);
    mSystemUiHider.setup();
    mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
      @Override public void onVisibilityChange(boolean visible) {
        if (mShortAnimTime == 0) {
          mShortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        }
        // ObjectAnimator.ofFloat(getPreviewContainer(), "translationY", visible ? 0 : -getHeadBarHeight()).setDuration(mShortAnimTime).start();
        // ObjectAnimator.ofFloat(mFooter, "translationY", visible ? 0 : mFooter.getHeight()).setDuration(mShortAnimTime).start();
      }
    });
    getLayoutInflater(savedInstanceState).inflate(R.layout.content_photo_header, getToolbar(),
        true);

    getToolbar().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });

    mSaveButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        // 相当于调用这句,只是加了权限判断 SettingDialogs.savePictureDialog(getActivity(), mUrls.get(mPager.getCurrentItem()));
        // showWriteStorageWithCheck();
      }
    });

    sum.setText(mUrls.size() + "");
    currentViewPositionLayout.setVisibility(mUrls.size() > 1 ? View.VISIBLE : View.GONE);

    PhotoAdapter adapter =
        new PhotoAdapter(getChildFragmentManager(), mUrls, mSmallUrls, isPortrait, mSystemUiHider);
    mPager.setAdapter(adapter);
    mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int i, float v, int i2) {

      }

      @Override public void onPageSelected(int i) {
        position.setText(String.valueOf(i + 1));
        String url = mUrls.get(mPager.getCurrentItem());
        if (TextUtils.isEmpty(url)) {
          mSaveButton.setVisibility(View.GONE);
        } else {
          mSaveButton.setVisibility(View.GONE);
        }
      }

      @Override public void onPageScrollStateChanged(int i) {

      }
    });
    mPager.setCurrentItem(mPoisition);
    String url = mUrls.get(mPoisition);
    if (TextUtils.isEmpty(url)) {
      mSaveButton.setVisibility(View.GONE);
    } else {
      mSaveButton.setVisibility(View.GONE);
    }
  }

  @Override public boolean onBackPressed() {
    getActivity().finish();
    getActivity().overridePendingTransition(0, R.anim.zoom_exit);
    return super.onBackPressed();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case REQUEST_CODE_SELECT_IMAGE:
        if (data == null) return;
        // Resource resource = (Resource) data.getExtras().getSerializable(MediaSelectFragment.EXTRA_RESULT);
        // Intent intent = new Intent();
        // intent.putExtra(MediaSelectFragment.EXTRA_RESULT, resource);
        // getActivity().setResult(Activity.RESULT_OK, intent);
        // getActivity().finish();
        break;
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void showContent(Object data) {

  }

  public static class PhotoAdapter extends FragmentPagerAdapter {
    private final SystemUiHider mSystemUiHider;
    private boolean isPortrait;
    private List<String> mUrls;
    private List<String> mSmallUrls;

    public PhotoAdapter(FragmentManager fm, List<String> urls, boolean isPortrait,
        SystemUiHider mSystemUiHider) {
      super(fm);
      mUrls = urls;
      this.isPortrait = isPortrait;
      this.mSystemUiHider = mSystemUiHider;
    }

    public PhotoAdapter(FragmentManager fm, List<String> urls, List<String> smallurls,
        boolean isPortrait, SystemUiHider mSystemUiHider) {
      super(fm);
      mUrls = urls;
      mSmallUrls = smallurls;
      this.isPortrait = isPortrait;
      this.mSystemUiHider = mSystemUiHider;
    }

    @Override public int getCount() {
      return mUrls == null ? 0 : mUrls.size();
    }

    @Override public Fragment getItem(int position) {
      // if (mSmallUrls == null || mSmallUrls.size() == 0) {
      return PictureFragment.newInstance("", mUrls.get(position), null, false, isPortrait,
          mSystemUiHider);
      // } else {
      // return PictureFragment.newInstance("", mUrls.get(position), mSmallUrls.get(position), null, false, isPortrait, mSystemUiHider);
      // }
      // return null;
    }
  }
}
