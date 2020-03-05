package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Products_Page extends Activity {

    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    GridView Products_gridView;
    int i;
    Globals g = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products__page);

        // create object for globals to get application
        g = (Globals) getApplication();
        // create gridview through id that defined into xml file
        Products_gridView = (GridView) findViewById(R.id.products_gridView);
        // set gridview through setAdapter
        Products_gridView.setAdapter(new products_Adapter(Products_Page.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products__page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // check which item clicked from menu bar
        switch (id) {

            // case is use for signout_page call
            case R.id.Sign_out_product :

                // create intent object to call another activity
                Intent signOut_intent = new Intent(Products_Page.this, Login_form.class);
                SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPref.edit();
                editor.clear();
                editor.commit();
                finish();
                Globals g = (Globals) getApplication();
                g.getFinal_order().clear();
                // start activity by calling intent object
                startActivity(signOut_intent);
                break;

            // case is use for call Home_page
            case R.id.Home_page_productPage :

                // create intent object to call another activity
                Intent HomePage_intent = new Intent(Products_Page.this, Home_Page.class);
                // start activity by calling intent object
                startActivity(HomePage_intent);
                break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**********************************************************************************************
     Name :- add_products
     Description :- Adding products and product name through constructor.
     **********************************************************************************************/
    class add_products {

        // declare int variable
        int products_images;
        // declare string variable
        String products_Name;
        // creating constructor to adding product image and product name
        add_products(int product_images, String product_Name) {

            this.products_images = product_images;
            this.products_Name = product_Name;
        }
    }

    /**********************************************************************************************
     Name :- Products_ViewHolder
     Description :- Products_ViewHolder holds products images and products name.
     **********************************************************************************************/
    class Products_ViewHolder
    {
        // declare Imageview object
        ImageView products_Images;

        // declare Texview object
        TextView productsName_textView;

        // creating constructor as Product_ViewHolder
        Products_ViewHolder(View view) {

            // create Imageview through id defined in xml file
            products_Images = (ImageView) view.findViewById(R.id.products_imageView);
            // create textview through id defined in xml file
            productsName_textView= (TextView) view.findViewById(R.id.productsName_textView);
        }
    }

    /**********************************************************************************************
     Name :- products_Adapter
     Description :- Adding products Images and products name in Gridview and on click of any item
     next page details will br filled by user.
     **********************************************************************************************/
    class products_Adapter extends BaseAdapter{

        int[] tempProductsImages = { R.drawable.newpipe,R.drawable.newwireclip,
                R.drawable.newcabletiew, R.drawable.newpipe,R.drawable.newcasing,
                R.drawable.j_box_cover,R.drawable.angle_batten_45,
                R.drawable.newnanomodular,R.drawable.lx_cover,R.drawable.newtester,
                R.drawable.gold_cover,R.drawable.glory_cover,
                R.drawable.one_modular_cover,R.drawable.pendant_holder_cover,
                R.drawable.newbell,R.drawable.lx_base_cover,R.drawable.plazzo_cover,
                R.drawable.eva_switch_cover,R.drawable.concealed_cover,R.drawable.exhaust_fan_swift_hx1_cover,
                R.drawable.flex_box_cover,R.drawable.spike_guard,R.drawable.spike_guard,
                R.drawable.multiplug_cover, R.drawable.round_plate_cover,R.drawable.omya_cover,
                R.drawable.patti_stand_cover,R.drawable.mcb_cover,R.drawable.bulkhead_cover,
                R.drawable.plastic_board_cover,R.drawable.plastic_sheet,R.drawable.royal_plate,
                R.drawable.wires_front_cover,R.drawable.wires_front_cover,
                R.drawable.wires_front_cover,R.drawable.mutilicore_cable_cover,
                R.drawable.casing_cover, R.drawable.eva_plate_cover,
                R.drawable.j_box_cover,R.drawable.uni_gb_cover,R.drawable.tejas_cb_cover,
                R.drawable.cable_tray_cover,R.drawable.newtejasmodular,R.drawable.mix_cover};

        // declare context object
        Context context;

        // declare arraylist<add_products> object
        ArrayList<add_products> products_list;
        // creating constructor as products_Adapter
        products_Adapter(Context context) {

            this.context = context;
            // initialize arraylist<add_products>
            products_list = new ArrayList<add_products>();
            // get resources of context
            android.content.res.Resources products_resources = context.getResources();
            // declare string of array to add product name
            String [] tempProductsName = products_resources.getStringArray(R.array.products_name);

            // using loop display images and textview into gridview
            for (i =0; i < 44; i++) {
                // add images and textview into add_products object
                add_products tempAddProducts = new add_products( tempProductsImages[i],
                        tempProductsName[i]);
                // add items into into arraylist
                products_list.add(tempAddProducts);
            }

        }
        @Override
        public int getCount() {
            return products_list.size();
        }

        @Override
        public Object getItem(int i) {
            return products_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, final View view, ViewGroup viewGroup) {

            // Declare View object
            View products_row = view;

            // Declare ViewHolder object
            Products_ViewHolder products_viewHolder = null;

            // check gridview is null or not
            if (products_row == null) {

                /******************************************************************************
                 Name:- LayoutInflater
                 Description:- LayoutInflater call another layout to set item into listview.
                 ******************************************************************************/
                LayoutInflater products_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // View object get layout through LayoutInflater object as inflater
                products_row = products_inflater.inflate(R.layout.activity_pipe_single_row__gridview, viewGroup, false);
                // Through viewHolder object get row into gridview
                products_viewHolder = new Products_ViewHolder(products_row);
                // set tag for gridview
                products_row.setTag(products_viewHolder);
            }
            else {
                // get row in gridview without using xml file to findViewById
                products_viewHolder = (Products_ViewHolder) products_row.getTag();
            }
            // add list into add_products object
            add_products tempAddproducts = products_list.get(i);
            // set image into products_viewHolder.products_Images
            products_viewHolder.products_Images.setImageResource(tempAddproducts.products_images);
            // set product name into textview
            products_viewHolder.productsName_textView.setText(tempAddproducts.products_Name);
            // set scale type into imageview
            products_viewHolder.products_Images.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // set padding for imageview
            products_viewHolder.products_Images.setPadding(6, 6, 6, 6);
            // call clicked function
            clickedItem();
            // return viewholder object
            return products_row;
        }
    }

    /******************************************************************************************
     Name :- clickedItem
     Description :- call next activity on click of each image from gridview
     *******************************************************************************************/
    public void clickedItem() {

        // set onclick listener on gridview each item
        Products_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // create intent object
                Intent nextActivity_intent = null;

                android.content.res.Resources r = getResources();

                /*********************************************************************************
                 check condition that which item is clicked through position and checks
                 isProduct_load_complete_flag value is true or false. If it will be false then
                 all another activity will not be call.
                 *********************************************************************************/
                if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.TEJASMODULAR_POSITION)
                        || position == r.getInteger(R.integer.MULTIPLUG_POSITION)
                        || position == r.getInteger(R.integer.ROUND_PLATE_POSITION)
                        || position == r.getInteger(R.integer.OTHER_ELECTRICAL_ACCESSORIES_POSITION)
                        || position == r.getInteger(R.integer.PATTI_STAND_POSITION)
                        || position == r.getInteger(R.integer.BULKHEAD_POSITION)
                        || position == r.getInteger(R.integer.GOLD_RANGE_POSITION)
                        || position == r.getInteger(R.integer.GLORY_RANGE_POSITION)
                        || position == r.getInteger(R.integer.ONE_RANGE_POSITION)
                        || position == r.getInteger(R.integer.EVA_SWITCH_POSITION)
                        || position == r.getInteger(R.integer.PLUG_POSITION)
                        || position == r.getInteger(R.integer.TEJAS_CONCEAL_BOX_POSITION)) {

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_2.class);
                    nextActivity_intent.putExtra("position",position);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.TESTER_POSITION)
                        || position == r.getInteger(R.integer.HOLDER_RANGE_POSITION)
                        || position == r.getInteger(R.integer.DOORBELL_POSITION)
                        || position == r.getInteger(R.integer.PALAZO_MP_POSITION)
                        || position == r.getInteger(R.integer.OMYAH_MP_POSITION)
                        || position == r.getInteger(R.integer.UNI_GANG_BOX_POSITION)) {

                    nextActivity_intent = new Intent(view.getContext(), Product_Detail_Page_Type_3.class);
                    nextActivity_intent.putExtra("position",position);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag()  && position ==  r.getInteger(R.integer.PCV_CONDUIT_POSITION)) {

                    String value = "ISI Accessories";
                    nextActivity_intent = new Intent(view.getContext(),ProductPage_subType.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value",value);
                    startActivity(nextActivity_intent);
                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.LX_PLATES_RANGE_POSITION)) {

                    String value = "LX Cover and Base Plates";
                    nextActivity_intent = new Intent(view.getContext(),ProductPage_subType.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value",value);
                    startActivity(nextActivity_intent);

                }  else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.CASING_POSITION)) {

                    int[] product_images = {R.drawable.cc_tejas, R.drawable.cc_classy,
                            R.drawable.cc_nano, R.drawable.cc_diya ,
                            R.drawable.cc_glory,R.drawable.cc_viraj,R.drawable.cc_lords,
                            R.drawable.cc_pressfit_boxtype,R.drawable.cc_galaxy,R.drawable.cc_gold,
                            R.drawable.cc_trunking_type1,R.drawable.cc_trunking_type2,R.drawable.cc_pressfit,
                            R.drawable.cc_royal,R.drawable.cc_lexi};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.casing_list);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.CONCEAL_BOX_POSITION) ){

                    int[] product_images = new int[]{R.drawable.metal_box, R.drawable.conceal_modular,
                            R.drawable.surface_box, R.drawable.box_photo ,
                            R.drawable.charm_concealed_board,R.drawable.box_photo,R.drawable.box_photo,
                            R.drawable.box_photo,R.drawable.box_photo};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.Surface_Box_Types);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.WIRE_CLIP_POSITION) ){

                    int[] product_images = new int[]{R.drawable.wire_clip,
                                R.drawable.flat_casing,R.drawable.roundcasingclip};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.clip_types);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.MCB_BOXES_POSITION) ){

                    int[] product_images = new int[]{R.drawable.mcb_gold_dbs,
                            R.drawable.mcb_gold_metal_enclosure,R.drawable.mcb_nano_dbs,
                            R.drawable.mcb_nano_ac_box,R.drawable.mcb_distribution_board,
                            R.drawable.mcb_diya_dbs,R.drawable.mcb_diya_plus_dbs,
                            R.drawable.mcb_box_metal,R.drawable.mcb_box_plastic};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.Mcb_types);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.CASING_CAPING_ACCESSORIES_POSITION) ){

                    int[] product_images = new int[]{R.drawable.casing_type_1,
                            R.drawable.casing_type_1,R.drawable.casing_type_1,R.drawable.casing_type_2,
                            R.drawable.casing_type_2,R.drawable.casing_type_1,R.drawable.casing_type_2,
                            R.drawable.casing_type_1,R.drawable.casing_type_3,
                            R.drawable.casing_type_4,R.drawable.casing_type_4,R.drawable.casing_type_4,
                            R.drawable.casing_type_5,R.drawable.casing_type_6,R.drawable.casing_type_7,R.drawable.casing_type_9};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.casing_caping_accessories_names);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.MULTICORE_CABLE_POSITION) ){

                    int[] product_images = new int[]{

                            R.drawable.multiwire_2core,R.drawable.multiwire_2core,R.drawable.multiwire_4core,
                            R.drawable.multiwire_2core,R.drawable.multiwire_2core,R.drawable.multiwire_4core,
                            R.drawable.no_product_image_small,R.drawable.no_product_image_small,
                            R.drawable.no_product_image_small,R.drawable.no_product_image_small,R.drawable.no_product_image_small,
                            R.drawable.no_product_image_small,R.drawable.no_product_image_small};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.MULTICORE_CABLE);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                }  else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.EXHAUST_FAN_POSITION) ){

                    int[] product_images = new int[]{R.drawable.rapid_air_exhast_fan,
                            R.drawable.kreta_exhast_fan,R.drawable.swift_hx_exhast_fan };

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.EXHAUST_FAN);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                }   else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.GANG_BOX_POSITION) ){

                    int[] product_images = new int[]{R.drawable.no_product_image_small,
                            R.drawable.no_product_image_small,R.drawable.no_product_image_small,
                            R.drawable.no_product_image_small};

                    nextActivity_intent = new Intent(view.getContext(),Product_Details_Page_Type_4.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("product_type", R.array.Nano_list);
                    nextActivity_intent.putExtra("product_images", product_images);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.PLASTIC_BOARD_POSITION) ){

                    String value = "Plastic Board";
                    nextActivity_intent = new Intent(view.getContext(),Product_Detail_Page_Type_3.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value",value);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.NON_ISI_ACCESSORIES_POSITION)){

                    String value = "NON_ISI Accessories";
                    nextActivity_intent = new Intent(view.getContext(),ProductPage_subType.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value",value);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.FLEX_BOX_POSITION)){

                    String value = "Flex Box";
                    nextActivity_intent = new Intent(view.getContext(),ProductPage_subType.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value",value);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.POWER_GUARD_POSITION)){

                    String value = "Power Guard";
                    nextActivity_intent = new Intent(view.getContext(),ProductPage_subType.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value", value);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.SPIKE_GUARD_POSITION)) {

                    String value = "Spike Guard";
                    nextActivity_intent = new Intent(view.getContext(), ProductPage_subType.class);
                    nextActivity_intent.putExtra("position", position);
                    nextActivity_intent.putExtra("subType_value", value);
                    startActivity(nextActivity_intent);

                } else if(g.isProduct_load_complete_flag() && position == r.getInteger(R.integer.EVA_EDGE_PLATES_POSITION)){

                    String value = "Eva Edge Range";
                    nextActivity_intent = new Intent(view.getContext(), ProductPage_subType.class);
                    nextActivity_intent.putExtra("position", position);
                    nextActivity_intent.putExtra("subType_value", value);
                    startActivity(nextActivity_intent);
                }
                else {

                    String value = "None";
                    nextActivity_intent = new Intent(view.getContext(), Product_Details_Page_Type_1.class);
                    nextActivity_intent.putExtra("position",position);
                    nextActivity_intent.putExtra("subType_value" ,value);
                    startActivity(nextActivity_intent);
                }
            }
        });
    }

    /******************************************************************************
     Name :- callAddcartPage_ProductPage
     Description :- function call another activity on click of add cart icon
     *****************************************************************************/
    public  void callAddcartPage_ProductPage(MenuItem item) {

        // create intent object to call another activity
        Intent calladdCartPage = new Intent(this,Order_Details.class);
        // start activity by calling intent object
        startActivity(calladdCartPage);
    }

    /********************************************************************************************
        Name :- search_product_ProductPage
        Input :- Pass MenuItem object
        Description :- onclick of search icon alert box to search product name according
                       to product code.
    *********************************************************************************************/
    public void search_product_ProductPage(MenuItem item){

        Intent callSearchProductPage = new Intent(Products_Page.this, SearchProduct_Page.class);
        startActivity(callSearchProductPage);
    }
}