package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.EventCard;
import com.example.android.lately.Cards.ParentCard;
import com.example.android.lately.Cards.SmallArticleCard;
import com.example.android.lately.Cards.WeatherCard;
import com.example.android.lately.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    boolean mPortrait;
    RecyclerView mTestRecyclerView;
    CardAdapter mTestAdapter;
    ArrayList<ParentCard> mTestArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        mTestRecyclerView = (RecyclerView) rootView.findViewById(R.id.testRecyclerView);

        int portrait = getResources().getConfiguration().orientation;
        if (portrait == 1) {
            mPortrait = true;
        } else {
            mPortrait = false;
        }

        RecyclerView();
        return rootView;
    }

    public void RecyclerView(){

        //Dummy ArrayList
        mTestArray = new ArrayList<ParentCard>();
        mTestArray.add(new WeatherCard("99", "SUNNY","FLORIDA","SEPT 12 1991", new String[]{"MON","TUE","WED","THU","FRI"}, new String[]{"MON","TUE","WED","THU","FRI"}, new String[]{"98","78","65","75","80"}, new String[]{"75","63","60","70","75"}, CardAdapter.TYPE_WEATHER,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new EventCard("10:10","ADI", "MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new EventCard("10:10","ADI", "MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new EventCard("10:10","ADI", "1MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new EventCard("10:10","ADI", "2MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new SmallArticleCard("10:10","ADI", "21 Awesome Programmers",CardAdapter.TYPE_SMALL_ARTICLE,CardAdapter.TAB_MAINPAGE, 2));
//        mTestArray.add(new SmallArticleCard("10:10","ADI", "21 Awesome Programmers",CardAdapter.TYPE_SMALL_ARTICLE,CardAdapter.TAB_MAINPAGE, 2));
//        mTestArray.add(new EventCard("10:10","ADI", "5MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));
//        mTestArray.add(new SmallArticleCard("10:10", "6MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_SMALL_ARTICLE,CardAdapter.TAB_MAINPAGE, 2));
//        mTestArray.add(new EventCard("10:10","ADI", "7MasterClass with James", "21 Awesome Programmers",CardAdapter.TYPE_EVENT,CardAdapter.TAB_MAINPAGE, 1));

        //no clue what this does
        mTestRecyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mTestRecyclerView.setLayoutManager(llm);

        mTestAdapter = new CardAdapter(mTestArray);
        mTestRecyclerView.setAdapter(mTestAdapter);
    }
}