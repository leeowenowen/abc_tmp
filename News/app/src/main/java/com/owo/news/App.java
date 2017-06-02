package com.owo.news;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.owo.common.utils.UIUtils;
import com.owo.news.theme.Blue;
import com.owo.news.theme.Theme;

/**
 * Created by wangli on 17-5-4.
 */

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    MobileAds.initialize(this, "ca-app-pub-7286925161756855~1743491325");
    Theme.instance().setCurrentConfig(new Blue());
    UIUtils.init(this);
  }
}
