package com.owo.news.core.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class WebViewActivity extends Activity {
  private WebView mWebview;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mWebview = new WebView(this);
    // web setting
    WebSettings ws = mWebview.getSettings();
    setupWebSettings(ws);
    setContentView(mWebview);
    String url = getIntent().getStringExtra("url");
    if (TextUtils.isEmpty(url)) {
      finish();
    } else {
      mWebview.loadUrl(url);
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void setupWebSettings(WebSettings ws) {
    ws.setJavaScriptEnabled(true);
    ws.setGeolocationEnabled(true);
    ws.setBuiltInZoomControls(true);
    ws.setDisplayZoomControls(false);

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
      ws.setUseWideViewPort(true);
    }

    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
      ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    // file access
    {
      ws.setAllowFileAccess(true);
    }
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
      ws.setAllowFileAccessFromFileURLs(true);
      ws.setAllowUniversalAccessFromFileURLs(true);
    }

    // html cache
    ws.setCacheMode(WebSettings.LOAD_DEFAULT);
    ws.setDomStorageEnabled(true);
    ws.setAppCacheEnabled(true);

    // user agent
    //   ws.setUserAgentString(UserAgentHelper.browserUserAgent());
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if (mWebview.canGoBack()) {
      mWebview.goBack();
    } else {
      finish();
    }
  }
}
