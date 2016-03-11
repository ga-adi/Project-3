package com.charlesdrews.hud;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Launches notifications for user's reminder items
 * Created by charlie on 3/11/16.
 */
public class ReminderService extends IntentService {
    public static final int NOTIFICATION_ID = 43;
    public static final String SCROLL_TO_REMINDERS = "scrollToReminders";

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent clickIntent = new Intent(this, MainActivity.class);
        clickIntent.putExtra(SCROLL_TO_REMINDERS, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_notifications_black_18dp)
                .setContentTitle("Reminder")
                .setContentText(intent.getStringExtra(DatabaseHelper.REMINDERS_COL_TEXT))
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
