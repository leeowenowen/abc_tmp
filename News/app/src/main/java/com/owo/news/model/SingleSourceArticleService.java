package com.owo.news.model;

import android.content.Context;

import com.owo.Action;
import com.owo.common.model.DataCallback;
import com.owo.common.model.DataProvider;
import com.owo.common.model.Result;
import com.owo.common.model.ResultCode;
import com.owo.common.model.RunningProvder;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.ArticleResponse;
import com.owo.news.model.entity.Source;
import com.owo.news.model.entity.SourceResponse;
import com.owo.news.model.fetcher.ArticleFetcher;
import com.owo.news.model.fetcher.SourceFetcher;

import java.util.List;

//TODO be a data expire monitor and auto refresher
public class SingleSourceArticleService extends DataService<List<Article>> {
  private ArticleFetcher mArticleFetcher;
  private String mSource;
  private String mOrderBy;

  public SingleSourceArticleService(ArticleFetcher articleFetcher, String source, String orderBy) {
    mArticleFetcher = articleFetcher;
    mSource = source;
    mOrderBy = orderBy;
  }

  @Override
  public DataProvider<List<Article>> networkProvider() {
    return new DataProvider<List<Article>>() {
      private boolean mIsRunning;

      @Override
      public void request(final DataCallback<List<Article>> callback) {
        mIsRunning = true;
        mArticleFetcher.start(mSource, mOrderBy, new Action<ArticleResponse>() {
          @Override
          public void run(ArticleResponse articleResponse) {
            mIsRunning = false;
            if ("ok".equals(articleResponse.status())) {
              callback.onResult(Result.make(ResultCode.SUCCESS, "", articleResponse.articles()));
            } else {
              callback.onResult(Result.make(ResultCode.ERROR_NO_DATA,
                                            "",
                                            articleResponse.articles()));
            }
          }
        });
      }

      @Override
      public boolean hasMore() {
        return mIsRunning;
      }
    };
  }
}
