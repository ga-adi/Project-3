package com.charlesdrews.hud.Facebook;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by charlie on 3/7/16.
 */
public class FacebookSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static FacebookSyncAdapter sFacebookSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sFacebookSyncAdapter == null)
                sFacebookSyncAdapter = new FacebookSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sFacebookSyncAdapter.getSyncAdapterBinder();
    }
}
