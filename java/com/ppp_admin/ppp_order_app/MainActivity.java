package com.ppp_admin.ppp_order_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.content.Intent;

public class MainActivity extends AppCompatActivity
{
    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/

    /********* Periodic Sync variables declaration ************************************************
     // Content provider authority
     public static final String AUTHORITY = "app.admin.pressfit.provider";

     // Define Account type
     public static final String ACCOUNT_TYPE = "app.admin";

     // Define default Account
     public static final String ACCOUNT = "pressfit_account";

     // A content resolver for accessing the provider
     private static ContentResolver mResolver;

     // Define Account object or instance fields of Account
     Account mAccount;

     // A content URI for the content provider's data table
     Uri mUri;

     /*public static TableObserver observer;*/
    /*************************************************************************/

     /*  --> TableObserver class use to attached to AccountMaster table in local database.
        --> If any changes made by user in local DB it will automatically dictated then action
            will be performed. */
   /* public class TableObserver extends ContentObserver
    {
        /* Creates a content observer.
         * @param handler The handler to run {@link #onChange} on, or null if none.
        public TableObserver(Handler handler)  { super(handler); }

        /* --> Define a method that's called when data in the observed content provider changes.
           --> This method signature is provided for compatibility with older platforms.
        @Override
        public void onChange(boolean selfChange)
        {
            /* Invoke the method signature available as of Android platform version 4.1,
                 with a null URI.

            Log.i("ON CHANGE :", "Entered OnChange 1 Function");

            onChange(selfChange, null);
        }

        /*  Define a method that's called when data in the observed content provider changes.
        @Override
        public void onChange(boolean selfChange, Uri changeUri)
        {
            SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
            String ID = loginSharedPref.getString("ID","");

            /* Ask the framework to run your sync adapter.
             * To maintain backward compatibility, assume that  changeUri is null.
            Log.i("ON CHANGE :", "Entered OnChange 2 Function");

            Bundle uriBundle = new Bundle();
            uriBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            uriBundle.putString("URI", "AccountMaster");
            uriBundle.putString("AgentID" , ID);

            Log.i("URI :", "AccountMaster");
            ContentResolver.requestSync(mAccount, AUTHORITY, uriBundle);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define Handler to Handle PressFit logo animation
        final Handler mHandler = new Handler();

        // call function to create sync adapter
        // CreateDataToSyncProductTable();

        //CreateDataSyncAccountMasterTable();

        // Call new activity
        mHandler.postDelayed(new Runnable() {
            public void run() {
                // condition check
                if (checkSharedPref()) {
                    Intent intent = new Intent(MainActivity.this, Home_Page.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, Login_form.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        }, 3000);

        boolean isTab = getResources().getBoolean(R.bool.isTab);
        if(isTab) {
            // call translate function
            translate_tab();
        } else {
            translate_handset();
        }
    }

    /******************************************************************************************
     Name :- CreateDataSyncAccountMasterTable
     Description :- call function to store or add data from local to server
     When any changes done in local database , it will automatically  add
     all data into server by using Table Observer.
     ******************************************************************************************
     public void CreateDataSyncAccountMasterTable() {
     Log.i("Entered  :", "CreateDataSyncAccountMasterTable function ");

     // Get the content resolver object for your app
     mResolver = getContentResolver();

     Log.i("Content Resolver  :", "Check point 1");
     // Construct a URI that points to the content provider data table
     mUri = new Uri.Builder()
     .scheme("content://")
     .authority(AUTHORITY)
     .path("AccountMaster")
     .build();

     Log.i(" mUri  :", "check point 2 ");

     /* Create a content observer object. Its code does not mutate the provider, so set
     selfChange to "false"
     observer = new TableObserver(null);

     Log.i("Observer object  :", "check point 3 ");

     /*  Register the observer for the data table.
     The table's path  and any of its subpaths trigger the observer.
     mResolver.registerContentObserver(mUri, true, observer);

     Log.i("mResolver  :", "check point 4 ");
     }*/

