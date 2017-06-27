package com.owo.news.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.owo.common.utils.UIUtils;
import com.owo.news.theme.Theme;

public class TitleBar extends FrameLayout implements Theme.ThemeObserver {
  private TextView mTitle;
  private TextView mSetting;

  public TitleBar(Context context) {
    super(context);

    {
      mTitle = new TextView(context);
      FrameLayout.LayoutParams layoutParams =
          new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                       LayoutParams.WRAP_CONTENT,
                                       Gravity.LEFT | Gravity.CENTER_VERTICAL);
      addView(mTitle, layoutParams);
      mTitle.getPaint().setFakeBoldText(true);
    }
    {
      mSetting = new TextView(context);
      FrameLayout.LayoutParams layoutParams =
          new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                       LayoutParams.WRAP_CONTENT,
                                       Gravity.RIGHT | Gravity.CENTER_VERTICAL);
      mSetting.setText("T");
      mSetting.getPaint().setFakeBoldText(true);
      addView(mSetting, layoutParams);
    }
    setPadding(0, 0, UIUtils.w(20), 0);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtils.h(180));
    setLayoutParams(layoutParams);
    onThemeChanged();
  }

  public void setTitle(String title) {
    mTitle.setText(title);
  }

  public void setSettingClickListener(OnClickListener l) {
    mSetting.setOnClickListener(l);
  }

  public void setTitleClickListener(OnClickListener l){
    mTitle.setOnClickListener(l);
  }

  public View settingView() {
    return mSetting;
  }

  @Override
  public void onThemeChanged() {
    mSetting.setTextColor(Theme.instance().getTitleNormalColor());
    mSetting.setTextSize(TypedValue.COMPLEX_UNIT_PX, Theme.instance().getTitleFontSize() * 2);
    mTitle.setTextColor(Theme.instance().getTitleNormalColor());
    mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, Theme.instance().getTitleFontSize() * 2);
  }
}
