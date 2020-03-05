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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdatePage_AccountDetails_singleCustomer extends Activity {
    /**********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
    private EditText UpdatePage_accountName;
    private EditText UpdatePage_nameOfOwner;
    private EditText UpdatePage_address1;
    private EditText UpdatePage_address2;
    private EditText UpdatePage_address3;
    private EditText UpdatePage_city;
    private EditText UpdatePage_state;
    private EditText UpdatePage_pincodeNo;
    private EditText UpdatePage_firmAnniversary;
    private EditText UpdatePage_contactPersonName;
    private EditText UpdatePage_contactPersonMobileNo;
    private EditText UpdatePage_ownermobileNo;
    private EditText UpdatePage_landline;
    private EditText UpdatePage_BirthDate;
    private EditText UpdatePage_Anniversary;
    private EditText UpdatePage_EmailID;
    private EditText UpdatePage_FaxNo;
    private EditText UpdatePage_website;
    private EditText UpdatePage_panNo;

    private EditText UpdatePage_GST_No;

    //private EditText UpdatePage_VatCst_TinNo;

    private EditText UpdatePage_Remark;
    private EditText UpdatePage_discountRatePercent;
    private Button UpdatePage_Submit;
    private Button UpdatePage_Cancel;
    private EditText UpdatePage_transport;
    Account_Master Showall_Details_Itent;
    private boolean valid_fields_flag = true;
    TextView error_show_textview;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_page__account_details_single_customer);

        error_show_textview = (TextView) findViewById(R.id.error_fields);
        error_show_textview.setVisibility(View.INVISIBLE);

        UpdatePage_EditText();
        setDate(UpdatePage_BirthDate);
        setDate(UpdatePage_firmAnniversary);
        setDate(UpdatePage_Anniversary);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**********************************************************************************************
     * Name :- UpdatePage_EditText
     * Description :- EditText will be set on page.
     **********************************************************************************************/
    public void UpdatePage_EditText() {

        UpdatePage_accountName = (EditText) findViewById(R.id.updatePage_firmName_editText);
        UpdatePage_nameOfOwner = (EditText) findViewById(R.id.updatePage_ownerName_editText);
        UpdatePage_address1 = (EditText) findViewById(R.id.updatePage_address1_editText);
        UpdatePage_address2 = (EditText) findViewById(R.id.updatePage_address2_editText);
        UpdatePage_address3 = (EditText) findViewById(R.id.updatePage_address3editText);
        UpdatePage_city = (EditText) findViewById(R.id.updatePage_city_editText);
        UpdatePage_state = (EditText) findViewById(R.id.updatePage_state_editText);
        UpdatePage_pincodeNo = (EditText) findViewById(R.id.updatePage_pincode_editText);
        UpdatePage_firmAnniversary = (EditText) findViewById(R.id.updatePage_firmAnniversaryDate_editText);
        UpdatePage_contactPersonName = (EditText) findViewById(R.id.updatePage_contactpersonname_editText);
        UpdatePage_contactPersonMobileNo = (EditText) findViewById(R.id.updatePage_contactPersonMobileNo_editText);
        UpdatePage_ownermobileNo = (EditText) findViewById(R.id.updatePage_ownerMobileNo_editText);
        UpdatePage_landline = (EditText) findViewById(R.id.updatePage_landLineNo_editText);
        UpdatePage_BirthDate = (EditText) findViewById(R.id.updatePage_BirthDate_editText);
        UpdatePage_Anniversary = (EditText) findViewById(R.id.updatePage_anniversaryDate_editText);
        UpdatePage_EmailID = (EditText) findViewById(R.id.updatePage_emailId_editText);
        UpdatePage_FaxNo = (EditText) findViewById(R.id.updatePage_faxNo_editText);
        UpdatePage_website = (EditText) findViewById(R.id.updatePage_website_editText);
        UpdatePage_GST_No = (EditText) findViewById(R.id.updatePage_GST_No_editText);
        UpdatePage_panNo = (EditText) findViewById(R.id.updatePage_panNo_editText);
        UpdatePage_Remark = (EditText) findViewById(R.id.updatePage_remarks_editText);
        UpdatePage_discountRatePercent = (EditText) findViewById(R.id.updatePage_discountRate__editText);
        UpdatePage_transport = (EditText) findViewById(R.id.updatePage_Transport_editText);
        UpdatePage_Submit = (Button) findViewById(R.id.updatePage_submit_button);
        UpdatePage_Cancel = (Button) findViewById(R.id.updatePage_cancel_button);
        setDetailsTo_EditText();

    }

    /**********************************************************************************************
     * Name :- setDetailsTo_EditText
     * Description :-Set all data into editText
     **********************************************************************************************/
    public void setDetailsTo_EditText() {
        // get all data from  Intent object to Account_Master object
        Showall_Details_Itent = (Account_Master) getIntent().getSerializableExtra("showallDetails");

        // set format of date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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
            if (!Showall_Details_Itent.getBirthDate().isEmpty() &&
                    !Showall_Details_Itent.getBirthDate().equals("") && !Showall_Details_Itent.getBirthDate().equals(" ")) {
                Convertdateformat_birthdate = dateFormatter.parse(Showall_Details_Itent.getBirthDate());
                birthday_date = dateFormatter.format(Convertdateformat_birthdate);
            }

            if (!Showall_Details_Itent.getFirmAnniversaryDate().isEmpty() &&
                    !Showall_Details_Itent.getFirmAnniversaryDate().equals("") &&
                    !Showall_Details_Itent.getFirmAnniversaryDate().equals(" ") &&
                    !Showall_Details_Itent.getFirmAnniversaryDate().equals(null)) {
                Convertdateformat_fimAnniversaryDate = dateFormatter.parse(Showall_Details_Itent.getFirmAnniversaryDate());
                firmAnniversaryDate = dateFormatter.format(Convertdateformat_fimAnniversaryDate);
            }

            if (!Showall_Details_Itent.getAnniversaryDate().isEmpty() &&
                    !Showall_Details_Itent.getAnniversaryDate().equals("") &&
                    !Showall_Details_Itent.getAnniversaryDate().equals(" ") &&
                    !Showall_Details_Itent.getAnniversaryDate().equals(null)) {
                Convertdateformat_AnniversaryDate = dateFormatter.parse(Showall_Details_Itent.getAnniversaryDate());
                AnniversaryDate = dateFormatter.format(Convertdateformat_AnniversaryDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**********************************************************************************
         set all data in textview and convert into uppercase format
         **********************************************************************************/
        UpdatePage_accountName.setText(Showall_Details_Itent.getAccountName().toString().toUpperCase());
        UpdatePage_nameOfOwner.setText(Showall_Details_Itent.getNameOfOwner().toString().toUpperCase());
        UpdatePage_address1.setText(Showall_Details_Itent.getAddress1().toString().toUpperCase());
        UpdatePage_address2.setText(Showall_Details_Itent.getAddress2().toString().toUpperCase());
        UpdatePage_address3.setText(Showall_Details_Itent.getAddress3().toString().toUpperCase());
        UpdatePage_city.setText(Showall_Details_Itent.getCity().toString().toUpperCase());
        UpdatePage_state.setText(Showall_Details_Itent.getState().toString().toUpperCase());
        UpdatePage_pincodeNo.setText(Showall_Details_Itent.getPinCode().toString().toUpperCase());
        UpdatePage_firmAnniversary.setText(firmAnniversaryDate);
        UpdatePage_contactPersonName.setText(Showall_Details_Itent.getContactPerson().toString().toUpperCase());
        UpdatePage_contactPersonMobileNo.setText(Showall_Details_Itent.getContactPersonMobileNo());
        UpdatePage_ownermobileNo.setText(Showall_Details_Itent.getOwnerMobileNo());
        UpdatePage_landline.setText(Showall_Details_Itent.getLandlineNo());
        UpdatePage_BirthDate.setText(birthday_date);
        UpdatePage_Anniversary.setText(AnniversaryDate);
        UpdatePage_EmailID.setText(Showall_Details_Itent.getEmailID().toString().toUpperCase());
        UpdatePage_FaxNo.setText(Showall_Details_Itent.getFaxNo());
        UpdatePage_website.setText(Showall_Details_Itent.getWebsite().toString().toUpperCase());
        UpdatePage_panNo.setText(Showall_Details_Itent.getPanNo().toString().toUpperCase());


        UpdatePage_GST_No.setText(Showall_Details_Itent.getGST().toString().toUpperCase());

        UpdatePage_discountRatePercent.setText(Showall_Details_Itent.getDiscount_Rate_Percent());
        UpdatePage_transport.setText(Showall_Details_Itent.getTransport());
        UpdatePage_Remark.setText(Showall_Details_Itent.getRemarks().toString().toUpperCase());

        // call submitButton_updateData function
        submitButton_UpdateData();

        // call cancel button to go back
        backPervious_cancelButton();
    }

    /**********************************************************************************************
     * Name :- submitButton_UpdateData
     * Description :- Submit button setonclicklistener to delete existing data and insert new data
     * that user has changed in form
     *********************************************************************************************/
    public void submitButton_UpdateData() {
        // set onclicklistener on submit button
        UpdatePage_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set boolean value
                valid_fields_flag = true;

                // get agentId into string form
                String Agentid = Showall_Details_Itent.getAgentID();

                /*******************************************************************************
                 Take all data into string object and display on activity
                 ******************************************************************************/
                String accountName = UpdatePage_accountName.getText().toString().toUpperCase();
                String accountNameofOwner = UpdatePage_nameOfOwner.getText().toString().toUpperCase();
                String Address1 = UpdatePage_address1.getText().toString().toUpperCase().toUpperCase();
                String Address2 = UpdatePage_address2.getText().toString().toUpperCase();
                String Address3 = UpdatePage_address3.getText().toString().toUpperCase();
                String city = UpdatePage_city.getText().toString().toUpperCase();
                String state = UpdatePage_state.getText().toString().toUpperCase();
                String pincode = UpdatePage_pincodeNo.getText().toString();
                String firmAnniversaryDate = UpdatePage_firmAnniversary.getText().toString();
                String contactPersonName = UpdatePage_contactPersonName.getText().toString().toUpperCase();
                String contactPersonMobileNo = UpdatePage_contactPersonMobileNo.getText().toString();
                String ownerMobileNo = UpdatePage_ownermobileNo.getText().toString();
                String landlineno = UpdatePage_landline.getText().toString();
                String BirthDate = UpdatePage_BirthDate.getText().toString();
                String anniversaryDate = UpdatePage_Anniversary.getText().toString();
                String EmailId = UpdatePage_EmailID.getText().toString().toUpperCase();
                String Faxno = UpdatePage_FaxNo.getText().toString();
                String panNo = UpdatePage_panNo.getText().toString().toUpperCase();
                String website = UpdatePage_website.getText().toString().toUpperCase();

                /* split VAT_CST_TIN_No with character "V"
                String vattinno = UpdatePage_VatCst_TinNo.getText().toString();
                String[] VATTINNo = vattinno.split("V");
                String VAT_CST_TIN_No = VATTINNo[0];*/

                String GST_No = UpdatePage_GST_No.getText().toString().toUpperCase();

                String discountRatePercent = UpdatePage_discountRatePercent.getText().toString();
                String remark = UpdatePage_Remark.getText().toString().toUpperCase();
                String transport = UpdatePage_transport.getText().toString().toUpperCase();

                /***********************************************************************************/
                /*  VALIDATIONS for fields
                /***********************************************************************************/
                ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollView_update_account_details);

                valid_fields_flag = Utility.Name_validateLetters(accountName, error_show_textview, scrollView);

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.Name_validateLetters(accountNameofOwner, error_show_textview, scrollView);
                }

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.Name_validateLetters(contactPersonName, error_show_textview, scrollView);
                }

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.City_State_validateLetters(city, error_show_textview,scrollView);
                }

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.City_State_validateLetters(state, error_show_textview,scrollView);
                }

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.Pincode_validation(pincode, error_show_textview,scrollView);
                }
                if (valid_fields_flag) {
                    if (firmAnniversaryDate.isEmpty()) {
                        valid_fields_flag = Utility.Date_validaiton(firmAnniversaryDate, error_show_textview,false,scrollView);
                    } else {
                        valid_fields_flag = Utility.Date_validaiton(firmAnniversaryDate, error_show_textview,true,scrollView);
                    }
                }
                if (valid_fields_flag) {
                    valid_fields_flag = Utility.Date_validaiton(BirthDate,error_show_textview,true,scrollView);
                }
                if (valid_fields_flag) {
                    valid_fields_flag = Utility.Date_validaiton(anniversaryDate,error_show_textview,true,scrollView);
                }
                if (valid_fields_flag) {

                    if (contactPersonMobileNo .isEmpty()) {
                        valid_fields_flag = Utility.MobileNo_validation(contactPersonMobileNo, error_show_textview,false,scrollView);
                    } else {
                        valid_fields_flag = Utility.MobileNo_validation(contactPersonMobileNo, error_show_textview,true,scrollView);
                    }
                }
                if (valid_fields_flag) {

                    valid_fields_flag = Utility.MobileNo_validation(ownerMobileNo,error_show_textview, true,scrollView);
                }
                if (valid_fields_flag) {
                    valid_fields_flag = Utility.LandlineNo_validation(landlineno,error_show_textview,scrollView);
                }
                if (valid_fields_flag) {
                    if (EmailId.isEmpty()) {
                        valid_fields_flag = Utility.AccountEmail_validation(EmailId,error_show_textview, false,scrollView);
                    } else {
                        valid_fields_flag = Utility.AccountEmail_validation(EmailId, error_show_textview, true,scrollView);
                    }
                }
                if (valid_fields_flag) {
                    if (panNo.isEmpty()) {

                        valid_fields_flag = Utility.panNo_validation(panNo,error_show_textview, false,scrollView);
                    } else {
                        valid_fields_flag = Utility.panNo_validation(panNo, error_show_textview, true,scrollView);
                    }
                }

              /*  if(valid_fields_flag){
                    valid_fields_flag = Utility.GST_NO_validation(GST_No,error_show_textview,scrollView);
                }*/

                if (valid_fields_flag) {
                    valid_fields_flag = Utility.FaxNo_validation(Faxno,error_show_textview,scrollView);
                }
                /*if (valid_fields_flag) {
                    valid_fields_flag = Utility.VatCstTinNo_validation(VAT_CST_TIN_No,error_show_textview,scrollView);
                }*/
                if (valid_fields_flag) {
                    valid_fields_flag = Utility.DiscountRate_validation(discountRatePercent, error_show_textview,scrollView);
                }
               /* if (valid_fields_flag) {
                    valid_fields_flag = Utility.VatCstTinNo_validation(VAT_CST_TIN_No,error_show_textview,scrollView);
                }*/

                /***********************************************************************************/
                /*Create an account master object for insertion*/
                /***********************************************************************************/

                if (valid_fields_flag) {
                    final Account_Master account_master = new Account_Master();

                    account_master.setID(Showall_Details_Itent.getID());
                    account_master.setAccountName(accountName);
                    account_master.setNameOfOwner(accountNameofOwner);
                    account_master.setAddress1(Address1);
                    account_master.setAddress2(Address2);
                    account_master.setAddress3(Address3);
                    account_master.setCity(city);
                    account_master.setState(state);
                    account_master.setPinCode(pincode);
                    account_master.setFirmAnniversaryDate(firmAnniversaryDate);
                    account_master.setContactPerson(contactPersonName);
                    account_master.setContactPersonMobileNo(contactPersonMobileNo);
                    account_master.setOwnerMobileNo(ownerMobileNo);
                    account_master.setLandlineNo(landlineno);
                    account_master.setBirthDate(BirthDate);
                    account_master.setAnniversaryDate(anniversaryDate);
                    account_master.setEmailID(EmailId);
                    account_master.setFaxNo(Faxno);
                    account_master.setWebsite(website);
                    account_master.setPanNo(panNo);

                    account_master.setGST(GST_No);

                    //account_master.setVatTinNo(VAT_CST_TIN_No + "V");
                    //account_master.setCSTNo(VAT_CST_TIN_No + "C");

                    account_master.setRemarks(remark);
                    account_master.setDiscount_Rate_Percent(discountRatePercent);
                    account_master.setTransport(transport);
                    account_master.setAgentID(Agentid);
                    account_master.setLastUpdatedDate(Get_current_date());

                    // call function to insert value into database by passing parameter into it
                    if (valid_fields_flag) {
                        insertData_AccountMaster(account_master);
                    }
                }
            }
        });
    }

    public String Get_current_date() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat today_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str_today_Date = today_date.format(c.getTime());
        // return current date
        return str_today_Date;
    }

    /**********************************************************************************************
     * Name :- insertData_AccountMaster
     * Input :-pass Account_Master object account_master
     * Description :- onclick of submit button alter box will be create for conformation on click
     * on conformation button data will be inserted into database and on click
     * on exit button dialog box will be cancel.
     **********************************************************************************************/
    public void insertData_AccountMaster(final Account_Master account_master) {
        // create alert box on click of submit button
        final AlertDialog.Builder UpdateAccount_alertBox_insert =
                new AlertDialog.Builder(UpdatePage_AccountDetails_singleCustomer.this);
        // set title on alert box
        UpdateAccount_alertBox_insert.setTitle(R.string.updatePage_insertData_alertTitle);
        // set message to alter box
        UpdateAccount_alertBox_insert.setMessage(R.string.updatePage_insertData_alertMessage);
        // set  cancelable alert box automatically
        UpdateAccount_alertBox_insert.setCancelable(false);
        // set positive button
        UpdateAccount_alertBox_insert.setPositiveButton(R.string.updatePage_insertData_alertTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ProgressDialog progressBar = new ProgressDialog(UpdatePage_AccountDetails_singleCustomer.this);
                Utility.show_progress_bar(progressBar, "Updating .....");

                ContentValues updateAccountValuesTOLocalDB = new ContentValues();

                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ID, account_master.getID());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AccountName, account_master.getAccountName());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_NameOfOwner, account_master.getNameOfOwner());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address1, account_master.getAddress1());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address2, account_master.getAddress2());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Address3, account_master.getAddress3());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_City, account_master.getCity());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_State, account_master.getState());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_PinCode, account_master.getPinCode());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_FirmAnniversaryDate, account_master.getFirmAnniversaryDate());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ContactPerson, account_master.getContactPerson());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_ContactPersonMobileNo, account_master.getContactPersonMobileNo());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_OwnerMobileNo, account_master.getOwnerMobileNo());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_LandlineNo, account_master.getLandlineNo());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_BirthDate, account_master.getBirthDate());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AnniversaryDate, account_master.getAnniversaryDate());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_EmailID, account_master.getEmailID());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_FaxNo, account_master.getFaxNo());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Website, account_master.getWebsite());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_PanNo, account_master.getPanNo());

                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_GST,account_master.getGST());

                //updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_VatTinNo, account_master.getVatTinNo());
                //updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_CSTNo, account_master.getCSTNo());


                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_AgentID, account_master.getAgentID());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Discount_Rate_Percent, account_master.getDiscount_Rate_Percent());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Remarks, account_master.getRemarks());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_Transport, account_master.getTransport());
                updateAccountValuesTOLocalDB.put(GlobalProvider.AccountMaster_LastUpdatedDate, account_master.getLastUpdatedDate());

                int uri = getContentResolver().update(GlobalProvider.CONTENT_URI_ACCOUNT, updateAccountValuesTOLocalDB,
                        "AccountId = ?", new String[]{Showall_Details_Itent.getID()});

                // show message to user when data is updated
                Toast.makeText(UpdatePage_AccountDetails_singleCustomer.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                progressBar.dismiss();

                finish();
                Intent intent = new Intent(UpdatePage_AccountDetails_singleCustomer.this, Account_Details.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("review_flag", false);
                startActivity(intent);
            }
        })
                .setNegativeButton(R.string.updatePage_insertData_alertExitMassage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // create alert box
        AlertDialog alertDialog = UpdateAccount_alertBox_insert.create();
        // show alert box
        alertDialog.show();
    }

    /*********************************************************************************************
     * Name :-  Updatepage_Cancel
     * Description :- Call Account_Details page when clicking on cancel button
     *********************************************************************************************/
    public void backPervious_cancelButton() {

        // set on clicklistener on cancel button
        UpdatePage_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean reviewFlag = getIntent().getExtras().getBoolean("review_flag");

                // create Intent object to call another activity
                Intent callAccountsPage = new Intent(UpdatePage_AccountDetails_singleCustomer.this, Account_Details.class);
                callAccountsPage.putExtra("review_flag", reviewFlag);
                startActivity(callAccountsPage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_page__account_details_single_customer, menu);
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
     * Name :- onStart
     * Description :- onStart function use for date picker so that user can select date.
     *********************************************************************************************/
    public void setDate(EditText et) {
        // set setOnFocusChangeListener on field of date to select date
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // check condition boolean value
                if (hasFocus) {
                    // create object of Dialog_DatePicker_addAccount class
                    Dialog_DatePicker_addAccount dialog_datePicker = new Dialog_DatePicker_addAccount(v);

                    /* set FragmentTransaction object
                     getFragmentManager() :- Return the FragmentManager for interacting with
                     fragments associated with this fragment's activity */
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    // show dialog picker to user
                    dialog_datePicker.show(fragmentTransaction, "Date Picker");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UpdatePage_AccountDetails_singleCustomer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.ppp_admin.ppp_order_app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UpdatePage_AccountDetails_singleCustomer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.ppp_admin.ppp_order_app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    /**********************************************************************************************/
}
