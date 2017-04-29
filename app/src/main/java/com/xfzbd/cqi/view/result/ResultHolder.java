package com.xfzbd.cqi.view.result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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
  @BindView(R.id.product_name) TextView productName;
  @BindView(R.id.producer_name) TextView producerName;
  @BindView(R.id.brand) TextView brand;

  public ResultHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
