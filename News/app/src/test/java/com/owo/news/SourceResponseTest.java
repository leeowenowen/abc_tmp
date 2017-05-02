package com.owo.news;

import com.owo.news.core.utils.JsonUtils;
import com.owo.news.model.default_data.SourceResponseData;
import com.owo.news.model.entity.*;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SourceResponseTest {
  @Test
  public void testParseResponse() throws Exception {
    com.owo.news.model.entity.SourceResponse sourceResponse =
        JsonUtils.fromJson(SourceResponseData.CONTENT, SourceResponse.class);
    Assert.assertNotNull(sourceResponse);
  }
}