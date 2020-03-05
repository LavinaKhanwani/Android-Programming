package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 3/3/2016.
 */
public class Order_Item
{
    private String ID;
    private String ProductID;
    private String Quantity;
    private Integer OrderID;
    private Float Price;

    @JsonProperty("ID")
    public String getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }

    @JsonProperty("OrderID")
    public Integer getOrderID() {
        return OrderID;
    }

    @JsonProperty("OrderID")
    public void setOrderID(Integer orderID) {
        OrderID = orderID;
    }

    @JsonProperty("Price")
    public float getPrice() {
        return Price;
    }

    @JsonProperty("Price")
    public void setPrice(float price) {
        Price = price;
    }

    @JsonProperty("Quantity")
    public String getQuantity() {
        return Quantity;
    }

    @JsonProperty("Quantity")
    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    @JsonProperty("ProductID")
    public String getProductID() {
        return ProductID;
    }

    @JsonProperty("ProductID")
    public void setProductID(String productID)
    {ProductID = productID;}
}
