package com.owo.news.ui;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.owo.news.model.entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleAdapter extends BaseAdapter {
  private List<Article> mArticles;
  private ImageLoader mImageLoader;

  public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
      int maxSize = 10 * 1024 * 1024;
      mCache = new LruCache<String, Bitmap>(maxSize) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
          return value.getRowBytes() * value.getHeight();
        }

      };
    }

    @Override
    public Bitmap getBitmap(String url) {
      return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
      mCache.put(url, bitmap);
    }

  }

  public ArticleAdapter(Context context) {
    RequestQueue mQueue = Volley.newRequestQueue(context);
    mImageLoader = new ImageLoader(mQueue, new BitmapCache());
  }

  public void setData(List<Article> articles) {
    mArticles = articles;
    notifyDataSetChanged();

  }

  @Override
  public int getCount() {
    return mArticles == null ? 0 : mArticles.size() + 1;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  private Stack<ArticleItemView> mArticleItemViews = new Stack<>();
  private Stack<NativeExpressAdView> mNativeViews = new Stack<>();

  private View obtainView(Context context, int position) {
    View v;
    if (position == mArticles.size()) {
      if (mNativeViews.size() > 0) {
        v = mNativeViews.pop();
      } else {
        v = obtainNativeView(context);
      }
    } else {
      if (mArticleItemViews.size() > 0) {
        v = mArticleItemViews.pop();
      } else {
        v = obtainArticleImageView(context, position);
      }
    }
    return v;
  }

  private ArticleItemView obtainArticleImageView(Context context, int position) {
    ArticleItemView itemView = new ArticleItemView(context);
    itemView.setData(mArticles.get(position), mImageLoader);
    return itemView;
  }

  private NativeExpressAdView obtainNativeView(Context context) {
    NativeExpressAdView adView = new NativeExpressAdView(context);
    adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
    adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
    AdRequest request = new AdRequest.Builder().build();
    adView.loadAd(request);
    return adView;
  }

  private void putIntoCache(View v) {
    if (v instanceof ArticleItemView && !mArticleItemViews.contains(v)) {
      mArticleItemViews.add((ArticleItemView) v);
      return;
    }
    if (v instanceof NativeExpressAdView && !mNativeViews.contains(v)) {
      mNativeViews.add((NativeExpressAdView) v);
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView != null) {
      putIntoCache(convertView);
    }
    return obtainView(parent.getContext(), position);
  }
}
