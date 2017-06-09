package com.owo.news.model;

import com.owo.Action;
import com.owo.common.model.DataService;
import com.owo.news.model.entity.Source;
import com.owo.news.model.entity.SourceResponse;
import com.owo.common.model.NetworkFetcher;
import com.owo.news.model.fetcher.SourceFetcher;

import java.util.List;

public class SingleCategorySourceService extends DataService<List<Source>> {
  private SourceFetcher mSourceFetcher;
  private NetworkFetcher<List<Source>> mNetworkFetcher;

  public SingleCategorySourceService(SourceFetcher sourceFetcher) {
    mSourceFetcher = sourceFetcher;
    mNetworkFetcher = new NetworkFetcher<List<Source>>() {
      @Override
      public void fetch(final Action<List<Source>> callback) {
        mSourceFetcher.fetch(new Action<SourceResponse>() {
          @Override
          public void run(SourceResponse sourceResponse) {
            if (sourceResponse == null) {
              callback.run(null);
            } else {
              callback.run(sourceResponse.sources());
            }
          }
        });
      }
    };
  }

  @Override
  public NetworkFetcher<List<Source>> networkFetcher() {
    return mNetworkFetcher;
  }
}
