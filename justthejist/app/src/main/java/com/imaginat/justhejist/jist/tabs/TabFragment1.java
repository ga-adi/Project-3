package com.imaginat.justhejist.jist.tabs;

/**
 * Created by generalassembly on 3/9/16.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.customLayouts.NewsArticleListAdapter;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.imaginat.justhejist.jist.onDemandUpdate.NYTimesGetData;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment implements NYTimesGetData.NYTimesDataReceivedInterface {

    private RecyclerView mRecyclerView;
    private NewsArticleListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.tab_fragment_1, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        //
        //        // use a linear layout manager
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        int orientation=this.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            //code for portrait mode
            llm.setOrientation(LinearLayoutManager.VERTICAL);
        }
        else{
            //code for landscape mode
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        mLayoutManager=llm;
         mRecyclerView.setLayoutManager(mLayoutManager);

        //
        //        // specify an adapter (see also next example)

        mAdapter = new NewsArticleListAdapter(new ArrayList<NewsStory>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NYTimesGetData dataRetriever = new NYTimesGetData(this);
        String section  = getArguments().getString("SECTION");
        dataRetriever.execute(section);
    }

    @Override
    public void onCompleted(List<NewsStory>stories) {
        ArrayList<NewsStory>newsStories=(ArrayList)stories;
        mAdapter.swap(newsStories);
    }

    @Override
    public Context resolveContext() {
        return getContext();
    }
}
