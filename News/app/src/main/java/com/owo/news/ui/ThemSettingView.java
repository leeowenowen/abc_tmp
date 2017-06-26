package com.owo.news.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.owo.common.utils.UIUtils;
import com.owo.news.theme.ITheme;
import com.owo.news.theme.Theme;

public class ThemSettingView extends ListView implements Theme.ThemeObserver {
  @Override
  public void onThemeChanged() {

  }

  public static class ThemeItem {
    public String mTitle;
    public ITheme mTheme;

    public ThemeItem(String title, ITheme theme) {
      mTitle = title;
      mTheme = theme;
    }
  }

  private ThemeItem[] mThemes;

  public ThemSettingView(Context context) {
    super(context);
    setAdapter(new ThemeAdapter());
    Theme.instance().registerObserver(this);
  }

  public void themeData(ThemeItem[] themeItems) {
    mThemes = themeItems;
  }

  private class ThemeAdapter extends BaseAdapter {
    @Override
    public int getCount() {
      return mThemes == null ? 0 : mThemes.length;
    }

    @Override
    public Object getItem(int position) {
      return null;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      TextView tv;
      if (convertView != null) {
        tv = (TextView) convertView;
      } else {
        tv = new TextView(getContext());
      }
      ThemeItem themeItem = mThemes[position];
      tv.setTextColor(themeItem.mTheme.getTitleNormalColor());
      tv.setBackgroundColor(themeItem.mTheme.getMainColor());
      tv.setText(themeItem.mTitle);
      tv.setGravity(Gravity.CENTER);
      AbsListView.LayoutParams layoutParams =
          new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, UIUtils.h(150));
      tv.setLayoutParams(layoutParams);
      return tv;
    }
  }
}
