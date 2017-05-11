package com.xfzbd.cqi.view.result;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.CommonUtils;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.view.Activities;
import com.xfzbd.cqi.view.detail.DetailFragment;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.xfzbd.cqi.common.wrapper.XImage.load;
import static com.xfzbd.cqi.common.wrapper.XImage.url1;

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
    if (0 != mResultFragment.mIntCategory) {
      holder.colCategory.setVisibility(View.GONE);
    } else {
      holder.colCategory.setVisibility(View.VISIBLE);
      holder.colCategory.setText(Html.fromHtml(
          CommonUtils.color(mResultFragment.getActivity(), product.getCategoryName(),
              mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    }
    holder.colProducerName.setText(Html.fromHtml(
        CommonUtils.color(mResultFragment.getActivity(), product.getProducerName(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    holder.colBrand.setText(Html.fromHtml(
        CommonUtils.color(mResultFragment.getActivity(), product.getBrand(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    holder.colType.setText(Html.fromHtml(
        CommonUtils.color(mResultFragment.getActivity(), product.getType(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    holder.colProducerArea.setText(Html.fromHtml(
        CommonUtils.color(mResultFragment.getActivity(), product.getProducerArea(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));

    load(mResultFragment.getActivity(), url1(product.getReportCode()), holder.colImage,
        R.drawable.ic_image_stub_list, new CropCircleTransformation(mResultFragment.getActivity()));
  }

  @Override public int getItemCount() {
    return null == mProductList ? 0 : mProductList.size();
  }
}