    /**********************************************************************************************
     Name :- CreateDataToSyncProductTable
     Description :- call function server to sync local database.
     Product Data will change periodically if in the server any changes will be done.
     All server product data will store into local database.
     *********************************************************************************************
     public void CreateDataToSyncProductTable()
     {
     SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
     String ID = loginSharedPref.getString("ID", "");

     Log.i("ENTERED :","CreateDataToSyncProductTable function");

     // Create the account type and default account
     mAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

     Log.i("CREATED :","Sync Adapter Account was created...");

     // Get an instance of the Android account manager
     AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);

     // Add the account and account type, no password or user data
     accountManager.addAccountExplicitly(mAccount, null, null);

     // Get the content resolver for your app
     mResolver = getContentResolver();
     Log.i("CALLING :","Calling Sync Adapter On Perform Sync Function");

     // create Bundle object
     Bundle productBundle = new Bundle();
     Bundle AccountBundle = new Bundle();
     Bundle OrderTableBundle = new Bundle();
     Bundle ListTableBundle = new Bundle();

     // Put Product data inserting into URI string through bundle
     productBundle.putString("URI", "Product");
     Log.i("CALLING :", "AccountMaster pass URI");
     AccountBundle.putString("URI", "AccountMaster");
     AccountBundle.putString("AgentID", ID);
     OrderTableBundle.putString("URI","OrderTable");
     ListTableBundle.putString("URI","ListTable");
     ListTableBundle.putString("AgentID", ID);

     /* Turn on periodic syncing *
     ContentResolver.setMasterSyncAutomatically(true);
     ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);

     Log.i("CALLING :","Loading product data");
     ContentResolver.addPeriodicSync(mAccount, AUTHORITY, productBundle, 86400);//************** >>>>>>> sync after 24hr

     Log.i("CALLING :", "AccountMaster call periodic sync");
     ContentResolver.addPeriodicSync(mAccount, AUTHORITY, AccountBundle, 86400);//************** >>>>>>> sync after 15days

     Log.i("CALLING :", "CreateDataToSyncOrderTable function");
     ContentResolver.addPeriodicSync(mAccount, AUTHORITY, OrderTableBundle, 86400);//*************** >>>>>>>> sync after 24hr

     Log.i("CALLING :", "Create Lists table to sync function");
     ContentResolver.addPeriodicSync(mAccount,AUTHORITY,ListTableBundle,86400);//*************** >>>>>>>> sync after 24hr
     }*/

    /******************************************************************************************
     Name :- checkSharedPref
     Description :- function check username and password is same or not if its same return
     false otherwise it will return true.
     ******************************************************************************************/
    public boolean checkSharedPref() {

        // create object SharedPreference object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);

        // get all values from SharedPreferences object to String objects
        String usr = loginSharedPref.getString("Username", "");
        String pswrd = loginSharedPref.getString("Password", "");
        String Name = loginSharedPref.getString("Name", "");
        String mobileNO = loginSharedPref.getString("MobileNo", "");
        String gender = loginSharedPref.getString("gender", "");
        String DOB = loginSharedPref.getString("dob", "");
        String emailID = loginSharedPref.getString("emailID", "");
        String ID = loginSharedPref.getString("ID", "");

        // condition checks whether user name and password is match or not
        if (usr.equals("") || pswrd.equals("")) { return false; }
        else {
            // create Globals object
            Globals g = (Globals) getApplication();

               /* condition checks if all values are empty then set values into
                LoginUser_Data object and after that set into globals object. */

            if(!Name.equals("") && !mobileNO.equals("") &&
                    !gender.equals("") && !DOB.equals("") && !emailID.equals("") && !ID.equals("")) {

                // create object of LoginUser_Data
                LoginUser_Data s = new LoginUser_Data();

                // set values into object
                s.setName(Name);
                s.setUserName(usr);
                s.setUserPassword(pswrd);
                s.setEmail_ID(emailID);
                s.setMobileNo(mobileNO);
                s.setGender(gender);
                s.setDateOfBirth(DOB);
                s.setID(ID);

                // set login user_data object into globals
                g.setLoginData(s);
                return true;
            }
            else {return false;}
        }
    }

    /*********************************************************************************************
     Name :- translate
     Description :- Setting the PressFit logo to RelativeLayout, duration to animate Logo
     *********************************************************************************************/
    public void translate_handset() {

        // RelativeLayout for PressFit Logo
        final RelativeLayout r = (RelativeLayout) findViewById(R.id.relativeLayout);
        final ImageView imageView = (ImageView) findViewById(R.id.pressfitlogo);
        imageView.clearAnimation();
        try {
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                    0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
                    0, Animation.RELATIVE_TO_PARENT, -0.48f);
            animation.setDuration(2000);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }
        catch (Exception e)
        {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

    public void translate_tab(){

        // RelativeLayout for PressFit Logo
        final RelativeLayout r = (RelativeLayout) findViewById(R.id.relativeLayout);
        final ImageView imageView = (ImageView) findViewById(R.id.pressfitlogo);
        imageView.clearAnimation();
        try {
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                    0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
                    0, Animation.RELATIVE_TO_PARENT, -0.20f);
            animation.setDuration(2000);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }
        catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }
    /**********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}