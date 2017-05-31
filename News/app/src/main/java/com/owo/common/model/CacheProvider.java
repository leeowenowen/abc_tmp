package com.owo.common.model;

public class CacheProvider<T> implements DataProvider<T> {
  private T mCache;

  public void setCache(T cache) {
    mCache = cache;
  }

  @Override
  public void request(DataCallback<T> callback) {
    if (mCache == null) {
      callback.onResult(Result.make(ResultCode.SUCCESS, "", mCache));
    } else {
      callback.onResult(Result.make(ResultCode.ERROR_NO_DATA, "", mCache));
    }
  }

  @Override
  public boolean hasMore() {
    return false;
  }
}
