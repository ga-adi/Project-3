package com.charlesdrews.hud.Weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by charlie on 3/7/16.
 */
public class WeatherSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static WeatherSyncAdapter sWeatherSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sWeatherSyncAdapter == null)
                sWeatherSyncAdapter = new WeatherSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sWeatherSyncAdapter.getSyncAdapterBinder();
    }
}
