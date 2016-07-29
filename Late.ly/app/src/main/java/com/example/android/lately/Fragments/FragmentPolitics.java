package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.ParentCard;
import com.example.android.lately.R;
import com.example.android.lately.Singleton;

import java.util.ArrayList;


public class FragmentPolitics extends Fragment {

    boolean mPortrait;
    RecyclerView mRecyclerView;
    CardAdapter mAdapter;
    ArrayList<ParentCard> mParentCardArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        Singleton instance = Singleton.getInstance();
        instance.getArrayList(CardAdapter.TAB_POLITICS);


        int portrait = getResources().getConfiguration().orientation;
        if (portrait == 1) {
            mPortrait = true;
        } else {
            mPortrait = false;
        }
        RecyclerView(mParentCardArrayList);
        return rootView;
    }

    public void RecyclerView(ArrayList<ParentCard> arrayList) {
        //no clue what this does
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new CardAdapter(arrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

}