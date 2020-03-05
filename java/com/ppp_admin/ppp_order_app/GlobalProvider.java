package com.ppp_admin.ppp_order_app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Admin on 4/11/2016.
 */
public class GlobalProvider extends ContentProvider{

    /*********************************************************************************************
     * DECLARATION OF VARIABLES
     **********************************************************************************************/
    private SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION_new  = 71;
    static final String PROVIDER_NAME = "app.admin.pressfit.provider";

    // Database Name
    static final String DataBase_Name = "pressfit.db";

    // create HashMap objects
    private static HashMap<String , String> Product_Projection;
    private static HashMap<String , String> AccountMaster_Projection;
    private static HashMap<String , String> OrderItem_Projection;
    private static HashMap<String , String> OrderTable_Projection;
    private static HashMap<String , String> Lists_Projection;
    private static HashMap<String , String> Lists_Item_Projection;

    /*********** PRODUCT TABLE VARIABLES ******************/
    // Table Name
    static  final String PRODUCT_TABLE = "Product";
    static final String Product_ID = "ID";
    static final String Product_Product_code = "Product_code";
    static final String Product_Name = "Name";
    static final String Product_Rate = "Rate";
    static final String Product_Rate_Quantity = "Rate_Quantity";
    static final String Product_STD_PKG = "STD_PKG";
    static final String Product_STD_PKG_Unit = "STD_PKG_Unit";
    static final String Product_Master_PKG = "Master_PKG";
    static final String Product_Master_PKG_Unit = "Master_PKG_Unit";
    static final String Product_Basic_Unit = "Basic_Unit";
    static final String Product_deleted = "__deleted";

    /****************** ACCOUNT MASTER TABLE VARIABLES *****************/
    // Table Name
    static  final String ACCOUNT_TABLE_NAME = "AccountMaster";
    static final String AccountMaster_ID = "AccountId";
    static final String AccountMaster_AccountName = "AccountName";
    static final String AccountMaster_NameOfOwner = "NameOfOwne";
    static final String AccountMaster_Address1 = "Address1";
    static final String AccountMaster_Address2 = "Address2";
    static final String AccountMaster_Address3 = "Address3";
    static final String AccountMaster_City = "City";
    static final String AccountMaster_State = "State";
    static final String AccountMaster_PinCode = "PinCode";
    static final String AccountMaster_FirmAnniversaryDate = "FirmAnniversaryDate";
    static final String AccountMaster_ContactPerson = "ContactPerson";
    static final String AccountMaster_ContactPersonMobileNo = "ContactPersonMobileNo";
    static final String AccountMaster_OwnerMobileNo = "OwnerMobileNo";
    static final String AccountMaster_LandlineNo = "LandlineNo";
    static final String AccountMaster_BirthDate = "BirthDate";
    static final String AccountMaster_AnniversaryDate = "AnniversaryDate";
    static final String AccountMaster_EmailID = "EmailID";
    static final String AccountMaster_FaxNo = "FaxNo";
    static final String AccountMaster_Website = "Website";
    static final String AccountMaster_PanNo = "PanNo";
    static  final String AccountMaster_GST = "GST_No";
    static final String AccountMaster_AgentID = "AgentID";
    static final String AccountMaster_Discount_Rate_Percent = "Discount_Rate_Percent";
    static final String AccountMaster_Remarks = "Remarks";
    static final String AccountMaster_Transport = "Transport";
    static final String AccountMaster_LastUpdatedDate = "LastUpdatedDate";

    /****************** ORDER ITEM TABLE VARIABLES *****************/
    static final String OrderITem_TableName = "Order_Item";

    static final String OrderItem_ID = "ID";
    static final String OrderItem_OrderID = "OrderID";
    static final String OrderItem_ProductID = "ProductID";
    static final String OrderItem_Quantity = "Quantity";
    static final String OrderItem_Price = "Price";

    /****************** ORDER_TABLE VARIABLES *****************/
    static final String OrderTable_TableName = "OrderTable";

    static final String OrderTable_OrderID = "ID";
    static final String OrderTable_AccountID = "AccountID";
    static final String OrderTable_AgentID = "AgentID";
    static final String OrderTable_DateTime = "DateTime";
    static final String OrderTable_Price = "Price";
    static final String OrderTable_TotalQTY = "Total_QTY";
    static final String OrderTable_Transport = "Transport";
    static final String OrderTable_ShippingAdd = "ShippingAddress";
    static final String OrderTable_Narration = "Narration";
    static final String OrderTable_ReferenceNo = "Ref_No";
    static final String OrderTable_deleted = "__deleted";

