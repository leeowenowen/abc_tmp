package com.owo.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class UIUtils {
  private static float scale;
  private static float scaledDensity;

  public static void init(Context context) {
    scale = context.getResources().getDisplayMetrics().density;
    scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
  }

  /**
   * dp转成px
   *
   * @param dipValue
   *
   * @return
   */
  public static int dip2px(float dipValue) {
    return (int) (dipValue * scale + 0.5f);
  }

  /**
   * px转成dp
   *
   * @param pxValue
   *
   * @return
   */
  public static int px2dip(float pxValue) {
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * sp转成px
   *
   * @param spValue
   * @param type
   *
   * @return
   */
  public static float sp2px(float spValue, int type) {
    return spValue * scaledDensity;
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
