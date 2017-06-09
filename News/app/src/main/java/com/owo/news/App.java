package com.owo.news;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.owo.common.utils.UIUtils;
import com.owo.news.theme.Black;
import com.owo.news.theme.Blue;
import com.owo.news.theme.Red;
import com.owo.news.theme.Theme;

/**
 * Created by wangli on 17-5-4.
 */

public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    MobileAds.initialize(this, "ca-app-pub-7286925161756855~1743491325");
    Theme.instance().setCurrentConfig(new Black());
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

      }

      @Override
      public void onActivityStarted(Activity activity) {

      }

      @Override
      public void onActivityResumed(Activity activity) {
        UIUtils.init(activity);
      }

      @Override
      public void onActivityPaused(Activity activity) {

      }

      @Override
      public void onActivityStopped(Activity activity) {

      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(Activity activity) {

      }
    });
  }
}
