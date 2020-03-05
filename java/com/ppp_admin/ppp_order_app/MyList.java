package com.ppp_admin.ppp_order_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyList extends AppCompatActivity  {

    my_list_custom_adapter custom_adapter_myList;
    ArrayList<String> myListName_array;
    String AgentID;
    ListView my_list_view;
    Globals g;

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
        setContentView(R.layout.activity_my_list);
        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        AgentID = loginSharedPref.getString("ID", "");
        // define array list object
        myListName_array = new ArrayList<String>();
        // call function of local List DB
        getListsDataFromLocalDB(AgentID);
        // define ListView object
        my_list_view = (ListView) findViewById(R.id.my_list_listview);
        // add data into custom adapter
        g = (Globals) getApplication();
        custom_adapter_myList = new my_list_custom_adapter(this,myListName_array,g);
        my_list_view.setAdapter(custom_adapter_myList);

    }

    /********************************************************************************************
     Name :- add_my_list
     Input :- Pass MenuItem object to set onClick on action bar icon
     Description :- add list name into livstview
     *********************************************************************************************/
    public void add_my_list(MenuItem i) {

        // define alert box object
       final AlertDialog.Builder add_my_list_builder = new AlertDialog.Builder(this);
        // set message
        add_my_list_builder.setMessage("Enter list name");
        // define edittext object
        final EditText enter_list_name = new EditText(MyList.this);
        // set edittext in view of alert box
        add_my_list_builder.setView(enter_list_name);
        // set hint in editext
        enter_list_name.setHint("list name");

        // set negative button to cancel alert box
        add_my_list_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dismiss dialog
                dialog.dismiss();
            }
        });

        // set positive button to get entered value from user
        add_my_list_builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // define string object to get value from edittext
                String entered_list_name = enter_list_name.getText().toString().trim();
                String trim_value = entered_list_name;
                if(!entered_list_name.equals("")) {
                    // add all values into constructor
                    Lists final_list = new Lists(entered_list_name,"0", AgentID, "0", Get_current_date());
                    // insert into local db of Lists table
                    insert_into_lists_table_local_db(final_list);
                } else {
                    Toast.makeText(MyList.this,
                         "List Name cannot be empty. Please try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
        add_my_list_builder.show();
    }

    /********************************************************************************************
     Name :-  insert_into_lists_table_local_db
     Input :- Input pass lists table object
     Description :- set values into ContentValues object and insert value into local DB.
     ********************************************************************************************/
    public void insert_into_lists_table_local_db(Lists final_list){

        Log.i("My List","Started.....");
        // create contentvalues object
        ContentValues copyListValuesTOLocalDB = new ContentValues();

        // set values into contentValues object
        copyListValuesTOLocalDB.put(GlobalProvider.List_ID, final_list.getID());
        copyListValuesTOLocalDB.put(GlobalProvider.List_Name, final_list.getList_Name());
        copyListValuesTOLocalDB.put(GlobalProvider.List_Login_User_ID, final_list.getLogin_User_Id());
        copyListValuesTOLocalDB.put(GlobalProvider.List_No_of_items, final_list.getNo_Of_Items());
        copyListValuesTOLocalDB.put(GlobalProvider.List_No_of_time_ordered, final_list.getNo_Of_Times_Ordered());
        copyListValuesTOLocalDB.put(GlobalProvider.List_Date_time_created, final_list.getDate_Created());

        Log.i("MyList", "All value set on content values object");
        // insert into local DB
        try{
            int list_max_incr_count = 0;
            boolean check_count_flag = check_count_list(final_list.getList_Name(), final_list.getLogin_User_Id());
            if(!check_count_flag){
                list_max_incr_count = max_no_of_list_increment(final_list.getLogin_User_Id());
                copyListValuesTOLocalDB.put(GlobalProvider.List_increment,list_max_incr_count);
                Uri uri = getContentResolver().insert(GlobalProvider.CONTENT_URI_List, copyListValuesTOLocalDB);
                // add list name into array list
                myListName_array.add(final_list.getList_Name());
            } else {
                Toast.makeText(this, "This List Name already exists. ", Toast.LENGTH_LONG).show();
            }
            Log.i("My List", "Added data successfully in local DB");
        } catch(Exception e){
            Log.i("My List", "Error in adding da in local DB");
            e.printStackTrace();
        }
    }

    /*********************************************************************************************
        Name :- check_count_list
        Input :- String,String
        Output :- return int
        Description :- getting no of counts from local database.
    **********************************************************************************************/
    public boolean check_count_list(String list_name, String login_user_id) {

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/Lists";

        // parse url
        Uri List_uri = Uri.parse(URL);
        int count = 0;
        /* firing query to get list values in cursor object*/
        Cursor count_ln_cursor =  getContentResolver().query(List_uri, null,
                GlobalProvider.List_Name + " = ?  AND " + GlobalProvider.List_Login_User_ID +
                        " = ? AND " + GlobalProvider.List_deleted + " = 0 ",
                new String[]{list_name, login_user_id}, null);
        return (count_ln_cursor.moveToFirst());
    }

    /********************************************************************************************
        Name :- max_no_of_list_increment
        Input :- pass String
        Output :- return int
        Description :- return max no of count
    *********************************************************************************************/
    public int max_no_of_list_increment(String login_user_id){

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/Lists";
        // parse url
        Uri List_uri = Uri.parse(URL);

        Cursor list_incr_cursor = getContentResolver().query(List_uri,
                new String[]{" Max ( " + GlobalProvider.List_increment + ") as MAX_LIST_INCR"},
                GlobalProvider.List_Login_User_ID + " = ?", new String[]{login_user_id}, null);

        list_incr_cursor.moveToFirst();
        int max_incr_count;
        String max_list_incr = list_incr_cursor.getString(list_incr_cursor.getColumnIndex("MAX_LIST_INCR"));
        if(null != max_list_incr){
            max_incr_count = Integer.parseInt(max_list_incr);
        }else{
            max_incr_count = 0;
        }
        return (max_incr_count + 1);
    }

    /********************************************************************************************
     Name :- getDataFromLocalListDB
     Input :- pass agent id as string
     Description :- get data from list table of local db add into array list object
     *********************************************************************************************/
    public void getListsDataFromLocalDB(String agentID){

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/Lists";
        // parse url
        Uri List_uri = Uri.parse(URL);

        // query to get data from local db of Lists table
        Cursor MyList_cursor = getContentResolver().query(List_uri, null,
                GlobalProvider.List_Login_User_ID + " = ?  AND " + GlobalProvider.List_deleted +
                        " = ?", new String[]{agentID, "0"}, null);

        /* cursor will move to first object */
        if(MyList_cursor.moveToFirst()){

            do{
                // set values in Lists table object
                Lists show_list = setValuesOfList_LocalDB(MyList_cursor);
                // names added into array list having in to local DB
                myListName_array.add(show_list.getList_Name());
            } while(MyList_cursor.moveToNext());
        }
    }

    /*********************************************************************************************
     Name :- setValuesOfList_LocalDB
     Input :- pass cursor object
     Description :- set current values into Lists table object from local DB
     **********************************************************************************************/
    public Lists setValuesOfList_LocalDB(Cursor myList_cursor){

        // create Lists object
        Lists set_List_value = new Lists();

        // set all values from cursor object
        set_List_value.setID(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_ID)));
        set_List_value.setList_Name(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_Name)));
        set_List_value.setList_Increment(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_increment)));
        set_List_value.setLogin_User_Id(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_Login_User_ID)));
        set_List_value.setNo_Of_Items(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_No_of_items)));
        set_List_value.setNo_Of_Times_Ordered(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_No_of_time_ordered)));
        set_List_value.setDate_Created(myList_cursor.getString(myList_cursor.
                getColumnIndex(GlobalProvider.List_Date_time_created)));

        // return Lists object
        return set_List_value;
    }

    /***********************************************************************
     Name :- Get_current_date
     Output:- returns today's date in string format
     Description :- converting today;s date into 'yyyy-mm-dd' format
     ***********************************************************************/
    public String Get_current_date() {

        // set date format
        SimpleDateFormat convert_default_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // convert date format into string
        String today_date = convert_default_format.format(new Date());
        // return current date
        return today_date;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mylist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //   return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*********************************************************************************************
     Name :- SyncAccounts
     Input :- Pass MenuItem object
     Description :-  While clicking on sync icon all data will be retrieved from
     ********************************************************************************************/
    public void SyncList_Items(MenuItem item) {

        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String ID = loginSharedPref.getString("ID","");

        // Create the account type and default account
        mAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        Log.i("List Items :", "Sync Adapter LI was created...");
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

        /* Request the sync for the default account, authority, and manual sync settings */
        ContentResolver.requestSync(mAccount, AUTHORITY, AccountBundle);
        Log.i("List Items :", "Sync Adapter called request sync");

        Toast.makeText(this,"Please again open List again",Toast.LENGTH_LONG).show();
    }


}