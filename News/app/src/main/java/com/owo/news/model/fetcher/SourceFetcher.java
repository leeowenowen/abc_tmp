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
import com.owo.news.model.entity.SourceResponse;

public class SourceFetcher implements NetworkFetcher<SourceResponse> {
  private RequestQueue mRequestQueue;
  private String mCategory;
  private String mLanguage;
  private String mCountry;

  public SourceFetcher(Context context) {
    mRequestQueue = Volley.newRequestQueue(context);
  }

  public SourceFetcher category(String category) {
    mCategory = category;
    return this;
  }

  public SourceFetcher language(String language) {
    mLanguage = language;
    return this;
  }

  public SourceFetcher country(String country) {
    mCountry = country;
    return this;
  }

  @Override
  public void fetch(final Action<SourceResponse> callback) {
    String url =
        Constants.URL_SOURCE + "?category=" + mCategory + "&language=" + mLanguage + "&country=" +
        mCountry + "&apiKey=" + Constants.API_KEY;

    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        Log.d("TAG", response);
        SourceResponse sourceResponse = JsonUtils.fromJson(response, SourceResponse.class);
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
