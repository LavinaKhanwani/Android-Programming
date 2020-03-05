package com.ppp_admin.ppp_order_app;

import java.io.Serializable;

/**
 * Created by Admin on 10/1/2015.
 */
public class LoginUser_Data implements Serializable
{
    @com.google.gson.annotations.SerializedName("ID")
    private String ID;
    @com.google.gson.annotations.SerializedName("Name")
    private String Name;
    @com.google.gson.annotations.SerializedName("UserName")
    private String UserName;
    @com.google.gson.annotations.SerializedName("UserPassword")
    private String UserPassword;
    @com.google.gson.annotations.SerializedName("EmailId")
    private String Email_ID;
    @com.google.gson.annotations.SerializedName("MobileNo")
    private String MobileNo;
    @com.google.gson.annotations.SerializedName("DateOfBirth")
    private String DateOfBirth;
    @com.google.gson.annotations.SerializedName("Gender")
    private String Gender;
    @com.google.gson.annotations.SerializedName("Deactivated")
    private String Deactivated;

    public String getDeactivated() {
        return Deactivated;
    }

    public void setDeactivated(String deactivated) {
        Deactivated = deactivated;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public String setName(String name) {
        Name = name;
        return name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getEmail_ID() {
        return Email_ID;
    }

    public void setEmail_ID(String email_ID) {
        Email_ID = email_ID;
    }

    public String getMobileNo() {return MobileNo;}

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getDateOfBirth() {return DateOfBirth;}

    public void setDateOfBirth(String dateOfBirth) {DateOfBirth = dateOfBirth;}

    public String getGender() {return Gender;}

    public void setGender(String gender) {Gender = gender;}
}
