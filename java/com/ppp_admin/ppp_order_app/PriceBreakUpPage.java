package com.ppp_admin.ppp_order_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PriceBreakUpPage extends Activity
{
    Account_Master account_master;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_break_up_page);

        // call function to calculate price
         CalculateTotalPrice();
    }

    /********************************************************************************************
        Name :- CalculateTotalPrice
        Description :- Display Price details
     *******************************************************************************************/
    public void CalculateTotalPrice()
    {
        // create orderTable object to get data from Intent
        OrderTable OrderTableData = (OrderTable) getIntent().getSerializableExtra("OrderTableData");

        // get account master data from Intent
        account_master = (Account_Master) getIntent().getSerializableExtra("AccountMasterData");

        // create float object
        Float DiscountRate = Float.valueOf(account_master.getDiscount_Rate_Percent());

        // create TextView object to set price
        TextView showTotalPrice_Items = (TextView) findViewById(R.id.TotalPrice_textView);
        showTotalPrice_Items.setText("Rs. " + String.valueOf(OrderTableData.getPrice()));

        // create Textview object to set discount percent
        TextView showDiscountRate  = (TextView) findViewById(R.id.discountPercentShow_textView);
        showDiscountRate.setText(String.valueOf(DiscountRate) + "%");

        // call function to calculate total price based on discount rate
        calculationPrice(OrderTableData, DiscountRate);
    }

    /*******************************************************************************************
        Name :- calculationPrice
        Input :- Pass OrderTable object and float object to calculate price
        Description :- Calculate total price based on discount rate
     *******************************************************************************************/
      public void calculationPrice(OrderTable OrdertableData , Float DiscountRate)
     {
        /* create float objects and calculate price and store into object */
        Float discountRate = DiscountRate;
        Float TotalPrice = OrdertableData.getPrice();
        Float DiscountedRate = (TotalPrice * discountRate / 100);
        Float finalTotalPrice = (TotalPrice - DiscountedRate);

        /* create textview object to set  discount rate*/
        TextView showDiscountRate = (TextView) findViewById(R.id.showDiscountRate_textView);
        showDiscountRate.setText("- Rs. " + String.valueOf(DiscountedRate));

        /* create textview object to set Total Price */
        TextView showFinalPrice = (TextView) findViewById(R.id.TotalPriceShow_textView);
        showFinalPrice.setText("Rs. " + String.valueOf(finalTotalPrice));
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_price_break_up_page, menu);
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
