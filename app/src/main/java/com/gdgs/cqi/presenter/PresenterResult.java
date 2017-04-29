package com.gdgs.cqi.presenter;

import android.content.Context;
import com.gdgs.cqi.common.wrapper.XDao;
import com.gdgs.cqi.common.wrapper.XLog;
import com.gdgs.cqi.contract.ContractResult;
import com.gdgs.cqi.database.ProductDao;
import com.gdgs.cqi.model.Result;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * XGitHub
 * com.hayukleung.xgithub.presenter.main
 * PresenterMain.java
 *
 * by hayukleung
 * at 2017-04-02 17:03
 */

public class PresenterResult extends ContractResult.IPresenterResult {

  @Inject public PresenterResult() {
  }

  public void query(final Context context, final String strKeyword, final int intCategory,
      ObservableTransformer lifecycleTransformer) {
    Observable.create(new ObservableOnSubscribe<Result>() {

      @Override public void subscribe(ObservableEmitter<Result> e) throws Exception {

        XDao xDao = XDao.getInstance(context.getApplicationContext());
        ProductDao productDao = xDao.getDaoSession().getProductDao();
        QueryBuilder queryBuilder = productDao.queryBuilder();
        String strKeywordLike = getStrKeyword(strKeyword);
        if (0 == intCategory) {
          queryBuilder.where(queryBuilder.or(ProductDao.Properties.ReportCode.like(strKeywordLike),
              ProductDao.Properties.ProductName.like(strKeywordLike),
              ProductDao.Properties.ProducerName.like(strKeywordLike),
              ProductDao.Properties.ProducerAddress.like(strKeywordLike),
              ProductDao.Properties.Brand.like(strKeywordLike),
              ProductDao.Properties.Type.like(strKeywordLike),
              ProductDao.Properties.ProducerArea.like(strKeywordLike),
              ProductDao.Properties.ThirdPartPlatform.like(strKeywordLike),
              ProductDao.Properties.OnlineSellerWebsite.like(strKeywordLike),
              ProductDao.Properties.Seller.like(strKeywordLike),
              ProductDao.Properties.SellerAddress.like(strKeywordLike),
              ProductDao.Properties.UnqualifiedItem.like(strKeywordLike),
              ProductDao.Properties.Judge.like(strKeywordLike),
              ProductDao.Properties.Dealing.like(strKeywordLike)));
        } else {
          queryBuilder.where(ProductDao.Properties.Category.eq(intCategory),
              queryBuilder.or(ProductDao.Properties.ReportCode.like(strKeywordLike),
                  ProductDao.Properties.ProductName.like(strKeywordLike),
                  ProductDao.Properties.ProducerName.like(strKeywordLike),
                  ProductDao.Properties.ProducerAddress.like(strKeywordLike),
                  ProductDao.Properties.Brand.like(strKeywordLike),
                  ProductDao.Properties.Type.like(strKeywordLike),
                  ProductDao.Properties.ProducerArea.like(strKeywordLike),
                  ProductDao.Properties.ThirdPartPlatform.like(strKeywordLike),
                  ProductDao.Properties.OnlineSellerWebsite.like(strKeywordLike),
                  ProductDao.Properties.Seller.like(strKeywordLike),
                  ProductDao.Properties.SellerAddress.like(strKeywordLike),
                  ProductDao.Properties.UnqualifiedItem.like(strKeywordLike),
                  ProductDao.Properties.Judge.like(strKeywordLike),
                  ProductDao.Properties.Dealing.like(strKeywordLike)));
        }
        Result result = new Result();
        result.setProductList(queryBuilder.list());
        e.onNext(result);
        e.onComplete();
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleTransformer)
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) throws Exception {
            getMVPView().showLoading();
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() throws Exception {
            getMVPView().dismissLoading();
          }
        })
        .subscribeWith(new DisposableObserver<Result>() {
          @Override public void onNext(Result value) {
            checkViewAttached();
            getMVPView().showContent(value);
          }

          @Override public void onError(Throwable e) {
            XLog.e(e);
            getMVPView().dismissLoading();
          }

          @Override public void onComplete() {
            getMVPView().dismissLoading();
          }
        });
  }

  private String getStrKeyword(String strKeyword) {
    // char[] words = strKeyword.toCharArray();
    // String like = "";
    // for (char word : words) {
    // like += String.format("%%%s%%", word);
    // }
    return String.format("%%%s%%", strKeyword);
  }
}
