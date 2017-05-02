package com.owo.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.owo.news.model.provider.ArticleDefProvider;
import com.owo.news.model.provider.ArticleProvider;
import com.owo.news.ui.ArticleAdapter;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ListView listView = new ListView(this);
    ArticleAdapter adapter = new ArticleAdapter(this);
    ArticleProvider articleProvider = new ArticleDefProvider();
    adapter.setData(articleProvider.getData());
    listView.setAdapter(adapter);
    setContentView(listView);
    //    setContentView(R.layout.activity_main);
    //    new SourceFetcher(this).start("", "", "", new Action<SourceResponse>() {
    //      @Override
    //      public void run(SourceResponse sourceResponse) {
    //
    //      }
    //    });
    //    new ArticleFetcher(this).start("the-next-web", "latest",new Action<ArticleResponse>() {
    //      @Override
    //      public void run(ArticleResponse sourceResponse) {
    //
    //      }
    //    });
  }
}
