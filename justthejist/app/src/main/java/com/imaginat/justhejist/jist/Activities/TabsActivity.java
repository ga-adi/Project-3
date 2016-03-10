package com.imaginat.justhejist.jist.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.tabs.MyPageAdapter;

public class TabsActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Window mWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tab 1");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
//        mToolbar.setTitle("Tab 1");

        mWindow = this.getWindow();
        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyPageAdapter adapter = new MyPageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.getTabAt(viewPager.getCurrentItem()).getText();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (viewPager.getCurrentItem()) {
                    case 0:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mToolbar.setTitle("Tab 1");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
                        break;
                    case 1:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mToolbar.setTitle("Tab 2");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag2));
                        break;
                    case 2:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mToolbar.setTitle("Tab 3");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag3));
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
