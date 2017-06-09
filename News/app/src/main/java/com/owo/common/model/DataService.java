package com.owo.common.model;

import com.owo.Action;


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

      @Override
      public boolean hasMore() {
        return networkProvider().hasMore();
      }
    });
  }

  @Override
  public void request(DataCallback<T> callback) {
    sourceProviders.request(callback);
  }

  public DataProvider<T> networkProvider() {
    return new DataProvider<T>() {
      @Override
      public void request(final DataCallback<T> callback) {
        networkFetcher().fetch(new Action<T>() {
          @Override
          public void run(T t) {
            callback.onResult(Result.make(t == null ? ResultCode.ERROR_NO_DATA : ResultCode.SUCCESS,
                                          "",
                                          t));
          }
        });
      }

      @Override
      public boolean hasMore() {
        return false;
      }
    };
  }

  public abstract NetworkFetcher<T> networkFetcher();

  public CacheProvider<T> cacheProvider() {
    if (cacheProvider == null) {
      cacheProvider = new CacheProvider<>();
    }
    return cacheProvider;
  }

  @Override
  public boolean hasMore() {
    return cacheProvider.hasMore() || networkProvider().hasMore();
  }
}
