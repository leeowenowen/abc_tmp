package com.owo.news.theme;

/**
 * Created by wangli on 17-6-2.
 */

public interface ITheme {
  /**
   * color
   */
  int getMainColor();
  int getSecondColor();
  int getContentBgColor();
  int getContentFgColor();
  int getTitleNormalColor();
  int getTitleHighlightColor();
  int getTitleReadedColor();
  int getDescriptionNormalColor();
  int getDescriptionReadedColor();
  /**
   * font
   */
  int getTitleFontSize();
  int getDescriptionFontSize();
}
