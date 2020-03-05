package com.ppp_admin.ppp_order_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 12/16/2015.
 */
public class Utility
{
    /**********************************************************************************************
     Name :- Name_ValidateLetters
     Input :- Pass string name through Validate_AccountName and edittext through edittext
     Description :- Check validation for Names that entered by user.
     **********************************************************************************************/
    public static boolean Name_validateLetters(String Validate_AccountName, TextView error_show_textview,ScrollView scrollView_error)
    {
        String regx = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(Validate_AccountName);
        if(!matcher.find()){
            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid Name.Name Should be like 'Alice David' !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);
            return false;

        }
        return true;
    }
    /***********************************************************************************************/
    /**********************************************************************************************
     Name :- City_State_validateLetters
     Input :- Pass string City_State name and edittext through edittext
     Description :- Check validation for City name and State Name.
                    It will accept with space and without space City and State name.
     **********************************************************************************************/
    public  static boolean City_State_validateLetters(String City_State,TextView error_show_textview,ScrollView scrollView_error)
    {
        String regx = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(City_State);
        if(!matcher.find() || City_State.isEmpty()){
            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid Entry for City/State !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
    }
    /***********************************************************************************************/
    /**********************************************************************************************
     Name :- Pincode_validation
     Input :- Pass string Validate_PincodeNo when user enter pincode
     Description :- Check Pincode validation entered by user.
                    if entered number is six digit then it doesn't show any error and Vice versa.
     **********************************************************************************************/
    public static boolean Pincode_validation(String Validate_PincodeNo ,TextView error_show_textview,ScrollView scrollView_error)
    {
        String regx = "^[0-9]{6}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(Validate_PincodeNo);

        // check condition boolean value if it will false then error will be set and vice versa
        if(!matcher.find()){
            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid PinCode. PinCode should be of six digits !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
    }
    /***********************************************************************************************/

    /**********************************************************************************************
     Name :- Date_validation
     Input :- Pass string date, EditText editText and boolean flag.
     Description :- check validation for Date and also check format also.
     **********************************************************************************************/
    public static boolean Date_validaiton(String date ,TextView error_show_textview, boolean flag,ScrollView scrollView_error)
    {
        String regex = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        SimpleDateFormat convert_default_format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            if (!date.isEmpty() && !date.equals("") && !date.equals(" "))
            {
                // convert date into Date format
                Date InputDate_user = convert_default_format.parse(date);

                // convert date into miliseconds
                long convert_InputDate = InputDate_user.getTime();

                // get instance of calender
                Calendar currentDate = Calendar.getInstance();

                // convert current_date into miliseconds
                long convert_currentdate = currentDate.getTimeInMillis();

                // check condition to compare inputed_date to current_date
                if (convert_InputDate > convert_currentdate) {

                    error_show_textview.setVisibility(View.VISIBLE);
                    error_show_textview.setFocusable(true);
                    // if condition will be true then error will be set
                    error_show_textview.setText(" Invalid date !!");
                    scrollView_error.fullScroll(ScrollView.FOCUS_UP);
                    // return boolean value
                    return false;
                }
                // check condition of date and boolean value also
                if (!matcher.find() & flag) {

                    error_show_textview.setVisibility(View.VISIBLE);
                    error_show_textview.setFocusable(true);
                    // if it will be true then error will be set
                    error_show_textview.setText(" Invalid Date.Date Format should be \'YYYY-MM-DD\' !!");
                    error_show_textview.setFocusable(true);
                    scrollView_error.fullScroll(ScrollView.FOCUS_UP);
                    // return boolean value
                    return false;
                }
            }
            else   {Log.i("DATE MSG" ,"Date is Empty");}
            }
            catch(ParseException e){
                e.printStackTrace();
            }
        return true;
    }

    public static boolean Address_Transport_validation(String str, TextView error_show_textview,String err_msg,ScrollView scrollView_error) {
        str.trim();
        if (str.isEmpty()) {
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Please enter "+ err_msg +" !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return  true;
    }

    /**********************************************************************************************
     Name :- MobileNo_validation
     Input :- pass string Validate_ContactPersonMobileNo and EditText editText
     Description :- check validation for Mobile No. If Mobile No will be 10 digit then it will
                    it doesn't show any error other wise it will show an error.
     **********************************************************************************************/
    public static boolean MobileNo_validation(String Validate_ContactPersonMobileNo , TextView error_show_textview,
                                              boolean flag,ScrollView scrollView_error)
    {
        String MobilePattern = "^0?[1-9][0-9]{9}$";
        Pattern pattern=Pattern.compile(MobilePattern);
        Matcher matcher = pattern.matcher(Validate_ContactPersonMobileNo);

        // check condition boolean value if it will false then error will be set and vice versa
        if(!matcher.find() & flag) {

            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid MobileNo.Mobile No should be 10 digit !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);
            return false;
        }
        return true;
    }
    /***********************************************************************************************/

    /**********************************************************************************************
     Name :- LandlineNo_validation
     Input :- pass sting Validate_landlineNo
     Description :- check validation for Landline no.
     **********************************************************************************************/
     public static boolean LandlineNo_validation(String Validate_LandlineNo,TextView error_show_textview, ScrollView scrollView_error)
     {
        String LandLinePattern = "^0?[0-9]{11}$";
        Pattern pattern=Pattern.compile(LandLinePattern);
        Matcher matcher = pattern.matcher(Validate_LandlineNo);

        // check condition boolean value if it will false then error will be set and vice versa
        if(!matcher.find()) {

            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid Landline No.No should be 0 followed by 11 digit !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
     }

    /***********************************************************************************************
     Name :- AccountEmail_validation
     Input :- pass String Validate_EmailId
     Description :- Check validation for email entered by user
     ***********************************************************************************************/
     public  static boolean AccountEmail_validation(String Validate_EmailId,TextView error_show_textview,boolean flag,ScrollView scrollView_error)
     {
         if(!Validate_EmailId.isEmpty() && !Validate_EmailId.equals("") && !Validate_EmailId.equals(" ")) {
             String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                     + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
             Pattern pattern = Pattern.compile(EMAIL_PATTERN);
             Matcher matcher = pattern.matcher(Validate_EmailId);

             // check condition boolean value if it will false then error will be set and vice versa
             if (!matcher.find() & flag) {
                 error_show_textview.setVisibility(View.VISIBLE);
                 error_show_textview.setFocusable(true);
                 error_show_textview.setText(" Invalid Email ID !!");
                 scrollView_error.fullScroll(ScrollView.FOCUS_UP);
                 return false;
             }
         }
         else {
             Log.i("Email ID ", "Is Empty");
         }
         return true;
     }

    /***********************************************************************************************
     Name :- FaxNo_validation
     Input :- pass string Validate_Faxno
     Description :- Check validation for FaxNo entered by user.
     ***********************************************************************************************/
     public static boolean FaxNo_validation(String Validate_Faxno, TextView error_show_textview,ScrollView scrollView_error)
     {
        String FaxPattern = "^[0-9]{10}$";
        Pattern pattern=Pattern.compile(FaxPattern);
        Matcher matcher = pattern.matcher(Validate_Faxno);

        // check condition boolean value if it will false then error will be set and vice versa
        if(!matcher.find() & !Validate_Faxno.isEmpty()) {

            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid FaxNo.FaxNo should be 10 digit !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
     }

    /***********************************************************************************************
     Name :- panNo_validation
     Input :- Pass String Validate_PanNo
     Description :- Check validation for PAN No entered by user.
     ***********************************************************************************************/
     public  static boolean panNo_validation(String Validate_PanNo, TextView error_show_textview,boolean flag,ScrollView scrollView_error)
     {
         if(!Validate_PanNo.isEmpty() && !Validate_PanNo.equals("") && !Validate_PanNo.equals(" ")) {
             String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
             Pattern pattern = Pattern.compile(regex);
             Matcher matcher = pattern.matcher(Validate_PanNo);

             // check condition boolean value if it will false then error will be set and vice versa
             if (!matcher.find() & flag) {

                 error_show_textview.setVisibility(View.VISIBLE);
                 error_show_textview.setFocusable(true);
                 error_show_textview.setText(" Invalid PanNo.It should have " +
                         "first five characters letters, "
                         + "next four numerals, last character letter. !!");
                 scrollView_error.fullScroll(ScrollView.FOCUS_UP);

                 return false;
             }
         }
        return true;
     }

    /***********************************************************************************************
     Name :- DiscountRate_validation
     Input :- Pass string Validate_DiscountRatePercent
     Description :- Check validation entered by user.
     ***********************************************************************************************/
     public static boolean DiscountRate_validation(String Validate_DiscountRatePercent, TextView error_show_textview,ScrollView scrollView_error)
      {
        String DiscountPattern = "^\\d{0,2}(\\.\\d{1,3})? *%?$";
        Pattern pattern=Pattern.compile(DiscountPattern);
        Matcher matcher = pattern.matcher(Validate_DiscountRatePercent);

        // check condition boolean value if it will false then error will be set and vice versa
        if(!matcher.find() || Validate_DiscountRatePercent.isEmpty()) {

            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Invalid Discount Rate !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
      }

    /********************************************************************************************
     Name :- UsernameValidator
     Description :- Validate username will be accept in characters with number or only
     characters from user.
     ********************************************************************************************/
     public static boolean UsernameValidator(String UserName,TextView error_show_textview,ScrollView scrollView_error)
     {
        String USERNAME_PATTERN = "^[a-zA-Z]+[0-9_]*$";
        Pattern pattern =Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(UserName);

        if(!matcher.find())
        {
            error_show_textview.setVisibility(View.VISIBLE);
            error_show_textview.setFocusable(true);
            error_show_textview.setText(" Only letters and numbers are allowed !!");
            scrollView_error.fullScroll(ScrollView.FOCUS_UP);

            return false;
        }
        return true;
     }
    /*********************************************************************************************/

    /***********************************************************************************************
     * Name :- check_Internet
     * Input :- Connectivity Manager, Context
     * Description :- This function checks if there is any active network and returns true or false
                     accordingly
     **********************************************************************************************/
     public static final boolean check_Internet(ConnectivityManager cm, Context context) {
        // ConnectivityManager connect_manager = cm ;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            String type_name = networkInfo.getTypeName();
            boolean connected = networkInfo.isConnected();

            if (connected) {
                return true;
            }
            else{
                Toast.makeText(context, "Looks like something is wrong with your Internet", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(context, "Looks like something is wrong with your Internet", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /***********************************************************************************************
     * Name :- show_progress_bar
     * Input :- ProgressDialog, message
     * Description :- This function shows a progress bar with the provided message
     **********************************************************************************************/
     public static final void show_progress_bar(ProgressDialog progressBar, String msg){
        // set Cancelable true so that bar should automatically cancelled
        progressBar.setCancelable(true);
        // set message which displays in bar
        progressBar.setMessage(msg);
        // set touch outside boolean value
        progressBar.setCanceledOnTouchOutside(false);
        // set bar style
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // set minimum no of progress bar from which points it should starts
        progressBar.setProgress(0);
        // set maximum no of progress bar at which point it should finish
        progressBar.setMax(100);
        // set progress will show
        progressBar.show();
    }

    /**********************************************************************************************
        Name :- check_values_nonISI
        Input :- Pass string info,product name and color.
        Description :- Check if the this product contains ivory color
    **************************************************************************************************/
    public static boolean check_values_nonISI(String product_name, Context context) {
        if (product_name.contains("PATTI SADDLE") || product_name.contains("CLIP") ||
            product_name.contains("COUPLING") || product_name.contains("BEND") || product_name.contains("JUNCTION BOX")) {
            if (product_name.contains("32 MM") || product_name.contains("40 MM") || product_name.contains("50 MM")) {
                if (product_name.contains("IVORY")) {
                    Toast.makeText(context, "These products 32 MM/40MM/50MM doesn't contains ivory color", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }
}
