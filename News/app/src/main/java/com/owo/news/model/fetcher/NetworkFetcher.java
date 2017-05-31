package com.owo.news.model.fetcher;

import com.owo.Action;

/**
 * Created by wangli on 17-6-1.
 */

public interface NetworkFetcher<T> {
  void fetch(Action<T> callback);
}
