package com.gdgs.cqi.presenter;

import android.content.Context;
import com.gdgs.cqi.common.wrapper.XDao;
import com.gdgs.cqi.common.wrapper.XLog;
import com.gdgs.cqi.contract.ContractProfile;
import com.gdgs.cqi.database.Product;
import com.gdgs.cqi.database.ProductDao;
import com.gdgs.cqi.database.ProductDaoExtend;
import com.gdgs.cqi.di.module.GitHubApiModule;
import com.gdgs.cqi.model.GitHub;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
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

public class PresenterProfile extends ContractProfile.IPresenterProfile {

  @Inject public PresenterProfile() {
  }

  public void request(GitHubApiModule gitHubApiModule, ObservableTransformer lifecycleTransformer) {

    // mCompositeDisposable.add(
    gitHubApiModule.providesGitHubApi()
        .api("hayukleung")
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
        .subscribeWith(new DisposableObserver<GitHub>() {
          @Override public void onNext(GitHub value) {
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
    // );
  }

  public void queryCategory(Context context) {
    XDao xDao = XDao.getInstance(context.getApplicationContext());
    List<Integer> categoryList = ProductDaoExtend.queryCategory(xDao.getDatabase());
  }

  public void query(Context context, String strKeyword, int intCategory) {
    XDao xDao = XDao.getInstance(context.getApplicationContext());
    ProductDao productDao = xDao.getDaoSession().getProductDao();
    QueryBuilder queryBuilder = productDao.queryBuilder();
    String strKeywordLike = getStrKeyword(strKeyword);
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
    List<Product> result = queryBuilder.list();
  }

  private String getStrKeyword(String strKeyword) {
    return String.format("%%%s%%", strKeyword);
  }
}
