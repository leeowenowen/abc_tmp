package com.owo.news.model.provider;

import com.owo.news.model.entity.Source;
import com.owo.news.model.entity.SourceResponse;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public interface SourceProvider {
  List<Source> getData();
}
