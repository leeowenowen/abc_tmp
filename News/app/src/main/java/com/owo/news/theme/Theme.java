package com.owo.news.theme;

public class Theme implements ITheme {
  private static final Theme sInstance = new Theme();

  public static final Theme instance() {
    return sInstance;
  }

  private ITheme mDelegate;

  public void setCurrentConfig(ITheme delegate) {
    mDelegate = delegate;
  }

  @Override
  public int getMainColor() {
    return mDelegate.getMainColor();
  }

  @Override
  public int getSecondColor() {
    return mDelegate.getSecondColor();
  }

  @Override
  public int getContentBgColor() {
    return mDelegate.getContentBgColor();
  }

  @Override
  public int getContentFgColor() {
    return mDelegate.getContentFgColor();
  }

  @Override
  public int getTitleNormalColor() {
    return mDelegate.getTitleNormalColor();
  }

  @Override
  public int getTitleHighlightColor() {
    return mDelegate.getTitleHighlightColor();
  }

  @Override
  public int getTitleReadedColor() {
    return mDelegate.getTitleReadedColor();
  }

  @Override
  public int getDescriptionNormalColor() {
    return mDelegate.getDescriptionNormalColor();
  }

  @Override
  public int getDescriptionReadedColor() {
    return mDelegate.getDescriptionReadedColor();
  }

  @Override
  public int getTitleFontSize() {
    return mDelegate.getTitleFontSize();
  }

  @Override
  public int getDescriptionFontSize() {
    return mDelegate.getDescriptionFontSize();
  }
}
