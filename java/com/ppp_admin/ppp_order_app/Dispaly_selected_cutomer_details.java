package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dispaly_selected_cutomer_details extends Activity
{
    /****************************************************************************************************
         * Section for declaration of the variables
     ****************************************************************************************************/
     TextView data_textview1,data_textview2,data_textview3,data_textview4,data_textview5,data_textview6,
     data_textview7,data_textview8,data_textview9,data_textview10,data_textview11,data_textview12,
     data_textview13,data_textview14,data_textview15,data_textview16,data_textview17,data_textview18,
     data_textview19,data_textview20,data_textview21,data_textview22,data_textview23;

     Account_Master selected_Data;

    /*************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispaly_selected_cutomer_details);

        DisplayData_TextView();
        setData_Textview();
    }

    /******************************************************************************************
        Name :- DisplayData_TextView
        Description :- Display all the textview in activity page
     *****************************************************************************************/
    public void DisplayData_TextView() {

        data_textview1 = (TextView) findViewById(R.id.firmName_textView);
        data_textview2 = (TextView) findViewById(R.id.nameOfOwner_textView);
        data_textview3 = (TextView) findViewById(R.id.address1_textView);
        data_textview4 = (TextView) findViewById(R.id.address2_textView);
        data_textview5 = (TextView) findViewById(R.id.address3_textView);
        data_textview6 = (TextView) findViewById(R.id.city_textView);
        data_textview7 = (TextView) findViewById(R.id.state_textView);
        data_textview8 = (TextView) findViewById(R.id.pincode_textView);
        data_textview9 = (TextView) findViewById(R.id.FirmAnniversaryDate_textView);
        data_textview10 = (TextView) findViewById(R.id.contactPersonName_textView);
        data_textview11 = (TextView) findViewById(R.id.customerMobileNo_textView);
        data_textview12 = (TextView) findViewById(R.id.ownerMobileNo_textView);
        data_textview13 = (TextView) findViewById(R.id.landline_textView);
        data_textview14 = (TextView) findViewById(R.id.birthdate_textView);
        data_textview15 = (TextView) findViewById(R.id.anniversaryDate_textView);
        data_textview16 = (TextView) findViewById(R.id.emailId_textView);
        data_textview17 = (TextView) findViewById(R.id.faxNo_textView);
        data_textview18 = (TextView) findViewById(R.id.website_textView);
        data_textview19 = (TextView) findViewById(R.id.panNo_textView);
        data_textview20 = (TextView) findViewById(R.id.GST_No_textview);
        data_textview21 = (TextView) findViewById(R.id.disountRatePercent_textView);
        data_textview22 = (TextView) findViewById(R.id.remarks_textView);
        data_textview23 = (TextView) findViewById(R.id.transport_textView);
    }

    /****************************************************************************************
        Name :- setData_Textview
        Description :- set all accounts data into textview
     ****************************************************************************************/
    public void setData_Textview() {

        /***************************************************************************************
         call Account Master Intent to pass Account_Master value from Account_Details.class
         **************************************************************************************/
        selected_Data= (Account_Master) getIntent().getSerializableExtra("currentData");

        initialize_date_variables();
    }
    /*********************************************************************************************
        Name :- convert_date_format
        Description :- Convert date into date format
     ********************************************************************************************/
    public void initialize_date_variables(){

        // create format for date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // create Date objects
        Date Convertdateformate_birthdate = null, Convertdateformate_fimAnniversaryDate=null,
                Convertdateformate_AnniversaryDate = null;

        // create String object
        String birthday_date = new String();
        String firmAnniversaryDate = new String();
        String AnniversaryDate = new String();

        /* --> get all birthdate, anniversarydate and firm_anniversary_date from Account_Master object
                and convert into date SimpleDateFormat and store into Date object.
           --> get all birthdate,anniversarydate and firmanniversarydate into string object */
        try {
            // convert all the dates into date format
            if(!selected_Data.getBirthDate().isEmpty() && !selected_Data.getBirthDate().equals("")
                    && !selected_Data.getBirthDate().equals(" ") ) {

                Convertdateformate_birthdate = dateFormatter.parse(selected_Data.getBirthDate());
                birthday_date = dateFormatter.format(Convertdateformate_birthdate);
            }

            if(!selected_Data.getFirmAnniversaryDate().isEmpty() && !selected_Data.getFirmAnniversaryDate().equals("") &&
                    !selected_Data.getFirmAnniversaryDate().equals(" ") && !selected_Data.getFirmAnniversaryDate().equals(null)) {

                Convertdateformate_fimAnniversaryDate = dateFormatter.parse(selected_Data.getFirmAnniversaryDate());
                firmAnniversaryDate = dateFormatter.format(Convertdateformate_fimAnniversaryDate);
            }

            if(!selected_Data.getAnniversaryDate().isEmpty() && !selected_Data.getAnniversaryDate().equals("")
                    && !selected_Data.getAnniversaryDate().equals(" ") && !selected_Data.getAnniversaryDate().equals(null) ) {

                Convertdateformate_AnniversaryDate = dateFormatter.parse(selected_Data.getAnniversaryDate());
                AnniversaryDate = dateFormatter.format(Convertdateformate_AnniversaryDate);
            }

        } catch (ParseException e) {e.printStackTrace();}

        set_values_in_textview(firmAnniversaryDate, birthday_date, AnniversaryDate);
    }

    /*********************************************************************************************
        Name :- set_values_in_textview
        Description :- set accounts details in textview
     *********************************************************************************************/
    public void set_values_in_textview(String firmAnniversaryDate, String birthday_date, String AnniversaryDate){

         /* set data to textview through Account_Master Intent's object as selected_data */
        data_textview1.setText(selected_Data.getAccountName().toString().toUpperCase());
        data_textview2.setText(selected_Data.getNameOfOwner().toString().toUpperCase());
        data_textview3.setText(selected_Data.getAddress1().toString().toUpperCase());
        data_textview4.setText(selected_Data.getAddress2().toString().toUpperCase() );
        data_textview5.setText(selected_Data.getAddress3().toString().toUpperCase() );
        data_textview6.setText(selected_Data.getCity().toString().toUpperCase() );
        data_textview7.setText(selected_Data.getState().toString().toUpperCase() );
        data_textview8.setText(selected_Data.getPinCode() );
        data_textview9.setText(firmAnniversaryDate);
        data_textview10.setText(selected_Data.getContactPerson().toString().toUpperCase() );
        data_textview11.setText(selected_Data.getContactPersonMobileNo());
        data_textview12.setText(selected_Data.getOwnerMobileNo());
        data_textview13.setText(selected_Data.getLandlineNo());
        data_textview14.setText(birthday_date);
        data_textview15.setText(AnniversaryDate);
        data_textview16.setText(selected_Data.getEmailID().toString().toUpperCase() );
        data_textview17.setText(selected_Data.getFaxNo());
        data_textview18.setText(selected_Data.getWebsite().toString().toUpperCase());
        data_textview19.setText(selected_Data.getPanNo().toString().toUpperCase() );
        data_textview20.setText(selected_Data.getGST().toString().toUpperCase());
        data_textview21.setText(selected_Data.getDiscount_Rate_Percent());
        data_textview22.setText(selected_Data.getRemarks().toString().toUpperCase() );
        data_textview23.setText(selected_Data.getTransport());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispaly_selected_cutomer_details, menu);
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
    /******************************************************************************************
        Name :- UpdatePageCall
        Input :- MeanuItem will be called
        Description :- this function call another page and pass Account_Master value.
     ******************************************************************************************/
    public void UpdatePageCall(MenuItem item) {
        // declare Intent object to call another page
        Intent updatePageIntent = new Intent(this,UpdatePage_AccountDetails_singleCustomer.class);

        // put all data to another page through selected_Data object
        updatePageIntent.putExtra("showallDetails",selected_Data);
        boolean reviewFlag = getIntent().getExtras().getBoolean("review_flag");
        updatePageIntent.putExtra("review_flag",reviewFlag);

        // start activity by calling intent object
        startActivity(updatePageIntent);
    }
}
