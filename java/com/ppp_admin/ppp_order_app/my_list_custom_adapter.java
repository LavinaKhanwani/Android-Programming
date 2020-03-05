package com.ppp_admin.ppp_order_app;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 1/11/2017.
 */
public class my_list_custom_adapter extends BaseAdapter {

    ArrayList<String> list_names ;
    // create the Context
    private static Context context;
    Globals globals;

    my_list_custom_adapter(Context new_context,ArrayList<String> listnames,Globals g_value){

        context = new_context;
        // define object of ArrayList<myList_single_row_name>
        list_names = listnames;
        globals = g_value;
    }

    public ArrayList<String> getList_names() {
        return list_names;
    }

    public void setList_name(ArrayList<String> list_name) {
        this.list_names = list_name;
    }

    @Override
    public int getCount() {
        return list_names.size();
    }

    @Override
    public Object getItem(int i) {

        return list_names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_show_my_list, parent, false);

        final TextView set_name_tv = (TextView) row.findViewById(R.id.display_mylist);
        Button addCart_button = (Button) row.findViewById(R.id.add_cart_myList_button);
        final Button delete_button = (Button) row.findViewById(R.id.add_delete_myList_button);
        delete_button.setVisibility(View.VISIBLE);

        final String final_list_name = list_names.get(i);
        set_name_tv.setText(final_list_name);

        /* add to cart  */
        addCart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_product_ids_local_li(final_list_name);
            }
        });

        /* Remove list name from listview */
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder delete_confirm_alertBox = new AlertDialog.Builder(context);
                // set Message to on alert boz
                delete_confirm_alertBox.setMessage("Are you sure you want to delete?");
                // set positive button to delete name from listview
                delete_confirm_alertBox.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete from db first
                        delete_from_lists_local_DB(final_list_name);
                        // remove item from listview
                        list_names.remove(i);
                        notifyDataSetChanged();
                    }
                });
                // set negative button to cancel alert box
                delete_confirm_alertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel alert box
                        dialog.dismiss();
                    }
                });
                delete_confirm_alertBox.show();
            }
        });
        set_onclick_on_list_item(set_name_tv);
        return row;
    }

    /*********************************************************************************************
     Name :- delete_from_lists_local_DB
     Input :- pass string as list name
     Description :- on click of delete button data will be deleted from local DB of Lists table
     **********************************************************************************************/
    public void delete_from_lists_local_DB(String listName) {

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = context.getSharedPreferences("LoginData", context.MODE_PRIVATE);
        // get current agent id
        String AgentID = loginSharedPref.getString("ID", "");
        Log.i("My List", "Deleting " + listName);
        // firing a query to delete data from local DB of List_Item and List table.
        ContentValues LI_copyvalue = new ContentValues();
        LI_copyvalue.put(GlobalProvider.LI_deleted, "1");
        int LI_uri = context.getContentResolver().update(GlobalProvider.CONTENT_URI_List_Item,LI_copyvalue,
                GlobalProvider.LI_Login_User_Id + " = ? AND  " + GlobalProvider.LI_List_Name + " = ? ",
                new String[]{AgentID, listName});

        Log.i("My List", "List Items for " + listName + " deleted from local DB successfully");
        ContentValues copyvalue = new ContentValues();
        copyvalue.put(GlobalProvider.List_deleted, "1");
        int uri = context.getContentResolver().update(GlobalProvider.CONTENT_URI_List,copyvalue,
                GlobalProvider.List_Login_User_ID + " = ? AND  " + GlobalProvider.List_Name + " = ? ",
                new String[]{AgentID, listName});


        Log.i("My List", listName + " deleted from local DB successfully");
    }
    /********************************************************************************************
     Name :- set_onclick_on_list_item
     Input :- TextView
     Output :- on click of list names it will call another page.
     *********************************************************************************************/
    public void set_onclick_on_list_item(final TextView set_name_tv){

        // point to current item onclick view
        set_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list_name_clicked = (String) set_name_tv.getText();
                Intent call_another_activity = new Intent(context, my_list_details.class);
                call_another_activity.putExtra("list_name", list_name_clicked);
                context.startActivity(call_another_activity);
            }
        });
    }

    /********************************************************************************************
     Name :- get_product_ids_local_li
     Input :- String
     Output :- get all product ids according to list name and current login user id from local db
     *********************************************************************************************/
    public ArrayList<String> get_product_ids_local_li(String final_list_name) {

        // get current login value saved in sharedpreferences object
        SharedPreferences loginSharedPref = context.getSharedPreferences("LoginData", context.MODE_PRIVATE);
        // get current agent id
        String AgentID = loginSharedPref.getString("ID", "");

        ArrayList<String> prod_ids_array_list = new ArrayList<>();
        String URL = "content://app.admin.pressfit.provider/List_Items";
        Uri li_uri = Uri.parse(URL);

        // query to get data from local db of List Item table
        Cursor li_cursor = context.getContentResolver().query(li_uri,
                new String[]{GlobalProvider.LI_Product_ID}, GlobalProvider.LI_List_Name
                + " = ? AND " + GlobalProvider.LI_Login_User_Id + " = ? AND "
                + GlobalProvider.LI_deleted + " = ? ",
                new String[]{final_list_name, AgentID,"0"}, null);

        if(li_cursor.moveToFirst()) {
            do{
                // get ids from local db od Product table
                String prod_ids = li_cursor.getString(li_cursor.
                        getColumnIndex(GlobalProvider.LI_Product_ID));
                // add all product id into array list
                prod_ids_array_list.add(prod_ids);
                // call add to cart
                add_products_to_cart(prod_ids_array_list);
            } while (li_cursor.moveToNext());
        }
        return prod_ids_array_list;
    }

    /*********************************************************************************************
     Name :- add_product_to_cart
     Input :- ArrayList<String>
     Description :- According to list name all products will be added into cart
     **********************************************************************************************/
    public void add_products_to_cart(ArrayList<String> arrayList_prod_ids) {

        String URL = "content://app.admin.pressfit.provider/Product";
        Uri prod_uri = Uri.parse(URL);

        /* looping through array list of products ids */
        for(String curr_prod_id : arrayList_prod_ids){

            /* firing query on local LI and get product name of current product id */
            Cursor  prod_name_cursor = context.getContentResolver()
                    .query(prod_uri, new String[]{GlobalProvider.Product_Name},
                            GlobalProvider.Product_ID + " =?  ", new String[]{curr_prod_id}, null);

            if(prod_name_cursor.moveToFirst()){
                do{
                    // get product name
                    String prod_name = prod_name_cursor.getString(prod_name_cursor.
                            getColumnIndex(GlobalProvider.Product_Name));
                    // create new Order detail object and add product name
                    Order_Detail_Object order_detail_object = new Order_Detail_Object(prod_name);
                    // add current product id and corresponding product name in final order object
                    globals.getFinal_order().put(curr_prod_id, order_detail_object);
                }while (prod_name_cursor.moveToNext());
            }
        }
        //  Display toast message
        Toast.makeText(context,"All products are added into cart",Toast.LENGTH_LONG).show();
    }
}