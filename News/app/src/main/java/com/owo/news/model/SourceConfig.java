package com.owo.news.model;

import java.util.List;

/**
 * Created by wangli on 17-6-1.
 */

public interface SourceConfig {
  List<String> categories();
  String country();
  String language();
}
