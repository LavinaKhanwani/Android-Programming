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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.mail.Quota;

public class Product_Details_Page_Type_2 extends Activity
{
    /********************************************************************************************
     * Section for declaration of the variables
     ********************************************************************************************/
      GridView product_singleItem_gridView;
      Button productDetailssingleItem_addToCart , productDetailssingleItem_cancel , add_to_list_btn;
      product_SingleAdapter productSingleAdapter;
      Globals g;
      AllProduct_getList allProduct_getList = new AllProduct_getList();
      ArrayList<String> product_ids_for_list =  new ArrayList<String>();
      boolean addToCart_flag;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_product_detais_page_type_2);

          // set gridview by calling id of girdview
          product_singleItem_gridView = (GridView) findViewById(R.id.product_type_SingleItem_gridView);

          // get position of products from product_page
          int product_position = getIntent().getExtras().getInt("position");

          set_list_of_product_in_adapter(product_position);

        /* call add to cart and cancel button process */
        addToCart_Process(product_position);
        add_to_my_list_process(product_position);
        cancel_process();
        changeActionBarTitle(product_position);
      }

     /***************************************************************************************
       Name :- create_list_of_products
       Input :- Pass product position as int
       Description :- Set product items in listview adapter
      ****************************************************************************************/
       public void set_list_of_product_in_adapter(int product_position){

         // set adapter for listview and add colors into listview
         ArrayList<AllProduct_getList.add_SingleItem_Product> listOfProducts =
                 new ArrayList<AllProduct_getList.add_SingleItem_Product>();

         /****************************************************************************************
          --> Define items depending on the position or the item clicked on the products page.
          --> And assign it to the adapter.
          *****************************************************************************************/
         listOfProducts = display_list_of_products(product_position);
         // set arraylist data into adapter object
         productSingleAdapter = new product_SingleAdapter(this,listOfProducts);
         // set adapter of gridview using adapter class object
         product_singleItem_gridView.setAdapter(productSingleAdapter);
         // set add to cart button by calling addToCart id
         productDetailssingleItem_addToCart = (Button)
                        findViewById(R.id.productDetails_singleItem_addToCart);

         // set cancel button by calling cancel id
         productDetailssingleItem_cancel = (Button)
                        findViewById(R.id.productDetails_singleItem_cancelButton);

           // set my list button
         add_to_list_btn = (Button) findViewById(R.id.productDetails_singleItem_myList);
     }

    /*********************************************************************************************
        Name :- display_list_of_products
        Input :- pass product_position as int
        Description :- get list of product to display in listview
     **********************************************************************************************/
     public ArrayList<AllProduct_getList.add_SingleItem_Product> display_list_of_products(int product_position) {

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.add_SingleItem_Product> listOfProducts = new ArrayList<AllProduct_getList.add_SingleItem_Product>();

        // create resources variable
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.TEJASMODULAR_POSITION)) {
            listOfProducts = allProduct_getList.getList_TejasModular();
        }  else if (product_position == r.getInteger(R.integer.MULTIPLUG_POSITION)) {
            listOfProducts = allProduct_getList.getMultiPlug_list();
        } else if (product_position == r.getInteger(R.integer.ROUND_PLATE_POSITION)) {
            listOfProducts = allProduct_getList.getRound_Plate();
        } else if (product_position == r.getInteger(R.integer.OTHER_ELECTRICAL_ACCESSORIES_POSITION)) {
            listOfProducts = allProduct_getList.getOtherElectrical_Accessories();
        } else if (product_position == r.getInteger(R.integer.PATTI_STAND_POSITION)) {
            listOfProducts = allProduct_getList.getPattiStand();
        } else if (product_position == r.getInteger(R.integer.BULKHEAD_POSITION)) {
            listOfProducts = allProduct_getList.getBulkHead();
        } else if (product_position == r.getInteger(R.integer.GOLD_RANGE_POSITION)) {
            listOfProducts = allProduct_getList.getGoldSwitch();
        } else if(product_position == r.getInteger(R.integer.GLORY_RANGE_POSITION)) {
            listOfProducts = allProduct_getList.getGlorySwitch();
        } else if(product_position == r.getInteger(R.integer.ONE_RANGE_POSITION)){
            listOfProducts = allProduct_getList.getOneSwitch();
        } else if(product_position == r.getInteger(R.integer.EVA_SWITCH_POSITION)) {
            listOfProducts = allProduct_getList.getEvaSwitch();
        } else if(product_position == r.getInteger(R.integer.PLUG_POSITION)){
            listOfProducts = allProduct_getList.getPlug();
        }  else if(product_position == r.getInteger(R.integer.TEJAS_CONCEAL_BOX_POSITION)) {
            listOfProducts = allProduct_getList.getTejas_Conceal_Box();
        }
        return listOfProducts;
     }

     /*****************************************************************************************
      Name :-changeActionBarTitle
      Input :- pass position of current item from gridview
      Description :- Changes action bar title using method getActionBar() of each product
                     item in gridview to another activity.
      *****************************************************************************************/
      public void changeActionBarTitle(int position_product) {

        Resources r = getResources();
        /************************************************************************************
         --> Check condition on which position user click compare to position_product and
             display title name accordingly
         ************************************************************************************/
        if(position_product == r.getInteger(R.integer.TEJASMODULAR_POSITION)) {
            getActionBar().setTitle("TEJAS MODULAR GANG BOX");
        } if(position_product == r.getInteger(R.integer.MULTIPLUG_POSITION)){
            getActionBar().setTitle("MULTIPLUGS");
        } if (position_product == r.getInteger(R.integer.ROUND_PLATE_POSITION)){
            getActionBar().setTitle("ROUND PLATE");
        } if(position_product == r.getInteger(R.integer.OTHER_ELECTRICAL_ACCESSORIES_POSITION)){
            getActionBar().setTitle("OTHER ACCESSORIES");
        } if(position_product == r.getInteger(R.integer.PATTI_STAND_POSITION)){
            getActionBar().setTitle("PATTI STAND");
        } if(position_product == r.getInteger(R.integer.BULKHEAD_POSITION)){
          getActionBar().setTitle("BULKHEAD");
        } if(position_product == r.getInteger(R.integer.GOLD_RANGE_POSITION)) {
             getActionBar().setTitle("GOLD SWITCHES");
        } if(position_product == r.getInteger(R.integer.GLORY_RANGE_POSITION)) {
             getActionBar().setTitle("GLORY SWITCHES");
        } if(position_product == r.getInteger(R.integer.ONE_RANGE_POSITION)) {
            getActionBar().setTitle("ONE MODULAR SWITCHES");
        } if(position_product == r.getInteger(R.integer.EVA_SWITCH_POSITION)) {
            getActionBar().setTitle("EVA SWITCHES");
        } if(position_product == r.getInteger(R.integer.PLUG_POSITION)) {
            getActionBar().setTitle("3 PIN PLUG TOPS");
        } if(position_product == r.getInteger(R.integer.TEJAS_CONCEAL_BOX_POSITION)){
              getActionBar().setTitle("TEJAS CONCEAL BOX");
          }
      }

     /******************************************************************************************
       Name:- ViewHolder
       Description:- viewholder class hold the things to display in gridview
      ******************************************************************************************/
      static class ViewHolder
      {
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

     /*****************************************************************************************
         Name:- product_SingleAdapter
         Description:- This class extends BaseAdapter to add all things into gridview one bye one
                   through implementing methods of BaseAdapter.
     *******************************************************************************************/
      class product_SingleAdapter extends BaseAdapter {

        // initialize int variable
        int i;

        // create context object
        Context context;
        // crate araylist<add_SingleItem_Product> object
        ArrayList<AllProduct_getList.add_SingleItem_Product> singleItem_products;

        /* declare constructor */
        product_SingleAdapter(Context context,ArrayList<AllProduct_getList.add_SingleItem_Product> singleItem_products)
        {
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
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // check whether check status in true or false
                    allProduct_getList.list_singleItemProduct.get(i).setCheckBox_staus(buttonView.isChecked());
                }

            });

            // add list into add_SingleItem_Product object
            AllProduct_getList.add_SingleItem_Product addSingleItemProduct = singleItem_products.get(i);

            // set image into  view_holder.imageview_SingleItem_product
            view_holder.imageview_SingleItem_product.setImageResource(addSingleItemProduct.getSingle_itemProduct());

            // set checkbox status into view_holder.single_checkbox
            view_holder.single_checkbox.setChecked(addSingleItemProduct.isCheckBox_staus());

            // set product name into textview
            view_holder.singleItemProductNameTextview.setText(addSingleItemProduct.getSingleItemProductName());

            // return view object
            return singleItemProduct_row;
        }

        /************************************************************************************
          Name :- getList()
          Output :- ArrayList<add_SingleItem_Product> of return list of product item
          Description :- function contains arraylist of Single Items Prodect items and
                        return through singleItem_products object.
         *************************************************************************************/
         public ArrayList<AllProduct_getList.add_SingleItem_Product> getList(){return singleItem_products;}
      }

     /*****************************************************************************************
       Name :- addToCart_Process
       Description :- on click of addToCart button which ever item will be selected it will
                    be add into cart.
     ******************************************************************************************/
      public void addToCart_Process(final int product_position) {

        productDetailssingleItem_addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addToCart_flag = true;
                // declare globals variable
                 g = (Globals) getApplication();

                String singleItemProductName = "";
                singleItemProductName = make_string_of_product(product_position);
                /* create final string according to product selected by user  */
                create_final_product_string(singleItemProductName, product_position);
                // function show message
                ToastMessage();
                // clear all data after adding product into cart
                clear_data();
            }
        });
      }

    /********************************************************************************************
     Name :- addtoMyList_process
     Description :- on click of addtoMyList_process button which ever item will be selected it will
     be add into list selected.
     ********************************************************************************************/
    public void add_to_my_list_process(final int product_position) {

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        final String AgentID = loginSharedPref.getString("ID", "");

        // set OnClickListener on addtoCart_btn button
        add_to_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart_flag = false;
                // Define String Variable
                String singleItemProductName = new String();
                singleItemProductName = make_string_of_product(product_position);
               /* create final string according to product selected by user  */
                create_final_product_string(singleItemProductName, product_position);
                /* get all list names into arraylist object */
                ArrayList<String> listNames = get_list_data_from_local_DB(AgentID);
                // display alert box to choose list name by user
                display_list_names(listNames, AgentID);
                // function will be call to clear_data
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
                        // insert values into local list_item db
                        insert_values_in_list_items_local_db(AgentID, selected_ln,list_incr);
                        // clear array list
                        product_ids_for_list.clear();
                    } else{
                        Toast.makeText(Product_Details_Page_Type_2.this,
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
        Name :- create_final_product_string
        Input :- pass product string and product position
        Description :- Create a final string of product, which item selected by user.
    *********************************************************************************************/
    public void create_final_product_string(String singleItemProductName, int product_position){

        Resources r = getResources();

        // getting data from adapter to pipeListAdapter object through loop
        for (final AllProduct_getList.add_SingleItem_Product selecteditem : productSingleAdapter.getList()) {

            // check condition of checkbox status
            if (selecteditem.isCheckBox_staus()) {

                final String ProductId;
                // get info into string which product is selected by user
                final String info = selecteditem.getSingleItemProductName();
                Order_Detail_Object orderDetailObject = null;

                if (product_position == r.getInteger(R.integer.TEJASMODULAR_POSITION)) {
                    // get data from haspMap of Order_Detail_Object object as orderDetailObject from database
                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "White");
                } else if(product_position == r.getInteger(R.integer.TEJAS_CONCEAL_BOX_POSITION)){

                    // get data from haspMap of Order_Detail_Object object as orderDetailObject from database
                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                }
                else if(product_position == r.getInteger(R.integer.ROUND_PLATE_POSITION)){

                    String RP_str_info = info;
                    String[] RP_array = RP_str_info.split(" ");

                    String RP_str = new String();
                    RP_str = RP_str.concat(RP_array[0] + ";" + RP_array[1] + " " +  RP_array[2]);
                    orderDetailObject = new Order_Detail_Object(singleItemProductName + RP_str + ";" + "WHITE");
                } else if(product_position == r.getInteger(R.integer.PATTI_STAND_POSITION)){

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                }  else if(product_position == r.getInteger(R.integer.GLORY_RANGE_POSITION)) {

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                } else if(product_position == r.getInteger(R.integer.GOLD_RANGE_POSITION)) {

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                } else if(product_position == r.getInteger(R.integer.PLUG_POSITION)) {

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                } else if(product_position == r.getInteger(R.integer.ONE_RANGE_POSITION)) {

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                }  else if(product_position == r.getInteger(R.integer.EVA_SWITCH_POSITION)) {

                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info + ";" + "WHITE");
                } else {
                    // get data from haspMap of Order_Detail_Object object as orderDetailObject from database
                    orderDetailObject = new Order_Detail_Object(singleItemProductName + info);
                }
                // function pass ProductId and orderDetailObject
                AddToFinalOrder(orderDetailObject);
            }
        }
    }

     /*****************************************************************************************
      Name :- AddToFinalOrder
      Input :- String productId and Order_Detail_Object object to set  values into final_order
      Description :- Set all values into Globals finalOrder.
     ******************************************************************************************/
     public void AddToFinalOrder(Order_Detail_Object order_detail_object)
     {
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
         } else {
             product_ids_for_list.add(ProductId);
         }
     }

     /***************************************************************************************
        Name :- ToastMessage
        Description :- Show toast message to user when items will be add into cart.
     ****************************************************************************************/
      public void ToastMessage() {

            // create toast message object
            final Toast toast = Toast.makeText(Product_Details_Page_Type_2.this,
                    "Product added to cart",Toast.LENGTH_LONG);
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

     /*************************************************************************************
        Name :- clear_data
        Description :- clear all fields data
     *************************************************************************************/
      public void clear_data()
      {
        // using loop all check will be set to be false
        for(AllProduct_getList.add_SingleItem_Product a : allProduct_getList.list_singleItemProduct)
        {
            // set checkbox status as false;
            a.setCheckBox_staus(false);
        }
        productSingleAdapter.notifyDataSetChanged();
      }

     /**************************************************************************************
       Name :- cancel_process
       Description :- on click of cancel button all checkbox will be clear
     **************************************************************************************/
     public void cancel_process()
     {
        productDetailssingleItem_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uncheck all checkbox
                clear_data();

                // toast message will be show to user clear data
                Toast.makeText(Product_Details_Page_Type_2.this, "Clear data", Toast.LENGTH_LONG).show();
            }
        });
     }

     /*********************************************************************************
        Name :- callAddcartPage
        Description :- On click of add to cart all the item will be show by user
      *********************************************************************************/
      public  void callAddcartPage(MenuItem item)
      {
        // declare Intent ojbect to call another activity
        Intent calladdCartPage = new Intent(this,Order_Details.class);

        // startActivity by calling intent object
        startActivity(calladdCartPage);
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details__single_item, menu);
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

    /*********************************************************************************************
        Name :- make_string_of_product
        Input :- Pass product_position as int
        Description :- Define the product depending on which item in clicked or which position is
                     clicked on the products page
     *******************************************************************************************/
     public String make_string_of_product(int product_position){

        Resources r = getResources();
        String  singleItemProductName = "";

        if(product_position == r.getInteger(R.integer.TEJASMODULAR_POSITION)) {
            singleItemProductName = "Tejas Modular Gang Box;";
        }  else  if(product_position == r.getInteger(R.integer.MULTIPLUG_POSITION)){
            singleItemProductName = "MULTIPLUG;";
        } else if(product_position == r.getInteger(R.integer.ROUND_PLATE_POSITION)){
            singleItemProductName = "ROUND PLATE;";
        } else if(product_position == r.getInteger(R.integer.OTHER_ELECTRICAL_ACCESSORIES_POSITION)){
            singleItemProductName = "OTHER ELECTRICAL ACCESSORIES;";
        } else if(product_position == r.getInteger(R.integer.PATTI_STAND_POSITION)){
            singleItemProductName = "PATTI STAND;";
        } else if(product_position == r.getInteger(R.integer.BULKHEAD_POSITION)){
            singleItemProductName = "BULKHEAD;";
        } else if(product_position == r.getInteger(R.integer.GLORY_RANGE_POSITION)) {
            singleItemProductName = "GLORY;";
        } else if(product_position == r.getInteger(R.integer.GOLD_RANGE_POSITION)) {
            singleItemProductName = "GOLD;";
        } else if(product_position == r.getInteger(R.integer.PLUG_POSITION)) {
            singleItemProductName = "PLUG;";
        } else if(product_position == r.getInteger(R.integer.ONE_RANGE_POSITION)) {
            singleItemProductName = "ONE MODULAR;";
        }  else if(product_position == r.getInteger(R.integer.EVA_SWITCH_POSITION)) {
            singleItemProductName = "EVA MODULAR;";
        } else if(product_position == r.getInteger(R.integer.UNI_GANG_BOX_POSITION)){
            singleItemProductName = "UNI MODULAR GANG BOX;";
        } else if(product_position == r.getInteger(R.integer.TEJAS_CONCEAL_BOX_POSITION)){
            singleItemProductName = "TEJAS CONCEAL BOX;";
        }
        return singleItemProductName;
     }
}
