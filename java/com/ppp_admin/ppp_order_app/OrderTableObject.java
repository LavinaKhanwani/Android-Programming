package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class OrderTableObject extends Activity
{
    /*******************************************************************************************
     * Section for declaration of the variables
     *******************************************************************************************/
      Globals globals_object;
      Account_Master AccountMasterDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table_object);

        AccountMasterDetails = (Account_Master) getIntent().getSerializableExtra("AccountMaster");
        createOrderItemObjects();
    }
     /****************************************************************************************
        Name :- createOrderItemObjects
        Description :- Calculate total price and total quantity to store in order Table.
      ******************************************************************************************/
      public void createOrderItemObjects() {

        // get globals context
        globals_object = (Globals) getApplication();

        // create ArrayList<Order_Item> to store data
        ArrayList<Order_Item> order_items = new ArrayList<Order_Item>();

        // create iterator object from map.entry hashmap
        Iterator<Map.Entry<String,Order_Detail_Object>> itr;

        // store values from globals's final_order
        itr = globals_object.getFinal_order().entrySet().iterator();

        // create float object
        float totalPrice = 0;

        // create int object
        int totalQty = 0;

        /* While loop iterate from Iterator object and calculate total price and total quantity */
        while(itr.hasNext()){

            // Map.Entry use to iterate next operation
            Map.Entry<String, Order_Detail_Object> current = itr.next();

            // create order_Item Object
            Order_Item item = new Order_Item();

            // create Order_Detail_Object to get current value
            Order_Detail_Object orderDetailObject = current.getValue();

            // set productID into order_item object
            item.setProductID(current.getKey());

            // set price to order_Item object
            item.setPrice(orderDetailObject.getPrice());

            // set quantity to order_item object
            item.setQuantity(orderDetailObject.getQuantity() + " " + orderDetailObject.getUnit());

            // add all item into ArrayList<Order_Item> Object
            order_items.add(item);

            //Calculate total quantity
             totalPrice = totalPrice + orderDetailObject.getPrice();

            // Calculate total quantity
            totalQty = totalQty + Integer.parseInt(orderDetailObject.getQuantity());
        }
          // call function to set value into OrderTable database
          createOrderTableObject(totalQty,totalPrice, order_items);
      }

     /******************************************************************************************
        Name :- createOrderTableObject
        Input :- Pass totalprice , totalquantity , ArrayList<Order_Item> object to set values
        Description :- Set all values into OrderTable object
      *****************************************************************************************/
      public void createOrderTableObject(int totalQty, float totalPrice, ArrayList<Order_Item> order_items) {

          // create OrderTable object
          OrderTable setOrderTableValue = new OrderTable();

          SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
          String AgentID = loginSharedPref.getString("ID", "");
          String Agent_EmailID = loginSharedPref.getString("emailID","");

          // store account id into string variable
          String AccountID = AccountMasterDetails.getID();

          // create object of calender
          Calendar c = Calendar.getInstance();

          // create date format
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

          // convert date into date format to get current date and time
          String CurrentDate = df.format(c.getTime());

          //Get Narration and Shipping Details
          String shipping_details = getIntent().getStringExtra("ShippingDetails");
          String narration = getIntent().getStringExtra("Narration");

          /* set all values into orderTable Object */
          setOrderTableValue.setAccountID(AccountID);
          setOrderTableValue.setAgentID(AgentID);
          setOrderTableValue.setDateTime(CurrentDate);
          setOrderTableValue.setPrice(totalPrice);
          setOrderTableValue.setTotal_Qty(totalQty);
          setOrderTableValue.setShippingAddress(shipping_details);
          setOrderTableValue.setNarration(narration);

          // call function to insert into order Table database
          InsertTableValuesOrderTable(setOrderTableValue, order_items, Agent_EmailID, AgentID);
      }

      /******************************************************************************************
        Name :- InsertTableValuesOrderTable
        Input :- Pass OrderTable object and ArrayList's object to insert values into database
        Description :- Insert data into OrderTable database
      ******************************************************************************************/
      public void InsertTableValuesOrderTable(final OrderTable setOrderTableValue,
                               final ArrayList<Order_Item> order_items ,final String Agent_EmailID,String AgentId)
      {
          // create int object
          int ref_nbr = 0;

            /* create object of ProgressDialog bar object */
          final ProgressDialog show_progressBar = new ProgressDialog(OrderTableObject.this);

          // pass message to show on progressbar
          Utility.show_progress_bar(show_progressBar, "Data Loading....");
          String TodaysDate = Get_current_date();
          Cursor  Order_cursor =  getContentResolver().query(GlobalProvider.CONTENT_URI_OrderTable,
                  new String[]{"MAX(" + GlobalProvider.OrderTable_ReferenceNo + ") AS Max_Ref_No"},
                  "AgentID = ? AND DateTime = ?", new String[]{AgentId, TodaysDate}, null);

          if(Order_cursor.moveToFirst()) {
              String refNo = Order_cursor.getString(0);
            /* check condition  currentData id empty if its true proceed
               further otherwise else part will be execute */
              if (refNo == null) {
                  ref_nbr = 1;
              } else {

                  // increment ref_nbr by adding 1
                  ref_nbr = Integer.parseInt(refNo)+ 1;
              }
          }
          // insert value into orderTable local database
          setOrderTableValue.setRefNo(ref_nbr);

          // create object of ContentValues
          ContentValues OTValuesTOLocalDB = new ContentValues();

          // set values of Order Table
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_ReferenceNo,setOrderTableValue.getRefNo());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_AccountID,setOrderTableValue.getAccountID());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_AgentID,setOrderTableValue.getAgentID());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_DateTime,setOrderTableValue.getDateTime());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_Price,setOrderTableValue.getPrice());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_TotalQTY,setOrderTableValue.getTotal_Qty());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_Transport,setOrderTableValue.getTransport());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_ShippingAdd,setOrderTableValue.getShippingAddress());
          OTValuesTOLocalDB.put(GlobalProvider.OrderTable_Narration,setOrderTableValue.getNarration());

          String OrderID = new String();
          // insert value into OrderTable in local database
          try {

              Uri uri_OrderTable = getContentResolver().insert(GlobalProvider.CONTENT_URI_OrderTable, OTValuesTOLocalDB);
              OrderID =  uri_OrderTable.getLastPathSegment();
              Log.i("Order Table " , "Order has been insert into order table successfully");
          }catch (Exception e)
          {e.printStackTrace();}

            /* insert multiple items into local orderItem database*/
            for (int i=0; i < order_items.size();i++) {
                order_items.get(i).setOrderID(Integer.parseInt(OrderID));

                // create object of ContentValues
                ContentValues OIValuesTOLocalDB = new ContentValues();

                // set values of Order Item
                OIValuesTOLocalDB.put(GlobalProvider.OrderItem_ProductID,order_items.get(i).getProductID());
                OIValuesTOLocalDB.put(GlobalProvider.OrderItem_Quantity,order_items.get(i).getQuantity());
                OIValuesTOLocalDB.put(GlobalProvider.OrderItem_Price,order_items.get(i).getPrice());
                OIValuesTOLocalDB.put(GlobalProvider.OrderItem_OrderID,order_items.get(i).getOrderID());

                // insert order item into local orderTable database
                Uri OI_uri = getContentResolver().insert(GlobalProvider.CONTENT_URI_OrderItem,OIValuesTOLocalDB);
                Log.i("Order Table " , "Order item has been insert into order item table successfully");
            }

            // creating Globals's object
              Globals g = (Globals) getApplication();

            // set OrderNumber globally
             g.setRef_No(ref_nbr);

            // call function display data on activity
             DisplayDetails(setOrderTableValue, Agent_EmailID);

            // call function to show table value
            ConfirmOrder_Process(setOrderTableValue);

            // dismiss progress bar
            show_progressBar.dismiss();
      }
    /***********************************************************************
     Name :- Get_current_date
     Output:- returns today's date in string format
     Description :- converting today;s date into 'yyyy-mm-dd' format
     ***********************************************************************/
    public String Get_current_date() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat today_date = new SimpleDateFormat("yyyy-MM-dd");
        String str_today_Date = today_date.format(c.getTime());
        // return current date
        return str_today_Date;
    }

    /******************************************************************
        Name :- ConfirmedOrderSendEmail
        Input :- Pass value of Float
        Description :- Send mail to Agent and company of orders.
     ******************************************************************/
     public void ConfirmedOrderSendEmail(String ProductCode,OrderTable setorderTableValues) {

         OrderTable ordertableValues = setorderTableValues;

         // create String variable to store email id
         String From_EmailID = getString(R.string.Comman_EmailId_from);

         // create string variable to store password
         String Password = getString(R.string.Comman_Password_password);

         // create string variable to store email id of TO
        String To_EmailID = "pressfitindia.pr@gmail.com";

        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        String AgentEmailID = loginSharedPref.getString("emailID","");

        // create List to send email store to many emails of CC
        List<String> CC_EmailID = new ArrayList<String>();

        // add all CC emails into list
        CC_EmailID.add(AgentEmailID);

         // define string to store subject
        String Subject = "Order has been placed";

        globals_object = (Globals) getApplication();

         //  Define strings
        String Product_Code = new String() ;
        String PartyName = new String();
        String City = new String();
        String ItemName = new String();
        String Quantity = new String();
        Float Rate = new Float(0.0f);
        String Transport = new String();
        String ContactNo = new String();
        String Unit_QTY = new String();
        ArrayList<String> body = new ArrayList<String>();

        // declare Iterator variable to get hashmap data into it.
        final Iterator<Map.Entry<String,Order_Detail_Object>> iterator = globals_object.getFinal_order().entrySet().iterator();

         // create string to store body of order into string
        String orderdetails = "<html>" +
                " <body> \n" +
                "  <center><div style= \"background-color: #ffb732; width: 75%; height: 3%; padding-top : 2%;padding-bottom: 2%; text-align: center;  \n" +
                "     color: white; font-family : Lucida Calligraphy; font-size: 200%; align: center\"> PF \n" +
                " </div>  </center>\n" +
                " <br><br><br>\n" +
                " <center><div style= \"background-color: #FFFFFF; text-align: left ; width: 75%; font-family: Times New Roman;font-size: 120% ;color: black\">  \n" +
                " <br> <b> &nbsp;&nbsp;&nbsp; New order has been receieved <br> \n" +
                " <br> <b> &nbsp;&nbsp;&nbsp; Product Details </b> <br><br>\n" +
                " <table border = 1 align = left-side cellpadding= 10% cellspacing = 10% >" +
                " <tr>" +
                "<th> Product Code </th> \n" +
                " <th>Product Name </th> \n" +
                " <th>Quantity </th> \n" +
                " <th> Rate </th>  \n" +
                " </tr>\n";

        while (iterator.hasNext()) {

            // declare Map.Entry object to iterate loop
            Map.Entry<String, Order_Detail_Object> current = iterator.next();

            // Get all product code of particular item name
            String Item_Name = current.getValue().getItem();
            if(Item_Name.contains("{") && Item_Name.contains("}")){
                Item_Name = Item_Name.substring(0,Item_Name.indexOf("{"));
            }
            Product_Code = globals_object.getProductsHashMap().get(Item_Name).getProduct_code();
            // get all values from final_order object
            ItemName = current.getValue().getItem();
            Quantity = current.getValue().getQuantity();
            Unit_QTY = current.getValue().getUnit();
            Transport = AccountMasterDetails.getTransport();
            PartyName = AccountMasterDetails.getAccountName();
            City = AccountMasterDetails.getCity();
            ContactNo = AccountMasterDetails.getContactPersonMobileNo();

            // Through Iterator add all orders of product into table-
            orderdetails = orderdetails +
                    " <tr>\n" +
                    "<td> " + Product_Code + "</td>\n"  +
                    "  <td> " + ItemName + "</td>\n" +
                    "  <td>" +  Quantity + "  " + Unit_QTY + "  " + "</td>\n" +
                    " <td>" + Rate + "</td>\n" +
                    " </tr>\n";
        }
        orderdetails = orderdetails +  " </table>\n" +
                " <br> <br> \n" +
                " <b> Total Amount : 0 </b><br><br>\n" +
                " <b> Customer Details : </b> </font> <br> <br>\n" +
                " <b> Party Name :- "+  PartyName  + "</b> <br>\n" +
                " <b> City :- " + City + "</b> <br> \n" +
                " <b> Contact No :- " +  ContactNo +  "</b> <br> <br>\n" +
                " <b> Transport : "  + Transport + "</b> <br> <br>\n"+
                " <b> Shipping Address  : " + ordertableValues.getShippingAddress() + "</b> <br>\n"+
                " <b> Narration : " + ordertableValues.getNarration() + "</b> <br> <br>\n"+
                "</div></center></body></html>" +
                "</div></body></html>";
        body.add(orderdetails);

        StringBuilder sb = new StringBuilder();
        for (String s : body) {
            sb.append(s);
            sb.append("\n");
        }

         // callSendMailTask to execute or send mail
        new SendMailTask_ConfirmOrder().execute(From_EmailID, Password, To_EmailID, CC_EmailID, Subject, sb);

         // show toast msg to user
        Toast.makeText(OrderTableObject.this, "Your order han been mailed", Toast.LENGTH_SHORT).show();

        Call_Home_page();
    }
    /******************************************************************************************
        Name :- Call_Home_page()
        Description :- Call home when all orders has been placed successfully.
     *******************************************************************************************/
     public void Call_Home_page ()
     {
         /* set duration to call another activity */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Intent call another activity */
                Intent call_home_page = new Intent(OrderTableObject.this, Home_Page.class);
                startActivity(call_home_page);
            }
        }, 8000);

     }

    /********************************************************************************************
       Name :- DisplayDetails
       Input :- Pass OrderTable Object show values and do calculation
       Description :- Show all Order details to user and mail will be sent by user automatically
     ********************************************************************************************/
     public void DisplayDetails(OrderTable  setOrderTableValue , String Agent_EmailID)
     {
        /* create Integer object to store orderNumber */
        Integer Ref_No = setOrderTableValue.getRefNo();

        /* Create textview objects to set all details regarding customer  */
        TextView showOrderNumber_textView = (TextView) findViewById(R.id.showOrderNumber_textView);
        TextView showOrderPartyName_textView = (TextView) findViewById(R.id.showOrderName_textView);
        TextView showOrderCity_textView = (TextView) findViewById(R.id.showOrderCity_textView);
        TextView showTransportName = (TextView) findViewById(R.id.showTransportName_textView);
        TextView showShippingDetails = (TextView) findViewById(R.id.ShippingDetails_textview);
        TextView showNarrationDetails = (TextView) findViewById(R.id.showNarration_textView);

        /* set details into to textview */
        showOrderNumber_textView.setText(Ref_No.toString());
        showOrderPartyName_textView.setText("   " + AccountMasterDetails.getAccountName());
        showOrderCity_textView.setText("    " + AccountMasterDetails.getCity());
        showTransportName.setText(" " + AccountMasterDetails.getTransport());
        showShippingDetails.setText(" " + setOrderTableValue.getShippingAddress());
        showNarrationDetails.setText("  " + setOrderTableValue.getNarration());

        /* create textviwe object to show mail details to user */
        TextView showEmails = (TextView) findViewById(R.id.showEmailID_textView);
        showEmails.setText( "-->  " + "sales@pressfitindia.com" + "\n--> " + Agent_EmailID);

        /* create textview object show final amount to user */
        TextView showTotalPrice = (TextView) findViewById(R.id.showAmountPay_textView);
        showTotalPrice.setText("RS. 0 " );
     }

    /******************************************************************************
       Name :- ConfirmOrder_Process
       Description :- Show Order Details into table
     *******************************************************************************/
     public void ConfirmOrder_Process( OrderTable setorderTableValues)
     {
        // declare global variable
        Globals g = (Globals) getApplication();

        // create tablelayout that defiend into xml file
        final TableLayout orderTable_showDetail = (TableLayout) findViewById(R.id.orderTable_tableLayout);

        // declare Iterator variable to get hashmap data into it.
        final Iterator<Map.Entry<String,Order_Detail_Object>> iterator = g.getFinal_order().entrySet().iterator();
         String product_code = new String();
        while (iterator.hasNext()) {

            // declare Map.Entry object to iterate loop
            Map.Entry<String, Order_Detail_Object> current = iterator.next();

            // create tablerow object
            final TableRow OrderShow_tableRow = new TableRow(this);

            // get current product code
            product_code = get_product_code(current);

            // set product code in textview
            TextView Product_Code_textView = set_product_code_textview(product_code);

            // get current product name to set in a current row of table
            TextView itemName_textView = set_product_name(current);

            // get qty,units of current row
            LinearLayout linearLayout = set_qty_units(current);

            // get price of product for current row
            TextView totalRate_textview = set_price_of_product();

            // add all items into review_tableRow
            OrderShow_tableRow.addView(Product_Code_textView, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            OrderShow_tableRow.addView(itemName_textView, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            OrderShow_tableRow.addView(linearLayout, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            OrderShow_tableRow.addView(totalRate_textview, new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f));

            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);

            // add review_tableRow into table layout
            orderTable_showDetail.addView(OrderShow_tableRow, rowParams);
        }
        ConfirmedOrderSendEmail(product_code,setorderTableValues);

        // g.setOrderNumber(null);
        g.getFinal_order().clear();
     }

    /*********************************************************************************************
        Name :- get_product_code
        Input :- pass current detail of order_details_object
        Output :- return product code as string
        Description :- get product code according to current product name
    **********************************************************************************************/
     public String get_product_code(Map.Entry<String, Order_Detail_Object> current){

         // declare global variable
         Globals g = (Globals) getApplication();

         // Get current product name
         String ItemName = current.getValue().getItem();

         /* check product name contain braces or not */
         if(ItemName.contains("{") && ItemName.contains("}")){

             ItemName = ItemName.substring(0,ItemName.indexOf("{"));
         }
         // get current product code globals hashmap of producthashmap
         String product_code = new String();

         // update the product name on global hashmap of product
         product_code = g.getProductsHashMap().get(ItemName).getProduct_code();

         // return product code as string
         return product_code;
     }

    /********************************************************************************************
        Name :- set_product_code_textview
        Input :- return textview object to set product code in current row of table
        Output :- pass current product code
        Description :- set product code in a textview of current row of table
    *********************************************************************************************/
    public TextView set_product_code_textview(String product_code){

        // get product code to set in textview
        TextView Product_Code_textView  = new TextView(this);
        Product_Code_textView.setGravity(Gravity.CENTER);
        //String[] Product_code = product_code.split("-");
        Product_Code_textView.setText(product_code);
        Product_Code_textView.setPadding(0, 0, 0, 10);

        // return textview object
        return Product_Code_textView;
    }

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
        Input :- pass current details of Order_Detail_Object from Map.Entry
        Output :- return LinearLayout object contains qty ans units
        Description :- set quantity and units entered by user on order details page
     *********************************************************************************************/
    public  LinearLayout set_qty_units(Map.Entry<String, Order_Detail_Object> current){

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

        // return LinearLayout object
        return linearLayout;
    }

    /* *******************************************************************************************
        Name :- set_price_of_product
        Output :- return textview object contains price
        Description :- set price of product in textview of current row of table
     *******************************************************************************************/
    public TextView set_price_of_product(){

        // set price of prduct
        TextView totalRate_textview  = new TextView(this);
        totalRate_textview.setGravity(Gravity.CENTER);
        totalRate_textview.setText("Rs. 0 " );
        totalRate_textview.setPadding(0, 0, 0, 10);
        return totalRate_textview;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_table_oject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.HomePage_OrderTablePage: Intent callHomepage_intent =
                    new Intent(OrderTableObject.this, Home_Page.class);
                startActivity(callHomepage_intent);
                break;

            case R.id.singOut_OrderTablePage: Intent callSignOutPage_intent =
                    new Intent(OrderTableObject.this, Login_form.class);
                SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPref.edit();
                editor.clear();
                editor.commit();
                finish();
                Globals g = (Globals) getApplication();
                g.getFinal_order().clear();
                startActivity(callSignOutPage_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
