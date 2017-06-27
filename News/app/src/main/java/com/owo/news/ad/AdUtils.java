package com.owo.news.ad;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by wangli on 17-6-28.
 */

public class AdUtils {
  public static void loadInterstitial(Context context) {
    InterstitialAd ad = new InterstitialAd(context);
    ad.setAdUnitId("ca-app-pub-7286925161756855/8882599720");
    AdRequest adRequest = new AdRequest.Builder().build();
    ad.loadAd(adRequest);
  }

  public static View loadBanner(Context context) {
    AdView adView = new AdView(context);
    adView.setAdSize(AdSize.SMART_BANNER);
    adView.setAdUnitId("ca-app-pub-7286925161756855/7405866525");
    adView.setAdListener(new AdListener() {
      @Override
      public void onAdClosed() {
        super.onAdClosed();
        
      }

      @Override
      public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
      }

      @Override
      public void onAdLeftApplication() {
        super.onAdLeftApplication();
      }

      @Override
      public void onAdOpened() {
        super.onAdOpened();
      }

      @Override
      public void onAdLoaded() {
        super.onAdLoaded();
      }
    });
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
    return adView;
  }
}
