package com.gdgs.cqi.view.welcome;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.gdgs.cqi.App;
import com.gdgs.cqi.R;
import com.gdgs.cqi.common.wrapper.XDao;
import com.gdgs.cqi.common.wrapper.XLog;
import com.gdgs.cqi.database.ProductDao;
import com.gdgs.cqi.view.Activities;
import com.gdgs.cqi.view.FullScreenActivity;
import com.gdgs.cqi.view.profile.ProfileFragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * XGitHub
 * com.hayukleung.xgithub.view.welcome
 * WelcomeActivity.java
 *
 * by hayukleung
 * at 2017-04-03 15:03
 */

public class WelcomeActivity extends FullScreenActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    layoutParams.gravity = Gravity.CENTER;
    ImageView background = new ImageView(this);
    background.setImageResource(R.drawable.bg_welcome);
    mContent.addView(background, layoutParams);

    new Thread(new Runnable() {
      @Override public void run() {
        XDao xDao = XDao.getInstance(WelcomeActivity.this.getApplicationContext());
        ProductDao productDao = xDao.getDaoSession().getProductDao();
        if (954 == productDao.count()) {
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
            }
          }
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

    CountDownTimer timer = new CountDownTimer(2 * 1000, 2 * 1000) {
      @Override public void onTick(long millisUntilFinished) {
      }

      @Override public void onFinish() {
        Activities.startActivity(WelcomeActivity.this, ProfileFragment.class);
        finish();
      }
    };
    timer.start();
  }
}
