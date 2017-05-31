package com.owo.news;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by wangli on 17-5-4.
 */

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    MobileAds.initialize(this);

  }
}
