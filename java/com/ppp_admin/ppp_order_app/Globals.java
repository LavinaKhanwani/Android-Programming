package com.ppp_admin.ppp_order_app;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by Admin on 12/30/2015.
 */
public class Globals extends Application
{
    /*******************************************************************************************
        Name :- HashMap<String, Order_Detail_Object>
        Key :- ProductId
        Value :-  OrderDetailObject's object "orderDetailObject"
        Description :- context stores fully orders details into hashmap object getting from
                       different pages that display on gridviews
                       like Product_Details_Page_Type1.class etc.
     ********************************************************************************************/
     private HashMap<String, Order_Detail_Object> final_order = new HashMap<>();

    /*************************************************************************************
        Name :- HashMap <String , Product>
        Key :- ItemName
        Value :- Product's Object "product"
        Description :- Context create hashmap of Products.
                        It stores all product values putting from Product_Page.class.
     *************************************************************************************/
      private HashMap<String, Product> productsHashMap  = new HashMap<>();

    /******************************************************************************************
        Name :- Boolean flag
        Description :- create a boolean flag to check whether product data loaded completely
                       or not.
     ******************************************************************************************/
      private boolean product_load_complete_flag = false;

    /******************************************************************************************
        Name :- LoginUser_Data
        value :- Fully LoginUser_Data
        Description :- context stores all login user data into LoginUser_Data object getting
                       from Login_from.class.
     ******************************************************************************************/
     private LoginUser_Data loginData;

    /*******************************************************************************************
        creating Integer object to store orderNumber getting from OrderTableObject.class
     ******************************************************************************************/
     private Integer Ref_No;

    /* all getter and setters are created */

     public Integer getRef_No() {return Ref_No;}

     public void setRef_No(Integer orderNumber) {Ref_No = orderNumber;}

     public LoginUser_Data getLoginData() {return loginData;}

     public void setLoginData(LoginUser_Data data) {loginData = data;}

     public HashMap<String, Order_Detail_Object> getFinal_order() {return final_order;}

     public void setFinal_order(HashMap<String, Order_Detail_Object> final_order) {this.final_order = final_order;}

     public HashMap<String, Product> getProductsHashMap() {return productsHashMap;}

     public void setProductsHashMap(HashMap<String, Product> productsHashMap){
      this.productsHashMap = productsHashMap;}

     public boolean isProduct_load_complete_flag() {return product_load_complete_flag;}

     public void setProduct_load_complete_flag(boolean product_load_complete_flag) {
          this.product_load_complete_flag = product_load_complete_flag; }
}
