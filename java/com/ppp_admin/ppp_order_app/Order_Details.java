package com.ppp_admin.ppp_order_app;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Order_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    Button addAccount_orderButton;
    Globals g;
    TableLayout orderTable;
    boolean UNIT_MATCHED = true;
    Product product;
    HashMap<String, String> product_code_id_map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details);

        // create button defined in xml file
        addAccount_orderButton = (Button) findViewById(R.id.button_addAccount_order);
        //call function addcart_process
        addCart_process();
    }

    /********************************************************************************************
     * Name :- addCart_process
     * Description :- This function create table layout and add table row dynamically and that
     * row all data will be display , we can delete row also which item we
     * didn't want.
     *******************************************************************************************/
    public void addCart_process() {

        // declare object of Iterator
        Iterator<Map.Entry<String, Order_Detail_Object>> iterator;

        // getApplication of globals
        g = (Globals) getApplication();

        // create table that defined in xml file
        orderTable = (TableLayout) findViewById(R.id.order_table);

        // get data from hashmap to Iterator object
        iterator = g.getFinal_order().entrySet().iterator();

        int count_row = 0;
        while (iterator.hasNext()) {

            // add next data into current object
             final Map.Entry<String, Order_Detail_Object> current = iterator.next();
            // create table row into table layout
            final TableRow tableRow = new TableRow(this);

            // Create unit array for current product element
            String[] UnitArray = get_unit_array_values_and_code(current);
            String[] units = {UnitArray[0], UnitArray[1], UnitArray[2]};

            // Set Product Code for the current row
            String product_code = UnitArray[3];
            // get current key from iterator
            String Product_id = current.getKey();
            // set product code in a current row
            TextView Product_Code_textView = set_product_code_textview(product_code, Product_id);
            // Set product name for the current row
            TextView itemName_tv = set_product_item_name(current);
            // get product name from textview
            String product_name = itemName_tv.getText().toString();

            // Set Product Qty for current row
            LinearLayout linearLayout = set_qty_textview(current, units, tableRow);
            // Set delete image for current row
            LinearLayout linearLayout_Image = set_image(product_name, tableRow);

            // add all items into tablerow
            tableRow.addView(Product_Code_textView, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(itemName_tv, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(linearLayout, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            tableRow.addView(linearLayout_Image, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 0.3f));

            // create layoutparams of tablelayout
            TableLayout.LayoutParams  rowParams = new TableLayout.LayoutParams(0,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f);

            // add tablerow into table layout
            orderTable.addView(tableRow, rowParams);
            // get no of rows counts added in table
            count_row = orderTable.getChildCount();
        }
        // call setAddAccounts_orderButton function
        setAddAccounts_orderButton(count_row);
    }

    /*********************************************************************************************
     * Name :- get_unit_array_values_and_code
     * Input :- Pass Map.Entry<String, Order_Detail_Object> object as current
     * Output :- Return string of array
     * Description :- function returns units array according to product name or
     * product selected by user.
     *********************************************************************************************/
        private String[] get_unit_array_values_and_code(Map.Entry<String, Order_Detail_Object> current) {

        // create string variable as product_code
        String product_code = new String();

        // create String array to store units and product code
        String[] UnitArray = new String[4];

        // get all current values in Order_Detail_Object
        Order_Detail_Object o = current.getValue();

        // get product name from Order_Detail_Object
        String ItemName = o.getItem();

        /* Condition check product name contains braces or not accordingly condition will work*/
        if (ItemName.contains("{") && ItemName.contains("}")) {
            String p_name = ItemName.substring(0, ItemName.indexOf("{"));

            // get product code in a string variable
            product_code = g.getProductsHashMap().get(p_name).getProduct_code();

            // Set Units correctly
            UnitArray[0] = g.getProductsHashMap().get(p_name).getMaster_PKG_Unit();
            UnitArray[1] = g.getProductsHashMap().get(p_name).getSTD_PKG_Unit();
            UnitArray[2] = g.getProductsHashMap().get(p_name).getBasic_Unit();
            // just to return code from this function
            UnitArray[3] = product_code;

        } else {
            // get product code in a string variable
            product_code = g.getProductsHashMap().get(ItemName).getProduct_code();
            // Set Units correctly
            UnitArray[0] = g.getProductsHashMap().get(ItemName).getMaster_PKG_Unit();
            UnitArray[1] = g.getProductsHashMap().get(ItemName).getSTD_PKG_Unit();
            UnitArray[2] = g.getProductsHashMap().get(ItemName).getBasic_Unit();
            // just to return code from this function
            UnitArray[3] = product_code;
        }
        // return String array object
        return UnitArray;
    }

    /*********************************************************************************************
     * Name :- set_product_code_textview
     * Input :- Pass product code and product id
     * Output :- return textview object
     * Description :- set product code in a current row of table
     **********************************************************************************************/
    public TextView set_product_code_textview(String product_code, String product_id) {

        // Add values to product code and ids hashmap
        product_code_id_map.put(product_code, product_id);

        // get product code to set in textview
        TextView Product_Code_textView = new TextView(this);
        Product_Code_textView.setGravity(Gravity.CENTER);

        Product_Code_textView.setText(product_code);
        // set padding of textview
        Product_Code_textView.setPadding(0, 0, 0, 10);

        // return TextView object
        return Product_Code_textView;
    }

    /*******************************************************************************************
     * Name :- set_product_item_name
     * Input :- Pass Map.Entry<String, Order_Detail_Object> object
     * Output :- Return TextView object
     * Description :- set product name in a current row of table
     ********************************************************************************************/
    public TextView set_product_item_name(final Map.Entry<String, Order_Detail_Object> current) {

        // get product name to set in textview
        final TextView itemName_tv;
        itemName_tv = new TextView(this);
        itemName_tv.setId(R.id.tv2_ItemName);
        final String ItemName = current.getValue().getItem();
        final String values = ItemName.replace(";", " ");
        itemName_tv.setGravity(Gravity.CENTER);
        itemName_tv.setText(values);
        itemName_tv.setPadding(0, 0, 0, 10);

        /*  Condition check product contains Door bell or not accordingly it will perform action */
        if (values.contains("Door Bell")) {

            // set onclicklistern to add mantras by user that will be add in textview
            itemName_tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // create alert box to confirm
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Order_Details.this);

                    // create edittext object to set text with mantras
                    final EditText editText = new EditText(Order_Details.this);
                    alert.setView(editText);
                    // set message of alert box
                    alert.setTitle("Enter tunes separated by comma");
                    // set positive button in alert box
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // get values from edittext define in a alert box
                            String str = editText.getText().toString();

                            // set text in textview of current row of table
                            itemName_tv.setText(values + "{" + str + "}");

                            // update the door bell product name in current
                            current.getValue().setItem(ItemName + "{" + str + "}");
                        }
                    })
                            // set negative button to cancel
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // dialog will get dismiss
                                    dialog.cancel();
                                }
                            });
                    // show the alert box on UI
                    alert.show();
                }
            });
        }

        // return TextView object contains product name
        return itemName_tv;
    }

    /*********************************************************************************************
     * Name :- set_qty_textview
     * Input :- pass Map.Entry<String, Order_Detail_Object> object,String array object
     * and TableRow object
     * Output :- return LinearLayout object
     * Description :- set unit and quantity in edittext
     **********************************************************************************************/
    public LinearLayout set_qty_textview(Map.Entry<String, Order_Detail_Object> current, String[] UnitArray, TableRow tableRow) {

        // set linearlayout
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // set edittext for quantity
        EditText qty_editText = (EditText) new EditText(this);
        qty_editText.setId(R.id.qty_edit_text);
        qty_editText.setPadding(10, 10, 10, 10);
        qty_editText.setTextSize(15);

        // get quantity from current
        final String qty = current.getValue().getQuantity();
        qty_editText.setText(qty);
        qty_editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        qty_editText.setGravity(Gravity.CENTER);
        linearLayout.addView(qty_editText);

        // create string of array to get unit as per product name
        Spinner selectUnit_spiner = new Spinner(this);
        selectUnit_spiner.setId(R.id.spinner_id);
        ArrayAdapter<String> selectUnitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UnitArray);
        selectUnit_spiner.setAdapter(selectUnitAdapter);
        String current_unit_value = current.getValue().getUnit();
        int current_unit_position = 0;
        /* loop will add all the item's units in a spinner */
        for (int i = 0; i < 3; i++) {
                /* condition will checks unit position are equal or not if the units are equal
                  then  loop will be close and unit will be selected uin spinner otherwise
                  if user changes the unit that unit will be set in
                   spinner. */
            if (UnitArray[i].equals(current_unit_value)) {

                //store value of i in new variable
                current_unit_position = i;
                break;
            }
        }
        // set values selected by user
        selectUnit_spiner.setSelection(current_unit_position);
        selectUnit_spiner.setOnItemSelectedListener(Order_Details.this);
        linearLayout.addView(selectUnit_spiner);

        // get update textview from current row of table
        TextView update_tv = set_update_textview(tableRow);

        // add textview into linearlayout
        linearLayout.addView(update_tv);
        // set gravity of linearlayout
        linearLayout.setGravity(Gravity.CENTER);
        // set orientation
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // return LinearLayout object
        return linearLayout;
    }

    /*********************************************************************************************
     * Name :- set_update_textview
     * Input :- Pass TableRow object
     * Output :- Return Textview object
     * Description :- set quantity , unit and update textview to update the details filled by user
     *********************************************************************************************/
    public TextView set_update_textview(final TableRow tableRow) {

        // set textview for update quantity
        TextView update_tv = new TextView(this);
        update_tv.setWidth(15);
        update_tv.setGravity(Gravity.CENTER);
        update_tv.setText("\n Update");

        // set OnClickListener t to update quantity
        update_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UNIT_MATCHED = true;

                // create edittext defined id in xml file
                EditText et = (EditText) findViewById(R.id.qty_edit_text);

                // create spinner defined id in xml file
                Spinner spinner = (Spinner) findViewById(R.id.spinner_id);

                // get product ID
                TextView current_Product_code = (TextView) tableRow.getChildAt(0);
                String p_code = current_Product_code.getText().toString();

                // declare linearlayout object to get quantity from table
                LinearLayout currentQYT_linerLayout = (LinearLayout) tableRow.getChildAt(2);

                // cast linearlayout to edittext object
                EditText currentQTY = (EditText) currentQYT_linerLayout.getChildAt(0);

                // cast linearlayout to spinner
                Spinner currentUnit = (Spinner) currentQYT_linerLayout.getChildAt(1);

                /* condition check whether edittext and spinner value is empty or value is
                        not greater than zero then message will shown to user */
                if (et.getText().toString().equals("0") || et.getText().toString().equals("") ||
                        et.getText().toString().isEmpty() || et.getText().toString().equals(" ")) {

                    UNIT_MATCHED = false;

                    // show message to user
                    Toast.makeText(Order_Details.this, "Please write quantity",
                            Toast.LENGTH_LONG).show();
                } else {
                    // convert unit into int format
                    int update_qty = Integer.parseInt(currentQTY.getText().toString());
                    // get current unit selected by uder
                    String unitValue = currentUnit.getSelectedItem().toString();
                    // replace space by hyphen
                    String pc = p_code.replace("\n", "-");
                    // get id of particular item added into cart
                    String id = product_code_id_map.get(pc);
                    // set qty , units and id
                    getTotalPrice(update_qty, unitValue, id);
                    // display toast message
                    ToastMessage();
                }
            }
        });
        // return textview object
        return update_tv;
    }

    /********************************************************************************************
     * Name:- set_image
     * Input :- pass product name nd table row object
     * Output :- return linear layout object
     * Description :- set delete image to single row from table
     ********************************************************************************************/
    public LinearLayout set_image(final String product_name, final TableRow tableRow) {

        // create linearlayout object
        LinearLayout linearLayout_Image = new LinearLayout(this);

        // set Imageview icon to delete particular row
        ImageView iv = new ImageView(this);
        // set delete image in background
        iv.setBackgroundResource(R.drawable.newdelete);
        // set OnClickListener on delete image
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set textview to get  id of cuurent row
                TextView tv = (TextView) tableRow.getChildAt(0);

                // get product code from textview
                String Prod_code = tv.getText().toString();
                Prod_code = Prod_code.replace("\n", "-");
                // get product id mapped from product code
                String prod_id = product_code_id_map.get(Prod_code);
                // remove data from tablerow
                orderTable.removeView(tableRow);
                // remove data from hashpmap
                g.getFinal_order().remove(prod_id);
                // display toast message to user
                Toast.makeText(Order_Details.this, "Remove " + product_name + " from your oder", Toast.LENGTH_LONG).show();
            }
        });
        // add delete image in linearlayout
        linearLayout_Image.addView(iv);
        return linearLayout_Image;
    }

    /*******************************************************************************************
     * Name :- updateDataAll
     * Description :- onclick of update icon all quantity will be updated
     * into hashmap and in tablerow and create menu item on action bar
     *******************************************************************************************/
    public void updateDataAll(MenuItem item) {

        UNIT_MATCHED = true;

        // create object for tablerow
        TableRow tableRow = new TableRow(this);

        // set id for tablerow
        tableRow.setId(R.id.tableRow_id);

        for (int i = 1; i < orderTable.getChildCount(); i++) {

            // add row through getChildAt method into tablerow
            tableRow = (TableRow) orderTable.getChildAt(i);

            TextView current_Product_code = (TextView) tableRow.getChildAt(0);
            String p_code = current_Product_code.getText().toString();
            String pc = p_code.replace("\n", "-");

            // get edittext of quantity fromtablerow into linearlayout object as currentQTYS
            LinearLayout currentQTYs = (LinearLayout) tableRow.getChildAt(2);

            // cast from linearlayout object to edittext object
            EditText currentQTY = (EditText) currentQTYs.getChildAt(0);
            Spinner currentUnit = (Spinner) currentQTYs.getChildAt(1);

            String qty = currentQTY.getText().toString();
            String unit = currentUnit.getSelectedItem().toString();

            if (qty.equals("0") || unit.equals("None") || qty.equals("")) {

                UNIT_MATCHED = false;
            } else {
                getTotalPrice(Integer.parseInt(qty), unit, product_code_id_map.get(pc));
            }
        }
        // show toast message to user that "Your quantity has been updated successfully"
        ToastMessage();
    }


    /*****************************************************************************************
     * Name :- ToastMessage
     * Description :- Check correct input according to message will be shown by user
     *****************************************************************************************/
    public void ToastMessage() {

        // Check condition boolean value will be true according to action will be performed
        if (UNIT_MATCHED) {

            // Toast message will be show by user
            Toast.makeText(Order_Details.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {

            // Toast message will be show by user
            Toast.makeText(this, "Unit or Price is not entered correctly", Toast.LENGTH_LONG).show();
        }
    }

    /*************************************************************************************
     * Name :- getTotalPrice
     * Input :- Pass Order_Detail_Object and Id to calculate price
     * Description :- get values from order_Detail_Object's object
     *************************************************************************************/
    public void getTotalPrice(int update_qty, String value_unit, String id) {

        // get corresponding data from ProductHashMap through Item name  store into Product Object
        String p_name = g.getFinal_order().get(id).getItem();
        if (p_name.contains("{") && p_name.contains("}")) {
            p_name = p_name.substring(0, p_name.indexOf("{"));
        }
        Product product = g.getProductsHashMap().get(p_name);

        // get rate_quantity converted string into integer
        Integer Rate_QTY = Integer.parseInt(product.getRate_Quantity());

        // get Master_PKG_Unit converted string into integer
        Integer Master_PKG_Unit = Integer.parseInt(product.getMaster_PKG());

        // get STD_PKG_Unit converted string into integer
        Integer STD_PKG_Unit = Integer.parseInt(product.getSTD_PKG());

        // get rate value into float type
        //Float rate = Float.valueOf(product.getRate());

        // calculate ActualRate
        //Float ActualRate = rate / Rate_QTY;
        Float ActualRate = 0F;

        // call function to calculate rate
        calculatePrice(product, value_unit, Master_PKG_Unit, STD_PKG_Unit, update_qty, ActualRate, id);
    }

    /*******************************************************************************************
     * Name :- calculatePrice
     * Input :- Pass values Product product,String Unit_Value,Integer Master_PKG_Unit,
     * Integer STD_PKG_Unit,Integer update_QTY,Float ActualRate,String id.
     * Description :- Calculate value according to unit_value entered by user
     *******************************************************************************************/
    public void calculatePrice(Product product, String Unit_Value, Integer Master_PKG_Unit,
                               Integer STD_PKG_Unit, Integer update_QTY, Float ActualRate, String id) {

            /* condition checks whether user puts unit_value matches to product's unit
                if condition will be true then it will proceed and calculate Total price
                according to unit and quantity otherwise,message will be show to user. */
        if (UNIT_MATCHED && Unit_Value.equals(product.getMaster_PKG_Unit())) {

            // calculate Master_rate
            int Master_Rate = update_QTY * Master_PKG_Unit * STD_PKG_Unit;

            // calculate final price
            Float finalPrice = Master_Rate * ActualRate;
            //g.getFinal_order().get(id).setPrice(finalPrice);
            g.getFinal_order().get(id).setQuantity(String.valueOf(update_QTY));
            g.getFinal_order().get(id).setUnit(Unit_Value);
            UNIT_MATCHED = true;
        } else if (UNIT_MATCHED && Unit_Value.equals(product.getSTD_PKG_Unit())) {

            // calculate std_rate
            int STD_Rate = update_QTY * STD_PKG_Unit;
            // calculate final price
            Float finalPrice = STD_Rate * ActualRate;
            //g.getFinal_order().get(id).setPrice(finalPrice);
            g.getFinal_order().get(id).setQuantity(String.valueOf(update_QTY));
            g.getFinal_order().get(id).setUnit(Unit_Value);
            UNIT_MATCHED = true;
        } else if (UNIT_MATCHED && Unit_Value.equals(product.getBasic_Unit())) {

            // calculate Basic unit price
            Float finalPrice = update_QTY * ActualRate;
            //g.getFinal_order().get(id).setPrice(finalPrice);
            g.getFinal_order().get(id).setQuantity(String.valueOf(update_QTY));
            g.getFinal_order().get(id).setUnit(Unit_Value);
            UNIT_MATCHED = true;
        } else {

            UNIT_MATCHED = false;
        }
    }

    /**************************************************************************************
     * Name :- setAddAccounts_orderButton
     * Description :- Call Review page on click of review button
     **************************************************************************************/
    public void setAddAccounts_orderButton(final int count_row) {  //-------------------------------------------------------------->>> Needs Tuning

        // set listener on review button
        addAccount_orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count_row > 0) {
                    // set boolean vaue
                    boolean flagvalue = true;
                    // create intent object to call another page
                    Intent call_reviewpage = new Intent(Order_Details.this, Account_Details.class);
                    // pass boolean value
                    call_reviewpage.putExtra("review_flag", flagvalue);

                    if (UNIT_MATCHED) {
                        // create object for tablerow
                        TableRow tableRow = new TableRow(Order_Details.this);

                        // set id for tablerow
                        tableRow.setId(R.id.tableRow_id);

                        for (int i = 1; i < orderTable.getChildCount(); i++) {

                            // add row through getChildAt method into tablerow
                            tableRow = (TableRow) orderTable.getChildAt(i);

                            TextView current_Product_code = (TextView) tableRow.getChildAt(0);
                            String p_code = current_Product_code.getText().toString();
                            String pc = p_code.replace("\n", "-");

                            // get edittext of quantity fromtablerow into linearlayout object as currentQTYS
                            LinearLayout currentQTYs = (LinearLayout) tableRow.getChildAt(2);

                            // cast from linearlayout object to edittext object
                            EditText currentQTY = (EditText) currentQTYs.getChildAt(0);
                            Spinner currentUnit = (Spinner) currentQTYs.getChildAt(1);

                            String qty = currentQTY.getText().toString();
                            String unit = currentUnit.getSelectedItem().toString();

                            if(qty.equals("0") || qty.equals("") || qty.isEmpty() || qty.equals(" ")) {

                                Toast.makeText(Order_Details.this,
                                        "Please write quantity", Toast.LENGTH_LONG).show();
                            }else {
                                getTotalPrice(Integer.parseInt(qty), unit, product_code_id_map.get(pc));
                            }
                        }
                        // start activity by using intent object
                        startActivity(call_reviewpage);
                    } else {
                        Toast.makeText(Order_Details.this, "Please update your quantity and unit", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Order_Details.this,"Your cart is empty!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order__details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            // case call when user click on sign out icon
            case R.id.Sign_out_orderpage:
                Intent signOut_intent = new Intent(Order_Details.this, Login_form.class);

                /* SharedPreferences remove data if user  signout*/
                SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPref.edit();
                editor.clear();
                editor.commit();
                finish();
                Globals g = (Globals) getApplication();
                g.getFinal_order().clear();
                startActivity(signOut_intent);
                break;
        }
        //no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}