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
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class Product_Details_Page_Type_1 extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    ListView productListView;
    Button addToCart_btn, addToMyList_btn;
    myListAdapter productListAdapter;
    Spinner  product_color_spinner;
    Button cancel;
    ScrollView parent_scrollview;
    String value;
    ImageView iv;
    boolean addToCart_flag;
    ArrayList<String> product_ids_for_list =  new ArrayList<String>();

    // define colors into int of array
    private int[] colors = new int[] {Color.parseColor("#d3d3d3"),Color.parseColor("#d3d3d3"),
            Color.parseColor("#d3d3d3"), Color.parseColor("#fff6e5"),Color.parseColor("#fff6e5"),
            Color.parseColor("#fff6e5") };
    int[] bottom_padding = new int[]{0,0,35};
    AllProduct_getList allProduct_getList = new AllProduct_getList();

    /*********************************************************************************************/
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_product__details__page__type_1);

        // create listview through id defined in xml file
        productListView = (ListView) findViewById(R.id.Product_listView);

        // set image of product
        iv = (ImageView) findViewById(R.id.imageView);

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts = new ArrayList<AllProduct_getList.Product_View_Obj>();

        /****************************************************************************************
         --> Define items depending on the position or the item clicked on the products page.
         --> And assign it to the adapter.
         *****************************************************************************************/
        int product_position = getIntent().getExtras().getInt("position");

        // getting subtype value from product_subtype_page
        value = getIntent().getStringExtra("subType_value");

        // Display list according to products
        listOfProducts  = display_list_products(product_position);

        productListAdapter = new myListAdapter(this, listOfProducts, colors);

        // adding list from adapter object as cableTiesListAdapter
        productListView.setAdapter(productListAdapter);

        // function of color choose color by user
        color_spinner(product_position);

        // create button given id in xml file
        addToCart_btn = (Button) findViewById(R.id.addToCart_button_type1);

        addToMyList_btn = (Button) findViewById(R.id.addTomyList_button_type1);
        // create button given id in xml file
        cancel = (Button) findViewById(R.id.ISIPVC_Pipe_cancel_button);
        // create scrollbar given id in xml file
        parent_scrollview = (ScrollView) findViewById(R.id.ScrollView_parent);

        addtoCart_process(product_position);
        addtoMyList_process(product_position);
        cancel_process();
        changeActionBarTitle(product_position);
    }

    /*********************************************************************************************
     Name :- display_list_products
     Input :- Pass product position as int
     Description :- Check condition accordingly dispaly list of products
     *******************************************************************************************/
    public  ArrayList<AllProduct_getList.Product_View_Obj> display_list_products(int product_position){

        // set adapter for listview and add list of products into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts = new ArrayList<AllProduct_getList.Product_View_Obj>();

        // check condition value getting from sub_type accordingly condition will perform action
        if (value.equals("None")) {
            listOfProducts = display_list_and_images_none_type(product_position);

        } else if (value.equals("ISI Accessories")) {
            listOfProducts = display_list_and_image_isi_accessories_type(product_position);

        } else if(value.equals("LX Cover and Base Plates")) {
            listOfProducts = display_list_and_images_lx_cover_type(product_position);

        }  else if(value.equals("NON_ISI Accessories")){
            listOfProducts = display_list_and_images_non_isi_accessories_type(product_position);

        } else if(value.equals("Flex Box")){
            listOfProducts = display_list_and_images_flex_box_type(product_position);

        }else if(value.equals("Power Guard")) {
            listOfProducts = display_list_and_images_power_guard_type(product_position);

        }else  if(value.equals("Spike Guard")) {
            listOfProducts = display_list_and_images_spike_guard_type(product_position);

        } else if(value.equals("Eva Edge Range")){
            listOfProducts = display_list_and_images_eva_edge_plate_type(product_position);
        }
        return listOfProducts;
    }
    /*****************************************************************************************
     Name :-changeActionBarTitle
     Input :- pass position of current item from gridview
     Description :- Changes action bar title using method getActionBar() of each product
     item in gridview to another activity.
     *****************************************************************************************/
    public  void changeActionBarTitle(int position_product) {

        /************************************************************************************
         --> Check condition on which position user click compare to position_product and
         display title name accordingly
         ************************************************************************************/
        if (value.equals("None")) {
            display_action_bar_title_none_type(position_product);
        }else if (value.equals("ISI Accessories")) {
            display_action_bar_title_isi_accessories_type(position_product);
        } else if (value.equals("LX Cover and Base Plates")) {
            display_action_bar_title_lx_cover_type(position_product);
        } else if (value.equals("NON_ISI Accessories")) {
            display_action_bar_title_non_isi_accessories_type(position_product);
        } else if (value.equals("Flex Box")) {
            display_action_bar_title_flex_box_type(position_product);
        } else if (value.equals("Power Guard")) {
            display_action_bar_title_power_guard_type(position_product);
        } else if (value.equals("Spike Guard")) {
            display_action_bar_title_spike_guard_type(position_product);
        } else if(value.equals("Eva Edge Range")){
            display_action_bar_title_eva_edge_plates_type(position_product);
        }
    }

    /*********************************************************************************************
     Name:- color_spinner
     Description:- Show dropdown box to choose color from user.
     ********************************************************************************************/
    public void color_spinner(int product_position) {

        TextView color_chooser_tv = (TextView) findViewById(R.id.color_chooser_textView);

        // create spinner by given id in xml file
        product_color_spinner = (Spinner) findViewById(R.id.colour_chooser_spinner);
        Resources r = getResources();
        ArrayAdapter Product_color_spinner = null;

        if(value.equals("None")) {
            Product_color_spinner = product_spinner_none_type(product_position);

        } else if(value.equals("ISI Accessories")) {
            Product_color_spinner = product_spinner_isi_accessories(product_position);

        } else if(value.equals("LX Cover and Base Plates")) {
            Product_color_spinner = product_spinner_lx_cover_base(product_position);

        }   else if(value.equals("NON_ISI Accessories")){
            Product_color_spinner = product_spinner_non_isi_accessories(product_position);

        } else if(value.equals("Flex Box")){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PSG_COLOR, android.R.layout.simple_spinner_dropdown_item);

                /* Hide spinner */
            color_chooser_tv.setVisibility(View.INVISIBLE);
            product_color_spinner .setVisibility(View.INVISIBLE);

        } else if(value.equals("Power Guard")) {

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PSG_COLOR, android.R.layout.simple_spinner_dropdown_item);

            /* Hide Spinner */
            color_chooser_tv.setVisibility(View.INVISIBLE);
            product_color_spinner .setVisibility(View.INVISIBLE);

        } else if( value.equals("Spike Guard")){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PSG_COLOR, android.R.layout.simple_spinner_dropdown_item);
            /* Hide Spinner */
            color_chooser_tv.setVisibility(View.INVISIBLE);
            product_color_spinner .setVisibility(View.INVISIBLE);

        } else if(value.equals("Eva Edge Range")){
            Product_color_spinner = product_spinner_eva_edge_choose_color(product_position);

        }
        // set adapter for spinner to add color in spinner
        product_color_spinner.setAdapter(Product_color_spinner);
        // set setOnItemSelectedListener to spinner
        product_color_spinner.setOnItemSelectedListener(Product_Details_Page_Type_1.this);
    }

    /*********************************************************************************************
     Name:- scroll
     Description:- set onTouchListener on relative layout and on listview to make scrollable.
     *********************************************************************************************/
    public  void scroll() {

        // set setOnTouchListener to scroll page
        parent_scrollview.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                // set touch on cableTie listview scrollbar
                findViewById(R.id.Product_listView).getParent()
                        .requestDisallowInterceptTouchEvent(false);

                // return boolean value
                return false;
            }
        });
    }
    /*********************************************************************************************/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}

    /*********************************************************************************************
     Name:- ViewHolder
     Description:- viewholder class holdes the things to display in listview
     ********************************************************************************************/
    static class Product_ViewHolder {

        // declare textview object
        protected TextView text;
        // declare checkbox object
        protected CheckBox checkbox;
    }

    /********************************************************************************************
     Name:- myListAdapter
     Description:- This class extends BaseAdapter to add all things into Listview one bye one
     through implementing methods of BaseAdapter.
     *********************************************************************************************/
    public class myListAdapter extends BaseAdapter {

        // declare context variable
        Context c;

        // declare araylist<Product_View_Obj> object as pipes
        ArrayList<AllProduct_getList.Product_View_Obj> product_items;

        // declare  array colorList as datatype int
        int colorlist[];

        // create constructor
        myListAdapter(Context c, ArrayList<AllProduct_getList.Product_View_Obj> p,int[] colors) {
            this.c = c;
            this.product_items = p;
            this.colorlist = colors;
        }

        @Override
        public int getCount() {
            return product_items.size();
        }

        @Override
        public Object getItem(int position) {
            return product_items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // Declare ViewHolder object
            final Product_ViewHolder viewHolder = new Product_ViewHolder();

            /******************************************************************************
             Name:- LayoutInflater
             Description:- LayoutInflater call another layout to set item into listview.
             ******************************************************************************/
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // View object get layout through LayoutInflater object as inflater
            convertView = inflater.inflate(R.layout.activity_pipelist_single_item, parent, false);

            // Through viewHolder object get Textview in a listview dynamically
            viewHolder.text = (TextView) convertView.findViewById(R.id.productsName_textView_common);

            // Through viewHolder object get checkbox in a listview dynamically
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox_common);

            // set tag for View object
            convertView.setTag(viewHolder);

            // getting mode value of position into colorPos
            int colorPos = position % colors.length;

            // set background color to each  mode value
            convertView.setBackgroundColor(colors[colorPos]);

            // getting paddingPos value by moding position to bottom_padding
            int paddingPos = position % bottom_padding.length;

            // set padding in listview item
            convertView.setPadding(0,0,0,bottom_padding[paddingPos]);

            // set OnCheckedChangeListener on checkbox
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    // check whether check status in true or false
                    allProduct_getList.list.get(position).setCb_status(buttonView.isChecked());
                }
            });

            // set tag to viewHolder
            convertView.setTag(viewHolder);

            // create textview through id and add product item into textview through viewHolder
            convertView.setTag(R.id.productsName_textView_common, viewHolder.text);

            // create checkbox through id
            convertView.setTag(R.id.checkBox_common, viewHolder.checkbox);

            // set text into textview
            viewHolder.text.setText(product_items.get(position).getInfo());

            // set checked value to checkbox
            viewHolder.checkbox.setChecked(product_items.get(position).isCb_status());

            // call scroll function
            scroll();

            // on view object set onTouchListener for scrolling listview
            convertView.setOnTouchListener(new View.OnTouchListener()
            {
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of
                    // child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            // return View object
            return convertView;
        }

        /****************************************************************************************
         Name :- getPipeList()
         Output :- ArrayList<Product_View_Obj> of return list of product item
         Description :- function contains arraylist of cableTie items and
         return through cable_ties object
         ****************************************************************************************/
        public ArrayList<AllProduct_getList.Product_View_Obj> getPipeList(){
            return product_items;
        }
    }

    /********************************************************************************************
     Name :- addtoCart_process
     Description :- on click of addtoCart_process button which ever item will be selected it will
     be add into cart.
     ********************************************************************************************/
    public void addtoCart_process(final int product_position) {

        // set OnClickListener on addtoCart_btn button
        addToCart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart_flag = true;
                // Define String Variable
                String product_name = "";
                // initialize counter
                int items_count = 0;
                /* get product name according to product item selected by user */
                product_name = Make_string_particular_product(product_position);
                create_final_product_string(items_count, product_position, product_name);
                // function will be call to clear_data
                clear_data();
            }
        });
    }

    /********************************************************************************************
     Name :- addtoMyList_process
     Description :- on click of addtoMyList_process button which ever item will be selected it will
     be add into list selected.
     ********************************************************************************************/
    public void addtoMyList_process(final int product_position) {

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current agent id
        final String AgentID = loginSharedPref.getString("ID", "");

        // set OnClickListener on addtoCart_btn button
        addToMyList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToCart_flag = false;
                // Define String Variable
                String product_name = "";
                // initialize counter
                int items_count = 0;
                /* get product name according to product item selected by user */
                product_name = Make_string_particular_product(product_position);
                /* create final string according to product selected by user  */
                create_final_product_string(items_count, product_position, product_name);
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
    private int selected = 0;
    public void display_list_names(final ArrayList<String> listNames, final String AgentID){

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
                        // insert into local db of list_items table
                        insert_values_in_list_items_local_db(AgentID, selected_ln,list_incr);
                        // clear array list
                        product_ids_for_list.clear();
                    } else{
                        Toast.makeText(Product_Details_Page_Type_1.this,
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
        // define content values object
        ContentValues copy_values_int_list_item = new ContentValues();
        // set values of list items
        copy_values_int_list_item.put(GlobalProvider.LI_List_Name, selected_ln);
        copy_values_int_list_item.put(GlobalProvider.LI_Increment,list_incr);
        copy_values_int_list_item.put(GlobalProvider.LI_Login_User_Id,AgentID);
        copy_values_int_list_item.put(GlobalProvider.LI_deleted,"0");

        // loop on array of prod ids selected by user
        for(String curr_prod_ids : product_ids_for_list){

            // set prod ids into content values object
            copy_values_int_list_item.put(GlobalProvider.LI_Product_ID, curr_prod_ids);
            try {
                if(!non_deleted_prod_id.contains(curr_prod_ids) || non_deleted_prod_id.isEmpty()) {

                    // firing query to insert into local db of list item
                    Uri uri = getContentResolver().insert(GlobalProvider.CONTENT_URI_List_Item,
                            copy_values_int_list_item);

                    Toast.makeText(this," Added data successfully in " + selected_ln ,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"Product is already exists in " + selected_ln,Toast.LENGTH_LONG).show();
                }
            } catch(Exception e){
               e.printStackTrace();
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
        return list_incr;
    }

    /*********************************************************************************************
     Name :- Make_string_particular_product
     Input :- pass product_name as string,product_position as in
     Description :- Define the product names depending on which item in clicked or
                    which position is clicked on the products page it will make
                    string of particular product.
     **********************************************************************************************/
    public String Make_string_particular_product(int product_position){

        String product_name = "";

        if (value.equals("None")) {
            product_name = make_string_of_product_none_type(product_position);

        } else if (value.equals("ISI Accessories")) {
            product_name = make_string_of_product_isi_accessories_type(product_position);

        } else if (value.equals("LX Cover and Base Plates")) {
            product_name = make_string_of_product_lx_cover_base_type(product_position);

        } else if (value.equals("NON_ISI Accessories")) {
            product_name = make_string_of_product_non_isi_accessories_type(product_position);

        } else if (value.equals("Power Guard")) {
            product_name = "POWERGUARD;";

        } else if (value.equals("Spike Guard")) {
            product_name = "SPIKEGUARD;";

        } else if (value.equals("Flex Box")) {
            product_name = "FLEX BOX;";
        } else if(value.equals("Eva Edge Range")){
            product_name = make_string_of_product_eva_edge_type(product_position);
        }
        return product_name;
    }

    /*********************************************************************************************
     Name :- create_final_product_string
     Input :- pass items_counts as int,product_position as int,product_name as string
     Description :- Create a final string of product, which item selected by user.
     *********************************************************************************************/
    public void create_final_product_string (int items_count, int product_position, String product_name){

        Resources r = getResources();

        // declare string variable
        String selectedPipes = "";

        // getting data from adapter to pipeListAdapter object through loop
        for (AllProduct_getList.Product_View_Obj p : productListAdapter.getPipeList()) {

            Order_Detail_Object orderDetailObject = null;

            // check condition of checkbox status
            if (p.isCb_status()) {

                // increment counter
                items_count++;

                // concat value getting from Product_View_Obj object as p
                selectedPipes = selectedPipes.concat(p.getInfo() + "; ");

                // get cableTie info into string
                String info = p.getInfo();

                if (value.equals("None")) {

                    if (product_position == r.getInteger(R.integer.ISI_PIPE_POSITION) ||
                            product_position == r.getInteger(R.integer.NON_ISI_PIPE_POSITION)) {
                        info = p.getInfo().replace(" ", ";");
                    }
                    orderDetailObject = new Order_Detail_Object(product_name +
                            info + ";" + product_color_spinner.getSelectedItem());

                } else if (value.equals("ISI Accessories")) {

                    String info_str = "";
                    if(product_position == r.getInteger(R.integer.PVC_JUNCTION_BOX_POSITION) ||
                            product_position == r.getInteger(R.integer.PVC_DEEP_JUNCTION_BOX_POSITION) ||
                            product_position == r.getInteger(R.integer.PVC_NORMAL_JUNTION_BOX_POSITION)) {
                        String PVC_str = p.getInfo();
                        String[] PVC_array_str = PVC_str.split(" ");

                        info_str = info_str.concat(PVC_array_str[0] + " " +
                                PVC_array_str[1] + " " + PVC_array_str[2] + ";" + PVC_array_str[3] + " " + PVC_array_str[4]);
                    } else {
                        info_str = p.getInfo();
                    }

                    orderDetailObject = new Order_Detail_Object(product_name +
                            info_str + ";" + product_color_spinner.getSelectedItem());

                } else if (value.equals("NON_ISI Accessories")) {

                    String non_isi_pvc_str = p.getInfo();
                    String[] non_isi_pvc_array = non_isi_pvc_str.split(" ");
                    String info_str = new String();
                    String prod_color = product_color_spinner.getSelectedItem().toString();

                    if (product_position == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION)) {
                            info_str = info_str.concat(non_isi_pvc_array[0] + " " + non_isi_pvc_array[1] + ";"
                                    + non_isi_pvc_array[2] + " " + non_isi_pvc_array[3]);

                            orderDetailObject = new Order_Detail_Object(product_name +
                                    info_str + ";" + product_color_spinner.getSelectedItem());
                    } else if(product_position == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION)){

                        info_str = info_str.concat(non_isi_pvc_array[0] + " " + non_isi_pvc_array[1] + ";"
                                + non_isi_pvc_array[2] + " " + non_isi_pvc_array[3]);

                        orderDetailObject = new Order_Detail_Object(product_name +
                                info_str + ";" + product_color_spinner.getSelectedItem());

                    } else {
                            info_str = info_str.concat(non_isi_pvc_array[0] + " " + non_isi_pvc_array[1]);

                            orderDetailObject = new Order_Detail_Object(product_name +
                                    info_str + ";" + product_color_spinner.getSelectedItem());
                    }
                } else if (value.equals("Power Guard")) {
                    orderDetailObject = create_power_guard_string(product_position, product_name, info);

                } else if (value.equals("Spike Guard")) {
                    orderDetailObject = create_Spike_guard_string(product_position, product_name, info);

                } else if (value.equals("Flex Box")) {
                    orderDetailObject = create_flex_box_string(product_position, product_name, info);

                } else {
                    // get data from haspMap of Order_Detail_Object object as orderDetailObject from database
                    orderDetailObject = new Order_Detail_Object(product_name + info
                            + ";" + product_color_spinner.getSelectedItem());
                }
                boolean value = Utility.check_values_nonISI(orderDetailObject.getItem(),getApplication());
                 if(value) {
                     add_product_string_in_finalOrder_hashmap(orderDetailObject);
                 }
            }
        }
        if(addToCart_flag) {
            Toast_message(items_count);
        }
    }

    /*********************************************************************************************
     Name :- add_product_string_in_finalOrder_hashmap
     Input :- pass Order_Detail_Object's object and Globals's object
     Description :- Get Id from of particular item selected by user from globals's
                    ProductHashmap and add into globals's finalorder hashmap.
     *********************************************************************************************/
    public void add_product_string_in_finalOrder_hashmap(Order_Detail_Object orderDetailObject){

        // declare globals variable
        Globals g = (Globals) getApplication();
        // get id from hashMap into string variable
        String ProductId = g.getProductsHashMap().get(orderDetailObject.getItem()).getID();

        if(addToCart_flag) {
            // check condition that contains productId
            if (g.getFinal_order().containsKey(ProductId)) {

                // get quantity from g.getFinal_order() into int variable
                int qty = Integer.parseInt(g.getFinal_order().get(ProductId).getQuantity());
                // increment qty
                qty = qty + 1;
                // set quantity into final_order of hashmap
                g.getFinal_order().get(ProductId).setQuantity(String.valueOf(qty));

            } else {
                // put id from  orderDetailObject to cable_productID from hashMap
                g.getFinal_order().put(ProductId, orderDetailObject);
            }
        } else{
            product_ids_for_list.add(ProductId);
        }
    }

    /*********************************************************************************************
     Name :- Toast_message
     Input :- pass items_count as int
     Description :- Toast message will be show by user.
     *********************************************************************************************/
    public void Toast_message(int items_count){

        // check condition if item is selected or not
        if (items_count == 0) {

            // if no item is selected then toast message will be shown
            Toast.makeText(Product_Details_Page_Type_1.this,
                    "Select some items from above list", Toast.LENGTH_LONG).show();
        } else {

            // toast message will be shown what ever user selected from listview
            Toast.makeText(Product_Details_Page_Type_1.this, "Product added to cart",
                    Toast.LENGTH_LONG).show();
        }
    }

    /*****************************************************************************************
     Name :- cancel_process
     Description :- onclick on cancel button all data will be clear
     ****************************************************************************************/
    public void cancel_process() {

        // set onclickListener on cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // function call to clear all fields
                clear_data();

                // toast message will be show to user clear data
                Toast.makeText(Product_Details_Page_Type_1.this, "Clear data", Toast.LENGTH_LONG).show();
                productListAdapter.notifyDataSetChanged();
            }
        });
    }

    /*************************************************************************************
     Name :- clear_data
     Description :- clear all fields data
     *************************************************************************************/
    public void clear_data() {

        // set selection 0 to spinner
        product_color_spinner.setSelection(0);

        // using loop all check will be set to be false
        for(AllProduct_getList.Product_View_Obj a : allProduct_getList.list) {
            // set checkbox status as false
            a.setCb_status(false);
        }

        // notify to checkbox adapter in listview
        productListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product__details__page__type_1, menu);
        return true;
    }

    /******************************************************************************
     Name :- callAddcartPage
     Description :- function call another activity on click of add cart icon
     ******************************************************************************/
    public  void callAddcartPage(MenuItem item) {

        // declare Intent object to call another activity
        Intent calladdCartPage = new Intent(this,Order_Details.class);

        // startActivity by calling intent object
        startActivity(calladdCartPage);
    }

    /*********************************************************************************************
     Name :- display_list_and_images_none_type
     Input :- Pass product position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of products which belongs to None type.
     *********************************************************************************************/
    public  ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_none_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts = new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.ISI_PIPE_POSITION)) {
            listOfProducts = allProduct_getList.get_ISI_PipeList();
            iv.setBackgroundResource(R.drawable.isi_non_isi_pipe);

        } else if (product_position == r.getInteger(R.integer.NON_ISI_PIPE_POSITION)) {
            listOfProducts = allProduct_getList.get_NON_ISI_PipeList();
            iv.setBackgroundResource(R.drawable.isi_non_isi_pipe);

        } else if (product_position == r.getInteger(R.integer.CABLETIE_POSITION)) {
            listOfProducts = allProduct_getList.getCableTieslist();
            iv.setBackgroundResource(R.drawable.cable_tie);

        } else if(product_position == r.getInteger(R.integer.LX_PLATES_RANGE_WHITE_POSITION)) {
            listOfProducts = allProduct_getList.getLXPlates_White_CB();
            iv.setBackgroundResource(R.drawable.lx_white_cb);

        } else if(product_position == r.getInteger(R.integer.PLASTIC_SHEET_POSITION)){
            listOfProducts = allProduct_getList.getPlastic_Sheet();
            iv.setBackgroundResource(R.drawable.decorativesheet);

        } else if(product_position == r.getInteger(R.integer.ROYAL_PLATE_POSITION)) {
            listOfProducts = allProduct_getList.getRoyal_Plates();
            iv.setBackgroundResource(R.drawable.royalplates_backcover);

        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_WIRE_POSITION)){
            listOfProducts = allProduct_getList.getPVCWireElectrical_and_FRLS();
            iv.setBackgroundResource(R.drawable.wire_back_cover);

        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_FRLS_POSITION)) {
            listOfProducts = allProduct_getList.getPVCWireElectrical_and_FRLS();
            iv.setBackgroundResource(R.drawable.wire_back_cover);

        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_GOLD_POSITION)){
            listOfProducts = allProduct_getList.getPVC_GOLD_WIRE();
            iv.setBackgroundResource(R.drawable.wire_back_cover);

        } else if(product_position == r.getInteger(R.integer.CABLE_TRAY_POSITION)){
            listOfProducts = allProduct_getList.getCable_Tray();
            iv.setBackgroundResource(R.drawable.cable_tray);

        }
        return listOfProducts;
    }


    /**********************************************************************************************
     Name :- display_list_and_image_isi_accessories_type
     Input :- Pass product position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of ISI Accessories Products
     *********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_image_isi_accessories_type(int product_position) {

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts = new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.PVC_JUNCTION_BOX_POSITION)) {
            listOfProducts = allProduct_getList.getPVCJunctionBox();
            iv.setBackgroundResource(R.drawable.pt_501);
        } else if (product_position == r.getInteger(R.integer.PVC_DEEP_JUNCTION_BOX_POSITION)) {
            listOfProducts = allProduct_getList.getPVCDeepJunctionBox();
            iv.setBackgroundResource(R.drawable.pt_509);
        } else if (product_position == r.getInteger(R.integer.PVC_NORMAL_JUNTION_BOX_POSITION)) {
            listOfProducts = allProduct_getList.getPVCNormalJuntionBox();
            iv.setBackgroundResource(R.drawable.pt_501);
        } else if(product_position == r.getInteger(R.integer.PVC_ISI_WALL_BEND_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_WALL_BEND_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_508);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_FABRICATED_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_508);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_NORMAL_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_FABRICATED_NORMAL_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_508);
        } else if(product_position == r.getInteger(R.integer.PVC_ISI_COUPLER_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_COUPLER_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_504);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_SADDLE_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_SADDLE_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_505);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_ELBOW_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_ELBOW_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_502);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_TEE_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_TEE_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_503);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FAN_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_FAN_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_511);
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_EXTRA_LID_ACCESSORIES_POSITION)){
            listOfProducts = allProduct_getList.getISI_EXTRA_ACCESSORIES();
            iv.setBackgroundResource(R.drawable.pt_508);
        }
        return listOfProducts;
    }
    /*********************************************************************************************
     Name :- display_list_and_images_lx_cover_type
     Input :- Pass product position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of LX Covers and Base Plates product
     *********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_lx_cover_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_NATURAL_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.natural_lx_cover_big);

        } if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_METALIC_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.metallic_cover_big);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_BLACK_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.black_lx_cover_big);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_WOODEN_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.wooden_cover_big);
        }

        // LX Base Plates
        if(product_position == r.getInteger(R.integer.LX_BASE_NATURAL_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.natural_base_big);
        }
        if(product_position == r.getInteger(R.integer.LX_BASE_METALIC_TOUCH_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.metallic_base_big);
        }
        if(product_position == r.getInteger(R.integer.LX_BASE_SILKY_BLACK_POSITION)){
            listOfProducts = allProduct_getList.getLXPlates();
            iv.setBackgroundResource(R.drawable.black_base_big);
        }

        return listOfProducts;
    }
    /*********************************************************************************************
     Name :- display_list_and_images_non_isi_accessories_type
     Input :- Pass product position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of Non ISI Accessories product
     *********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_non_isi_accessories_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Junction_Deep_Box__Accessories();
            iv.setBackgroundResource(R.drawable.pt_501);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Deep_Box__Accessories();
            iv.setBackgroundResource(R.drawable.pt_509);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_ELBOW_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_502);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_TEE_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_503);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_COUPLING_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories_Of_PCCB();
            iv.setBackgroundResource(R.drawable.pt_504);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_PATTI_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories_Of_PCCB();
            iv.setBackgroundResource(R.drawable.pt_505);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_PUSH_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_506);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_CLIP_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories_Of_PCCB();
            iv.setBackgroundResource(R.drawable.pt_519);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_BEND_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories_Of_PCCB();
            iv.setBackgroundResource(R.drawable.pt_508);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_EXTENSION_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_510);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_FAN_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_511);
        }
        else if(product_position == r.getInteger(R.integer.NON_ISI_FAN_WH_POSITION)){
            listOfProducts = allProduct_getList.getNon_ISI_Other_Accessories();
            iv.setBackgroundResource(R.drawable.pt_512);

        } else if(product_position == r.getInteger(R.integer.NON_ISI_GALAXY_LOCK_POSITION)){
            listOfProducts = allProduct_getList.getNON_ISI_Galaxy_Lock();
            iv.setBackgroundResource(R.drawable.pt_260);
        }
        return listOfProducts;
    }

    /********************************************************************************************
     Name :- display_list_and_images_flex_box_type
     Input :- Pass position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of Flex Box product
     ********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_flex_box_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();
        // create resources object
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.ORANGE_FB_POSITION)) {
            listOfProducts = allProduct_getList.getOrange_FB();
            iv.setBackgroundResource(R.drawable.pf_202);
        }else if(product_position == r.getInteger(R.integer.QUEEN_FB_POSITION)) {
            listOfProducts = allProduct_getList.getQueen_FB();
            iv.setBackgroundResource(R.drawable.pf_204);
        }else if(product_position == r.getInteger(R.integer.CHARMS_FB_POSITION)) {
            listOfProducts = allProduct_getList.getCharm_FB();
            iv.setBackgroundResource(R.drawable.pf_206);
        }else if(product_position == r.getInteger(R.integer.SUPER_CLASSIC_FB_POSITION)) {
            listOfProducts = allProduct_getList.getSuperClassic_FB();
            iv.setBackgroundResource(R.drawable.pf_207);
        }else if(product_position == r.getInteger(R.integer.KING_FB)) {
            listOfProducts = allProduct_getList.getKing_FB();
            iv.setBackgroundResource(R.drawable.pf_208);
        }else if(product_position == r.getInteger(R.integer.BADSHAH_FB)) {
            listOfProducts = allProduct_getList.getBadshah_FB();
            iv.setBackgroundResource(R.drawable.pf_209);
        }else if(product_position == r.getInteger(R.integer.ORANGE_FB_WITHOUT_HANDLE_FB)) {
            listOfProducts = allProduct_getList.getOrange_FB_WithOut_Handle();
            iv.setBackgroundResource(R.drawable.pf_211);
        }else if(product_position == r.getInteger(R.integer.APPLE_FB)) {
            listOfProducts = allProduct_getList.getApple_FB();
            iv.setBackgroundResource(R.drawable.pf_214);
        }else if(product_position == r.getInteger(R.integer.BOSS_FB)) {
            listOfProducts = allProduct_getList.getBoss_FB();
            iv.setBackgroundResource(R.drawable.pt_203);
        }else if(product_position == r.getInteger(R.integer.JEWEL_FB)) {
            listOfProducts = allProduct_getList.getJewel_FB();
            iv.setBackgroundResource(R.drawable.pf_212);
        }else if(product_position == r.getInteger(R.integer.CLASSIC_FB)) {
            listOfProducts = allProduct_getList.getClassic_FB();
            iv.setBackgroundResource(R.drawable.pf_215);
        }else if(product_position == r.getInteger(R.integer.AP_FB)) {
            listOfProducts = allProduct_getList.getAP_FB();
            iv.setBackgroundResource(R.drawable.pf_205);
        } else if(product_position == r.getInteger(R.integer.DELUXE_FB)){
            listOfProducts = allProduct_getList.getDeluex_FB();
            iv.setBackgroundResource(R.drawable.pf_216);
        }else if(product_position == r.getInteger(R.integer.ROYAL_FB)){
            listOfProducts = allProduct_getList.getRoyal_FB();
            iv.setBackgroundResource(R.drawable.pf_217);
        }else if(product_position == r.getInteger(R.integer.KING_AMP_FB)){
            listOfProducts = allProduct_getList.getKing_FB_AMP();
            iv.setBackgroundResource(R.drawable.pf_221);
        }else if(product_position == r.getInteger(R.integer.BLACKBERRY_FB)){
            listOfProducts = allProduct_getList.getBlackberry_FB_WithOut_and_With_Handle();
            iv.setBackgroundResource(R.drawable.pf_233);
        } else if(product_position == r.getInteger(R.integer.NANO_FB)){
            listOfProducts = allProduct_getList.getNano_FB();
            iv.setBackgroundResource(R.drawable.pf_234);
        } else if(product_position == r.getInteger(R.integer.BLACKBERRY_WH_FB)){
            listOfProducts = allProduct_getList.getBlackberry_FB_WithOut_and_With_Handle();
            iv.setBackgroundResource(R.drawable.pf_235);
        } else if(product_position == r.getInteger(R.integer.INNOVA_FB)){
            listOfProducts = allProduct_getList.getInnova_FB();
            iv.setBackgroundResource(R.drawable.pf_236);
        } else if(product_position == r.getInteger(R.integer.RANGOLI_FB)){
            listOfProducts = allProduct_getList.getRangoli_FB();
            iv.setBackgroundResource(R.drawable.pf_237);
        } else if(product_position == r.getInteger(R.integer.GALAXY_FB)){
            listOfProducts = allProduct_getList.getGalaxy_FB();
            iv.setBackgroundResource(R.drawable.pf_238);
        } else if(product_position == r.getInteger(R.integer.WHITE_CIRTUS_FB)){
            listOfProducts = allProduct_getList.getWhite_Cirtus_FB();
            iv.setBackgroundResource(R.drawable.pf_239);
        } else if(product_position == r.getInteger(R.integer.TEJAS_FB)){
            listOfProducts = allProduct_getList.getTejas_FB();
            iv.setBackgroundResource(R.drawable.pf_240);
        } else if(product_position == r.getInteger(R.integer.DIYA_POSITION)){
            listOfProducts = allProduct_getList.getDiya();
            iv.setBackgroundResource(R.drawable.pf_1213);
        } else if(product_position == r.getInteger(R.integer.GREY_POSITION)){
            listOfProducts = allProduct_getList.getGrey_FB();
            iv.setBackgroundResource(R.drawable.superclassicgrey_big);
        } else if(product_position == r.getInteger(R.integer.DELTA_POSITION)){
            listOfProducts = allProduct_getList.getDelta_FB();
            iv.setBackgroundResource(R.drawable.delta_big);
        }
        return listOfProducts;
    }

    /*********************************************************************************************
     Name :- display_list_and_images_power_guard_type
     Input :- Pass position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of Power guard product
     *********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_power_guard_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.ZEL_PG_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.zelpowerguard_big);

        }else if (product_position == r.getInteger(R.integer.SPLENDOR_PG_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.splendorpowerguard_big);

        } else if (product_position == r.getInteger(R.integer.NANO_PG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.nanopowerguard4_4_big);

        } else if (product_position == r.getInteger(R.integer.NANO_PG_4S4OPOSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.nanopowerguard4_4_big);

        } else if (product_position == r.getInteger(R.integer.NANO_PG_1S6OPOSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.nanopowerguard6_big);

        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S2O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.diya2_big);

        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S3O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.diya3_big);

        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.diya4_big);

        } else if (product_position == r.getInteger(R.integer.DIYA_PG_4S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.diya4_4_big);

        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.diya6_big);

        } else if (product_position == r.getInteger(R.integer.POD_PG_1S2O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.pod2_big);

        } else if (product_position == r.getInteger(R.integer.POD_PG_1S3O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.pod3_big);

        } else if (product_position == r.getInteger(R.integer.POD_PG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.pod4_big);

        } else if (product_position == r.getInteger(R.integer.POD_PG_4S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.pod4_4_big);

        } else if (product_position == r.getInteger(R.integer.POD_PG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.pod6_big);

        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S2O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.mint2_big);

        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S3O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.mint3_big);

        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.mint4_big);

        } else if (product_position == r.getInteger(R.integer.MINT_PG_4S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.mint4_4_big);

        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.mint6_big);

        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S2O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.charms2_big);

        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S3O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.charms3_big);

        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.charms4_big);

        } else if (product_position == r.getInteger(R.integer.CHARM_PG_4S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.charms4_4_big);

        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.charms6_big);

        } else if (product_position == r.getInteger(R.integer.PIVOT_PG_POSITION)){
            listOfProducts = allProduct_getList.getPivot_PG();
            iv.setBackgroundResource(R.drawable.pg_pivot);
        }

        return listOfProducts;
    }

    /*********************************************************************************************
     Name :- display_list_and_images_spike_guard_type
     Input :- Pass position as int
     Output :- Return ArrayList<AllProduct_getList.Product_View_Obj>'s object
     Description :- Get list of Spike guard products
     *********************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_spike_guard_type(int product_position){

        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.ZEL_SG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_226);
        } else if (product_position == r.getInteger(R.integer.ZEL_SG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_227);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_229);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S5O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_229_1);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_229_2);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_4S4O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_230);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_5S5O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_230_1);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_6S6O_POSITION)) {
            listOfProducts = allProduct_getList.getPower_Spike_Guard_ITEM();
            iv.setBackgroundResource(R.drawable.sg_230_2);
        } else if(product_position == r.getInteger(R.integer.ELECTRA_SG_POSITION)){
            listOfProducts = allProduct_getList.getElectra_SG();
            iv.setBackgroundResource(R.drawable.sg_combine);
        }
        return listOfProducts;
    }

    /*******************************************************************************************
     Name:- display_action_bar_title_none_type
     Input :- Pass position as int
     Description :- Change title of action bar which belongs none type
     ******************************************************************************************/
    public void display_action_bar_title_none_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.ISI_PIPE_POSITION)) {
            getActionBar().setTitle("ISI PVC PIPE");
        }
        if (position_product == r.getInteger(R.integer.NON_ISI_PIPE_POSITION)) {
            getActionBar().setTitle("NON ISI PVC PIPE");
        }
        if (position_product == r.getInteger(R.integer.CABLETIE_POSITION)) {
            getActionBar().setTitle("CABLE TIES");
        }
        if (position_product == r.getInteger(R.integer.LX_PLATES_RANGE_WHITE_POSITION)) {
            getActionBar().setTitle("LX MODULAR PLATES");
        }
        if(position_product == r.getInteger(R.integer.PLASTIC_SHEET_POSITION)){
            getActionBar().setTitle("PLASTIC SHEET");
        }
        if(position_product == r.getInteger(R.integer.ROYAL_PLATE_POSITION)){
            getActionBar().setTitle("ROYAL PLATES");
        }
        if(position_product == r.getInteger(R.integer.PVC_ELECTRICAL_WIRE_POSITION)){
            getActionBar().setTitle("PVC WIRES");
        }
        if(position_product == r.getInteger(R.integer.PVC_ELECTRICAL_FRLS_POSITION)){
            getActionBar().setTitle("PVC WIRES-FRLS");
        }
        if(position_product == r.getInteger(R.integer.PVC_ELECTRICAL_GOLD_POSITION)){
            getActionBar().setTitle("PVC GOLD WIRES");
        }
        if(position_product == r.getInteger(R.integer.CABLE_TRAY_POSITION)){
            getActionBar().setTitle("CABLE TRAY");
        }
    }


    /*******************************************************************************************
     Name:- display_list_and_images_eva_edge_plate_type
     Input :- Pass position as int
     Description :- Change title of action bar which belongs none type
     ******************************************************************************************/
    public ArrayList<AllProduct_getList.Product_View_Obj> display_list_and_images_eva_edge_plate_type(int product_position) {


        // set adapter for listview and add colors into listview
        ArrayList<AllProduct_getList.Product_View_Obj> listOfProducts =
                new ArrayList<AllProduct_getList.Product_View_Obj>();

        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.EDGE_PLATE_NATURAL_POSITION)) {
            listOfProducts = allProduct_getList.getEVA_Edge_list();
            iv.setBackgroundResource(R.drawable.edge_swich_cb_natural);

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_METALIC_POSITION)) {
            listOfProducts = allProduct_getList.getEVA_Edge_list();
            iv.setBackgroundResource(R.drawable.metallic_edge);

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_MARBLE_POSITION)) {
            listOfProducts = allProduct_getList.getEVA_Edge_list();
            iv.setBackgroundResource(R.drawable.marble_edge);

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_WOODEN_POSITION)) {
            listOfProducts = allProduct_getList.getEVA_Edge_list();
            iv.setBackgroundResource(R.drawable.wooden_edge_big);
        }

        return  listOfProducts;
    }

    /*******************************************************************************************
     Name:- display_action_bar_title_none_type
     Input :- Pass position as int
     Description :- Change title of action bar which belongs none type
     ******************************************************************************************/
    public void display_action_bar_title_eva_edge_plates_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.EDGE_PLATE_NATURAL_POSITION)) {
            getActionBar().setTitle("NATURAL");
        }
        if (position_product == r.getInteger(R.integer.EDGE_PLATE_METALIC_POSITION)) {
            getActionBar().setTitle("METALLIC");
        }
        if (position_product == r.getInteger(R.integer.EDGE_PLATE_WOODEN_POSITION)) {
            getActionBar().setTitle("WOODEN");
        }
        if (position_product == r.getInteger(R.integer.EDGE_PLATE_MARBLE_POSITION)) {
            getActionBar().setTitle("MARBLE");
        }
    }

    /*******************************************************************************************
     Name:- display_action_bar_title_isi_accessories_type
     Input :- Pass position as int
     Description :- Change action bar title of ISI Accessories product
     ******************************************************************************************/
    public void display_action_bar_title_isi_accessories_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.PVC_JUNCTION_BOX_POSITION)) {
            getActionBar().setTitle("JUNCTION BOXES");
        }
        if (position_product == r.getInteger(R.integer.PVC_DEEP_JUNCTION_BOX_POSITION)) {
            getActionBar().setTitle("DEEP JUNCTION BOXES");
        }
        if (position_product == r.getInteger(R.integer.PVC_NORMAL_JUNTION_BOX_POSITION)) {
            getActionBar().setTitle("NORMAL JUNCTION BOXES");
        }
        if (position_product == r.getInteger(R.integer.PVC_ISI_WALL_BEND_ACCESSORIES_POSITION)) {
            getActionBar().setTitle("WALL BEND");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_FABRICATED_ACCESSORIES_POSITION)){
            getActionBar().setTitle("FABRICATED BEND");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_FABRICATED_NORMAL_ACCESSORIES_POSITION)){
            getActionBar().setTitle("FABRICATED BEND NORMAL");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_COUPLER_ACCESSORIES_POSITION)){
            getActionBar().setTitle("COUPLER ACCESSORIES");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_SADDLE_ACCESSORIES_POSITION)){
            getActionBar().setTitle("SADDLE BASE WITH SCREW");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_ELBOW_ACCESSORIES_POSITION)){
            getActionBar().setTitle("ELBOW ACCESSORIES");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_TEE_ACCESSORIES_POSITION)){
            getActionBar().setTitle("TEE ACCESSORIES");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_FAN_ACCESSORIES_POSITION)){
            getActionBar().setTitle("FAN BOX");
        }
        if(position_product == r.getInteger(R.integer.PVC_ISI_EXTRA_LID_ACCESSORIES_POSITION)){
            getActionBar().setTitle("EXTRA LID WITH SCREWS");
        }

    }
    /*******************************************************************************************
     Name:- display_action_bar_title_lx_cover_type
     Input :- Pass position as int
     Description :- Change action bar title of LX Cover and Base Plate product
     ******************************************************************************************/
    public void  display_action_bar_title_lx_cover_type(int position_product) {

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.LX_COVER_PLATE_NATURAL_POSITION)) {
            getActionBar().setTitle("NATURAL COVER");
        }
        if (position_product == r.getInteger(R.integer.LX_COVER_PLATE_METALIC_POSITION)) {
            getActionBar().setTitle("METALLIC COVER");
        }
        if (position_product == r.getInteger(R.integer.LX_COVER_PLATE_BLACK_POSITION)) {
            getActionBar().setTitle("BLACK COVER");
        }
        if (position_product == r.getInteger(R.integer.LX_COVER_PLATE_WOODEN_POSITION)) {
            getActionBar().setTitle("WOODEN COVER");
        }

        // lx base plate
        if (position_product == r.getInteger(R.integer.LX_BASE_NATURAL_POSITION)) {
            getActionBar().setTitle("NATURAL BASE");
        }
        if (position_product == r.getInteger(R.integer.LX_BASE_METALIC_TOUCH_POSITION)) {
            getActionBar().setTitle("METALIC BASE");
        }
        if (position_product == r.getInteger(R.integer.LX_BASE_SILKY_BLACK_POSITION)) {
            getActionBar().setTitle("BLACK BASE");
        }
    }
    /*******************************************************************************************
     Name:- display_action_bar_title_non_isi_accessories_type
     Input :- Pass position as int
     Description :- Change action bar title of NON ISI Accessories product
     ******************************************************************************************/
    public void  display_action_bar_title_non_isi_accessories_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION)) {
            getActionBar().setTitle("JUNCTION BOX");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION)) {
            getActionBar().setTitle("DEEP JUNCTION BOX");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_ELBOW_POSITION)) {
            getActionBar().setTitle("ELBOW ACCESSORIES");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_TEE_POSITION)) {
            getActionBar().setTitle("TEE ACCESSORIES");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_COUPLING_POSITION)) {
            getActionBar().setTitle("COUPLING ACCESSORIES");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_PATTI_POSITION)) {
            getActionBar().setTitle("PATTI SADDLE");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_PUSH_POSITION)) {
            getActionBar().setTitle("PUSH SADDLE");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_CLIP_POSITION)) {
            getActionBar().setTitle("CLIP ACCESSORIES");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_BEND_POSITION)) {
            getActionBar().setTitle("BEND ACCESSORIES");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_EXTENSION_POSITION)) {
            getActionBar().setTitle("EXTENSION RING");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_FAN_POSITION)) {
            getActionBar().setTitle("FAN BOX");
        } else if (position_product == r.getInteger(R.integer.NON_ISI_FAN_WH_POSITION)) {
            getActionBar().setTitle("FAN BOX(WITH HOOK)");
        } else  if(position_product == r.getInteger(R.integer.NON_ISI_GALAXY_LOCK_POSITION)){
            getActionBar().setTitle("GALAXY LOCK SADDLE");
        }
    }
    /*******************************************************************************************
     Name:- display_action_bar_title_flex_box_type
     Input :- Pass position as int
     Description :- Change action bar title of Flex Box product
     ******************************************************************************************/
    public void display_action_bar_title_flex_box_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.ORANGE_FB_POSITION)) {
            getActionBar().setTitle("ORANGE FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.QUEEN_FB_POSITION)) {
            getActionBar().setTitle("QUEEN FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.CHARMS_FB_POSITION)) {
            getActionBar().setTitle("CHARM FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.SUPER_CLASSIC_FB_POSITION)) {
            getActionBar().setTitle("SUPER CLASSIC FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.KING_FB)) {
            getActionBar().setTitle("KING FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.BADSHAH_FB)) {
            getActionBar().setTitle("BADSHAH FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.ORANGE_FB_WITHOUT_HANDLE_FB)) {
            getActionBar().setTitle("ORANGE FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.APPLE_FB)) {
            getActionBar().setTitle("APPLE FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.BOSS_FB)) {
            getActionBar().setTitle("BOSS FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.JEWEL_FB)) {
            getActionBar().setTitle("JEWEL FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.CLASSIC_FB)) {
            getActionBar().setTitle("CLASSIC FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.AP_FB)) {
            getActionBar().setTitle("A.P FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.DELUXE_FB)) {
            getActionBar().setTitle("DELUXE FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.ROYAL_FB)) {
            getActionBar().setTitle("ROYAL FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.KING_AMP_FB)) {
            getActionBar().setTitle("KING FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.BLACKBERRY_FB)) {
            getActionBar().setTitle("BLACKBERRY FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.NANO_FB)) {
            getActionBar().setTitle("NANO FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.BLACKBERRY_WH_FB)) {
            getActionBar().setTitle("BLACKBERRY FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.INNOVA_FB)) {
            getActionBar().setTitle("INNOVA FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.RANGOLI_FB)) {
            getActionBar().setTitle("RANGOLI FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.GALAXY_FB)) {
            getActionBar().setTitle("GALAXY FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.WHITE_CIRTUS_FB)) {
            getActionBar().setTitle("WHITE CIRTUS FLEX BOX");
        } else if (position_product == r.getInteger(R.integer.TEJAS_FB)) {
            getActionBar().setTitle("TEJAS FLEX BOX");
        } else if(position_product == r.getInteger(R.integer.DIYA_POSITION)){
            getActionBar().setTitle("DIYA FLEX BOX");
        } else if(position_product == r.getInteger(R.integer.GREY_POSITION)){
            getActionBar().setTitle("GREY FLEX BOX");
        } else if(position_product == r.getInteger(R.integer.DELTA_POSITION)){
            getActionBar().setTitle("DELTA FLEX BOX");
        }
    }

    /*******************************************************************************************
     Name:- display_action_bar_title_power_guard_type
     Input :- Pass position as int
     Description :- Change action bar title of Power Guard product
     ******************************************************************************************/
    public void display_action_bar_title_power_guard_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.ZEL_PG_POSITION)) {
            getActionBar().setTitle("ZEL PG");
        } else if (position_product == r.getInteger(R.integer.SPLENDOR_PG_POSITION)) {
            getActionBar().setTitle("SPLENDOR");
        } else if (position_product == r.getInteger(R.integer.NANO_PG_1S4O_POSITION)) {
            getActionBar().setTitle("NANO POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.NANO_PG_4S4OPOSITION)) {
            getActionBar().setTitle("NANO POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.NANO_PG_1S6OPOSITION)) {
            getActionBar().setTitle("NANO POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.DIYA_PG_1S2O_POSITION)) {
            getActionBar().setTitle("DIYA POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.DIYA_PG_1S3O_POSITION)) {
            getActionBar().setTitle("DIYA POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.DIYA_PG_1S4O_POSITION)) {
            getActionBar().setTitle("DIYA POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.DIYA_PG_4S4O_POSITION)) {
            getActionBar().setTitle("DIYA POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.DIYA_PG_1S6O_POSITION)) {
            getActionBar().setTitle("DIYA POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.POD_PG_1S2O_POSITION)) {
            getActionBar().setTitle("POD POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.POD_PG_1S3O_POSITION)) {
            getActionBar().setTitle("POD POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.POD_PG_1S4O_POSITION)) {
            getActionBar().setTitle("POD POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.POD_PG_4S4O_POSITION)) {
            getActionBar().setTitle("POD POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.POD_PG_1S6O_POSITION)) {
            getActionBar().setTitle("POD POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.MINT_PG_1S2O_POSITION)) {
            getActionBar().setTitle("MINT POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.MINT_PG_1S3O_POSITION)) {
            getActionBar().setTitle("MINT POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.MINT_PG_1S4O_POSITION)) {
            getActionBar().setTitle("MINT POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.MINT_PG_4S4O_POSITION)) {
            getActionBar().setTitle("MINT POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.MINT_PG_1S6O_POSITION)) {
            getActionBar().setTitle("MINT POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.CHARM_PG_1S2O_POSITION)) {
            getActionBar().setTitle("CHARM POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.CHARM_PG_1S3O_POSITION)) {
            getActionBar().setTitle("CHARM POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.CHARM_PG_1S4O_POSITION)) {
            getActionBar().setTitle("CHARM POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.CHARM_PG_4S4O_POSITION)) {
            getActionBar().setTitle("CHARM POWER GUARD");
        } else if (position_product == r.getInteger(R.integer.CHARM_PG_1S6O_POSITION)) {
            getActionBar().setTitle("CHARM POWER GUARD");
        } else if(position_product == r.getInteger(R.integer.PIVOT_PG_POSITION)){
            getActionBar().setTitle("PIVOT POWETSTRIP");
        }
    }

    /*******************************************************************************************
     Name:- display_action_bar_title_spike_guard_type
     Input :- Pass position as int
     Description :- Change action bar title of Spike Guard product
     ******************************************************************************************/
    public void display_action_bar_title_spike_guard_type(int position_product){

        // create resources object
        Resources r = getResources();

        if (position_product == r.getInteger(R.integer.ZEL_SG_1S4O_POSITION)) {
            getActionBar().setTitle("ZEL UNIVERAL");
        } else if (position_product == r.getInteger(R.integer.ZEL_SG_1S6O_POSITION)) {
            getActionBar().setTitle("ZEL UNIVERSAL");
        } else if (position_product == r.getInteger(R.integer.UNO_SG_1S4O_POSITION)) {
            getActionBar().setTitle("UNO SPIKE GUARD");
        } else if (position_product == r.getInteger(R.integer.UNO_SG_1S5O_POSITION)) {
            getActionBar().setTitle("UNO SPIKE GUARD");
        } else if (position_product == r.getInteger(R.integer.UNO_SG_1S6O_POSITION)) {
            getActionBar().setTitle("UNO SPIKE GUARD");
        } else if (position_product == r.getInteger(R.integer.PROTO_SG_4S4O_POSITION)) {
            getActionBar().setTitle("PROTO SPIKE GUARD");
        } else if (position_product == r.getInteger(R.integer.PROTO_SG_5S5O_POSITION)) {
            getActionBar().setTitle("PROTO SPIKE GUARD");
        } else if (position_product == r.getInteger(R.integer.PROTO_SG_6S6O_POSITION)) {
            getActionBar().setTitle("PROTO SPIKE GUARD");
        } else if(position_product == r.getInteger(R.integer.ELECTRA_SG_POSITION)){
            getActionBar().setTitle("ELECTRA");
        }
    }
    /***********************************************************************************************
     Name :- make_string_of_product_none_type
     Input :- Pass product position as int
     Output :- return product name as string
     Description :- Create string of particular product as product_name
     when user clicks on gridview which belongs to None type.
     **********************************************************************************************/
    public String make_string_of_product_none_type(int product_position){

        String product_name = "";
        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.ISI_PIPE_POSITION)) {
            product_name = "ISI PVC Pipe;";
        } else if (product_position == r.getInteger(R.integer.NON_ISI_PIPE_POSITION)) {
            product_name = "Non ISI PVC Pipe;";
        } else if (product_position == r.getInteger(R.integer.CABLETIE_POSITION)) {
            product_name = "Cable Tie;";
        } else if(product_position == r.getInteger(R.integer.LX_PLATES_RANGE_WHITE_POSITION)){
            product_name = "LX MODULAR PLATE-BASE & COVER;";
        } else if(product_position == r.getInteger(R.integer.PLASTIC_SHEET_POSITION)){
            product_name = "PLASTIC SHEETS;";
        } else if(product_position == r.getInteger(R.integer.ROYAL_PLATE_POSITION)){
            product_name = "ROYAL PLATES;";
        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_WIRE_POSITION)){
            product_name = "PVC INSULATED ELECTRICAL WIRES;";
        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_FRLS_POSITION)){
            product_name = "PVC INSULATED ELECTRICAL WIRES-FRLS;";
        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_GOLD_POSITION)){
            product_name = "PVC INSULATED ELECTRICAL GOLD WIRES(180 MTRS. ONLY);";
        } else if(product_position == r.getInteger(R.integer.CABLE_TRAY_POSITION)){
            product_name = "CABLE TRAY;";
        }
        return product_name;
    }

    /***********************************************************************************************
     Name :- make_string_of_product_isi_accessories_type
     Input :- Pass product position as int
     Output :- return product name as string
     Description :- Create string of particular product as product_name
     when user clicks on gridview belongs to ISI Accessories product.
     **********************************************************************************************/
    public String make_string_of_product_isi_accessories_type(int product_position){

        String product_name = "";
        // create resources object
        Resources r = getResources();
        if(product_position == r.getInteger(R.integer.PVC_JUNCTION_BOX_POSITION)) {
            product_name = "ISI PVC CONDUIT JUNCTION BOX;";
        }
        if (product_position == r.getInteger(R.integer.PVC_DEEP_JUNCTION_BOX_POSITION)) {
            product_name = "ISI PVC CONDUIT DEEP JUNCTION BOX;";
        }
        if (product_position == r.getInteger(R.integer.PVC_NORMAL_JUNTION_BOX_POSITION)) {
            product_name = "ISI PVC CONDUIT NORMAL JUNCTION BOX;";
        }
        if(product_position == r.getInteger(R.integer.PVC_ISI_WALL_BEND_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT WALL BEND;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT FABRICATED BEND;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_NORMAL_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT FABRICATED BEND NORMAL;";
        } else if(product_position == r.getInteger(R.integer.PVC_ISI_COUPLER_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT COUPLER;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_SADDLE_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT SADDLE BASE WITH SCREW;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_ELBOW_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT ELBOW;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_TEE_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT TEE;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_FAN_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT FAN BOX;";
        }else if(product_position == r.getInteger(R.integer.PVC_ISI_EXTRA_LID_ACCESSORIES_POSITION)){
            product_name = "ISI PVC CONDUIT EXTRA LID WITH SCREW FOR J.BOX;";
        }
        return product_name;
    }
    /***********************************************************************************************
     Name :- make_string_of_product_lx_cover_base_type
     Input :- Pass product position as int
     Output :- return product name as string
     Description :- Create string of particular product as product_name
     when user clicks on gridview belongs to LX cover and Base product.
     **********************************************************************************************/
    public String make_string_of_product_lx_cover_base_type(int product_position){

        String product_name = "";
        // create resources object
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_NATURAL_POSITION) ||
                product_position == r.getInteger(R.integer.LX_COVER_PLATE_METALIC_POSITION)  ||
                product_position == r.getInteger(R.integer.LX_COVER_PLATE_BLACK_POSITION) ||
                product_position == r.getInteger(R.integer.LX_COVER_PLATE_WOODEN_POSITION)) {

            product_name ="LX MODULAR PLATE-COVER;";
        }

        // lx  base plate
        if(product_position == r.getInteger(R.integer.LX_BASE_NATURAL_POSITION) ||
                product_position == r.getInteger(R.integer.LX_BASE_METALIC_TOUCH_POSITION)||
                product_position == r.getInteger(R.integer.LX_BASE_SILKY_BLACK_POSITION)){

            product_name ="LX MODULAR PLATE-BASE;";
        }

        return product_name;
    }

    /***********************************************************************************************
     Name :- make_string_of_product_non_isi_accessories_type
     Input :- Pass product position as int
     Output :- return product name as string
     Description :- Create string of particular product as product_name
     when user clicks on gridview belongs to NON ISI Accessories product.
     **********************************************************************************************/
    public String make_string_of_product_non_isi_accessories_type(int product_position){

        String product_name = "";
        // create resources object
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION)){
            product_name = "NON ISI CONDUIT JUNCTION BOX;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION)){
            product_name = "NON ISI CONDUIT DEEP JUNCTION BOX;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_ELBOW_POSITION)){
            product_name = "NON ISI CONDUIT ELBOW;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_TEE_POSITION)){
            product_name = "NON ISI CONDUIT TEE;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_COUPLING_POSITION)){
            product_name = "NON ISI CONDUIT COUPLING;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_PATTI_POSITION)){
            product_name = "NON ISI CONDUIT PATTI SADDLE;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_PUSH_POSITION)){
            product_name = "NON ISI CONDUIT PUSH SADDLE;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_CLIP_POSITION)){
            product_name = "NON ISI CONDUIT CLIP;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_BEND_POSITION)){
            product_name = "NON ISI CONDUIT BEND;";
        } else  if(product_position == r.getInteger(R.integer.NON_ISI_EXTENSION_POSITION)){
            product_name = "NON ISI CONDUIT EXTENSION RING;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_FAN_POSITION)){
            product_name = "NON ISI CONDUIT FAN BOX;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_FAN_WH_POSITION)){
            product_name = "NON ISI CONDUIT FAN BOX WITH HOOK;";
        } else if(product_position == r.getInteger(R.integer.NON_ISI_GALAXY_LOCK_POSITION)){
            product_name = "NON ISI CONDUIT GALAXY LOCK SADDLE;";
        }
        return product_name;
    }

    /*********************************************************************************************
     Name :- create_power_guard_string
     Input :- pass product_position as int,product_name as string ,info as string
     Output :- return Order_Detail_Object's object
     Description :- Create string of power guard that which item is selected by user.
     *********************************************************************************************/
    public Order_Detail_Object create_power_guard_string(int product_position,String product_name,String info){

        // create resources object
        Resources r = getResources();

        // Define Order_Detail_Object variable
        Order_Detail_Object orderDetailObject = null;

        if (product_position == r.getInteger(R.integer.ZEL_PG_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ZEL 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.SPLENDOR_PG_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "SPLENDOR 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.NANO_PG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "SPLENDOR 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.NANO_PG_4S4OPOSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "NANO 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.NANO_PG_1S6OPOSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "NANO 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S2O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DIYA 1 + 2;" + info);
        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S3O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DIYA 1 + 3;" + info);
        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DIYA 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.DIYA_PG_4S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DIYA 4 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.DIYA_PG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DIYA 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.POD_PG_1S2O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "POD 1 + 2;" + info);
        } else if (product_position == r.getInteger(R.integer.POD_PG_1S3O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "POD 1 + 3;" + info);
        } else if (product_position == r.getInteger(R.integer.POD_PG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "POD 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.POD_PG_4S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "POD 4 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.POD_PG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "POD 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S2O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "MINT 1 + 2;" + info);
        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S3O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "MINT 1 + 3;" + info);
        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "MINT 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.MINT_PG_4S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "MINT 4 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.MINT_PG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "MINT 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S2O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS 1 + 2;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S3O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS 1 + 3;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARM_PG_4S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS 4 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARM_PG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS 1 + 6;" + info);
        } else if(product_position == r.getInteger(R.integer.PIVOT_PG_POSITION)){
            orderDetailObject = new Order_Detail_Object(product_name + "PIVOT POWER STRIP;" + info);
        }
        return orderDetailObject;
    }

    /*********************************************************************************************
     Name :- create_Spike_guard_string
     Input :- pass product_position as int ,product_name as string ,info as string
     Output :- return Order_Detail_Object's object
     Description :- Create string of spike guard that which item is selected by user.
     *********************************************************************************************/
    public Order_Detail_Object create_Spike_guard_string(int product_position, String product_name, String info){

        // create resources object
        Resources r = getResources();
        Order_Detail_Object orderDetailObject = null;

        if (product_position == r.getInteger(R.integer.ZEL_SG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ZEL 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.ZEL_SG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ZEL 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "UNO 1 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S5O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "UNO 1 + 5;" + info);
        } else if (product_position == r.getInteger(R.integer.UNO_SG_1S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "UNO 1 + 6;" + info);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_4S4O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "PROTO 4 + 4;" + info);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_5S5O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "PROTO 5 + 5;" + info);
        } else if (product_position == r.getInteger(R.integer.PROTO_SG_6S6O_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "PROTO 6 + 6;" + info);
        } else if(product_position == r.getInteger(R.integer.ELECTRA_SG_POSITION)){
            orderDetailObject = new Order_Detail_Object(product_name + "ELECTRA;" + info);
        }
        return orderDetailObject;
    }

    /*********************************************************************************************
     Name :- create_flex_box_string
     Input :- pass product_position as int ,product_name as string ,info as string
     Output :- return Order_Detail_Object's object
     Description :- Create string of flex box that which item is selected by user.
     *********************************************************************************************/
    public Order_Detail_Object create_flex_box_string(int product_position, String product_name, String info){

        // create resources object
        Resources r = getResources();
        Order_Detail_Object orderDetailObject = null;

        if (product_position == r.getInteger(R.integer.ORANGE_FB_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ORANGE WITH HANDLE;" + info);
        } else if (product_position == r.getInteger(R.integer.QUEEN_FB_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "QUEEN;" + info);
        } else if (product_position == r.getInteger(R.integer.CHARMS_FB_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CHARMS WITH HANDLE;" + info);
        } else if (product_position == r.getInteger(R.integer.SUPER_CLASSIC_FB_POSITION)) {
            orderDetailObject = new Order_Detail_Object(product_name + "SUPER CLASSIC;" + info);
        } else if (product_position == r.getInteger(R.integer.KING_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "KING;" + info);
        } else if (product_position == r.getInteger(R.integer.BADSHAH_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "BADSHAH;" + info);
        } else if (product_position == r.getInteger(R.integer.ORANGE_FB_WITHOUT_HANDLE_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ORANGE WITHOUT HANDLE;" + info);
        } else if (product_position == r.getInteger(R.integer.APPLE_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "APPLE WITHOUT HANDLE;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.BOSS_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "BOSS WITH HANDLE;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.JEWEL_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "JEWEL WITHOUT HANDLE;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.CLASSIC_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "CLASSIC;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.AP_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "A.P(ALL PURPOSE);" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.DELUXE_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "DELUXE;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.ROYAL_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "ROYAL;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.KING_AMP_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "KING;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.BLACKBERRY_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "BLACKBERRY WITH HANDLE;" + info);
            return orderDetailObject;
        } else if (product_position == r.getInteger(R.integer.NANO_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "NANO;" + info);
        } else if (product_position == r.getInteger(R.integer.BLACKBERRY_WH_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "BLACKBERRY WITHOUT HANDLE;" + info);
        } else if (product_position == r.getInteger(R.integer.INNOVA_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "INNOVA;" + info);
        } else if (product_position == r.getInteger(R.integer.RANGOLI_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "RANGOLI;" + info);
        } else if (product_position == r.getInteger(R.integer.GALAXY_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "GALAXY;" + info);
        } else if (product_position == r.getInteger(R.integer.WHITE_CIRTUS_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "WHITE CITRUS;" + info);
        } else if (product_position == r.getInteger(R.integer.TEJAS_FB)) {
            orderDetailObject = new Order_Detail_Object(product_name + "TEJAS;" + info);
        } else if(product_position == r.getInteger(R.integer.DIYA_POSITION)){
            orderDetailObject =  new Order_Detail_Object(product_name + "DIYA;" + info);
        } else if (product_position == r.getInteger(R.integer.GREY_POSITION)) {
            orderDetailObject =  new Order_Detail_Object(product_name + "GREY;" + info);
        } else if(product_position == r.getInteger(R.integer.DELTA_POSITION)){
            orderDetailObject = new Order_Detail_Object(product_name + "DELTA;" + info);
        }

        return orderDetailObject;
    }

    /*********************************************************************************************
     Name :- product_spinner_none_type
     Input :- Pass product_position as int
     Output :- Return ArrayAdapter'object
     Description :- Display spinner color according to products
     ********************************************************************************************/
    public ArrayAdapter product_spinner_none_type(int product_position){

        ArrayAdapter Product_color_spinner = null;
        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.ISI_PIPE_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_PIPE_POSITION)) {

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.Pipe_color, android.R.layout.simple_spinner_dropdown_item);
        } else if (product_position == r.getInteger(R.integer.CABLETIE_POSITION)) {

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.color_cableTies_product, android.R.layout.simple_spinner_dropdown_item);
        } else if (product_position == r.getInteger(R.integer.LX_PLATES_RANGE_WHITE_POSITION)) {

            Product_color_spinner =ArrayAdapter.createFromResource
                    (this,R.array.LX_Modular_Plate_Color,android.R.layout.simple_spinner_dropdown_item);
        } else if(product_position == r.getInteger(R.integer.PLASTIC_SHEET_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PLASTIC_SHEET_COLOR,android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.ROYAL_PLATE_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.ROYAL_BOARD_COLOR,android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_WIRE_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ELECTRICAL_FRLS_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PVC_ELECTRICAL_WIRES_AND_FRLS_COLORS,android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.PVC_ELECTRICAL_GOLD_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PVC_ELECTRICAL_WIRES_GOLD_COLORS,android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.CABLE_TRAY_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.CABLE_TRAY_COLOR,android.R.layout.simple_spinner_dropdown_item);

        }
        return Product_color_spinner;
    }
    /*********************************************************************************************
     Name :- product_spinner_isi_accessories
     Input :- pass product_position as int
     Description :- show spinner in ISI Accessories product
     *********************************************************************************************/
    public ArrayAdapter product_spinner_isi_accessories(int product_position){

        ArrayAdapter Product_color_spinner = null;
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.PVC_JUNCTION_BOX_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_DEEP_JUNCTION_BOX_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_NORMAL_JUNTION_BOX_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_WALL_BEND_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_FABRICATED_NORMAL_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_COUPLER_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_SADDLE_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_ELBOW_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_TEE_ACCESSORIES_POSITION)||
                product_position == r.getInteger(R.integer.PVC_ISI_FAN_ACCESSORIES_POSITION) ||
                product_position == r.getInteger(R.integer.PVC_ISI_EXTRA_LID_ACCESSORIES_POSITION)) {

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.PVC_Conduit_color,android.R.layout.simple_spinner_dropdown_item);
        }
        return Product_color_spinner;
    }
    /*********************************************************************************************
     Name :- product_spinner_lx_cover_base
     Input :- pass product_position as int
     Description :- Show spinner in lx cover and base product
     *********************************************************************************************/
    public ArrayAdapter product_spinner_lx_cover_base(int product_position){

        ArrayAdapter Product_color_spinner = null;
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_NATURAL_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Cover_Natural_color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_METALIC_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Cover_Metalic_color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_BLACK_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Cover_Black_color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_METALIC_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Cover_Metalic_color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_COVER_PLATE_WOODEN_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Cover_Wooden_color,android.R.layout.simple_spinner_dropdown_item);
        }
        // LX Base Plate
        if(product_position == r.getInteger(R.integer.LX_BASE_NATURAL_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Base_Plate_Natural_Color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_BASE_METALIC_TOUCH_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Base_Plate_Metalic_color,android.R.layout.simple_spinner_dropdown_item);
        }
        if(product_position == r.getInteger(R.integer.LX_BASE_SILKY_BLACK_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.LX_Base_Plate_SilkyBlack_color,android.R.layout.simple_spinner_dropdown_item);
        }
        return Product_color_spinner;
    }
    /*********************************************************************************************
     Name :- product_spinner_non_isi_accessories
     Input :- Pass product_position as int
     Description :- Show spinner in NON ISI Accessories product
     *********************************************************************************************/
    public ArrayAdapter product_spinner_non_isi_accessories(int product_position){

        ArrayAdapter Product_color_spinner = null;
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION)){
            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.NON_ISI_ACCESSORIES_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.NON_ISI_ACCESSORIES_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.NON_ISI_JUNCTION_BOX_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_DEEP_BOX_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_ELBOW_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_TEE_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_COUPLING_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_PATTI_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_PUSH_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_CLIP_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_BEND_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_EXTENSION_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_GALAXY_LOCK_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.NON_ISI_ACCESSORIES_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else  if(product_position == r.getInteger(R.integer.NON_ISI_FAN_POSITION) ||
                product_position == r.getInteger(R.integer.NON_ISI_FAN_WH_POSITION) ) {

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.NON_ISI_ACCESSORIES_FAN_BOX_COLOR, android.R.layout.simple_spinner_dropdown_item);
        }
        return Product_color_spinner;
    }

    /*********************************************************************************************
     Name :- product_spinner_eva_edge_choose_color
     Input :- Pass product_position as int
     Description :- Show spinner in eva edge plates
     *********************************************************************************************/
    public ArrayAdapter product_spinner_eva_edge_choose_color(int product_position){

        ArrayAdapter Product_color_spinner = null;
        Resources r = getResources();

        if(product_position == r.getInteger(R.integer.EDGE_PLATE_NATURAL_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.EVA_EDGE_NATURAL_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.EDGE_PLATE_METALIC_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.EVA_EDGE_METALIC_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else if(product_position == r.getInteger(R.integer.EDGE_PLATE_WOODEN_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.EVA_EDGE_WOODEN_COLOR, android.R.layout.simple_spinner_dropdown_item);

        } else  if(product_position == r.getInteger(R.integer.EDGE_PLATE_MARBLE_POSITION)){

            Product_color_spinner = ArrayAdapter.createFromResource(this,
                    R.array.EVA_EDGE_MARBLE_COLOR, android.R.layout.simple_spinner_dropdown_item);
        }
        return Product_color_spinner;
    }

    /***********************************************************************************************
     Name :- make_string_of_product_eva_edge_type
     Input :- Pass product position as int
     Output :- return product name as string
     Description :- Create string of particular product as product_name
     when user clicks on gridview belongs to eva edge plates product.
     **********************************************************************************************/
    public String make_string_of_product_eva_edge_type(int product_position) {

        String product_name = "";
        // create resources object
        Resources r = getResources();

        if (product_position == r.getInteger(R.integer.EDGE_PLATE_NATURAL_POSITION)) {
            product_name = "EDGE MODULAR PLATE WITH SILVER BORDER;";

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_METALIC_POSITION)) {
            product_name = "EDGE MODULAR PLATE METALIC FINISH WITH SILVER BORDER;";

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_WOODEN_POSITION)) {
            product_name = "EDGE MODULAR PLATE WOODEN FINISH WITH SILVER BORDER;";

        } else if (product_position == r.getInteger(R.integer.EDGE_PLATE_MARBLE_POSITION)) {
            product_name = "EDGE MODULAR PLATE MARBLE FINISH WITH SILVER BORDER;";
        }
        return product_name;
    }
    /********************************************************************************************/



}

