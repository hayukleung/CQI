package com.xfzbd.cqi.ui.welcome;

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
import android.widget.TextView;

import com.xfzbd.cqi.App;
import com.xfzbd.cqi.BuildConfig;
import com.xfzbd.cqi.R;
import com.xfzbd.cqi.common.wrapper.XDao;
import com.xfzbd.cqi.common.wrapper.XLog;
import com.xfzbd.cqi.database.ProductDao;
import com.xfzbd.cqi.ui.Activities;
import com.xfzbd.cqi.ui.FullScreenActivity;
import com.xfzbd.cqi.ui.result.ResultFragment;
import com.xfzbd.cqi.widget.PercentageCircleView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;

/**
 * 欢迎页
 */
public class WelcomeActivity extends FullScreenActivity {

  private static final int SIZE = 1352;

  private Handler mHandler;
  private PercentageCircleView mProgressBar;
  // private TextView mPercentage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mHandler = new WelcomeHandler(this);

    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    // layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.xp12_0);
    layoutParams.bottomMargin = 0;
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
    mProgressBar = new PercentageCircleView(this);
    mProgressBar.setTotal(SIZE);
    mProgressBar.setVisibility(View.GONE);
    mContent.addView(mProgressBar, layoutParams);

    layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    // layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.xp14_0);
    layoutParams.topMargin = 0;
    layoutParams.bottomMargin = 0;
    // mPercentage = new TextView(this);
    // mPercentage.setTextColor(Color.BLACK);
    // mPercentage.setGravity(Gravity.CENTER);
    // mPercentage.setTextSize(getResources().getDimensionPixelSize(R.dimen.xp3_0));
    // mPercentage.setBackground(null);
    // textPaint = mPercentage.getPaint();
    // textPaint.setFakeBoldText(true);
    // mPercentage.setVisibility(View.GONE);
    // mContent.addView(mPercentage, layoutParams);

    new Thread(new Runnable() {
      @Override public void run() {
        XDao xDao = XDao.getInstance(WelcomeActivity.this.getApplicationContext());
        ProductDao productDao = xDao.getDaoSession().getProductDao();
        if (SIZE == productDao.count()) {
          // if (false) {
          mHandler.sendEmptyMessageDelayed(0, 1000);
          return;
        }

        productDao.deleteAll();

        SQLiteDatabase database = xDao.getDatabase();

        Reader reader = null;
        try {
          reader = new InputStreamReader(
              App.getInstance(WelcomeActivity.this.getApplicationContext())
                  .getAssets()
                  .open("product.sql"));
        } catch (IOException e) {
          e.printStackTrace();
        }
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(reader);
        int count = 0;
        int percentage = 0;
        String line;
        try {
          while ((line = br.readLine()) != null) {
            if (line.startsWith("insert")) {
              database.execSQL(line);
              count++;
              if (BuildConfig.DEBUG) {
                XLog.e(count + " lines inserted.");
              }
              int tempPercentage = (int) ((float) count * 10000f / (float) SIZE);
              if (tempPercentage > percentage || 10000 == tempPercentage) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                mHandler.sendMessage(message);
              }
              percentage = tempPercentage;
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
            // welcomeActivity.mPercentage.setVisibility(View.GONE);
            Activities.startActivity(welcomeActivity, ResultFragment.class);
            welcomeActivity.finish();
            break;
          }
          default: {
            welcomeActivity.mProgressBar.setCurrent(msg.arg1);
            welcomeActivity.mProgressBar.postInvalidate();
            welcomeActivity.mProgressBar.setVisibility(View.VISIBLE);
            // welcomeActivity.mPercentage.setText(String.format(Locale.CHINA, "%.2f%%", ((float) msg.arg1) / (float) SIZE * 100f));
            // welcomeActivity.mPercentage.setVisibility(View.VISIBLE);
            break;
          }
        }
      }
    }
  }
}
