package com.charlesdrews.hud;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.charlesdrews.hud.CardsData.CardData;
import com.charlesdrews.hud.CardsData.CardType;
import com.charlesdrews.hud.CardsData.FacebookCardData;
import com.charlesdrews.hud.CardsData.NewsCardData;
import com.charlesdrews.hud.CardsData.WeatherCardData;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ITEM_COUNT = 3;
    public static final int WEATHER_POSITION = 0;
    public static final int NEWS_POSITION = 1;
    public static final int FACEBOOK_POSITION = 2;

    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;
    private Account mAccount;
    public LoginButton mFacebookLoginButton;
    public CallbackManager mCallbackManager;
    private TextView mLoginText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView backgroundImage = (ImageView)findViewById(R.id.imageframe);
        backgroundImage.setImageResource(R.drawable.rothkoyello);


        handleIntent(getIntent());

        //TODO facebook stuff
        mLoginText = (TextView)findViewById(R.id.status_update);
        //TODO - can this initialization be done in an async task?
        FacebookSdk.sdkInitialize(getApplicationContext());

        // register content observers
        getContentResolver().registerContentObserver(
                Uri.parse(CardContentProvider.BASE_URI_STRING), // base uri w/o API-specific path
                true,                                           // notify for API-specific paths
                new CardContentObserver(new Handler())
        );

        // set up array of card data for use in adapter
        mCardsData = new ArrayList<>(ITEM_COUNT);
        mCardsData.add(new CardData(CardType.Weather));     // index 0
        mCardsData.add(new CardData(CardType.News));        // index 1
        mCardsData.add(new CardData(CardType.Facebook));    // index 2


        // update each item in the card data array by pulling from db via asyc task
        new PullFromDbAsyncTask().execute(CardType.Weather);
        new PullFromDbAsyncTask().execute(CardType.News);
        new PullFromDbAsyncTask().execute(CardType.Facebook);

        // set up recycler view & adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mAdapter = new RecyclerAdapter(this, mCardsData);
        mAdapter.setHasStableIds(false);
        recyclerView.setAdapter(mAdapter);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.populaterecyclerview);
        recyclerView.startAnimation(animation);

        // set up syncing
        mAccount = createSyncAccount(this);
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        //TODO - check if error thrown when device offline
        ContentResolver.requestSync(mAccount, CardContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, CardContentProvider.AUTHORITY, true);
        //TODO - figure out why this is syncing so frequently (definitely not just every 15 min)
        ContentResolver.addPeriodicSync(mAccount, CardContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 15);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "Query: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    public class CardContentObserver extends ContentObserver {
        private final String TAG = CardContentObserver.class.getCanonicalName();

        public CardContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            int uriType = CardContentProvider.sUriMatcher.match(uri);

            switch (uriType) {
                case CardContentProvider.FACEBOOK: {
                    Log.d(TAG, "onChange: facebook");
                    new PullFromDbAsyncTask().execute(CardType.Facebook);
                    break;
                }
                case CardContentProvider.NEWS: {
                    Log.d(TAG, "onChange: news");
                    new PullFromDbAsyncTask().execute(CardType.News);
                    break;
                }
                case CardContentProvider.WEATHER: {
                    Log.d(TAG, "onChange: weather");
                    new PullFromDbAsyncTask().execute(CardType.Weather);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(
                context.getString(R.string.account),
                context.getString(R.string.account_type)
        );

        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d(MainActivity.class.getCanonicalName(), "Account added successfully");
        } else {
            Log.d(MainActivity.class.getCanonicalName(), "Account NOT added successfully");
        }
        return newAccount;
    }

    private class PullFromDbAsyncTask extends AsyncTask<CardType, Void, CardType> {
        private final String TAG = PullFromDbAsyncTask.class.getCanonicalName();

        private CardType mCardType;

        @Override
        protected CardType doInBackground(CardType... params) {
            if ((mCardType = params[0]) == null) {
                cancel(true);
            }

            switch (mCardType) {
                case Facebook: {
                    Log.d(TAG, "doInBackground: query facebook");
                    return updateCardDataArrayFromCursor(getContentResolver().query(
                            CardContentProvider.FACEBOOK_URI, null, null, null, null));
                }
                case News: {
                    Log.d(TAG, "doInBackground: query news");
                    return updateCardDataArrayFromCursor(getContentResolver().query(
                            CardContentProvider.NEWS_URI, null, null, null, null));
                }
                case Weather: {
                    Log.d(TAG, "doInBackground: query weather");
                    return updateCardDataArrayFromCursor(getContentResolver().query(
                            CardContentProvider.WEATHER_URI, null, null, null, null));
                }
                default:
                    return null;
            }
        }

        private CardType updateCardDataArrayFromCursor(Cursor cursor) {
            if (cursor != null && cursor.moveToFirst()) {
                switch (mCardType) {
                    case Facebook: {
                        FacebookCardData facebookCardData = new FacebookCardData(
                                CardType.Facebook,
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_AUTHOR)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_STATUS_UPDATE))
                        );
                        mCardsData.set(FACEBOOK_POSITION, facebookCardData);
                        break;
                    }
                    case News: {
                        NewsCardData newsCardData = new NewsCardData(CardType.News, cursor);
                        mCardsData.set(NEWS_POSITION, newsCardData);
                        break;
                    }
                    case Weather: {
                        WeatherCardData weatherCardData = new WeatherCardData(
                                CardType.Weather,
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_HIGH)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_LOW))
                        );
                        mCardsData.set(WEATHER_POSITION, weatherCardData);
                        break;
                    }
                    default:
                        return null;
                }
                cursor.close();
            }
            return mCardType;
        }

        @Override
        protected void onPostExecute(CardType cardType) {
            super.onPostExecute(cardType);

            switch (cardType) {
                case Facebook:
                    Animation flipcard = AnimationUtils.loadAnimation(MainActivity.this, R.anim.populaterecyclerview);
                    //RecyclerAdapter.FacebookCard.startAnimation(flipcard);
                    mAdapter.notifyItemChanged(FACEBOOK_POSITION);
                    break;
                case News:
                    mAdapter.notifyItemChanged(NEWS_POSITION);
                    break;
                case Weather:
                    mAdapter.notifyItemChanged(WEATHER_POSITION);
                    break;
                default:
                    break;
            }
        }
    }

    public void facebookLogin(){

        mFacebookLoginButton = (LoginButton)findViewById(R.id.login_button);
        mFacebookLoginButton.setReadPermissions("user_likes");
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                mLoginText.setText("User ID: " + loginResult.getAccessToken().getUserId() + "Auth token: " + loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Login error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO - this crashes if you close the login window without logging in
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
