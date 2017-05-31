package com.owo.news.model;

import android.content.Context;

import com.owo.common.model.DataCallback;
import com.owo.common.model.DataProvider;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.Source;
import com.owo.news.model.fetcher.ArticleFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiSourceArticleProvider implements DataProvider<List<Article>> {
  private Map<String, SingleSourceArticleService> mChildren = new HashMap<>();
  private ArticleFetcher mArticleFetcher;

  public MultiSourceArticleProvider(Context context) {
    mArticleFetcher = new ArticleFetcher(context);
  }

  public void updateSource(List<Source> sources) {
    mChildren.clear();
    for (Source source : sources) {
      SingleSourceArticleService service =
          new SingleSourceArticleService(mArticleFetcher, source.id(), "");
      mChildren.put(source.id(), service);
    }
  }

  @Override
  public void request(DataCallback<List<Article>> callback) {

  }

  @Override
  public boolean hasMore() {
    for(Map.Entry<String, SingleSourceArticleService> entry: mChildren.entrySet()){
      SingleSourceArticleService service = entry.getValue();
      if(service.hasMore()){
        return true;
      }
    }
    return false;
  }
}
