package com.example.android.lately;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.lately.Fragments.DetailsFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    Window mWindow;
    Toolbar mMainToolbar;
    boolean mPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWindow = this.getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));

        mMainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));

        TabLayout.Tab tab1 = tabLayout.newTab();
        ImageView imageViewTest = new ImageView(this);
        imageViewTest.setImageResource(android.R.drawable.ic_btn_speak_now);
        imageViewTest.setPadding(50, 50, 50, 50);
        imageViewTest.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag1));
        tab1.setCustomView(imageViewTest);
        tabLayout.addTab(tab1);

        View view = (View) imageViewTest.getParent();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        view.setPadding(0, 0, 0, 0);
        view.setLayoutParams(lp);

        //textView
//        final TextView textTest = new TextView(this);
//        textTest.setText("MAKE IT SO");
//        textTest.setPadding(50, 50, 50, 50);
//        textTest.setBackgroundColor(Color.YELLOW);
//        tab1.setCustomView(textTest);
//        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        ImageView imageViewTest2 = new ImageView(this);
        imageViewTest2.setImageResource(android.R.drawable.ic_menu_call);
        imageViewTest2.setPadding(50, 50, 50, 50);
        imageViewTest2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag2));
        tab2.setCustomView(imageViewTest2);
        tabLayout.addTab(tab2);

        View view2 = (View) imageViewTest2.getParent();
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view2.setPadding(0, 0, 0, 0);
        view2.setLayoutParams(lp2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        ImageView imageViewTest3 = new ImageView(this);
        imageViewTest3.setImageResource(android.R.drawable.ic_dialog_map);
        imageViewTest3.setPadding(50, 50, 50, 50);
        imageViewTest3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag3));
        tab3.setCustomView(imageViewTest3);
        tabLayout.addTab(tab3);

        View view3 = (View) imageViewTest3.getParent();
        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view3.setPadding(0, 0, 0, 0);
        view3.setLayoutParams(lp3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        ImageView imageViewTest4 = new ImageView(this);
        imageViewTest4.setImageResource(android.R.drawable.ic_lock_lock);
        imageViewTest4.setPadding(50, 50, 50, 50);
        imageViewTest4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag4));
        tab4.setCustomView(imageViewTest4);
        tabLayout.addTab(tab4);

        View view4 = (View) imageViewTest4.getParent();
        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view4.setPadding(0, 0, 0, 0);
        view4.setLayoutParams(lp4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        ImageView imageViewTest5 = new ImageView(this);
        imageViewTest5.setImageResource(android.R.drawable.ic_media_play);
        imageViewTest5.setPadding(50, 50, 50, 50);
        imageViewTest5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag5));
        tab5.setCustomView(imageViewTest5);
        tabLayout.addTab(tab5);

        View view5 = (View) imageViewTest5.getParent();
        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view5.setPadding(0, 0, 0, 0);
        view5.setLayoutParams(lp5);

//        adds tabs to tablayout bar
//        tabLayout.addTab(tabLayout.newTab().setText("Boop"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(5);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (viewPager.getCurrentItem()) {
                    case 0:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mMainToolbar.setTitle(R.string.frag1text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
                        break;
                    case 1:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mMainToolbar.setTitle(R.string.frag2text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag2));
                        break;
                    case 2:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mMainToolbar.setTitle(R.string.frag3text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag3));
                        break;
                    case 3:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mMainToolbar.setTitle(R.string.frag4text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag4));
                        break;
                    case 4:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag5));
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

        int portrait = getResources().getConfiguration().orientation;
        if (portrait == 1) {
            mPortrait = true;
        } else {
            mPortrait = false;
        }

        GraphRequest request= new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        String obj = response.getJSONObject().toString();
                        try {
                            String message1 = response.getJSONObject().getJSONArray("data").getJSONObject(0).getString("message");
                            String message2 = response.getJSONObject().getJSONArray("data").getJSONObject(1).getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        request.executeAsync();



//        if (mPortrait) {
//        } else {
//            Fragment fragment = new DetailsFragment();
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.detailsFragmentContainer, fragment);
//            transaction.commit();
//        }
    }
}
