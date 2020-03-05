package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;


public class SignUp_Page extends Activity
{
    /*********************************************************************************************
     * Section for declaration of the variables
     *********************************************************************************************/
     private MobileServiceTable<LoginUser_Data> loginUser_data;
     boolean valid_fields_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up__page);

        /* MobileServiceClient object that is used to access your mobile service */
        try {
            /* Establish connection through MobileServiceClient object as mClient and passing url
             and key to context.*/
            MobileServiceClient mClient = new MobileServiceClient("https://pressfit-accounts.azure-mobile.net/",
                                                "SSXvnqKmdneRWFDZBdcyQCyiDwBHzc77",SignUp_Page.this);

            /* MobileServiceTable object by calling the getTable method on the MobileServiceClient.
             * getTable method use table name existing in database.*/
            loginUser_data = mClient.getTable("LoginUser_Credentials", LoginUser_Data.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setDate();
    }

    /*********************************************************************************************
        Name :- signUp_cancel
        Description :- Set OnclickListener on cancel button to clear all fields.
     *******************************************************************************************/
      public void signUp_cancel(View v) {

          EditText signUp_Name = (EditText)findViewById(R.id.signup_name_editText);
          EditText signUp_username = (EditText)findViewById(R.id.signup_userName_editText);
          EditText signUp_password = (EditText)findViewById(R.id.signUp_userPassword_editText);
          EditText signup_emailid = (EditText)findViewById(R.id.signup_emailID_editText);
          EditText signup_mobileno = (EditText)findViewById(R.id.signup_mobileNo_editText);
          EditText signUp_confirmPassword= (EditText)findViewById(R.id.signUp_confirmPassword_editText);
          EditText signUp_DateOfBirth = (EditText)findViewById(R.id.signUp_DOB_edittext);

          signUp_Name.setText("");
          signUp_username.setText("");
          signUp_password.setText("");
          signUp_confirmPassword.setText("");
          signup_emailid.setText("");
          signup_mobileno.setText("");
          signUp_DateOfBirth.setText("");
      }

    /**********************************************************************************************
        Name :- SignUp_submit_button
        Description :- Onclick of submit button it will check validation if all input value will be
        correct then data will be inserted into database and user can see message.
     **********************************************************************************************/
      public void SignUp_submit_button(View v) {
          /* This boolean variable is a flag to check the validation of all fields in the form */
          valid_fields_flag = true;

         /* Retrieve all values of the form */
          final LoginUser_Data insert_loginUser_Data = retrieveValues();

          // check valid_fields_flag value if it will true proceed in condition other wise code will be code.
          if (insert_loginUser_Data != null && valid_fields_flag) {
              insert_loginUser_Data.setGender(setclickRadioButton());
              insert_loginUser_Data.setDeactivated("N");

              confirm_alert_box(insert_loginUser_Data);
          }
      }

    /***********************************************************************************************
     * Name :- setDate
     * Description:- This function is called as soon as the user clicks on the edit text of date of
     * birth in order to open the date picker for date selection
     **********************************************************************************************/
    public void setDate() {

        EditText dob = (EditText) findViewById(R.id.signUp_DOB_edittext);
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    /***********************************************************************************************
     * Name:- retrieveValues
     * Description :- This function retrieves all the fields from the form, validates each of them
     * and then returns a not-null object if all the fields are valid
     **********************************************************************************************/
     public LoginUser_Data retrieveValues(){

         ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollView_sign_up);

        LoginUser_Data formData = new LoginUser_Data();

        EditText signUp_Name = (EditText)findViewById(R.id.signup_name_editText);
        EditText signUp_username = (EditText)findViewById(R.id.signup_userName_editText);
        EditText signUp_password = (EditText)findViewById(R.id.signUp_userPassword_editText);
        EditText signup_emailid = (EditText)findViewById(R.id.signup_emailID_editText);
        EditText signup_mobileno = (EditText)findViewById(R.id.signup_mobileNo_editText);
        EditText signUp_confirmPassword= (EditText)findViewById(R.id.signUp_confirmPassword_editText);
        EditText signUp_DateOfBirth = (EditText)findViewById(R.id.signUp_DOB_edittext);

         /* Retrieving all values provided by the user in the form */
        String signUp_name = signUp_Name.getText().toString().toUpperCase();
        String signUp_userName = signUp_username.getText().toString().toUpperCase();
        String signUp_EmailID = signup_emailid.getText().toString().toUpperCase();
        String signUp_MobileNo = signup_mobileno.getText().toString();
        String signUp_ConfirmPassword = signUp_confirmPassword.getText().toString();
        String signUp_Password = signUp_password.getText().toString();
        String userDateOfBirth = signUp_DateOfBirth.getText().toString();

        /* Check Password fields */
        isValidPassword(signUp_password, signUp_confirmPassword, signUp_Password, signUp_ConfirmPassword);

        /**********************************************************************************
         /*  VALIDATIONS for fields
         /*********************************************************************************/
        if(valid_fields_flag) {
            valid_fields_flag = Utility.UsernameValidator(signUp_userName, signUp_username,scrollView);
        }

        if(valid_fields_flag) {
            valid_fields_flag = Utility.MobileNo_validation(signUp_MobileNo, signup_mobileno,true,scrollView);
        }

        if(valid_fields_flag) {
            valid_fields_flag = Utility.AccountEmail_validation(signUp_EmailID, signup_emailid,true,scrollView);
        }

        if(valid_fields_flag) {
            valid_fields_flag = Utility.Name_validateLetters(signUp_name, signUp_Name,scrollView);
        }

        if(valid_fields_flag) {
            valid_fields_flag = Utility.Date_validaiton(userDateOfBirth, signUp_DateOfBirth, true,scrollView);
        }

        if(valid_fields_flag){
            formData.setName(signUp_name);
            formData.setUserName(signUp_userName);
            formData.setUserPassword(signUp_Password);
            formData.setEmail_ID(signUp_EmailID);
            formData.setMobileNo(signUp_MobileNo);
            formData.setDateOfBirth(userDateOfBirth);
        }
        return formData;
    }

    /***********************************************************************************************
     * Name:- confirm_alert_box
     * Input :- LoginUser_Data
     * Description:- This function displays the alert box to confirm the insertion of the data and
     * takes further action accordingly
     **********************************************************************************************/
     private void confirm_alert_box(final LoginUser_Data insert_loginUser_Data) {
        final AlertDialog.Builder signUp_alertBox = new AlertDialog.Builder(SignUp_Page.this);

        // set title for alert box
        signUp_alertBox.setTitle(R.string.signUp_alert_setTitle);
        signUp_alertBox.setMessage(R.string.signUp_alert_Message);
        signUp_alertBox.setCancelable(false);
        signUp_alertBox.setPositiveButton(R.string.signUp_positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onConfirmButtonClick(insert_loginUser_Data);
            }
        });
        // set negative button to close dialog box
        signUp_alertBox.setNegativeButton(R.string.signUp_alert_Exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if this button is clicked, just close the dialog box and do nothing
                dialog.cancel();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = signUp_alertBox.create();
        // show it
        alertDialog.show();
     }

    /***********************************************************************************************
     * Name:- onConfirmButtonClick
     * Input :- LoginUser_Data
     * Description:- This function inserts the data when the data to be inserted is confirmed
     **********************************************************************************************/
     private void onConfirmButtonClick(final LoginUser_Data insert_loginUser_Data){
        // create progress bar
        final ProgressDialog signUp_progressBar = new ProgressDialog(SignUp_Page.this);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Utility.show_progress_bar(signUp_progressBar,
                        String.valueOf("Please wait..."));
            }
            @Override
            protected Void doInBackground(Void... params) {
                try {
                /* Make list of username and email id are same in currentUsers
                                 object from MobileServiceList<LoginUser_Data> */
                    final MobileServiceList<LoginUser_Data> currentUsers = loginUser_data.where()
                            .field("UserName").eq(insert_loginUser_Data.getUserName()).or()
                            .field("EmailId").eq(insert_loginUser_Data.getEmail_ID()).execute().get();

                    // check currentUsers is empty or not
                    if (currentUsers.isEmpty()) {
                        loginUser_data.insert(insert_loginUser_Data).get().toString().toUpperCase();
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (currentUsers.isEmpty()) {
                                signUp_progressBar.dismiss();
                                // message will be shown when data will be inserted
                                Toast.makeText(SignUp_Page.this,"Your Information has been added successfully.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                signUp_progressBar.dismiss();
                                // message will be shown by user username and email already exist
                                Toast.makeText(SignUp_Page.this,
                                        R.string.signUp_signUp_button_toast2, Toast.LENGTH_LONG).show();
                            }
                            finish();
                            Intent next_page = new Intent(SignUp_Page.this, Login_form.class);
                            startActivity(next_page);
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /*********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign__up__page, menu);
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
        Name :- setclickRadioButton
        Input :- setclickRadioButton returns gender which user selected
        Description :- function checks user which radio button clicks according
                        it will perform action.
     *********************************************************************************************/
     public String setclickRadioButton()
     {
        RadioGroup rg = (RadioGroup) findViewById(R.id.gender_radioGroup_signUpPage);
        int checkedId = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)findViewById(checkedId);
        String gender = rb.getText().toString();
        if(gender.equals("Male")){
            return "M";
        }else if(gender.equals("Female")){
            return "F";
        }
        return "M";
     }

    /********************************************************************************************
     Name :- isValidPassword
     Input:- password edit text, confirm password edit text, password value, confirm password value
     Description :- Validation for password and conformation password should match.
     ********************************************************************************************/
      private void isValidPassword(EditText et_signUp_password, EditText et_signUp_confirmPassword,
                                      String signUp_Password , String signUp_confirmPassword) {
          /* Check condition of password entered by user */
          if (signUp_Password.length() < 6) {
              et_signUp_password.setError("Password should be at least 6 characters long");
              valid_fields_flag = false;
          }
          /* Check condition new and confirm password must match */
          if(!signUp_Password.equals(signUp_confirmPassword)){
              et_signUp_confirmPassword.setError("Both password fields must match.");
              valid_fields_flag = false;
          }
      }
}

