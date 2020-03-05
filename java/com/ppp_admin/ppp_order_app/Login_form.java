package com.ppp_admin.ppp_order_app;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Login_form extends AppCompatActivity
{
    /*********************************************************************************************
     Declaration of variables
     *********************************************************************************************/
    private static Context context;
    private MobileServiceTable<LoginUser_Data> loginUser_data;
    SharedPreferences loginSharedPref;
    Editor loginSharedPrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        // checking internet connection
        ConnectivityManager cm = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        boolean is_internet_active = Utility.check_Internet(cm, getApplicationContext());

        /* To proceed further only if there is working internet connection */
        if(is_internet_active) {
            try {
              /* Establish connection through MobileServiceClient object as mClient and passing ulr and key to context.*/
                MobileServiceClient mClient = new MobileServiceClient("https://pressfit-accounts.azure-mobile.net/",
                        "SSXvnqKmdneRWFDZBdcyQCyiDwBHzc77", Login_form.this);

                /* MobileServiceTable object by calling the getTable method on the MobileServiceClient.
                   getTable method use table name existing in database. */
                loginUser_data = mClient.getTable("LoginUser_Credentials", LoginUser_Data.class);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        adjust_UI();
    }

    /*********************************************************************************************
     Name : getAppContext
     Output: Context
     Description: Returns the context of this activity
     *********************************************************************************************/
      public static Context getAppContext(){
        return Login_form.context;
    }

    /**********************************************************************************************
     Name :- adjust_UI
     Description :- Create UserName and Password UI design
     *********************************************************************************************/
      public void adjust_UI() {

          EditText username = (EditText) findViewById(R.id.username_editText);
          username.setMaxHeight(username.getHeight());
          EditText password = (EditText) findViewById(R.id.password_editText);
          password.setMaxHeight(password.getHeight());
          password.setMaxWidth(password.getWidth());
      }

    /**********************************************************************************************
     Name :- login_process
     Description :- While clicking on Login button it will check if the username and password is
     correct or not. If it is correct then it will login successfully otherwise it will show an
     error message.
     *********************************************************************************************/
     public void login_process(View v) {

        // getting text from user
        EditText username = (EditText) findViewById(R.id.username_editText);
        String user_name = username.getText().toString().toUpperCase();

        // set ProgressDialog bar at time of Login
        final ProgressDialog login_progressBar = new ProgressDialog(Login_form.this);
        Utility.show_progress_bar(login_progressBar, String.valueOf("Signing In ..."));

        // getting password from user
        EditText password = (EditText) findViewById(R.id.password_editText);
        String pswrd = password.getText().toString();

        // if username and password will match then it will proceed further
        if (user_name.equals("") || pswrd.equals("")) {
            // if user directly clicks on login button without filling any data this message will show to user
            Toast.makeText(Login_form.this, R.string.Loginform_Emptyfields_ToastMessage, Toast.LENGTH_SHORT).show();
            login_progressBar.dismiss();
        } else {
            boolean flag_user_name_email;
            if(user_name.contains("@")) {
                flag_user_name_email = false;
                proceed_with_login(user_name, pswrd, login_progressBar,flag_user_name_email);
            } else {
                flag_user_name_email = true;
                proceed_with_login(user_name, pswrd, login_progressBar,flag_user_name_email);
            }
        }
    }

    /********************************************************************************************
     Name:- proceed_with_login
     Inputs:- UserName, Password, ProgressBar
     Description:- If the fields are non-blank this function verifies the user name and its
     passwords required for successful login
     *********************************************************************************************/
      public void proceed_with_login(final String user_name, final String password, final ProgressDialog login_progressBar, final boolean flag_user_name_email){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... params) {
                try {
                    // query firing to get username from database,that puts user in editext
                   final MobileServiceList<LoginUser_Data> currentUsers = loginUser_data.where().field("UserName").eq(user_name).
                                                    or().field("EmailId").eq(user_name).execute().get();
                    runOnUiThread(new Runnable() {
                        public void run() {

                            // check username is empty or not
                            if (currentUsers.isEmpty()) {
                                // if username will be incorrect then this message will be shown to user
                                Toast.makeText(Login_form.this, R.string.Loginform_invalidUserName_ToastMessage, Toast.LENGTH_SHORT).show();
                                login_progressBar.dismiss();

                            } else {
                                login_userName_or_email(currentUsers,flag_user_name_email,password,login_progressBar);
                            }
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /**********************************************************************************************
        Name :- login_userName_or_email
        Input :- Pass MobileServiceList<LoginUser_Data> object,boolean object,password string,
                 progressDialog
        Description :- while doing login on screen it will accept username or email id both.
    ***********************************************************************************************/
    public void login_userName_or_email(MobileServiceList<LoginUser_Data> currentUsers,
                boolean flag_user_name_email, String password, ProgressDialog login_progressBar){

        /* looping to get current info */
        for (LoginUser_Data current_userName_email : currentUsers) {
            // check password matches or not
            if (current_userName_email.getUserPassword().equals(password)) {
                // Creating Intent to call another activity
                Intent intent = new Intent(Login_form.this, Home_Page.class);
                // create globals object
                Globals g = (Globals) getApplication();
                // set user name or email id into globals
                g.setLoginData(current_userName_email);
                // dismiss bar
                login_progressBar.dismiss();

                /* according to boolean value action will be perform */
                if(flag_user_name_email) {
                    // set user email id
                    sharedPrefernces(current_userName_email);
                    // start new activity
                    startActivity(intent);
                    // Toast message will be shown when login will be successful
                    Toast.makeText(Login_form.this, R.string.Loginform_loginSuccess_Toastmessage, Toast.LENGTH_SHORT).show();
                } else {
                    // set user name
                    sharedPrefernces(current_userName_email);
                    // start new activity
                    startActivity(intent);
                    // Toast message will be shown when login will be successful
                    Toast.makeText(Login_form.this, R.string.Loginform_loginSuccess_Toastmessage, Toast.LENGTH_SHORT).show();
                }
            } else {
                // Toast message will be shown if password doesn't matches
                Toast.makeText(Login_form.this, R.string.Loginform_incorrectPassword_ToastMessage, Toast.LENGTH_SHORT).show();
                login_progressBar.dismiss();
            }
        }
    }

    /********************************************************************************************
        Name :- sharedPrefernces
        Input :- Pass username and password
        Description :- Function use to keep loggedIn when user do first time login in app.
     ********************************************************************************************/
     public void sharedPrefernces(LoginUser_Data currentUser) {

         loginSharedPref = getSharedPreferences("LoginData", MODE_PRIVATE);
         loginSharedPrefEdit = loginSharedPref.edit();
         loginSharedPrefEdit.clear();
         loginSharedPrefEdit.putString("Username", currentUser.getUserName());
         loginSharedPrefEdit.putString("ID", currentUser.getID());
         loginSharedPrefEdit.putString("Password", currentUser.getUserPassword());
         loginSharedPrefEdit.putString("Name", currentUser.getName());
         loginSharedPrefEdit.putString("MobileNo", currentUser.getMobileNo());
         loginSharedPrefEdit.putString("gender", currentUser.getGender());
         loginSharedPrefEdit.putString("dob", currentUser.getDateOfBirth());
         loginSharedPrefEdit.putString("emailID", currentUser.getEmail_ID());
         loginSharedPrefEdit.commit();
     }

    /*********************************************************************************************
     Name :- cancel_process
     Description :- On clicking cancel button all fields will be cleared
     *********************************************************************************************/
    public  void cancel_process(View v) {

        EditText username = (EditText)findViewById(R.id.username_editText);
        EditText password = (EditText)findViewById(R.id.password_editText);

        // Reset the values
        username.setText("");
        password.setText("");
    }

    /*********************************************************************************************
     Name :- forgotPassword
     Description :- This function is called on click of Forgot_Password link. If the username
     provided is a valid one, it will provide the password to the user through SMS and email on the
     registered mobile number and email id.
     *********************************************************************************************/
      public  void  forgotPassword(View v) {

        EditText username = (EditText)findViewById(R.id.username_editText);
        final String username_text = username.getText().toString();

        // set AsyncTask
        new AsyncTask<Void, Void, Void>() {
            // getting username as string
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<LoginUser_Data> currentUsers =
                            loginUser_data.where().field("UserName").eq(username_text).execute().get();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // check username is empty or not
                            if (currentUsers.isEmpty()) {
                                // if username will be incorrect then toast message will be shown to user
                                Toast.makeText(Login_form.this, R.string.Loginform_forgotpswd_ToastMessage, Toast.LENGTH_SHORT).show();
                            } else {
                                for (LoginUser_Data currentUser : currentUsers) {

                                    //getting mobile_no from currentUser
                                    String mobile_no = currentUser.getMobileNo();
                                    //getting email_id from currentUser
                                    String email_id = currentUser.getEmail_ID();
                                    //getting userPassword from currentUser
                                    String user_password = currentUser.getUserPassword();
                                    // pass parameter eamil_id,user_password_user_name to sendEmail function
                                    sendEmail(email_id, user_password, username_text);
                                    // pass parameter mobile_no and user_password to Message_sent function
                                    Message_sent(mobile_no, user_password);
                                    // Toast message will be shown by user when clicks on Forgot_password
                                    Toast.makeText(Login_form.this,
                                     R.string.Loginform_forgotpswd_clicked_ToastMessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /*********************************************************************************************
     Name :- Message_sent
     Input :- Mobile number, User password
     Description :- This message is used to send an automated text message
     *********************************************************************************************/
      public void Message_sent(String mobile_no,String User_password)
      {
        final String cont = mobile_no;
        final String msg =User_password;

        //Getting intent and PendingIntent instance
        final Intent send_intent = new Intent(Login_form.this, MainActivity.class);
        PendingIntent send_pending = PendingIntent.getActivity(Login_form.this, 0, send_intent, 0);

        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(cont, null, R.string.Loginform_Messagesent_message + msg, send_pending, null);
      }

    /*********************************************************************************************
        Name :- sendEmail
        Input :- User_email_id, User_account_password, UserName
        Description :- This message is used to send an automated email
     *********************************************************************************************/
      public void sendEmail(String user_email_id,String user_account_password, String userName)
      {
        String from = getString(R.string.Comman_EmailId_from);
        String password =getString(R.string.Comman_Password_password);

        // creating List array as string
        List<String> to = new ArrayList<String>();

        // add email_id to list
        to.add(user_email_id);

        //set subject for email
        String subject = getString(R.string.Loginform_Email_subject);

        // set body for email
        String body = "<html><body align = \"center\">" +
                "<div style=\"background-color: orange; width: 75%; height: 10%; padding-top : 2%;padding-bottom: 2%; text-align: center; " +
                "color: white; font-family : Lucida Calligraphy; font-size: 200%\"> PressFit" +
                "</div> " +
                "<br><br><br>"+
                "<div style=\"text-align: center ; width: 75%; font-family: Times New Roman;font-size: 120%\">" +
                "<b>Your Credential Details:<br> <br> Username : " + userName + "<br> Password : " +  user_account_password + "</b>" +
                "</div></body></html>";
        new SendMailTask().execute(from, password, to, subject, body);

        // set message for email
        Toast.makeText(Login_form.this,R.string.Loginform_Email_ToastMessage,Toast.LENGTH_SHORT).show();
      }

    /*********************************************************************************************
     Name :- signUp
     Description :- While clicking on Sign Up button SignUp page will be open for new user.
     *********************************************************************************************/
    public void signUp(View v)
    {
        // Intent object use to call another activity
        Intent signUp_intent = new Intent(Login_form.this, SignUp_Page.class);
        startActivity(signUp_intent);
    }
    /*********************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_form, menu);
        MenuItem menuItem = menu.findItem(R.id.Login_form);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /* Handle action bar item clicks here. The action bar wil automatically handle clicks on
         the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml. */

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
