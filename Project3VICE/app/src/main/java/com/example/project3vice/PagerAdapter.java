package com.example.project3vice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.project3vice.section_fragments.NewsFragment;

/**
 * Created by David on 3/7/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public static final String CATEGORY_KEY = "category";
    int mNumOfTabs;
    private static NewsFragment fragment;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new NewsFragment();
        Bundle args = new Bundle();

        switch (position) {
            case 0:
                args.putString(CATEGORY_KEY,"news");
                break;
            case 1:
                args.putString(CATEGORY_KEY,"music");
                break;
            case 2:
                args.putString(CATEGORY_KEY,"fashion");
                break;
            case 3:
                args.putString(CATEGORY_KEY,"travel");
                break;
            case 4:
                args.putString(CATEGORY_KEY,"sports");
                break;
            case 5:
                args.putString(CATEGORY_KEY,"tech");
                break;
            case 6:
                args.putString(CATEGORY_KEY,"food");
                break;
            case 7:
                args.putString(CATEGORY_KEY,"gaming");
                break;
            case 8:
                args.putString(CATEGORY_KEY,"nsfw");
                break;
            default:
                return null;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}