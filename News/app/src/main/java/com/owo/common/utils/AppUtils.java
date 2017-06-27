package com.owo.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangli on 17-6-28.
 */

public class AppUtils {
  public static int versionCode(Context context) {
    PackageManager manager = context.getPackageManager();
    try {
      PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
      return info.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static String versionName(Context context) {
    PackageManager manager = context.getPackageManager();
    try {
      PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
      return packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }
}
