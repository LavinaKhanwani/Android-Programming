package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 12/30/2015.
 */
public class Product
{

    private String ID;
    private String Product_code;
    private String Name;
    private String Rate;
    private String Rate_Quantity;
    private String STD_PKG;
    private String STD_PKG_Unit;
    private String Master_PKG;
    private String Master_PKG_Unit;
    private String Basic_Unit;



    @JsonProperty("Product_code")
    public String getProduct_code() {
        return Product_code;
    }
    @JsonProperty("Product_code")
    public void setProduct_code(String product_code) {
        Product_code = product_code;
    }
    @JsonProperty("Name")
    public String getName() {
        return Name;
    }
    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }
    @JsonProperty("Rate")
    public String getRate() {
        return Rate;
    }
    @JsonProperty("Rate")
    public void setRate(String rate) {
        Rate = rate;
    }
    @JsonProperty("Rate_Quantity")
    public String getRate_Quantity() {
        return Rate_Quantity;
    }
    @JsonProperty("Rate_Quantity")
    public void setRate_Quantity(String rate_Quantity) {
        Rate_Quantity = rate_Quantity;
    }
    @JsonProperty("STD_PKG")
    public String getSTD_PKG() {
        return STD_PKG;
    }
    @JsonProperty("STD_PKG")
    public void setSTD_PKG(String STD_PKG) {
        this.STD_PKG = STD_PKG;
    }
    @JsonProperty("STD_PKG_Unit")
    public String getSTD_PKG_Unit() {
        return STD_PKG_Unit;
    }
    @JsonProperty("STD_PKG_Unit")
    public void setSTD_PKG_Unit(String STD_PKG_Unit) {
        this.STD_PKG_Unit = STD_PKG_Unit;
    }
    @JsonProperty("Master_PKG")
    public String getMaster_PKG() {
        return Master_PKG;
    }
    @JsonProperty("Master_PKG")
    public void setMaster_PKG(String master_PKG) {
        Master_PKG = master_PKG;
    }
    @JsonProperty("Master_PKG_Unit")
    public String getMaster_PKG_Unit() {
        return Master_PKG_Unit;
    }
    @JsonProperty("Master_PKG_Unit")
    public void setMaster_PKG_Unit(String master_PKG_Unit) {
        Master_PKG_Unit = master_PKG_Unit;
    }
    @JsonProperty("ID")
    public String getID() {return ID;}
    @JsonProperty("ID")
    public void setID(String ID) {this.ID = ID;}
    @JsonProperty("Basic_Unit")
    public String getBasic_Unit() {return Basic_Unit;}
    @JsonProperty("Basic_Unit")
    public void setBasic_Unit(String basic_Unit) {Basic_Unit = basic_Unit;}

}
