package com.imaginat.justhejist.jist.Activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.imaginat.justhejist.jist.DBHelper.TopStoryDBHelper;
import com.imaginat.justhejist.jist.Notifications.MaratNotifications;
import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.imaginat.justhejist.jist.onDemandUpdate.NYTimesGetData;

import org.json.JSONObject;

import java.util.List;



public class MainActivity extends AppCompatActivity implements NYTimesGetData.NYTimesDataReceivedInterface {
  // hash: Ra/aSVj6IEwD+XYG+5pLHo0J9tQ=
//  private RecyclerView mRecyclerView;
//  private RecyclerView.Adapter mAdapter;
//  private RecyclerView.LayoutManager mLayoutManager;
//
//  private Button mButtonForAddingShitToDatabase;
//  private TopStoryDBHelper topStoryDBHelper;

  private Button mCheckingNotification;
  private MaratNotifications maratNotifications;





  CallbackManager callbackManager;
  AccessTokenTracker accessTokenTracker;
//  ContentResolver mResolver;

  public static final String AUTHORITY =
      "com.imaginat.justhejist.jist.sync.StubProvider";
  public static final String ACCOUNT_TYPE = "example.com";
  public static final String ACCOUNT = "default_account";

  Account mAccount;
  String mChoice = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

//    Button nyTimesUpdate = (Button)findViewById(R.id.updateNYTIMES);
//    nyTimesUpdate.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        NYTimesGetData nyTimesGetData = new NYTimesGetData(MainActivity.this);
//        Random random = new Random();
//        nyTimesGetData.execute(Section.getSections()[random.nextInt(Section.getSections().length)]);
//      }
//    });
    // Tab button
    findViewById(R.id.Tabs).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, com.imaginat.justhejist.jist.Activities.TabsActivity.class);
        startActivity(i);
      }
    });

    mCheckingNotification = (Button) findViewById(R.id.button_for_notifications);
    mCheckingNotification.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        maratNotifications = new MaratNotifications(MainActivity.this, "Teddy Bear", "http://media.salon.com/2016/01/ted-cruz.jpg");
        maratNotifications.createNotificationForNewArticle();
      }
    });


//    mAccount = createSyncAccount(this);
//    getContentResolver().registerContentObserver(Uri.parse("content://com.imaginat.justhejist.jist.sync.StubProvider"),true,new TopStoriesContentObserver(new Handler()));
////
    //------------------------------------------------------------------

    //------------------------------------------------------------------------------
    FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Sync it up!!", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                /*
                 * Request the sync for the default account, authority, and
                 * manual sync settings
                 */
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);


      }
    });

    //------------------------FACEBOOK
    // BUTTONS---------------------------------------------
