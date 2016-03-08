package com.charlesdrews.hud;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesdrews.hud.Twitter.TwitterCardData;
import com.charlesdrews.hud.Weather.WeatherCardData;
import com.charlesdrews.hud.Weather.WeatherContentProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        mCardsData = new ArrayList<>();

        //TODO - remove this - it's for testing only
        mCardsData.add(new WeatherCardData(CardType.Weather, 52, 37));
        mCardsData.add(new TwitterCardData(CardType.Twitter, "Kyle", "Twitter is my favorite!"));

        //TODO - consider using a hashmap to keep track of which card is in which position in the adapter

        mAdapter = new RecyclerAdapter(mCardsData);
        recyclerView.setAdapter(mAdapter);

        getContentResolver().registerContentObserver(
                WeatherContentProvider.CONTENT_URI,
                true,
                new CardContentObserver(new Handler())
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //TODO - launch settings activity
                return true;
            /*
            case R.id.search:
                //TODO - setup and launch search
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CardContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public CardContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            //TODO - request data from content resolver using specified URI
            //TODO - update the adapter with this data (need to be careful to update the correct card)
        }
    }
}
