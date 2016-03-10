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
import android.widget.Toast;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.RedditComment;
import com.example.android.lately.Cards.WeatherCard;
import com.example.android.lately.Forecast.Weather;
import com.example.android.lately.Foursquare.FoursquareVenues;
import com.example.android.lately.Fragments.DetailsFragment;
import com.example.android.lately.Reddit.RedditArticle.Comments.CommentProcessor;
import com.example.android.lately.Reddit.RedditArticle.RedditArticle;
import com.example.android.lately.Reddit.RedditArticle.RedditResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Window mWindow;
    Toolbar mMainToolbar;
    boolean mPortrait;
    ArrayList<RedditComment> mComments;
    CommentAsyncTask commentAsyncTask;
    Singleton mSingletonArrayOfParentCards;
    TabLayout mTabLayout;
//    public static String stuff;

    int mSports;
    private static String mForecastUrl = "https://api.forecast.io/forecast/39a42687f8dbe7c14cd4f97d201af744/";
    private static String mRedditUrl = "https://www.reddit.com/r/";
    public static String mFoursquareUrl = "https://api.foursquare.com/v2/venues/search?client_id=JLOYBXMM0G3SFCJASFGDZJTAHOMYOMUO2JDCF0YIOPPYN312&client_secret=PIWMY5DZACK0F5U0J4PIHRUJWVSLGX14R1CPZRNGJXTIGJ35&v=20130815&ll=";
    public static final String mFoursquareEndpoint = "https://api.foursquare.com/";
    public static final String FOURSQUARE_CLIENT_ID = "JLOYBXMM0G3SFCJASFGDZJTAHOMYOMUO2JDCF0YIOPPYN312";
    public static final String FOURSQUARE_CLIENT_SECRET = "PIWMY5DZACK0F5U0J4PIHRUJWVSLGX14R1CPZRNGJXTIGJ35";
    public static final String FOURSQUARE_VERSION_NUMBER = "20130815";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSingletonArrayOfParentCards = Singleton.getInstance();

        mSports = 1;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        mWindow = this.getWindow();
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarker));

        mMainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        ArrayList<String> selection = new ArrayList<>();
        selection.add("Animals");
        selection.add("Tech");
        selection.add("Bwah");
        selection.add("Dating");
        selection.add("Dating");
        selection.add("Food");
        selection.add("Nature");

        createTabs(selection);


//        TabLayout.Tab tab1 = tabLayout.newTab();
//        ImageView imageViewTest = new ImageView(this);
//        imageViewTest.setImageResource(android.R.drawable.ic_btn_speak_now);
//        imageViewTest.setPadding(50, 50, 50, 50);
//        imageViewTest.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag1));
//        tab1.setCustomView(imageViewTest);
//        tabLayout.addTab(tab1);
//        View view = (View) imageViewTest.getParent();
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
//        view.setPadding(0, 0, 0, 0);
//        view.setLayoutParams(lp);
//
//        //textView
////        final TextView textTest = new TextView(this);
////        textTest.setText("MAKE IT SO");
////        textTest.setPadding(50, 50, 50, 50);
////        textTest.setBackgroundColor(Color.YELLOW);
////        tab1.setCustomView(textTest);
////        tabLayout.addTab(tab1);
//
//        TabLayout.Tab tab2 = tabLayout.newTab();
//        ImageView imageViewTest2 = new ImageView(this);
//        imageViewTest2.setImageResource(android.R.drawable.ic_menu_call);
//        imageViewTest2.setPadding(50, 50, 50, 50);
//        imageViewTest2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag2));
//        tab2.setCustomView(imageViewTest2);
//        tabLayout.addTab(tab2);
//
//        View view2 = (View) imageViewTest2.getParent();
//        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.getLayoutParams();
//        view2.setPadding(0, 0, 0, 0);
//        view2.setLayoutParams(lp2);
//
//        TabLayout.Tab tab3 = tabLayout.newTab();
//        ImageView imageViewTest3 = new ImageView(this);
//        imageViewTest3.setImageResource(android.R.drawable.ic_dialog_map);
//        imageViewTest3.setPadding(50, 50, 50, 50);
//        imageViewTest3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag3));
//        tab3.setCustomView(imageViewTest3);
//        tabLayout.addTab(tab3);
//
//        View view3 = (View) imageViewTest3.getParent();
//        LinearLayout.LayoutParams lp3 = (LinearLayout.LayoutParams) view.getLayoutParams();
//        view3.setPadding(0, 0, 0, 0);
//        view3.setLayoutParams(lp3);
//
//        TabLayout.Tab tab4 = tabLayout.newTab();
//        ImageView imageViewTest4 = new ImageView(this);
//        imageViewTest4.setImageResource(android.R.drawable.ic_lock_lock);
//        imageViewTest4.setPadding(50, 50, 50, 50);
//        imageViewTest4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag4));
//        tab4.setCustomView(imageViewTest4);
//        tabLayout.addTab(tab4);
//
//        View view4 = (View) imageViewTest4.getParent();
//        LinearLayout.LayoutParams lp4 = (LinearLayout.LayoutParams) view.getLayoutParams();
//        view4.setPadding(0, 0, 0, 0);
//        view4.setLayoutParams(lp4);
//
//        TabLayout.Tab tab5 = tabLayout.newTab();
//        ImageView imageViewTest5 = new ImageView(this);
//        imageViewTest5.setImageResource(android.R.drawable.ic_media_play);
//        imageViewTest5.setPadding(50, 50, 50, 50);
//        imageViewTest5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFrag5));
//        tab5.setCustomView(imageViewTest5);
//        tabLayout.addTab(tab5);
//
//        View view5 = (View) imageViewTest5.getParent();
//        LinearLayout.LayoutParams lp5 = (LinearLayout.LayoutParams) view.getLayoutParams();
//        view5.setPadding(0, 0, 0, 0);
//        view5.setLayoutParams(lp5);


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


