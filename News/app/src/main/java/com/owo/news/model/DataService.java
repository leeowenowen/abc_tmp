package com.owo.news.model;

import com.owo.common.model.CacheProvider;
import com.owo.common.model.DataCallback;
import com.owo.common.model.DataProvider;
import com.owo.common.model.Result;
import com.owo.common.model.UntilOneDataProvider;


public abstract class DataService<T> implements DataProvider<T> {
  private UntilOneDataProvider<T> sourceProviders = new UntilOneDataProvider<>();
  private CacheProvider<T> cacheProvider;

  public DataService() {
    sourceProviders.first(cacheProvider()).then(new DataProvider<T>() {
      @Override
      public void request(final DataCallback<T> callback) {
        networkProvider().request(new DataCallback<T>() {
          @Override
          public void onResult(Result<T> result) {
            if (result.success()) {
              cacheProvider().setCache(result.data());
            }
            callback.onResult(result);
          }
        });
      }
    });
  }

  @Override
  public void request(DataCallback<T> callback) {
    sourceProviders.request(callback);
  }

  public abstract DataProvider<T> networkProvider();

  public CacheProvider<T> cacheProvider() {
    if (cacheProvider == null) {
      cacheProvider = new CacheProvider<>();
    }
    return cacheProvider;
  }
}
