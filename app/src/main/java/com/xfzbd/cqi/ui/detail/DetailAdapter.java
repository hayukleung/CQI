package com.xfzbd.cqi.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.database.Product;
import com.xfzbd.cqi.ui.Activities;
import com.xfzbd.cqi.ui.photo.PhotoFragment;
import java.util.ArrayList;

import static com.xfzbd.cqi.common.wrapper.XImage.load;
import static com.xfzbd.cqi.common.wrapper.XImage.url1;
import static com.xfzbd.cqi.common.wrapper.XImage.url2;
import static com.xfzbd.cqi.common.wrapper.XImage.url3;

/**
 * 详情数据适配器
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailHolder> {

  private DetailFragment mContext;
  private Product mProduct;

  public DetailAdapter(DetailFragment context, Product product) {
    mContext = context;
    mProduct = product;
  }

  public Product getProduct() {
    return mProduct;
  }

  @Override public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new DetailHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false),
        mProduct);
  }

  @Override public void onBindViewHolder(final DetailHolder holder, int position) {
    holder.colReportCode.setText(mProduct.getReportCode());
    holder.colProductName.setText(mProduct.getProductName());
    holder.colProducerName.setText(mProduct.getProducerName());
    holder.colProducerAddress.setText(mProduct.getProducerAddress());
    holder.colBrand.setText(mProduct.getBrand());
    holder.colType.setText(mProduct.getType());
    holder.colProducerArea.setText(mProduct.getProducerArea());
    holder.colThirdPartPlatform.setText(mProduct.getThirdPartPlatform());
    holder.colOnlineSeller.setText(mProduct.getOnlineSellerWebsite());
    holder.colSeller.setText(mProduct.getSeller());
    holder.colSellerAddress.setText(mProduct.getSellerAddress());
    holder.colUnqualifiedItem.setText(mProduct.getUnqualifiedItem());
    holder.colJudge.setText(mProduct.getJudge());
    holder.colDealing.setText(mProduct.getDealing());

    load(mContext.getActivity(), url1(mProduct.getReportCode()), holder.colImage1,
        R.drawable.ic_image_stub_detail);
    load(mContext.getActivity(), url2(mProduct.getReportCode()), holder.colImage2,
        R.drawable.ic_image_stub_detail);
    load(mContext.getActivity(), url3(mProduct.getReportCode()), holder.colImage3,
        R.drawable.ic_image_stub_detail);

    holder.colImage1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url1(mProduct.getReportCode()));
        urls.add(url2(mProduct.getReportCode()));
        urls.add(url3(mProduct.getReportCode()));
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PhotoFragment.URLS, urls);
        bundle.putInt(PhotoFragment.POSITION, 0);
        Activities.startActivity(mContext, PhotoFragment.class, bundle);
      }
    });

    holder.colImage2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url1(mProduct.getReportCode()));
        urls.add(url2(mProduct.getReportCode()));
        urls.add(url3(mProduct.getReportCode()));
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PhotoFragment.URLS, urls);
        bundle.putInt(PhotoFragment.POSITION, 1);
        Activities.startActivity(mContext, PhotoFragment.class, bundle);
      }
    });

    holder.colImage3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(url1(mProduct.getReportCode()));
        urls.add(url2(mProduct.getReportCode()));
        urls.add(url3(mProduct.getReportCode()));
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PhotoFragment.URLS, urls);
        bundle.putInt(PhotoFragment.POSITION, 2);
        Activities.startActivity(mContext, PhotoFragment.class, bundle);
      }
    });
  }

  @Override public int getItemCount() {
    return 1;
  }
}
