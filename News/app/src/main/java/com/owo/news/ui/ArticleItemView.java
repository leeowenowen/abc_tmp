package com.owo.news.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.owo.news.model.entity.Article;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleItemView extends FrameLayout {
  private TextView mTitle;
  private NetworkImageView mCover;

  public ArticleItemView(Context context) {
    super(context);
    FrameLayout content = new FrameLayout(context);
    mTitle = new TextView(context);
    mTitle.setBackgroundColor(Color.argb(150, 100, 100, 100));
    mTitle.setTextColor(Color.WHITE);
    mCover = new NetworkImageView(context);
    content.addView(mCover,
                    new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                                 LayoutParams.WRAP_CONTENT));
    content.addView(mTitle,
                    new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                                 LayoutParams.WRAP_CONTENT,
                                                 Gravity.BOTTOM));

    content.setBackgroundColor(Color.BLACK);
    addView(content);
    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) content.getLayoutParams();
    marginLayoutParams.topMargin = 20;
    marginLayoutParams.bottomMargin = 20;
    setBackgroundColor(Color.argb(100, 100, 100, 100));
  }

  public void setData(Article article, ImageLoader imageLoader) {
    mTitle.setText(article.title());
    mCover.setImageUrl(article.urlToImage(), imageLoader);
  }
}
