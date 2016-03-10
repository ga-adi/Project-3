package com.example.android.lately;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.lately.Fragments.Fragment1;
import com.example.android.lately.Fragments.FragmentAnimals;
import com.example.android.lately.Fragments.FragmentCareer;
import com.example.android.lately.Fragments.FragmentDancing;
import com.example.android.lately.Fragments.FragmentDating;
import com.example.android.lately.Fragments.FragmentFood;
import com.example.android.lately.Fragments.FragmentLanguage;
import com.example.android.lately.Fragments.FragmentNature;
import com.example.android.lately.Fragments.FragmentPolitics;
import com.example.android.lately.Fragments.FragmentSports;
import com.example.android.lately.Fragments.FragmentTech;

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
                FragmentAnimals tab2 = new FragmentAnimals();
                return tab2;
            case 2:
                FragmentCareer tab3 = new FragmentCareer();
                return tab3;
            case 3:
                FragmentDancing tab4 = new FragmentDancing();
                return tab4;
            case 4:
                FragmentDating tab5 = new FragmentDating();
                return tab5;
            case 5:
                FragmentFood tab6 = new FragmentFood();
                return tab6;
            case 6:
                FragmentLanguage tab7 = new FragmentLanguage();
                return tab7;
            case 7:
                FragmentNature tab8 = new FragmentNature();
                return tab8;
            case 8:
                FragmentPolitics tab9 = new FragmentPolitics();
                return tab9;
            case 9:
                FragmentSports tab10 = new FragmentSports();
                return tab10;
            case 10:
                FragmentTech tab11 = new FragmentTech();
                return tab11;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabNumber;
    }
}
