package com.ppp_admin.ppp_order_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Account_Details extends Activity {

    /*******************************************************************************************
     * Section for declaration of the Variables
     *******************************************************************************************/
    Globals g;
    ListView showAccountDetails_listView;
    ArrayAdapter showAccountDetails_adapter;
    /********************************************************************************************
        Name :- HashMap<String ,Account_Master>
        Key :- Account ID
        Value :- Account_Master object showDetailsAccount
        Description :- add all accounts related to current id
    *********************************************************************************************/
    HashMap<String ,Account_Master> showAccountDetails_hashMap = new HashMap<>();
    boolean reviewFlag;
    String search_str = new String();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__details);

        /*****************************************************************************************/
        g = (Globals)getApplication();
        // getting boolean value through intent object
        reviewFlag = getIntent().getExtras().getBoolean("review_flag");
        // function is used to change action bar fro different activity
        invalidateOptionsMenu();
        // creating listview through by given id in xml file
        showAccountDetails_listView = (ListView) findViewById(R.id.showAccountDetails_listView);
        showAccountDetails_adapter = new ArrayAdapter(this,R.layout.showaccountdetails_textbox,R.id.showAccountDetails_textView);
        ShowAccountDetails_addAccountMaster();
        clickable_listItem();
    }

    /*********************************************************************************************
        Name :- ShowAccountDetails_addAccountMaster
        Description :- Show all data in listview to user on Account Details page.
    *********************************************************************************************/
    public void ShowAccountDetails_addAccountMaster() {

        // Display progress bar until data loads
        final ProgressDialog AccountDetails_progressBar = new ProgressDialog(Account_Details.this);

        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String AgentID = loginSharedPref.getString("ID", "");

        String URL = "content://app.admin.pressfit.provider/AccountMaster";

        Uri AccountDetails_uri =Uri.parse(URL);

        //Account_Master showDetailsAccount = null;
        Cursor  AD_cursor  = getContentResolver().query(AccountDetails_uri,null,
                "AgentID = ? ",new String[] {AgentID},"AccountName ASC");

        if(AD_cursor.moveToFirst()) {

            Utility.show_progress_bar(AccountDetails_progressBar, "Data Loading...");
            do{
                // get values from function
                Account_Master showDetailsAccount = set_details_of_account_master (AD_cursor);

                /* getting current User and putting into hashMap for later use and display detail */
                showAccountDetails_hashMap.put(showDetailsAccount.getID(), showDetailsAccount);

                /* set listview adapter to add data in data into listview and
                   while fetching data from database it will convert all data into
                   uppercase and shown by user.*/
                showAccountDetails_adapter .add(showDetailsAccount.getID() + " :- "
                        + showDetailsAccount.getAccountName().toString().toUpperCase() + " "
                        + showDetailsAccount.getCity().toString().toUpperCase());

            } while (AD_cursor.moveToNext());
        }
        else {
            /* show message to user when list view doesn't contains any data  */
            TextView showNullData = (TextView) findViewById(R.id.showNullValue_textView);
            showNullData.setVisibility(View.VISIBLE);
        }
        AccountDetails_progressBar.dismiss();
        showAccountDetails_listView.setAdapter(showAccountDetails_adapter);
    }

    /*********************************************************************************************
        Name :- clickable_listItem
        Description :- set click on every item in listview , on Click of every item
                       it will show all details to next page as Display_selected_customer_details.
     *********************************************************************************************/
      public  void clickable_listItem() {

        // set OnItemClickListener to every item in list view
        showAccountDetails_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // getting current Item of current position into String variable as item
                String item = (String) parent.getItemAtPosition(position);

                // split value frm item and storing into string array variable as value
                String[] value = item.split(":-");

                // replacing space from array of 0th value
                value[0] = value[0].replace(" ", "");

                // getting value from hashMap and storing into Account_Master object as current_account
                Account_Master current_account = showAccountDetails_hashMap.get(value[0]);

                // creating Intent to call context to another page
                Intent selectedData = null;
                if (!reviewFlag) {
                    // call next activity through intent object
                    selectedData = new Intent(Account_Details.this, Dispaly_selected_cutomer_details.class);
                } else {
                    // call next activity through intent object
                    selectedData = new Intent(Account_Details.this, Review_page.class);
                }
                // put data from current_account to new variable as currentData.
                selectedData.putExtra("currentData", current_account);
                selectedData.putExtra("reviewFlag", false);
                // This start activity by calling intent object.
                startActivity(selectedData);
            }
        });
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_account__details, menu);

        /* search manager is used to search products on search page  */
        SearchManager searchManager = (SearchManager) getSystemService(Account_Details.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_addAccount).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String search_str) {

                // this is your adapter that will be filtered
                showAccountDetails_adapter.getFilter().filter(search_str);
                add_search_accounts_in_list(search_str);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {

                // this is your adapter that will be filtered
                showAccountDetails_adapter.getFilter().filter(query);
               // System.out.println("on query submit: "+query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);
    }

    /*********************************************************************************************
        Name :- add_search_accounts_in_list
        Input :- Pass string entered by user
        Description :- if user lost application aging user will login in app by clicking on
                       refresh icon all accounts will be display in listview
    ******************************************************************************************/
    public void add_search_accounts_in_list(String search_str){

        ArrayList<Account_Master> ac_array= new ArrayList<Account_Master>();
        Iterator<Map.Entry<String,Account_Master>> iterator;
        iterator = showAccountDetails_hashMap.entrySet().iterator();

        if(search_str.equals("")){

            showAccountDetails_listView.setAdapter(showAccountDetails_adapter);
        }else {
            while (iterator.hasNext()) {

                Map.Entry<String, Account_Master> current = iterator.next();
                Account_Master ac = current.getValue();

                if (search_str.contains(ac.getAccountName())) {
                    ac_array.add(ac);
                }
                for (Account_Master ad : ac_array) {

                    showAccountDetails_adapter.add(ad.getID() + " :- " +
                            ad.getAccountName() + " :- " + ad.getCity());
                }
            }
            showAccountDetails_listView.setAdapter(showAccountDetails_adapter);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        MenuItem addItem = menu.findItem(R.id.AddAccount_Details_addAccount);
        /* Condition will check value of reviewFlag accordingly it will perform task*/
        if(reviewFlag){
            addItem.setVisible(false);
        }else{
            addItem.setVisible(true);
        }
        /* --> Icon of Sync will help to retrieve data aging if user uninstall app
           --> if user installing app again then while clicking on sync icon, user see all
               the accounts details again.*/
        MenuItem syncAccountItem = menu.findItem(R.id.SyncAccount_Details);
                /* Condition will check value of reviewFlag accordingly it will perform task*/
                if(reviewFlag) {
                    syncAccountItem.setVisible(false);
                }else {
                    syncAccountItem.setVisible(true);
                }
        return super.onPrepareOptionsMenu(menu);
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

    /****************************************************************************************
        Name :- AddAccountDetailsCall
        Input :- create an menu bar on action bar by passing parameter as MenuItem
        Description :- Function call another page to add new data or party details
                         and passing current user AgentID also.
     ****************************************************************************************/
     public void AddAccountDetailsCall(MenuItem item) {

        // create Intent object to call context to another page.
        Intent updatePageIntent = new Intent(this,addAccount_Customer.class);

        // This start activity by calling intent object.
        startActivity(updatePageIntent);
    }

    /*********************************************************************************************
        Name :- SyncAccounts
        Input :- Pass MenuItem object
        Description :-  While clicking on sync icon all data will be retrieved from
     ********************************************************************************************/
    public void SyncAccounts(MenuItem item) {

        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String ID = loginSharedPref.getString("ID","");

        // Create the account type and default account
        mAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        Log.i("Account_Details :", "Sync Adapter Account was created...");
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) this.getSystemService(ACCOUNT_SERVICE);

        // Add the account and account type, no password or user data
        accountManager.addAccountExplicitly(mAccount, null, null);

        // Pass the settings flags by inserting them in a bundle
        Bundle AccountBundle = new Bundle();
        AccountBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        AccountBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        AccountBundle.putString("URI", "AccountMaster");
        AccountBundle.putString("AgentID", ID);

        /*
         * Request the sync for the default account, authority, and manual sync settings
         */
        ContentResolver.requestSync(mAccount, AUTHORITY, AccountBundle);
        Log.i("Account_Details :", "Sync Adapter called request sync");

        Toast.makeText(this,"Please again open Accounts List",Toast.LENGTH_LONG).show();
    }

    /***********************************************************************************************
        Name :- set_details_of_account_master
        Input :- Pass Cursor object
        Output :- Return Account_Master object
        Description :- Set accounts details into Accounts_Master object to store into local database.
     ***********************************************************************************************/
    public Account_Master set_details_of_account_master(Cursor AD_cursor){

        // Initialize Account_Master object
        Account_Master showDetailsAccount = new Account_Master();

        // set all details into the object
        showDetailsAccount.setID(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_ID)));
        showDetailsAccount.setAccountName(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_AccountName)));
        showDetailsAccount.setNameOfOwner(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_NameOfOwner)));
        showDetailsAccount.setAddress1(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address1)));
        showDetailsAccount.setAddress2(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address2)));
        showDetailsAccount.setAddress3(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address3)));
        showDetailsAccount.setCity(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_City)));
        showDetailsAccount.setState(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_State)));
        showDetailsAccount.setPinCode(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_PinCode)));
        showDetailsAccount.setFirmAnniversaryDate(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_FirmAnniversaryDate)));
        showDetailsAccount.setContactPerson(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPerson)));
        showDetailsAccount.setContactPersonMobileNo(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPersonMobileNo)));
        showDetailsAccount.setOwnerMobileNo(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_OwnerMobileNo)));
        showDetailsAccount.setLandlineNo(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_LandlineNo)));
        showDetailsAccount.setBirthDate(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_BirthDate)));
        showDetailsAccount.setAnniversaryDate(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_AnniversaryDate)));
        showDetailsAccount.setEmailID(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_EmailID)));
        showDetailsAccount.setFaxNo(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_FaxNo)));
        showDetailsAccount.setWebsite(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Website)));
        showDetailsAccount.setPanNo(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_PanNo)));
        showDetailsAccount.setGST((AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_GST))));
        showDetailsAccount.setRemarks(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Remarks)));
        showDetailsAccount.setDiscount_Rate_Percent(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Discount_Rate_Percent)));
        showDetailsAccount.setTransport(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_Transport)));
        showDetailsAccount.setAgentID(AD_cursor.getString(AD_cursor.getColumnIndex(GlobalProvider.AccountMaster_AgentID)));

        return showDetailsAccount;
    }
}
