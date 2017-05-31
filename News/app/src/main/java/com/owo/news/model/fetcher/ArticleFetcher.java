package com.owo.news.model.fetcher;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owo.Action;
import com.owo.news.core.utils.JsonUtils;
import com.owo.news.model.Constants;
import com.owo.news.model.entity.ArticleResponse;

public class ArticleFetcher implements NetworkFetcher<ArticleResponse> {
  private RequestQueue mRequestQueue;
  private String mSource;
  private String mSortBy;


  public ArticleFetcher(Context context) {
    mRequestQueue = Volley.newRequestQueue(context);
  }

  public ArticleFetcher source(String source) {
    mSource = source;
    return this;
  }

  public ArticleFetcher sortBy(String sortBy) {
    mSortBy = sortBy;
    return this;
  }

  @Override
  public void fetch(final Action<ArticleResponse> callback) {
    String url = Constants.URL_ARTICLES + "?source=" + mSource + "&sortBy=" + mSortBy + "&apiKey=" +
                 Constants.API_KEY;

    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        Log.d("TAG", response);
        ArticleResponse sourceResponse = JsonUtils.fromJson(response, ArticleResponse.class);
        callback.run(sourceResponse);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("TAG", error.getMessage(), error);
      }
    });
    mRequestQueue.add(stringRequest);
  }
}
