package com.gdgs.cqi.view.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.gdgs.cqi.R;
import com.gdgs.cqi.database.Product;
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

  private List<Product> mProductList;

  public ResultAdapter(List<Product> productList) {
    mProductList = productList;
  }

  @Override public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ResultHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false));
  }

  @Override public void onBindViewHolder(ResultHolder holder, int position) {
    Product product = mProductList.get(position);
    holder.productName.setText(product.getProductName());
    holder.producerName.setText(product.getProducerName());
    holder.brand.setText(product.getBrand());
  }

  @Override public int getItemCount() {
    return null == mProductList ? 0 : mProductList.size();
  }
}
