package com.owo.news;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.owo.news.core.webview.WebViewActivity;
import com.owo.news.model.SourceConfig;
import com.owo.news.model.provider.ArticleDefProvider;
import com.owo.news.ui.ArticleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  //private MaterialViewPager mViewPager;
  private ViewPager mViewPager;
  private TabLayout mTabLayout;
  private SourceConfig mSourceConfig;
  private List<ListView> mListViews = new ArrayList<>();

  @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //    ListView listView = new ListView(this);
    //    ArticleAdapter adapter = new ArticleAdapter(this);
    //    ArticleProvider articleProvider = new ArticleDefProvider();
    //    adapter.setData(articleProvider.getData());
    //    listView.setAdapter(adapter);
    //    setContentView(listView);
    //  setContentView(R.layout.activity_main);
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
    // mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
    mSourceConfig = new SourceConfig();
    mTabLayout = new TabLayout(this);
    mViewPager = new ViewPager(this);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    mViewPager.setAdapter(new PagerAdapter() {

      @Override
      public int getCount() {
        return mSourceConfig.categories() == null ? 0 : mSourceConfig.categories().size();
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
        return view == object;
      }

      @Override
      public Object instantiateItem(ViewGroup container, int position) {
        ListView listView = mListViews.get(position);
        if (listView == null) {
          listView = new ListView(MainActivity.this);
          mListViews.add(position,listView);
          ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.this);
          final ArticleDefProvider provider = new ArticleDefProvider();
          articleAdapter.setData(provider.getData());
          listView.setAdapter(articleAdapter);
          container.addView(listView);
          listView.setDivider(null);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String url = provider.getData().get(position - position / 5).url();
              Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
              intent.putExtra("url", url);
              MainActivity.this.startActivity(intent);
            }
          });
        }
        return listView;
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
      }

      @Override
      public CharSequence getPageTitle(int position) {
        return mSourceConfig.categories().get(position);
      }
    });
    LinearLayout main = new LinearLayout(this);
    main.setOrientation(LinearLayout.VERTICAL);
    main.addView(mTabLayout);
    main.addView(mViewPager);
    setContentView(main);
  }
}
