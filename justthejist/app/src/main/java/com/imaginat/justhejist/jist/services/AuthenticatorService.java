package com.imaginat.justhejist.jist.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.imaginat.justhejist.jist.sync.Authenticator;

/**
 * Created by nat on 3/8/16.
 */
public class AuthenticatorService extends Service {

  private Authenticator mAuthenticator;

  @Override
  public void onCreate() {

    mAuthenticator = new Authenticator(this);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mAuthenticator.getIBinder();
  }
}
