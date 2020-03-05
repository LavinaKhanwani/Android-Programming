package com.ppp_admin.ppp_order_app;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Admin on 5/11/2016.
 */
public class SendMailTask_ConfirmOrder extends AsyncTask
{

    @Override
    protected Object doInBackground(Object... args)
    {
        try {
            Log.i(String.valueOf(R.string.SendMailTask_contex), String.valueOf(R.string.SendMailTask_Log_Message1));
            GMail_ConfirmOrder androidEmail = new GMail_ConfirmOrder(args[0].toString(),
                    args[1].toString(), args[2].toString(), (List) args[3], args[4].toString(),
                     args[5].toString()) ;

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