    /****************** LISTS_TABLE VARIABLES *****************/
    static final String Lists_TableName = "Lists";

    static final String List_ID = "ID";
    static final String List_Name = "List_Name";
    static final String List_Login_User_ID = "Login_User_Id";
    static final String List_No_of_items = "No_Of_Items";
    static final String List_No_of_time_ordered = "No_Of_Times_Ordered";
    static final String List_Date_time_created = "Date_Created";
    static final String List_deleted = "__deleted";
    static final String List_increment = "List_Increment";

    /****************** LIST_ITEMS_TABLE VARIABLES *****************/
    static final String List_ITem_TableName = "List_Items";

    static final String LI_ID = "ID";
    static final String LI_List_Name = "List_Name";
    static final String LI_Increment = "List_Increment";
    static final String LI_Login_User_Id = "Login_User_Id";
    static final String LI_Product_ID = "Product_Id";
    static final String LI_deleted = "__deleted";

    /**************************************************************************************/

    /******* Create String for Product table ********************************************/
    private static final String CREATE_PRODUCT_TABLE = "create table " + PRODUCT_TABLE + " (" +
            (Product_ID +  " INTEGER PRIMARY KEY NOT NULL," +
                    Product_Product_code + " TEXT UNIQUE not null," +
                    Product_Name + " TEXT UNIQUE not null," +
                    Product_Rate + " FLOAT not null," +
                    Product_Rate_Quantity+ " INTEGER not null," +
                    Product_STD_PKG + " INTEGER ," +
                    Product_STD_PKG_Unit + " TEXT not null,"+
                    Product_Master_PKG + " INTEGER ," +
                    Product_Master_PKG_Unit + " TEXT ," +
                    Product_Basic_Unit +  " TEXT," +
                    Product_deleted + " Integer Default 0);");

    /******* Create String for AccountMaster table ********************************************/
    private static final String CREATE_ACCOUNTMASTER_TABLE = "create table " + ACCOUNT_TABLE_NAME  +  " ( " +
            (AccountMaster_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
                    AccountMaster_AccountName + " VARCHAR (255) not null," +
                    AccountMaster_NameOfOwner + " VARCHAR (255) ," +
                    AccountMaster_Address1 +  " VARCHAR (255), " +
                    AccountMaster_Address2 + " VARCHAR (255)," +
                    AccountMaster_Address3 +  " VARCHAR (255)," +
                    AccountMaster_City + " VARCHAR (255) ," +
                    AccountMaster_State + " VARCHAR (255)," +
                    AccountMaster_PinCode + " VARCHAR (255), " +
                    AccountMaster_FirmAnniversaryDate +  " DATE, " +
                    AccountMaster_ContactPerson + " Varchar(255) not null," +
                    AccountMaster_ContactPersonMobileNo + " VARCHAR (255) , " +
                    AccountMaster_OwnerMobileNo + " VARCHAR (255), " +
                    AccountMaster_LandlineNo + " VARCHAR (255)," +
                    AccountMaster_BirthDate + " DATE, " +
                    AccountMaster_AnniversaryDate + " DATE, " +
                    AccountMaster_EmailID + " VARCHAR (255), " +
                    AccountMaster_FaxNo + " VARCHAR (255)," +
                    AccountMaster_Website + " VARCHAR (255)," +
                    AccountMaster_PanNo  + " VARCHAR(255)," +
                    AccountMaster_GST + " VARCHAR (255)," +
                    AccountMaster_AgentID + " INTEGER not null," +
                    AccountMaster_Discount_Rate_Percent + " INTEGER, " +
                    AccountMaster_Remarks + " Varchar(255), " +
                    AccountMaster_Transport + " VARCHAR(255), " +
                    AccountMaster_LastUpdatedDate + " DateTime not null , " +
                    " CONSTRAINT AccountMaster_AccountId_AgentId_Unique UNIQUE (" + AccountMaster_AgentID + "," + AccountMaster_ID + "));");

    /******* Create String for OrderItem table ********************************************/
    private static final String CREATE_ORDERITEM_TABLE = "create table Order_Item " + " (" +
            (OrderItem_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    OrderItem_OrderID + " Integer , " +
                    OrderItem_ProductID + " Integer ," +
                    OrderItem_Quantity + " Varchar(255), " +
                    OrderItem_Price + " Float," +
                    " FOREIGN KEY ( " +OrderItem_OrderID + ") REFERENCES " + OrderTable_TableName + "(" + OrderTable_ReferenceNo+ ") ON UPDATE CASCADE ON DELETE CASCADE ," +
                    " FOREIGN KEY (" + OrderItem_ProductID + ") REFERENCES " +  PRODUCT_TABLE  + "(" + Product_ID + ")  ON UPDATE CASCADE );");

