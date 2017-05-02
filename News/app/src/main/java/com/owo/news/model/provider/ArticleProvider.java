package com.owo.news.model.provider;

import com.owo.news.model.entity.Article;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public interface ArticleProvider {
  List<Article> getData();
}
