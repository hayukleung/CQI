package com.gdgs.cqi.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gdgs.cqi.R;
import com.gdgs.cqi.view.result.FilterFragment;
import com.hayukleung.absolutescreenmatch.Screen;

import static com.gdgs.cqi.widget.TabManager.Animate.IN;
import static com.gdgs.cqi.widget.TabManager.Animate.OUT;

public class FilterLayout extends LinearLayout implements TabHost.OnTabChangeListener {

  public static final String TAG = "filter";

  @BindView(R.id.category) TextView mCategory;
  @BindView(R.id.layout_tag) LinearLayout mLayoutTag;

  private TabManager mFilterManager;
  private OnSelectedListener mSelectedListener;

  public FilterLayout(Context context) {
    super(context);
    init();
  }

  private void init() {
    View view = inflate(getContext(), R.layout.layout_filter_header, this);
    ButterKnife.bind(this, view);
    // setBackgroundResource(R.drawable.bg_toolbar);
    // setDividerDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, getContext().getTheme()));
    setDividerPadding(Screen.getInstance(getContext()).dp2px(10));
    setShowDividers(SHOW_DIVIDER_MIDDLE);
  }

  public FilterLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FilterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public FilterLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public void setOnSelectedListener(OnSelectedListener listener) {
    mSelectedListener = listener;
  }

  public void init(FragmentManager fm) {
    mFilterManager = new TabManager(getContext(), fm, R.id.filter_container);
    mFilterManager.addTab(TAG, FilterFragment.class, R.anim.slide_in_top_filter_layout,
        R.anim.slide_out_top_filter_layout, null);
    mFilterManager.setOnTabChangedListener(this);
  }

  @OnClick({ R.id.layout_tag }) public void onClick(View v) {
    switch (v.getId()) {
      case R.id.layout_tag: {
        updateFilter(v.getId(), TAG);
        break;
      }
    }
  }

  private void updateFilter(int id, String tag) {
    if (id == R.id.layout_tag && TAG.equals(mFilterManager.getCurrentTag())) {
      mFilterManager.detach(OUT);
    } else {
      mFilterManager.changeTab(tag, IN);
    }
  }

  public void setName(String name) {
    mCategory.setText(name);
  }

  public void setVisibility(String tag, int visibility) {
    if (TAG.equals(tag)) {
      mLayoutTag.setVisibility(visibility);
    }
  }

  @Override public Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SavedState ss = new SavedState(superState);
    if (mFilterManager != null) ss.mCurrentTag = mFilterManager.getCurrentTag();
    return ss;
  }

  @Override public void onRestoreInstanceState(Parcelable state) {
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    if (ss.mCurrentTag != null) {
      mFilterManager.restoreState(ss.mCurrentTag);
      dismissCurrentFilter();
    }
  }

  public boolean dismissCurrentFilter() {
    if (mFilterManager.getCurrentTag() != null) {
      mFilterManager.detach(OUT);
      return true;
    }
    return false;
  }

  @Override public void onTabChanged(String tabId) {
    setSelected(tabId);
  }

  private void setSelected(String tag) {
    mLayoutTag.setSelected(TAG.equals(tag));
    if (tag != null && mSelectedListener != null) {
      mSelectedListener.onSelected(tag);
    }
  }

  public interface OnSelectedListener {
    void onSelected(String tag);
  }

  public static class SavedState extends BaseSavedState {
    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
      @Override public SavedState createFromParcel(Parcel source) {
        return new SavedState(source);
      }

      @Override public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };
    String mCurrentTag;

    public SavedState(Parcel source) {
      super(source);
      mCurrentTag = source.readString();
    }

    public SavedState(Parcelable superState) {
      super(superState);
    }

    @Override public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeString(mCurrentTag);
    }
  }
}