    /******* Create String for OrderTable table ********************************************/
    private static final String CREATE_ORDERTABLE_TABLE = "create table OrderTable " + " (" +
            (OrderTable_OrderID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    OrderTable_AccountID + " Integer, " +
                    OrderTable_ReferenceNo + " Integer not null , "+
                    OrderTable_AgentID + " Integer not null,"+
                    OrderTable_DateTime  + " Date not null, " +
                    OrderTable_Price +  " Float, " +
                    OrderTable_TotalQTY + " Integer, "+
                    OrderTable_Transport + " Varchar (255), " +
                    OrderTable_ShippingAdd + " Varchar(255), " +
                    OrderTable_Narration + " Varchar(255)," +
                    OrderTable_deleted +   " Integer not null Default 1," +    // ------------------------------------------>>> Check y 1 default??
                    "FOREIGN KEY ("+ OrderTable_AccountID +")REFERENCES " + ACCOUNT_TABLE_NAME + "(" + AccountMaster_ID + "),"
                    + " CONSTRAINT OrderTable_RefNo_AgentId_Date UNIQUE (" + OrderTable_ReferenceNo + "," + OrderTable_AgentID +"," +  OrderTable_DateTime + ")); ");

    /******* Create String for List table ********************************************/
    private static final String CREATE_LISTS_TABLE = "create table Lists " + " (" +
            (List_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    List_Name + " Varchar(255) , " +
                    List_increment + " Integer not null Default 0 ," + //------------------------------------------------------->>>> newly added
                    List_Login_User_ID + " Integer not null ," +
                    List_No_of_items + " Integer not null Default 0, " +
                    List_No_of_time_ordered + " Integer ," +
                    List_Date_time_created + " DateTime not null Default '9999-12-31', "+
                    List_deleted + " Integer not null Default 0," + //---------------------------------------------->>>> newly added
                    "CONSTRAINT List_increment_and_ListLogin_User_ID UNIQUE (" + List_increment + "," + List_Login_User_ID + ")" +
                    " FOREIGN KEY(" + List_Login_User_ID + ") REFERENCES " + ACCOUNT_TABLE_NAME + "(" + AccountMaster_AgentID + "));"
            );

    /******* Create String for List_Item table ********************************************/
    private static final String CREATE_LIST_ITEM_TABLE = "create table List_Items " + " (" +
            ( LI_ID + " Integer Primary Key Autoincrement not null, " +
                    LI_List_Name + " Varchar(255) not null , " +
                    LI_Increment + " Integer not null ," + //----------------------------------------------->>>> newly added
                    LI_Login_User_Id + " Integer not null ," +
                    LI_Product_ID + " Integer not null, " +
                    LI_deleted + " Integer not null default 0,"+  //--------------------------------------------->>> Newly added
                    " FOREIGN KEY (" + LI_Product_ID + ") REFERENCES " + PRODUCT_TABLE + "( " + Product_ID + ")," +
                    "Constraint FK_LIST_INCREMENT_AND_LOGIN_USER_ID FOREIGN KEY (" + LI_Increment + " , " + LI_Login_User_Id + ")"
                    + " REFERENCES " + Lists_TableName  + "(" + List_increment + " , " + List_Login_User_ID + ")" +
                    ");"
            );

    /*******************************************************************************************/

    // creating String of URL according to different tables
    static final String URL_Product= "content://" + PROVIDER_NAME + "/Product";
    static final String URL_AccountMaster = "content://" + PROVIDER_NAME + "/AccountMaster";

    static final String URL_OrderItem = "content://" + PROVIDER_NAME + "/Order_Item";
    static final String URL_OrderTable = "content://" + PROVIDER_NAME + "/OrderTable";

    static final String URL_Lists = "content://" + PROVIDER_NAME + "/Lists";
    static final String URL_List_Item = "content://" + PROVIDER_NAME + "/List_Items";

    // define String of URL according to ID
    static final String URL_Product_ID = URL_Product + "/" + Product_ID;
    static final String URL_AccountMaster_ID = URL_AccountMaster + "/" + AccountMaster_ID;

