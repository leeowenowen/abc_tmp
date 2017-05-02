package com.owo.news.model.entity;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class Source {
  private String id;
  private String name;
  private String description;
  private String url;
  private String language;
  private String country;
  private UrlToLogos urlsToLogo;
  private List<String> sortBysAvailable;

  public String id() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String language() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String country() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public UrlToLogos urlsToLogo() {
    return urlsToLogo;
  }

  public void setUrlsToLogo(UrlToLogos urlsToLogo) {
    this.urlsToLogo = urlsToLogo;
  }

  public List<String> sortBysAvailable() {
    return sortBysAvailable;
  }

  public void setSortBysAvailable(List<String> sortBysAvailable) {
    this.sortBysAvailable = sortBysAvailable;
  }
}
