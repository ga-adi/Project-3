package com.example.project3vice.section_fragments;

import android.content.Context;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project3vice.ArticleRecyclerViewAdapter;
import com.example.project3vice.MainActivity;
import com.example.project3vice.PagerAdapter;
import com.example.project3vice.R;
import com.example.project3vice.SyncClasses.ViceArticleContentProvider;
import com.example.project3vice.vice_classes.GetLatestByCategoryAsyncTask;
import com.example.project3vice.vice_classes.ViceArticle;

import java.util.ArrayList;

public class NewsFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mCategory;
    private Context mContext;
    private StockContentObserver mObserver;
    public ArrayList<ViceArticle> mArrayOfArticlesByCategory;
    private ArrayList<ViceArticle> mArrayOfArticlesBySearch;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

//        //Fragment content's category determined by argument passed in PagerAdapter class and used
        // to execute AsyncTask pulling list of articles to show in homescreen
        if (!mArrayOfArticlesByCategory.isEmpty()) {
            mArrayOfArticlesByCategory.clear();
        }

        if (getArguments().getString(PagerAdapter.CATEGORY_KEY) != null) {
            mCategory = getArguments().getString(PagerAdapter.CATEGORY_KEY);
        }


        if (isDeviceOnline()) {
            GetLatestByCategoryAsyncTask getArticles = new GetLatestByCategoryAsyncTask();
            try {
                mArrayOfArticlesByCategory = getArticles.execute(mCategory).get();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network connection detected!", Toast.LENGTH_SHORT).show();
        }

        //Instantiate RecyclerView & associated content via Adapter
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new ArticleRecyclerViewAdapter(mArrayOfArticlesByCategory);
        mRecyclerView.setAdapter(mAdapter);

        //Set observer to watch for updates to articles
        mObserver = new StockContentObserver(new Handler());
        mContext.getContentResolver().registerContentObserver(ViceArticleContentProvider.CONTENT_URI, true, mObserver);

        return rootView;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mContext = context;
        mArrayOfArticlesByCategory = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        mContext.getContentResolver().unregisterContentObserver(mObserver);
        super.onDestroy();
    }

    //Observer updates the collection of Vice Articles displayed on screen when the database set
    // changes via Sync
    class StockContentObserver extends ContentObserver {
        public StockContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            GetLatestByCategoryAsyncTask getLatestByCategoryAsyncTask = new GetLatestByCategoryAsyncTask();
            try{
                mArrayOfArticlesByCategory.clear();
                getLatestByCategoryAsyncTask.execute(mCategory);
                mAdapter = new ArticleRecyclerViewAdapter(mArrayOfArticlesByCategory);
                mRecyclerView.setAdapter(mAdapter);
            } catch (Throwable e){
                e.printStackTrace();
            }
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }
    }

    //Check to confirm network connectivity
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onResume() {
        super.onResume();
        mArrayOfArticlesBySearch = new ArrayList<>();
        if (MainActivity.mSearchEnabled) {
            for (ViceArticle article : mArrayOfArticlesByCategory) {
                String title = article.getTitle().toString();
                if (title.contains(MainActivity.mQuery)) {
                    mArrayOfArticlesBySearch.add(article);
                }
            }

            mAdapter = new ArticleRecyclerViewAdapter(mArrayOfArticlesBySearch);
            mRecyclerView.setAdapter(mAdapter);

            MainActivity.mSearchEnabled = false;
        } else {
            mAdapter = new ArticleRecyclerViewAdapter(mArrayOfArticlesByCategory);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
