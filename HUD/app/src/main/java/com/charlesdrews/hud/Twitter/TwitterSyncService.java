package com.charlesdrews.hud.Twitter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.charlesdrews.hud.Weather.WeatherSyncAdapter;

/**
 * Created by charlie on 3/7/16.
 */
public class TwitterSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static TwitterSyncAdapter sTwitterSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sTwitterSyncAdapter == null)
                sTwitterSyncAdapter = new TwitterSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sTwitterSyncAdapter.getSyncAdapterBinder();
    }
}
