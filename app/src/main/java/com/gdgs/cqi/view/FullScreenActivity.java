package com.gdgs.cqi.view;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class FullScreenActivity extends XActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
  }
}