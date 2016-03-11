package com.example.android.lately;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.EventCard;
import com.example.android.lately.Cards.FacebookCard;
import com.example.android.lately.Cards.FoursquareCard;
import com.example.android.lately.Cards.RedditCard;
import com.example.android.lately.Cards.RedditComment;
import com.example.android.lately.Cards.WeatherCard;
import com.example.android.lately.Forecast.Weather;
import com.example.android.lately.Foursquare.FoursquarePhotos.FoursquarePhotos;
import com.example.android.lately.Foursquare.FoursquareVenues;
import com.example.android.lately.Fragments.DetailsFragment;
import com.example.android.lately.Meetup.MeetupEvents;
import com.example.android.lately.Meetup.MeetupPhotos.MeetupPhotos;
import com.example.android.lately.Reddit.RedditArticle.Comments.CommentProcessor;
import com.example.android.lately.Reddit.RedditArticle.RedditArticle;
import com.example.android.lately.Reddit.RedditArticle.RedditResult;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

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
    ArrayList<String> redditUrlList;
    ArrayList<String> venueIdList;
    ArrayList<RedditComment> mComments;
    CommentAsyncTask commentAsyncTask;
//    Singleton mSingletonArrayOfParentCards;
    TabLayout mTabLayout;

    int mSports;
    private static String mForecastUrl = "https://api.forecast.io/forecast/39a42687f8dbe7c14cd4f97d201af744/";
    private static String mRedditUrl = "https://www.reddit.com/r/";
    public static final String mFoursquareEndpoint = "https://api.foursquare.com/";
    public static final String FOURSQUARE_CLIENT_ID = "JLOYBXMM0G3SFCJASFGDZJTAHOMYOMUO2JDCF0YIOPPYN312";
    public static final String FOURSQUARE_CLIENT_SECRET = "PIWMY5DZACK0F5U0J4PIHRUJWVSLGX14R1CPZRNGJXTIGJ35";
    public static final String FOURSQUARE_VERSION_NUMBER = "20130815";
    public static final String mMeetupEndPoint = "https://api.meetup.com/";
    public static final String mMeetupKey = "152c38353e11216219776676c5c366a";
    public static final String mMeetupTime = ",1w";
    public static final String mMeetupSign = "true";
    public static final String mMeetupPage = "20";
    public static final String mMeetupRadius = "40";
    public String foursquarePhotoUrl;
    public String meetupPhotoUrl;
    public String facebookUsername;
    public String facebookUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookUsername = getIntent().getStringExtra("name") + " " + getIntent().getStringExtra("surname");
        facebookUserImage = getIntent().getStringExtra("imageUrl");

