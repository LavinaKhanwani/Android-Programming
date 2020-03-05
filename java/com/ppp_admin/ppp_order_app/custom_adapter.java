/**
 * Created by Admin on 9/26/2015.
 */
package com.ppp_admin.ppp_order_app;


import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.json.PackageVersion;

import java.util.ArrayList;

 /********************************************************************************************
 * Creating class as custom_adapter by extends BaseAdapter
 * Implements all method of BaseAdapter
 ********************************************************************************************/
public class custom_adapter extends BaseAdapter {

    // set ArrayList for Single_row and create object of ArrayList
    ArrayList<Single_row> list;
    // create the Context
    private static Context context;

    // create the Constructor for custom_adapter.java class
    custom_adapter(Context new_context) {

        context = new_context;
        // define object of ArrayList<Single_row>
        list = new ArrayList<Single_row>();
    }

    /******************************************************************************************
     Name :- getList
     Output :- ArrayList<Single_row>
     Input :- returning list of arraylist object
     Description :- creating getter for ArrayList<Single_row>
     ******************************************************************************************/
    public ArrayList<Single_row> getList() {
        return list;
    }

    public void setList(ArrayList<Single_row> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        /****************************************************
         --> get the root View
         --> use the root view to find other views
         --> set the values
         ****************************************************/

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_single_row, viewGroup, false);

        // create textview object for call and sms button
        TextView final_wish = (TextView) row.findViewById(R.id.text_view_final_wish);
        Button call_button = (Button) row.findViewById(R.id.call_button);
        Button sms_button = (Button) row.findViewById(R.id.sms_button);

        final Single_row new_list = list.get(i);
        final_wish.setText(new_list.getFinal_wish());

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String uri = Contact_Person;
                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:" + new_list.getNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(in);
              }
          });

          sms_button.setOnClickListener(new View.OnClickListener() {

              final String cont = new_list.getNumber();
              String msg = String.valueOf(R.string.cutomerAdapter_SMS);

              @Override
              public void onClick(View v)
              {
                // confirm_sms_to_be_send(cont, msg);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle(R.string.customAdapter_alertTitle);
                // set dialog message
                alertDialogBuilder.setMessage(R.string.customAdapter_alertMessage);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton(R.string.customAdapter_alertPositive_ButtonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //Getting intent and PendingIntent instance
                        Intent Send_intent = new Intent(context.getApplicationContext(), Home_Page.class);
                        PendingIntent send_pending = PendingIntent.getActivity(context.getApplicationContext(), 0, Send_intent, 0);
                        //Get the SmsManager instance and call the sendTextMessage method to send message
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(cont, null, msg, send_pending, null);
                        Toast.makeText(context.getApplicationContext(), R.string.customAdapter_alert_Toastmessage, Toast.LENGTH_LONG).show();
                    }
                });
                   alertDialogBuilder.setNegativeButton(R.string.customerAdapter_alertNegative_ButtonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
                  // create alert dialog
                  AlertDialog alertDialog = alertDialogBuilder.create();
                  // show it
                  alertDialog.show();
            }
        });
           // return the view of single_row.xml
           return row;
       }
 }