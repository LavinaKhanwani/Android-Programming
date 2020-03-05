package com.ppp_admin.ppp_order_app;

/**
 * Created by Admin on 12/26/2015.
 */
public class Order_Detail_Object
{
    /*****************************************************
        Section declare variables
     ****************************************************/
      private String Item;
      private String Quantity;
      private String Unit;
      private Float Price;

      // crate default constructor
      Order_Detail_Object() {}

      // create constructor
      Order_Detail_Object(String item)
      {
        this.Item = item;
        this.Quantity = "1";
        this.Unit = null;
        this.Price = 0.0F;
      }

      // create getter and setter of order_Details_Object
      public Float getPrice() {return Price;}
      public void setPrice(Float price) {Price = price;}
      public String getItem() {return Item;}
      public void setItem(String item) {Item = item;}
      public String getQuantity() {return Quantity;}
      public void setQuantity(String quantity) {Quantity = quantity;}
      public String getUnit() {return Unit;}
      public void setUnit(String unit) {Unit = unit;}
}
