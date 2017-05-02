package com.owo.news.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.owo.news.model.entity.Article;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleItemView extends LinearLayout {
  private TextView mTitle;
  private NetworkImageView mCover;

  public ArticleItemView(Context context) {
    super(context);

    mTitle = new TextView(context);
    mCover = new NetworkImageView(context);
    setOrientation(VERTICAL);
    addView(mTitle, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 100));
    addView(mCover, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 300));
  }

  public void setData(Article article, ImageLoader imageLoader) {
    mTitle.setText(article.title());
    mCover.setImageUrl(article.urlToImage(), imageLoader);
  }
}
