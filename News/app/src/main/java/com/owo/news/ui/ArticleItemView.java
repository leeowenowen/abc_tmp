package com.owo.news.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.owo.news.model.entity.Article;
import com.owo.news.theme.Theme;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleItemView extends FrameLayout implements Theme.ThemeObserver {
  private TextView mTitle;
  private NetworkImageView mCover;
  private FrameLayout mContent;

  public ArticleItemView(Context context) {
    super(context);
    mContent = new FrameLayout(context);
    mTitle = new TextView(context);
    mCover = new NetworkImageView(context);
    mContent.addView(mCover, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, 400));
    mContent.addView(mTitle,
                     new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                                  LayoutParams.WRAP_CONTENT,
                                                  Gravity.BOTTOM));


    addView(mContent);
    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) mContent.getLayoutParams();
    marginLayoutParams.topMargin = 20;
    marginLayoutParams.bottomMargin = 20;
    onThemeChanged();
    Theme.instance().registerObserver(this);
  }

  public void setData(Article article, ImageLoader imageLoader) {
    mTitle.setText(article.title());
    mCover.setImageUrl(article.urlToImage(), imageLoader);
  }

  @Override
  public void onThemeChanged() {
    mTitle.setBackgroundColor(Color.argb(150, 100, 100, 100));
    mTitle.setTextColor(Color.WHITE);
    mContent.setBackgroundColor(Color.BLACK);
    setBackgroundColor(Color.argb(100, 100, 100, 100));
  }
}
