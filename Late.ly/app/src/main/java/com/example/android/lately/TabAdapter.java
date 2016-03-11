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

import java.util.ArrayList;

/**
 * Created by ShowMe on 3/7/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    int mTabNumber;
    ArrayList<String> mSelections = new ArrayList<String>();


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
                Fragment tab2 = getFragmentForCategory(Selections().get(0));
                //                Fragment tab2 = new Fragment1();
                return tab2;
            case 2:
                Fragment tab3 = getFragmentForCategory(Selections().get(1));
//                Fragment tab3 = new Fragment1();

                return tab3;
            case 3:
                Fragment tab4 = getFragmentForCategory(Selections().get(2));
//                Fragment tab4 = new Fragment1();

                return tab4;
            case 4:
                Fragment tab5 = getFragmentForCategory(Selections().get(3));
//                Fragment tab5 = new Fragment1();

                return tab5;
            case 5:
                Fragment tab6 = getFragmentForCategory(Selections().get(4));
//                Fragment tab6 = new Fragment1();

                return tab6;
            case 6:
                Fragment tab7 = getFragmentForCategory(Selections().get(5));
//                Fragment tab7 = new Fragment1();

                return tab7;
            case 7:
                Fragment tab8 = getFragmentForCategory(Selections().get(6));
//                Fragment tab8 = new Fragment1();

                return tab8;
            case 8:
                Fragment tab9 = getFragmentForCategory(Selections().get(7));
//                Fragment tab9 = new Fragment1();

                return tab9;
            case 9:
                Fragment tab10 = getFragmentForCategory(Selections().get(8));
//                Fragment tab10 = new Fragment1();

                return tab10;
            case 10:
                Fragment tab11 = getFragmentForCategory(Selections().get(9));
//                Fragment tab11 = new Fragment1();

                return tab11;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabNumber;
    }

    public Fragment getFragmentForCategory(String category){
        Fragment fragment;


        switch (category){
            case "Animals":
                fragment = new FragmentAnimals();
                return fragment;
            case "Career":
                fragment = new FragmentCareer();
                return fragment;
            case "Dancing":
                fragment = new FragmentDancing();
                return fragment;
            case "Dating":
                fragment = new FragmentDating();
                return fragment;
            case "Food":
                fragment = new FragmentFood();
                return fragment;
            case "Language":
                fragment = new FragmentLanguage();
                return fragment;
            case "Nature":
                fragment = new FragmentNature();
                return fragment;
            case "Politics":
                fragment = new FragmentPolitics();
                return fragment;
            case "Sports":
                fragment = new FragmentSports();
                return fragment;
            case "Tech":
                fragment = new FragmentTech();
                return fragment;
        }
        return null;
    }

    public ArrayList<String> Selections(){
        mSelections.add("bah");
        mSelections.add("bah");
        mSelections.add("bah");
        mSelections.add("bah");
        mSelections.add("bah");
        return mSelections;
    }
}


