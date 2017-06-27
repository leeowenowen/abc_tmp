package com.owo.news.about;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.owo.common.utils.AppUtils;
import com.owo.news.ad.AdUtils;

/**
 * Created by wangli on 17-6-28.
 */

public class AboutView extends LinearLayout {
  private TextView mVersion;
  private TextView mAuthor;
  private TextView mEmail;
  private View mAdView;

  public AboutView(Context context) {
    super(context);
    init(context);
    loadAd();
  }

  private void init(Context context) {
    mVersion = new TextView(context);
    mAuthor = new TextView(context);
    mEmail = new TextView(context);
    mAdView = AdUtils.loadBanner(context);
    setOrientation(VERTICAL);
    LinearLayout content = new LinearLayout(getContext());
    content.setOrientation(VERTICAL);
    content.setGravity(Gravity.CENTER_HORIZONTAL);
    content.addView(mVersion);
    content.addView(mAuthor);
    content.addView(mEmail);
    addView(content, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
    addView(mAdView);
    mVersion.setText("Version:" + AppUtils.versionName(getContext()));
    mAuthor.setText("Author: OwenOranBa");
    mEmail.setText("Email: leeowenowen@gmail.com");
  }

  private void loadAd() {

  }
}
