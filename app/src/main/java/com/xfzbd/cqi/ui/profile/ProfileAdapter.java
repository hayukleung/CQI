package com.xfzbd.cqi.ui.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.model.Stub;
import java.util.ArrayList;
import java.util.List;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarAdapter.java
 *
 * by hayukleung
 * at 2017-04-07 12:26
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileHolder> {

  private List<Stub> mStubList = new ArrayList<>(20);

  public ProfileAdapter() {
    for (int i = 0; i < 20; i++) {
      mStubList.add(new Stub());
    }
  }

  @Override public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ProfileHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false));
  }

  @Override public void onBindViewHolder(ProfileHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return mStubList.size();
  }
}
