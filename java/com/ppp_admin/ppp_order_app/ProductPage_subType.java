package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class ProductPage_subType extends Activity
{
    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    GridView ProductsSubType_gridView;
    Globals g;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page_sub_type1);

        // create gridview through id that defined into xml file
        ProductsSubType_gridView = (GridView) findViewById(R.id.ProductSubType_gridView);

        value = getIntent().getStringExtra("subType_value");

        AllProduct_getList allProduct_getList = new AllProduct_getList();

        ArrayList<Integer> tempImages = null;
        ArrayList<String> tempProductName = new ArrayList<String>();

        if(value.equals("ISI Accessories")) {
            tempImages = allProduct_getList.getPVC_Conduit_Images();
            tempProductName = allProduct_getList.getPVC_Conduit_ProductName();
        }
        else if(value.equals("LX Cover and Base Plates")){
            tempImages = allProduct_getList.getLXRangeCoverPlate_Images();
            tempProductName =allProduct_getList.getLXRangeCoverPlate_ProductName();
        }
        else if(value.equals("NON_ISI Accessories")){
            tempImages = allProduct_getList.getNON_ISI_Accessories_Image();
            tempProductName = allProduct_getList.getNON_ISI_Accessories_Name();
        }
        else if(value.equals("Flex Box")){
            tempImages = allProduct_getList.getFlex_Box_Images();
            tempProductName = allProduct_getList.getFlex_Box_Name();

        } else if(value.equals("Power Guard")){
            tempImages = allProduct_getList.getPower_Guard_Images();
            tempProductName = allProduct_getList.getPower_Guards_Name();

        } else if(value.equals("Spike Guard")){
            tempImages = allProduct_getList.getSpike_Guard_Images();
            tempProductName = allProduct_getList.getSpike_Guard_Name();

        } else if(value.equals("Eva Edge Range")){
            tempImages = allProduct_getList.get_EVA_Images();
            tempProductName = allProduct_getList.getEVA_Range_List();
        }

        // set gridview through setAdapter
        ProductsSubType_gridView.setAdapter(new productSubType_Adapter(ProductPage_subType.this , tempImages,tempProductName));
        ChangeTitleofActionBar();
    }

    /*******************************************************************************************
     Name :- ChangeTitleofActionBar()
     Description :- Change title of action bar.
     *********************************************************************************************/
    public void ChangeTitleofActionBar() {

        if(value.equals("ISI Accessories")) {
            getActionBar().setTitle("ISI ACCESSORIES");
        }
        else if(value.equals("LX Cover and Base Plates")) {
            getActionBar().setTitle("LX BASE & COVER PLATES");
        }
        else if(value.equals("NON_ISI Accessories")){
            getActionBar().setTitle("NON ISI ACCESSORIES");
        }
        else if(value.equals("Flex Box")){
            getActionBar().setTitle("FLEX BOX");
        }
        else if(value.equals("Power Guard")){
            getActionBar().setTitle("Power Guard");
        }
        else if(value.equals("Spike Guard")){
            getActionBar().setTitle("SPIKE GUARD");

        } else if(value.equals("Eva Edge Range")){
            getActionBar().setTitle("EDGE PLATES");
        }
    }

    /**********************************************************************************************
     Name :- add_products
     Description :- Adding products and product name through constructor.
     **********************************************************************************************/
    class product_singleItems {

        // declare int variable
        int products_images;
        // declare string variable
        String products_Name;

        // creating constructor to adding product image and product name
        product_singleItems(int product_images, String product_Name) {

            this.products_images = product_images;
            this.products_Name = product_Name;
        }
    }

    /**********************************************************************************************
     Name :- Products_ViewHolder
     Description :- Products_ViewHolder holds products images and products name.
     **********************************************************************************************/
    class Products_ViewHolder {

        // declare Imageview object
        ImageView products_Images;

        // declare Texview object
        TextView productsName_textView;

        // creating constructor as Product_ViewHolder
        Products_ViewHolder(View view)
        {
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
    class productSubType_Adapter extends BaseAdapter
    {
        // get all images into array of Integer
        ArrayList<Integer> tempProductsImages;
        ArrayList<String> tempProductName;

        // declare context object
        Context context;

        // declare arraylist<add_products> object
        ArrayList<product_singleItems> products_list;

        // creating constructor as products_Adapter
        productSubType_Adapter(Context context, ArrayList<Integer> tempProductsImages, ArrayList<String> tempProductName) {

            this.context = context;
            this.tempProductsImages = tempProductsImages ;
            this.tempProductName = tempProductName;

            // initialize arraylist<add_products>
            products_list = new ArrayList<>();

            // using loop display images and textview into gridview
            for (int i =0; i < tempProductsImages.size(); i++) {

                // add images and textview into add_products object
                product_singleItems tempAddProducts =
                        new  product_singleItems(tempProductsImages.get(i),tempProductName.get(i));
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
        public View getView(int i, final View view, ViewGroup viewGroup)
        {
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
                products_row = products_inflater.inflate(R.layout.activity_product_sub_type_gridview1, viewGroup, false);

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
            final product_singleItems tempAddproducts = products_list.get(i);

            // set image into products_viewHolder.products_Images
            products_viewHolder.products_Images.setImageResource(tempAddproducts.products_images);
            // set product name into textview
            products_viewHolder.productsName_textView.setText(tempAddproducts.products_Name);
            // set scale type into imageview
            products_viewHolder.products_Images.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // set padding for imageview
            products_viewHolder.products_Images.setPadding(6, 6, 6, 6);

            clickedItem();

            // return viewholder object
            return products_row;
        }
    }

    /***************************************************************
     Name :- clickedItem
     Description :- Call another activity through intent.
     ****************************************************************/
    public void clickedItem() {

        // set onclick listener on gridview each item
        ProductsSubType_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*********************************************************************************
                 check condition that which item is clicked through position and checks
                 isProduct_load_complete_flag value is true or false. If it will be false then
                 all another activity will not be call.
                 *********************************************************************************/
                String sub_type_value = getIntent().getStringExtra("subType_value");

                    /* --> Condition checks if its matches to ISI Accessories further action will be perform.
                        --> If condition will satisfy then it will pass position of product and
                            name of product as subType_value. */

                if(value.equals("ISI Accessories")) {
                    calling_another_page(view,position,sub_type_value);

                } else if(value.equals("LX Cover and Base Plates")) {
                    calling_another_page(view, position, sub_type_value);

                } else if(value.equals("NON_ISI Accessories")){
                    calling_another_page(view, position, sub_type_value);

                } else if(value.equals("Flex Box")){
                    calling_another_page(view, position, sub_type_value);

                } else if(value.equals("Power Guard")) {
                    calling_another_page(view, position, sub_type_value);

                }else if(value.equals("Spike Guard")) {
                    calling_another_page(view,position,sub_type_value);

                } else if(value.equals("Eva Edge Range")){
                    calling_another_page(view,position,sub_type_value);
                }
            }
        });
    }

    /**********************************************************************************************
     Name :- calling_another_page
     Input :- Pass View object as view, int object as position and String object as sub_type_value
     Description :- Function works to call another page from subtype page.
     *********************************************************************************************/
    public void calling_another_page(View view, int position, String sub_type_value){

        // create intent object
        Intent nextActivity_intent = null;

        // initialize which activity have to call
        nextActivity_intent = new Intent(view.getContext(), Product_Details_Page_Type_1.class);

        // pass position of product
        nextActivity_intent.putExtra("position", position);

        // pass sub_type_value of product
        nextActivity_intent.putExtra("subType_value",sub_type_value);

        // call another activity
        startActivity(nextActivity_intent);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_page_sub_type, menu);
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