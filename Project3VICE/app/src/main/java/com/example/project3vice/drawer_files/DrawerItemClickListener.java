package com.example.project3vice.drawer_files;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.project3vice.section_fragments.NewsFragment;

/**
 * Created by austinjones on 3/4/16.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        Fragment fragment = null;

        Bundle args;

        switch (position) {
            case 0:
                NewsFragment dndFragment = new NewsFragment();
                args = new Bundle();
                args.putString("category","dnd");
                dndFragment.setArguments(args);
//            case 1:
//                fragment = new MusicFragment();
//            case 2:
//                fragment = new FashionFragment();
//            case 3:
//                fragment = new TravelFragment();
//            case 4:
//                fragment = new SportsFragment();
//            case 5:
//                fragment = new TechFragment();
//            case 6:
//                fragment = new FoodFragment();
//            case 7:
//                fragment = new GamingFragment();
//            case 8:
//                fragment = new NSFWFragment();
//            case 9:
//                fragment = new DDFragment();

            default:
                break;
        }

        if (fragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
//            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
        }
    }

