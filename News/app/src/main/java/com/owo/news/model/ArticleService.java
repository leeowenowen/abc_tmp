package com.owo.news.model;

import android.content.Context;

import com.owo.Action;
import com.owo.common.model.CompositeDataProvider;
import com.owo.common.model.DataCallback;
import com.owo.common.model.Result;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.Source;
import com.owo.news.model.fetcher.ArticleFetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

//multi source article service
public class ArticleService {
  private Context mContext;
  public ArticleService(Context context) {
    mContext = context;
  }

  private Map<String, SingleSourceArticleService> mChildren = new HashMap<>();

  public void updateSource(List<Source> sources) {
    mChildren.clear();
    for (Source source : sources) {
      SingleSourceArticleService service =
          new SingleSourceArticleService(new ArticleFetcher(mContext).source(source.id()));
      mChildren.put(source.id(), service);
    }
  }

  public boolean hasMore() {
    for (Map.Entry<String, SingleSourceArticleService> entry : mChildren.entrySet()) {
      SingleSourceArticleService service = entry.getValue();
      if (service.hasMore()) {
        return true;
      }
    }
    return false;
  }

  public boolean hasMore(String type) {
    return mChildren.get(type).hasMore();
  }

  public void requestMore(final DataCallback<List<Article>> callback) {
    CompositeDataProvider<List<Article>> compositeDataProvider = new CompositeDataProvider<>();
    for (SingleSourceArticleService service : mChildren.values()) {
      compositeDataProvider.add(service);
    }
    compositeDataProvider.request(new DataCallback<List<List<Article>>>() {
      @Override
      public void onResult(Result<List<List<Article>>> result) {
        if (!result.success()) {
          callback.onResult(Result.make(result.code(), result.msg(), (List<Article>) null));
        } else {
          List<Article> datas = new ArrayList<Article>();
          for (List<Article> data : result.data()) {
            datas.addAll(data);
          }
          callback.onResult(Result.make(result.code(), result.msg(), datas));
        }
      }
    });
  }

  public void requestMore(String type, final DataCallback<List<Article>> callback) {
    mChildren.get(type).request(callback);
  }
}
