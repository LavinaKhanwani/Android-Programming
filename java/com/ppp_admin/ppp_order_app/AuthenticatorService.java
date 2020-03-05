package com.ppp_admin.ppp_order_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 * Created by Admin on 4/11/2016.
 */
public class AuthenticatorService extends Service
{

    Authenticator authenticator ;

    @Override
     public void onCreate() {

         authenticator = new Authenticator(this);

         Toast.makeText(this,"Done" , Toast.LENGTH_LONG).show();
         super.onCreate();
     }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return  authenticator.getIBinder();
    }

}
