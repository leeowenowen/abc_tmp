package com.owo.news.model.entity;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class ArticleResponse {
  private String status;
  private String source;
  private String sortBy;
  private List<Article> articles;

  public String status() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String source() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String sortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public List<Article> articles() {
    return articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }
}
