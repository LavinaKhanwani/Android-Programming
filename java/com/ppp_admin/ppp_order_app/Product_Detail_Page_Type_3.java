package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;

import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Product_Detail_Page_Type_3 extends Activity
{
    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    GridView product_singleItem_gridView;
    Button productDetailType3_addToCart_btn , productDetailType3_cancel , add_to_my_list_btn;
    product_SingleAdapter productSingleAdapter;
    ArrayList<String> SingleItem_Tester_arrayList = new ArrayList<String>();
    Globals g;
    AllProduct_getList allProduct_getList = new AllProduct_getList();
    int product_position;
    Resources r;
    ArrayList<String> product_ids_for_list =  new ArrayList<String>();
    boolean addToCart_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail__page__type_3);

        // set gridview by calling id of girdview
        product_singleItem_gridView = (GridView) findViewById(R.id.Producttype3_gridView);

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.ItemWithoutColor_SingleItem> listOfProducts = new ArrayList<>();

        /****************************************************************************************
         --> Define items depending on the position or the item clicked on the products page.
         --> And assign it to the adapter.
         *****************************************************************************************/
        product_position = getIntent().getExtras().getInt("position");

        r = getResources();

        if(product_position == r.getInteger(R.integer.TESTER_POSITION)) {
            listOfProducts = allProduct_getList.getTester();
        } else if(product_position == r.getInteger(R.integer.HOLDER_RANGE_POSITION)) {
            listOfProducts = allProduct_getList.getHolder();
        } else if(product_position == r.getInteger(R.integer.PLASTIC_BOARD_POSITION)){
            listOfProducts = allProduct_getList.getPlastic_Board();
        } else if (product_position == r.getInteger(R.integer.DOORBELL_POSITION)) {
            listOfProducts = allProduct_getList.getDoorBell();
        } else if(product_position == r.getInteger(R.integer.PALAZO_MP_POSITION)){
            listOfProducts = allProduct_getList.getPalazzo_list();
        } else if(product_position == r.getInteger(R.integer.OMYAH_MP_POSITION)){
            listOfProducts = allProduct_getList.getOmyah_list();
        } else if(product_position == r.getInteger(R.integer.UNI_GANG_BOX_POSITION)){
            listOfProducts = allProduct_getList.getUNI_Gang_Box();
        }

        // set arraylist data into adapter object
        productSingleAdapter = new product_SingleAdapter(this,listOfProducts);
        // set adapter of gridview using adapter class object
        product_singleItem_gridView.setAdapter(productSingleAdapter);
        // set add to cart button by calling addToCart id
        productDetailType3_addToCart_btn = (Button) findViewById(R.id.addCart_button_type3);
        // set my list button
        add_to_my_list_btn = (Button) findViewById(R.id.myList_button_type3);
        // set cancel button by calling cancel id
        productDetailType3_cancel = (Button) findViewById(R.id.cancel_button_type3);

        /* call addToCart and cancel button process */
        addToCartProcess();
        add_to_my_list_process();
        cancel_process();

        /* call function to change title message of action bar */
        changeActionBarTitle();
    }

    /*****************************************************************************************
     Name :-changeActionBarTitle
     Input :- pass position of current item from gridview
     Description :- Changes action bar title using method getActionBar() of each product
     item in gridview to another activity.
     *****************************************************************************************/
    public  void changeActionBarTitle() {

        /************************************************************************************
         --> Check condition on which position user click compare to position_product and
         display title name accordingly
         ************************************************************************************/
        Resources r = getResources();
        if(product_position == r.getInteger(R.integer.TESTER_POSITION)) {
            getActionBar().setTitle("TESTER");
        } else if(product_position == r.getInteger(R.integer.HOLDER_RANGE_POSITION)) {
            getActionBar().setTitle("HOLDERS");
        } else if(product_position == r.getInteger(R.integer.PLASTIC_BOARD_POSITION)){
            getActionBar().setTitle("PLASTIC BOARD");
        } else if(product_position ==  r.getInteger(R.integer.DOORBELL_POSITION)) {
            getActionBar().setTitle("DOOR BELLS");
        } else if(product_position == r.getInteger(R.integer.PALAZO_MP_POSITION)){
            getActionBar().setTitle("PALAZZO");
        } else if(product_position == r.getInteger(R.integer.OMYAH_MP_POSITION)){
            getActionBar().setTitle("OMYAH");
        } else if(product_position == r.getInteger(R.integer.UNI_GANG_BOX_POSITION)){
            getActionBar().setTitle("UNI GANG BOX");
        }
    }

    /*********************************************************************************************
     Name:- ViewHolder
     Description:- viewholder class hold the things to display in gridview
     ********************************************************************************************/
    static class ViewHolder {

        // create Imageview object
        ImageView imageview_SingleItem_product;

        // create checkbox object
        CheckBox single_checkbox;

        // create textview object
        TextView singleItemProductNameTextview;

        // declare constructor
        ViewHolder(View v) {
            imageview_SingleItem_product = (ImageView) v.findViewById(R.id.commmon_singleItem_imageView);
            single_checkbox = (CheckBox) v.findViewById(R.id.common_singleItem_checkBox);
            singleItemProductNameTextview = (TextView) v.findViewById(R.id.singleItem_product_textView);
        }
    }

    /********************************************************************************************
     Name:- product_SingleAdapter
     Description:- This class extends BaseAdapter to add all things into gridview one bye one
     through implementing methods of BaseAdapter.
     *********************************************************************************************/
    class product_SingleAdapter extends BaseAdapter {

        // initialize int variable
        int i;

        // create context object
        Context context;

        // crate araylist<<AllProduct_getList.ItemWithoutColor_SingleItem> object
        ArrayList<AllProduct_getList.ItemWithoutColor_SingleItem> singleItem_products;

        /* declare constructor */
        product_SingleAdapter(Context context,ArrayList<AllProduct_getList.ItemWithoutColor_SingleItem> singleItem_products) {

            this.context = context;
            this.singleItem_products = singleItem_products;}

        @Override
        public int getCount() {return singleItem_products.size();}

        @Override
        public Object getItem(int  i) {return singleItem_products.get(i);}

        @Override
        public long getItemId(int i) {return i;}

        @Override
        public View getView(final int i, final View convertView, ViewGroup parent) {

            // create view for single row
            View singleItemProduct_row = convertView;
            // create ViewHolder object
            ViewHolder view_holder = null;

            LayoutInflater products_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // View object get layout through LayoutInflater object as inflater
            singleItemProduct_row = products_inflater.inflate(R.layout.activity_singleitem_products, parent, false);
            // Through ViewHolder object get row into gridview
            view_holder = new ViewHolder(singleItemProduct_row);
            // set tag for View object
            singleItemProduct_row.setTag(view_holder);

            view_holder.single_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    // check whether check status in true or false
                    allProduct_getList.list_SIPWithOutColor.get(i).setCheckBox_staus(buttonView.isChecked());
                    if(isChecked) {
                        checkItem(allProduct_getList.list_SIPWithOutColor.get(i), isChecked);
                    }
                }
            });

            // add list into  AllProduct_getList.ItemWithoutColor_SingleItem object
            AllProduct_getList.ItemWithoutColor_SingleItem addSingleItemProduct = singleItem_products.get(i);

            // set image into  view_holder.imageview_SingleItem_product
            view_holder.imageview_SingleItem_product.setImageResource(addSingleItemProduct.getSingle_itemProduct());
            // set checkbox status into view_holder.single_checkbox
            view_holder.single_checkbox.setChecked(addSingleItemProduct.isCheckBox_staus());
            // set product name into textview
            view_holder.singleItemProductNameTextview.setText(addSingleItemProduct.getSingleItemProductName());

            // return view object
            return singleItemProduct_row;
        }

        /****************************************************************************************
         Name :- getList()
         Output :- ArrayList<ItemWithoutColor_SingleItem> of return list of product item
         Description :- function contains arraylist of Tester items and
         return through singleItem_products object
         ****************************************************************************************/
        public ArrayList<AllProduct_getList.ItemWithoutColor_SingleItem> getList() {
            return singleItem_products;
        }
    }

    /********************************************************************************************
     Name :- addToCartProcess
     Description :- on click of addToCart button which ever item will be selected it will
     be add into cart.
     ********************************************************************************************/
    public  void addToCartProcess() {

        productDetailType3_addToCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart_flag = true;
                // declare globals variable
                g = (Globals) getApplication();

                // getting data from arraylist object through loop
                for (String a : SingleItem_Tester_arrayList) {

                    // define order_detail_object and set value getting from loop
                    Order_Detail_Object orderDetailObject = new Order_Detail_Object(a);
                    // pass productid and orderdetailobject
                    AddToFinalOrder(orderDetailObject);
                }
                // clear arraylist
                SingleItem_Tester_arrayList.clear();
                // call function show toast message
                ToastMessage();
                // clear all data
                clear_data();
            }
        });
    }

    /********************************************************************************************
     Name :- add_to_my_list_process
     Description :- on click of add_to_my_list button which ever item will be selected it will
     be add into my list.
     ********************************************************************************************/
    public  void add_to_my_list_process() {

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        final String AgentID = loginSharedPref.getString("ID", "");

        add_to_my_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart_flag = false;

                // declare globals variable
                g = (Globals) getApplication();

                // getting data from arraylist object through loop
                for (String a : SingleItem_Tester_arrayList) {

                    // define order_detail_object and set value getting from loop
                    Order_Detail_Object orderDetailObject = new Order_Detail_Object(a);
                    // pass productid and orderdetailobject
                    AddToFinalOrder(orderDetailObject);
                }
                 /* get all list names into arraylist object */
                ArrayList<String> listNames = get_list_data_from_local_DB(AgentID);
                // display alert box to choose list name by user
                display_list_names(listNames, AgentID);
                // clear arraylist
                SingleItem_Tester_arrayList.clear();
                // clear all data
                clear_data();
            }
        });
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
                        //  get selected list name chosen by user
                        String selected_ln = arrayAdapter_list_names.getItem(selected);
                        // get increment into int variable
                        int list_incr = get_list_incr_from_local_list_db(selected_ln, AgentID);
                        // insert values into local list_item
                        insert_values_in_list_items_local_db(AgentID, selected_ln,list_incr);
                        product_ids_for_list.clear();
                    } else{
                        Toast.makeText(Product_Detail_Page_Type_3.this,
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

    /*********************************************************************************************
     Name :- AddToFinalOrder
     Input :- String productId and Order_Detail_Object object to set  values into final_order
     Description :- Set all values into Globals finalOrder.
     *********************************************************************************************/
    public void AddToFinalOrder(Order_Detail_Object order_detail_object) {

        g = (Globals) getApplication();

        // get product id from producthashmap of particular item
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

    /******************************************************************************************
     Name :- ToastMessage
     Description :- Show toast message to user when items will be add into cart.
     ******************************************************************************************/
    public void ToastMessage() {

        // create toast message object
        final Toast toast = Toast.makeText(Product_Detail_Page_Type_3.this,
                "Product added to cart", Toast.LENGTH_SHORT);
        // show method call to show Toast message
        toast.show();

        // create handler to set duration for toast message
        Handler handler = new Handler()
        {
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

    /*******************************************************************************************
     Name  :- checkItem
     Input :- pass ItemWithoutColor_SingleItem's object and boolean value
     Description :- function checks which checked according to that it will perform action.
     ********************************************************************************************/
    public void checkItem(AllProduct_getList.ItemWithoutColor_SingleItem list_singleItemProduct, boolean isChecked ) {

        // create string[] object to get array define in xml file into object
        final String[] light = getResources().getStringArray(R.array.Tester_Color_Light);

        String[] palazzo_and_Omyah_item = getResources().getStringArray(R.array.palazzo_item);

        // get selected item into String object , user checks in checkbox
        final String info = list_singleItemProduct.getSingleItemProductName();

        // set counter for alert message
        int count = light.length;

        // set boolean[] value through counter
        final boolean[] checkStatus = new boolean[count];

        /***************************************************************************************
         Condition checks whether user checks above mention string, if its equals then it
         will perform action and checkbox it will show alert box and ask from user choose
         light options.
         ****************************************************************************************/
        if (info.equals("Sathiya White") || info.equals("Sathiya Green")|| info.equals("Sathi Gold Green")
                || info.equals("Sathi Gold White") ) {
            make_list_of_tester_with_light_and_color(checkStatus, info, light);

        } else if(info.equals("Diya")  || info.equals("Chotu")
                || info.equals("Charms") || info.equals("Glory")) {
            make_list_of_tester_with_light(checkStatus, info, light);

        } else if(product_position == r.getInteger(R.integer.HOLDER_RANGE_POSITION)) {
            make_string_of_holder(isChecked,info);

        } else if(product_position == r.getInteger(R.integer.PLASTIC_BOARD_POSITION)){
            make_list_of_PB_colors(checkStatus, info);

        } else if(product_position == r.getInteger(R.integer.DOORBELL_POSITION)){
            make_string_of_door_bell(isChecked,info);

        } else if(product_position == r.getInteger(R.integer.PALAZO_MP_POSITION)){
            make_alert_box_palazzo_choose_item(checkStatus, info, palazzo_and_Omyah_item);

        } else if(product_position == r.getInteger(R.integer.OMYAH_MP_POSITION)){
            make_alert_box_omyah_choose_item(checkStatus,info,palazzo_and_Omyah_item);

        } else if(product_position == r.getInteger(R.integer.UNI_GANG_BOX_POSITION)){
            make_alert_box_uni_gang_box_choose_item(checkStatus,info,palazzo_and_Omyah_item);

        } else {
            make_string_of_tester_without_color(isChecked,info,light);
        }
    }

    /**************************************************************************************
     Name :- cancel_process
     Description :- on click of cancel button all checkbox will be clear
     **************************************************************************************/
    public void cancel_process() {

        productDetailType3_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Uncheck all checkbox
                clear_data();
                // toast message will be show to user clear data
                Toast.makeText(Product_Detail_Page_Type_3.this, "Clear data", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*************************************************************************************
     Name :- clear_data
     Description :- clear all fields data
     *************************************************************************************/
    public void clear_data() {

        // using loop all check will be set to be false
        for(AllProduct_getList.ItemWithoutColor_SingleItem a : allProduct_getList.list_SIPWithOutColor) {
            // set checkbox status as false;
            a.setCheckBox_staus(false);
        }
        productSingleAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product__detail__page__type_3, menu);
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

    /**********************************************************************************************
     Name :- make_string_of_tester_with_light_and_color
     Input :- Pass boolean variable as checkStatus, String variable as info,
     String variable as light.
     Description :- Function works to create alter box to choose light option by
     user for tester product.
     ***********************************************************************************************/
    public void make_list_of_tester_with_light_and_color(final boolean[] checkStatus, final String info, String[] light){

        // create alert box's object
        AlertDialog.Builder alertTester = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);

        // set title message
        alertTester.setTitle("Choose Tester light");

        // set Multichoiceitems on alert box
        alertTester.setMultiChoiceItems(light,checkStatus,new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkStatus[which] = isChecked;
            }
        })
                // set cancelable boolean value
                .setCancelable(false)

                        // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // function call to make string
                        make_string_of_light_and_color(dialog, info);

                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel
                        dialog.cancel();
                    }
                });
        // create alert box
        AlertDialog alertDialog = alertTester.create();

        // show it
        alertDialog.show();
    }

    /*********************************************************************************************
     Name :- make_list_of_light_and_color
     Input :- Pass DialogInterface variable and String variable
     Description :- Function work when use choose light option it will make string
     with light and color and it add product name into finalItem array.
     *********************************************************************************************/
    public void make_string_of_light_and_color(DialogInterface dialog, String info){

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // split values and store into string[]'s object
        String[] str = info.split(" ");

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String light_value =  list.getItemAtPosition(i).toString();

            // concat values and store into string's object
            String currentValue = info.concat(";" + light_value + ";" + str[str.length - 1]);

            // make final value that matches from database
            String finalItem = "Tester;" + currentValue;

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if(checked) {

                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            }else if(SingleItem_Tester_arrayList.contains(finalItem)) {

                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }

    /**********************************************************************************************
     Name :- make_string_of_tester_with_light
     Input :- Pass boolean variable as checkStatus, String variable info and String[] variable
     as light
     Description :- Function will work to display alert box with light options.
     **********************************************************************************************/
    public void make_list_of_tester_with_light(final boolean[] checkStatus, final String info, String[] light){

        // create alert box's object
        AlertDialog.Builder alertTester = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);
        // set title message
        alertTester.setTitle("Choose Tester light");
        // set Multichoiceitems on alert box
        alertTester.setMultiChoiceItems(light,checkStatus,new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkStatus[which] = isChecked;
            }
        })
                // set cancelable boolean value
                .setCancelable(false)
                        // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        make_string_of_tester(dialog, info);
                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel
                        dialog.cancel();
                    }
                });
        // create alert box
        AlertDialog alertDialog = alertTester.create();

        // show it
        alertDialog.show();
    }

    /**********************************************************************************************
     Name :- make_list_of_light
     Input :- Pass Dialog variable as dialog and String variable and info
     Description :- Function will work to make string of tester with light option choose
     by user and added into finalItem variable.
     **********************************************************************************************/
    public void make_string_of_tester(DialogInterface dialog,String info){

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String light_value =  list.getItemAtPosition(i).toString();

            // concat values and store into string's object
            String currentValue = info.concat(";" + light_value);

            // make final value that matches from database
            String finalItem = "Tester;" + currentValue;

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if(checked) {

                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            }else if(SingleItem_Tester_arrayList.contains(finalItem)) {

                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }

    /*********************************************************************************************
     Name :- make_string_of_holder
     Input :- Pass boolean value as isChecked and String value as info
     Description :- Function works to make string of Holder product and add product name into
     finalItem array.
     *********************************************************************************************/
    public void make_string_of_holder(boolean isChecked, String info){

        String finalItem = new String();

        if(info.equals("TEJAS ANGLE;BLACK") || info.equals("TEJAS BATTEN;RED") ||
                info.equals("PC PENDENT(WITH SKIRT);BLACK")
                || info.equals("JEWEL PENDENT(WITH SKIRT);BLACK")
                || info.equals("JEWEL PENDENT(O.R);BLACK") || info.equals("TEJAS ANGLE;RED")
                || info.equals("TEJAS BATTEN;BLACK") || info.equals("PC PENDENT(O.R);BLACK")
                || info.equals("PENDENT OBR;BLACK")) {

            // get tester light property from array
            String currentValue = info;
            // make statement that matches from database
            finalItem = "HOLDER;" + currentValue;

        } else {
            // get tester light property from array
            String currentValue = info.concat(";");
            // make statement that matches from database
            finalItem = "HOLDER;" + currentValue + "WHITE";
        }
        /******************************************************************************
         check condition boolean value if its true item will be added into arraylist
         other it will remove item from arraylist.
         ******************************************************************************/
        if (isChecked) {
            // add item into arraylist
            SingleItem_Tester_arrayList.add(finalItem);

        } else if (SingleItem_Tester_arrayList.contains(finalItem)) {
            // remove item from arraylist
            SingleItem_Tester_arrayList.remove(finalItem);
        }
    }

    /********************************************************************************************
     Name :- make_string_of_plastic_board
     Input :- Pass boolean variable as checkStatus and String variable as info
     Description :- Function works to display list of colors in alert box.
     ********************************************************************************************/
    public void make_list_of_PB_colors(final boolean[] checkStatus, final String info){

        // create alert box's object
        AlertDialog.Builder alertHolder = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);

        // set title message
        alertHolder.setTitle("Choose Color");


        if(info.contains("4X4 CAMERA MOUNTING BOX")) {

            String[]  PB_color = getResources().getStringArray(R.array.PLASTIC_BOARDS_COLOR_NEW);

            // set Multichoice items on alert box
            alertHolder.setMultiChoiceItems(PB_color, checkStatus, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    checkStatus[which] = isChecked;
                }
            });
        } else {

            String[]  PB_color = getResources().getStringArray(R.array.PLASTIC_BOARDS_COLOR);

            // set Multichoice items on alert box
            alertHolder.setMultiChoiceItems(PB_color, checkStatus, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    checkStatus[which] = isChecked;
                }
            });
        }
        // set cancelable boolean value
        alertHolder .setCancelable(false)
                // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        make_string_of_plastic_board(dialog, info);
                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // canceldialog.cancel();
                    }
                });

        // create alert box
        AlertDialog alertDialog = alertHolder.create();

        // show it
        alertDialog.show();
    }

    /*********************************************************************************************
     Name :- make_list_of_colors
     Input :- Pass DialogInterface as dialog and String as info
     Description :- Function will work to make to make string of Plastic board product and
     added the item into finalItem array.
     *********************************************************************************************/
    public void make_string_of_plastic_board(DialogInterface dialog, String info) {

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String color_value = list.getItemAtPosition(i).toString();
            // concat values and store into string's object
            String currentValue = info.concat(";" + color_value);
            // make final value that matches from database
            String finalItem = "PLASTIC BOARDS;" + currentValue;

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if (checked) {
                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            } else if (SingleItem_Tester_arrayList.contains(finalItem)) {
                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }
    /*********************************************************************************************
     Name :- make_string_of_door_bell
     Input :- Pass boolean variable as isChecked and String variable as info
     Description :- Function will work to make string of Door Bell Product and item name will
     be added into finalItem array.
     *********************************************************************************************/
    public void make_string_of_door_bell(boolean isChecked, String info){

        String finalItem =  "Door Bell;" + info;
        /******************************************************************************
         check condition boolean value if its true item will be added into arraylist
         other it will remove item from arraylist.
         ******************************************************************************/
        if(isChecked){
            // add item into arraylist
            SingleItem_Tester_arrayList.add(finalItem);
        }else if(SingleItem_Tester_arrayList.contains(finalItem)){
            // remove item from arraylist
            SingleItem_Tester_arrayList.remove(finalItem);
        }
    }

    /*********************************************************************************************
     Name :- make_string_of_tester_without_color
     Input :- Pass boolean variable as isChecked, String variable as info and
     String[] variable as light
     Description :- Function will work to make string which is not having color or light option
     and product name will be added into finalItem array.
     *********************************************************************************************/
    public void make_string_of_tester_without_color(boolean isChecked, String info, String[] light){

        // get tester light property from array
        String currentValue =info.concat(";" + light[0]);

        // make statement that matches from database
        String finalItem = "Tester;" + currentValue;

        /******************************************************************************
         check condition boolean value if its true item will be added into arraylist
         other it will remove item from arraylist.
         ******************************************************************************/
        if(isChecked){
            // add item into arraylist
            SingleItem_Tester_arrayList.add(finalItem);
        }else if(SingleItem_Tester_arrayList.contains(finalItem)){
            // remove item from arraylist
            SingleItem_Tester_arrayList.remove(finalItem);
        }
    }

    /**********************************************************************************************
     Name :- make_alert_box_palazzo_choose_item
     Input :- Pass boolean variable as isChecked and String variable as info
     Description :- Function will create alertbox to choose border from border option
     accordingly item will be add into cart.
     **********************************************************************************************/
    public void make_alert_box_palazzo_choose_item(final boolean[] checkStatus, final String info, final String[] palazzo_and_Omyah_item){

        // create alert box's object
        AlertDialog.Builder alertTester = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);

        // set title message
        alertTester.setTitle("Choose Palazzo Plates ");

        // set Multichoiceitems on alert box
        alertTester.setMultiChoiceItems(palazzo_and_Omyah_item,checkStatus,new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkStatus[which] = isChecked;
            }
        })
                // set cancelable boolean value
                .setCancelable(false)

                        // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // function call to make string
                        make_string_of_palazzo_border(dialog, info);

                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel
                        dialog.cancel();
                    }
                });
        // create alert box
        AlertDialog alertDialog = alertTester.create();

        // show it
        alertDialog.show();
    }

    /**********************************************************************************************
     Name :- make_string_of_palazzo_border
     Input :- Pass boolean variable as isChecked, String variable as info and
     String[] variable as light
     Description :- Function will work to make string which is having border or not
     accordingly product will be added into finalItem array.
     ***********************************************************************************************/
    public void make_string_of_palazzo_border(DialogInterface dialog, String info){

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String palazzo_value =  list.getItemAtPosition(i).toString();

            String finalItem = new String();

            if(palazzo_value.contains("WITH SILVER BORDER")){

                finalItem =  "PALAZZO MODULAR PLATES WITH SILVER BORDER;" + info + ";WHITE";
            } else {
                finalItem = "PALAZZO MODULAR PLATES;" + info + ";WHITE";
            }

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if(checked) {

                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            }else if(SingleItem_Tester_arrayList.contains(finalItem)) {

                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }

    /**********************************************************************************************
     Name :- make_alert_box_omyah_choose_item
     Input :- Pass boolean variable as isChecked and String variable as info
     Description :- Function will create alertbox to choose border from border option
     accordingly item will be add into cart.
     **********************************************************************************************/
    public void make_alert_box_omyah_choose_item(final boolean[] checkStatus, final String info, final String[] palazzo_and_Omyah_item){

        // create alert box's object
        AlertDialog.Builder alertTester = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);

        // set title message
        alertTester.setTitle("Choose Omyah Plates ");

        // set Multichoiceitems on alert box
        alertTester.setMultiChoiceItems(palazzo_and_Omyah_item,checkStatus,new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkStatus[which] = isChecked;
            }
        })
                // set cancelable boolean value
                .setCancelable(false)

                        // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // function call to make string
                        make_string_of_omyah_border(dialog, info);

                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel
                        dialog.cancel();
                    }
                });
        // create alert box
        AlertDialog alertDialog = alertTester.create();

        // show it
        alertDialog.show();
    }

    public void make_string_of_omyah_border(DialogInterface dialog, String info){

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String palazzo_value =  list.getItemAtPosition(i).toString();

            String finalItem = new String();

            if(palazzo_value.contains("WITH SILVER BORDER")){

                finalItem =  "OMYAH MODULAR PLATES WITH SILVER BORDER;" + info + ";WHITE";
            } else {
                finalItem = "OMYAH MODULAR PLATES;" + info + ";WHITE";
            }

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if(checked) {

                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            }else if(SingleItem_Tester_arrayList.contains(finalItem)) {

                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }

    /**********************************************************************************************
     Name :- make_alert_box_uni_gang_box_choose_item
     Input :- Pass boolean variable as isChecked and String variable as info
     Description :- Function will create alertbox to choose border from border option
     accordingly item will be add into cart.
     **********************************************************************************************/
    public void make_alert_box_uni_gang_box_choose_item(final boolean[] checkStatus, final String info, final String[] palazzo_and_Omyah_item){

        // create alert box's object
        AlertDialog.Builder alertTester = new AlertDialog.Builder(Product_Detail_Page_Type_3.this);

        // set title message
        alertTester.setTitle("Choose Omyah Plates ");

        // set Multichoiceitems on alert box
        alertTester.setMultiChoiceItems(palazzo_and_Omyah_item,checkStatus,new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                checkStatus[which] = isChecked;
            }
        })
                // set cancelable boolean value
                .setCancelable(false)

                        // set positive button value
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // function call to make string
                        make_string_of_uni_gang_border(dialog, info);

                        // alert dialog will be dismiss
                        dialog.dismiss();
                    }
                })
                        // set negative button onclick of negative button alert box will be close
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel
                        dialog.cancel();
                    }
                });
        // create alert box
        AlertDialog alertDialog = alertTester.create();

        // show it
        alertDialog.show();
    }

    public void make_string_of_uni_gang_border(DialogInterface dialog, String info){

        // set alert message in listview
        ListView list = ((AlertDialog) dialog).getListView();

        // loop  use for iterate
        for (int i = 0; i < list.getCount(); i++) {

            // get checked status into boolean's object
            boolean checked = list.isItemChecked(i);

            // get value from list store into string's object
            String palazzo_value =  list.getItemAtPosition(i).toString();

            String finalItem = new String();

            if(palazzo_value.contains("WITH SILVER BORDER")){

                finalItem =  "UNI MODULAR GANG BOX WITH SILVER BORDER;" + info + ";WHITE";
            } else {
                finalItem = "UNI MODULAR GANG BOX;" + info + ";WHITE";
            }

            /*********************************************************************
             condition check boolean values if it will be true then item will me
             added into arraylist's object and unchecked item will be remove from
             arraylist.
             *********************************************************************/
            if(checked) {

                // add finalItem into arraylist
                SingleItem_Tester_arrayList.add(finalItem);
            }else if(SingleItem_Tester_arrayList.contains(finalItem)) {

                // remove finalItem into arraylist
                SingleItem_Tester_arrayList.remove(finalItem);
            }
        }
    }

}