//        mSingletonArrayOfParentCards = Singleton.getInstance();

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


        //put in the selections into shared preferences and feed it into the creates tab

        ArrayList<String> selection = new ArrayList<>();
        selection.add("Animals");
        selection.add("Tech");
        selection.add("Dancing");
        selection.add("Dating");
        selection.add("Dating");
        selection.add("Food");
        selection.add("Nature");
        selection.add("Politics");
        selection.add("Career");
        selection.add("Dancing");
        selection.add("Language");
        selection.add("Sports");
        selection.add("Politics");

        createTabs(selection);

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
                switch (tab.getTag().toString()) {
                    case "Main":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        mMainToolbar.setTitle("Main");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarker));
                        break;
                    case "Animals":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkAnimals));
                        mMainToolbar.setTitle("Animals");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkAnimals));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerAnimals));
                        break;
                    case "Career":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkCareer));
                        mMainToolbar.setTitle("Career");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkCareer));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerCareer));
                        break;
                    case "Dancing":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDancing));
                        mMainToolbar.setTitle("Dancing");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDancing));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerDancing));
                        break;
                    case "Dating":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDating));
                        mMainToolbar.setTitle("Dating");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkDating));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerDating));
                        break;
                    case "Food":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFood));
                        mMainToolbar.setTitle("Food");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkFood));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerFood));
                        break;
                    case "Language":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkLanguage));
                        mMainToolbar.setTitle("Language");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkLanguage));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerLanguage));
                        break;
                    case "Nature":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkNature));
                        mMainToolbar.setTitle("Nature");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkNature));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerNature));
                        break;
                    case "Politics":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkPolitics));
                        mMainToolbar.setTitle("Politics");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkPolitics));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerPolitics));
                        break;
                    case "Sports":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkSports));
                        mMainToolbar.setTitle("Sports");
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkSports));
                        mWindow.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkerSports));
                        break;
                    case "Tech":
                        mMainToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTech));
                        mMainToolbar.setTitle("Tech");
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

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);

        if (isConnected) {
            //connected notification
            builder.setSmallIcon(android.R.drawable.presence_online);
            builder.setContentTitle("Network Status");
            builder.setContentText("The network is up");

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(12345, notification);

        } else {
            builder.setSmallIcon(android.R.drawable.stat_notify_error);
            builder.setContentTitle("Network Status");
            builder.setContentText("The network is down");

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(12345, notification);
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
                        formattedCurrentDate, nextFiveDaysDates, nextFiveDaysSummary, nextFiveDaysHighTemp, nextFiveDaysLowTemp, CardAdapter.TYPE_WEATHER, CardAdapter.TAB_MAINPAGE, 0);

                Singleton.getInstance().addParentCard(weatherCard, CardAdapter.TAB_MAINPAGE);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getRedditApi(final ArrayList<Integer> tabTypes) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mRedditUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        redditUrlList = new ArrayList<>();
        ArrayList<String> subredditSelections = new ArrayList<>();
        for (int l = 0; l <tabTypes.size() ; l++) {
            String subreddit = redditSwitcher(tabTypes.get(l));
            subredditSelections.add(subreddit);
        }

        RedditRequest redditRequest = retrofit.create(RedditRequest.class);

        for (int i = 0; i < subredditSelections.size(); i++) {
            Call<RedditResult> result = redditRequest.getRedditFeed(subredditSelections.get(i));
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
                        redditUrlList.add(articleUrl);
                        articleNumOfComment = result.get(i).getData().getNumComments();
                        articleScore = result.get(i).getData().getScore();
                        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hha, EEE");
                        Date currentDate = new Date((long) (result.get(i).getData().getCreated() * 1000L));
                        articleTime = format.format(currentDate);
                        articleSubreddit = result.get(i).getData().getSubreddit();
                        idNumber = i + 1;

                        //TODO Create object here. Type null for the comment parameter.
                        RedditCard redditCard = new RedditCard(articleAuthor,null,articleContent,articleNumOfComment,articleScore,articleSubreddit,
                                articleTime,articleTitle,articleUrl,CardAdapter.TYPE_REDDIT,CardAdapter.TAB_FOOD,Singleton.getInstance().getSize());
                        Log.d("REDDIT",redditCard.getmTitle());
                        Singleton.getInstance().addParentCard(redditCard, CardAdapter.TAB_MAINPAGE);
                        Singleton.getInstance().addParentCard(redditCard, CardAdapter.TAB_FOOD);

                    }
                    String[] urlArray = redditUrlList.toArray(new String[0]);
                    commentAsyncTask = new CommentAsyncTask();
                    commentAsyncTask.execute(urlArray);
                }

                @Override
                public void onFailure(Call<RedditResult> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public class CommentAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String data = "";
            try {
                for (int i = 0; i < params.length; i++) {
                    URL url = new URL(params[i]+"/.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inStream = connection.getInputStream();
                    data = getInputData(inStream);
                    CommentProcessor commentProcessor = new CommentProcessor(data);
                    mComments = new ArrayList<>();
                    mComments = commentProcessor.fetchComments();

                    if(Singleton.getInstance().getArrayList(CardAdapter.TAB_MAINPAGE).get(i+1).getmCardType() == CardAdapter.TYPE_REDDIT) {
                        RedditCard redditCard = (RedditCard) Singleton.getInstance().getArrayList(CardAdapter.TAB_MAINPAGE).get(i + 1);
                        redditCard.setmComments(mComments);
                    }
                }
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
        }
    }

    public void getFoursquareApi(String latlon) {

        venueIdList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mFoursquareEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final FoursquareRequest foursquareRequest = retrofit.create(FoursquareRequest.class);
        Call<FoursquareVenues> result = foursquareRequest.getVenues(FOURSQUARE_CLIENT_ID, FOURSQUARE_CLIENT_SECRET, FOURSQUARE_VERSION_NUMBER, latlon);
        result.enqueue(new Callback<FoursquareVenues>() {
            @Override
            public void onResponse
                    (Call<FoursquareVenues> call, Response<FoursquareVenues> response) {
                for (int i = 0; i < response.body().getResponse().getVenues().size(); i++) {
                    final String venueId = response.body().getResponse().getVenues().get(i).getId();
                    final String venueName = response.body().getResponse().getVenues().get(i).getName();
                    String streetAddress = response.body().getResponse().getVenues().get(i).getLocation().getAddress();
                    String cityAddress = response.body().getResponse().getVenues().get(i).getLocation().getCity();
                    String stateAddress = response.body().getResponse().getVenues().get(i).getLocation().getState();
                    String zipcodeAddress = response.body().getResponse().getVenues().get(i).getLocation().getPostalCode();
                    final String venueAddress = streetAddress + ", " + cityAddress + ", " + stateAddress + ", " + zipcodeAddress;
                    final String venueUrl = response.body().getResponse().getVenues().get(i).getUrl();

                    Call<FoursquarePhotos> photos = foursquareRequest.getPhotoes(venueId, FOURSQUARE_CLIENT_ID, FOURSQUARE_CLIENT_SECRET, FOURSQUARE_VERSION_NUMBER);
                    photos.enqueue(new Callback<FoursquarePhotos>() {
                        @Override
                        public void onResponse(Call<FoursquarePhotos> call, Response<FoursquarePhotos> response) {
                            if(response.body().getResponse().getPhotos().getItems().size() != 0) {
                                String prefix = response.body().getResponse().getPhotos().getItems().get(0).getPrefix();
                                String suffix = response.body().getResponse().getPhotos().getItems().get(0).getSuffix();
                                int width = response.body().getResponse().getPhotos().getItems().get(0).getWidth();
                                int height = response.body().getResponse().getPhotos().getItems().get(0).getHeight();
                                foursquarePhotoUrl = prefix + width + "x" + height + suffix;

                                //FoursquareCard foursquareCard = new FoursquareCard(venueAddress,venueId,venueName,venueUrl,foursquarePhotoUrl,CardAdapter.TYPE_FOURSQUARE,CardAdapter.TAB_MAINPAGE,Singleton.getInstance().getSize());
                                FoursquareCard foursquareCard1 = new FoursquareCard(venueAddress,venueId,venueName,venueUrl,foursquarePhotoUrl,CardAdapter.TYPE_FOURSQUARE,CardAdapter.TAB_MAINPAGE,1);
                                Singleton.getInstance().addParentCard(foursquareCard1, CardAdapter.TAB_MAINPAGE);
                            }
                        }

                        @Override
                        public void onFailure(Call<FoursquarePhotos> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FoursquareVenues> call, Throwable t) {

            }
        });
    }

    public void getMeetupApi(ArrayList<Integer> tabTypes, String lat, String lon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mMeetupEndPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArrayList<String> selections = new ArrayList<>();

        for (int l = 0; l <tabTypes.size(); l++) {
            String selection = meetupSwitcher(tabTypes.get(l));
            selections.add(selection);
        }

        for (int i = 0; i < selections.size(); i++) {
            final MeetupRequest meetupRequest = retrofit.create(MeetupRequest.class);
            Call<MeetupEvents> result = meetupRequest.getEvents(mMeetupRadius, mMeetupPage, mMeetupKey, mMeetupTime, selections.get(i), lat, lon);
            result.enqueue(new Callback<MeetupEvents>() {
                @Override
                public void onResponse(Call<MeetupEvents> call, Response<MeetupEvents> response) {
                    for (int j = 0; j < response.body().getResults().size(); j++) {
                        String eventId = response.body().getResults().get(j).getId();
                        String eventName = response.body().getResults().get(j).getName();
                        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hha, EEE");
                        Date currentDate = new Date((long) (response.body().getResults().get(j).getTime() * 1000L));
                        String eventTime = format.format(currentDate);
                        String groupName = response.body().getResults().get(j).getGroup().getName();
                        String eventDescription = response.body().getResults().get(j).getDescription();
                        String venueName = "Venue will be shown to members only";
                        String venueAddress = "Members only";
                        String groupUrl = response.body().getResults().get(j).getGroup().getUrlname();
                        String eventUrl = response.body().getResults().get(j).getEventUrl();
                        if (response.body().getResults().get(j).getVenue() != null) {
                            venueName = response.body().getResults().get(j).getVenue().getName();
                            String venueStreetAddress = response.body().getResults().get(j).getVenue().getAddress1();
                            String venueCityAddress = response.body().getResults().get(j).getVenue().getCity();
                            String venueStateAddress = response.body().getResults().get(j).getVenue().getState();
                            venueAddress = venueCityAddress + ", " + venueCityAddress + ", " + venueStateAddress;
                        } else {
                            venueAddress = "Unknown";
                        }

                        EventCard eventCard = new EventCard(eventName, eventDescription, eventUrl, groupName, "20", eventTime, venueAddress, venueName,
                                CardAdapter.TYPE_EVENT, CardAdapter.TAB_TECH,Singleton.getInstance().getArrayList(CardAdapter.TAB_MAINPAGE).size());
                        Singleton.getInstance().addParentCard(eventCard, CardAdapter.TAB_MAINPAGE);
                        Singleton.getInstance().addParentCard(eventCard, CardAdapter.TAB_TECH);
                        Log.d("ADDED A CARD : ",eventCard.getEvent()+" "+eventCard.getVenueAddress());

                    }
                }

                @Override
                public void onFailure(Call<MeetupEvents> call, Throwable t) {

                }
            });
        }
    }

    public void getFacebookFeed(){
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        String obj = response.getJSONObject().toString();
                        try {
                            for (int i = 0; i <response.getJSONObject().getJSONArray("data").length() ; i++) {
                                if(!response.getJSONObject().getJSONArray("data").getJSONObject(i).optString("message").isEmpty()) {
                                    String feedMessage = response.getJSONObject().getJSONArray("data").getJSONObject(i).getString("message");
                                    String feedTime = (response.getJSONObject().getJSONArray("data").getJSONObject(i).getString("created_time")).substring(0, 10);
                                    Log.d("FACEBOOK FEED JSON : ", feedMessage);
                                    Log.d("FACEBOOK FEED JSON : ", feedTime);
                                FacebookCard facebookCard = new FacebookCard(feedTime, facebookUserImage, feedMessage, facebookUsername, CardAdapter.TYPE_FACEBOOK, CardAdapter.TAB_MAINPAGE, Singleton.getInstance().getArrayList(CardAdapter.TAB_MAINPAGE).size());
                                Singleton.getInstance().addParentCard(facebookCard,CardAdapter.TAB_MAINPAGE);
                                Log.d("fb", String.valueOf(Singleton.getInstance().getArrayList(CardAdapter.TAB_MAINPAGE).get(0).getmCardType()));

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        request.executeAsync();
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

                ArrayList<Integer> topics = new ArrayList<Integer>();
                topics.add(CardAdapter.TAB_FOOD);
                getForecastApi();
                getRedditApi(topics); //TODO This parameter is a place holder. We'll change it into the user topic int.
                getFoursquareApi(latlon);
                //getMeetupApi(new ArrayList<Integer>(), latitude, longitude); //TODO This one also.
                getFacebookFeed();

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
        public Call<FoursquarePhotos> getPhotoes(@Path("venueId") String venueId, @Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("v") String version);

    }

    public interface MeetupRequest {
        @GET("2/open_events?sign=true")
        public Call<MeetupEvents> getEvents(@Query("radius") String radius, @Query("page") String page, @Query("key") String key, @Query("time") String time, @Query("category") String category, @Query("lat") String lat, @Query("lon") String lon);

        @GET("2/photos?&sign=true&photo-host=public")
        public Call<MeetupPhotos> getPhotos(@Query("key") String key, @Query("group_urlname") String groupUrlname, @Query("page") String page);

    }

    public void createTabs(ArrayList selection) {
        //// TODO: 3/10/16 change "contains" to TAB_BLAH

        TabLayout.Tab tabMain = mTabLayout.newTab();
        ImageView imageViewMain = new ImageView(this);
        imageViewMain.setImageResource(R.drawable.ic_home);
        tabMain.setTag("Main");
        imageViewMain.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabMain.setCustomView(imageViewMain);
        mTabLayout.addTab(tabMain);
        View viewMain = (View) imageViewMain.getParent();
        LinearLayout.LayoutParams lpMain = (LinearLayout.LayoutParams) viewMain.getLayoutParams();
        viewMain.setPadding(0, 0, 0, 0);
        viewMain.setLayoutParams(lpMain);


        if (selection.contains("Animals")) {
            TabLayout.Tab tabAnimals = mTabLayout.newTab();
            ImageView imageViewAnimals = new ImageView(this);
            imageViewAnimals.setImageResource(R.drawable.animal_icon);
            tabAnimals.setTag("Animals");
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
            imageViewCareer.setImageResource(R.drawable.career_icon);
            tabCareer.setTag("Career");
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
            imageViewDancing.setImageResource(R.drawable.dance_icon);
            tabDancing.setTag("Dancing");
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
            imageViewDating.setImageResource(R.drawable.dating_icon);
            tabDating.setTag("Dating");
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
            imageViewFood.setImageResource(R.drawable.food_icon);
            tabFood.setTag("Food");
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
            imageViewLanguage.setImageResource(R.drawable.language_icon);
            tabLanguage.setTag("Language");
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
            imageViewNature.setImageResource(R.drawable.nature_icon);
            tabNature.setTag("Nature");
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
            imageViewPolitics.setImageResource(R.drawable.politics_icon);
            tabPolitics.setTag("Politics");
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
            imageViewSports.setImageResource(R.drawable.sports_icon);
            tabSports.setTag(mSports);
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
            imageViewTech.setImageResource(R.drawable.tech_icon);
            tabTech.setTag("Tech");
            imageViewTech.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTech));
            tabTech.setCustomView(imageViewTech);
            mTabLayout.addTab(tabTech);
            View viewTech = (View) imageViewTech.getParent();
            LinearLayout.LayoutParams lpTech = (LinearLayout.LayoutParams) viewTech.getLayoutParams();
            viewTech.setPadding(0, 0, 0, 0);
            viewTech.setLayoutParams(lpTech);
        }


    }

    public String redditSwitcher(int TAB) {
        switch (TAB) {
            case CardAdapter.TAB_MAINPAGE:
                return "AskReddit";
            case CardAdapter.TAB_POLITICS:
                return "politics";
            case CardAdapter.TAB_TECH:
                return "technology";
            case CardAdapter.TAB_CAREER:
                return "financialindependance";
            case CardAdapter.TAB_FOOD:
                return "food";
            case CardAdapter.TAB_DANCING:
                return "Dance";
            case CardAdapter.TAB_ANIMALS:
                return "Animals";
            case CardAdapter.TAB_LANGUAGE:
                return "europe";
            case CardAdapter.TAB_NATURE:
                return "nature";
            case CardAdapter.TAB_SPORTS:
                return "sports";
            case CardAdapter.TAB_DATING:
                return "Tinder";
            default:
                return "AskReddit";
        }
    }

    public String meetupSwitcher(int TAB) {
        switch (TAB) {
            case CardAdapter.TAB_MAINPAGE:
                return "4";
            case CardAdapter.TAB_POLITICS:
                return "13";
            case CardAdapter.TAB_TECH:
                return "34";
            case CardAdapter.TAB_CAREER:
                return "2";
            case CardAdapter.TAB_FOOD:
                return "10";
            case CardAdapter.TAB_DANCING:
                return "5";
            case CardAdapter.TAB_ANIMALS:
                return "26";
            case CardAdapter.TAB_LANGUAGE:
                return "16";
            case CardAdapter.TAB_NATURE:
                return "23";
            case CardAdapter.TAB_SPORTS:
                return "32";
            case CardAdapter.TAB_DATING:
                return "30";
            default:
                return "4";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SelectionPage.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
