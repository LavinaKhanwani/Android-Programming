package com.ppp_admin.ppp_order_app;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class my_list_details extends AppCompatActivity {

    ArrayList<String> arrayList_product_ids = new ArrayList<>();
    ArrayList<String> arrayList_prod_name = new ArrayList<>();
    ListView listView_my_list_details;
    my_list_details_custom_adapter custom_adapter;
    boolean edit_flag = false;
    Globals g;
    String list_name = new String();

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
        setContentView(R.layout.activity_my_list_details);

        // get string of list names from my_list_adapter class through intent
        list_name = getIntent().getStringExtra("list_name");
        // change action bar title according to list name clicked by user
        getSupportActionBar().setTitle(list_name);
        g = (Globals) getApplication();
        get_product_list_items_from_local_db();
        listView_my_list_details = (ListView) findViewById(R.id.listView_my_list_details);
        custom_adapter = new my_list_details_custom_adapter(this,arrayList_prod_name,g,edit_flag,list_name);
        listView_my_list_details.setAdapter(custom_adapter);
    }

    /*******************************************************************************************
     Name :- get_product_list_items_from_local_db
     Description :- get list item values from local db.
     *********************************************************************************************/
    public void get_product_list_items_from_local_db(){

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        String AgentID = loginSharedPref.getString("ID", "");
        // set url to query for List Items table in local DB
        String URL = "content://app.admin.pressfit.provider/List_Items";
        // parse url
        Uri list_Item_uri = Uri.parse(URL);
        // query to get data from local db of List Item table
        Cursor MyList_cursor = getContentResolver().query(list_Item_uri, null,
                GlobalProvider.LI_Login_User_Id + " = ?  And " +
                GlobalProvider.LI_List_Name + " = ? And " + GlobalProvider.LI_deleted + " = ?",
                new String[]{AgentID, list_name,"0"}, null);

        /* cursor will move to first object */
        if(MyList_cursor.moveToFirst()){
            do {
                // set values in Lists table object
                List_Items show_list_item = setValuesOfListItem_LocalDB(MyList_cursor);
                // names added into array list having in to local DB
                arrayList_product_ids.add(show_list_item.getProduct_Id());
            } while(MyList_cursor.moveToNext());
        }
        getProductsName();
    }

    /********************************************************************************************
     Name :- setValuesOfListItem_LocalDB
     Input :- pass cursor object
     Output :- return List item object
     Description :- set values in list item object
     *********************************************************************************************/
    public List_Items setValuesOfListItem_LocalDB(Cursor myList_cursor){

        List_Items set_values_li = new List_Items();
        set_values_li.setList_Name(myList_cursor.getString
                (myList_cursor.getColumnIndex(GlobalProvider.LI_List_Name)));
        set_values_li.setList_Increment(myList_cursor.getString
                (myList_cursor.getColumnIndex(GlobalProvider.List_increment)));
        set_values_li.setLogin_User_id(myList_cursor.getString
                (myList_cursor.getColumnIndex(GlobalProvider.LI_Login_User_Id)));
        set_values_li.setProduct_Id(myList_cursor.getString
                (myList_cursor.getColumnIndex(GlobalProvider.LI_Product_ID)));

        return set_values_li;

    }

    /*********************************************************************************************
     Name :- getProductsName
     Description :- creating arraylist of name according to products ids
     ***********************************************************************************************/
    public void getProductsName() {

        String URL = "content://app.admin.pressfit.provider/Product";
        Uri prod_uri = Uri.parse(URL);

        /* loop on products ids */
        for (String curr_prods_id : arrayList_product_ids){

            // query to get data from local db of List Item table
            Cursor prod_cursor = getContentResolver().query(prod_uri, new String[] {GlobalProvider.Product_Name},
                    GlobalProvider.Product_ID  + " IN (?) " , new String[]{curr_prods_id}, null);

            if(prod_cursor.moveToFirst()){
                do{
                    // get column of list_name of list table from local db
                    String names = prod_cursor.getString(prod_cursor.getColumnIndex(GlobalProvider.Product_Name));
                    // added names according to products id into arraylist
                    arrayList_prod_name.add(names);
                } while (prod_cursor.moveToNext());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_list_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case R.id.edit_item :
                edit_flag = !edit_flag;
                invalidateOptionsMenu();
                custom_adapter = new my_list_details_custom_adapter(this, arrayList_prod_name, g,edit_flag,list_name);
                listView_my_list_details.setAdapter(custom_adapter);
                break;
            case R.id.add_products_item :
                Intent call_products_page = new Intent(this, Products_Page.class);
                startActivity(call_products_page);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem add = menu.findItem(R.id.add_products_item);
        MenuItem edit_done = menu.findItem(R.id.edit_item);
        if (edit_flag) {
            edit_done.setIcon(R.drawable.done_icon);
            add.setVisible(false);
        } else{
            edit_done.setIcon(R.drawable.editlogo);
            add.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
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
        AccountBundle.putString("URI", "ListTable");
        AccountBundle.putString("AgentID", ID);

        /*
         * Request the sync for the default account, authority, and manual sync settings
         */
        ContentResolver.requestSync(mAccount, AUTHORITY, AccountBundle);
        Log.i("Account_Details :", "Sync Adapter called request sync");

        Toast.makeText(this,"Please again open Accounts List",Toast.LENGTH_LONG).show();
    }

}
