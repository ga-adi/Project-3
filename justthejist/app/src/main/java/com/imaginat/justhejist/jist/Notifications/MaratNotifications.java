package com.imaginat.justhejist.jist.Notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.imaginat.justhejist.jist.Activities.MainActivity;
import com.imaginat.justhejist.jist.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by generalassembly on 3/7/16.
 */
public class MaratNotifications {
  private Activity mActivity = null;
  private String nameOfArticle;
  private String mUrl;

  public MaratNotifications(Activity a, String articleName, String url) {
    mActivity = a;
    nameOfArticle = articleName;
    mUrl = url;
  }

  // big picture notification for every time a breaking news article is synced
  public void createNotificationForNewArticle() {
    NotificationCompat.BigPictureStyle bigPic = new NotificationCompat.BigPictureStyle();
    bigPic.bigPicture(BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.joe_biden_nyt)).build();
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(mActivity);
    builder.setSmallIcon(R.drawable.nyt_icon);
    builder.setContentTitle("New Article: " + nameOfArticle);
//    builder.setContentInfo("Check Out This New Interesting Story");
    builder.setContentText("Check Out This New Interesting Story");

    Intent notificationIntent = new Intent(mActivity, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(
            mActivity, (int) System.currentTimeMillis(), notificationIntent, 0);

    builder.setContentIntent(pendingIntent);
    builder.setAutoCancel(true);

//    NotificationCompat.BigPictureStyle bigPic =
//        new NotificationCompat.BigPictureStyle().bigPicture(
//                BitmapFactory.decodeResource(getResources(), R.drawable.network_available)).build();

    builder.setStyle(bigPic);

    Notification notification = builder.build();

    NotificationManager notificationManager = (NotificationManager)mActivity.getSystemService(mActivity.NOTIFICATION_SERVICE);
    notificationManager.notify(1,notification);

  }

  // turn the bitmap received into a url that is accepted in the big picture notification
  public static Bitmap getBitmapFromURL(String src) {
    try {
      URL url = new URL(src);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoInput(true);
      connection.connect();
      InputStream input = connection.getInputStream();
      Bitmap myBitmap = BitmapFactory.decodeStream(input);
      return myBitmap;
    } catch (IOException e) {
      // Log exception
      return null;
    }
  }
}
