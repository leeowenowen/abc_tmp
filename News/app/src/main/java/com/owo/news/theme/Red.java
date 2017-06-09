package com.owo.news.theme;

import android.graphics.Color;

import com.owo.common.utils.UIUtils;

/**
 * Created by wangli on 17-6-2.
 */

public class Red implements ITheme {
  @Override
  public int getMainColor() {
    return Color.RED;
  }

  @Override
  public int getSecondColor() {
    return Color.parseColor("#CC0000");
  }

  @Override
  public int getContentBgColor() {
    return Color.parseColor("#999999");
  }

  @Override
  public int getContentFgColor() {
    return Color.parseColor("#990000");
  }

  @Override
  public int getTitleNormalColor() {
    return Color.WHITE;
  }

  @Override
  public int getTitleHighlightColor() {
    return Color.YELLOW;
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
    return UIUtils.w(48);
  }

  @Override
  public int getDescriptionFontSize() {
    return UIUtils.w(42);
  }
}
