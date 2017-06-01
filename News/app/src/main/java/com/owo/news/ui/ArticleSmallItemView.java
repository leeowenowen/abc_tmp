package com.owo.news.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.owo.news.model.entity.Article;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleSmallItemView extends LinearLayout {
  private TextView mTitle;
  private TextView mDescription;
  private NetworkImageView mCover;

  public ArticleSmallItemView(Context context) {
    super(context);
    mTitle = new TextView(context);
    // mTitle.setTextColor(Color.WHITE);

    LinearLayout content = new LinearLayout(context);
    mCover = new NetworkImageView(context);
    mDescription = new TextView(context);
    content.setOrientation(HORIZONTAL);
    content.addView(mDescription, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
    content.addView(mCover, new LayoutParams(LayoutParams.WRAP_CONTENT, 300));
    setOrientation(VERTICAL);
    addView(mTitle, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    addView(content, new LayoutParams(LayoutParams.MATCH_PARENT, 300));
    setBackgroundColor(Color.argb(100, 100, 100, 100));
  }

  public void setData(Article article, ImageLoader imageLoader) {
    mTitle.setText(article.title());
    mDescription.setText(article.description());
    mCover.setImageUrl(article.urlToImage(), imageLoader);
  }
}
