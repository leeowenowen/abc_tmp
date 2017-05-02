package com.owo.news.core.common;

/**
 * Created by wangli on 17-5-3.
 */

public interface Action<T> {
  void run(T t);
}
