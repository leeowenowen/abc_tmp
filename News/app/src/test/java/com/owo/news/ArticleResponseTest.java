package com.owo.news;

import com.owo.news.core.utils.JsonUtils;
import com.owo.news.model.default_data.ArticleResponseData;
import com.owo.news.model.entity.ArticleResponse;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArticleResponseTest {
  @Test
  public void testParseResponse() throws Exception {
    ArticleResponse articleResponse =
        JsonUtils.fromJson(ArticleResponseData.CONTENT, ArticleResponse.class);
    Assert.assertNotNull(articleResponse);
  }
}