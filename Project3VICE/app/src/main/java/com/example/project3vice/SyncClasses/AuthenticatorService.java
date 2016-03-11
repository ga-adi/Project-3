package com.example.project3vice.SyncClasses;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Todo on 3/7/2016.
 */
public class AuthenticatorService extends Service {
    private FakeAuthenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new FakeAuthenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
