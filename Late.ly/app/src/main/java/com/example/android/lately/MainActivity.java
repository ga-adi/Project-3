package com.example.android.lately;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Toolbar mMainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMainToolbar);

//        sets the top bar text and color
        setTitle("Does this work?");
        mMainToolbar.setBackgroundColor(Color.YELLOW);



//        creates the tabbar
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(Color.YELLOW);

//        creating the first tab
        TabLayout.Tab tab1 = tabLayout.newTab();
        ImageView imageViewTest = new ImageView(this);
        imageViewTest.setImageResource(android.R.drawable.ic_btn_speak_now);
        imageViewTest.setPadding(50, 50, 50, 50);
        imageViewTest.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tab1.setCustomView(imageViewTest);
        tabLayout.addTab(tab1);
//        final TextView textTest = new TextView(this);
//        textTest.setText("MAKE IT SO");
//        textTest.setPadding(50, 50, 50, 50);
//        textTest.setBackgroundColor(Color.YELLOW);
//        tab1.setCustomView(textTest);
//        tabLayout.addTab(tab1);

//        gets parent view and gets ride of padding in tab text
        View view = (View) imageViewTest.getParent();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        view.setPadding(0, 0, 0, 0);
        view.setLayoutParams(lp);

        TabLayout.Tab tab2 = tabLayout.newTab();
        TextView textTest2 = new TextView(this);
        textTest2.setText("BOOP");
        textTest2.setPadding(50, 50, 50, 50);
        textTest2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tab2.setCustomView(textTest2);
        tabLayout.addTab(tab2);

        View view2 = (View) textTest2.getParent();
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view2.setPadding(0, 0, 0, 0);
        view2.setLayoutParams(lp2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        TextView textTest3 = new TextView(this);
        textTest3.setText("MRAB");
        textTest3.setPadding(50, 50, 50, 50);
        textTest3.setBackgroundColor(Color.MAGENTA);
        tab3.setCustomView(textTest3);
        tabLayout.addTab(tab3);

        View view3 = (View) textTest3.getParent();
        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view3.setPadding(0, 0, 0, 0);
        view3.setLayoutParams(lp3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        TextView textTest4 = new TextView(this);
        textTest4.setText("BAPPP");
        textTest4.setPadding(50, 50, 50, 50);
        textTest4.setBackgroundColor(Color.BLACK);
        tab4.setCustomView(textTest4);
        tabLayout.addTab(tab4);

        View view4 = (View) textTest4.getParent();
        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view4.setPadding(0, 0, 0, 0);
        view4.setLayoutParams(lp4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        TextView textTest5 = new TextView(this);
        textTest5.setText("BOOP");
        textTest5.setPadding(50, 50, 50, 50);
        textTest5.setBackgroundColor(Color.RED);
        tab5.setCustomView(textTest5);
        tabLayout.addTab(tab5);

        View view5 = (View) textTest5.getParent();
        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view5.setPadding(0, 0, 0, 0);
        view5.setLayoutParams(lp5);


//        adds tabs to tablayout bar
//        tabLayout.addTab(tabLayout.newTab().setText("Boop"));
//        tabLayout.addTab(tabLayout.newTab().setText("Grar"));
//        tabLayout.addTab(tabLayout.newTab().setText("Marl"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                somehow here get the color from the tabs and fit it into setbackground color
                Log.d("Test", String.valueOf(tab.getPosition()));
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        mMainToolbar.setBackgroundColor(Color.YELLOW);
                        tabLayout.setBackgroundColor(Color.YELLOW);

                        break;
                    case 1:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                        tabLayout.setBackgroundColor(Color.BLUE);

                        break;
                    case 2:
                        mMainToolbar.setBackgroundColor(Color.MAGENTA);
                        tabLayout.setBackgroundColor(Color.MAGENTA);

                        break;
                    case 3:
                        mMainToolbar.setBackgroundColor(Color.BLACK);
                        tabLayout.setBackgroundColor(Color.BLACK);

                        break;
                    case 4:
                        mMainToolbar.setBackgroundColor(Color.RED);
                        tabLayout.setBackgroundColor(Color.RED);
                        break;
                    default:
                }
                Log.d("Test2", String.valueOf(viewPager.getCurrentItem()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
