package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class addAccount_Customer extends Activity
{
   /*********************************************************************************************
     * Section for declaration of the variables
   **********************************************************************************************/
    private EditText addAccount_accountName;
    private EditText addAccount_nameOfOwner;
    private EditText addAccount_address1;
    private EditText addAccount_address2;
    private EditText addAccount_address3;
    private EditText addAccount_city;
    private EditText addAccount_state;
    private EditText addAccount_pincodeNo;
    private EditText addAccount_firmAnniversary;
    private EditText addAccount_contactPersonName;
    private EditText addAccount_contactPersonMobileNo;
    private EditText addAccount_ownermobileNo;
    private EditText addAccount_landline;
    private EditText addAccount_BirthDate;
    private EditText addAccount_Anniversary;
    private EditText addAccount_EmailID;
    private EditText addAccount_FaxNo;
    private EditText addAccount_website;
    private EditText addAccount_panNo;
    private EditText addAccount_GST;
    private EditText addAccount_agentId;
    private EditText addAccount_Remark;
    private EditText addAccount_discountRatePercent;
    private EditText addAccount_Transprot;
    private Button addAccount_Submit;
    private Button addAccount_Cancel;
    private boolean valid_fields_flag = true;
    TextView error_show_textview;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account__customer);

        error_show_textview = (TextView) findViewById(R.id.error_fields);
        error_show_textview.setVisibility(View.INVISIBLE);

        /* addAccount_EditText function called */
        addAccount_EditText();
    }
    /**********************************************************************************************
     Name :- addAccount_EditText()
     Description :-  Creating from for customer detail.
     *********************************************************************************************/
    public void addAccount_EditText() {

        set_textview_on_UI();
        set_Date(addAccount_firmAnniversary);
        set_Date(addAccount_BirthDate);
        set_Date(addAccount_Anniversary);

        // call functions
        Submit_process();
        AccountData_cancel();

    }
    /*****************************************************************************************************************
     Name :- Submit_process
     Description :- On click of submit button it check the validations for all fields retrieved from user,
     if all fields are valid then process will be continue and data will be insert
     in database ,otherwise it will show error and data will not be entered.
     *******************************************************************************************************************/
    public void Submit_process() {

        addAccount_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set flags for valid field
                valid_fields_flag = true;

                /***********************************************************************************/
                /* Retrieving all values provided by the user in the form */
                /***********************************************************************************/
                SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
                String AgentID = loginSharedPref.getString("ID", "");

                Account_Master account_master = get_details_of_accounts(AgentID);
                valid_fields_flag = check_accounts_details_validation(account_master);

                /***********************************************************************************/
                /*Create an account master object for insertion*/
                /***********************************************************************************/
                if (valid_fields_flag) {
                    check_dups_accounts(account_master);
                }
            }
        });
    }

    /**********************************************************************************************
        Name :- get_details_of_accounts
        Input :- Pass string variable as agentID
        Output :- return Account_Master object
        Description :- Function will work to get accounts details from user and set into
                        Account_Master object.
    ***********************************************************************************************/
    public Account_Master get_details_of_accounts(String AgentID){

        final Account_Master account_master = new Account_Master();

        account_master.setAccountName(addAccount_accountName.getText().toString().toUpperCase());
        account_master.setNameOfOwner(addAccount_nameOfOwner.getText().toString().toUpperCase());
        account_master.setAddress1(addAccount_address1.getText().toString().toUpperCase());
        account_master.setAddress2(addAccount_address2.getText().toString().toUpperCase());
        account_master.setAddress3(addAccount_address3.getText().toString().toUpperCase());
        account_master.setCity(addAccount_city.getText().toString().toUpperCase());
        account_master.setState(addAccount_state.getText().toString().toUpperCase());
        account_master.setPinCode(addAccount_pincodeNo.getText().toString());
        account_master.setFirmAnniversaryDate(addAccount_firmAnniversary.getText().toString());
        account_master.setContactPerson(addAccount_contactPersonName.getText().toString().toUpperCase());
        account_master.setContactPersonMobileNo(addAccount_contactPersonMobileNo.getText().toString());
        account_master.setOwnerMobileNo(addAccount_ownermobileNo.getText().toString());
        account_master.setLandlineNo(addAccount_landline.getText().toString());
        account_master.setBirthDate(addAccount_BirthDate.getText().toString());
        account_master.setAnniversaryDate(addAccount_Anniversary.getText().toString());
        account_master.setEmailID(addAccount_FaxNo.getText().toString());
        account_master.setFaxNo(addAccount_EmailID.getText().toString().toUpperCase());
        account_master.setWebsite(addAccount_website.getText().toString().toUpperCase());
        account_master.setPanNo(addAccount_panNo.getText().toString().toUpperCase());
        account_master.setGST(addAccount_GST.getText().toString().toUpperCase());
        account_master.setRemarks(addAccount_Remark.getText().toString().toUpperCase());
        account_master.setDiscount_Rate_Percent(addAccount_discountRatePercent.getText().toString());
        account_master.setTransport(addAccount_Transprot.getText().toString().toUpperCase());
        account_master.setAgentID(AgentID);
        account_master.setLastUpdatedDate(Get_current_date());

        return account_master;

    }

    /*********************************************************************************************
        Name :- check_accounts_details_validation
        Input :- Pass Account_Master as accounts
        Output :- return boolean value
        Description :- Function will work to check validation of accounts fields.
    **********************************************************************************************/
    public boolean check_accounts_details_validation(Account_Master account_master){

        /***********************************************************************************/
                /*  VALIDATIONS for fields */
         /***********************************************************************************/
        ScrollView auto_scroll_up = (ScrollView) findViewById(R.id.ScrollView_addAccounts);

        valid_fields_flag = Utility.Name_validateLetters(account_master.getAccountName(), error_show_textview,auto_scroll_up);

        if (valid_fields_flag) {
            valid_fields_flag = Utility.Name_validateLetters(account_master.getNameOfOwner(), error_show_textview,auto_scroll_up);

        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Name_validateLetters(account_master.getContactPerson(), error_show_textview,auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.City_State_validateLetters(account_master.getCity(), error_show_textview,auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.City_State_validateLetters(account_master.getState(), error_show_textview,auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Pincode_validation(account_master.getPinCode(), error_show_textview,auto_scroll_up);
        }
        if (valid_fields_flag) {
           if (account_master.getAnniversaryDate().isEmpty()) {
                valid_fields_flag = Utility.Date_validaiton(account_master.getAnniversaryDate(), error_show_textview, false,auto_scroll_up);
            } else {
                valid_fields_flag = Utility.Date_validaiton(account_master.getAnniversaryDate(), error_show_textview, true,auto_scroll_up);
            }
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Date_validaiton(account_master.getBirthDate(), error_show_textview,true,auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Date_validaiton(account_master.getAnniversaryDate(), error_show_textview,true,auto_scroll_up);
        }
        if (valid_fields_flag) {
           if (account_master.getContactPersonMobileNo().isEmpty()) {
                valid_fields_flag = Utility.MobileNo_validation(account_master.getContactPersonMobileNo(), error_show_textview,false,auto_scroll_up);
           } else {
               valid_fields_flag = Utility.MobileNo_validation(account_master.getContactPersonMobileNo(), error_show_textview,true,auto_scroll_up);
           }
        }
        if (valid_fields_flag) {

            valid_fields_flag = Utility.MobileNo_validation(account_master.getOwnerMobileNo(), error_show_textview,true,auto_scroll_up);
        }
        if (valid_fields_flag) {

            valid_fields_flag = Utility.LandlineNo_validation(account_master.getLandlineNo(), error_show_textview,auto_scroll_up);
        }
        if (valid_fields_flag) {

            if (account_master.getEmailID().isEmpty()) {
                valid_fields_flag = Utility.AccountEmail_validation(account_master.getEmailID(), error_show_textview,false,auto_scroll_up);
            } else {
                valid_fields_flag = Utility.AccountEmail_validation(account_master.getEmailID(), error_show_textview,true,auto_scroll_up);
            }
        } if (valid_fields_flag) {

            if (account_master.getPanNo().isEmpty()) {
                valid_fields_flag = Utility.panNo_validation(account_master.getPanNo(), error_show_textview,false,auto_scroll_up);
            } else {
                valid_fields_flag = Utility.panNo_validation(account_master.getPanNo(), error_show_textview,true,auto_scroll_up);
            }
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.FaxNo_validation(account_master.getFaxNo(), error_show_textview,auto_scroll_up);
        }

        /*if(valid_fields_flag){
            valid_fields_flag  = Utility.GST_NO_validation(account_master.getGST(), error_show_textview,auto_scroll_up);
        }*/

      /*  if (valid_fields_flag) {
            valid_fields_flag = Utility.VatCstTinNo_validation(account_master.getVatTinNo(), error_show_textview,auto_scroll_up);
        }*/
        if (valid_fields_flag) {
            valid_fields_flag = Utility.DiscountRate_validation(account_master.getDiscount_Rate_Percent(), error_show_textview,auto_scroll_up);
        }
        /*if (valid_fields_flag) {
            valid_fields_flag = Utility.VatCstTinNo_validation(account_master.getCSTNo(), error_show_textview,auto_scroll_up);
        }*/
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Address_Transport_validation(account_master.getAddress1(), error_show_textview,"Address1",auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Address_Transport_validation(account_master.getAddress2(), error_show_textview, "Address2",auto_scroll_up);
        }
        if (valid_fields_flag) {
            valid_fields_flag = Utility.Address_Transport_validation(account_master.getTransport(), error_show_textview, "Transport",auto_scroll_up);
        }

        return valid_fields_flag;
    }

    /*********************************************************************************************
        Name :- Get_current_date
        Output :- return date and time in string format
        Description :- Function will give current date and time
    **********************************************************************************************/
    public String Get_current_date() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat today_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_today_Date = today_date.format(c.getTime());
        // return current date
        return str_today_Date;
    }

    /***********************************************************************************************
     Name :- check_dups_accounts
     Input :- Passing data from Account_Master table through Account_Master object
     Description :-Checking duplicates data from table when user enter same detail more then
     one time.
     **********************************************************************************************/
    public void check_dups_accounts(final Account_Master account_master)
    {
        // set progress bar
        final ProgressDialog login_progressBar =  new ProgressDialog(addAccount_Customer.this);
        Utility.show_progress_bar(login_progressBar, String.valueOf(R.string.addAccount_checkDup_ProgressBarMSG));

        /*****************************************************************************************
         creating Account_Master array list object to check duplicate values
         ****************************************************************************************/
        final ArrayList<Account_Master> dup_accounts = new ArrayList<Account_Master>();

        Boolean show_dups_alert_box = false;

        // create object SharedPreference object
        SharedPreferences loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
        // get current id from sharedPreference
        String AgentID = loginSharedPref.getString("ID", "");

        String URL = "content://app.admin.pressfit.provider/AccountMaster";

        Uri AccounAddCustomert_uri =Uri.parse(URL);

        Cursor a_cursor = getContentResolver().query(AccounAddCustomert_uri,null,
                "AccountName LIKE ? AND AgentID = ?",new String[]{account_master.getAccountName() + "%" ,AgentID},null);

        if (a_cursor.moveToFirst()) {
            show_dups_alert_box = true;
            do {

                Account_Master account_master_addToLocalDB = new Account_Master();

                account_master_addToLocalDB.setAccountName(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_AccountName)));
                account_master_addToLocalDB.setNameOfOwner(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_NameOfOwner)));
                account_master_addToLocalDB.setAddress1(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address1)));
                account_master_addToLocalDB.setAddress2(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address2)));
                account_master_addToLocalDB.setAddress3(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Address3)));
                account_master_addToLocalDB.setCity(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_City)));
                account_master_addToLocalDB.setState(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_State)));
                account_master_addToLocalDB.setPinCode(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_PinCode)));
                account_master_addToLocalDB.setFirmAnniversaryDate(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_FirmAnniversaryDate)));
                account_master_addToLocalDB.setContactPerson(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPerson)));
                account_master_addToLocalDB.setContactPersonMobileNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_ContactPersonMobileNo)));
                account_master_addToLocalDB.setOwnerMobileNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_OwnerMobileNo)));
                account_master_addToLocalDB.setLandlineNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_LandlineNo)));
                account_master_addToLocalDB.setBirthDate(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_BirthDate)));
                account_master_addToLocalDB.setAnniversaryDate(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_AnniversaryDate)));
                account_master_addToLocalDB.setEmailID(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_EmailID)));
                account_master_addToLocalDB.setFaxNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_FaxNo)));
                account_master_addToLocalDB.setWebsite(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Website)));
                account_master_addToLocalDB.setPanNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_PanNo)));

                account_master_addToLocalDB.setGST(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_GST)));

                //account_master_addToLocalDB.setVatTinNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_VatTinNo)));
                //account_master_addToLocalDB.setCSTNo(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_CSTNo)));

                account_master_addToLocalDB.setRemarks(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Remarks)));
                account_master_addToLocalDB.setDiscount_Rate_Percent(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Discount_Rate_Percent)));
                account_master_addToLocalDB.setTransport(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_Transport)));
                account_master_addToLocalDB.setAgentID(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_AgentID)));
                account_master_addToLocalDB.setLastUpdatedDate(a_cursor.getString(a_cursor.getColumnIndex(GlobalProvider.AccountMaster_LastUpdatedDate)));

                dup_accounts.add(account_master_addToLocalDB);
            }
            while (a_cursor.moveToNext());

            // Toast message will be shown if duplicate data will be there
            Toast.makeText(addAccount_Customer.this,
                    R.string.addAccount_duplicateInsertToast, Toast.LENGTH_LONG).show();
        }

        login_progressBar.dismiss();

        proceed_with_dups(dup_accounts, account_master, show_dups_alert_box);

        insert_confirmed_account(account_master, show_dups_alert_box);
    }

    /*****************************************************************************************
     Name :- proceed_With_dups
     Input :- Pass duplicate data through ArrayList<Account_Master> object as dups_accounts,
     pass Boolean value through Boolean object as show_dups_alert_box.
     Description :- This function show duplicate values in alter box if click Non of above and
     then clicks on Proceed button data will be insert and if chosen any other
     data then it will show Toast message that data is already exists.
     *****************************************************************************************/
    private int selected = -1;
    public void proceed_with_dups(final ArrayList<Account_Master> dup_accounts,
                                  final Account_Master account_master, final Boolean show_dups_alert_box) {

        // check boolean values
        if(show_dups_alert_box & valid_fields_flag) {

            // add data into an alert box through sting array
            final String[] items = new String[dup_accounts.size() + 1];
            int i = 0;
            // increment loop
            items[i++] = "None of above Add the information filled in the form.";

            // get data from dp_accounts to Account_Master object through loop
            for(Account_Master a : dup_accounts)
            {
                // set data into string array
                String str = "Account Name:- "+ a.getAccountName() + " \nOwner Name:- " + a.getNameOfOwner() +
                        "\nContact Person Name:- " + a.getContactPerson() + "\nCity:- " + a.getCity() +
                        " \nState:- " + a.getState();
                items[i++] = str;
            }

            // create alter box
            final AlertDialog.Builder addAccount_alertCheck_dups = new AlertDialog.Builder(addAccount_Customer.this);
            // set title in alert box
            addAccount_alertCheck_dups.setTitle(R.string.addAccount_alertTitle_dups);
            // set radio button to select one item only
            addAccount_alertCheck_dups.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        selected = which;
                }
            })
                    // set cancelable so alter box cancel automatically
                    .setCancelable(false)

                            //set positive button to proceed further
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            // check selection greater than user
                            if (selected > 0) {

                                // show message when data is already there
                                Toast.makeText(addAccount_Customer.this,
                                        R.string.addAccount_alertSelect_Toast_dups, Toast.LENGTH_LONG).show();
                            } else {
                                // insert data in Account_Master table
                                insert_account(account_master);

                                // show message when new customer information will be add
                                Toast.makeText(getApplicationContext(),
                                        R.string.addAccount_alterProceed_Toast, Toast.LENGTH_LONG).show();
                            }

                            finish();
                            Intent intent = new Intent (addAccount_Customer.this,Account_Details.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("review_flag", false);
                            startActivity(intent);
                        }
                    });

            // create alert box
            AlertDialog alertDialog1 = addAccount_alertCheck_dups.create();

            // show it
            alertDialog1.show();
        }
    }
    /**********************************************************************************************/
    /**********************************************************************************************
     Name :- insert_confirmed_account
     Input :- Pass Account_Master table data through Account_Master object account_master,
     pass boolean value through show_dups_alter_box
     Description :- When user fill all details on click of submit button confirmation dialog
                    will open.
     **********************************************************************************************/
    public void insert_confirmed_account(final Account_Master account_master, Boolean show_dups_alert_box)
    {
        if (valid_fields_flag && !show_dups_alert_box)
        {
            // Initialize AlertDialog.Builder object
            final AlertDialog.Builder addAccount_alertBox_insert = new AlertDialog.Builder(addAccount_Customer.this);
            // set title
            addAccount_alertBox_insert.setTitle(R.string.addAccount_alertTitle_insert);
            // set message in alert box
            addAccount_alertBox_insert.setMessage(R.string.addAccount_insert_confirmed_alertMSG);
            // set cancelable value so that it will cancel automatically
            addAccount_alertBox_insert.setCancelable(false);
            // set positiveButton and message for button
            addAccount_alertBox_insert.setPositiveButton(R.string.addAccount_buttonConfirm, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // set focusable value on touch mode
                    addAccount_accountName.setFocusableInTouchMode(true);

                    // insert value into database through Account_Master object
                    insert_account(account_master);
                    finish();
                    Intent intent = new Intent (addAccount_Customer.this,Account_Details.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("review_flag", false);
                    startActivity(intent);
                }
            })
                    // set negative button and set message also
                    .setNegativeButton(R.string.addAccount_buttonExit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = addAccount_alertBox_insert.create();
            // show it
            alertDialog.show();
        }
    }

    /**********************************************************************************************
     Name :- insert_account
     Input :- Pass Account_Master  data through Account_Master object account_master
     Description :- When the user click on confirm button data will be insert into local database
     ***********************************************************************************************/
    public void insert_account(final Account_Master account_master)
    {
        ContentValues copyAccountValuesTOLocalDB = new ContentValues();

        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ID,account_master.getID());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AccountName, account_master.getAccountName());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_NameOfOwner, account_master.getNameOfOwner());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address1, account_master.getAddress1());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address2, account_master.getAddress2());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address3, account_master.getAddress3());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_City, account_master.getCity());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_State, account_master.getState());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_PinCode, account_master.getPinCode());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_FirmAnniversaryDate, account_master.getFirmAnniversaryDate());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ContactPerson, account_master.getContactPerson());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ContactPersonMobileNo, account_master.getContactPersonMobileNo());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_OwnerMobileNo, account_master.getOwnerMobileNo());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_LandlineNo, account_master.getLandlineNo());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_BirthDate, account_master.getBirthDate());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AnniversaryDate, account_master.getAnniversaryDate());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_EmailID, account_master.getEmailID());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_FaxNo, account_master.getFaxNo());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Website, account_master.getWebsite());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_PanNo, account_master.getPanNo());

        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_GST,account_master.getGST());

        //copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_VatTinNo, account_master.getVatTinNo());
        //copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_CSTNo, account_master.getCSTNo());

        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AgentID, account_master.getAgentID());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Discount_Rate_Percent, account_master.getDiscount_Rate_Percent());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Remarks, account_master.getRemarks());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Transport, account_master.getTransport());
        copyAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_LastUpdatedDate,account_master.getLastUpdatedDate());

        Uri uri = getContentResolver().insert(
                GlobalProvider.CONTENT_URI_ACCOUNT, copyAccountValuesTOLocalDB);

        finish();
        Intent intent = new Intent (addAccount_Customer.this,Account_Details.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("review_flag", false);
        startActivity(intent);

        Log.i("Account Master", "Added Data successfully");

        addAccount_clear();

        //Toast message will shown to user "Your information has been added successfully"
        Toast.makeText(addAccount_Customer.this,"Your information has been added successfully.", Toast.LENGTH_LONG).show();
    }
    /**********************************************************************************************/
    /**********************************************************************************************
     Name :- addAccount_clear
     Description :- addAccount_clear function clear all data that filled in form.
     **********************************************************************************************/
    public void addAccount_clear() {

        addAccount_accountName.setText("");
        addAccount_nameOfOwner.setText("");
        addAccount_address1.setText("");
        addAccount_address2.setText("");
        addAccount_address3.setText("");
        addAccount_city.setText("");
        addAccount_state.setText("");
        addAccount_pincodeNo.setText("");
        addAccount_firmAnniversary.setText("");
        addAccount_contactPersonName.setText("");
        addAccount_contactPersonMobileNo.setText("");
        addAccount_ownermobileNo.setText("");
        addAccount_landline.setText("");
        addAccount_BirthDate.setText("");
        addAccount_Anniversary.setText("");
        addAccount_EmailID.setText("");
        addAccount_FaxNo.setText("");
        addAccount_website.setText("");
        addAccount_GST.setText("");

        //addAccount_VatCst_TinNo.setText("");

        addAccount_panNo.setText("");
        addAccount_Remark.setText("");
        addAccount_discountRatePercent.setText("");
        addAccount_Transprot.setText("");
    }

    /**********************************************************************************************
     Name :- AccountData_cancel
     Description :- On click of cancel button data will be clear.
     **********************************************************************************************/
    public  void AccountData_cancel()
    {
        // creating button by given id in xml file
        addAccount_Cancel = (Button) findViewById(R.id.addAccount_cancel_button);
        // set onclickListener on button to clear all fields
        addAccount_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAccount_clear();
            }
        });
    }
    /***********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_account__customer, menu);
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

    /*********************************************************************************************
     Name :- set_Date
     Description :- This function focus on date field and date_picker_dialog open to select date.
     *********************************************************************************************/
    public void set_Date(EditText et) {
        // set setOnFocusChange
        // Touch on field of date to select date
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // check condition boolean value
                if (hasFocus) {
                    // create object of Dialog_DatePicker_addAccount class
                    Dialog_DatePicker_addAccount dialog_datePicker = new Dialog_DatePicker_addAccount(v);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    // show dialog picker to user
                    dialog_datePicker.show(fragmentTransaction, "Date Picker");
                }
            }
        });
    }

    /*********************************************************************************************
     Name :- set_textview_on_UI
     Description :- Find textview id's to set into user interface
     *********************************************************************************************/
    public void set_textview_on_UI() {

        addAccount_accountName = (EditText) findViewById(R.id.addAccount_firmName_editText);
        addAccount_nameOfOwner = (EditText) findViewById(R.id.addAccount_ownerName_editText);
        addAccount_address1 = (EditText) findViewById(R.id.addAccount_address1_editText);
        addAccount_address2 = (EditText) findViewById(R.id.addAccount_address2_editText);
        addAccount_address3 = (EditText) findViewById(R.id.addAccount_address3editText);
        addAccount_city = (EditText) findViewById(R.id.addAccount_city_editText);
        addAccount_state = (EditText) findViewById(R.id.addAccount_state_editText);
        addAccount_pincodeNo = (EditText) findViewById(R.id.addAccount_pincode_editText);
        addAccount_firmAnniversary = (EditText) findViewById(R.id.addAccount_firmAnniversaryDate_editText);
        addAccount_contactPersonName = (EditText) findViewById(R.id.addAccount_contactpersonname_editText);
        addAccount_contactPersonMobileNo = (EditText) findViewById(R.id.addAccount_contactPersonMobileNo_editText);
        addAccount_ownermobileNo = (EditText) findViewById(R.id.addAccount_ownerMobileNo_editText);
        addAccount_landline = (EditText) findViewById(R.id.addAccount_landLineNo_editText);
        addAccount_BirthDate = (EditText) findViewById(R.id.addAccount_BirthDate_editText);
        addAccount_Anniversary = (EditText) findViewById(R.id.addAccount_anniversaryDate_editText);
        addAccount_EmailID = (EditText) findViewById(R.id.addAccount_emailId_editText);
        addAccount_FaxNo = (EditText) findViewById(R.id.addAccount_faxNo_editText);
        addAccount_website = (EditText) findViewById(R.id.addAccount_website_editText);

        addAccount_GST = (EditText) findViewById(R.id.addAccount_GST_No_editText);

        //addAccount_VatCst_TinNo  = (EditText) findViewById(R.id.addAccount_VatCST_TinNo_editText);

        addAccount_panNo = (EditText) findViewById(R.id.addAccount_panNo_editText);
        addAccount_Remark = (EditText) findViewById(R.id.addAccount_remarks_editText);
        addAccount_discountRatePercent = (EditText) findViewById(R.id.addAccount_discountRate__editText);
        addAccount_Transprot = (EditText) findViewById(R.id.addAccount_transport_editText);
        addAccount_Submit = (Button) findViewById(R.id.addAccount_submit_button);
    }
}
