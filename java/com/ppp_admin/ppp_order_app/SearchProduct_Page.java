package com.ppp_admin.ppp_order_app;

import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchProduct_Page extends AppCompatActivity{

    ListView display_items_listview;
    ArrayAdapter add_item_in_arrayAdapter;
    EditText search_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_page);

        // getApplication of globals
        Globals g = (Globals) getApplication();
        /*****************************************************************************************
            Name :- product_details
            key :- product code
            Description :- store product code and name according search by user.
        ******************************************************************************************/
        final HashMap<String, Product> product_details = g.getProductsHashMap();

        // set id to listview object
        display_items_listview = (ListView) findViewById(R.id.display_search_item_listview);
        // add textview from layout into array adapter
        add_item_in_arrayAdapter = new ArrayAdapter(this, R.layout.search_list_textview,R.id.search_list_tv);
        // set id to search textview
         search_tv = (EditText) findViewById(R.id.search_edit_text);
        // filter list view
        search_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    add_item_in_arrayAdapter.clear();

                } else{
                    String product_description = new String();
                    // get text which is entered by user
                    product_description = search_tv.getText().toString().toUpperCase();
                    // display list of products
                    search_product(product_description, product_details);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /*******************************************************************************************
     Name :- search_product
     Input :- Pass string as product code
     Description :- Display all the item related to the product code in list.
     ********************************************************************************************
     * @param product_description
     * @param product_details*/
    public void search_product(String product_description, HashMap<String, Product> product_details){

        ArrayList<Product> searched_details = new ArrayList<Product>();
        // declare object of Iterator
        Iterator<Map.Entry<String,Product>> iterator;
        // get data from hashmap to Iterator object
        iterator = product_details.entrySet().iterator();
        // set textview for show null or empty
        TextView tv = (TextView) findViewById(R.id.showNullValueList_tvProduct);
        add_item_in_arrayAdapter.clear();

        // check condition empty and show error message
        if(product_description.equals(" ") || product_description.equals("")) {

            // show error message
            tv.setVisibility(View.VISIBLE);
            // clear adapter of listview
            searched_details.clear();

        } else {
            // set visibility invisible
            tv.setVisibility(View.INVISIBLE);
            // loop will iterate on ProductHashMap
            while (iterator.hasNext()) {

                // add next data into current object
                Map.Entry<String, Product> current = iterator.next();
                // get product details from current
                Product prod_details_current = current.getValue();
                // get current product name
                String prod_name_current = current.getKey();
                // get product code from current
                String prod_code_current = prod_details_current.getProduct_code();
                /* add all product details into arraylist */
                if (prod_code_current.contains(product_description)
                        || prod_name_current.contains(product_description)) {
                    // add product details into arraylist
                    searched_details.add(prod_details_current);
                }
            }
            /*  loop helping to get current product code and product name in from arraylist */
            for (Product final_order : searched_details) {
                // add all current product code and product name into listview
                add_item_in_arrayAdapter.add(final_order.getProduct_code() + "  -->  "
                        + final_order.getName());
            }
        }
        // add adapter into listview
        display_items_listview.setAdapter(add_item_in_arrayAdapter);
        // set onclickItemlistener on listview
        display_items_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get current selected item from listview
                String selected_item = (String) parent.getItemAtPosition(position);
                // add current item into cart
                add_to_cart_process(selected_item);
            }
        });
    }

    /********************************************************************************************
     Name :- add_to_cart_process
     Input :-  pass current item in string as selected_item
     Description :- add current item into cart which is selected by user.
     *********************************************************************************************/
    public void add_to_cart_process(String selected_item){

        // create globals object
        Globals g = (Globals) getApplication();
        // use split function on product name and code
        String[] final_item = selected_item.split("  -->  ");
        // get product name
        String prod_name = final_item[1];
        // add current product name into Order_Detail_Object's object to set Item name
        Order_Detail_Object order_detail_object = new Order_Detail_Object(prod_name);
        // get current product id according to current product name
        String ProductId = g.getProductsHashMap().get(order_detail_object.getItem()).getID();

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
        // show toast message by user
        Toast.makeText(this,"Product added to cart ",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_product_page, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
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
}
