package com.xfzbd.cqi.view.welcome;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.xfzbd.cqi.App;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.wrapper.XDao;
import com.xfzbd.cqi.common.wrapper.XLog;
import com.xfzbd.cqi.database.ProductDao;
import com.xfzbd.cqi.view.Activities;
import com.xfzbd.cqi.view.FullScreenActivity;
import com.xfzbd.cqi.view.result.ResultFragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.welcome
 * WelcomeActivity.java
 *
 * by hayukleung
 * at 2017-04-03 15:03
 */

public class WelcomeActivity extends FullScreenActivity {

  private static final int SIZE = 1198;

  private Handler mHandler;
  private ProgressBar mProgressBar;
  private TextView mPercentage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mHandler = new WelcomeHandler(this);

    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.xp12_0);
    TextView name = new TextView(this);
    name.setText(R.string.app_name);
    name.setTextColor(Color.BLACK);
    name.setGravity(Gravity.CENTER);
    name.setTextSize(getResources().getDimensionPixelSize(R.dimen.xp4_0));
    name.setBackground(null);
    TextPaint textPaint = name.getPaint();
    textPaint.setFakeBoldText(true);
    mContent.addView(name, layoutParams);

    layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    layoutParams.bottomMargin = 0;
    mProgressBar = new ProgressBar(this);
    mProgressBar.setMax(SIZE);
    mProgressBar.setVisibility(View.GONE);
    mContent.addView(mProgressBar, layoutParams);

    layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.xp14_0);
    layoutParams.bottomMargin = 0;
    mPercentage = new TextView(this);
    mPercentage.setTextColor(Color.BLACK);
    mPercentage.setGravity(Gravity.CENTER);
    mPercentage.setTextSize(getResources().getDimensionPixelSize(R.dimen.xp3_0));
    mPercentage.setBackground(null);
    textPaint = mPercentage.getPaint();
    textPaint.setFakeBoldText(true);
    mPercentage.setVisibility(View.GONE);

    mContent.addView(mPercentage, layoutParams);


    new Thread(new Runnable() {
      @Override public void run() {
        XDao xDao = XDao.getInstance(WelcomeActivity.this.getApplicationContext());
        ProductDao productDao = xDao.getDaoSession().getProductDao();
        if (SIZE == productDao.count()) {
          mHandler.sendEmptyMessageDelayed(0, 1000);
          return;
        }

        productDao.deleteAll();

        SQLiteDatabase database = xDao.getDatabase();

        Reader reader = null;
        try {
          reader = new InputStreamReader(
              App.getInstance(WelcomeActivity.this.getApplicationContext()).getAssets().open("product.sql"));
        } catch (IOException e) {
          e.printStackTrace();
        }
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(reader);
        int count = 0;
        String line;
        try {
          while ((line = br.readLine()) != null) {
            if (line.startsWith("insert")) {
              database.execSQL(line);
              XLog.e(++count + " lines inserted.");
              Message message = new Message();
              message.what = 1;
              message.arg1 = count;
              mHandler.sendMessage(message);
            }
          }
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          mHandler.sendEmptyMessage(0);
        }
      }
    }).start();
  }

  @Override public void onBackPressed() {
  }

  private static class WelcomeHandler extends Handler {
    private WeakReference<WelcomeActivity> mWelcomeActivityWeakReference;

    WelcomeHandler(WelcomeActivity welcomeActivity) {
      mWelcomeActivityWeakReference = new WeakReference<>(welcomeActivity);
    }

    @Override public void handleMessage(Message msg) {
      WelcomeActivity welcomeActivity = mWelcomeActivityWeakReference.get();
      if (null != welcomeActivity) {
        switch (msg.what) {
          case 0: {
            welcomeActivity.mProgressBar.setVisibility(View.GONE);
            welcomeActivity.mPercentage.setVisibility(View.GONE);
            Activities.startActivity(welcomeActivity, ResultFragment.class);
            welcomeActivity.finish();
            break;
          }
          default: {
            welcomeActivity.mProgressBar.setProgress(msg.arg1);
            welcomeActivity.mProgressBar.setVisibility(View.VISIBLE);
            welcomeActivity.mPercentage.setText(
                String.format(Locale.CHINA, "%.2f%%", ((float) msg.arg1) / (float) SIZE * 100f));
            welcomeActivity.mPercentage.setVisibility(View.VISIBLE);
            break;
          }
        }
      }
    }
  }
}
