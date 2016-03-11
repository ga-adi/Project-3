package com.example.project3vice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project3vice.vice_classes.ArticleSearchAsyncTask;
import com.example.project3vice.vice_classes.ViceArticle;
import com.example.project3vice.vice_classes.Video;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.PlayerDomain;
import com.ooyala.android.ui.OoyalaPlayerLayoutController;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
public class ArticleActivity extends AppCompatActivity implements Observer {

    private static final String TAG = ArticleActivity.class.getSimpleName();
    private static final String PCODE = "R2d3I6s06RyB712DN0_2GsQS-R-Y";
    private static final String DOMAIN = "http://www.vice.com";
    private OoyalaPlayer player;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ImageView imageView = (ImageView) findViewById(R.id.topImageView);
        setSupportActionBar(toolbar);
        TextView authorText = (TextView) findViewById(R.id.authorView);
        WebView webView = (WebView) findViewById(R.id.webView);
        TextView tagsView = (TextView) findViewById(R.id.tagsView);

        Intent intent = getIntent();
        final long articleID = intent.getLongExtra("ID",0);
        // Get Article
        ArticleSearchAsyncTask articleSearchAsyncTask = new ArticleSearchAsyncTask();
        ViceArticle viceArticle = new ViceArticle();

        if(isDeviceOnline()) {
            try {
                viceArticle = articleSearchAsyncTask.execute(articleID).get();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            String body = viceArticle.getBody();
            title = viceArticle.getTitle().toString();
            String author = viceArticle.getAuthor();
            String[] tags = viceArticle.getTags();
            String tagsString = Arrays.toString(tags);
            String imageURL = viceArticle.getImage();
            String url = viceArticle.getUrl();


        // OnClick to make notification when article is shared.
        View.OnClickListener notificationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ArticleActivity.this);
                builder.setContentTitle("Shared to Facebook");
                builder.setContentText(title + " has been shared on Facebook.");
                builder.setSmallIcon(R.drawable.facebook);
                builder.setColor(Color.argb(1,59, 89, 152));
                Intent notificationIntent = new Intent(ArticleActivity.this,ArticleActivity.class);
                notificationIntent.putExtra("ID", articleID);
                PendingIntent pendingIntent = PendingIntent.getActivity(ArticleActivity.this, (int) System.currentTimeMillis(), notificationIntent, 0);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1,notification);
            }
        };

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
            shareButton.setOnClickListener(notificationListener);


            //Set Views

            webView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;}</style>"+ body, "text/html", "utf-8", null);
            authorText.setText("By " + author);
            tagsView.setText("TAGS: "+tagsString);
            toolbarLayout.setTitle(title);
            Picasso.with(this)
                    .load(imageURL)
                    .fit().centerCrop()
                    .into(imageView);

            //Check for a media and show video
            if (viceArticle.getMedia() != null) {
                Video video = viceArticle.getMedia().getVideo();
                String ooyalaId = video.getOoyalaId();
                OoyalaPlayerLayout playerLayout = (OoyalaPlayerLayout) findViewById(R.id.ooyalaPlayer);
                playerLayout.setVisibility(View.VISIBLE);
                player = new OoyalaPlayer(PCODE, new PlayerDomain(DOMAIN));
                new OoyalaPlayerLayoutController(playerLayout, player);
                player.addObserver(this);

                if (player.setEmbedCode(ooyalaId)) {
                    player.play();
                } else {
                    Log.d(TAG, "Asset failed");
                }
            }

        } else {
            Toast.makeText(ArticleActivity.this, "No Network connection detected!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.suspend();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.resume();
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (arg0 != player) {
            return;
        }

        if (arg1 == OoyalaPlayer.TIME_CHANGED_NOTIFICATION) {
            return;
        }

        if (arg1 == OoyalaPlayer.ERROR_NOTIFICATION) {
            final String msg = "Error event received";
            if (player != null && player.getError() != null) {
                Log.e(TAG, msg, player.getError());
            } else {
                Log.e(TAG, msg);
            }
            return;
        }
        Log.d(TAG, "Notification Received: " + arg1 + " - state: " + player.getState());
    }

    //Check to confirm network connectivity
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}