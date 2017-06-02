package com.owo.news.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.owo.common.utils.UIUtils;
import com.owo.news.model.entity.Article;
import com.owo.news.theme.Theme;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleSmallItemView extends FrameLayout {
  private TextView mTitle;
  private TextView mDescription;
  private NetworkImageView mCover;
  private LinearLayout mContent;

  public ArticleSmallItemView(Context context) {
    super(context);
    mTitle = new TextView(context);
    mCover = new NetworkImageView(context);
    mDescription = new TextView(context);
    mContent = new LinearLayout(context);
    mContent.setOrientation(LinearLayout.VERTICAL);
    mContent.addView(mTitle);
    mContent.addView(mCover,
                     new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                   LinearLayout.LayoutParams.WRAP_CONTENT));
    addView(mContent);
    MarginLayoutParams mlp = (MarginLayoutParams) mContent.getLayoutParams();
    int hMargin = UIUtils.dip2px(10);
    int vMargin = UIUtils.dip2px(5);
    mlp.leftMargin = hMargin;
    mlp.rightMargin = hMargin;
    mlp.topMargin = vMargin;
    mlp.bottomMargin = vMargin;
    setupTheme();
  }

  private void setupTheme() {
    Theme theme = Theme.instance();
    setBackgroundColor(theme.getContentBgColor());
    mTitle.setTextColor(theme.getTitleNormalColor());
    mTitle.setBackgroundColor(Color.TRANSPARENT);
    mDescription.setTextColor(theme.getDescriptionNormalColor());
    mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.getTitleFontSize());
    mDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.getDescriptionFontSize());

    // 外部矩形弧度
    float outRV = UIUtils.dip2px(10);
    float[] outerR = new float[] {outRV, outRV, outRV, outRV, outRV, outRV, outRV, outRV};
    // 内部矩形与外部矩形的距离
//    int hPadding = UIUtils.dip2px(10);
//    int vPadding = UIUtils.dip2px(10);
//    setPadding(hPadding, vPadding, hPadding, vPadding);
//    RectF inset = new RectF(hPadding, vPadding, hPadding, vPadding);
    // 内部矩形弧度
   // float[] innerRadii = null;//new float[] {outRV, 20, 20, 20, 20, 20, 20, 20};
    RoundRectShape rr = new RoundRectShape(outerR, null, null);
    ShapeDrawable bg = new ShapeDrawable(rr);
    // mBg.getPaint().setShader(UIUtils.makeLinear());
    bg.getPaint().setColor(Theme.instance().getContentFgColor());
    bg.getPaint().setStrokeWidth(1);
    UIUtils.setBackgroundDrawable(mContent, bg);
  }

  public void setData(Article article, ImageLoader imageLoader) {
    mTitle.setText(article.title());
    mDescription.setText(article.description());
    mCover.setImageUrl(article.urlToImage(), imageLoader);
  }
}
