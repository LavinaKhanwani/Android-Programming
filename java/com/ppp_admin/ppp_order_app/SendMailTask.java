package com.ppp_admin.ppp_order_app;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Admin on 10/11/2015.
 */
public class SendMailTask extends AsyncTask
{
    @Override
    protected Object doInBackground(Object... args)
    {
        try {
            Log.i(String.valueOf(R.string.SendMailTask_contex), String.valueOf(R.string.SendMailTask_Log_Message1));
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(), (List) args[2], args[3].toString(),
                    args[4].toString());

            Log.i(String.valueOf(R.string.SendMailTask_contex),String.valueOf(R.string.SendMailTask_Log_Message2));
            androidEmail.createEmailMessage();
            Log.i(String.valueOf(R.string.SendMailTask_contex),String.valueOf(R.string.SendMailTask_Log_Message3));
            androidEmail.sendEmail();
            Log.i(String.valueOf(R.string.SendMailTask_contex), String.valueOf(R.string.SendMailTask_Log_Message4));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }
}
