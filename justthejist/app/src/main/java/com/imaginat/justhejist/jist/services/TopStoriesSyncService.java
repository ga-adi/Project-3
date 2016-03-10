package com.imaginat.justhejist.jist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.imaginat.justhejist.jist.sync.TopStoriesSyncAdapter;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class TopStoriesSyncService extends Service {
  private static TopStoriesSyncAdapter mAdapter = null;
  private static final Object mSyncAdapterLock = new Object();

  @Override
  public void onCreate() {
    super.onCreate();

    synchronized (mSyncAdapterLock) {
      if (mAdapter == null) {
        mAdapter = new TopStoriesSyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mAdapter.getSyncAdapterBinder();
  }
}
