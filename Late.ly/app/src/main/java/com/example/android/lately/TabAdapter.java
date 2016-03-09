package com.example.android.lately;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.lately.Fragments.Fragment1;
import com.example.android.lately.Fragments.Fragment2;
import com.example.android.lately.Fragments.Fragment3;
import com.example.android.lately.Fragments.Fragment4;
import com.example.android.lately.Fragments.Fragment5;

/**
 * Created by ShowMe on 3/7/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    int mTabNumber;

    public TabAdapter(FragmentManager fm, int tabNumber) {
        super(fm);
        this.mTabNumber = tabNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment1 tab1 = new Fragment1();
                return tab1;
            case 1:
                Fragment2 tab2 = new Fragment2();
                return tab2;
            case 2:
                Fragment3 tab3 = new Fragment3();
                return tab3;
            case 3:
                Fragment4 tab4 = new Fragment4();
                return tab4;
            case 4:
                Fragment5 tab5 = new Fragment5();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabNumber;
    }
}
