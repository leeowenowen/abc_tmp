package com.owo.news.model.provider;

import com.owo.news.core.utils.JsonUtils;
import com.owo.news.model.default_data.ArticleResponseData;
import com.owo.news.model.default_data.SourceResponseData;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.ArticleResponse;
import com.owo.news.model.entity.Source;
import com.owo.news.model.entity.SourceResponse;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class SourceDefProvider implements SourceProvider {
  private final SourceResponse mResponse;

  public SourceDefProvider() {
    mResponse = JsonUtils.fromJson(SourceResponseData.CONTENT, SourceResponse.class);
  }

  @Override
  public List<Source> getData() {
    return mResponse.sources();
  }
}
