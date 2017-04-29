package com.gdgs.cqi.view.result;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gdgs.cqi.R;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.view.Activities;
import com.gdgs.cqi.view.detail.DetailFragment;
import java.util.List;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.star
 * StarAdapter.java
 *
 * by hayukleung
 * at 2017-04-07 12:26
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {

  private ResultFragment mResultFragment;
  private List<Product> mProductList;

  public ResultAdapter(ResultFragment resultFragment, List<Product> productList) {
    mResultFragment = resultFragment;
    mProductList = productList;
  }

  @Override public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ResultHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false));
  }

  @Override public void onBindViewHolder(ResultHolder holder, int position) {
    final Product product = mProductList.get(position);
    holder.root.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Product.class.getSimpleName(), product);
        Activities.startActivity(mResultFragment, DetailFragment.class, bundle);
      }
    });
    holder.productName.setText(product.getProductName());
    holder.producerName.setText(product.getProducerName());
    holder.brand.setText(product.getBrand());
  }

  @Override public int getItemCount() {
    return null == mProductList ? 0 : mProductList.size();
  }
}
