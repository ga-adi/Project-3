package com.example.project3vice;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.project3vice.drawer_files.NavigationDrawerFragment;
import com.example.project3vice.section_fragments.NewsFragment;
import com.example.project3vice.vice_classes.ViceArticle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ViewPager mViewPager;
    private static PagerAdapter mAdapter;
    public static TabLayout mTabLayout;
    private FloatingActionButton mFab;
    private CursorAdapter mCursorAdapter;
    public static ArrayList<ViceArticle> mArrayList;
    public static boolean mSearchEnabled = false;
    public static String mQuery;

    public static final String CATEGORY_KEY = "category";
    public static int mTabPosition;

    private static final String AUTHORITY = "com.example.project3vice.SyncClasses.ViceArticleContentProvider";
    public static final String ACCOUNT_TYPE = "example.com";
    public static final String ACCOUNT = "default_account";
    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");

        mAccount = createSyncAccount(this);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDeviceOnline()) {
                    Bundle settingsBundle = new Bundle();
                    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                    settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                    ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection detected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instantiate newsFragment's tabLayout, adapter, and pager
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout.addTab(mTabLayout.newTab().setText("News"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Music"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Fashion"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Travel"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Sports"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tech"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Food"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Gaming"));
        mTabLayout.addTab(mTabLayout.newTab().setText("NSFW"));
        mAdapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setHorizontalScrollBarEnabled(true);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabPosition = tab.getPosition();
                mViewPager.setCurrentItem(mTabPosition);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        handleIntent(getIntent());

        mViewPager.setCurrentItem(mTabPosition);

    }

    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchEnabled = true;
                mQuery = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch (item.getItemId()) {
            case R.id.home:
                fragmentManager.popBackStackImmediate();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.photoTitle:
                args.putString(CATEGORY_KEY,"photo");
                break;
            case R.id.comicsTitle:
                args.putString(CATEGORY_KEY,"comics");
                break;
            case R.id.stuffTitle:
                args.putString(CATEGORY_KEY,"stuff");
                break;
            case R.id.filmTitle:
                args.putString(CATEGORY_KEY,"film");
                break;
            case R.id.festivalsTitle:
                args.putString(CATEGORY_KEY,"festivals");
                break;
            case R.id.galleryTitle:
                args.putString(CATEGORY_KEY,"gallery");
                break;
            case R.id.noiseyTitle:
                args.putString(CATEGORY_KEY,"noisey");
                break;
            case R.id.interviewsTitle:
                args.putString(CATEGORY_KEY,"interviews");
                break;
            case R.id.cultureTitle:
                args.putString(CATEGORY_KEY,"culture");
                break;
            case R.id.columnTitle:
                args.putString(CATEGORY_KEY,"column");
                break;
            default:
                args.putString(CATEGORY_KEY,"news");
                break;
        }

        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.content_main_layout, fragment)
                .addToBackStack(null)
                    .commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
        } else {
        }
        return newAccount;
    }

//    Check to confirm network connectivity
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
