package com.xfzbd.cqi.presenter;

import android.content.Context;
import com.xfzbd.cqi.common.wrapper.XDao;
import com.xfzbd.cqi.common.wrapper.XLog;
import com.xfzbd.cqi.contract.ContractResult;
import com.xfzbd.cqi.database.ProductDao;
import com.xfzbd.cqi.model.Result;
import com.xfzbd.cqi.ui.result.ResultView;
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
import org.greenrobot.greendao.query.WhereCondition;

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

  /**
   * @param context
   * @param strKeyword
   * @param intCategory
   * @param lifecycleTransformer
   */
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
              ProductDao.Properties.CategoryName.like(strKeywordLike),
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
                  ProductDao.Properties.CategoryName.like(strKeywordLike),
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
            // getMVPView().showLoading();
            ((ResultView) getMVPView()).showQueryLoading();
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() throws Exception {
            // getMVPView().dismissLoading();
            ((ResultView) getMVPView()).stopQueryLoading();
          }
        })
        .subscribeWith(new DisposableObserver<Result>() {
          @Override public void onNext(Result value) {
            checkViewAttached();
            getMVPView().showContent(value);
          }

          @Override public void onError(Throwable e) {
            XLog.e(e);
            // getMVPView().dismissLoading();
            ((ResultView) getMVPView()).stopQueryLoading();
          }

          @Override public void onComplete() {
            // getMVPView().dismissLoading();
            ((ResultView) getMVPView()).stopQueryLoading();
          }
        });
  }

  private String getStrKeyword(String strKeyword) {
    return String.format("%%%s%%", strKeyword);
  }

  /**
   * TODO
   *
   * @param keyword
   * @return
   */
  private WhereCondition[] genWhereConditionArray(String keyword) {
    int size = keyword.length();
    WhereCondition[] whereConditions = new WhereCondition[size * 15];

    for (int i = 0; i < size; i++) {
      whereConditions[i * 15 + 0] =
          ProductDao.Properties.ReportCode.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 1] =
          ProductDao.Properties.CategoryName.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 2] =
          ProductDao.Properties.ProductName.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 3] =
          ProductDao.Properties.ProducerName.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 4] =
          ProductDao.Properties.ProducerAddress.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 5] =
          ProductDao.Properties.Brand.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 6] =
          ProductDao.Properties.Type.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 7] =
          ProductDao.Properties.ProducerArea.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 8] =
          ProductDao.Properties.ThirdPartPlatform.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 9] = ProductDao.Properties.OnlineSellerWebsite.like(
          getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 10] =
          ProductDao.Properties.Seller.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 11] =
          ProductDao.Properties.SellerAddress.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 12] =
          ProductDao.Properties.UnqualifiedItem.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 13] =
          ProductDao.Properties.Judge.like(getStrKeyword(keyword.substring(i, i + 1)));
      whereConditions[i * 15 + 14] =
          ProductDao.Properties.Dealing.like(getStrKeyword(keyword.substring(i, i + 1)));
    }

    return whereConditions;
  }
}
