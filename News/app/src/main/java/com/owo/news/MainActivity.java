package com.owo.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.owo.common.model.DataCallback;
import com.owo.common.model.Result;
import com.owo.common.utils.UIUtils;
import com.owo.news.core.webview.WebViewActivity;
import com.owo.news.model.ArticleService;
import com.owo.news.model.SourceConfigImpl;
import com.owo.news.model.SourceService;
import com.owo.news.model.entity.Article;
import com.owo.news.model.entity.Source;
import com.owo.news.theme.Black;
import com.owo.news.theme.Blue;
import com.owo.news.theme.Red;
import com.owo.news.theme.Theme;
import com.owo.news.ui.ArticleAdapter;
import com.owo.news.ui.ThemSettingView;
import com.owo.news.ui.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements Theme.ThemeObserver {
  private TitleBar mTitleBar;
  private ViewPager mViewPager;
  private TabLayout mTabLayout;
  private SourceConfigImpl mSourceConfig;
  private List<ListView> mListViews = new ArrayList<>();

  //Model
  SourceService mSourceService;

  @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Theme.instance().registerObserver(this);
    mTitleBar = new TitleBar(this);
    mTitleBar.setTitle("Cool News");
    mTitleBar.setOnClickListener(mSettingClickListener);
    mTabLayout = new TabLayout(this);
    mViewPager = new ViewPager(this);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    LinearLayout main = new LinearLayout(this);
    main.setOrientation(LinearLayout.VERTICAL);
    main.addView(mTitleBar);
    main.addView(mTabLayout);
    main.addView(mViewPager);
    setContentView(main);

    setupModel();
    setupViewPager();
    onThemeChanged();
  }

  private void setupModel() {
    mSourceConfig = new SourceConfigImpl();
    mSourceService = new SourceService(this);
    mSourceService.updateCategory(mSourceConfig);
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
        ListView listView;
        if (mListViews.size() > position) {
          listView = mListViews.get(position);
        } else {
          final ListView newListView = new ListView(MainActivity.this);
          Theme.ThemeObserver themeObserver = new Theme.ThemeObserver() {
            @Override
            public void onThemeChanged() {
              newListView.setBackgroundColor(Theme.instance().getContentBgColor());
            }
          };
          Theme.instance().registerObserver(themeObserver);
          themeObserver.onThemeChanged();
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
        container.addView(listView);
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

  private View.OnClickListener mSettingClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final PopupWindow popupWindow = new PopupWindow(MainActivity.this);
      popupWindow.setWidth(UIUtils.w(300));
      popupWindow.setHeight(UIUtils.h(480));
      popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      ThemSettingView themSettingView = new ThemSettingView(MainActivity.this);
      final ThemSettingView.ThemeItem[] themeItems = new ThemSettingView.ThemeItem[] {
          new ThemSettingView.ThemeItem("Black", new Black()),//
          new ThemSettingView.ThemeItem("Blue", new Blue()),//
          new ThemSettingView.ThemeItem("Red", new Red())//
      };
      themSettingView.themeData(themeItems);
      themSettingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          ThemSettingView.ThemeItem themeItem = themeItems[position];
          Theme.instance().setCurrentConfig(themeItem.mTheme);
          popupWindow.dismiss();
        }
      });
      popupWindow.setContentView(themSettingView);
      popupWindow.setOutsideTouchable(true);
      popupWindow.showAsDropDown(mTitleBar.settingView());
    }
  };

  @Override
  public void onThemeChanged() {
    UIUtils.setBackgroundDrawable(mTitleBar, new ColorDrawable(Theme.instance().getMainColor()));
    mTabLayout.setTabTextColors(Theme.instance().getTitleNormalColor(),
                                Theme.instance().getTitleHighlightColor());
    mTabLayout.setBackgroundColor(Theme.instance().getSecondColor());
  }
}
