package com.example.android.lately;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.lately.Cards.RedditComment;
import com.example.android.lately.Forecast.Weather;
import com.example.android.lately.Fragments.DetailsFragment;
import com.example.android.lately.Reddit.RedditArticle.Comments.CommentProcessor;
import com.example.android.lately.Reddit.RedditArticle.Data;
import com.example.android.lately.Reddit.RedditArticle.RedditArticle;
import com.example.android.lately.Reddit.RedditArticle.RedditResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Window mWindow;
    Toolbar mMainToolbar;
    boolean mPortrait;
    ArrayList<RedditComment> mComments;
    CommentAsyncTask commentAsyncTask;

    ProgressBar progressbar;

    private static String mForecastUrl = "https://api.forecast.io/forecast/39a42687f8dbe7c14cd4f97d201af744/";
    private static String mRedditUrl = "https://www.reddit.com/r/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        mWindow = this.getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));

        mMainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));

        TabLayout.Tab tab1 = tabLayout.newTab();
        ImageView imageViewTest = new ImageView(this);
        imageViewTest.setImageResource(android.R.drawable.ic_btn_speak_now);
        imageViewTest.setPadding(50, 50, 50, 50);
        imageViewTest.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag1));
        tab1.setCustomView(imageViewTest);
        tabLayout.addTab(tab1);

        View view = (View) imageViewTest.getParent();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        view.setPadding(0, 0, 0, 0);
        view.setLayoutParams(lp);

        //textView
//        final TextView textTest = new TextView(this);
//        textTest.setText("MAKE IT SO");
//        textTest.setPadding(50, 50, 50, 50);
//        textTest.setBackgroundColor(Color.YELLOW);
//        tab1.setCustomView(textTest);
//        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        ImageView imageViewTest2 = new ImageView(this);
        imageViewTest2.setImageResource(android.R.drawable.ic_menu_call);
        imageViewTest2.setPadding(50, 50, 50, 50);
        imageViewTest2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag2));
        tab2.setCustomView(imageViewTest2);
        tabLayout.addTab(tab2);

        View view2 = (View) imageViewTest2.getParent();
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view2.setPadding(0, 0, 0, 0);
        view2.setLayoutParams(lp2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        ImageView imageViewTest3 = new ImageView(this);
        imageViewTest3.setImageResource(android.R.drawable.ic_dialog_map);
        imageViewTest3.setPadding(50, 50, 50, 50);
        imageViewTest3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag3));
        tab3.setCustomView(imageViewTest3);
        tabLayout.addTab(tab3);

        View view3 = (View) imageViewTest3.getParent();
        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view3.setPadding(0, 0, 0, 0);
        view3.setLayoutParams(lp3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        ImageView imageViewTest4 = new ImageView(this);
        imageViewTest4.setImageResource(android.R.drawable.ic_lock_lock);
        imageViewTest4.setPadding(50, 50, 50, 50);
        imageViewTest4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag4));
        tab4.setCustomView(imageViewTest4);
        tabLayout.addTab(tab4);

        View view4 = (View) imageViewTest4.getParent();
        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view4.setPadding(0, 0, 0, 0);
        view4.setLayoutParams(lp4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        ImageView imageViewTest5 = new ImageView(this);
        imageViewTest5.setImageResource(android.R.drawable.ic_media_play);
        imageViewTest5.setPadding(50, 50, 50, 50);
        imageViewTest5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag5));
        tab5.setCustomView(imageViewTest5);
        tabLayout.addTab(tab5);

        View view5 = (View) imageViewTest5.getParent();
        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) view.getLayoutParams();
        view5.setPadding(0, 0, 0, 0);
        view5.setLayoutParams(lp5);

