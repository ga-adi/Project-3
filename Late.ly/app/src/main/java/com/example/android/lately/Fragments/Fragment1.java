package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.lately.MeetUpAdapter;
import com.example.android.lately.MeetUpClass;
import com.example.android.lately.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    boolean mPortrait;
    RecyclerView mTestRecyclerView;
    MeetUpAdapter mTestAdapter;
    ArrayList<MeetUpClass> mTestArray;


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

        RecyclerViewTest();
        return rootView;



    }

    public void RecyclerViewTest(){

        //Dummy ArrayList
        mTestArray = new ArrayList<MeetUpClass>();
        mTestArray.add(new MeetUpClass("10:10","ADI", "MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "1MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "2MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "3MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "4MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "5MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "6MasterClass with James", "21 Awesome Programmers"));
        mTestArray.add(new MeetUpClass("10:10","ADI", "7MasterClass with James", "21 Awesome Programmers"));

        //no clue what this does
        mTestRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mTestRecyclerView.setLayoutManager(llm);
        mTestAdapter = new MeetUpAdapter(mTestArray);

        mTestRecyclerView.setAdapter(mTestAdapter);

    }

}