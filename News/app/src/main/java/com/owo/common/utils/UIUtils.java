package com.owo.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

public class UIUtils {
  private static float scale;
  private static float scaledDensity;

  public static void init(Activity activity) {
    scale = activity.getResources().getDisplayMetrics().density;
    scaledDensity = activity.getResources().getDisplayMetrics().scaledDensity;
    sDisplayMetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(sDisplayMetrics);

  }

  public static int w(int w) {
    return w * displayMetrics().widthPixels / 1080;
  }

  public static int h(int h) {
    return h * displayMetrics().heightPixels / 1920;
  }

  private static DisplayMetrics sDisplayMetrics = null;

  public static DisplayMetrics displayMetrics() {
    return sDisplayMetrics;
  }

  public static int screenWidth() {
    return sDisplayMetrics.widthPixels;
  }

  public static int screenHeight() {
    return sDisplayMetrics.heightPixels;
  }

  public static int dip2px(int dip) {
    return (int) displayMetrics().density * dip;
  }

  public static int sp2Px(int sp) {
    return (int) (sp * displayMetrics().scaledDensity);
  }

  public static float pointToPixel(float point) {
    double xdpi = displayMetrics().xdpi;
    return (float) (point * xdpi * (1.0f / 72));
  }

  public static float millimeterToPixel(float mm) {
    double xdpi = displayMetrics().xdpi;
    return (float) (mm * xdpi * (1.0f / 25.4f));
  }

  public static final void setBackgroundDrawable(View v, Drawable d) {
    if (Build.VERSION.SDK_INT >= 16) {
      v.setBackground(d);
    } else {
      v.setBackgroundDrawable(d);
    }
  }

  public static Shader makeSweep() {
    return new SweepGradient(150,
                             25,
                             new int[] {0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFF0000},
                             null);
    //return new SweepGradient(150, 25, new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF }, new float[]{0.9f,0.1f,0.9f});
  }

  public static Shader makeLinear() {
    return new LinearGradient(0,
                              0,
                              50,
                              50,
                              new int[] {0xFFFF0000, 0xFF00FF00, 0xFF0000FF},
                              null,
                              Shader.TileMode.REPEAT);
  }

  public Shader makeTiling() {
    int[] pixels = new int[] {0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};

    Bitmap bm = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.ARGB_8888);
    // Bitmap bm = Bitmap.createBitmap(mBitmap, 60, 45,  Bitmap.Config.ARGB_8888);

    return new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
  }

}
