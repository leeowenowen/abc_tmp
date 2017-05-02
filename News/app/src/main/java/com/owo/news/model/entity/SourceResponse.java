package com.owo.news.model.entity;

import java.util.List;

/**
 * Created by wangli on 17-5-3.
 */

public class SourceResponse {
  private String status;
  private List<Source> sources;

  public String status() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<Source> sources() {
    return sources;
  }

  public void setSources(List<Source> sources) {
    this.sources = sources;
  }
}
