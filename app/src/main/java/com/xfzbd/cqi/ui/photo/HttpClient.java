package com.xfzbd.cqi.ui.photo;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 匿名 SSL HttpClient
 */
public class HttpClient {
  public static OkHttpClient getDefaultHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.interceptors().add(createInterceptor(new DispatchingProgressListener()));
    try {
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
          new java.security.SecureRandom());
      builder.sslSocketFactory(sc.getSocketFactory());
      builder.hostnameVerifier(new TrustAnyHostnameVerifier());
    } catch (KeyManagementException ignored) {
    } catch (NoSuchAlgorithmException ignored) {
    }
    return builder.build();
  }

  private static Interceptor createInterceptor(
      final OkHttpProgressGlideModule.ResponseProgressListener listener) {
    return new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return response.newBuilder()
            .body(new OkHttpProgressResponseBody(request.url(), response.body(), listener))
            .build();
      }
    };
  }

  private static class TrustAnyTrustManager implements X509TrustManager {
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[] {};
    }
  }

  private static class TrustAnyHostnameVerifier implements HostnameVerifier {
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }
}
