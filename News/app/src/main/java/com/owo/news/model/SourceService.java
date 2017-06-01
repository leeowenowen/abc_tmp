package com.owo.news.model;

import android.content.Context;

import com.owo.common.model.CompositeDataProvider;
import com.owo.common.model.DataCallback;
import com.owo.common.model.Result;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.Source;
import com.owo.news.model.fetcher.SourceFetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//multi category source service
public class SourceService {
  private Context mContext;

  public SourceService(Context context) {
    mContext = context;
  }

  private Map<String, SingleCategorySourceService> mChildren = new HashMap<>();

  public void updateCategory(SourceConfig sourceConfig) {
    mChildren.clear();
    for (String category : sourceConfig.categories()) {
      SingleCategorySourceService service =
          new SingleCategorySourceService(new SourceFetcher(mContext)//
                                                                     .category(category)//
                                                                     .country(sourceConfig.country())//
                                                                     .language(sourceConfig.language()));
      mChildren.put(category, service);
    }
  }

  public void request(final DataCallback<List<Source>> callback) {
    CompositeDataProvider<List<Source>> compositeDataProvider = new CompositeDataProvider<>();
    for (SingleCategorySourceService service : mChildren.values()) {
      compositeDataProvider.add(service);
    }
    compositeDataProvider.request(new DataCallback<List<List<Source>>>() {
      @Override
      public void onResult(Result<List<List<Source>>> result) {
        if (!result.success()) {
          callback.onResult(Result.make(result.code(), result.msg(), (List<Source>) null));
        } else {
          List<Source> datas = new ArrayList<Source>();
          for (List<Source> data : result.data()) {
            datas.addAll(data);
          }
          callback.onResult(Result.make(result.code(), result.msg(), datas));
        }
      }
    });
  }

  public void request(String category, final DataCallback<List<Source>> callback) {
    mChildren.get(category).request(callback);
  }
}
