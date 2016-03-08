package com.charlesdrews.hud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesdrews.hud.HudCardData.CardData;
import com.charlesdrews.hud.HudCardData.CardType;
import com.charlesdrews.hud.HudCardData.TwitterCardData;
import com.charlesdrews.hud.HudCardData.WeatherCardData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;

    //"http://api.nytimes.com/svc/search/v2/articlesearch.json?q=new+york&page=1&sort=newest&api-key=29975101513df1dfdf9895c3324ca6d2:6:74605150";

    @Override
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

        mAdapter = new RecyclerAdapter(mCardsData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
}
