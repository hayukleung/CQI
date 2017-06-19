package com.xfzbd.cqi.ui.result;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.ui.Activities;
import com.xfzbd.cqi.ui.detail.DetailFragment;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.text.Html.fromHtml;
import static com.xfzbd.cqi.common.CommonUtils.color;
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

  @Override public void onBindViewHolder(final ResultHolder holder, int position) {
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
      holder.colCategory.setText(
          fromHtml(color(mResultFragment.getActivity(), product.getCategoryName(),
              mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    }
    holder.colProducerName.setText(
        fromHtml(color(mResultFragment.getActivity(), product.getProducerName(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));
    holder.colBrand.setText(fromHtml(
        color(mResultFragment.getActivity(), product.getBrand(), mResultFragment.getStrKeyword(),
            R.color.colorKeyword)));
    holder.colType.setText(fromHtml(
        color(mResultFragment.getActivity(), product.getType(), mResultFragment.getStrKeyword(),
            R.color.colorKeyword)));
    holder.colProducerArea.setText(
        fromHtml(color(mResultFragment.getActivity(), product.getProducerArea(),
            mResultFragment.getStrKeyword(), R.color.colorKeyword)));

    load(mResultFragment.getActivity(), url1(product.getReportCode()), holder.colImage,
        R.drawable.ic_image_stub_list, new CropCircleTransformation(mResultFragment.getActivity()));
  }

  @Override public void onBindViewHolder(ResultHolder holder, int position, List<Object> payloads) {
    if (null != payloads && 0 < payloads.size()) {
      Product product = mProductList.get(holder.getAdapterPosition());
      Bundle bundle = (Bundle) payloads.get(0);
      product.setCategory(bundle.getInt("category"));
      product.setCategoryName(bundle.getString("categoryName"));
      product.setReportCode(bundle.getString("reportCode"));
      product.setProductName(bundle.getString("productName"));
      product.setProducerName(bundle.getString("producerName"));
      product.setProducerAddress(bundle.getString("producerAddress"));
      product.setBrand(bundle.getString("brand"));
      product.setType(bundle.getString("type"));
      product.setProducerArea(bundle.getString("producerArea"));
      product.setThirdPartPlatform(bundle.getString("thirdPartPlatform"));
      product.setOnlineSellerWebsite(bundle.getString("onlineSellerWebsite"));
      product.setSeller(bundle.getString("seller"));
      product.setSellerAddress(bundle.getString("sellerAddress"));
      product.setUnqualifiedItem(bundle.getString("unqualifiedItem"));
      product.setJudge(bundle.getString("judge"));
      product.setDealing(bundle.getString("dealing"));
    }
    this.onBindViewHolder(holder, position);
  }

  @Override public int getItemCount() {
    return null == mProductList ? 0 : mProductList.size();
  }
}
