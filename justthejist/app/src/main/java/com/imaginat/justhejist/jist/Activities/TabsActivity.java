package com.imaginat.justhejist.jist.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.imaginat.justhejist.jist.DBHelper.TopStoryDBHelper;
import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.api.nyt.JSONParser;
import com.imaginat.justhejist.jist.api.nyt.Section;
import com.imaginat.justhejist.jist.models.Medium;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.imaginat.justhejist.jist.tabs.MyPageAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Window mWindow;

    String section = null;
    String mChoice = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(Section.getSections()[0]);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
        FacebookSdk.sdkInitialize(getApplicationContext());
        Intent intent = getIntent();
        handleIntent(intent);

        mWindow = this.getWindow();
        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (String section : Section.getSections()) {
            tabLayout.addTab(tabLayout.newTab().setText(section));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyPageAdapter adapter = new MyPageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (viewPager.getCurrentItem()) {
                    case 6:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
                        break;
                    case 7:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag2));
                        break;
                    case 8:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag3));
                        break;
                    case 9:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag4));
                        break;
                    case 10:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag5));
                        break;
                    case 0:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag6));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag6));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag6));
                        break;
                    case 1:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag7));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag7));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag7));
                        break;
                    case 2:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag8));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag8));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag8));
                        break;
                    case 3:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag9));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag9));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag9));
                        break;
                    case 4:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag10));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag10));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag10));
                        break;
                    case 5:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag11));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag11));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag11));
                        break;
                    case 11:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag12));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag12));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag12));
                        break;
                    case 12:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag13));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag13));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag13));
                        break;
                    case 13:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag14));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag14));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag14));
                        break;
                    case 14:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag15));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag15));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag15));
                        break;
                    case 15:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag16));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag16));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag16));
                        break;
                    case 16:
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag17));
                        mToolbar.setTitle(Section.getSections()[tab.getPosition()]);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag17));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag17));
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
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (mChoice != null) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                Log.d("dude", mChoice);
                Toast.makeText(TabsActivity.this, "Searching for " + query + "/n/r" + mChoice,
                        Toast.LENGTH_SHORT)
                        .show();

                Cursor cursor = TopStoryDBHelper.getInstance(this)
                        .searchByUserCategory(query, mChoice);
                cursor.moveToFirst();

                //loop through cursor
                ArrayList<NewsStory>newsStories = new ArrayList<>();
                while(cursor.moveToNext()){
                    NewsStory.Builder builder = new NewsStory.Builder();
                    builder.author(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_BYLINE)));
                    builder.title(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_TITLE)));
                    builder.summary(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_ABSTRACT)));
                    builder.url(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_URL)));

                    List<Medium> media = new ArrayList<>();
                    try {
                        JSONArray mediaArray = new JSONArray(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_MULTIMEDIA)));
                        media = JSONParser.parseMultimedia(mediaArray);
                    } catch (JSONException ex) {
                    }

                    builder.multimedia(media);
                    NewsStory nw = builder.build();
                    newsStories.add(nw);
                }



                // TextView searchResult = (TextView) findViewById(R.id.tempTempView);
                // searchResult.setText(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_TITLE)));
            }

            else {
                String query = intent.getStringExtra(SearchManager.QUERY);
                Toast.makeText(TabsActivity.this, "Searching for " + query,
                        Toast.LENGTH_SHORT)
                        .show();

                Cursor cursor = TopStoryDBHelper.getInstance(this)
                        .searchArticlesByAllThree(query);
                cursor.moveToFirst();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search) {

            AlertDialog.Builder builder = new AlertDialog.Builder(TabsActivity.this);
            builder.setTitle("Please Select What To Search By")
                    .setItems(R.array.choices, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = getResources();
                            String[] choices = res.getStringArray(R.array.choices);
                            mChoice = choices[which].toLowerCase();
                        }
                    });

//      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int id) {
//          // User clicked OK button
//        }
//      });
//
//      builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int id) {
//          // User cancelled the dialog
//        }
//      });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.test_animate_scroll_vertical) {
        }

        if (id == R.id.test_update_breakingNews) {
            Toast.makeText(TabsActivity.this,"attempting",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
