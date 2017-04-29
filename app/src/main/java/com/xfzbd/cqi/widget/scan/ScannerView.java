package com.xfzbd.cqi.widget.scan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.zbar.ZBarScannerView;
import com.zbar.core.IViewFinder;

/**
 * 扫描控件
 */
public class ScannerView extends ZBarScannerView {
  public ScannerView(Context context) {
    super(context);
  }

  public ScannerView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
  }

  @Override protected IViewFinder createViewFinderView(Context context) {
    return new ScannerFinderView(context);
  }

  @Override public void resumeCameraPreview(ResultHandler resultHandler) {
    if (mViewFinderView != null) {
      ((View) mViewFinderView).setVisibility(resultHandler == null ? GONE : VISIBLE);
    }
    super.resumeCameraPreview(resultHandler);
  }
}
