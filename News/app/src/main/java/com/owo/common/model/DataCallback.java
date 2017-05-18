package com.owo.common.model;

public interface DataCallback<T> {
  void onResult(Result<T> result);
}
