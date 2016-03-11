package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.EventCard;
import com.example.android.lately.Cards.FoursquareCard;
import com.example.android.lately.Cards.ParentCard;
import com.example.android.lately.Cards.RedditCard;
import com.example.android.lately.Cards.RedditComment;
import com.example.android.lately.Cards.SmallArticleCard;
import com.example.android.lately.Cards.WeatherCard;
import com.example.android.lately.R;
import com.example.android.lately.Singleton;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    boolean mPortrait;
    RecyclerView mRecyclerView;
    CardAdapter mAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<ParentCard> mTestArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeOnRefresh);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        int portrait = getResources().getConfiguration().orientation;
        if (portrait == 1) {
            mPortrait = true;
        } else {
            mPortrait = false;
        }

        RecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter = new CardAdapter(mTestArray);
                mRecyclerView.swapAdapter(mAdapter, false);


            }
        });

        return rootView;
    }

    public void RecyclerView(){

        //Dummy ArrayList
        mTestArray = new ArrayList<ParentCard>();
//        RedditCard dummyReddit = new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3);
//        FoursquareCard dummyfoursquare = new FoursquareCard("999 Success and Employement Way", "98", "Fortune Wealth Happiness", "10293i1jnd", "o9je92hoi",CardAdapter.TYPE_FOURSQUARE, CardAdapter.TAB_MAINPAGE, 4);
        Singleton singleton = Singleton.getInstance();
//        singleton.addParentCard(dummyReddit,CardAdapter.TAB_MAINPAGE);
//        singleton.addParentCard(dummyfoursquare,CardAdapter.TAB_MAINPAGE);
        mTestArray.addAll(singleton.getArrayList(CardAdapter.TAB_MAINPAGE));

//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));
//        mTestArray.add(new RedditCard("Jhonny Bananas", new ArrayList<RedditComment>(), "Content", 99, 613,"/r/beautiful", "99:99", "This Project rocks and we are proud", "1092120", CardAdapter.TYPE_REDDIT, CardAdapter.TAB_MAINPAGE, 3));


        //no clue what this does
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        Log.d("array_size", String.valueOf(Singleton.getInstance().getSize()));
        mAdapter = new CardAdapter(mTestArray);
        mRecyclerView.setAdapter(mAdapter);
    }
}