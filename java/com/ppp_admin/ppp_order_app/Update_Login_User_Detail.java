package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Update_Login_User_Detail extends Activity
{
    /*********************************************************************************************
     * Section for declaration of the variables
     ********************************************************************************************/
    Intent intent;
    LoginUser_Data loginData ,updatedLoginData ;
    private MobileServiceTable<LoginUser_Data> loginUser_data;
     ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__login__user__detail);

        // function to Establish connection to server ot database
        try {
              /* Establish connection through MobileServiceClient object as mClient and passing ulr and key to context.*/
            MobileServiceClient  mClient = new MobileServiceClient("https://pressfit-accounts.azure-mobile.net/",
                    "SSXvnqKmdneRWFDZBdcyQCyiDwBHzc77", Update_Login_User_Detail.this);

            /***********************************************************************************************
             * MobileServiceTable object by calling the getTable method on the MobileServiceClient.
             * getTable method use table name existing in database.
             **********************************************************************************************/
            loginUser_data = mClient.getTable("LoginUser_Credentials", LoginUser_Data.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        scrollView = (ScrollView) findViewById(R.id.ScrollView_update_login_user);

        intent = getIntent();

        Globals g = (Globals) getApplication();
        loginData = g.getLoginData();

        // assign loginData to new variable of LoginUser_Data
        updatedLoginData = loginData;

        // function call to display Login User Information
        try {displayData_LoginUser();}
        catch (ParseException e) {e.printStackTrace();}
    }

    /*********************************************************************************************
     * Name :- displayData_LoginUser
     * Description :- Display all user's data on update_Login_User_Detail page.
     ********************************************************************************************/
    public void displayData_LoginUser() throws ParseException
    {
        /**************************************************************
         Set textview to display login user all data.
         *************************************************************/
        TextView display_name = (TextView) findViewById(R.id.name_textView);
        display_name.setText("Hi "+ updatedLoginData.getName());

        TextView display_emailid = (TextView) findViewById(R.id.emailid_textView_updateProfile);
        display_emailid.setText("Email : " + updatedLoginData.getEmail_ID());

        TextView display_mobileNo = (TextView) findViewById(R.id.mobileNo_textView);
        display_mobileNo.setText("Mobile No : " + updatedLoginData.getMobileNo());

        TextView display_gender = (TextView) findViewById(R.id.gender_textView);
        display_gender.setText("Gender : " + updatedLoginData.getGender());

        /**********************************************************
         Convert date into date format to display date only
         **********************************************************/
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date Convertdateformate_birthdate = null;
        Convertdateformate_birthdate = dateFormatter.parse(loginData.getDateOfBirth());
        String birthday_date = dateFormatter.format(Convertdateformate_birthdate);

        TextView dispaly_DOB = (TextView) findViewById(R.id.dateOfBirth_textView);
        dispaly_DOB.setText("Date of Birth : " + birthday_date);

        /*****************************************************************
            Display textview on activity
         *****************************************************************/
        TextView updateName = (TextView) findViewById(R.id.loginUser_updateName_textView);
        updateName.setPaintFlags(updateName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView updateEmail = (TextView) findViewById(R.id.loginUser_updateEmailId_textView);
        updateEmail.setPaintFlags(updateEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView updateMobileNo = (TextView) findViewById(R.id.loginUser_updateMobileNo_textView);
        updateMobileNo.setPaintFlags(updateMobileNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView updatePassword = (TextView) findViewById(R.id.loginUser_changepassword_textView);
        updatePassword.setPaintFlags(updatePassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    /**********************************************************************************************
     * Name :- updateName
     * Description :- Set onclick on textview so that user when clicks on textview they can change
     * there data or information and saved changes data into database.
     *********************************************************************************************/
    public void updateName(View v) {

        // set Alert Dialog box object
        AlertDialog.Builder update_Name_alertBox = new AlertDialog.Builder(Update_Login_User_Detail.this);

        // set Title to alter box
        update_Name_alertBox.setTitle(R.string.UpdateLogin_updateName_alertMSG_title);

        // set messgae to alert box
        update_Name_alertBox.setMessage(R.string.UpdateLogin_updateName_alertMSG_Message);

        // create edittext
        final EditText changeName_edtitext = new EditText(Update_Login_User_Detail.this);

        // add editext into view
        update_Name_alertBox.setView(changeName_edtitext);

        // set negative button
        update_Name_alertBox.setNegativeButton(Html.fromHtml("<font color='#FF0000'> Cancel </font> "),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel dialog
                        dialog.cancel();
                    }
                });

        // set positive button
        update_Name_alertBox.setPositiveButton(R.string.UpdateLogin_update_PositiveButtonMSG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // get entered value from user to String variable
                String enteredName = changeName_edtitext.getText().toString();

                // validation for name entered ny user
                if (Utility.Name_validateLetters(enteredName, changeName_edtitext, scrollView)) {
                    // set enterName to updatedLoginData
                    updatedLoginData.setName(enteredName);

                    // call function to update data
                    updateField();
                } else {
                    // message will be show if user enter worng name.
                    Toast.makeText(Update_Login_User_Detail.this,
                            R.string.UpdateLogin_updateName_toastMSG, Toast.LENGTH_LONG).show();
                }
            }
        });
        // show or create alert box
        update_Name_alertBox.show();
    }

    /********************************************************************************************
     * Name :- updateEmail
     * Description :-  Set onclick on textview so that user when clicks on textview they can change
     * there data or information and saved changes data into database.
     *******************************************************************************************/
     public void updateEmail(View v) {

        // set Alert Dialog box object
        final AlertDialog.Builder update_Email_alertBox = new AlertDialog.Builder(Update_Login_User_Detail.this);

        // set Title to alter box
        update_Email_alertBox.setTitle(R.string.UpdateLogin_updateEmail_alertTitleMSG);

        // set messgae to alert box
        update_Email_alertBox.setMessage(R.string.UpdateLogin_updateEmail_alertMessage);

        // create edittext
        final EditText changeEmail_edtitext = new EditText(Update_Login_User_Detail.this);

        // add editext into view
        update_Email_alertBox.setView(changeEmail_edtitext);

        // set positive button
        update_Email_alertBox.setPositiveButton(R.string.UpdateLogin_update_PositiveButtonMSG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // get entered value from user to String variable
                String enteredEmail = changeEmail_edtitext.getText().toString();

                // Validation for email id enter by user
                if (Utility.AccountEmail_validation(enteredEmail, changeEmail_edtitext, true, scrollView)) {

                    // set enteredEmail to updatedLoginData
                    updatedLoginData.setEmail_ID(enteredEmail);
                    if(!enteredEmail.equals("")) {
                        // call function o update data
                        updateField();
                    } else {
                        Toast.makeText(Update_Login_User_Detail.this,"Pleas enter your email id",Toast.LENGTH_LONG).show();
                    }
                } else {
                    // message will be show if user enter worng name.
                    Toast.makeText(Update_Login_User_Detail.this,
                            R.string.UpdateLogin_updateEmail_ToastMSG, Toast.LENGTH_LONG).show();
                }
            }
        });
          // set negative button
          update_Email_alertBox.setNegativeButton(Html.fromHtml("<font color='#FF0000'> Cancel</font>"),
         new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
                 // cancel alert box
                 dialog.cancel();
             }
                  });
         AlertDialog d = update_Email_alertBox.create();
        // show or create alert box
        d.show();

     }

    /*********************************************************************************************
     * Name :- updatePassword
     * Description :-  Set onclick on textview so that user when clicks on textview they can change
     * there data or information and saved changes data into database.
     ********************************************************************************************/
    public void updatePassword(View v)
    {
      // set AlertDialog.Builder object
      AlertDialog.Builder update_password_alertBox = new AlertDialog.Builder(Update_Login_User_Detail.this);

       // set title for alter box
       update_password_alertBox.setTitle(R.string.UpdateLogin_updatePassword_alertTitleMSG);

       // set message for alert box
       update_password_alertBox.setMessage(R.string.UpdateLogin_updatePassword_alertMessage);

        // create edittext object
       final EditText oldPassword_editText = new EditText(Update_Login_User_Detail.this);

        // password set in dots
        oldPassword_editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // set hint to edittext
        oldPassword_editText.setHint("old password");

       // create edititext
       final EditText changePassword_edtitext = new EditText(Update_Login_User_Detail.this);

        // password set in dots
        changePassword_edtitext.setTransformationMethod(PasswordTransformationMethod.getInstance());

       // set hint to Editext
       changePassword_edtitext.setHint("new password");

       // create edittext
       final EditText confirm_password = new EditText(Update_Login_User_Detail.this);

        // password set in dots
        confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

       // set hint tot edittext
       confirm_password.setHint("confirm password");

        // create linearlaoyut for context
       LinearLayout linearLayout = new LinearLayout(Update_Login_User_Detail.this);

       // set orientation of layout
       linearLayout.setOrientation(LinearLayout.VERTICAL);

       // add edittext into linearlayout
       linearLayout.addView(oldPassword_editText);

       // add edittext into linearlayout through object
       linearLayout.addView(changePassword_edtitext);

       // add edittext into linearlayout
       linearLayout.addView(confirm_password);

       // add linearlayout into alertbox object
       update_password_alertBox.setView(linearLayout);

       // set positive button
       update_password_alertBox.setPositiveButton(R.string.UpdateLogin_update_PositiveButtonMSG,
               new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               // get old password from user to string variable
               String oldPassword = oldPassword_editText.getText().toString();

               // get entered from user to String variable
               String enteredNewPassword = changePassword_edtitext.getText().toString();

               // get entered from user to String variable
               String enteredConfirmPassword = confirm_password.getText().toString();

               /*****************************************************************************
                --> check condition old password match and entered new password and confirm
                password matches if value will be true LoginUser_Data object will be updated
                otherwise function will be close.
                *****************************************************************************/
               if (oldPassword.equals(loginData.getUserPassword())
                       && enteredConfirmPassword.equals(enteredNewPassword)
                       && enteredConfirmPassword.length() >= 6) {

                   // set confirm password to updatedLoginData
                   updatedLoginData.setUserPassword(enteredConfirmPassword);

                   // function will call to update data
                   updateField();

               } else {

                   /***********************************************************************
                    -->  message will be shown if new and confirm password will not be
                    correct entered by user.
                    ***********************************************************************/
                   Toast.makeText(Update_Login_User_Detail.this,
                          R.string.UpdateLogin_updatePassword_ToastMSG , Toast.LENGTH_LONG).show();
               }
           }
       });

        // set negative button
        update_password_alertBox.setNegativeButton(Html.fromHtml("<font color='#FF0000'> Cancel</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel alert box
                        dialog.cancel();
                    }
                });

        AlertDialog alert = update_password_alertBox.create();

        // show or create alert box
        alert.show();
    }

    /*********************************************************************************************
     * Name :- updateMobileNo
     * Description :- Set onclick on textview so that user when clicks on textview they can change
                      there data or information and saved changes data into database.
     ********************************************************************************************/
    public void updateMobileNo(View v)
    {
        // create AlertDialog.builder object
        AlertDialog.Builder update_MobileNo_alertBox = new AlertDialog.Builder(this);

        // set Title for alert box
        update_MobileNo_alertBox.setTitle(R.string.UpdateLogin_updateMobileNo_alertTitleMSG);

        // set message for alert box
        update_MobileNo_alertBox.setMessage(R.string.UpdateLogin_updateMobileNo_alertMessage);

        // create edittext
        final EditText changeMobileNo_edtitext = new EditText(this);

        // set input type of alert box as numeric
        changeMobileNo_edtitext.setInputType(InputType.TYPE_CLASS_NUMBER);

        // add edittext into alert box
        update_MobileNo_alertBox.setView(changeMobileNo_edtitext);

        // set positive button
        update_MobileNo_alertBox.setPositiveButton(R.string.UpdateLogin_update_PositiveButtonMSG,
                    new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // get entered value into string variable
                String enteredMobileNo = changeMobileNo_edtitext.getText().toString();

                // validation checks for mobile no entered by user
                if(Utility.MobileNo_validation(enteredMobileNo,changeMobileNo_edtitext,true,scrollView))
                {
                    // set enteredMobileNo to updatedLoginData
                    updatedLoginData.setMobileNo(enteredMobileNo);

                    // call function to update data
                    updateField();
                }
                else {
                    // message will be shown if user enter mobile no more then 10 digit
                    Toast.makeText(Update_Login_User_Detail.this,
                            R.string.UpdateLogin_updateMobileNo_ToastMSG, Toast.LENGTH_LONG).show();
                }
            }
        });

        // create negative button
        update_MobileNo_alertBox.setNegativeButton(Html.fromHtml("<font color='#FF0000'> Cancel</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // cancel alert box
                        dialog.cancel();
                    }
                });

        // create or show alert box
        update_MobileNo_alertBox.show();
    }

    /*******************************************************************************************
        Name :- updateField
        Description :- This function update field in database, what every changes made by user.
     *******************************************************************************************/
    public void updateField()
    {
        // create progrssDialog object
        final ProgressDialog updateLoginUser_progressBar = new ProgressDialog(Update_Login_User_Detail.this);

        // create AsyncTask do work in background process
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Utility.show_progress_bar(updateLoginUser_progressBar, "Please Wait ....");
            }

            @Override
            protected Void doInBackground(Void... params) {
                try
                {
                    /*****************************************************************************
                        fire query to update data into database using LoginUser_Data
                        object contains updated data
                     *****************************************************************************/
                    //loginUser_data.update(updatedLoginData).get();
                    loginUser_data.update(updatedLoginData).get().toString().toUpperCase();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                        // dismiss progressbar
                        updateLoginUser_progressBar.dismiss();

                        // show toast message to user
                        Toast.makeText(Update_Login_User_Detail.this,
                         R.string.UpdateLogin_updateField_PostExecute_ToastMSG, Toast.LENGTH_LONG).show();

                    // function call to finish process
                    finish();

                    // again activity starts
                    startActivity(intent);
            }
        }.execute();
    }
    /********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update__login__user__detail, menu);
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
}
