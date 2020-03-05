package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Admin on 3/3/2016.
 */
public class OrderTable implements Serializable
{
    private String ID;
    private String AccountID;
    private String AgentID;
    private String DateTime;
    private Float  Price;
    private Integer OrderNumber;
    private Integer Total_Qty;
    private String Transport;
    private String ShippingAddress;
    private String Narration;
    private Integer RefNo;
    private int __deleted;

    @JsonProperty("__deleted")
    public int get__deleted() {return __deleted;}

    @JsonProperty("__deleted")
    public void set__deleted(int __deleted) {this.__deleted = __deleted;}

    @JsonProperty("OrderNumber")
    public Integer getOrderNumber() {
        return OrderNumber;
    }

    @JsonProperty("OrderNumber")
    public void setOrderNumber(Integer orderNumber) {
        OrderNumber = orderNumber;
    }

    @JsonProperty("ID")
    public String getOrderID() {
        return ID;
    }

    @JsonProperty("ID")
    public void setOrderID(String orderID) {ID = orderID;}

    @JsonProperty("AccountID")
    public String getAccountID() {
        return AccountID;
    }

    @JsonProperty("AccountID")
    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    @JsonProperty("AgentID")
    public String getAgentID() {
        return AgentID;
    }

    @JsonProperty("AgentID")
    public void setAgentID(String agentID) {
        AgentID = agentID;
    }

    @JsonProperty("DateTime")
    public String getDateTime() {
        return DateTime;
    }

    @JsonProperty("DateTime")
    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    @JsonProperty("Price")
    public Float getPrice() {
        return Price;
    }

    @JsonProperty("Price")
    public void setPrice(Float price) {
        Price = price;
    }

    @JsonProperty("Total_Qty")
    public Integer getTotal_Qty() {
        return Total_Qty;
    }

    @JsonProperty("Total_Qty")
    public void setTotal_Qty(Integer total_Qty) {
        Total_Qty = total_Qty;
    }

    @JsonProperty("Transport")
    public String getTransport() {
        return Transport;
    }

    @JsonProperty("Transport")
    public void setTransport(String transport) {
        Transport = transport;
    }

    @JsonProperty("ShippingAddress")
    public String getShippingAddress() {
        return ShippingAddress;
    }

    @JsonProperty("ShippingAddress")
    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    @JsonProperty("Narration")
    public String getNarration() {
        return Narration;
    }

    @JsonProperty("Narration")
    public void setNarration(String narration) {
        Narration = narration;
    }

    @JsonProperty("Ref_No")
    public Integer getRefNo() {
        return RefNo;
    }

    @JsonProperty("Ref_No")
    public void setRefNo(Integer refNo) {
        RefNo = refNo;
    }

}
