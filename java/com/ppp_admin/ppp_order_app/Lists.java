package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 12/31/2016.
 */
public class Lists
{
    private String ID;
    private String List_Name;
    private String List_Increment;
    private String Login_User_Id;
    private String No_Of_Items;
    private String No_Of_Times_Ordered;
    private String Date_Created;
    private String __deleted;
    Lists(){}

    Lists(String list_Name,String list_incr,String AgentId, String No_Of_Items, String Date_Created){
        this.List_Name = list_Name;
        List_Increment = list_incr;
        Login_User_Id = AgentId;
        this.No_Of_Items = No_Of_Items;
        this.Date_Created = Date_Created;
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

    @JsonProperty("List_Name")
    public String getList_Name() {
        return List_Name;
    }

    @JsonProperty("List_Name")
    public void setList_Name(String list_Name) {
        List_Name = list_Name;
    }

    @JsonProperty("Login_User_Id")
    public String getLogin_User_Id() {
        return Login_User_Id;
    }

    @JsonProperty("Login_User_Id")
    public void setLogin_User_Id(String login_User_Id) {
        Login_User_Id = login_User_Id;
    }

    @JsonProperty("No_Of_Items")
    public String getNo_Of_Items() {
        return No_Of_Items;
    }

    @JsonProperty("No_Of_Items")
    public void setNo_Of_Items(String no_Of_Items) {
        No_Of_Items = no_Of_Items;
    }

    @JsonProperty("No_Of_Times_Ordered")
    public String getNo_Of_Times_Ordered() {
        return No_Of_Times_Ordered;
    }

    @JsonProperty("No_Of_Times_Ordered")
    public void setNo_Of_Times_Ordered(String no_Of_Times_Ordered) {
        No_Of_Times_Ordered = no_Of_Times_Ordered;
    }

    @JsonProperty("Date_Created")
    public String getDate_Created() {
        return Date_Created;
    }

    @JsonProperty("Date_Created")
    public void setDate_Created(String date_Created) {
        Date_Created = date_Created;
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