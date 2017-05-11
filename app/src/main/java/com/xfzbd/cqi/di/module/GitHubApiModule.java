package com.xfzbd.cqi.di.module;

import com.xfzbd.cqi.BuildConfig;
import com.xfzbd.cqi.api.GitHubApi;
import com.xfzbd.cqi.common.Constants;
import dagger.Module;
import dagger.Provides;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * XGitHub
 * com.hayukleung.xgithub
 * GitHubApiModule.java
 *
 * by hayukleung
 * at 2017-03-31 10:28
 */

@Module public class GitHubApiModule {

  @Inject public GitHubApiModule() {
  }

  @Provides public GitHubApi providesGitHubApi() {

    // OkHttpClient
    OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS);
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.interceptors().add(loggingInterceptor);
    }
    TrustAnyTrustManager trustAnyTrustManager = new TrustAnyTrustManager();
    SSLContext sc = null;
    try {
      sc = SSLContext.getInstance("SSL");
      sc.init(null, new TrustManager[] { trustAnyTrustManager }, new SecureRandom());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }
    builder.sslSocketFactory(sc.getSocketFactory(), trustAnyTrustManager);
    builder.hostnameVerifier(new TrustGitHubHostnameVerifier());
    OkHttpClient okHttpClient = builder.build();

    // Retrofit
    Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.HOST_API_GITHUB)
        // 添加Rx适配器
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        // 添加Gson转换器
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

    return retrofit.create(GitHubApi.class);
  }

  private static class TrustAnyTrustManager implements X509TrustManager {

    @Override public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    @Override public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    @Override public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[] {};
    }
  }

  /**
   * 信任 api.github.com
   */
  private static class TrustGitHubHostnameVerifier implements HostnameVerifier {

    @Override public boolean verify(String hostname, SSLSession session) {
      return Constants.HOST_API_GITHUB.contains(hostname);
    }
  }
}