//        adds tabs to tablayout bar
//        tabLayout.addTab(tabLayout.newTab().setText("Boop"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(5);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (viewPager.getCurrentItem()) {
                    case 0:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mMainToolbar.setTitle(R.string.frag1text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag1));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag1));
                        break;
                    case 1:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mMainToolbar.setTitle(R.string.frag2text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag2));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag2));
                        break;
                    case 2:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mMainToolbar.setTitle(R.string.frag3text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag3));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag3));
                        break;
                    case 3:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mMainToolbar.setTitle(R.string.frag4text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag4));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag4));
                        break;
                    case 4:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFrag5));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFrag5));
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        int portrait = getResources().getConfiguration().orientation;
        if (portrait == 1) {
            mPortrait = true;
        } else {
            mPortrait = false;
        }

        if (mPortrait) {
        } else {
            Fragment fragment = new DetailsFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.detailsFragmentContainer, fragment);
            transaction.commit();
        }


        }


    public void getForecastApi(){
        String latitude = String.valueOf(mLastLocation.getLatitude()).substring(0, 7);
        String longitude = String.valueOf(mLastLocation.getLongitude()).substring(0, 8);
        String latlon = latitude+","+longitude;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mForecastUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherRequest weatherRequest = retrofit.create(WeatherRequest.class);
        Call<Weather> result = weatherRequest.getWeather(latlon);
        result.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                //Getting current weather status

                String currentTemperature = String.valueOf(response.body().getCurrently().getTemperature());
                String currentSummary = response.body().getCurrently().getSummary();
                String currentLocation = response.body().getTimezone();

                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hha z, EEE");
                format.setTimeZone(TimeZone.getTimeZone("GMT"+response.body().getOffset()));
                Date currentDate = new Date((long)(response.body().getCurrently().getTime() * 1000L));
                String formattedCurrentDate = format.format(currentDate);


                //Getting next five days' weather
                String[] nextFiveDaysDates = new String[5];
                String[] nextFiveDaysSummary = new String[5];
                String[] nextFiveDaysHighTemp = new String[5];
                String[] nextFiveDaysLowTemp = new String[5];
                for(int i=0; i<5; i++){
                    Date nextDayTime = new Date((long)(response.body().getDaily().getData().get(i+1).getTime() * 1000L));
                    String formattedDate = format.format(nextDayTime);
                    nextFiveDaysDates[i] = formattedDate;
                    //If you want to just extract day names(i.e. Tue), comment out the line above this and uncomment the below one.
                    //nextFiveDaysDates[i] = formattedDate.substring(formattedDate.length()-3,formattedDate.length());

                    nextFiveDaysSummary[i] = response.body().getDaily().getData().get(i+1).getSummary();
                    nextFiveDaysHighTemp[i] = String.valueOf(response.body().getDaily().getData().get(i+1).getTemperatureMax());
                    nextFiveDaysHighTemp[i] = String.valueOf(response.body().getDaily().getData().get(i+1).getTemperatureMin());
                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRedditApi(String subreddit){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mRedditUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ArrayList<String> urlList = new ArrayList<>();

        RedditRequest redditRequest = retrofit.create(RedditRequest.class);
        Call<RedditResult> result = redditRequest.getRedditFeed(subreddit);
        result.enqueue(new Callback<RedditResult>() {
            @Override
            public void onResponse(Call<RedditResult> call, Response<RedditResult> response) {

                List<RedditArticle> result = response.body().getData().getChildren();
                String articleAuthor,articleUrl,articleSubreddit,articleContent, articleTitle, articleTime;
                int articleScore, articleNumOfComment, idNumber;
                for(int i=0; i<result.size(); i++){
                    articleAuthor = result.get(i).getData().getAuthor();
                    articleSubreddit = result.get(i).getData().getSubreddit();
                    articleTitle = result.get(i).getData().getTitle();
                    articleContent = result.get(i).getData().getSelftext();
                    articleUrl = result.get(i).getData().getUrl();
                    articleNumOfComment = result.get(i).getData().getNumComments();
                    articleScore = result.get(i).getData().getScore();
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hha, EEE");
                    Date currentDate = new Date((long)(result.get(i).getData().getCreated() * 1000L));
                    articleTime = format.format(currentDate);
                    articleSubreddit = result.get(i).getData().getSubreddit();
                    idNumber = i+1;


                    //This background method clears the mComments ArrayList(member variable) and stuffs new comment lists.
                    //So you don't need to instantiate a new comment ArrayList here.
                    //Just stuff mComment as a parameter of the reddit article constructor after this async task execute method.

                    //Build a new reddit article object here.
                }
            }

            @Override
            public void onFailure(Call<RedditResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public class CommentAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String data = "";
            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
                CommentProcessor commentProcessor = new CommentProcessor(data);
                mComments = new ArrayList<>();
                mComments = commentProcessor.fetchComments();
            }catch (Throwable e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("REDDITARTICLE",mComments.get(1).getmAuthor() +"  "+ mComments.get(1).getmContent());
        }
    }


    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String data = null;
        while ((data = reader.readLine()) != null) {
            builder.append(data);
        }
        reader.close();
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MainActivity.this, "Please allow this app to access to your location", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mLastLocation != null) {
                getForecastApi();
                getRedditApi("Fitness"); //This parameter is a place holder. We'll change it into the user topic
//                    getFoursquareApi();
//                    getMeetupApi();
            } else {
                Toast.makeText(MainActivity.this, "Turn on GPS and try again", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "No network connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface WeatherRequest{
        @GET("{latlon}/")
        Call<Weather> getWeather(@Path("latlon") String latlon);
    }

    public interface RedditRequest{
        @GET("{subreddit}/.json")
        Call<RedditResult> getRedditFeed(@Path("subreddit") String subreddit);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}
