package com.ppp_admin.ppp_order_app;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Admin on 12/10/2015.
 */
public class Dialog_DatePicker_addAccount extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    EditText datePicker_editText;

    @SuppressLint("ValidFragment")
    public Dialog_DatePicker_addAccount(){}

    @SuppressLint("ValidFragment")
    public Dialog_DatePicker_addAccount(View view)
    {
        datePicker_editText = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // create calender object and getInstance
        final Calendar dateCalender = Calendar.getInstance();
        // get date into int variable
        int year = dateCalender.get(Calendar.YEAR);
        // get month into int variable
        int month=dateCalender.get(Calendar.MONTH);
        // get day into into variable
        int day=dateCalender.get(Calendar.DAY_OF_MONTH);
        // return DatePickerDialog
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        // get user selected date into string variable
        String userSeletected_Date = year + "-" + (month+1) + "-" + day;

        // set datepicker to edittext
        datePicker_editText.setText(userSeletected_Date);

    }
}
