package com.ppp_admin.ppp_order_app;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Admin on 4/13/2016.
 */
public class SyncAdapterService extends Service
{
    private static final Object syncAdapterLock = new Object();
    private static SyncAdapter syncAdapter = null;

    @Override
    public void onCreate()
    {
        synchronized (syncAdapterLock)
        {
            if (syncAdapter == null)
               syncAdapter = new SyncAdapter(getApplicationContext(), true);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
        {
        return syncAdapter.getSyncAdapterBinder();
    }
}
