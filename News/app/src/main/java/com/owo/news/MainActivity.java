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

import com.owo.common.model.DataCallback;
import com.owo.common.model.Result;
import com.owo.news.core.webview.WebViewActivity;
import com.owo.news.model.ArticleService;
import com.owo.news.model.SourceConfigImpl;
import com.owo.news.model.SourceService;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.Source;
import com.owo.news.ui.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  //private MaterialViewPager mViewPager;
  private ViewPager mViewPager;
  private TabLayout mTabLayout;
  private SourceConfigImpl mSourceConfig;
  private List<ListView> mListViews = new ArrayList<>();

  //Model
  SourceService mSourceService;
  //ArticleService mArticleService;

  @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mTabLayout = new TabLayout(this);
    mViewPager = new ViewPager(this);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    LinearLayout main = new LinearLayout(this);
    main.setOrientation(LinearLayout.VERTICAL);
    main.addView(mTabLayout);
    main.addView(mViewPager);
    setContentView(main);

    setupModel();
    //startLoadingData();
    setupViewPager();
  }

  private void setupModel() {
    mSourceConfig = new SourceConfigImpl();
    mSourceService = new SourceService(this);
    mSourceService.updateCategory(mSourceConfig);
    //mArticleService = new ArticleService(this);
  }

  private void startLoadingData() {
    mSourceService.request(new DataCallback<List<Source>>() {
      @Override
      public void onResult(Result<List<Source>> result) {
        if (result.success()) {
          List<Source> sources = result.data();
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              setupViewPager();
            }
          });
        }
      }
    });
  }

  private void setupViewPager() {
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
        ListView listView = null;
        if (mListViews.size() > position) {
          listView = mListViews.get(position);
        } else {
          final ListView newListView = new ListView(MainActivity.this);
          listView = newListView;
          mListViews.add(position, listView);
          final ArticleAdapter articleAdapter = new ArticleAdapter(MainActivity.this);
          mSourceService.request(mSourceConfig.categories().get(position),
                                 new DataCallback<List<Source>>() {
                                   @Override
                                   public void onResult(Result<List<Source>> result) {
                                     if (!result.success()) {
                                       return;
                                     }
                                     ArticleService articleService =
                                         new ArticleService(MainActivity.this);
                                     articleService.updateSource(result.data());
                                     articleService.requestMore(new DataCallback<List<Article>>() {
                                       @Override
                                       public void onResult(final Result<List<Article>> result) {
                                         runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                             if (result.success()) {
                                               articleAdapter.setData(result.data());
                                               newListView.setAdapter(articleAdapter);
                                             } else {

                                             }
                                           }
                                         });
                                       }
                                     });
                                   }
                                 });

          container.addView(listView);
          listView.setDivider(null);
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String url = articleAdapter.getData().get(position - position / 5).url();
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
  }
}