//    callbackManager = CallbackManager.Factory.create();
//    LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
//    loginButton.setReadPermissions("user_friends");
//
//    LoginManager.getInstance().registerCallback(
//        callbackManager, new FacebookCallback<LoginResult>() {
//          @Override
//          public void onSuccess(LoginResult loginResult) {
//            // App code
//          }
//
//          @Override
//          public void onCancel() {
//            // App code
//          }
//
//          @Override
//          public void onError(FacebookException exception) {
//            // App code
//          }
//        });
//    LoginManager.getInstance().logInWithReadPermissions(
//        this, Arrays.asList("public_profile", "user_friends", "user_posts"));

    accessTokenTracker = new AccessTokenTracker() {
      @Override
      protected void onCurrentAccessTokenChanged(
          AccessToken oldAccessToken, AccessToken currentAccessToken) {
        // Set the access token using
        // currentAccessToken when it's loaded or set.
      }
    };
    // If the access token is available already assign it.

    Button attemptNewsFeedPull = (Button)findViewById(R.id.attemptNewsFeedPull);
    attemptNewsFeedPull.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // AccessToken token = AccessToken.getCurrentAccessToken();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Bundle params = new Bundle();
        params.putString("fields", "id,name,link");

        GraphRequest request = new GraphRequest(
            accessToken, "/5281959998/feed", params, HttpMethod.GET,
            new GraphRequest.Callback() {
              public void onCompleted(GraphResponse response) {
                JSONObject nyTimesObject = response.getJSONObject();
                Log.d("MainActivity",
                      "nyTimesObject " + nyTimesObject.toString());
              }
            });
        request.executeAsync();
      }
    });
    //        ShareLinkContent content = new ShareLinkContent.Builder()
    //                .setContentUrl(Uri.parse("http://www.starwars.com/"))
    //                .build();
    //
    //        final ShareButton postLinkButton =
    //        (ShareButton)findViewById(R.id.fb_share_button);
    //        postLinkButton.setShareContent(content);
    //
    //
    //        LikeView likeView = (LikeView)
    //        findViewById(R.id.testFacebookLikeButton);
    //        likeView.setObjectIdAndType(
    //                "http://www.starwars.com/",
    //                LikeView.ObjectType.PAGE);
    //----------------------------------------------------------------------------------------

    Intent intent = getIntent();
    handleIntent(intent);
  }

  @Override
  protected void onResume() {
    super.onResume();
    AppEventsLogger.activateApp(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    AppEventsLogger.deactivateApp(this);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      if (mChoice != null) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        Log.d("dude", mChoice);
        Toast.makeText(MainActivity.this, "Searching for " + query + "/n/r" + mChoice,
                Toast.LENGTH_SHORT)
                .show();

        Cursor cursor = TopStoryDBHelper.getInstance(this)
                .searchByUserCategory(query, mChoice);
        cursor.moveToFirst();

        // TextView searchResult = (TextView) findViewById(R.id.tempTempView);
        // searchResult.setText(cursor.getString(cursor.getColumnIndex(TopStoryDBHelper.COL_TITLE)));
      }

      else {
        String query = intent.getStringExtra(SearchManager.QUERY);
        Toast.makeText(MainActivity.this, "Searching for " + query,
                Toast.LENGTH_SHORT)
                .show();

        Cursor cursor = TopStoryDBHelper.getInstance(this)
                .searchArticlesByAllThree(query);
        cursor.moveToFirst();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);

    SearchManager searchManager =
        (SearchManager)getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView =
        (SearchView)menu.findItem(R.id.search).getActionView();
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.search) {

      AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
      builder.setTitle("Fuck Yeah Dude")
              .setItems(R.array.choices, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  Resources res = getResources();
                  String[] choices = res.getStringArray(R.array.choices);
                  mChoice = choices[which].toLowerCase();
                }
              });

//      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int id) {
//          // User clicked OK button
//        }
//      });
//
//      builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int id) {
//          // User cancelled the dialog
//        }
//      });

      AlertDialog dialog = builder.create();
      dialog.show();

    }

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    if (id == R.id.test_animate_scroll_vertical) {
    }

    if (id == R.id.test_update_breakingNews) {
      Toast.makeText(MainActivity.this,"attempting",Toast.LENGTH_SHORT).show();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
                                  Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    accessTokenTracker.stopTracking();
  }

  public static Account createSyncAccount(Context context) {
    // Create the account type and default account
    Account newAccount = new Account(
            ACCOUNT, ACCOUNT_TYPE);
    // Get an instance of the Android account manager
    AccountManager accountManager =
            (AccountManager) context.getSystemService(
                    ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */

    try {


      if (accountManager.addAccountExplicitly(newAccount, null, null)) {
        //accountManager.addAccountExplicitly(newAccount, null, null)
              /*
               * If you don't set android:syncable="true" in
               * in your <provider> element in the manifest,
               * then call context.setIsSyncable(account, AUTHORITY, 1)
               * here.
               */
      } else {
                /*
                 * The account exists or some other error occurred. Log this, report it,
                 * or handle it internally.
                 */
      }
    }catch(Exception ex){
        Log.d("Exception ugh","excption "+ex.getMessage());
      Log.d("Exception ugh","excption "+ex.toString());
    }
    return newAccount;
  }




  @Override
  public void onCompleted(List<NewsStory> newStories) {

  }

  @Override
  public Context resolveContext() {
    return this;
  }

  public class TopStoriesContentObserver extends ContentObserver {

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public TopStoriesContentObserver(Handler handler) {
      super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
      //do stuff on UI thread
      Log.d(MainActivity.class.getName(), "CHANGE OBSERVED AT URI: " + uri);
      MaratNotifications mn = new MaratNotifications(MainActivity.this,"ArticleName","URL");
      mn.createNotificationForNewArticle();
    }
  }
}
