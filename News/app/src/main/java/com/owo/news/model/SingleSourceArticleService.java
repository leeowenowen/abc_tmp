package com.owo.news.model;

import com.owo.Action;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.ArticleResponse;
import com.owo.news.model.fetcher.ArticleFetcher;
import com.owo.news.model.fetcher.NetworkFetcher;

import java.util.List;

//TODO be a data expire monitor and auto refresher
public class SingleSourceArticleService extends DataService<List<Article>> {
  private ArticleFetcher mArticleFetcher;
  private NetworkFetcher<List<Article>> mNetworkFetcher;

  public SingleSourceArticleService(ArticleFetcher articleFetcher) {
    mArticleFetcher = articleFetcher;
    mNetworkFetcher = new NetworkFetcher<List<Article>>() {
      @Override
      public void fetch(final Action<List<Article>> callback) {
        mArticleFetcher.fetch(new Action<ArticleResponse>() {
          @Override
          public void run(ArticleResponse articleResponse) {
            if (articleResponse == null) {
              callback.run(null);
            } else {
              callback.run(articleResponse.articles());
            }
          }
        });
      }
    };
  }

  @Override
  public NetworkFetcher<List<Article>> networkFetcher() {
    return mNetworkFetcher;
  }
}
