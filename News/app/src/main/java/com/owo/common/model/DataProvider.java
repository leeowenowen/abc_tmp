package com.owo.common.model;

public interface DataProvider<T> {
  void request(DataCallback<T> callback);
  boolean hasMore();
}
