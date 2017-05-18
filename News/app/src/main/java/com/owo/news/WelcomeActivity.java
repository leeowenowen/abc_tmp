package com.owo.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;

public class WelcomeActivity extends Activity {
  static {
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
      }
    }, 1000);
  }
}
