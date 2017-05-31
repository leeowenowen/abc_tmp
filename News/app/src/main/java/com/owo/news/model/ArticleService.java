package com.owo.news.model;

import com.owo.Action;
import com.owo.news.model.entity.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ArticleService extends Observable{
  private List<Article> mArticles = new ArrayList<>();

  public interface Filter {
    boolean match(Article article);
  }

  public List<Article> getArticles() {
    return mArticles;
  }

  public List<Article> getArticles(final Filter filter) {
    List<Article> result = new ArrayList<>();
    for (Article article : mArticles) {
      if (filter.match(article)) {
        result.add(article);
      }
    }
    return result;
  }

  public boolean hasMore(){
    return true;
  }
  public boolean hasMore(String type){
    return true;
  }

  public void requestMore(Action<Boolean> callback){

  }

  public void requestMore(String type, Action<Boolean> callback) {

  }
}
