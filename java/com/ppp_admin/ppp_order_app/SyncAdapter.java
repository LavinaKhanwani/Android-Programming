package com.ppp_admin.ppp_order_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 4/13/2016.
 */
    public class SyncAdapter extends AbstractThreadedSyncAdapter {

    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    private AccountManager mAccountManager;
    private ContentProviderClient providerClient;
    private MobileServiceTable<Account_Master> ShowAccountsData;
    private MobileServiceTable<OrderTable> GetOrderTableData;
    private MobileServiceTable<Lists> mobileServiceTable_list;
    private MobileServiceTable<List_Items> mobileServiceTable_LI;
    private MobileServiceTable<Product> ShowProductData;
    private MobileServiceTable<Order_Item> GetOrderItemeData;

    /********************************************************************************
     Name :-  HashMap<String, Product>
     Key :-  Product_code
     Description :- store product data from server into hasmap Product object.
     *******************************************************************************/
    HashMap<String, Product> serverProductsHashMap = new HashMap<>();
    /*******************************************************************************
     Name :- HashMap<String, Account_Master>
     Key :- AccountId
     Description :- Store Account_Master data from  accounts
     ******************************************************************************/
    HashMap<String, Account_Master> serverAccountsHashMap = new HashMap<>();
    /******************************************************************************************
     Name :- HashMap<Integer, ArrayList<Order_Item>>
     Key :- OrderID
     Description :- Store order id into local database
     *******************************************************************************************/
    HashMap<Integer, ArrayList<Order_Item>> OI_Local_HashMap = new HashMap<>();
    /*******************************************************************************************
     Name :- HashMap<String,Lists>
     Key :- list increment ,agent id
     Description :- Store key as list incr and agent id and list values.
     ***************************************************************************************/
     HashMap<String , Lists> server_List_HashMap  = new HashMap<>();
    /***************************************************************************************
     Name :- HashMap<String,Lists>
     Key :- list increment ,agent id
     Description :- Store key as list incr and agent id and list values.
     *******************************************************************************************/
    HashMap<String,Lists> local_List_Hashmap;
    /*******************************************************************************************
     Name :-  HashMap<String, ArrayList<String>>
     Key :-  list increment ,agent id
     Description :- Store key as list incr and agent id and product_id and deleted status values.
     *********************************************************************************************/
    HashMap<String, ArrayList<String>> LI_Local_HashMap = new HashMap<>();
    /*********************************************************************************************
     Name :- HashMap<String, ArrayList<String>>
     Key :- list increment ,agent id
     Description :- Store key as list incr and agent id and id, product_id and deleted status values.
     **********************************************************************************************/
     HashMap<String, ArrayList<String>> server_LI_HashMap = new HashMap<>();

    /********************************************************************************************
     Name :- HashMap<String,String>
     Key :- list_increment,agentid
     Value :- delete status
     *********************************************************************************************/
    HashMap<String,String> local_LI_delete_status = new HashMap<>();
    // create constructor
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        Log.i("SYNC ADAPTER:", "Entered OnPerformSync Function");

        Log.i("MSG", "Establishing Connection with the server");
        providerClient = provider;

        // Establish connection to server
        EstablishConnection();

          /*  condition checks uriString matches with defined string if it
             will be true further process will be done. */
        if (extras.containsKey("URI")) {

            String uriString = extras.getString("URI");
            if (uriString.equals("Product")) {
                // Get data from the remote server
                getAllProductData();
            } else if (uriString.equals("AccountMaster")) {
                String AgentID = extras.getString("AgentID");
                Log.i("AccountMaster:-", "Syncing Accounts Table");
                getAllAccountsDataServer(AgentID);
            } else if (uriString.equals("OrderTable")) {
                try {
                    // Getting ArrayList of OrderTable from local database
                    ArrayList<OrderTable> OT_LocalData_Array  = getOrderTableValueFromLocal();
                    // Getting OrderItem Data from local database
                    getOrderItemValueFromLocal();
                    // Getting ArrayList of AgentID from local database
                    ArrayList<String> agent_ids_list = create_agent_ids_list();
                    new getOrderTableData().execute(new Order_Agent_Info(agent_ids_list,OT_LocalData_Array));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (uriString.equals("ListTable")) {

                String AgentID = extras.getString("AgentID");
                try {
                    // crete hashmap of List table from local db
                    local_List_Hashmap = getListsFromLocalDB(AgentID);
                    Log.i("List :- ", "Data retrieved from local list table successfully");

                    // create hashmap of List Item table from local db
                    getList_Item_from_Local_DB(AgentID);
                    Log.i("List Items :-", "Data retrieved from local list item table successfully");

                    // create hashmap of List table from server db
                    getAllListDataFromServer(AgentID);
                    Log.i("List :- ", " Sync for list and list items completed successfully");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /********************************************************************************************
     * Name :- getProductDatafromLocal
     * Input :- pass ContentProviderClient object
     * OutPut :- returning HashMap object.
     * Description :- function call to get all product values from local to hashmap object.
     ********************************************************************************************/
    public HashMap getProductDatafromLocal(ContentProviderClient provider) {
        // create hashmap object
        HashMap localProductHashMap = new HashMap<String, Product>();
        try {
            Log.i("Product", "Number of records in local database :- " + localProductHashMap.size());

            // Get shows from the local storage
            Cursor cursor = provider.query(GlobalProvider.CONTENT_URI_Product, null, null, null, null);

            /* condition check if cursor is empty or not according to that condition will be proceed */
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    // get all values from GlobalProvider class and store into string object
                    String productCode = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Product_code));
                    String Product_name = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Name));
                    String Product_Rate = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Rate));
                    String Product_Rate_Quantity = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Rate_Quantity));
                    String Product_STD_PKG = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_STD_PKG));
                    String Product_STD_PKG_Unit = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_STD_PKG_Unit));
                    String Product_Master_PKG = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Master_PKG));
                    String Product_Master_PKG_Unit = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Master_PKG_Unit));
                    String Product_Basic_Unit = cursor.getString(cursor.getColumnIndex(GlobalProvider.Product_Basic_Unit));

                    // set all product values into Product object
                    Product setProductValueLocal = new Product();
                    setProductValueLocal.setProduct_code(productCode);
                    setProductValueLocal.setName(Product_name);
                    setProductValueLocal.setRate(Product_Rate);
                    setProductValueLocal.setRate_Quantity(Product_Rate_Quantity);
                    setProductValueLocal.setSTD_PKG(Product_STD_PKG);
                    setProductValueLocal.setSTD_PKG_Unit(Product_STD_PKG_Unit);
                    setProductValueLocal.setMaster_PKG(Product_Master_PKG);
                    setProductValueLocal.setMaster_PKG_Unit(Product_Master_PKG_Unit);
                    setProductValueLocal.setBasic_Unit(Product_Basic_Unit);

                    // put all product values into hashmap object using key as Product_code
                    localProductHashMap.put(productCode, setProductValueLocal);
                }
                // close cursor
                cursor.close();
                Log.i("Product:", "Product Local HashMap created successfully");
            }
        } catch (RemoteException e) {
            Log.i("Product: ", "There seems to be an ERROR in the local map creation process");
            e.printStackTrace();
        }

        // return HashMap object
        return localProductHashMap;
    }

    /********************************************************************************
     * Name :- EstablishConnection
     * Description :- Establish connection from server to get Product table .
     *********************************************************************************/
    public void EstablishConnection() {
        try {

            Log.i("Sync Adapter: ", "Establishing Connection with the server");
          /* Establish connection through MobileServiceClient object as mClient and passing ulr
             and key to context.*/
            MobileServiceClient mClient_showDetails = null;
            try {
                mClient_showDetails = new MobileServiceClient
                        ("https://pressfit-accounts.azure-mobile.net/", "SSXvnqKmdneRWFDZBdcyQCyiDwBHzc77", getContext());

                /* MobileServiceTable object by calling the getTable method on the MobileServiceClient.
                   getTable method use table name existing in database. */
                ShowProductData = mClient_showDetails.getTable("Product", Product.class);
                ShowAccountsData = mClient_showDetails.getTable("Account_Master", Account_Master.class);
                GetOrderTableData = mClient_showDetails.getTable("OrderTable", OrderTable.class);
                GetOrderItemeData = mClient_showDetails.getTable("Order_Item",Order_Item.class);
                mobileServiceTable_list = mClient_showDetails.getTable("Lists" , Lists.class);
                mobileServiceTable_LI = mClient_showDetails.getTable("List_Items",List_Items.class);

            } catch (MalformedURLException e1) {
                Log.i("Sync Adapter: ", "Seems to be an error in establishing Connection with the server");
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Log.i("Sync Adapter: ", "Seems to be an error in establishing Connection with the server");
            e.printStackTrace();
        }
    }

    /***************************************************************************************
     * Name :- getAllProductData
     * Description :- get all product values from server and store into Hashmap object
     * as "serverProductHashmap"  using key Product_code.
     ***************************************************************************************/
    public void getAllProductData() {

        new AsyncTask<Void, Void, Void>() {

            // declare object for  MobileServiceList<Product>
            MobileServiceList<Product> product_list;

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    Log.i("Product: ", "Retrieving Product Data from the server");

                    // get all products data by firing query
                    Integer pr_total_count = (ShowProductData.includeInlineCount().execute().get()).getTotalCount();
                    Log.i("Product","Total number of rows :- " + pr_total_count.toString());
                    Integer itemsToSkip = 0;

                    do{
                        if(product_list == null || product_list.isEmpty()){
                            product_list = ShowProductData.select().top(1000).execute().get();
                        }else{
                            product_list.addAll(ShowProductData.select().skip(itemsToSkip).top(1000).execute().get());
                        }
                        itemsToSkip = itemsToSkip + 1000;
                    }while(itemsToSkip < pr_total_count);

                    // get all product from  MobileServiceList<Product> object to Products object using loop
                    for (Product product : product_list) {
                        // put products data from hashmap to Product object
                        serverProductsHashMap.put(product.getProduct_code(), product);
                    }

                    Log.i("Product: ", "Server product Hashmap created successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    /*********************************************************************
                     --> Compare data between local and server.
                     --> Transfer the data that is not present on local from server.
                     *********************************************************************/
                    Log.i("Product: ", "Starting to retrieve Product data from Local");
                    HashMap<String, Product> localData = getProductDatafromLocal(providerClient);
                    // call function
                    Log.i("Product: ", "Starting to transfer Product data from server to local database");
                    transferProductDataFromServerToLocal(localData);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /*****************************************************************************************
     * Name :- transferProductDataFromServerToLocal
     * Input :- Pass HashMap object of Product
     * Description :- function call to transfer product data from server to local database of
     * Product table.
     *******************************************************************************************/
    public void transferProductDataFromServerToLocal(HashMap<String, Product> localData) throws RemoteException {

        // declare object of Iterator
        Iterator<Map.Entry<String, Product>> itr;

        // Iterate data from server HashMap object
        itr = serverProductsHashMap.entrySet().iterator();
        Log.i("Product: ", "Starting transfer from server to local");
        Log.i("Product:", "Number of records in local database :-" + localData.size());
        Log.i("Product:", "Number of records on server database :-" + serverProductsHashMap.size());
        Log.i("Product:", "Transferring " + String.valueOf(serverProductsHashMap.size() - localData.size())
                + " new products in local database");

        while (itr.hasNext()) {

            // add next data into current object
            Map.Entry<String, Product> current = itr.next();

            /* Condition checks local HashMap object contains key into server HashMap object.
               if it is not all product values will be get from GlobalProvider's Product and store
               into ContentValues object and if condition will be false then it will go in else part */
            if (!localData.containsKey(current.getKey())) {

                Log.i("Product:", "Adding new data");
                // Put data from server to local using key and value into local HashMap object
                localData.put(current.getKey(), current.getValue());

                // create ContentValues Object
                ContentValues copyvaluesLocalDB = new ContentValues();

                // get all product values from GlobalProvider' Product and store into ContentValues Object
                copyvaluesLocalDB.put(GlobalProvider.Product_ID, current.getValue().getID());
                copyvaluesLocalDB.put(GlobalProvider.Product_Product_code, current.getValue().getProduct_code());
                copyvaluesLocalDB.put(GlobalProvider.Product_Name, current.getValue().getName());
                copyvaluesLocalDB.put(GlobalProvider.Product_Rate, current.getValue().getRate());
                copyvaluesLocalDB.put(GlobalProvider.Product_Rate_Quantity, current.getValue().getRate_Quantity());
                copyvaluesLocalDB.put(GlobalProvider.Product_STD_PKG, current.getValue().getSTD_PKG());
                copyvaluesLocalDB.put(GlobalProvider.Product_STD_PKG_Unit, current.getValue().getSTD_PKG_Unit());
                copyvaluesLocalDB.put(GlobalProvider.Product_Master_PKG, current.getValue().getMaster_PKG());
                copyvaluesLocalDB.put(GlobalProvider.Product_Master_PKG_Unit, current.getValue().getMaster_PKG_Unit());
                copyvaluesLocalDB.put(GlobalProvider.Product_Basic_Unit, current.getValue().getBasic_Unit());

                try {
                    /* Firing query to insert into local database */
                    providerClient.insert(GlobalProvider.CONTENT_URI_Product, copyvaluesLocalDB);
                    Log.i("Product " , "Total product data added into local " + copyvaluesLocalDB.size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {

                /* Condition Check any changes done in Rate Column.
                   if it is then Rate column values will be updated */
                if (!(current.getValue().getRate()).equals(localData.get(current.getKey()).getRate())) {

                    Log.i("Product:", "Updating existing data");
                    // Put data from server to local hashmap object using key and values
                    localData.put(current.getKey(), current.getValue());

                    // create ContentValues Object
                    ContentValues copyvalue = new ContentValues();

                    String url = "content://app.admin.pressfit.provider/Product";
                    Uri product_Uri = Uri.parse(url);

                    // Put Rate values from GlobalProvider's Product and store into ContentValues Object
                    copyvalue.put(GlobalProvider.Product_Rate, current.getValue().getRate());

                    try {
                        /* firing query to update from server to local database in particular row */
                        providerClient.update(product_Uri, copyvalue,
                                GlobalProvider.Product_Product_code + "=?", new String[]{current.getKey()});
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                /* When Product Name will be changed in server then changes will be done in local database also */
                else if (!(current.getValue().getName()).equals(localData.get(current.getKey()).getName())) {

                    Log.i("Product:", "Updating existing Product name");
                    // Put data from server to local hashmap object using key and values
                    localData.put(current.getKey(), current.getValue());

                    // create ContentValues Object
                    ContentValues copyvalue_ProductName = new ContentValues();

                    String url = "content://app.admin.pressfit.provider/Product";
                    Uri product_Uri = Uri.parse(url);

                    // Put Rate values from GlobalProvider's Product and store into ContentValues Object
                    copyvalue_ProductName.put(GlobalProvider.Product_Name, current.getValue().getName());

                    try {
                        /* firing query to update from server to local database in particular row */
                        providerClient.update(product_Uri, copyvalue_ProductName,
                                GlobalProvider.Product_Product_code + "=?", new String[]{current.getKey()});

                        Log.i("Product:", "Product Name is updated successfully");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else if(!(current.getValue().getProduct_code()).equals(localData.get(current.getValue().getProduct_code()))) {

                    Log.i("Product:", "Updating existing Product code");

                    // Put data from server to local hashmap object using key and values
                    localData.put(current.getKey(), current.getValue());

                    // create ContentValues Object
                    ContentValues copyvalue_ProductCode = new ContentValues();

                    String url = "content://app.admin.pressfit.provider/Product";
                    Uri product_Uri = Uri.parse(url);

                    // Put Rate values from GlobalProvider's Product and store into ContentValues Object
                    copyvalue_ProductCode.put(GlobalProvider.Product_Product_code, current.getValue().getProduct_code());

                    try {
                         /* firing query to update from server to local database in particular row*/
                        providerClient.update(product_Uri, copyvalue_ProductCode,
                                GlobalProvider.Product_Name + "=?", new String[]{current.getValue().getName()});

                        Log.i("Product:", "Product Code is updated successfully");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.i("Product", "No of records in product table after insert " + localData.size());
        Log.i("Product", "SYNC COMPLETE SUCCESS");
    }

    /**********************************************************************************
     * Name :- getAllAccountsDataServer
     * Input :- Pass AgentID in String variable
     * Description :- Get all Accounts data from server to server HashMap object
     **********************************************************************************/
    public void getAllAccountsDataServer(final String agentID) {

        new AsyncTask<Void, Void, Void>() {

            // declare object for  MobileServiceList<Product>
            MobileServiceList<Account_Master> accounts_list;

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    Log.i("AccountMaster:-", "Retrieving Accounts from the Server");
                    // get all accounts data by firing query according to login user
                    accounts_list = ShowAccountsData.where().field("AgentID").eq(agentID).execute().get();

                    // get all accounts data from  MobileServiceList<AccountMaster> object to AccountMaster object using loop
                    for (Account_Master accountMaster : accounts_list) {

                        // put products data from hashmap to Product object
                        serverAccountsHashMap.put(accountMaster.getAccountId(), accountMaster);
                    }
                    Log.i("AccountMaster:-", "Server Map created successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                /*********************************************************************
                 --> Compare data between local and server.
                 --> Transfer the data that is not present on local from server.
                 *********************************************************************/
                Log.i("AccountMaster:-", "Retrieving Accounts from the Local for Agent Id = " + agentID);
                HashMap<String, Account_Master> localAccountsDataHasMap = getAccountsDatafromLocal(providerClient, agentID);

                if (localAccountsDataHasMap.isEmpty()) {
                    TransferAccountsFromServerToLocal(localAccountsDataHasMap);
                } else {
                    TransferAccountsFromLocalToServer(localAccountsDataHasMap);
                }
            }
        }.execute();
    }

    /********************************************************************************************
     * Name :- getAccountsDatafromLocal
     * Input :- pass ContentProviderClient object
     * OutPut :- returning HashMap object.
     * Description :- function call to get all account_master values from local to local hashmap object.
     ********************************************************************************************/
    public HashMap getAccountsDatafromLocal(ContentProviderClient provider, String AgentID) {
        // create hashmap object
        HashMap localAccountsHashMap = new HashMap<String, Account_Master>();
        try {
            Log.i("AccountMaster:-", "Retrieving Accounts from the Local Database");

            // Get shows from the local storage
            Cursor cursor = provider.query(GlobalProvider.CONTENT_URI_ACCOUNT, null,
                    GlobalProvider.AccountMaster_AgentID + "= ?", new String[]{AgentID}, null);

            /* condition check if cursor is empty or not according to that condition will be proceed */
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // get all values from GlobalProvider class and store into string object
                    String AM_ID = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_ID));
                    String AM_AccountName = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_AccountName));
                    String AM_NameOfOwner = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_NameOfOwner));
                    String AM_Address1 = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Address1));
                    String AM_Address2 = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Address1));
                    String AM_Address3 = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Address3));
                    String AM_City = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_City));
                    String AM_State = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_State));
                    String AM_Pincode = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_PinCode));
                    String AM_FirmAnniversaryDate = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_FirmAnniversaryDate));
                    String AM_ContactPersonName = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPerson));
                    String AM_ContactPersonMobileNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPersonMobileNo));
                    String AM_OwnerMobileNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_OwnerMobileNo));
                    String AM_LandlineNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_LandlineNo));
                    String AM_BirthDate = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_BirthDate));
                    String AM_AnniversaryDate = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_AnniversaryDate));
                    String AM_EmailID = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_EmailID));
                    String AM_FaxNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_FaxNo));
                    String AM_WebSite = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Website));
                    String AM_PanNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_PanNo));

                    String AM_GST = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_GST));

                    //String AM_VatTinNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_VatTinNo));
                    //String AM_CSTNo = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_CSTNo));

                    String AM_AgentID = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_AgentID));
                    String AM_DiscountRatePercent = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Discount_Rate_Percent));
                    String AM_Remarks = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Remarks));
                    String AM_Transport = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_Transport));
                    String AM_CurrentDateTime = cursor.getString(cursor.getColumnIndex(GlobalProvider.AccountMaster_LastUpdatedDate));


                    // set all Account Master values into Product object
                    Account_Master account_master = new Account_Master();

                    account_master.setAccountId(AM_ID);
                    account_master.setAccountName(AM_AccountName);
                    account_master.setNameOfOwner(AM_NameOfOwner);
                    account_master.setAddress1(AM_Address1);
                    account_master.setAddress2(AM_Address2);
                    account_master.setAddress3(AM_Address3);
                    account_master.setCity(AM_City);
                    account_master.setState(AM_State);
                    account_master.setPinCode(AM_Pincode);
                    account_master.setFirmAnniversaryDate(AM_FirmAnniversaryDate);
                    account_master.setContactPerson(AM_ContactPersonName);
                    account_master.setContactPersonMobileNo(AM_ContactPersonMobileNo);
                    account_master.setOwnerMobileNo(AM_OwnerMobileNo);
                    account_master.setLandlineNo(AM_LandlineNo);
                    account_master.setBirthDate(AM_BirthDate);
                    account_master.setAnniversaryDate(AM_AnniversaryDate);
                    account_master.setEmailID(AM_EmailID);
                    account_master.setFaxNo(AM_FaxNo);
                    account_master.setWebsite(AM_WebSite);
                    account_master.setPanNo(AM_PanNo);

                    account_master.setGST(AM_GST);

                    //account_master.setVatTinNo(AM_VatTinNo);
                    //account_master.setCSTNo(AM_CSTNo);

                    account_master.setRemarks(AM_Remarks);
                    account_master.setDiscount_Rate_Percent(AM_DiscountRatePercent);
                    account_master.setTransport(AM_Transport);
                    account_master.setAgentID(AM_AgentID);
                    account_master.setLastUpdatedDate(AM_CurrentDateTime);

                    // put all accounts values into hashmap object using key as account ID
                    localAccountsHashMap.put(AM_ID, account_master);
                }

                // close cursor
                cursor.close();

                Log.i("AccountMaster:- ", "Local Map for Accounts created Successfully");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // return HashMap object
        return localAccountsHashMap;
    }

    /*********************************************************************************************
     * Name :- transferFromLocalToServer
     * Input :- Pass localAccountsHasMap HashMap object
     * Description :- Transfer data from local to server
     **********************************************************************************************/
    public void TransferAccountsFromLocalToServer(final HashMap<String, Account_Master> localAccountsDataHasMap) {

        Log.i("AccountMaster:-", "NBR OF RECORDS into Local Accounts :- " + localAccountsDataHasMap.size());
        Log.i("AccountMaster:-", "NBR OF RECORDS BEFORE INSERT on Server Accounts :- " + serverAccountsHashMap.size());
        Log.i("AccountMaster:-", "Inserting " + String.valueOf(localAccountsDataHasMap.size() - serverAccountsHashMap.size()) + " records on server");
        // declare object of Iterator
        Iterator<Map.Entry<String, Account_Master>> itr;

        // Iterate data from local HashMap object
        itr = localAccountsDataHasMap.entrySet().iterator();

        // Get Yesterday's Date and Today's Date both at 8 am respectively
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 8);  //------------------------------------------------>>>>> change this to 8
        today.set(Calendar.MINUTE, 00);
        today.set(Calendar.SECOND, 00);
        long today_time_in_millis = today.getTimeInMillis();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        yesterday.set(Calendar.HOUR_OF_DAY, 8);
        yesterday.set(Calendar.MINUTE, 00);
        yesterday.set(Calendar.SECOND, 00);
        long yest_time_in_millis = yesterday.getTimeInMillis();

        while (itr.hasNext()) {

            // add next data into current object
            final Map.Entry<String, Account_Master> current = itr.next();

            /* Check condition server HashMap Object contains current key if its not then enter
               into condition and insert data on server Account_Master table  */
            if (!serverAccountsHashMap.containsKey(current.getKey())) {
                Log.i("AccountMaster:-", "Starting to add new account");

                /* Put data from local to server using key as "ID" and
                    value  "Accounts Data" into local HashMap object */
                serverAccountsHashMap.put(current.getKey(), current.getValue());

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {

                            // insert all data into database while converting into uppercase
                            ShowAccountsData.insert(current.getValue()).get().toString().toUpperCase();
                            Log.i("AccountMaster:-", "New Account Added Successfully ");

                        } catch (Exception exception) {
                            Log.i("AccountMaster:-", "Error adding an account on to server ");
                            exception.printStackTrace();
                        }
                        return null;
                    }
                }.execute();

            } else {

                // create boolean object
                boolean UPDATE_FLAG = false;
                SimpleDateFormat df = null;

                // Get Last Updated Date properly for checking todays updates
                String Last_Updated_Date = current.getValue().getLastUpdatedDate();

                if (Last_Updated_Date.contains("Z")) {
                    df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                } else {
                    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }

                Date dateObj = null;
                try {
                    dateObj = df.parse(Last_Updated_Date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(dateObj);
                long curr_data_time_in_millis = c.getTimeInMillis();

                // check yesterday time is greater then today's time, if its true according to action will be perform
                if (yest_time_in_millis <= curr_data_time_in_millis && curr_data_time_in_millis < today_time_in_millis) {
                    Log.i("AccountMaster:- :", "Record seems to be updated. Starting Updating process");

                    // put current key into Account_Master object
                    Account_Master server_am = serverAccountsHashMap.get(current.getKey());

                    // get current value into Account_Master object
                    Account_Master local_am = current.getValue();

                    // Declare Date format
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                    // declare String object for server dates and local dates
                    String FAD_STR_Server = null, DOB_STR_Server = null, ANVD_STR_Server = null;
                    String FAD_STR_Local = null, DOB_STR_Local = null, ANVD_STR_Local = null;

                    // declare date object for server dates and local dates
                    Date FAD_DT_Server = null, DOB_DT_Server = null, ANVD_DT_Server = null;
                    Date FAD_DT_Local = null, DOB_DT_Local = null, ANVD_DT_Local = null;
                    try {

                        if (!server_am.getFirmAnniversaryDate().isEmpty() && !server_am.getFirmAnniversaryDate().equals("") &&
                                !server_am.getFirmAnniversaryDate().equals(" ") && !local_am.getFirmAnniversaryDate().isEmpty()
                                && !local_am.getFirmAnniversaryDate().equals("") && !local_am.getFirmAnniversaryDate().equals(" ")) {

                            // convert string date into date format and  convert that parse date into date format
                            FAD_DT_Server = dateFormatter.parse(server_am.getFirmAnniversaryDate());
                            FAD_STR_Server = dateFormatter.format(FAD_DT_Server);

                            FAD_DT_Local = dateFormatter.parse(local_am.getFirmAnniversaryDate());
                            FAD_STR_Local = dateFormatter.format(FAD_DT_Local);
                        }

                        if (!server_am.getBirthDate().isEmpty() && !server_am.getBirthDate().equals("") &&
                                !server_am.getBirthDate().equals(" ") && !local_am.getBirthDate().isEmpty()
                                && !local_am.getBirthDate().equals("") && !local_am.getBirthDate().equals(" ")) {

                            // convert string date into date format and  convert that parse date into date format
                            DOB_DT_Server = dateFormatter.parse(server_am.getBirthDate());
                            DOB_STR_Server = dateFormatter.format(DOB_DT_Server);

                            DOB_DT_Local = dateFormatter.parse(local_am.getBirthDate());
                            DOB_STR_Local = dateFormatter.format(DOB_DT_Local);
                        }

                        if (!server_am.getAnniversaryDate().isEmpty() && !server_am.getAnniversaryDate().equals("") &&
                                !server_am.getAnniversaryDate().equals(" ") && !local_am.getAnniversaryDate().isEmpty()
                                && !local_am.getAnniversaryDate().equals("") && !local_am.getAnniversaryDate().equals(" ")) {

                            // convert string date into date format and  convert that parse date into date format
                            ANVD_DT_Server = dateFormatter.parse(server_am.getAnniversaryDate());
                            ANVD_STR_Server = dateFormatter.format(ANVD_DT_Server);

                            ANVD_DT_Local = dateFormatter.parse(local_am.getAnniversaryDate());
                            ANVD_STR_Local = dateFormatter.format(ANVD_DT_Local);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    /* When user fo changes in Accounts into local database,it will update that
                        data on server also through below conditions */
                    if (!server_am.getAccountName().equalsIgnoreCase(local_am.getAccountName())) {
                        Log.i("AccountMaster:- :", "Checking Account Name");
                        UPDATE_FLAG = true;
                        server_am.setAccountName(local_am.getAccountName());
                    }
                    if (!server_am.getNameOfOwner().equalsIgnoreCase(current.getValue().getNameOfOwner())) {
                        Log.i("AccountMaster:- :", "Checking Name Of Owner");
                        UPDATE_FLAG = true;
                        server_am.setNameOfOwner(local_am.getNameOfOwner());
                    }
                    if (!server_am.getAddress1().equalsIgnoreCase(local_am.getAddress1())) {
                        Log.i("AccountMaster:- :", "Checking Address 1");
                        UPDATE_FLAG = true;
                        server_am.setAddress1(local_am.getAddress1());
                    }

                    if (!server_am.getAddress2().equalsIgnoreCase(local_am.getAddress2())) {
                        Log.i("AccountMaster:- :", "Checking Address 2");
                        UPDATE_FLAG = true;
                        server_am.setAddress2(local_am.getAddress2());
                    }

                    if (!server_am.getAddress3().equalsIgnoreCase(local_am.getAddress3())) {
                        Log.i("AccountMaster:- :", "Checking Address 3");
                        UPDATE_FLAG = true;
                        server_am.setAddress3(local_am.getAddress3());
                    }

                    if (!server_am.getCity().equalsIgnoreCase(local_am.getCity())) {
                        Log.i("AccountMaster:- :", "City");
                        UPDATE_FLAG = true;
                        server_am.setCity(local_am.getCity());
                    }
                    if (!server_am.getState().equalsIgnoreCase(local_am.getState())) {
                        Log.i("AccountMaster:- :", "Checking state");
                        UPDATE_FLAG = true;
                        server_am.setState(local_am.getState());
                    }
                    if (!server_am.getPinCode().equalsIgnoreCase(local_am.getPinCode())) {
                        UPDATE_FLAG = true;
                        server_am.setPinCode(local_am.getPinCode());
                    }

                    if (!FAD_STR_Server.equalsIgnoreCase(FAD_STR_Local)) {
                        Log.i("AccountMaster:- :", "Checking Firm Anniv Date" + FAD_STR_Server + " " + FAD_STR_Local);
                        UPDATE_FLAG = true;
                        server_am.setFirmAnniversaryDate(local_am.getFirmAnniversaryDate());
                    }

                    if (!server_am.getContactPerson().equalsIgnoreCase(local_am.getContactPerson())) {
                        Log.i("AccountMaster:- :", "Checking Contact Person");
                        UPDATE_FLAG = true;
                        server_am.setContactPerson(local_am.getContactPerson());
                    }
                    if (!server_am.getContactPersonMobileNo().equalsIgnoreCase(local_am.getContactPersonMobileNo())) {
                        Log.i("AccountMaster:- :", "Checking Contact Person Mob No.");
                        UPDATE_FLAG = true;
                        server_am.setContactPersonMobileNo(local_am.getContactPersonMobileNo());
                    }
                    if (!server_am.getOwnerMobileNo().equalsIgnoreCase(local_am.getOwnerMobileNo())) {
                        Log.i("AccountMaster:- :", "Checking Owner Mob No.");
                        UPDATE_FLAG = true;
                        server_am.setOwnerMobileNo(local_am.getOwnerMobileNo());
                    }

                    if (!server_am.getLandlineNo().equalsIgnoreCase(local_am.getLandlineNo())) {
                        Log.i("AccountMaster:- :", "Checking Landline No.");
                        UPDATE_FLAG = true;
                        server_am.setLandlineNo(local_am.getLandlineNo());
                    }

                    if (!DOB_STR_Server.equalsIgnoreCase(DOB_STR_Local)) {
                        Log.i("AccountMaster:- :", "Checking Birthdate." + "SBD : " + DOB_STR_Server + " LBD :" + DOB_STR_Local);
                        UPDATE_FLAG = true;
                        server_am.setBirthDate(local_am.getBirthDate());
                    }

                    if (!ANVD_STR_Local.equalsIgnoreCase(ANVD_STR_Server)) {
                        Log.i("AccountMaster:- :", "Checking Anniv date." + ANVD_STR_Server + " " + ANVD_STR_Local);
                        UPDATE_FLAG = true;
                        server_am.setAnniversaryDate(local_am.getAnniversaryDate());
                    }

                    if (!server_am.getEmailID().equalsIgnoreCase(local_am.getEmailID())) {
                        Log.i("AccountMaster:- :", "Checking Email " + server_am.getEmailID() + " " + local_am.getEmailID());
                        UPDATE_FLAG = true;
                        server_am.setEmailID(local_am.getEmailID());
                    }
                    if (!server_am.getFaxNo().equalsIgnoreCase(local_am.getFaxNo())) {
                        Log.i("AccountMaster:- :", "Checking Fax No.");
                        UPDATE_FLAG = true;
                        server_am.setFaxNo(local_am.getFaxNo());
                    }
                    if (!server_am.getWebsite().equalsIgnoreCase(local_am.getWebsite())) {
                        Log.i("AccountMaster:- :", "Checking Website.");
                        UPDATE_FLAG = true;
                        server_am.setWebsite(local_am.getWebsite());
                    }
                    if (!server_am.getPanNo().equalsIgnoreCase(local_am.getPanNo())) {
                        Log.i("AccountMaster:- :", "Checking PAN .");
                        UPDATE_FLAG = true;
                        server_am.setPanNo(local_am.getPanNo());
                    }
                   if(!server_am.getGST().equalsIgnoreCase(local_am.getGST())){
                       Log.i("AccountMaster:- :", "Checking GST .");
                       UPDATE_FLAG = true;
                       server_am.setGST(local_am.getGST());
                   }



                    /*if (!server_am.getVatTinNo().equalsIgnoreCase(local_am.getVatTinNo())) {
                        Log.i("AccountMaster:- :", "Checking VAT .");
                        UPDATE_FLAG = true;
                        server_am.setVatTinNo(local_am.getVatTinNo());
                    }
                    if (!server_am.getCSTNo().equalsIgnoreCase(local_am.getCSTNo())) {
                        Log.i("AccountMaster:- :", "Checking CST.");
                        UPDATE_FLAG = true;
                        server_am.setCSTNo(local_am.getCSTNo());
                    }*/

                    if (!server_am.getDiscount_Rate_Percent().equalsIgnoreCase(local_am.getDiscount_Rate_Percent())) {
                        Log.i("AccountMaster:- :", "Checking Discount.");
                        UPDATE_FLAG = true;
                        server_am.setDiscount_Rate_Percent(local_am.getDiscount_Rate_Percent());
                    }
                    if (!server_am.getRemarks().equalsIgnoreCase(local_am.getRemarks())) {
                        Log.i("AccountMaster:- :", "Checking Remarks.");
                        UPDATE_FLAG = true;
                        server_am.setRemarks(local_am.getRemarks());
                    }
                    if (!server_am.getTransport().equalsIgnoreCase(local_am.getTransport())) {
                        Log.i("AccountMaster:- :", "Checking Transport.");
                        UPDATE_FLAG = true;
                        server_am.setTransport(local_am.getTransport());
                    }
                    try {
                        /*  check if UPDATE_FLAG value is true then data will be updated on server also*/
                        if (UPDATE_FLAG) {

                            // get last updated time from local object of Accounts
                            server_am.setLastUpdatedDate(local_am.getLastUpdatedDate());
                            Log.i("AccountMaster:- :", "Starting to Update");

                            // fire query to updat data on server's Account_Master table
                            ShowAccountsData.update(server_am).get().toString().toUpperCase();
                            Log.i("AccountMaster:- :", "Data updated Successfully");
                        }
                    } catch (InterruptedException e) {
                        Log.i("AccountMaster:- :", "Error in Updating Data");
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        Log.i("AccountMaster:- :", "Error in Updating Data");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*********************************************************************************************
     * Name :- TransferAccountsFromServerToLocal
     * Input :- Pass Hashmap object of AccountMaster
     * Description :- transfer Accounts Data from server to local database
     *********************************************************************************************/
    public void TransferAccountsFromServerToLocal(HashMap<String, Account_Master> localAccountsDataHasMap) {

        Log.i("AccountMaster:-", "Local Data seems to be missing. Starting transfer from Server to Local");
        // declare object of Iterator
        Iterator<Map.Entry<String, Account_Master>> itr;

        // Iterate data from local HashMap object
        itr = serverAccountsHashMap.entrySet().iterator();

        while (itr.hasNext()) {

            // add next data into current object
            final Map.Entry<String, Account_Master> current = itr.next();

             /* Check condition server HashMap Object contains current key if its not then enter
               into condition and insert data on server Account_Master table  */

            if (!localAccountsDataHasMap.containsKey(current.getKey())) {

                /* Put data from local to server using key as "ID" and
                    value  "Accounts Data" into local HashMap object */
                localAccountsDataHasMap.put(current.getKey(), current.getValue());

                // set format of date
                SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

                String birthday_date = new String();
                String firmAnniversaryDate = new String();
                String AnniversaryDate = new String();

                // declare Date objects
                Date Convertdateformat_birthdate = null, Convertdateformat_fimAnniversaryDate = null,
                        Convertdateformat_AnniversaryDate = null;

                 /* --> get all birthdate, anniversarydate and firm_anniversary_date from Account_Master object
                         and convert into date SimpleDateFormat and store into Date object.
                    --> get all birthdate,anniversarydate and firmanniversarydate into string object */
                try {
                    // convert all the dates into date format
                    if (!current.getValue().getBirthDate().isEmpty() &&
                            !current.getValue().getBirthDate().equals("")
                            && !current.getValue().getBirthDate().equals(" ")) {
                        Convertdateformat_birthdate = DF.parse(current.getValue().getBirthDate());
                        birthday_date = DF.format(Convertdateformat_birthdate);
                    }
                    if (!current.getValue().getFirmAnniversaryDate().isEmpty() &&
                            !current.getValue().getFirmAnniversaryDate().equals("") &&
                            !current.getValue().getFirmAnniversaryDate().equals(" ") &&
                            !current.getValue().getFirmAnniversaryDate().equals(null)) {

                        Convertdateformat_fimAnniversaryDate = DF.parse(current.getValue().getFirmAnniversaryDate());
                        birthday_date = DF.format(Convertdateformat_fimAnniversaryDate);
                    }

                    if (!current.getValue().getAnniversaryDate().isEmpty() &&
                            !current.getValue().getAnniversaryDate().equals("") &&
                            !current.getValue().getAnniversaryDate().equals(" ") &&
                            !current.getValue().getAnniversaryDate().equals(null)) {

                        Convertdateformat_AnniversaryDate = DF.parse(current.getValue().getAnniversaryDate());
                        AnniversaryDate = DF.format(Convertdateformat_AnniversaryDate);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // get all Accounts value into ContentValues
                ContentValues InsertAgainServerToLocalAccounts = new ContentValues();

                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_ID, current.getValue().getAccountId());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_AccountName, current.getValue().getAccountName());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_NameOfOwner, current.getValue().getNameOfOwner());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Address1, current.getValue().getAddress1());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Address2, current.getValue().getAddress2());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Address3, current.getValue().getAddress3());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_City, current.getValue().getCity());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_State, current.getValue().getState());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_PinCode, current.getValue().getPinCode());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_FirmAnniversaryDate, birthday_date);
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_ContactPerson, current.getValue().getContactPerson());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_ContactPersonMobileNo, current.getValue().getContactPersonMobileNo());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_OwnerMobileNo, current.getValue().getOwnerMobileNo());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_LandlineNo, current.getValue().getLandlineNo());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_BirthDate, birthday_date);
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_AnniversaryDate, AnniversaryDate);
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_EmailID, current.getValue().getEmailID());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_FaxNo, current.getValue().getFaxNo());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Website, current.getValue().getWebsite());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_PanNo, current.getValue().getPanNo());

                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_GST,current.getValue().getGST());

                //InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_VatTinNo, current.getValue().getVatTinNo());
                //InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_CSTNo, current.getValue().getCSTNo());

                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_AgentID, current.getValue().getAgentID());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Discount_Rate_Percent, current.getValue().getDiscount_Rate_Percent());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Remarks, current.getValue().getRemarks());
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_Transport, current.getValue().getTransport());

                String lastUpdateDate = new String();

                try {
                    Log.i("AccountMaster:-", "Creating Last Update Date for current entry");

                    // declare date format
                    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    // parse string date fromat into date format
                    Date date = SDF.parse(current.getValue().getLastUpdatedDate());
                    // declare new dat format
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // convert date into date format
                    lastUpdateDate = dateFormatter.format(date);
                    Log.i("AccountMaster : ", "Last Updated Date " + lastUpdateDate + " added successfully");

                } catch (ParseException e) {
                    Log.i("AccountMaster : ", "Error found in Last Updated Date " + lastUpdateDate);
                    e.printStackTrace();
                }

                // put date into local database
                InsertAgainServerToLocalAccounts.put(GlobalProvider.AccountMaster_LastUpdatedDate, lastUpdateDate);

                try {
                    Log.i("AccountMaster:", "Inserting Server Data to Local");

                    // firing query on local database of Account_Master to insert Accounts data
                    providerClient.insert(GlobalProvider.CONTENT_URI_ACCOUNT, InsertAgainServerToLocalAccounts);
                    Log.i("AccountMaster:", "Inserted Successfully");
                } catch (RemoteException e) {
                    Log.i("AccountMaster:", "Error inserting Server Data to Local");
                    e.printStackTrace();
                }
            }
        }
    }
    /*********************************************************************************************
     Name :- getOrderTableValueFromLocal
     Output :- return array list of order table object.
     Description :- Get value from local database using cursor and store into
     Arraylist<OrderTable> of order table object.
     *********************************************************************************************/
    public ArrayList<OrderTable> getOrderTableValueFromLocal() throws RemoteException {

        // Declare ArrayList<OrderTable> object
        ArrayList<OrderTable> OT_LocalData_Array = new ArrayList<OrderTable>();

        /* Firing query on local database through cursor, get all order table values into cursor object*/
        Cursor OT_cursor = providerClient.query(GlobalProvider.CONTENT_URI_OrderTable, null, null, null, null);

        /* moveToFirst method move it to the first row of result table */
        if (OT_cursor.moveToFirst()) {
            do {
                // get all local order table data into String
                String OrderID = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_OrderID));
                String AccountID = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_AccountID));
                String AgentID = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_AgentID));
                String DateTime = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_DateTime));
                String Price = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_Price));
                String Total_QTY = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_TotalQTY));
                String Transport = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_Transport));
                String ShippingAdd = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_ShippingAdd));
                String Narration = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_Narration));
                String Ref_No = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_ReferenceNo));
                String __delete = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_deleted));

                /* Create new Order table object set all values into Order table object */
                OrderTable setValuesOf_OT = new OrderTable();
                setValuesOf_OT.setOrderID(OrderID);
                setValuesOf_OT.setAccountID(AccountID);
                setValuesOf_OT.setAgentID(AgentID);
                setValuesOf_OT.setDateTime(DateTime);
                setValuesOf_OT.setPrice(Float.valueOf(Price));
                setValuesOf_OT.setTotal_Qty(Integer.parseInt(Total_QTY));
                setValuesOf_OT.setTransport(Transport);
                setValuesOf_OT.setShippingAddress(ShippingAdd);
                setValuesOf_OT.setNarration(Narration);
                setValuesOf_OT.setRefNo(Integer.parseInt(Ref_No));
                setValuesOf_OT.set__deleted(Integer.parseInt(__delete));

                // add all local order table data into ArrayList of Order table
                OT_LocalData_Array.add(setValuesOf_OT);

            } while (OT_cursor.moveToNext());
        }
        // return ArrayList<OrderTable> object
        return OT_LocalData_Array;
    }

    /**********************************************************************************************
     Name :- getOrderItemValueFromLocal
     Description :- Get all values from local database of order item table.
     *********************************************************************************************/
    public void getOrderItemValueFromLocal() {
        try {

            /* Firing query to get values from local data base of order item table */
            Cursor OI_cursor = providerClient.query(GlobalProvider.CONTENT_URI_OrderItem, null, null, null, null);

            /* moveToFirst method move it to the first row of result table */
            if (OI_cursor.moveToFirst()) {
                do {
                    /* get all values of order item into string */
                    String ID = OI_cursor.getString(OI_cursor.getColumnIndex(GlobalProvider.OrderItem_ID));
                    String OrderID = OI_cursor.getString(OI_cursor.getColumnIndex(GlobalProvider.OrderItem_OrderID));
                    String ProductId = OI_cursor.getString(OI_cursor.getColumnIndex(GlobalProvider.OrderItem_ProductID));
                    String Quantity = OI_cursor.getString(OI_cursor.getColumnIndex(GlobalProvider.OrderItem_Quantity));
                    String Price = OI_cursor.getString(OI_cursor.getColumnIndex(GlobalProvider.OrderItem_Price));

                    /* create Order_Item object and set all values in it */
                    Order_Item setValuesOf_OI = new Order_Item();
                    setValuesOf_OI.setID(ID);
                    setValuesOf_OI.setOrderID(Integer.parseInt(OrderID));
                    setValuesOf_OI.setProductID(ProductId);
                    setValuesOf_OI.setQuantity(Quantity);
                    setValuesOf_OI.setPrice(Integer.parseInt(Price));

                    CheckOrdersInOI(setValuesOf_OI);
                } while (OI_cursor.moveToNext());

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************
     Name :- CheckOrdersInOI
     Input :- pass order_item object as " setValuesOf_OI"
     Description :- add all orders into ArrayList<Order_Item> object.
     *******************************************************************************************/
    public void CheckOrdersInOI(Order_Item setValuesOf_OI) {

        /* check condition contains order id into local hashmap ot order_item.
           if condition will be false it will enter into if condition other
           else part will be execute */
        if (!OI_Local_HashMap.containsKey(setValuesOf_OI.getOrderID())) {

            /* Create array list object of order_item */
            ArrayList<Order_Item> OI_List = new ArrayList<Order_Item>();
            // add all orders into arraylist
            OI_List.add(setValuesOf_OI);
            // put arraylist object into hashmap
            OI_Local_HashMap.put(setValuesOf_OI.getOrderID(), OI_List);
        } else {

            // create ArrayList<Order_Item> obejct
            ArrayList<Order_Item> OI_list = OI_Local_HashMap.get(setValuesOf_OI.getOrderID());
            // add all orders into arraylist
            OI_list.add(setValuesOf_OI);
        }
    }

    /*********************************************************************************************
     Name :- create_agent_ids_list
     Output :- return array list object of string contains agent_id list
     Description :- Making array list of AgentID , firing query on local database of order_item
     *********************************************************************************************/
    public ArrayList<String> create_agent_ids_list() {

        // create array list object of string to store agent_id
        final ArrayList<String> AgentID_list = new ArrayList<>();
        try {

            /* firing query on local database of order_item , get agent_id into cursor */
            Cursor OT_cursor = providerClient.query(GlobalProvider.CONTENT_URI_OrderTable, new String[]{"Distinct (" +
                    GlobalProvider.OrderTable_AgentID + ")"}, null, null, null);

            /*  moveToFirst method move it to the first row of result table */
            if (OT_cursor.moveToFirst())
                do {
                    // create string of agent_id and get agent_id from single row and store into string object
                    String AgentId = OT_cursor.getString(OT_cursor.getColumnIndex(GlobalProvider.OrderTable_AgentID));

                    // add all agent_id into array_list object
                    AgentID_list.add(AgentId);

                } while (OT_cursor.moveToNext());

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // return array_list object
        return AgentID_list;
    }

    /**********************************************************************************************
     Name :- getOrderTableData
     Description :- Insert all orders from local to server OrderTable table and after that
     insert local order details from local to server order_item table.
     **********************************************************************************************/
    class  getOrderTableData extends AsyncTask<Order_Agent_Info, Void, Void> {

        @Override
        protected Void doInBackground(Order_Agent_Info... params) {

            if (params != null) {

                // create ArrayList<OrderTable> object getting order table values from params
                ArrayList<OrderTable> OT_ArrayList = params[0].getOT_ArrayList();

                // create ArrayList<String> object getting agent_ids values from params
                ArrayList<String> agent_ids = params[0].getAgent_id_list();

                // getting agent_ids into hashmap<string,string> object.
                HashMap<String, String> account_agent_ids_hashmap = createAccountIdsHashMap(agent_ids);

                /* check agent_id is null or not */
                if (account_agent_ids_hashmap != null) {

                    // call function transfer from local to server data
                    transfer_orders_from_local_to_server(OT_ArrayList, account_agent_ids_hashmap);
                }
            }
            Log.i("OrderTable :", "Sync Completed successfully");
            return null;
        }
    }

    /******************************************************************************************
     Name :- transfer_orders_from_local_to_server
     Input :- Pass array list object of OrderTable,HashMap object account_agent_ids_hashmap
     of string.
     Description :- Get orders from the local array list of OrderTable through loop and
     insert all orders into server in OrderTable.
     *****************************************************************************************/
    private void transfer_orders_from_local_to_server (ArrayList<OrderTable> OT_ArrayList, HashMap<String, String> account_agent_ids_hashmap) {

        // loop of array list object og OrderTable
        for (OrderTable local_order_obj : OT_ArrayList) {

            // declare MobileServiceList<OrderTable>  object
            MobileServiceList<OrderTable> OrderTableList;

            // get Order_id from array list to string object
            String current_local_order_id = local_order_obj.getOrderID();

            // declare OrderTable object get all orders from local aaray list of hasmap
            OrderTable server_order_obj = local_order_obj;

            // declare Integer object
            Integer max_orderNumber = null;
            try {

                /* firing query to get top first order_number of corresponding agent_id */
                OrderTableList = GetOrderTableData.where().field("AgentID")
                        .eq(local_order_obj.getAgentID())
                        .select("OrderNumber").top(1).orderBy("OrderNumber", QueryOrder.Descending).execute().get();

                /* loop OrderTableList to get max order_number */
                for (OrderTable MaxOrderNumber : OrderTableList) {
                    max_orderNumber = MaxOrderNumber.getOrderNumber();
                }

                /* check max_orderNumber is null or not if its null max_orderNumber will
                          be assign to 1 otherwise it go in else part do +1 */
                if (max_orderNumber == null) {
                    max_orderNumber = 1;
                } else {
                    max_orderNumber = max_orderNumber + 1;
                }

                /* get agent_id and account_id from local hashmap into string object */
                String acc_id = account_agent_ids_hashmap.get(local_order_obj.getAgentID() + "," + local_order_obj.getAccountID());

                // set account_id into OrderTable object
                server_order_obj.setAccountID(acc_id);

                // set OrderNumber into OrderTable object
                server_order_obj.setOrderNumber(max_orderNumber);

                // set deleted zero into OrderTable object
                server_order_obj.set__deleted(0);

                // set order_id null
                server_order_obj.setOrderID(null);

                try {
                    /* fire query to insert orders into OrderTable */
                    GetOrderTableData.insert(server_order_obj).get();
                    Log.i("OrderTable : ", "Data inserted successfully in Order Table");
                } catch (Exception e) {
                    // if order didn't insert into server then set deleted value as 1
                    server_order_obj.set__deleted(1);
                    Log.i("OrderTable : ", "Error in data insertion in order table");
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                Log.i("OrderTable : ", "Error in generating order number");
                e.printStackTrace();
            } catch (ExecutionException e) {
                server_order_obj.set__deleted(1);
                Log.i("OrderTable : ", "Error in generating order number ");
                e.printStackTrace();
            }
            /* call function to get data from local order_items  */
            insert_corresponding_order_items_hashmap(server_order_obj, max_orderNumber, current_local_order_id);

            // set order_id into server object of order_table
            server_order_obj.setOrderID(current_local_order_id);
        }
        /* call function to insert data into order_item */
        insert_order_items(OT_ArrayList);
    }

    /******************************************************************************************
     Name :- insert_corresponding_order_items_hashmap
     Input :- Pass OrderTable object server_order_obj , Integer object max_orderNumber and
     String object current_local_order_id
     Description :- Get ID from server of OrderTable , set that id into order_item table  of
     ArrayList<Order_Item> object into server and make ArrayList<Order_Item>
     object having all data of Order_Item.
     ******************************************************************************************/
    private void insert_corresponding_order_items_hashmap(OrderTable server_order_obj,
                                                          Integer max_orderNumber, String current_local_order_id) {

        // check deleted value is zero or not in server of OrderTable
        if (server_order_obj.get__deleted() == 0) {

            // declare MobileServiceList<OrderTable> object
            MobileServiceList<OrderTable> OrderTableList;
            try {

                /* firing query get to get id corresponding to agent_id and order_number */
                OrderTableList = GetOrderTableData.where().field("OrderNumber").eq(max_orderNumber)
                        .and().field("AgentID").eq(server_order_obj.getAgentID()).select("ID").execute().get();

                // declare new String object
                String server_recentOrderID = new String();

                // get current_id local into new String object
                String local_recentOrderID = current_local_order_id;

                // loop on OrderTableList get recentID from server
                for (OrderTable recentId : OrderTableList) {

                    // get recent id from server into string object
                    server_recentOrderID = recentId.getOrderID();
                }

                // Create ArrayList<Order_Item> object and store all OI_Local_HashMap values in it
                ArrayList<Order_Item> server_OI_List = OI_Local_HashMap.get(Integer.parseInt(local_recentOrderID));
                for(Order_Item OI : server_OI_List)
                {
                    // set order_id as recent id getting from server OrderTable into server's Order_Item table
                    OI.setOrderID(Integer.parseInt(server_recentOrderID));
                }
            } catch (InterruptedException e) {
                Log.i("OrderTable : ", "Error in retrieving order id for Order Items");
                e.printStackTrace();
            } catch (ExecutionException e) {
                Log.i("OrderTable : ", "Error in retrieving order id for Order Items");
                e.printStackTrace();
            }
        }
    }

    /*****************************************************************************************
     Name :- insert_order_items
     Input :- pass ArrayList<OrderTable> object
     Description :- insert data into server's order_item table.
     ****************************************************************************************/
    private void insert_order_items(ArrayList<OrderTable> OT_ArrayList) {

        // loop on array list object of order_table
        for(OrderTable ot : OT_ArrayList){

            /* check deleted values is zero or not in ArrayList<OrderTable> object */
            if(ot.get__deleted() == 0){

                // create ArrayList<Order_Item> object and get all data from OI_Local_HashMap
                ArrayList<Order_Item> server_OI_list = OI_Local_HashMap.get(Integer.parseInt(ot.getOrderID()));

                // loop on array list of order_item object and get order_id and store into string object
                for (Order_Item OI : server_OI_list) {

                    // get order_id into new String object
                    String order_item_local_ID = OI.getID();

                    // set orderId null
                    OI.setID(null);
                    try {

                        /* fire query to insert data into server's order_item table */
                        GetOrderItemeData.insert(OI).get();
                        Log.i("OrderTable ", "Data inserted successfully in Order Item");

                        // fire query delete all data from local order_item row by row
                        providerClient.delete(GlobalProvider.CONTENT_URI_OrderItem,
                                GlobalProvider.OrderItem_ID + " =?" ,new String[] {order_item_local_ID});

                    } catch (Exception e) {
                        Log.i("OrderTable : ", "Error in retrieving Order Items");
                        ot.set__deleted(1);
                        e.printStackTrace();
                    }
                }
            }
        }
        // call function to delete orders from local data of OrderTable
        delete_from_OT_local(OT_ArrayList);
    }

    /*****************************************************************************************
     Name :- delete_from_OT_local
     Description :- delete all orders from local database of OrderTable
     *****************************************************************************************/
    private void delete_from_OT_local(ArrayList<OrderTable> OT_ArrayList)  {
        // loop on ArrayList<OrderTable> object
        for(OrderTable ot : OT_ArrayList) {

            // check deleted values is 0 or not accroding to that action will be perform
            if(ot.get__deleted() == 0){
                try {
                    // fire query to delete all data from orderTable into local database
                    providerClient.delete(GlobalProvider.CONTENT_URI_OrderTable,
                            GlobalProvider.OrderTable_OrderID + " =?", new String[]{ot.getOrderID()});
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    /*****************************************************************************************
     Name :- createAccountIdsHashMap
     Input :- Pass ArrayList<String> object of AgentIDs
     Output :- return HashMap<String, String> object of agent_ids list
     Description :- get ID , AccountId , AgentID from server corresponding to AgentId and
     make put all values into Hashmap<String,String> object.
     ****************************************************************************************/
    public HashMap<String, String> createAccountIdsHashMap(ArrayList<String> AgentID_list ){

        // create MobileServiceList<Account_Master> object
        MobileServiceList<Account_Master> AccountsList;

        // create HashMap<String, String> object
        HashMap<String, String> account_agent_id_hashmap = new HashMap<String, String>();
        try{
            // loop on AgentID_list
            for (int i = 0; i < AgentID_list.size(); i++) {

                // firing query on server get ID,Account_ID,AgentID values
                AccountsList = ShowAccountsData.where().field("AgentID").eq(AgentID_list.get(i)).
                        select("ID", "AgentID", "AccountId").execute().get();

                // get AgentID , AccountID , ID from MobileServiceList object and put values into HashMap Object
                for (Account_Master account : AccountsList) {

                    // get AgentID and store into String Object
                    String AgentId = account.getAgentID();
                    // get AccountID and store into String Object
                    String AccountID = account.getAccountId();
                    // get ID and store into String Object
                    String ID = account.getID();
                    // put all values into HashMap object
                    account_agent_id_hashmap.put(AgentId + "," + AccountID, ID);
                }
            }
        } catch (Exception e) {
            Log.i("OrderTable:" , "Error retrieving Account IDs from the server");
            e.printStackTrace();
        }
        // return HashMap object
        return account_agent_id_hashmap;
    }

    /*********************************************************************************************
     Name :- getListsFromLocalDB
     Output :- return array list of lists order from local db
     Description :- get all values from local db from Lists table and add into
                    arraylist of lists table.
     **********************************************************************************************/
    public HashMap<String,Lists> getListsFromLocalDB(String AgentID) throws RemoteException {

        /****************************************************************************************
            Name :-  HashMap<String, Lists>
            Key :- list increment and agent id
            Description :- Store key as list incr and agent id and list values.
        *****************************************************************************************/
        HashMap<String, Lists> Local_List_HashMap =  new HashMap<>();

        /* Firing query on local database through cursor, get all lists table values into cursor object*/
        Cursor Lists_cursor = providerClient.query(GlobalProvider.CONTENT_URI_List, null,
                GlobalProvider.List_Login_User_ID + " =?" ,new String[] {AgentID}, null);

        /* moveToFirst method move it to the first row of result table */
        if (Lists_cursor.moveToFirst()) {
            do {
                // get all local order table data into String
                String Lists_ID = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_ID));
                String List_name = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_Name));
                String List_incr = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_increment));
                String Login_user_id = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_Login_User_ID));
                String No_of_items = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_No_of_items));
                String No_of_item_ordered = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_No_of_time_ordered));
                String DateTime_created = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_Date_time_created));
                String __deleted = Lists_cursor.getString(Lists_cursor.getColumnIndex(GlobalProvider.List_deleted));

                /* Create new Lists table object set all values into Lists table object */
                Lists setValuesOf_lists = new Lists();
                setValuesOf_lists.setID(Lists_ID);
                setValuesOf_lists.setList_Name(List_name);
                setValuesOf_lists.setList_Increment(List_incr);
                setValuesOf_lists.setLogin_User_Id(Login_user_id);
                setValuesOf_lists.setNo_Of_Items(No_of_items);
                setValuesOf_lists.setNo_Of_Times_Ordered(No_of_item_ordered);
                setValuesOf_lists.setDate_Created(DateTime_created);
                setValuesOf_lists.set__deleted(__deleted);

                /* creating list key */
                String list_key = setValuesOf_lists.getList_Increment() +  "," +
                        setValuesOf_lists.getLogin_User_Id();
                // all data into hashmap lists from local db
                Local_List_HashMap.put(list_key, setValuesOf_lists);

            } while (Lists_cursor.moveToNext());
        }
        return  Local_List_HashMap;
    }

    /*********************************************************************************************
     Name :- getList_Item_from_Local_DB
     Output :- return array list of list_item order from local db
     Description :- get all values from local db from List_Item table and add into
                    arraylist of list_item table.
     **********************************************************************************************/
    public void getList_Item_from_Local_DB( String AgentID) throws RemoteException {

        Log.i("List Items :-", "Starting to create local hashmap of List Items");
        /* Firing query on local database through cursor, get all lists item table values into cursor object*/
        Cursor LI_cursor_current = providerClient.query(GlobalProvider.CONTENT_URI_List_Item,null,
                GlobalProvider.LI_Login_User_Id + " =?" ,new String[] {AgentID},null);

          /* moveToFirst method move it to the first row of result table */
        if(LI_cursor_current.moveToFirst()){
            do{
                // get data from local db into string
                String LI_ID = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_ID));
                String LI_List_Name = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_List_Name));
                String LI_Increment = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_Increment));
                String LI_Login_User_Id = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_Login_User_Id));
                String LI_Product_Id = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_Product_ID));
                String LI_deleted = LI_cursor_current.getString(LI_cursor_current.getColumnIndex(GlobalProvider.LI_deleted));

              /* Create new List item table object set all current values into Lists item table object */
                List_Items setValuesOf_LI = new List_Items();

                setValuesOf_LI.setID(LI_ID);
                setValuesOf_LI.setList_Name(LI_List_Name);
                setValuesOf_LI.setList_Increment(LI_Increment);
                setValuesOf_LI.setLogin_User_id(LI_Login_User_Id);
                setValuesOf_LI.setProduct_Id(LI_Product_Id);
                setValuesOf_LI.set__deleted(LI_deleted);

                // Capture the delete status of each list item ---------------------------------------------------->>>>>>>>>>>>>>>>>>
                /*String local_LI_delete_status_key = setValuesOf_LI.getList_Name() + "," +
                        setValuesOf_LI.getLogin_User_id() + "," + setValuesOf_LI.getProduct_Id();
                String local_LI_delete_status_value = setValuesOf_LI.get__deleted();
                local_LI_delete_status.put(local_LI_delete_status_key, local_LI_delete_status_value);*/

                String local_LI_key = setValuesOf_LI.getList_Increment() + "," + setValuesOf_LI.getLogin_User_id();
                String local_LI_delete_status_value = setValuesOf_LI.get__deleted();
                local_LI_delete_status.put(local_LI_key, local_LI_delete_status_value);

                // check  orders in List_Items tables local
                CheckOrderListItem(setValuesOf_LI);
                Log.i("List Items:-", LI_List_Name + " added to local hash map successfully");
            } while(LI_cursor_current.moveToNext());
        }
    }

    /*********************************************************************************************
     Name :- CheckOrderListItem
     Input :- pass List_Items data
     Description :- Check orders are present in local hashmap if not all data will be added into
                 hashmap otherwise all orders will be get from local hashmap in added into hashmap
     ***********************************************************************************************/
    public void CheckOrderListItem(List_Items setValuesOf_LI) {

        String LI_key = setValuesOf_LI.getList_Increment() + "," + setValuesOf_LI.getLogin_User_id();

        // checking current order are present in List_Items object
        if(!LI_Local_HashMap.containsKey(LI_key)){

            /* Create array list object of list item */
            ArrayList<String> LI_array_list = new ArrayList<>();
            //add all list_id, product ids and deleted status into arraylist
            LI_array_list.add(setValuesOf_LI.getID() + "," +setValuesOf_LI.getProduct_Id() +
                              "," + setValuesOf_LI.get__deleted());
            LI_Local_HashMap.put(LI_key, LI_array_list);
        } else{
            // create ArrayList<List_Item> object
            ArrayList<String> LI_array_list = LI_Local_HashMap.get(LI_key);
            // add all data into arraylist
            LI_array_list.add(setValuesOf_LI.getID() + "," + setValuesOf_LI.getProduct_Id() +
                              "," + setValuesOf_LI.get__deleted());
        }
    }

    /*********************************************************************************************
     Name :- getAllListDataFromServer
     Input :- pass agent id and list name as string
     Description :- get all list table data from server into server_hashmap object.
     *********************************************************************************************/
    public void getAllListDataFromServer (final String agentID) {
        Log.i("List :-" , "Started getting List data from server");

        new AsyncTask<Void, Void, Void>() {

            MobileServiceList<Lists> mobileServiceList_list_table;
            MobileServiceList<List_Items> mobileServiceList_listItemtable;

            @Override
            protected Void doInBackground(Void... params) {

                Log.i("List :-", "Async Task Started. Retrieving List Data in progress... ");
                try {
                    /* Fire query get list from server */
                    mobileServiceList_list_table = mobileServiceTable_list.where().
                            field(GlobalProvider.List_Login_User_ID).eq(agentID).execute().get();
                    Log.i("List :-" , "Retrieved List Data from Server Successfully");
                    create_list_server_hashmap(mobileServiceList_list_table);
                } catch (InterruptedException e) {
                    Log.i("List :-" , "Seems there is a error in the retrieval process 1.");
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Log.i("List :-" , "Seems there is a error in the retrieval process 2.");
                    e.printStackTrace();
                }
                 /* Fire query get list items from server */
                try {
                    mobileServiceList_listItemtable = mobileServiceTable_LI.where().
                            field(GlobalProvider.LI_Login_User_Id).eq(agentID).execute().get();
                    Log.i("List Items :-", "Retrieved List Items Data from Server Successfully");
                    create_list_items_server_hashmap(mobileServiceList_listItemtable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    if(local_List_Hashmap.isEmpty()){
                        transferListsFromServerToLocal();
                    }else {
                        Log.i("List :- ", "Starting to Transfer data of list table from local to server");
                        try {
                            transferListsFromLocalToServer();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        Log.i("List :- ", "Transferred data of list table from local to server successfully");
                    }
                } catch (ExecutionException e) {
                    Log.i("List :- " ,"Seems there is some error in Transferring data of list table from local to server");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.i("List :- " ,"Seems there is some error in Transferring data of list table from local to server");
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /*********************************************************************************************
     Name :- create_list_server_hashmap
     Input :- MobileServiceList<Lists>
     Description :- Transfer all the list data retrieved from the server into a hash map
     ********************************************************************************************
     * @param mobileServiceList_list_table*/
    public void create_list_server_hashmap(MobileServiceList<Lists> mobileServiceList_list_table){
        Log.i("List :-" , "Starting Transfer of List Server Data to a HashMap");
         /* loop on mobile service object to get current value and add into Lists object */
        for(Lists current_lists : mobileServiceList_list_table){
            /* create list key as incr and agent id */
            String server_list_hashmap_key = current_lists.getList_Increment() + "," +
                            current_lists.getLogin_User_Id();
            /* put all list into Lists hashmap */
            server_List_HashMap.put(server_list_hashmap_key,current_lists);
        }
        Log.i("List :-", "Transferred the List Server Data to a HashMap");
    }

    /*********************************************************************************************
     Name :- create_list_items_server_hashmap
     Input :- String, MobileServiceList<List_Items>
     Description :- Transfer all the list item data retrieved from the server into a hash map
     *********************************************************************************************/
    public void create_list_items_server_hashmap(MobileServiceList<List_Items> mobileServiceList_listItemtable){

        Log.i("List Items :-" , "Starting Transfer of List Item Server Data to a HashMap");

        /* loop on mobile service object to get current value and add into Lists object */
        for(List_Items current_LI : mobileServiceList_listItemtable) {

            List_Items LI_values = new List_Items();
            LI_values.setID(current_LI.getID());
            LI_values.setList_Name(current_LI.getList_Name());
            LI_values.setList_Increment(current_LI.getList_Increment());
            LI_values.setLogin_User_id(current_LI.getLogin_User_id());
            LI_values.setProduct_Id(current_LI.getProduct_Id());
            LI_values.set__deleted(current_LI.get__deleted());

            String server_li_key = current_LI.getList_Increment() + "," + current_LI.getLogin_User_id();

            /*  checking server LI hashmap contains list name of server if its not
                       all prod_id will be added into array list or it will perform else part */
            if (!server_LI_HashMap.containsKey(server_li_key)){

                /* create array list object to store prod_ids from server */
                ArrayList<String> array_li_ids = new ArrayList<>();
                array_li_ids.add(LI_values.getID() + "," + LI_values.getProduct_Id() +
                                 "," + LI_values.get__deleted());
                // put all data into server LI hashmap object
                server_LI_HashMap.put(server_li_key, array_li_ids);
            } else {
                /* create array list object and put prod_ids into server LI
                           hashmap according to list_name */
                ArrayList<String> array_li_ids = server_LI_HashMap.get(server_li_key);
                array_li_ids.add(LI_values.getID() + "," + LI_values.getProduct_Id() +
                                 "," + LI_values.get__deleted());
            }
        }
        Log.i("List Items :-", "Transferred the List Item Server Data to a HashMap Successfully");
    }

    /*********************************************************************************************
     Name :- transferListsFromServerToLocal
     Input :- pass String as agentID
     Description :- transfer all lists data from server to local
     *********************************************************************************************/
    public void transferListsFromServerToLocal() throws ExecutionException, InterruptedException {
        Log.i("Lists :-", "Looks like no local list data is present. Starting to transfer data from server to local.");

        // Iterate through server hash map of lists
        Iterator<Map.Entry<String, Lists>> server_hashmap_iterator;
        server_hashmap_iterator = server_List_HashMap.entrySet().iterator();

        while(server_hashmap_iterator.hasNext()){

            Map.Entry<String, Lists> current_server_item = server_hashmap_iterator.next();
            Lists curr_server_list = current_server_item.getValue();
            String listname = curr_server_list.getList_Name();
            String list_key = curr_server_list.getList_Increment()  + "," +
                              curr_server_list.getLogin_User_Id();
            String[] list_str = list_key.split(",");
            String list_incr = list_str[0];
            String agent_id = list_str[1];

            local_List_Hashmap.put(list_key, curr_server_list);
            Log.i("List :-", "Added " + listname + " for increment this " + list_incr + " to local hash map");
            ContentValues insert_server_to_local_list = new ContentValues();

            insert_server_to_local_list.put(GlobalProvider.List_Name, listname);
            insert_server_to_local_list.put(GlobalProvider.List_increment,list_incr);
            insert_server_to_local_list.put(GlobalProvider.List_deleted,"0");
            insert_server_to_local_list.put(GlobalProvider.List_Date_time_created,curr_server_list.getDate_Created());
            insert_server_to_local_list.put(GlobalProvider.List_Login_User_ID,agent_id);
            insert_server_to_local_list.put(GlobalProvider.List_No_of_items, curr_server_list.getNo_Of_Items());
            insert_server_to_local_list.put(GlobalProvider.List_No_of_time_ordered, curr_server_list.getNo_Of_Times_Ordered());

            try{
                Log.i("List :-", "Inserting " + listname + " for increment this " + list_incr + " to local database");
                providerClient.insert(GlobalProvider.CONTENT_URI_List, insert_server_to_local_list);
                Log.i("List :-", "Inserted " + listname + " for increment this "+ list_incr + " to local database successfully.");
            } catch (RemoteException e) {
                Log.i("List :-", "Looks like there is some error in inserting list " + listname + " to local database");
                e.printStackTrace();
            }

            // Iterate through List items to insert the corresponding list items to the local database
            if(server_LI_HashMap.containsKey(list_key)) {
                ArrayList<String> corresponding_list_items = server_LI_HashMap.get(list_key);
                if(corresponding_list_items != null ) {
                    insert_LI_to_local_db(list_key, listname, corresponding_list_items);
                }
            }
        }
    }

    /*********************************************************************************************
     Name :- transferListsFromLocalToServer
     Description :- transfer all lists data from local to server
     *********************************************************************************************/
    public void transferListsFromLocalToServer() throws ExecutionException, InterruptedException, RemoteException {

        ArrayList<String> list_incr_to_be_removed_from_local_hashmap = new ArrayList<>();
        Log.i("List :-", "Starting transfer of Local List data to Server");
        // declare object of Iterator
        Iterator<Map.Entry<String, Lists>> iterator_lists;
        // Iterate data from local HashMap object
        iterator_lists = local_List_Hashmap.entrySet().iterator();
        while (iterator_lists.hasNext()) {
            // add next data into current object
            Map.Entry<String, Lists> current_List = iterator_lists.next();
            // get current key and value in a variable
            final String list_key = current_List.getKey();
            String[] list_incr_agent_id = list_key.split(",");
            String current_list_incr = list_incr_agent_id[0];
            String current_list_agentid = list_incr_agent_id[1];

            Lists local_list_value = current_List.getValue();
            String list_name = local_list_value.getList_Name();
            /* check condition if current key is not present in server hashmap then all data will
                be transfer from local to server other wise some updates in lists table */
            Log.i("List :-", " Current List name is " + list_name + " increment with " + current_list_incr);
            if (!server_List_HashMap.containsKey(list_key)) {
                if (local_list_value.get__deleted().equals("0")) {
                    Log.i("List :-", list_name + " incremented with  " + current_list_incr + " is not present on the server.");
                    // create a server hashmap of List table
                    server_List_HashMap.put(list_key, local_list_value);
                    final Lists server_li = new Lists(local_list_value.getList_Name(),local_list_value.getList_Increment(),
                            local_list_value.getLogin_User_Id(), local_list_value.getNo_Of_Items(), local_list_value.getDate_Created());
                    Log.i("List :- ", list_name + " incremented with " + current_list_incr + " added to server hash map.");
                    // insert this list and its list items into server
                    insert_list_into_server_db(server_li);
                } else if (local_list_value.get__deleted().equals("1")) {
                    Log.i("List :- ", "List " + list_name + " increment with " +
                            current_list_incr + " looks like needs Physical Deletion from local database");
                    // delete corresponding list items from local database
                    delete_LI_from_local_db(list_name, current_list_agentid,current_list_incr);
                    // delete this list from local database
                    delete_list_from_local_db(list_name,list_key, list_incr_to_be_removed_from_local_hashmap);
                }
            } else {
                /* create list object containing particular list data according to
                   list's table listname of local db*/
                Log.i("List :- ", "List already present on the server");
                    final Lists server_list_object = server_List_HashMap.get(list_key); //-------------------------------->>>>> mistake
                if (local_list_value.get__deleted().equals("0")) {
                        transferLIfromLocalToServer(list_name, list_key);
                    Log.i("List :- ", "Checking for Number of Items......");
                /* checking no of items are not same in server and local then there will
                    some updates are there in List table*/
                  /*  if (!server_list_object.getNo_Of_Items().equals(local_li.getNo_Of_Items())) {
                        // get updated all List items from server list hashmap
                        update_list_on_server_db(list_name, local_li, server_list_object);
                    }*/
                } else if (local_list_value.get__deleted().equals("1")) {
                    Log.i("List :-", "Looks like deletion is needed on both server and local database for "
                            + list_name  + " incremented with " + current_list_incr);
                    // Delete corresponding List items from local db
                    delete_LI_from_local_db(list_name, current_list_agentid,current_list_incr);
                    // Delete this list from local db
                    delete_list_from_local_db(list_name,list_key, list_incr_to_be_removed_from_local_hashmap);
                    // Delete this list and list items from server db
                    delete_list_from_server_db(server_list_object);
                }
            }
        }
        for (String str : list_incr_to_be_removed_from_local_hashmap) {
             local_List_Hashmap.remove(str);
             LI_Local_HashMap.remove(str);
        }
    }

    /**********************************************************************************************
     Name :- delete_List_from_local_db
     Input :- pass server_list_name and list_key as string and
              list_incr_to_be_removed_from_local_hashmap as ArrayList<string> object.
     Description :- Physical Deletion of given list for the provided agent id
     ***********************************************************************************************/
    public void delete_list_from_local_db(String list_name,String list_key,ArrayList<String>
            list_incr_to_be_removed_from_local_hashmap){

            String[] list_str = list_key.split(",");
            String list_incr = list_str[0];
            String list_agentid = list_str[1];
         try {
            Log.i("List :-", "Starting to delete " + list_name + " incremented with " + list_incr  + " from local database");
            int uri = providerClient.delete(GlobalProvider.CONTENT_URI_List,
                    GlobalProvider.List_Login_User_ID + " = ? AND  " + GlobalProvider.List_increment + " = ? ",
                    new String[]{list_agentid, list_incr});
            list_incr_to_be_removed_from_local_hashmap.add(list_key);
            Log.i("List :- ", list_name + " deleted successfully.");
        } catch (RemoteException e) {
            Log.i("List :- ", "Error in deleting " + list_name + " from local database.");
            e.printStackTrace();
        }
    }

    /**********************************************************************************************
     Name :- delete_LI_from_local_db
     Input :- pass server_list_name , agent id and list_incr as string
     Description :- Physical Deletion of list items from local db for given list name and agent id
     ***********************************************************************************************/
    public void delete_LI_from_local_db(String list_name, String agentId,String list_incr){
        try {
            Log.i("List Items :-", "Physical Deletion for List Items of " + list_name + "started...");
            int li_uri = providerClient.delete(GlobalProvider.CONTENT_URI_List_Item,
                    GlobalProvider.List_Login_User_ID + " = ? AND  " + GlobalProvider.List_Name + " = ? AND "
                    + GlobalProvider.List_increment + " = ?" ,
                    new String[]{agentId, list_name,list_incr});
            Log.i("List Items :-", "Physical Deletion for List Items of " + list_name + "completed successfully.");
        } catch (RemoteException e) {
            Log.i("List Items :-", "Error in Physical Deletion for List Items of " + list_name);
            e.printStackTrace();
        }
    }

    /**********************************************************************************************
     Name :- delete_corresponding_items_from_server_LI
     Input :- pass server_list_name, list_key as string, msg as string (whether insert or delete)
     Description :- List Items will be deleted or inserted in the server db from given list name and
     as instructed in the msg variable
     ***********************************************************************************************/
    public void delete_corresponding_items_from_server_LI(String server_list_name,String list_key){

        Log.i("List Item :- ", " Starting to delete list item from/to server for " + server_list_name);

            ArrayList<String> deleted_prod_ids = new ArrayList<String>();
            ArrayList<String> server_prod_id = server_LI_HashMap.get(list_key);

            String[] li_key_str = list_key.split(",");
            String li_incr = li_key_str[0];
            String li_agent_id = li_key_str[1];

            if (server_prod_id != null) {

                for (String server_prod_id_current : server_prod_id) {

                    String[] li_str = server_prod_id_current.split(",");
                    String list_id = li_str[0];
                    String list_prod_id = li_str[1];
                    String list_deleted_status = li_str[2];

                    List_Items delete_LI_from_server = new List_Items();
                    delete_LI_from_server.setID(list_id);
                    delete_LI_from_server.setList_Name(server_list_name);
                    delete_LI_from_server.setList_Increment(li_incr);
                    delete_LI_from_server.setLogin_User_id(li_agent_id);
                    delete_LI_from_server.setProduct_Id(list_prod_id);
                    delete_LI_from_server.set__deleted(list_deleted_status);

                    mobileServiceTable_LI.delete(delete_LI_from_server);
                    Log.i("List Item :- ", server_prod_id_current + " deleted from list item table of server successfully");
                    deleted_prod_ids.add(server_prod_id_current);
                }
            }
            for (String str : deleted_prod_ids) {
                server_LI_HashMap.get(list_key).remove(str);
            }
    }

    /**********************************************************************************************
     Name :- insert_corresponding_items_from_server_LI
     Input :- pass server_list_name, list_key as string, msg as string (whether insert or delete)
     Description :- List Items will be deleted or inserted in the server db from given list name and
                     as instructed in the msg variable
     ***********************************************************************************************/
    public void insert_corresponding_items_to_server_LI(String list_name, String list_key) {

        String[] li_str = list_key.split(",");
        String li_incr = li_str[0];
        String li_agent_id = li_str[1];

        Log.i("List Item :- ", " Starting to insert list item from/to server for " + list_name +
                " and incremented with  " + li_incr);
        if(LI_Local_HashMap.containsKey(list_key)) {

            ArrayList<String> local_prod_ids_and_deleted_status = LI_Local_HashMap.get(list_key);

            for (String curr_prod_ids_deleted_status : local_prod_ids_and_deleted_status) {

                String[] li_str_id_prod_id = curr_prod_ids_deleted_status.split(",");
                String curr_id = li_str_id_prod_id[0];
                String curr_prod_id = li_str_id_prod_id[1];
                String curr_deleted_status = li_str_id_prod_id[2];

                if (curr_deleted_status.equals("0")) {

                    List_Items insert_LI_to_server = new List_Items();
                    insert_LI_to_server.setID(null);
                    insert_LI_to_server.setList_Name(list_name);
                    insert_LI_to_server.setList_Increment(li_incr);
                    insert_LI_to_server.setLogin_User_id(li_agent_id);
                    insert_LI_to_server.setProduct_Id(curr_prod_id);
                    insert_LI_to_server.set__deleted(curr_deleted_status);

                    try {
                        mobileServiceTable_LI.insert(insert_LI_to_server).get();
                        Log.i("List Item :- ", curr_prod_id + " inserted to list item table of server successfully");
                        if (server_LI_HashMap.containsKey(list_key)) {
                            server_LI_HashMap.get(list_key).add(curr_prod_id);
                        } else {
                            ArrayList<String> arrayList = new ArrayList<String>();
                            arrayList.add(curr_prod_id);
                            server_LI_HashMap.put(list_key, arrayList);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.i("List Item :-  " , list_name +  " doesn't contains any products list");
        }
    }

//---------------------------------------------------Combine these two if possible --------------------------------------------
    /**********************************************************************************************
     Name :- delete_list_from_server_db
     Input :- pass List server object
     Description :- Given List object will be deleted from the server database.
     ***********************************************************************************************/
    public void delete_list_from_server_db(final Lists server_list_object) {

        final String list_key = server_list_object.getList_Increment() + "," +
                                server_list_object.getLogin_User_Id();
        String[] list_str = list_key.split(",");
        final String list_incr = list_str[0];
        final String agentId = list_str[1];
        final String list_name = server_list_object.getList_Name();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // First deleting corresponding list items from the server
                delete_corresponding_items_from_server_LI(list_name, list_key);

                // Now deleting the list
                Log.i("List :-", "Starting to delete " + list_name + " increment with " + list_incr + " from server database");
                mobileServiceTable_list.delete(server_list_object);
                server_List_HashMap.remove(list_key);
                //server_LI_HashMap.remove(list_name);
                Log.i("List :- ", list_name + " deleted from server and server hashmap ");
                return null;
            }
        }.execute();
    }

    /**********************************************************************************************
     Name :- insert_list_into_server_db
     Input :- pass server_list_object as Lists
     Description :- Given List object will be deleted from the server database.
     ***********************************************************************************************/
    public void insert_list_into_server_db(final Lists server_list_object){

        final String list_name = server_list_object.getList_Name();
        final String list_key = server_list_object.getList_Increment() + "," +
                                server_list_object.getLogin_User_Id();
        String[] list_str = list_key.split(",");
        final String list_incr = list_str[0];
        final String agentId = list_str[1];
                new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.i("List :- ", list_name + " incremented with " + list_incr + ".... Starting to add to server table.");
                    mobileServiceTable_list.insert(server_list_object).get();
                    Log.i("List :- ", list_name + " added to server table successfully");
                } catch (Exception e) {
                    Log.i("List :-", "Error in adding list " + list_name + " from local to server");
                    e.printStackTrace();
                }
                server_List_HashMap.put(list_key,server_list_object); //-------------- This entry wont be present in the server_ids_hashmap
        insert_corresponding_items_to_server_LI(list_name, list_key);
        return null;
    }
}.execute();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    /**********************************************************************************************
     Name :- update_list_on_server_db
     Input :- pass list_name as string, local_li as Lists, server_list_object as Lists
     Description :- Given server List object will be update with local list object from the
     server database.
     ***********************************************************************************************/
    public void update_list_on_server_db(final String list_name,Lists local_li,Lists server_list_object){
        Log.i("List :- ", list_name + ".... Starting to update to server table.");
        local_li.setID(server_list_object.getID());
        server_List_HashMap.put(list_name, local_li);
        final Lists updated_list_object = server_List_HashMap.get(list_name);
        // all update will be done in server
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.i("List :- ", "Starting update for " + list_name + " on server");
                    mobileServiceTable_list.update(updated_list_object).get().toString().toUpperCase();
                    Log.i("List :- ", "Server List " + list_name + " has been updated successfully");
                } catch (ExecutionException e) {
                    Log.i("List :- ", "Seems to be error in updating server list " + list_name);
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.i("List :- ", "Seems to be error in updating server list " + list_name);
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /**********************************************************************************************
     Name :- insert_LI_to_local_db
     Input :- pass list_key as string, list name as string, corresponding_list_items
     as ArrayList<Lists>
     Description :- Insert the list items for given list name to local database
     ***********************************************************************************************/
    public void insert_LI_to_local_db(String list_key ,String listname, ArrayList<String>
            corresponding_list_items){

        String[] list_key_values = list_key.split(",");
        String curr_li_incr = list_key_values[0];
        String curr_li_agent_id = list_key_values[1];

        for(String curr_prod : corresponding_list_items){

            String[] curr_li_str = curr_prod.split(",");
            String local_li_id = curr_li_str[0];
            String local_prod_id = curr_li_str[1];
            String local_deleted_status = curr_li_str[2];

            ContentValues corresponding_li = new ContentValues();
            corresponding_li.put(GlobalProvider.LI_List_Name, listname);
            corresponding_li.put(GlobalProvider.LI_Increment,curr_li_incr);
            corresponding_li.put(GlobalProvider.LI_Login_User_Id, curr_li_agent_id);
            corresponding_li.put(GlobalProvider.LI_Product_ID, local_prod_id);
            corresponding_li.put(GlobalProvider.LI_deleted, "0");
            try{
                Log.i("List Items :-", "Inserting " + curr_prod + " to local database for list " + listname);
                providerClient.insert(GlobalProvider.CONTENT_URI_List_Item, corresponding_li);
                Log.i("List :-", "Inserted " + curr_prod + " to local database successfully.");
            } catch (RemoteException e) {
                Log.i("List :-", "Looks like there is some error in inserting list item " +
                        curr_prod + " to local database for list " + listname);
                e.printStackTrace();
            }
        }

        LI_Local_HashMap.put(list_key,corresponding_list_items);
        Log.i("List Items :- ", " All items added successfully in local LI hashmap");
    }

    /**********************************************************************************************
     Name :- transferLIfromLocalToServer
     Input :- pass listname and list_key string as string
     Description :- Insert the list items for given list name to local database
     **********************************************************************************************
     * @param list_name
     * @param list_key*/
    public void transferLIfromLocalToServer(String list_name, String list_key) throws RemoteException {

        Log.i("List Items :- ", " Started transferring List items already present in local data base");

        if(LI_Local_HashMap.containsKey(list_key)) {

            String LI_key = list_key;
            String[] LI_key_split = LI_key.split(",");
            String local_incr = LI_key_split[0];
            String local_agent_id = LI_key_split[1];

            ArrayList<String> LI_values_array_list = LI_Local_HashMap.get(list_key);
            //key :- product_id , Value :- id
            HashMap<String, String> non_deleted_items_from_local = new HashMap<String, String>();
            // key :- product_id , Value :- array_list of ids
            HashMap<String, ArrayList<String>> deleted_items_from_local = new HashMap<String, ArrayList<String>>();

            for (String current_value : LI_values_array_list) {

                String[] li_str = current_value.split(",");
                String curr_id = li_str[0];
                String curr_prod_id = li_str[1];
                String curr_deleted_status = li_str[2];

                if (curr_deleted_status.equals("0")) {
                    non_deleted_items_from_local.put(curr_prod_id, curr_id);
                    Log.i("List Items :- ", " Non deleted items from local DB are "
                            + curr_prod_id + " -> " + curr_id + " array size " + non_deleted_items_from_local.size());
                } else {

                    if (!non_deleted_items_from_local.containsKey(curr_prod_id)) {
                        non_deleted_items_from_local.put(curr_prod_id, null);
                        Log.i("List Items :- ", " Non deleted items with null are "
                                + non_deleted_items_from_local + non_deleted_items_from_local.size());
                    }
                    if (!deleted_items_from_local.containsKey(curr_prod_id)) {
                        ArrayList<String> li_array = new ArrayList<String>();
                        li_array.add(curr_id);
                        deleted_items_from_local.put(curr_prod_id, li_array);
                        Log.i("List Items :- ", " Deleted items not present in hashmap object :- "
                                + curr_prod_id + " -> " + curr_id + " array size " + deleted_items_from_local.size());
                    } else {
                        ArrayList<String> li_array = deleted_items_from_local.get(curr_prod_id);
                        li_array.add(curr_id);
                        Log.i("List Items :- ", " Already present in hashmap object " + curr_prod_id + " -> "
                                + curr_id + " array size " + deleted_items_from_local.size());
                    }
                }
            }

            HashMap<String, String> non_deleted_items_from_server = get_server_list_item_info(list_key);
            check_on_server_for_new_entry_in_LI(non_deleted_items_from_server, non_deleted_items_from_local,
                                        list_name, list_key,deleted_items_from_local);
        }
    }

    /********************************************************************************************
        Name :- get_server_list_item_info
        Input :- pass string
        Output :- return hashmap object
        Description :- get all list items from server
    *********************************************************************************************/
    public HashMap<String,String> get_server_list_item_info(String list_key){

        // key :- product_id , Value :- id
        HashMap<String, String> non_deleted_items_from_server = new HashMap<String, String>();

        /* check server LI hashmap contains key */
        if(server_LI_HashMap.containsKey(list_key)){
            ArrayList<String> LI_values_array_list = server_LI_HashMap.get(list_key);
            for(String curr_li_values : LI_values_array_list){

                String[]  li_str = curr_li_values.split(",");
                String curr_id = li_str[0];
                String curr_prod_id = li_str[1];
                String curr_deleted_status = li_str[2];

                non_deleted_items_from_server.put(curr_prod_id,curr_id);
            }
        }
        return non_deleted_items_from_server;
    }

    /********************************************************************************************
        Name :- check_on_server_for_new_entry_in_LI
        Input :- HashMap<String, String> non_deleted_items_from_server ,
                HashMap<String, String> non_deleted_items_from_local,String list_name ,
                String list_key, HashMap<String, ArrayList<String>> deleted_items_from_local
        Description :- New product items will be added in existing list.
    **********************************************************************************************/
    public void check_on_server_for_new_entry_in_LI(HashMap<String, String> non_deleted_items_from_server,
              final HashMap<String, String> non_deleted_items_from_local,String list_name,
              final String list_key, HashMap<String, ArrayList<String>> deleted_items_from_local) throws RemoteException {

        String[] li_key_str = list_key.split(",");
        String li_incr = li_key_str[0];
        String li_agent_id = li_key_str[1];
        // declare object of Iterator
        Iterator<Map.Entry<String, String>> iterator_lists;
        // Iterate data from local HashMap object
        iterator_lists = non_deleted_items_from_local.entrySet().iterator();

        while (iterator_lists.hasNext()) {

                // add next data into current object
                Map.Entry<String, String> current_LI = iterator_lists.next();

                final String local_li_prod_id = current_LI.getKey();
                final String local_li_id = current_LI.getValue();

                /* check local id is null or not */
                if(local_li_id != null) {

                    /* check current product id is present in server hashmap object if not
                       product will be add into server db of LI */
                    if (!non_deleted_items_from_server.containsKey(local_li_prod_id)) {

                        /* create list object set all values into it */
                        final List_Items insert_LI_to_server = new List_Items();
                        insert_LI_to_server.setID(null);
                        insert_LI_to_server.setList_Name(list_name);
                        insert_LI_to_server.setList_Increment(li_incr);
                        insert_LI_to_server.setLogin_User_id(li_agent_id);
                        insert_LI_to_server.setProduct_Id(local_li_prod_id);
                        insert_LI_to_server.set__deleted("0");

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                try {
                                    /* insert new product on server LI db */
                                    mobileServiceTable_LI.insert(insert_LI_to_server).get();
                                    Log.i("List Items :- ", "All newly products added successfully in server LI table");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                    }
                    /* check local deleted hashmap contains current product id
                        if yes all product will be deleted from local db of LI  */
                    if(deleted_items_from_local.containsKey(local_li_prod_id)) {

                        try {
                            /* fire query to delete all product from local LI  */
                            Log.i("List Items :- ", " Entered in deleted function ");
                            delete_this_product_LI_from_local_db(list_key, local_li_prod_id,local_li_id);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    /* check non deleted hashmap od server contains product id if yes then
                       get ID from server LI hashmap object, deleted all items from
                       server LI db */
                    if(non_deleted_items_from_server.containsKey(local_li_prod_id)) {
                        String server_li_id = non_deleted_items_from_server.get(local_li_prod_id);
                        delete_this_product_LI_from_server_db(list_key, list_name, local_li_prod_id,server_li_id);
                    }
                    /* delete items from local which are having local id null */
                    if(deleted_items_from_local.containsKey(local_li_prod_id)){
                        delete_this_product_LI_from_local_db(list_key, local_li_prod_id, local_li_id);
                    }
                }
        }
    }

    /**********************************************************************************************
      Name :- delete_this_product_LI_from_local_db
      Input :- pass listname as string, agentId as string, productId as String
      Description :- Delete this list item for given list name from local database
     **********************************************************************************************
     * @param list_key
     * @param local_li_prod_id */
    public void delete_this_product_LI_from_local_db(String list_key, String local_li_prod_id,String local_li_id) throws RemoteException {

        String [] list_key_str = list_key.split(",");
        String curr_incr = list_key_str[0];
        String curr_agent_id = list_key_str[1];

            try {
                Log.i("List Items :-", "Physical Deletion for " + local_li_prod_id + " in list items of " +
                        list_key + " started...");
                int li_uri = providerClient.delete(GlobalProvider.CONTENT_URI_List_Item,
                        GlobalProvider.LI_Login_User_Id + " = ? AND " + GlobalProvider.LI_Increment +
                                " = ? AND " + GlobalProvider.LI_Product_ID + " = ? AND "
                                + GlobalProvider.LI_deleted + " = ?" ,
                        new String[]{curr_agent_id, curr_incr, local_li_prod_id, "1"});

                Log.i("List Items :-", "Physical Deletion for " + local_li_prod_id + " in list items of " +
                        list_key +  " completed successfully." + li_uri);
                // once deleted from local delete from local hashmap too
                String curr_id_prod_id_deleted_status = local_li_id + "," + local_li_prod_id + "," + "1";
                LI_Local_HashMap.get(list_key).remove(curr_id_prod_id_deleted_status);

            } catch (RemoteException e) {
                Log.i("List Items :-", "Error in Physical Deletion of " + local_li_prod_id + " in list items of" +
                        " " + list_key);
                e.printStackTrace();
            }
        int count_after = check_count_list(list_key , local_li_prod_id);
        Log.i("List Items :- " , " No of records after deletion in local db  " + count_after);
    }

    /*********************************************************************************************
     Name :- check_count_list
     Input :- String,String
     Output :- return int
     Description :- getting no of counts from local database.
     *********************************************************************************************
     * @param list_key
     * @param local_li_prod_id*/
    public int check_count_list(String list_key, String local_li_prod_id) throws RemoteException {

        String [] list_key_str = list_key.split(",");
        String curr_incr = list_key_str[0];
        String curr_agent_id = list_key_str[1];

        // set url to query for Lists table in local DB
        String URL = "content://app.admin.pressfit.provider/List_Items";

        // parse url
        Uri LI_uri = Uri.parse(URL);
        int count = 0;
        /* firing query to get list values in cursor object*/
        Cursor count_ln_cursor =  providerClient.query(LI_uri, null,
                GlobalProvider.LI_Login_User_Id + " = ?  AND " + GlobalProvider.LI_Increment +
                        " = ? AND " + GlobalProvider.LI_Product_ID + " = ? ",
                new String[]{curr_agent_id, curr_incr, local_li_prod_id}, null);
        count_ln_cursor.moveToFirst();

        count = count_ln_cursor.getCount();
        return (count);
    }

    /**********************************************************************************************
     Name :- delete_this_product_LI_from_server_db
     Input :- pass listname as string, agentId as string, productId as String
     Description :- Delete this list item for given list name from server database
     ***********************************************************************************************/
    public void delete_this_product_LI_from_server_db(String list_key, String list_name,String local_li_prod_id , String server_li_id) {

        String [] list_key_str = list_key.split(",");
        String curr_incr = list_key_str[0];
        String curr_agent_id = list_key_str[1];

        Log.i("List Items :-", "Deletion for " + local_li_prod_id + " in list items of " +
                list_name + " from server started....");

                // First deleting corresponding list items from the server
                List_Items delete_LI_from_server = new List_Items();
                delete_LI_from_server.setID(server_li_id);
                delete_LI_from_server.setList_Name(list_name);
                delete_LI_from_server.setList_Increment(curr_incr);
                delete_LI_from_server.setLogin_User_id(curr_agent_id);
                delete_LI_from_server.setProduct_Id(local_li_prod_id);
                delete_LI_from_server.set__deleted("0");

                mobileServiceTable_LI.delete(delete_LI_from_server);
                Log.i("List Items :-", "Deletion for " + local_li_prod_id + " in list items of " +
                        list_key + " from server completed successfully. and ID is " + server_li_id);

                // Once deleted delete this from server and local hash maps too
                String curr_id_prod_id_deleted_status = server_li_id + "," + local_li_prod_id + "," + "0";
                server_LI_HashMap.get(list_key).remove(curr_id_prod_id_deleted_status);
    }
}