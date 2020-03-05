package com.ppp_admin.ppp_order_app;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * Created by Admin on 8/28/2015.
 */

// creating Async task to handle the HTTP handler and rest Template MainActivity.xml

public class HttpRequestTask extends AsyncTask<String, Void, Account_Master[]>
{
    AsyncResponse delegate = null;

    @Override
    protected Account_Master[] doInBackground(String... params)
    {
        // set array for Account_Master_bkp
        Account_Master[] accounts = null;
        try {
            // Set the Accept header
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the Jackson message converter
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String current_date = Get_current_date();

            // web service url

            String url= "https://pressfit-accounts.azure-mobile.net/tables/Account_Master?$filter=BirthDate eq " +
                    "'" + current_date + "'" + " or AnniversaryDate eq" + "'" + current_date + "'";

            // Make the HTTP GET request, marshaling the response from JSON to an array of accounts
            ResponseEntity<Account_Master[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Account_Master[].class);

            accounts = responseEntity.getBody();

            }catch(Exception e)
            {
                Log.e("MainActivity", e.getMessage(), e);
            }
        return accounts;
    }

    /*************************************************************************************************************************
        Name :- onPostExecute
        Input :- Doing process in background pass data in accounts
        Description :- protected  void onPostExecute(Result), invoked on the UI thread after the background computation finishes.
                      The result of the background computation is passed to this step as a parameter.(Account_Master_bkp[] accounts)
    ***************************************************************************************************************************/
    protected  void onPostExecute(Account_Master[] accounts)
    {
        // set ArraryList<Account_Master_bkp> getting today_birthday_accounts Birthday data
        ArrayList<Account_Master> today_birthday_accounts = new ArrayList<>();

        // check condition if accounts is null or not
        if(accounts!= null) {

            // using loop get accounts in arraylist
            for (int i = 0; i < accounts.length; i++) {

                // add accounts into araylist
                today_birthday_accounts.add(accounts[i]);
            }

            // call delegate method to pass today_birthday _accounts
            delegate.processfinish(today_birthday_accounts);
        }
    }

    /***********************************************************************
        Name :- Get_current_date
        Output:- returns today's date in string format
        Description :- converting today;s date into 'yyyy-mm-dd' format
     ***********************************************************************/
       public String Get_current_date()
       {
         // set date format
        SimpleDateFormat convert_default_format = new SimpleDateFormat("yyyy-MM-dd");

        // convert date format into string
        String today_date = convert_default_format.format(new Date());

        // return current date
        return today_date;
      }
    /********************************************************************************************/
}