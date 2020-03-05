package com.ppp_admin.ppp_order_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class Home_Page extends Activity implements AsyncResponse
{
    /***********************************************************************************************
     * Section for declaration of the variables
     ***********************************************************************************************/
    public int currentimageindex = 0;
    private static Context context;
    private MobileServiceTable<LoginUser_Data> loginUser_data;
    HashMap<String ,String> agent_details = new HashMap<>();
    Globals g;
    // set the Image in Array
    private int[] IMAGE_IDS = {
            R.drawable.background1, R.drawable.backgound2, R.drawable.background3, R.drawable.background4,
            R.drawable.background5, R.drawable.background7,R.drawable.background8,
            R.drawable.background9,R.drawable.background10,R.drawable.background11,R.drawable.background6};

    // IMAGE_IDS_LENGTH calculate the length of Image
    private int IMAGE_IDS_LENGTH = IMAGE_IDS.length;
    /*******************************************************************************************/

    /********* Periodic Sync variables declaration ************************************************/
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /* Assigning the corresponding layout page */
        setContentView(R.layout.activity_home_page);

        // checking internet connection
        ConnectivityManager cm = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        boolean is_internet_active = Utility.check_Internet(cm, getApplicationContext());

        CreateDataToSyncProductTable();

        g = (Globals) getApplication();
        // g.getFinal_order();
        // check condition boolean value is true or false
        if(!g.isProduct_load_complete_flag()){
            getAllProductList();
        }

        if(is_internet_active) {
            // Call Async Task function
            callHttpRequestTask();
        }
        TextView welcome_textView = (TextView) findViewById(R.id.text_view_welcome);
        //intent = getIntent();

        // show Welcome Textbox
        welcome_textView.setText(" Welcome ");

        getIntent().addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        background_animation();

    }

    /**********************************************************************************************
     Name :- CreateDataToSyncProductTable
     Description :- call function server to sync local database.
     Product Data will change periodically if in the server any changes will be done.
     All server product data will store into local database.
     *********************************************************************************************/
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

         /* Turn on periodic syncing */
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
    }


    /***********************************************************************************************
     * Name :- background_animation
     * Description:- This function creates a handler that sets period each background image should be
     * displayed
     ***********************************************************************************************/

    public void background_animation() {

        final Handler mHandler = new Handler();

        // Background Image set
        final RelativeLayout bg_sliding_image = (RelativeLayout) findViewById(R.id.background);

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow(bg_sliding_image);
            }
        };

        int delay = 1500; // delay for 1 sec.
        int period = 2500; // repeat every 4 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                mHandler.post(mUpdateResults);
            }

        }, delay, period);
    }
    /***********************************************************************************************
     * Name : callHttpRequestTask
     * Description: Calls the Async Task that in turns calls the web service url to retrieve data
     ***********************************************************************************************/
    public void callHttpRequestTask() {

        // declare object of HttpRequestTask class
        HttpRequestTask Task = new HttpRequestTask();

        // call delegates from context
        Task.delegate = this;

        // execute task
        Task.execute();
    }

    /*********************************************************************************************
     * Name : getAppContext
     * Output: Context
     * Description: Returns the context of this activity
     *********************************************************************************************/
    public static Context getAppContext() {
        return Home_Page.context;
    }

    /***********************************************************************************************
     * Name :- onNewIntent
     * Input :- Pass the Intent object and call the " processExtraData(); ".
     * Description :- onNewIntent() is meant as entry point for singleTop activities which already
     * run somewhere else in the stack and therefor can't call onCreate().
     ***********************************************************************************************/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    /****************************************************************************************************
     * Name :- processExtraData
     * Input :- getting today's birthday date from  arraylist from account_master
     * Description  :- ProcessExtraData set the Data in Textview that come from Alarm_Service_1.java class
     ****************************************************************************************************/
    private void processExtraData(final ArrayList<Account_Master> today_birthday_accounts)
            throws ExecutionException, InterruptedException {

        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String agentID = loginSharedPref.getString("ID", "");

        // create the custom_adapter.java class object
        final custom_adapter custom_adapter = new custom_adapter(this);

        // set the ListView adapter in wishes_list_view object
        ListView wishes_list_view = (ListView) findViewById(R.id.listView);
        wishes_list_view.setAdapter(custom_adapter);

        // set loop get today's birthday date into Account_Master object
        for (Account_Master a : today_birthday_accounts) {

            /*************************************************************************************
             --> check condition for admin that loing data match from data base
             --> if condition matches then admin can see all data or birthday message including
             there sales person and sales' party name.
             *************************************************************************************/
            if (agentID.equals(a.getAgentID()))
            {
                // get account_name into string variable
                final String Account_Name = a.getAccountName();

                // get contact number into string variable
                final String Contact_Person = a.getContactPersonMobileNo();

                /********************************************************************************
                 This condition checks that date matches to current date if it will be true
                 then it will show message otherwise it will go in else part.
                 ********************************************************************************/
                String final_wish = "";
                if (a.getAnniversaryDate().equals(Get_current_date())) {
                    // storing message into string variable including Account_name
                    final_wish = "PressFit Wish " + Account_Name
                            + " Happy Anniversary a great year ahead with beautiful memories.";
                }
                else {
                    // storing message into string variable including Account_name
                    final_wish = "PressFit Wish "+ Account_Name
                            + " Happy Birthday great year ahead with beautiful memories.";
                }

                // set Single_row.java class object for pass parameter
                Single_row single_row = new Single_row(final_wish, Contact_Person);

                // get array list from getter as "get list" through custom_adapter object and add it to single_row object
                custom_adapter.getList().add(single_row);

                // set visibility of listview as visible
                wishes_list_view.setVisibility(View.VISIBLE);
            }
        }
    }

    /***********************************************************************
     Name :- Get_current_date
     Output:- returns today's date in string format
     Description :- converting today;s date into 'yyyy-mm-dd' format
     ***********************************************************************/
    public String Get_current_date() {

        // set format of date
        SimpleDateFormat convert_default_format = new SimpleDateFormat("yyyy-MM-dd");

        // convert date fromat into string from
        String today_date = convert_default_format.format(new Date());

        // return date
        return today_date;
    }
    /*********************************************************************************************
     * Name :- AnimateandSlideShow
     * Input :- Pass Image to RelativeLayout object through bg_sliding_image
     * Description :- Sliding the Images in background of Home_Page
     *********************************************************************************************/
    private void AnimateandSlideShow(RelativeLayout bg_sliding_image) {
        bg_sliding_image.setBackgroundResource(IMAGE_IDS[currentimageindex % IMAGE_IDS_LENGTH]);
        currentimageindex++;
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.slide_image_fadein);
        bg_sliding_image.startAnimation(rotateimage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        boolean reviewFlag = false;
        /***********************************************************************************
         --> It will shoe menu item into an action bar on click of any menu it
         will perform action on it.
         --> In switch case it will check id from that the user clicks on which menu and
         according to it case will call.
         ************************************************************************************/
        switch (id) {

            // set case for sign_out page
            case R.id.Sign_out : Intent signOut_intent = new Intent(Home_Page.this, Login_form.class);
                /* SharedPreferences  remove data if user signout */
                SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPref.edit();
                editor.clear();
                editor.commit();
                finish();
                Globals g = (Globals) getApplication();
                g.getFinal_order().clear();
                startActivity(signOut_intent);
                break;

            // set case for account_details page to call
            case R.id.add_account : Intent AccountDetails_intent = new Intent(Home_Page.this,Account_Details.class);
                AccountDetails_intent.putExtra("review_flag", reviewFlag);
                startActivity(AccountDetails_intent);
                break;

            // set case fro call product_page
            case R.id.add_products :
                // Sync the products if internet is active
                ConnectivityManager cm = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                boolean is_internet_active = Utility.check_Internet(cm, getApplicationContext());
                Intent addProducts_intent = new Intent(Home_Page.this,Products_Page.class);
                startActivity(addProducts_intent);
                break;

            // set case for call Update_Login_User_Detail
            case R.id.update_profile :
                Intent UpdateProfile_intent = new Intent(Home_Page.this,Update_Login_User_Detail.class);
                startActivity(UpdateProfile_intent);
                break;

            case R.id.my_list :
                Intent intent_my_list = new Intent(Home_Page.this,MyList.class);
                startActivity(intent_my_list);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void processfinish(ArrayList<Account_Master> accounts)
    {
        // call function of Show_List
        Show_List(accounts);
    }

    /**********************************************************************************************
     * Name :- setAlarm
     * Output :- today_birthday_accounts pass ArrayList from Account_Master_bkp
     * Description :- get time from calender according to that check condition that current time
     is greater then or not if it will be greater colse programme other wise
     it will pass todays_birthday details to processExtraData function.
     **********************************************************************************************/
    public void Show_List(ArrayList<Account_Master> today_birthday_accounts) {

        try {

            // get calender instance for firing time
            Calendar firingCal = Calendar.getInstance();

            // get calender instance for current time
            Calendar currentCal = Calendar.getInstance();

            // set resources
            Resources r = getResources();

            // get hour_of_day into int variable
            int hour = r.getInteger(R.integer.alarm_hour_ofDay);

            // get mintue into int variable
            int minute = r.getInteger(R.integer.alarm_minute);

            // get second into variable
            int second =r.getInteger(R.integer.alarm_seconds);

            // set hour, minute and second into calender object at which time it should fire alarm
            firingCal.set(Calendar.HOUR_OF_DAY, hour);
            firingCal.set(Calendar.MINUTE,minute);
            firingCal.set(Calendar.SECOND, second);

            // convert time into millisecond
            long intendedTime = firingCal.getTimeInMillis();
            long currentTime = currentCal.getTimeInMillis();

            // check that current time is greater then end time
            if (currentTime >= intendedTime)
            {
                // pass todays_birthday_accounts into procesExtraData
                processExtraData(today_birthday_accounts);

                // set alarm for set day or add day into firingCal to set alarm for next day
                firingCal.add(Calendar.DAY_OF_MONTH, r.getInteger(R.integer.alarm_add_nextDay));
            }
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    /*************************************************************************
     Name :- callAddcartPage_HomePage
     Output:- create menuitem on action bar
     Description :- Call another activity though intent.
     *************************************************************************/
    public  void callAddcartPage_HomePage(MenuItem item)
    {
        // create intent object to call another activity
        Intent calladdCartPage = new Intent(this,Order_Details.class);
        // start activity
        startActivity(calladdCartPage);
    }


    /******************************************************************************************
     Name :- getAllProductList
     Description :- Get all products data from database.
     ******************************************************************************************/
    public void getAllProductList() {

        // set progress bar
        final ProgressDialog dataLoading_progressBar =  new ProgressDialog(Home_Page.this);
        Utility.show_progress_bar(dataLoading_progressBar, "Loading ....");

        // Retrieve product records
        String URL = "content://app.admin.pressfit.provider/Product";

        Uri product_Uri = Uri.parse(URL);
        Cursor c = getContentResolver().query(product_Uri, null, null, null, null);

        if(c.moveToFirst()) {

            do {
                Product product =  new Product();

                product.setID(c.getString(c.getColumnIndex(GlobalProvider.Product_ID)));

                product.setProduct_code(c.getString(c.getColumnIndex(GlobalProvider.Product_Product_code)));

                product.setName(c.getString(c.getColumnIndex(GlobalProvider.Product_Name)));

                product.setRate(c.getString(c.getColumnIndex(GlobalProvider.Product_Rate)));

                product.setRate_Quantity(c.getString(c.getColumnIndex(GlobalProvider.Product_Rate_Quantity)));

                product.setSTD_PKG(c.getString(c.getColumnIndex(GlobalProvider.Product_STD_PKG)));

                product.setSTD_PKG_Unit(c.getString(c.getColumnIndex(GlobalProvider.Product_STD_PKG_Unit)));

                product.setMaster_PKG(c.getString(c.getColumnIndex(GlobalProvider.Product_Master_PKG)));

                product.setMaster_PKG_Unit(c.getString(c.getColumnIndex(GlobalProvider.Product_Master_PKG_Unit)));

                product.setBasic_Unit(c.getString(c.getColumnIndex(GlobalProvider.Product_Basic_Unit)));

                String key = product.getName();
                g.getProductsHashMap().put(key, product);

            }while (c.moveToNext());
        }

        Toast.makeText(this, "NBR of Products are : " + g.getProductsHashMap().size(), Toast.LENGTH_LONG).show();
        dataLoading_progressBar.dismiss();
        g.setProduct_load_complete_flag(true);
    }
}




