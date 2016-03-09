package com.charlesdrews.hud.News;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.charlesdrews.hud.News.NewsSyncAdapter;

/**
 * Created by charlie on 3/7/16.
 */
public class NewsSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static NewsSyncAdapter sNewsSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sNewsSyncAdapter == null)
                sNewsSyncAdapter = new NewsSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sNewsSyncAdapter.getSyncAdapterBinder();
    }
}
