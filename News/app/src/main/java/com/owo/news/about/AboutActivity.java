package com.owo.news.about;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new AboutView(this));
  }
}
