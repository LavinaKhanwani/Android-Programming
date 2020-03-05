package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 12/31/2016.
 */
public class List_Items {

    private String ID;
    private String List_Name;
    private String List_Increment;
    private String Login_User_Id;
    private String Product_Id;
    private String __deleted;

    List_Items(){}

    List_Items(String list_Name, String login_user_id, String Product_id){
        List_Name = list_Name;
        Login_User_Id = login_user_id;
        Product_Id = Product_id;
        __deleted = "0";
    }
    @JsonProperty("List_Increment")
    public String getList_Increment() {
        return List_Increment;
    }
    @JsonProperty("List_Increment")
    public void setList_Increment(String list_Increment) {
        List_Increment = list_Increment;
    }

    @JsonProperty("ID")
    public String getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }
    @JsonProperty("Login_User_Id")
    public String getLogin_User_id() {
        return Login_User_Id;
    }
    @JsonProperty("Login_User_Id")
    public void setLogin_User_id(String login_User_id) {
        Login_User_Id = login_User_id;
    }
    @JsonProperty("List_Name")
    public String getList_Name() {
        return List_Name;
    }
    @JsonProperty("List_Name")
    public void setList_Name(String list_Name) {
        List_Name = list_Name;
    }
    @JsonProperty("Product_Id")
    public String getProduct_Id() {
        return Product_Id;
    }
    @JsonProperty("Product_Id")
    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }
    @JsonProperty("__deleted")
    public String get__deleted() {
        return __deleted;
    }
    @JsonProperty("__deleted")
    public void set__deleted(String __deleted) {
        this.__deleted = __deleted;
    }
}