    static final String URL_OrderItem_ID = URL_OrderItem + "/" + OrderItem_ID;
    static final String URL_OrderTable_ID = URL_OrderTable + "/" + OrderTable_OrderID;

    static final String URL_Lists_ID = URL_Lists + "/" + List_ID;
    static final String URL_List_Item_ID = URL_List_Item + "/" + LI_ID;

    // Parse all string url into URI format
    static final Uri CONTENT_URI_Product = Uri.parse(URL_Product);
    static final Uri CONTENT_URI_Product_ID = Uri.parse(URL_Product_ID);

    static final Uri CONTENT_URI_ACCOUNT = Uri.parse(URL_AccountMaster);
    static final Uri CONTENT_URI_ACCOUNT_ID = Uri.parse(URL_AccountMaster_ID);

    static final Uri CONTENT_URI_OrderItem = Uri.parse(URL_OrderItem);
    static final Uri CONTENT_URI_OrderItem_ID = Uri.parse(URL_OrderItem_ID);

    static final Uri CONTENT_URI_OrderTable = Uri.parse(URL_OrderTable);
    static final Uri CONTENT_URI_OrderTable_ID = Uri.parse(URL_OrderTable_ID);

    static final Uri CONTENT_URI_List = Uri.parse(URL_Lists);
    static final Uri CONTENT_URI_List_ID = Uri.parse(URL_Lists_ID);

    static final Uri CONTENT_URI_List_Item = Uri.parse(URL_List_Item);
    static final Uri CONTENT_URI_List_Item_ID = Uri.parse(URL_List_Item_ID);

    static final int PRODUCTS = 1;
    static final int PRODUCT_ID = 2;
    static final int ACCOUNTMASTER = 3;
    static final int ACCOUNTMASTER_ID = 4;

    static final int ORDERTABLE = 5;
    static final int ORDERTABLE_ID = 6;
    static final int ORDERITEM = 7;
    static final int ORDERITEM_ID =8;

    static final int LISTS = 9;
    static final int LISTS_ID = 10;
    static final int LIST_ITEM = 11;
    static final int LIST_ITEM_ID = 12;

    // create UriMatcher object to match uri and add into UriMatcher object.
    static final UriMatcher uriMatcher;
    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Product", PRODUCTS);
        uriMatcher.addURI(PROVIDER_NAME, "Product/#", PRODUCT_ID);
        uriMatcher.addURI(PROVIDER_NAME,"AccountMaster" , ACCOUNTMASTER);
        uriMatcher.addURI(PROVIDER_NAME,"AccountMaster/#" , ACCOUNTMASTER_ID);

        uriMatcher.addURI(PROVIDER_NAME,"Order_Item",ORDERITEM);
        uriMatcher.addURI(PROVIDER_NAME,"Order_Item/#" , ORDERITEM_ID);
        uriMatcher.addURI(PROVIDER_NAME,"OrderTable" , ORDERTABLE);
        uriMatcher.addURI(PROVIDER_NAME,"OrderTable/#",ORDERTABLE_ID);

