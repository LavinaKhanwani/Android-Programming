package com.ppp_admin.ppp_order_app;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 1/28/2017.
 */
public class my_list_details_custom_adapter extends BaseAdapter {

    ArrayList<String> show_products;
    boolean edit_flag;
    private static Context context;
    Globals g;
    String listName;

    my_list_details_custom_adapter(Context context_new,ArrayList<String> product_names ,
                                   Globals g_value,boolean flag_value, String listName) {
        context = context_new;
        show_products = product_names;
        g = g_value;
        edit_flag = flag_value;
        this.listName = listName;
    }

    public ArrayList<String> getShow_products() {
        return show_products;
    }

    public void setShow_products(ArrayList<String> show_products) {
        this.show_products = show_products;
    }

    public boolean isFlag() {
        return edit_flag;
    }

    public boolean setFlag(boolean flag) {
        this.edit_flag = flag;
        return flag;
    }

    @Override
    public int getCount() { return show_products.size(); }

    @Override
    public Object getItem(int i) { return show_products.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_show_my_list_details, parent, false);

        final TextView set_product_name_tv = (TextView) row.findViewById(R.id.display_mylist_details);
        final Button cart_and_delete_btn = (Button) row.findViewById(R.id.cart_and_delete_btn);

        final String prod_name = show_products.get(i);
        set_product_name_tv.setText(prod_name);

        if(edit_flag) {

            cart_and_delete_btn.setBackgroundResource(R.drawable.delete_icon);
              /* Remove list name from listview */
                    cart_and_delete_btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            final AlertDialog.Builder delete_confirm_alertBox = new AlertDialog.Builder(context);
                            // set Message to on alert boz
                            delete_confirm_alertBox.setMessage("Are you sure you want to delete?");
                            // set positive button to delete name from listview
                            delete_confirm_alertBox.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String delete_prod_arr = get_product_ids_local_li(i);
                                    delete_from_local_list_item_table(delete_prod_arr);
                                    // remove item from listview
                                    show_products.remove(i);
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
        } else {

            cart_and_delete_btn.setBackgroundResource(R.drawable.add_to_cart);
            cart_and_delete_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    add_single_product_into_cart(i);
                }
            });
        }
        return row;
    }

    /********************************************************************************************
     Name :- add_single_product_into_cart
     Description :- adding single product into cart.
     *********************************************************************************************/
    public void add_single_product_into_cart(int i){

        Order_Detail_Object order_detail_object = null;
        order_detail_object = new Order_Detail_Object(show_products.get(i));
        String prod_id = g.getProductsHashMap().get(show_products.get(i)).getID();
        g.getFinal_order().put(prod_id, order_detail_object);
        Toast.makeText(context, "Product added to cart", Toast.LENGTH_LONG).show();
    }

    /*******************************************************************************************
     Name :-  delete_from_local_list_item_table
     Description :- clicked item will be deleted from local db of LI.
     ********************************************************************************************/
    public void delete_from_local_list_item_table(String delete_prod_arr) {

        SharedPreferences loginSharedPref = context.getSharedPreferences("LoginData", context.MODE_PRIVATE);
        // get current agent id
        String AgentID = loginSharedPref.getString("ID", "");
        Log.i("List_Item :- ", delete_prod_arr + "..deleting from local LI");

        ContentValues copyvalue = new ContentValues();
        copyvalue.put(GlobalProvider.LI_deleted, "1");
        // firing a query to delete data from local DB of Lists table
        int uri = context.getContentResolver().update(GlobalProvider.CONTENT_URI_List_Item,copyvalue,
                GlobalProvider.List_Login_User_ID + " = ? AND  " +
                        GlobalProvider.LI_Product_ID + " = ?  AND " + GlobalProvider.LI_List_Name + " = ?",
                new String[]{AgentID, delete_prod_arr,listName});
        Log.i("List_Item", delete_prod_arr + " deleted successfully local db of LI");

        Toast.makeText(context,"Product has been removed successfully" ,Toast.LENGTH_LONG).show();

    }

    /*********************************************************************************************
     Name :- get_product_ids_local_li
     Input :- Integer
     Output :- String
     Description :- get product id from local db according to product name selected by user
     *********************************************************************************************/
    public String get_product_ids_local_li(int position){
        String URL = "content://app.admin.pressfit.provider/Product";
        Uri prod_uri = Uri.parse(URL);
        String prod_id = new String();

        /* loop on products ids */
        Cursor prod_cursor = context.getContentResolver()
                .query(prod_uri, new String[]{GlobalProvider.Product_ID},
                        GlobalProvider.Product_Name + " = ? ", new String[]{show_products.get(position)}, null);
        //if(prod_cursor.moveToFirst()) {
        prod_cursor.moveToFirst();

        // get ids from local db of Product table
        prod_id = prod_cursor.getString(prod_cursor.
                getColumnIndex(GlobalProvider.Product_ID));
        return prod_id;
    }
}