// if ("Sports".equals(tab.getTag());
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarker));
                        break;
                    case 1:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkAnimals));
                        mMainToolbar.setTitle(R.string.frag1text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkAnimals));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerAnimals));
                        break;
                    case 2:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkCareer));
                        mMainToolbar.setTitle(R.string.frag2text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkCareer));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerCareer));
                        break;
                    case 3:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDancing));
                        mMainToolbar.setTitle(R.string.frag3text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDancing));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerDancing));
                        break;
                    case 4:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDating));
                        mMainToolbar.setTitle(R.string.frag4text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDating));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerDating));
                        break;
                    case 5:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFood));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFood));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFood));
                        break;
                    case 6:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkLanguage));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkLanguage));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerLanguage));
                        break;
                    case 7:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkNature));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkNature));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerNature));
                        break;
                    case 8:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkPolitics));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkPolitics));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerPolitics));
                        break;
                    case 9:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkSports));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkSports));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerSports));
                        break;
                    case 10:
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTech));
                        mMainToolbar.setTitle(R.string.frag5text);
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTech));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerTech));
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

    public void getForecastApi() {
        String latitude = String.valueOf(mLastLocation.getLatitude()).substring(0, 7);
        String longitude = String.valueOf(mLastLocation.getLongitude()).substring(0, 8);
        String latlon = latitude + "," + longitude;

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
                format.setTimeZone(TimeZone.getTimeZone("GMT" + response.body().getOffset()));
                Date currentDate = new Date((long) (response.body().getCurrently().getTime() * 1000L));
                String formattedCurrentDate = format.format(currentDate);


                //Getting next five days' weather
                String[] nextFiveDaysDates = new String[5];
                String[] nextFiveDaysSummary = new String[5];
                String[] nextFiveDaysHighTemp = new String[5];
                String[] nextFiveDaysLowTemp = new String[5];
                for (int i = 0; i < 5; i++) {
                    Date nextDayTime = new Date((long) (response.body().getDaily().getData().get(i + 1).getTime() * 1000L));
                    String formattedDate = format.format(nextDayTime);
                    nextFiveDaysDates[i] = formattedDate;
                    //If you want to just extract day names(i.e. Tue), comment out the line above this and uncomment the below one.
                    //nextFiveDaysDates[i] = formattedDate.substring(formattedDate.length()-3,formattedDate.length());

                    nextFiveDaysSummary[i] = response.body().getDaily().getData().get(i + 1).getSummary();
                    nextFiveDaysHighTemp[i] = String.valueOf(response.body().getDaily().getData().get(i + 1).getTemperatureMax());
                    nextFiveDaysLowTemp[i] = String.valueOf(response.body().getDaily().getData().get(i + 1).getTemperatureMin());
                }
                WeatherCard weatherCard = new WeatherCard(currentTemperature, currentSummary, currentLocation,
                        formattedCurrentDate, nextFiveDaysDates, nextFiveDaysSummary, nextFiveDaysHighTemp, nextFiveDaysLowTemp, CardAdapter.TAB_MAINPAGE,
                        CardAdapter.TYPE_WEATHER, 0);

                mSingletonArrayOfParentCards.addParentCard(weatherCard, CardAdapter.TAB_MAINPAGE);

                //TODO : Creating a constructor and stuff String variables above
            }


            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRedditApi(String subreddit) {

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
                String articleAuthor, articleUrl, articleSubreddit, articleContent, articleTitle, articleTime;
                int articleScore, articleNumOfComment, idNumber;

                for (int i = 0; i < result.size(); i++) {
                    articleAuthor = result.get(i).getData().getAuthor();
                    articleSubreddit = result.get(i).getData().getSubreddit();
                    articleTitle = result.get(i).getData().getTitle();
                    articleContent = result.get(i).getData().getSelftext();
                    articleUrl = result.get(i).getData().getUrl();
                    articleNumOfComment = result.get(i).getData().getNumComments();
                    articleScore = result.get(i).getData().getScore();
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hha, EEE");
                    Date currentDate = new Date((long) (result.get(i).getData().getCreated() * 1000L));
                    articleTime = format.format(currentDate);
                    articleSubreddit = result.get(i).getData().getSubreddit();


                    //This background method clears the mComments ArrayList(member variable) and stuffs new comment lists.
                    //So you don't need to instantiate a new comment ArrayList here.
                    //Just stuff mComment as a parameter of the reddit article constructor after this async task execute method.

                    //Build a new reddit article object here.
                    idNumber = i + 1;
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
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
                CommentProcessor commentProcessor = new CommentProcessor(data);
                mComments = new ArrayList<>();
                mComments = commentProcessor.fetchComments();
            } catch (Throwable e) {
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
            Log.d("REDDITARTICLE", mComments.get(1).getmAuthor() + "  " + mComments.get(1).getmContent());
        }
    }

    public void getFoursquareApi(String latlon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mFoursquareEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoursquareRequest foursquareRequest = retrofit.create(FoursquareRequest.class);
        Call<FoursquareVenues> result = foursquareRequest.getVenues(FOURSQUARE_CLIENT_ID, FOURSQUARE_CLIENT_SECRET, FOURSQUARE_VERSION_NUMBER, latlon);
        result.enqueue(new Callback<FoursquareVenues>() {
            @Override
            public void onResponse(Call<FoursquareVenues> call, Response<FoursquareVenues> response) {
                for (int i = 0; i < response.body().getResponse().getVenues().size(); i++) {
                    String venueName = response.body().getResponse().getVenues().get(i).getName();
                    String streetAddress = response.body().getResponse().getVenues().get(i).getLocation().getAddress();
                    String cityAddress = response.body().getResponse().getVenues().get(i).getLocation().getCity();
                    String stateAddress = response.body().getResponse().getVenues().get(i).getLocation().getState();
                    String zipcodeAddress = response.body().getResponse().getVenues().get(i).getLocation().getPostalCode();
                    String venueAddress = stateAddress + ", " + cityAddress + ", " + stateAddress + ", " + zipcodeAddress;

                }
            }

            @Override
            public void onFailure(Call<FoursquareVenues> call, Throwable t) {

            }
        });
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
                String latitude = String.valueOf(mLastLocation.getLatitude()).substring(0, 5);
                String longitude = String.valueOf(mLastLocation.getLongitude()).substring(0, 5);
                String latlon = latitude + "," + longitude;

                //TODO create for loop length of master_string_selection_topic_tab_array. Pass the TAB int inside the API calls
                //TODO Create method get SPECIFICREDDITCALLSWITCHER(TAB) to return to specific topic call required while maintaining the TAB

                getForecastApi();
                getRedditApi("Fitness"); //This parameter is a place holder. We'll change it into the user topic
                getFoursquareApi(latlon);
//                    getMeetupApi();
            } else {
                Toast.makeText(MainActivity.this, "Turn on GPS and try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "No network connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public interface WeatherRequest {
        @GET("{latlon}/")
        Call<Weather> getWeather(@Path("latlon") String latlon);
    }

    public interface RedditRequest {
        @GET("{subreddit}/.json")
        Call<RedditResult> getRedditFeed(@Path("subreddit") String subreddit);
    }


    public interface FoursquareRequest {
        @GET("v2/venues/search")
        public Call<FoursquareVenues> getVenues(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("v") String version, @Query("ll") String ll);

        @GET("v2/venues/{venueId}/photos")
        public Call<FoursquareVenues> getPhotoes(@Path("venueId") String venueId, @Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("v") String version);

    }

    public void createTabs(ArrayList selection) {
        //// TODO: 3/10/16 change "contains" to TAB_BLAH
        if (selection.contains("Animals")) {
            TabLayout.Tab tabAnimals = mTabLayout.newTab();
            ImageView imageViewAnimals = new ImageView(this);
            imageViewAnimals.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabAnimals.setTag("Animals");
            imageViewAnimals.setPadding(50, 50, 50, 50);
            imageViewAnimals.setBackgroundColor(getResources().getColor(R.color.colorPrimaryAnimals));
            tabAnimals.setCustomView(imageViewAnimals);
            mTabLayout.addTab(tabAnimals);
            View viewAnimals = (View) imageViewAnimals.getParent();
            LinearLayout.LayoutParams lpAnimals = (LinearLayout.LayoutParams) viewAnimals.getLayoutParams();
            viewAnimals.setPadding(0, 0, 0, 0);
            viewAnimals.setLayoutParams(lpAnimals);
        }
        if (selection.contains("Career")) {

            TabLayout.Tab tabCareer = mTabLayout.newTab();
            ImageView imageViewCareer = new ImageView(this);
            imageViewCareer.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabCareer.setTag("Career");
            imageViewCareer.setPadding(50, 50, 50, 50);
            imageViewCareer.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCareer));
            tabCareer.setCustomView(imageViewCareer);
            mTabLayout.addTab(tabCareer);
            View viewCareer = (View) imageViewCareer.getParent();
            LinearLayout.LayoutParams lpCareer = (LinearLayout.LayoutParams) viewCareer.getLayoutParams();
            viewCareer.setPadding(0, 0, 0, 0);
            viewCareer.setLayoutParams(lpCareer);
        }
        if (selection.contains("Dancing")) {
            TabLayout.Tab tabDancing = mTabLayout.newTab();
            ImageView imageViewDancing = new ImageView(this);
            imageViewDancing.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabDancing.setTag("Dancing");
            imageViewDancing.setPadding(50, 50, 50, 50);
            imageViewDancing.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDancing));
            tabDancing.setCustomView(imageViewDancing);
            mTabLayout.addTab(tabDancing);
            View viewDancing = (View) imageViewDancing.getParent();
            LinearLayout.LayoutParams lpDancing = (LinearLayout.LayoutParams) viewDancing.getLayoutParams();
            viewDancing.setPadding(0, 0, 0, 0);
            viewDancing.setLayoutParams(lpDancing);
        }
        if (selection.contains("Dating")) {
            TabLayout.Tab tabDating = mTabLayout.newTab();
            ImageView imageViewDating = new ImageView(this);
            imageViewDating.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabDating.setTag("Dating");
            imageViewDating.setPadding(50, 50, 50, 50);
            imageViewDating.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDating));
            tabDating.setCustomView(imageViewDating);
            mTabLayout.addTab(tabDating);
            View viewDating = (View) imageViewDating.getParent();
            LinearLayout.LayoutParams lpDating = (LinearLayout.LayoutParams) viewDating.getLayoutParams();
            viewDating.setPadding(0, 0, 0, 0);
            viewDating.setLayoutParams(lpDating);
        }
        if (selection.contains("Food")) {
            TabLayout.Tab tabFood = mTabLayout.newTab();
            ImageView imageViewFood = new ImageView(this);
            imageViewFood.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabFood.setTag("Food");
            imageViewFood.setPadding(50, 50, 50, 50);
            imageViewFood.setBackgroundColor(getResources().getColor(R.color.colorPrimaryFood));
            tabFood.setCustomView(imageViewFood);
            mTabLayout.addTab(tabFood);
            View viewFood = (View) imageViewFood.getParent();
            LinearLayout.LayoutParams lpFood = (LinearLayout.LayoutParams) viewFood.getLayoutParams();
            viewFood.setPadding(0, 0, 0, 0);
            viewFood.setLayoutParams(lpFood);
        }
        if (selection.contains("Language")) {
            TabLayout.Tab tabLanguage = mTabLayout.newTab();
            ImageView imageViewLanguage = new ImageView(this);
            imageViewLanguage.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabLanguage.setTag("Language");
            imageViewLanguage.setPadding(50, 50, 50, 50);
            imageViewLanguage.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLanguage));
            tabLanguage.setCustomView(imageViewLanguage);
            mTabLayout.addTab(tabLanguage);
            View viewLanguage = (View) imageViewLanguage.getParent();
            LinearLayout.LayoutParams lpLanguage = (LinearLayout.LayoutParams) viewLanguage.getLayoutParams();
            viewLanguage.setPadding(0, 0, 0, 0);
            viewLanguage.setLayoutParams(lpLanguage);
        }
        if (selection.contains("Nature")) {
            TabLayout.Tab tabNature = mTabLayout.newTab();
            ImageView imageViewNature = new ImageView(this);
            imageViewNature.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabNature.setTag("Nature");
            imageViewNature.setPadding(50, 50, 50, 50);
            imageViewNature.setBackgroundColor(getResources().getColor(R.color.colorPrimaryNature));
            tabNature.setCustomView(imageViewNature);
            mTabLayout.addTab(tabNature);
            View viewNature = (View) imageViewNature.getParent();
            LinearLayout.LayoutParams lpNature = (LinearLayout.LayoutParams) viewNature.getLayoutParams();
            viewNature.setPadding(0, 0, 0, 0);
            viewNature.setLayoutParams(lpNature);
        }
        if (selection.contains("Politics")) {
            TabLayout.Tab tabPolitics = mTabLayout.newTab();
            ImageView imageViewPolitics = new ImageView(this);
            imageViewPolitics.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabPolitics.setTag("Politics");
            imageViewPolitics.setPadding(50, 50, 50, 50);
            imageViewPolitics.setBackgroundColor(getResources().getColor(R.color.colorPrimaryPolitics));
            tabPolitics.setCustomView(imageViewPolitics);
            mTabLayout.addTab(tabPolitics);
            View viewPolitics = (View) imageViewPolitics.getParent();
            LinearLayout.LayoutParams lpPolitics = (LinearLayout.LayoutParams) viewPolitics.getLayoutParams();
            viewPolitics.setPadding(0, 0, 0, 0);
            viewPolitics.setLayoutParams(lpPolitics);
        }
        if (selection.contains("Sports")) {
            TabLayout.Tab tabSports = mTabLayout.newTab();
            ImageView imageViewSports = new ImageView(this);
            imageViewSports.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabSports.setTag(mSports);
            imageViewSports.setPadding(50, 50, 50, 50);
            imageViewSports.setBackgroundColor(getResources().getColor(R.color.colorPrimarySports));
            tabSports.setCustomView(imageViewSports);
            mTabLayout.addTab(tabSports);
            View viewSports = (View) imageViewSports.getParent();
            LinearLayout.LayoutParams lpSports = (LinearLayout.LayoutParams) viewSports.getLayoutParams();
            viewSports.setPadding(0, 0, 0, 0);
            viewSports.setLayoutParams(lpSports);
        }
        if (selection.contains("Tech")) {
            TabLayout.Tab tabTech = mTabLayout.newTab();
            ImageView imageViewTech = new ImageView(this);
            imageViewTech.setImageResource(android.R.drawable.ic_btn_speak_now);
            tabTech.setTag("Tech");
            imageViewTech.setPadding(50, 50, 50, 50);
            imageViewTech.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTech));
            tabTech.setCustomView(imageViewTech);
            mTabLayout.addTab(tabTech);
            View viewTech = (View) imageViewTech.getParent();
            LinearLayout.LayoutParams lpTech = (LinearLayout.LayoutParams) viewTech.getLayoutParams();
            viewTech.setPadding(0, 0, 0, 0);
            viewTech.setLayoutParams(lpTech);
        }


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
