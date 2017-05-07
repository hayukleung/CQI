package com.xfzbd.cqi.view.result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xfzbd.cqi.R;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarHolder.java
 *
 * by hayukleung
 * at 2017-04-07 12:27
 */

public class ResultHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.root) View root;
  @BindView(R.id.col_category) TextView colCategory;
  @BindView(R.id.col_other) TextView colOther;
  @BindView(R.id.col_image) ImageView colImage;

  // @BindView(R.id.col_producer_name) TextView colProducerName;
  // @BindView(R.id.col_brand) TextView colBrand;
  // @BindView(R.id.col_type) TextView colType;
  // @BindView(R.id.col_producer_area) TextView colProducerArea;

  public ResultHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
