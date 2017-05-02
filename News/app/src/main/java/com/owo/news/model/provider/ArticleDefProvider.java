package com.owo.news.model.provider;

import com.owo.news.core.utils.JsonUtils;
import com.owo.news.model.default_data.ArticleResponseData;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.ArticleResponse;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleDefProvider implements ArticleProvider {
  private final ArticleResponse mResponse;

  public ArticleDefProvider() {
    mResponse = JsonUtils.fromJson(ArticleResponseData.CONTENT, ArticleResponse.class);
  }

  @Override
  public List<Article> getData() {
    return mResponse.articles();
  }
}
