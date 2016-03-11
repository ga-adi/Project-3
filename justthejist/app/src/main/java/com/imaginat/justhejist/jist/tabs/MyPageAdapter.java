package com.imaginat.justhejist.jist.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.imaginat.justhejist.jist.api.nyt.Section;

/**
 * Created by generalassembly on 3/9/16.
 */
public class MyPageAdapter extends FragmentStatePagerAdapter {
  int mNumOfTabs;

  public MyPageAdapter(FragmentManager fm, int NumOfTabs) {
    super(fm);
    this.mNumOfTabs = NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {

      TabFragment1 tab1 = new TabFragment1();
      Bundle bundle = new Bundle();
      bundle.putString("SECTION", Section.getSections()[position]);
      tab1.setArguments(bundle);
      return tab1;

  }

  @Override
  public int getCount() {
    return mNumOfTabs;
  }
}
