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

public class ArticleFetcher {
  private RequestQueue mRequestQueue;


  public ArticleFetcher(Context context) {
    mRequestQueue = Volley.newRequestQueue(context);
  }

  public void start(String source, String sortBy, final Action<ArticleResponse> callback) {
    String url = Constants.URL_ARTICLES + "?source=" + source + "&sortBy=" + sortBy + "&apiKey=" +
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