        uriMatcher.addURI(PROVIDER_NAME,"Lists",LISTS);
        uriMatcher.addURI(PROVIDER_NAME,"Lists/#",LISTS_ID);
        uriMatcher.addURI(PROVIDER_NAME,"List_Items",LIST_ITEM);
        uriMatcher.addURI(PROVIDER_NAME,"List_Items/#",LIST_ITEM_ID);
    }

    /* Database specific constant declarations */
    public static class DataBase_Product extends SQLiteOpenHelper {

        // create constructor
        DataBase_Product(Context context) {

            super(context, DataBase_Name,null, DATABASE_VERSION_new);

            /* --> create object of SQLiteDatabase.
               --> getWritableDatabase create and/or open a database
                   that will be used for reading and writing. */
            SQLiteDatabase db = this.getWritableDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            /* condition checks New Database version is greater then old
               database version accordingly condition works. */

            if(oldVersion < newVersion) {

                // create query to drop table
                db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
                db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);

                db.execSQL("DROP TABLE IF EXISTS  " + OrderTable_TableName);
                db.execSQL("DROP TABLE IF EXISTS  " + OrderITem_TableName);

                db.execSQL("DROP TABLE IF EXISTS " + Lists_TableName);
                db.execSQL("DROP TABLE IF EXISTS " + List_ITem_TableName);
            }
            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // create query to Create table into database
            db.execSQL(CREATE_PRODUCT_TABLE);
            Log.i("GLOBAl PROVIDER :- ", "Product table has been created");

            db.execSQL(CREATE_ACCOUNTMASTER_TABLE);
            Log.i("GLOBAl PROVIDER :- ", "Account Master has been created");

            db.execSQL(CREATE_ORDERTABLE_TABLE);
            Log.i("GLOBAl PROVIDER :- ", "Order_Table table has been created");

            db.execSQL(CREATE_ORDERITEM_TABLE);
            Log.i("GLOBAl PROVIDER :- ", "Order Item table has been created");

            db.execSQL(CREATE_LISTS_TABLE);
            Log.i("GLOBAL PROVIDER :- ", "Lists table has been created");

            db.execSQL(CREATE_LIST_ITEM_TABLE);
            Log.i("GLOBAL PROVIDER :- ", "List Items table has been created");
        }
    }

    @Override
    public boolean onCreate()
    {
        Context context = getContext();

        // declare class object
        DataBase_Product dataBase_product = new DataBase_Product(context);

        /* Create a write able database which will trigger its  creation if it doesn't already exist.*/
        db = dataBase_product.getWritableDatabase();

        return (db == null)? false : true ;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        // Uses SQLiteQueryBuilder to query multiple tables
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        /* condition check uri match or not getting uri from query */
        switch (uriMatcher.match(uri))
        {
            case PRODUCTS:
                // set table name
                queryBuilder.setTables(PRODUCT_TABLE);
                // set ProjectionMap
                queryBuilder.setProjectionMap(Product_Projection);
                break;

            case PRODUCT_ID:
                // set table name
                queryBuilder.setTables(PRODUCT_TABLE);
                // create where caluse
                queryBuilder.appendWhere(Product_ID + "=" + uri.getPathSegments().get(0));
                break;

            case ACCOUNTMASTER:
                // set table name
                queryBuilder.setTables(ACCOUNT_TABLE_NAME);
                // set projection map
                queryBuilder.setProjectionMap(AccountMaster_Projection);
                break;

            case ACCOUNTMASTER_ID:
                // set table name
                queryBuilder.setTables(ACCOUNT_TABLE_NAME);
                // create where clause
                queryBuilder.appendWhere(AccountMaster_ID + "=" + uri.getPathSegments().get(0));
                break;

            case ORDERITEM:
                queryBuilder.setTables(OrderITem_TableName);
                queryBuilder.setProjectionMap(OrderItem_Projection);
                break;

            case  ORDERITEM_ID:
                queryBuilder.setTables(OrderITem_TableName);
                queryBuilder.appendWhere(OrderItem_ID + "=" + uri.getPathSegments().get(0));
                break;

            case ORDERTABLE :
                queryBuilder.setTables(OrderTable_TableName);
                queryBuilder.setProjectionMap(OrderTable_Projection);
                break;

            case  ORDERTABLE_ID:
                queryBuilder.setTables(OrderTable_TableName);
                queryBuilder.appendWhere(OrderTable_OrderID + "=" + uri.getPathSegments().get(0));
                break;

            case LISTS:
                queryBuilder.setTables(Lists_TableName);
                queryBuilder.setProjectionMap(Lists_Projection);
                break;

            case LISTS_ID:
                queryBuilder.setTables(Lists_TableName);
                queryBuilder.appendWhere(List_ID + "=" + uri.getPathSegments().get(0));
                break;

            case LIST_ITEM:
                queryBuilder.setTables(List_ITem_TableName);
                queryBuilder.setProjectionMap(Lists_Item_Projection);
                break;

            case LIST_ITEM_ID:
                queryBuilder.setTables(List_ITem_TableName);
                queryBuilder.appendWhere(LI_ID + "=" + uri.getPathSegments().get(0));

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // create cursor object fire query on database
        Cursor c = queryBuilder.query(db, projection,selection, selectionArgs,null, null, null);

        /* register to watch a content URI for changes */
        c.setNotificationUri(getContext().getContentResolver(), uri);

        // return cursor object
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        /* Add a new product record*/
        long Row_ID = 0;

        /* create Uri object */
        Uri URL = null;

        /*  condition check uri match or not getting uri from insert query */
        switch (uriMatcher.match(uri))
        {
            case PRODUCTS:
                Row_ID =   db.insert(PRODUCT_TABLE , "" , values);
                URL = CONTENT_URI_Product;
                break;

            case ACCOUNTMASTER:
                Row_ID = db.insert(ACCOUNT_TABLE_NAME, "",values );
                URL = CONTENT_URI_ACCOUNT;
                break;

            case ORDERITEM:
                Row_ID = db.insert(OrderITem_TableName,"",values);
                URL =  CONTENT_URI_OrderItem;
                break;

            case ORDERTABLE:
                Row_ID = db.insert(OrderTable_TableName,"",values);
                URL = CONTENT_URI_OrderTable;
                break;

            case LISTS:
                Row_ID = db.insert(Lists_TableName,"", values);
                URL = CONTENT_URI_List;
                break;

            case LIST_ITEM:
                Row_ID = db.insert(List_ITem_TableName,"",values);
                URL = CONTENT_URI_List_Item;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        /* If record is successfully added */
        if(Row_ID > 0)
        {
            Uri insert_uri = ContentUris.withAppendedId(URL , Row_ID);
            getContext().getContentResolver().notifyChange(insert_uri , null);
            return  insert_uri;
        }

        // return Uri object
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // initialize count
        int count = 0;

        /* condition check uri match or not getting uri from delete query */
        switch (uriMatcher.match(uri))
        {
            case PRODUCTS:
                count = db.delete(PRODUCT_TABLE, selection, selectionArgs);
                break;

            case PRODUCT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(PRODUCT_TABLE,Product_ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case ACCOUNTMASTER:
                count = db.delete(ACCOUNT_TABLE_NAME,selection,selectionArgs);
                break;

            case ACCOUNTMASTER_ID:
                String account_id = uri.getPathSegments().get(1);
                count = db.delete(ACCOUNT_TABLE_NAME ,ACCOUNTMASTER_ID + " = " + account_id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),selectionArgs);
                break;

            case ORDERITEM:
                count = db.delete(OrderITem_TableName,selection,selectionArgs);
                break;

            case ORDERITEM_ID:
                String OrderItem_id = uri.getPathSegments().get(1);
                count = db.delete(OrderITem_TableName,OrderItem_ID + " = " + OrderItem_id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case ORDERTABLE:
                count = db.delete(OrderTable_TableName,selection,selectionArgs);
                break;

            case ORDERTABLE_ID:
                String OrderTable_id = uri.getPathSegments().get(1);
                count = db.delete(OrderTable_TableName,OrderTable_OrderID + " = " + OrderTable_id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case LISTS:
                count =  db.delete(Lists_TableName,selection,selectionArgs);
                break;

            case LISTS_ID:
                String Lists_id = uri.getPathSegments().get(1);
                count = db.delete(Lists_TableName,List_ID + " = " + Lists_id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),selectionArgs);
                break;

            case LIST_ITEM:
                count = db.delete(List_ITem_TableName,selection,selectionArgs);
                break;

            case LIST_ITEM_ID:
                String List_Item_Id = uri.getPathSegments().get(1);
                count= db.delete(List_ITem_TableName,List_Item_Id + " = " + List_Item_Id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Log.i("DELETE DATA" , "DELETE DATA SUCCESS");

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int count = 0;

        switch (uriMatcher.match(uri)){

            case PRODUCTS:
                count = db.update(PRODUCT_TABLE, values, selection, selectionArgs);
                break;

            case PRODUCT_ID:
                count = db.update(PRODUCT_TABLE, values,Product_ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            case ACCOUNTMASTER:
                count = db.update(ACCOUNT_TABLE_NAME,values,selection,selectionArgs);
                break;

            case ACCOUNTMASTER_ID :
                count = db.update(ACCOUNT_TABLE_NAME,values,AccountMaster_ID + "=" + uri.getPathSegments().get(1)+
                        (!TextUtils.isEmpty(selection)? " AND (" + selection + ')' : ""),selectionArgs);
                break;

            case ORDERITEM:
                count = db.update(OrderITem_TableName,values,selection,selectionArgs);
                break;

            case ORDERITEM_ID:
                count = db.update(OrderITem_TableName,values,OrderItem_ID + " = " + uri.getPathSegments().get(1)+
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case ORDERTABLE:
                count = db.update(OrderTable_TableName,values,selection,selectionArgs);
                break;

            case ORDERTABLE_ID:
                count = db.update(OrderTable_TableName,values,OrderTable_OrderID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case LISTS:
                count = db.update(Lists_TableName,values,selection,selectionArgs);
                break;

            case LISTS_ID:
                count = db.update(Lists_TableName,values,List_ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case LIST_ITEM:
                count = db.update(List_ITem_TableName,values,selection,selectionArgs);
                break;

            case LIST_ITEM_ID:
                count = db.update(List_ITem_TableName,values,LI_ID + " = " + uri.getPathSegments().get(1)+
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}