package com.owo.news.theme;

import android.graphics.Color;

import com.owo.common.utils.UIUtils;

/**
 * Created by wangli on 17-6-2.
 */

public class Blue implements ITheme {
  @Override
  public int getMainColor() {
    return Color.BLUE;
  }

  @Override
  public int getContentBgColor() {
    return Color.parseColor("#999999");
  }

  @Override
  public int getContentFgColor() {
    return Color.WHITE;
  }

  @Override
  public int getTitleNormalColor() {
    return Color.BLACK;
  }

  @Override
  public int getTitleReadedColor() {
    return Color.parseColor("#555555");
  }

  @Override
  public int getDescriptionNormalColor() {
    return Color.parseColor("#999999");
  }

  @Override
  public int getDescriptionReadedColor() {
    return Color.parseColor("#AAAAAA");
  }

  @Override
  public int getTitleFontSize() {
    return UIUtils.dip2px(16);
  }

  @Override
  public int getDescriptionFontSize() {
    return UIUtils.dip2px(14);
  }
}
