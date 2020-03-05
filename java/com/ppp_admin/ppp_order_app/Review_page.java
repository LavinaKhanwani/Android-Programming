package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

public class Review_page extends Activity
{
    /*******************************************************************************************
     * Section for declaration of the variables
     *******************************************************************************************/
      Globals g;
      Account_Master accountSelected;
      Button buttonConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        // create button object through id define in xml file
        buttonConfirmOrder = (Button) findViewById(R.id.button_confirm_order);
        accountSelected = (Account_Master) getIntent().getSerializableExtra("currentData");

        /*  create Textview object to set Customer Name ans City  */
        TextView showSelectedAccountName = (TextView) findViewById(R.id.showAccountName_textview);
        showSelectedAccountName.setGravity(Gravity.CENTER);
        showSelectedAccountName.setText("Name :- " + accountSelected.getAccountName().toUpperCase()
                + "  " + accountSelected.getCity().toUpperCase());
        // call Review_process function
        Review_process();
        OrderTableObject();
    }

    /*********************************************************************************************
        Name :- Review_process
        Description :- This function display all items that user selects from listvew of products
     ********************************************************************************************/
      public void Review_process() {

             // declare global variable
             g = (Globals) getApplication();

             // create tablelayout that defiend into xml file
            final TableLayout review_orderTable = (TableLayout) findViewById(R.id.review_order_table);

            // declare Iterator variable to get hashmap data into it.
            final Iterator<Map.Entry<String,Order_Detail_Object>> iterator = g.getFinal_order().entrySet().iterator();

            while (iterator.hasNext())
            {
                // declare Map.Entry object to iterate loop
                Map.Entry<String, Order_Detail_Object> current = iterator.next();

                // create tablerow object
                final TableRow review_tableRow = new TableRow(this);

                // get current product code according to item chosen by user
                String product_code = get_product_code(current);

                // set current product code in a current row of product
                TextView  Product_Code_textView =  set_product_code(product_code);

                // set product name in current row of textview
                TextView itemName_textView = set_product_name(current);

                // set qty,units in a current row of table
                LinearLayout linearLayout = set_qty_units(current);

                // set price in current row of table
                TextView totalRate_textview = set_price_of_product(current);

                // add all items into review_tableRow
                review_tableRow.addView(Product_Code_textView, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                review_tableRow.addView(itemName_textView, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                review_tableRow.addView(linearLayout, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                review_tableRow.addView(totalRate_textview, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

                TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);

                // add review_tableRow into table layout
                review_orderTable.addView(review_tableRow, rowParams);
            }
      }

    /********************************************************************************************
        Name :- get_product_code
        Input :- pass Map.Entry<String, Order_Detail_Object> object contains details
        Output :- return current product code
        Description :- get current product code according to itemaname
     *********************************************************************************************/
    public String get_product_code(Map.Entry<String, Order_Detail_Object> current){

        // Get product name
        String ItemName = current.getValue().getItem();
        if(ItemName.contains("{") && ItemName.contains("}")){
            ItemName = ItemName.substring(0,ItemName.indexOf("{"));
        }
        // get product code from globals
        String product_code = g.getProductsHashMap().get(ItemName).getProduct_code();
        return product_code;
    }

    /********************************************************************************************
        Name :- set_product_code
        Input :- pass current product code to set in textview
        Output :- return textview object
        Description :- set product code in current row of table
    ********************************************************************************************/
    public TextView set_product_code(String product_code){

        // get product code to set in textview
       TextView Product_Code_textView = new TextView(this);
        Product_Code_textView.setGravity(Gravity.CENTER);
        //String[] Product_code = product_code.split("-");
        Product_Code_textView.setText(product_code);
        Product_Code_textView.setPadding(0, 0, 0, 10);

        return Product_Code_textView;
    }

    /********************************************************************************************
        Name :- set_product_name
        Input :- pass Map.Entry<String, Order_Detail_Object> object contains details
        Output :- return textview object
        Description :- set product name in a current row od table
     *********************************************************************************************/
    public TextView set_product_name(Map.Entry<String, Order_Detail_Object> current){

        // set textview for product name
        TextView itemName_textView = new TextView(this);
        String values = current.getValue().getItem().replace(";", " ");
        itemName_textView.setGravity(Gravity.CENTER);
        itemName_textView.setText(values);
        itemName_textView.setPadding(0, 0, 0, 10);
        return itemName_textView;
    }

    /*********************************************************************************************
        Name :- set_qty_units
        Input :- pass qty and unit through Map.Entry<String, Order_Detail_Object> object
        Output :- return LinearLayout obejct contains qty and units
        Description :- set qty and units in current row of textview in table
    **********************************************************************************************/
    public LinearLayout set_qty_units(Map.Entry<String, Order_Detail_Object> current){

        // set linearlayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // set textview for quantity
        TextView quantity_textview = new TextView(this);
        quantity_textview.setGravity(Gravity.CENTER);
        quantity_textview.setText(current.getValue().getQuantity());
        quantity_textview.setPadding(0, 0, 0, 10);
        linearLayout.addView(quantity_textview);

        // set textview for unit
        TextView unit_textview = new TextView(this);
        unit_textview.setGravity(Gravity.CENTER);
        String UnitValue = current.getValue().getUnit();
        unit_textview.setText(UnitValue);
        unit_textview.setPadding(0, 0, 0, 10);
        linearLayout.addView(unit_textview);

        return linearLayout;
    }

    /********************************************************************************************
        Name :- set_price_of_product
        Input :- pass Map.Entry<String, Order_Detail_Object> object contains price
        Output :- return textview object contains price
        Description :- set price in textview of current row of table
     ********************************************************************************************/
    public TextView set_price_of_product(Map.Entry<String, Order_Detail_Object> current){

        // set TextView for Rate
        TextView  totalRate_textview  = new TextView(this);
        totalRate_textview.setGravity(Gravity.CENTER);
        Float TotalRate = current.getValue().getPrice();
        totalRate_textview.setText("Rs. " + String.valueOf(TotalRate));
        totalRate_textview.setPadding(0,0,0,10);
        return totalRate_textview;
    }

    /*************************************************************************************
        Name :- OrderTableObject
        Description :- Call next page through intent
     ************************************************************************************/
     public void OrderTableObject() {

        // set onclick listener on confirm button
        buttonConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shipping_details = ((EditText) findViewById(R.id.et_shippingAddr)).getText().toString();
                String narration = ((EditText) findViewById(R.id.et_splReq)).getText().toString();

                // create intent object to call next activity
                Intent OrderTablePage_Intent = new Intent(Review_page.this, OrderTableObject.class);

                // pass AccountMaster data to next activity
                OrderTablePage_Intent.putExtra("AccountMaster", accountSelected);
                OrderTablePage_Intent.putExtra("ShippingDetails",shipping_details);
                OrderTablePage_Intent.putExtra("Narration", narration);

                // This start activity by calling intent object.
                startActivity(OrderTablePage_Intent);
            }
        });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review_page, menu);
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

             // case is use for review_page call
            case R.id.Sign_out_reviewPage :

                // create intent object to call another activity
                Intent signOut_intent = new Intent(Review_page.this,Login_form.class);

                /* SharedPreferences remove data if user signout*/
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
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}