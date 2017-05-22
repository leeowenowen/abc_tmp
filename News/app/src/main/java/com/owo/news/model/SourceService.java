package com.owo.news.model;

import android.content.Context;

import com.owo.Action;
import com.owo.common.model.DataCallback;
import com.owo.common.model.DataProvider;
import com.owo.common.model.Result;
import com.owo.common.model.ResultCode;
import com.owo.news.model.entity.Source;
import com.owo.news.model.entity.SourceResponse;
import com.owo.news.model.fetcher.SourceFetcher;

import java.util.List;

//TODO be a data expire monitor and auto refresher
public class SourceService extends DataService<List<Source>> {
  private SourceFetcher mSourceFetcher;

  public SourceService(Context context) {
    mSourceFetcher = new SourceFetcher(context);
  }

  @Override
  public DataProvider<List<Source>> networkProvider() {
    return new DataProvider<List<Source>>() {
      @Override
      public void request(final DataCallback<List<Source>> callback) {
        mSourceFetcher.start("", "", "", new Action<SourceResponse>() {
          @Override
          public void run(SourceResponse sourceResponse) {
            if ("ok".equals(sourceResponse.status())) {
              callback.onResult(Result.make(ResultCode.SUCCESS, "", sourceResponse.sources()));
            } else {
              callback.onResult(Result.make(ResultCode.ERROR_NO_DATA,
                                            "",
                                            sourceResponse.sources()));
            }
          }
        });
      }
    };
  }
}
