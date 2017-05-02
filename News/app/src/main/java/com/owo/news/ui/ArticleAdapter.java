package com.owo.news.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.owo.news.model.entity.Article;

import java.util.List;

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
    return mArticles == null ? 0 : mArticles.size();
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
    ArticleItemView view;
    if (convertView != null) {
      view = (ArticleItemView) convertView;
    } else {
      view = new ArticleItemView(parent.getContext());
    }
    view.setData(mArticles.get(position), mImageLoader);
    return view;
  }
}
