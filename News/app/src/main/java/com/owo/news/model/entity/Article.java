package com.owo.news.model.entity;

/**
 * Created by wangli on 17-5-3.
 */

public class Article {
  private String author;
  private String title;
  private String description;
  private String url;
  private String urlToImage;
  private String publishedAt;

  public String author() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String title() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String description() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String url() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String urlToImage() {
    return urlToImage;
  }

  public void setUrlToImage(String urlToImage) {
    this.urlToImage = urlToImage;
  }

  public String publishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }
}
