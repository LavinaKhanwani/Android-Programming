package com.ppp_admin.ppp_order_app;

/**
 * Created by Admin on 9/26/2015.
 */
public class Single_row {

       /**********************************************************************************
               Section for declaration of the variables
        **********************************************************************************/
        private String final_wish;
        private String number;

    /*********************************************************************************************
        Name :-  Single_row
        Output :- pass string as final_wish and number
        Description :- pass String value to assigned value and creating getter and
                       setter for parameters
    ********************************************************************************************/
    Single_row(String final_wish, String number) {

        this.final_wish = final_wish;
        this.number = number;
    }

    public String getFinal_wish()
    {
        return final_wish;
    }

    public String getNumber()
    {
        return number;
    }

    public void setFinal_wish(String final_wish)
    {
        this.final_wish = final_wish;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }
}
