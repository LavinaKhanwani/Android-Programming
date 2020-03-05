package com.ppp_admin.ppp_order_app;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.*;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Product_Details_Page_Type_4 extends AppCompatActivity {

    int count = 0;
    HashMap<Integer,ArrayList<Integer>> to_refer_each_item = new HashMap<Integer,ArrayList<Integer>>();
    Globals g;
    ArrayList<String> product_ids_for_list =  new ArrayList<String>();
    boolean addToCart_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details__page__type_4);
        int product_position = getIntent().getExtras().getInt("position");

        changeActionBarTitle(product_position);
        g = (Globals) getApplication();

        addDataToTable();
        cancel_process();
    }

    /**********************************************************************************************
        Name :- changeActionBarTitle
        Input:- Pass int variable as position_product
        Description :- Change the action bar title according to product page.
     *********************************************************************************************/
    public  void changeActionBarTitle(int position_product) {

        Resources r = getResources();
        if (position_product == r.getInteger(R.integer.WIRE_CLIP_POSITION) ) {
            getSupportActionBar().setTitle("WIRE CLIPS");
        } if (position_product == r.getInteger(R.integer.CONCEAL_BOX_POSITION) ) {
            getSupportActionBar().setTitle("CONCEAL BOARDS");
        } if (position_product == r.getInteger(R.integer.MCB_BOXES_POSITION) ) {
            getSupportActionBar().setTitle("MCBS");
        } if(position_product == r.getInteger(R.integer.CASING_CAPING_ACCESSORIES_POSITION)){
            getSupportActionBar().setTitle("CASING ACCESSORIES");
        } if(position_product == r.getInteger(R.integer.MULTICORE_CABLE_POSITION)){
            getSupportActionBar().setTitle("MULTICORE CABLE");
        } if(position_product == r.getInteger(R.integer.EXHAUST_FAN_POSITION)){
            getSupportActionBar().setTitle("EXHAUST FANS");
        } if(position_product == r.getInteger(R.integer.CASING_POSITION)){
            getSupportActionBar().setTitle("CASING CAPING");
        } if(position_product == r.getInteger(R.integer.GANG_BOX_POSITION)){
            getSupportActionBar().setTitle("GANG BOXES");
        }
    }
    /**********************************************************************************************
        Name:- addDataToTable
        Description :- This function makes a table row with two columns - i) Product Image and Name
                        ii) Types of the product.
        Types of product is the form of list of check boxes. Then adds this table row to the table.
     *********************************************************************************************/
     private void addDataToTable() {

        /* Get the main table from the xml */
        TableLayout mainTable = (TableLayout) findViewById(R.id.mainTable);

         /* Retrieve which products need to be displayed */
         int product_type = getIntent().getExtras().getInt("product_type");
         String[] product_types_array = getResources().getStringArray(product_type);

         /* Retrieve product images for the above list of products */
         int[] product_images =   getIntent().getExtras().getIntArray("product_images");

         /* Loop through to add each product details in the table */
         for (int i = 0; i < product_types_array.length; i++) {

             TableRow tableRow = new TableRow(this);
             TableRow.LayoutParams tableRowParams =
                     new TableRow.LayoutParams
                             (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
             tableRow.setLayoutParams(tableRowParams);
             tableRow.setPadding(20, 20, 20, 20);

             // Initialize Linearlayout to set product name and images
             LinearLayout product_desc = new LinearLayout(this);
             product_desc.setId(R.id.product_linearlayout);
             product_desc.setOrientation(LinearLayout.VERTICAL);
             product_desc.setGravity(Gravity.START);
             product_desc.setPadding(10,0,10,0);

             // initialize string to separate product type
             String[] pn_str = product_types_array[i].split(";");
             // initialize string to get product name
             String prod_name_str = pn_str[0];
             // initialize string to get product color
             String prod_color = pn_str[1];

            /* Product Name Details */
             TextView textView_PN = new TextView(this);
             textView_PN.setText(prod_name_str);
             textView_PN.setWidth(350);
             textView_PN.setHeight(50);
             textView_PN.setId(R.id.product_name);
             textView_PN.setGravity(Gravity.CENTER_HORIZONTAL);
             textView_PN.setTypeface(null, Typeface.BOLD);
             textView_PN.setTextSize(13);
             textView_PN.setPadding(0, 0, 0, 0);
             product_desc.addView(textView_PN);

              /* Product Color */
             TextView textView_PC = new TextView(this);
             textView_PC.setText("Color : " + prod_color);
             textView_PC.setId(R.id.product_color);
             textView_PC.setGravity(Gravity.CENTER_HORIZONTAL);
             textView_PC.setTypeface(null, Typeface.BOLD);
             textView_PC.setTextSize(13);
             textView_PC.setPadding(10, 10, 10, 10);
             product_desc.addView(textView_PC);

            /* Product Image Details */
             ImageView imageView = new ImageView(this);
             imageView.setImageResource(product_images[i]);
             imageView.setId(R.id.product_image);
             imageView.setPadding(20, 20, 20, 20);
             product_desc.addView(imageView);

             // add row in table
             tableRow.addView(product_desc, new TableRow.LayoutParams
                     (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

             /* LinearLayout for checkboxes */
             LinearLayout product_types = new LinearLayout(this);
             product_types.setOrientation(LinearLayout.VERTICAL);
             product_types.setGravity(Gravity.CENTER_VERTICAL);

             int box_type_value = decide_type(prod_name_str);

             /* show all products item with checkbox */
             if( box_type_value != -1){

                 String[] product_modules_array = getResources().getStringArray(box_type_value);

                 /* Loop for printing multiple checkboxes */
                 for (int j = 0; j < product_modules_array.length; j++) {

                     CheckBox checkBox = new CheckBox(this);
                     checkBox.setTextSize(13);
                     checkBox.setText(product_modules_array[j]);
                     checkBox.setChecked(false);
                     checkBox.setId(count);

                     /* check condition if particular item name belongs to product hashmap */
                     if (to_refer_each_item.containsKey(i)) {
                         (to_refer_each_item.get(i)).add(count);
                     } else {
                         ArrayList<Integer> arrayList = new ArrayList<Integer>();
                         arrayList.add(count);
                         to_refer_each_item.put(i, arrayList);
                     }
                     count++;
                     product_types.addView(checkBox);
                 }
             }
             tableRow.addView(product_types, new TableRow.LayoutParams
                     (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f));
             mainTable.addView(tableRow);
         }
     }

    /*********************************************************************************************
        Name :- decide_type
        Input :- Pass String variable as prod_name_str
        Description :- Display all products items according to product name.
     ********************************************************************************************/
    private int decide_type(String prod_name_str) {

     // initialize int type variable
     int type_array = -1;

     /* check condition to display product items */
     if(prod_name_str.equals("ONE MODULAR CONCEALED METAL BOX")
             || prod_name_str.equals("ONE MODULAR CONCEALED BOX")
             || prod_name_str.equals("ONE MODULAR SURFACE BOX")){
         type_array = R.array.Surface_Boxes_Modules;
     } else if(prod_name_str.equals("DIYA CONCEAL BOX")){
         type_array = R.array.Diya_Conceal_Box_Type;
     } else if(prod_name_str.equals("CHARM CONCEALED BOARD")){
         type_array = R.array.Charm_board_array;
     }else if(prod_name_str.equals("JUNCTION BOX")) {
         type_array = R.array.Junction_box;
     } else if(prod_name_str.equals("Wire Clip")){
         type_array = R.array.wire_clip_sizes;
     } else if(prod_name_str.equals("FLAT CASING CLIP") || prod_name_str.equals("ROUND CASING CLIP")){
         type_array = R.array.casing_clip_size;
     } else if(prod_name_str.equals("GOLD DBS")){
         type_array = R.array.gold_nano_dbs_modules;
     } else if(prod_name_str.equals("GOLD METAL ENCLOSURE")){
         type_array = R.array.gold_metal_enclosure_modules;
     } else if(prod_name_str.equals("NANO DBS")){
         type_array = R.array.gold_nano_dbs_modules;
     } else if(prod_name_str.equals("AC BOX")){
         type_array = R.array.ac_box_types;
     } else if(prod_name_str.equals("DBS")){
         type_array = R.array.dbs_board;
     } else if(prod_name_str.equals("DIYA DBS")){
         type_array = R.array.diya_dbs_modules;
     } else if(prod_name_str.equals("DIYA PLUS DBS(METAL BACK)")){
         type_array = R.array.diya_dbs_modules;
     } else if(prod_name_str.equals("MCB BOX METAL BLACK")){
         type_array = R.array.mcb_box;
     } else if(prod_name_str.equals("MCB BOX PLASTIC BLACK")){
         type_array = R.array.mcb_box;
     } else if(prod_name_str.equals("LX SURFACE BOX")){
         type_array = R.array.LX_SURFACE_BOX_POSITION;
     } else if(prod_name_str.equals("LX MODULAR GANG BOX")){
         type_array = R.array.LX_GANG_BOX_POSITION;
     } else if(prod_name_str.equals("50X20(2 INCHES) SUITABLE FOR ALL CASING")){
         type_array = R.array.casing_Accessories_type_1;
     } else  if(prod_name_str.equals("38X20(1.5 INCHES) SUITABLE FOR ALL CASING")){
         type_array = R.array.Casing_Accessories_type_2;
     } else if(prod_name_str.equals("20MM(0.5 INCHES)")){
         type_array = R.array.Casing_Accessories_type_3;
     } else if(prod_name_str.equals("20MM(20X12 BOX TYPE) SUITABLE FOR ALL BOX TYPE CASING(0.5 INCHES)")) {
         type_array = R.array.Casing_Accessories_type_4;
     } else if(prod_name_str.equals("22MM(SUITABLE FOR 22MM AND 25X12 CASING 3.4 INCHES)")){
         type_array = R.array.Casing_Accessories_type_5;
     } else if(prod_name_str.equals("25MM(SUITABLE FOR 25MM AND 32X12 CASING 1 INCHES)")){
         type_array = R.array.Casing_Accessories_type_6;
     } else if(prod_name_str.equals("30X15(SUITABLE FOR 30X15 CASING 1 INCHES)")){
         type_array = R.array.Casing_Accessories_type_7;
     } else if(prod_name_str.equals("25X16(3.4 INCHES)")){
         type_array = R.array.Casing_Accessories_type_8;
     } else if(prod_name_str.equals("SQUARE BOX")) {
         type_array = R.array.casing_accessories_square_box_badshah;
     } else if(prod_name_str.equals("JUNCTION BOX")){
         type_array = R.array.Casing_Accessories_Junction_type_9;
     } else if(prod_name_str.equals("30X15 J.BOX")){
         type_array = R.array.Casing_Accessories_Junction_type_10;
     } else if(prod_name_str.equals("ROUND BLOCK")){
         type_array = R.array.Casing_Accessories_ROUND_BLOCK;
     } else if(prod_name_str.equals("DIYA JUNCTION BOX")){
         type_array = R.array.Casing_Accessories_Junction_type_11;
     } else if(prod_name_str.equals("DOCTOR")){
         type_array = R.array.DOCTOR;
     } else if(prod_name_str.equals("REDUCER")){
         type_array = R.array.Casing_Accessories_REDUCER;
     } else if(prod_name_str.equals("TEJAS SQUARE BOX")){
         type_array = R.array.casing_Accessories_Tejas_Square;
     } else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 2 CORE") ){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     } else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 3 CORE")){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     }else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 4 CORE")){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     }  else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 2 CORE") ){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     } else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 3 CORE")){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     }else if(prod_name_str.equals("MULTICORE INDUSTRIAL CABLE 4 CORE")){
         type_array = R.array.MULTICORE_CABLE_TYPES;
     } else if(prod_name_str.equals("TELEPHONE PAIR AND SWITCHBOARD CABLES 0.51 MM")){
         type_array = R.array.TELEPHONE_AND_SWITCH_TYPE1;
     } else if(prod_name_str.equals("TELEPHONE PAIR AND SWITCHBOARD CABLES 0.41 MM")){
         type_array = R.array.TELEPHONE_AND_SWITCH_TYPE2;
     } else if(prod_name_str.equals("3 CORE FLAT SUBMERSIBLE CABLES")){
         type_array = R.array.FLAT_3_CORE_TYPES;
     } else if(prod_name_str.equals("COAXIAL CABLES(CCS)")){
         type_array =R.array.COAXIAL_CABLE_TYPES;
     } else if(prod_name_str.equals("CC TV CABLE SPECIAL PARALLEL")){
         type_array = R.array.CC_TV_SPECIAL_TYPE;
     } else if(prod_name_str.equals("CC TV CABLE ROUND")){
         type_array = R.array.CC_TV_ROUND_CABLE_TYPE;
     } else if(prod_name_str.equals("LAN CABLE COMPUTER CABLE")) {
         type_array = R.array.LAN_CABLE_TYPE;
     } else if(prod_name_str.equals("EXHAUST FAN RAPID")){
         type_array = R.array.EXHAUST_FAN_SUB_TYPE_RAPID;
     } else if(prod_name_str.equals("EXHAUST FAN KRETA")){
         type_array = R.array.EXHAUST_FAN_SUB_TYPE_KRETA;
     } else if(prod_name_str.equals("EXHAUST FAN SWIFT")){
         type_array = R.array.EXHAUST_FAN_SUB_TYPE_SWIFT;
     }  else if(prod_name_str.equals("Casing and Caping Tejas")){
         type_array = R.array.casing_tejas;
     } else if(prod_name_str.equals("Casing and Caping Classy")){
         type_array = R.array.casing_calssy;
     } else if(prod_name_str.equals("Casing and Caping Nano")){
         type_array = R.array.casing_nano;
     } else if(prod_name_str.equals("Casing and Caping Diya")){
         type_array = R.array.casing_diya;
     } else if(prod_name_str.equals("Casing and Caping Glory")){
         type_array = R.array.casing_glory;
     } else if(prod_name_str.equals("Casing and Caping Viraj")){
         type_array = R.array.casing_viraj;
     } else if(prod_name_str.equals("Casing and Caping Lords")){
         type_array = R.array.casing_lords;
     } else if(prod_name_str.equals("Casing and Caping Pressfit Box Type")){
         type_array = R.array.casing_boxtype;
     } else if(prod_name_str.equals("Casing and Caping Pressfit Galaxy")){
         type_array = R.array.casing_galaxy;
     } else if(prod_name_str.equals("Casing and Caping Pressfit Gold")){
         type_array = R.array.casing_gold;
     } else  if(prod_name_str.equals("Casing and Caping New")){
         type_array = R.array.casing_trunking;
     }else if(prod_name_str.equals("Casing and Caping Pressfit")){
         type_array = R.array.casing_pressfit_list;
     }  else if(prod_name_str.equals("Casing and Caping Royal")){
         type_array = R.array.casing_royal;
     } else if(prod_name_str.equals("Casing and Caping Lexi")){
         type_array = R.array.casing_lexi;
     } else if(prod_name_str.equals("NANO GANG BOX")){
         type_array = R.array.Nano_gang_box;
     } else if(prod_name_str.equals("NANO PLUS GANG BOX")){
         type_array = R.array.Nano_plus_gang_box;
     } else if(prod_name_str.equals("ECHO GANG BOX")){
         type_array = R.array.echo_gang_box_list;
     } else if(prod_name_str.equals("GOLD GANG BOX")){
         type_array = R.array.gold_gang_box;
     }
        return type_array;
    }

    /**********************************************************************************************
        Name :- addToCart_process
        Description :- on click of addToCart button all items will be added into cart.
     *********************************************************************************************/
     public void addToCart_process(View v) {

         addToCart_flag  = true;
         // create string
        create_final_string_product();
        // function show message
        ToastMessage();
        // clear all data after adding product into cart
        clear_data();
     }

    /**********************************************************************************************
     Name :- add_to_my_list_process
     Description :- on click of add to my list button all items will be added into cart.
     *********************************************************************************************/
    public void add_to_my_list_process(View v) {

        addToCart_flag = false;
        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        final String AgentID = loginSharedPref.getString("ID", "");
        // create string
        create_final_string_product();
         /* get all list names into arraylist object */
        ArrayList<String> listNames = get_list_data_from_local_DB(AgentID);
        // display alert box to choose list name by user
        display_list_names(listNames, AgentID);
        // clear all data after adding product into cart
        clear_data();
    }
    /*********************************************************************************************
     Name :- display_list_names
     Input :- pass arraylist of string name and agentid as string
     Description :- display alert box to choose list name from user, onclick of ok button
     what ever products are selected along with list name all will be combined
     and added into list item table in local db.
     **********************************************************************************************/
    private int selected = -1;
    public void display_list_names(final ArrayList<String> listNames,final String AgentID){

        // define alert box object
        final AlertDialog.Builder select_LN_alertBox =  new AlertDialog.Builder(this);
        // define array adapter of list name getting from local table of lists set into alert box
        final ArrayAdapter<String> arrayAdapter_list_names = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, listNames);

        if(!arrayAdapter_list_names.isEmpty()) {

            // set title of alert box
            select_LN_alertBox.setTitle("Select List");
            // create radio buttons of list in alert box
            select_LN_alertBox.setSingleChoiceItems(arrayAdapter_list_names, selected, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // get current position
                    selected = which;
                }
            });
            // set positive button
            select_LN_alertBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    /* checking product ids are empty or not */
                    if(!product_ids_for_list.isEmpty()) {
                        // get selected list name chosen by user
                        String selected_ln = arrayAdapter_list_names.getItem(selected);
                        // get increment into int variable
                        int list_incr = get_list_incr_from_local_list_db(selected_ln, AgentID);
                        // insert values into local list_items
                        insert_values_in_list_items_local_db(AgentID, selected_ln,list_incr);
                        product_ids_for_list.clear();
                    } else{
                        Toast.makeText(Product_Details_Page_Type_4.this,
                                "Please select some products" ,Toast.LENGTH_LONG).show();
                    }
                }
            });
            // set negative button
            select_LN_alertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {

            select_LN_alertBox.setMessage("No records found");
            select_LN_alertBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        // show alert box
        select_LN_alertBox.show();
    }

    /**********************************************************************************************
     Name :- insert_values_in_list_items_local_db
     Input :- pass agent id as string
     Description :- set values in contentvalues object and added into list item table in local db
     ***********************************************************************************************/
    public void insert_values_in_list_items_local_db(String AgentID, String selected_ln, int list_incr) {

        // non_deleted_arraylist
        ArrayList<String> non_deleted_prod_id = get_non_deleted_product_id(selected_ln, AgentID, list_incr);

        // define contentvalues object
        ContentValues copy_values_int_list_item = new ContentValues();

        // set values of list items
        copy_values_int_list_item.put(GlobalProvider.LI_List_Name, selected_ln);
        copy_values_int_list_item.put(GlobalProvider.LI_Increment,list_incr);
        copy_values_int_list_item.put(GlobalProvider.LI_Login_User_Id,AgentID);
        copy_values_int_list_item.put(GlobalProvider.LI_deleted,"0");

        // loop on array of prod ids selected by user
        for(String curr_prod_ids : product_ids_for_list){

            // set prod ids into contentvalues object
            copy_values_int_list_item.put(GlobalProvider.LI_Product_ID, curr_prod_ids);
            if(!non_deleted_prod_id.contains(curr_prod_ids) || non_deleted_prod_id.isEmpty()) {
                // firing query to insert into local db of list item
                Uri uri = getContentResolver().insert(GlobalProvider.CONTENT_URI_List_Item,
                        copy_values_int_list_item);
                Toast.makeText(this,
                        " Added data successfully in " + selected_ln ,Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Product is already exists in " + selected_ln,Toast.LENGTH_LONG).show();
            }
        }
    }

    /********************************************************************************************
     Name :- get_non_deleted_product_id
     Input :- String, String, int
     Output :- return ArrayList<String> object
     Description :- get non deleted product id into arraylist object
     **********************************************************************************************/
    public ArrayList<String> get_non_deleted_product_id(String selected_ln, String agentID, int list_incr){

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/List_Items";
        // parse url
        Uri LI_uri = Uri.parse(URL);
        ArrayList<String> non_deleted_prod_id = new ArrayList<String>();

        /* firing query to get product ids from local db  */
        Cursor li_cursor = getContentResolver().query(LI_uri, new String[]{GlobalProvider.LI_Product_ID},
                GlobalProvider.LI_List_Name + " =? AND " + GlobalProvider.LI_Login_User_Id + " =? AND " +
                        GlobalProvider.LI_Increment + " =? AND " + GlobalProvider.LI_deleted + " =? ",
                new String[]{selected_ln,agentID,String.valueOf(list_incr),"0"}, null);

        /* checking null factor  */
        if(null != li_cursor && li_cursor.moveToFirst()) {
            do {
                String prod_id = li_cursor.getString(li_cursor.getColumnIndex(GlobalProvider.LI_Product_ID));
                non_deleted_prod_id.add(prod_id);
            } while (li_cursor.moveToNext());
        }
        return non_deleted_prod_id;
    }

    /**********************************************************************************************
     Name :- get_list_data_from_local_DB
     Input :- pass string os agent id
     Output :- return arraylist of string object having all listnames
     Description :- query on local db of list table get all list names from local db added into
                    arraylist object.
     **********************************************************************************************/
    public ArrayList<String> get_list_data_from_local_DB(String agentID){

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/Lists";
        // parse url
        Uri List_uri = Uri.parse(URL);
        // define arraylist object
        ArrayList<String> array_of_lists_local_DB = new ArrayList<>();

        // query on List name column get all list names from local db of list table
        Cursor MyList_cursor = getContentResolver().query(List_uri, new String[]{GlobalProvider.List_Name},
                GlobalProvider.List_Login_User_ID + " = ? AND " + GlobalProvider.List_deleted + " = ?",
                new String[]{agentID, "0"}, null);

        /* cursor will move to first object */
        if(MyList_cursor.moveToFirst()){
            do{
                // listData_obj = setValuesOfList_LocalDB(MyList_cursor)
                String list_name = MyList_cursor.getString(MyList_cursor.getColumnIndex(GlobalProvider.List_Name));
                // added into arraylist
                array_of_lists_local_DB.add(list_name);

            } while(MyList_cursor.moveToNext());
        }
        return array_of_lists_local_DB;
    }

    /*********************************************************************************************
     Name :- get_list_incr_from_local_list_db
     Input :- String , String
     Output :- return int
     Description :- get list increment from local list table
     **********************************************************************************************/
    public int get_list_incr_from_local_list_db(String listNames, String agentID){

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/Lists";
        // parse url
        Uri List_uri = Uri.parse(URL);
        int list_incr = 0;
            /* firing query to get list increment column */
        Cursor list_cursor = getContentResolver().query(List_uri, new String[]{GlobalProvider.List_increment},
                GlobalProvider.List_Name + " =?  AND " + GlobalProvider.List_Login_User_ID + " =? AND " +
                        GlobalProvider.List_deleted + " =? ", new String[]{listNames, agentID, "0"}, null);
        // move to get first row
        list_cursor.moveToFirst();
        // get column value in int object
        list_incr = Integer.parseInt(list_cursor.getString(list_cursor.getColumnIndex(GlobalProvider.List_increment)));
        return  list_incr;
    }

    /********************************************************************************************
        Name :- create_final_string_product
        Description :- create string for particular product selected by user
    **********************************************************************************************/
    public void create_final_string_product(){

         // initialize tablelayout variable
         TableLayout tableLayout = (TableLayout) findViewById(R.id.mainTable);

         for(Map.Entry<Integer,ArrayList<Integer>> entry : to_refer_each_item.entrySet()){

             TableRow tr = (TableRow) tableLayout.getChildAt(entry.getKey());

             // get subtype name of product
             LinearLayout l_pn = (LinearLayout) tr.getChildAt(0);
             TextView tv_pn = (TextView) l_pn.getChildAt(0);
             String text_pn = tv_pn.getText().toString();

             LinearLayout l_pc = (LinearLayout) findViewById(R.id.product_linearlayout);
             // get color of product
             TextView text_pc_yv = (TextView) l_pn.getChildAt(1);
             String text_pc = text_pc_yv.getText().toString();
             String[] text_pc_str = text_pc.split(":");
             String final_text_pc = text_pc_str[1].replace(" ", "");
             // define array list object
             ArrayList<Integer> list = entry.getValue();
             Order_Detail_Object orderDetailObject = null;
             for(int i = 0; i < list.size();i++) {
                 // set checkbox in according to product items
                 CheckBox c = (CheckBox)findViewById(list.get(i));
                 /* check status of checkbox */
                 if(c.isChecked()) {

                     // create string variable to concat
                     String text_str =  text_pn;
                     /* get data from haspMap of Order_Detail_Object object as
                        orderDetailObject from database*/
                     orderDetailObject = new Order_Detail_Object
                             (text_str + ";" + c.getText().toString() + ";" + final_text_pc);
                     // function pass ProductId and orderDetailObject
                     AddToFinalOrder(orderDetailObject);
                 }
             }
         }
    }

    /*****************************************************************************************
     Name :- AddToFinalOrder
     Input :- String productId and Order_Detail_Object object to set  values into final_order
     Description :- Set all values into Globals finalOrder.
     ******************************************************************************************/
     public void AddToFinalOrder(Order_Detail_Object order_detail_object) {

        g = (Globals) getApplication();
         // get product id of particular product from product hashmap
        String ProductId = g.getProductsHashMap().get(order_detail_object.getItem()).getID();
        if(addToCart_flag) {
            // check condition that contains ProductId
            if (g.getFinal_order().containsKey(ProductId)) {

                // get quantity from g.getFinal_order() into int variable
                int qty = Integer.parseInt(g.getFinal_order().get(ProductId).getQuantity());
                // increment qty
                qty = qty + 1;
                // set quantity into final_order of hashmap
                g.getFinal_order().get(ProductId).setQuantity(String.valueOf(qty));
            } else {
                // put id and order_detail_object from globals
                g.getFinal_order().put(ProductId, order_detail_object);
            }
        } else{
            product_ids_for_list.add(ProductId);
        }
     }

    /***************************************************************************************
     Name :- ToastMessage
     Description :- Show toas message to user when items will be add into cart.
     ****************************************************************************************/
     public void ToastMessage() {

        // create toast message object
        final Toast toast = Toast.makeText(Product_Details_Page_Type_4.this,
                "Product added to cart",Toast.LENGTH_LONG);
        // show method call to show Toast message
        toast.show();

        // create handler to set duration for toast message
        Handler handler = new Handler() {

            @Override
            public void close() {
                // set duration for toast message
                toast.setDuration(100);
                // cancel toast message
                toast.cancel();
            }
            @Override
            public void flush() {}

            @Override
            public void publish(LogRecord record) {}
        };
     }

    /*************************************************************************************
     Name :- clear_data
     Description :- clear all fields data
     *************************************************************************************/
     public void clear_data() {

       for(Map.Entry<Integer,ArrayList<Integer>> entry : to_refer_each_item.entrySet()) {

            ArrayList<Integer> list = entry.getValue();

            for (int i = 0; i < list.size(); i++) {
                CheckBox c = (CheckBox) findViewById(list.get(i));
                if (c.isChecked()) {
                    c.setChecked(false);
                }
            }
        }
     }

    /*********************************************************************************
     Name :- callAddcartPage
     Description :- On click of add to cart all the item will be show by user
     *********************************************************************************/
     public  void callAddcartPage(MenuItem item) {

        // declare Intent ojbect to call another activity
        Intent calladdCartPage = new Intent(this,Order_Details.class);
        // startActivity by calling intent object
        startActivity(calladdCartPage);
     }

    /**************************************************************************************
     Name :- cancel_process
     Description :- on click of cancel button all checkbox will be clear
     **************************************************************************************/
    public void cancel_process() {

        Button cancel = (Button) findViewById(R.id.cancel_type4);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Uncheck all checkbox
                clear_data();

                // toast message will be show to user clear data
                Toast.makeText(Product_Details_Page_Type_4.this, "Clear data", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details_page_4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
