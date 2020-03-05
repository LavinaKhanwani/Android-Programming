package com.ppp_admin.ppp_order_app;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by Admin on 8/6/2015.
 */

public class Account_Master_bkp implements  Parcelable
{

    private String ID;
    private String BranchId;
    private String AccountId;
    private String AccountName;
    private String PrintName;
    private String ContactPerson;
    private String Mobile;
    private String EMail;
    private String BirthDate;

    public Account_Master_bkp() {}

    protected Account_Master_bkp(Parcel in) {
        ID = in.readString();
        BranchId = in.readString();
        AccountId = in.readString();
        AccountName = in.readString();
        PrintName = in.readString();
        ContactPerson = in.readString();
        Mobile = in.readString();
        EMail = in.readString();
        BirthDate = in.readString();
    }

    public static final Creator<Account_Master_bkp> CREATOR = new Creator<Account_Master_bkp>() {
        @Override
        public Account_Master_bkp createFromParcel(Parcel in) {
            return new Account_Master_bkp(in);
        }

        @Override
        public Account_Master_bkp[] newArray(int size) {
            return new Account_Master_bkp[size];
        }
    };

    @JsonProperty("ID")
    public String getID() {
        return ID;
    }

    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }

    @JsonProperty("BranchID")
    public String getBranchId() {
        return BranchId;
    }

    @JsonProperty("BranchID")
    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    @JsonProperty("AccountID")
    public String getAccountId() {
        return AccountId;
    }

    @JsonProperty("AccountID")
    public void setAccountId(String AccountsId) {
        AccountId = AccountsId;
    }

    @JsonProperty("AccountName")
    public String getAccountName() {
        return AccountName;
    }

    @JsonProperty("AccountName")
    public void setAccountName(String AccountsName) {
        AccountName = AccountsName;
    }

    @JsonProperty("PrintName")
    public String getPrintName() {
        return PrintName;
    }

    @JsonProperty("PrintName")
    public void setPrintName(String PrintsName) {
        PrintName = PrintsName;
    }

    @JsonProperty("ContactPerson")
    public String getContactPerson() {
        return ContactPerson;
    }

    @JsonProperty("ContactPerson")
    public void setContactPerson(String ContactsPerson) {
        ContactPerson = ContactsPerson;
    }

    @JsonProperty("Mobile")
    public String getMobile() {
        return Mobile;
    }

    @JsonProperty("Mobile")
    public void setMobile(String Mobiles) {
        Mobile = Mobiles;
    }

    @JsonProperty("EMail")
    public String getEMail() {
        return EMail;
    }

    @JsonProperty("EMail")
    public void setEMail(String EMails) {
        this.EMail = EMails;
    }

    @JsonProperty("BirthDate")
    public String getBirthDate() {
        return BirthDate;
    }

    @JsonProperty("BirthDate")
    public void setBirthDate(String BirthDates) {
        BirthDate = BirthDates;
    }

    public String toString(String str) {
        str = str.concat("Account Name: " + this.getAccountName() + "\n" +
                "Contact Person: " + this.getContactPerson() + "\n" + "EMail:" + this.getEMail() + "\n" + "BirthDate: " + this.getBirthDate());
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(BranchId);
        dest.writeString(AccountId);
        dest.writeString(AccountName);
        dest.writeString(PrintName);
        dest.writeString(ContactPerson);
        dest.writeString(Mobile);
        dest.writeString(EMail);
        dest.writeString(BirthDate);
    }


    private  void readFromParcel(Parcel in)
    {
        ID = in.readString();
        BranchId = in.readString();
        AccountName = in.readString();
        PrintName = in.readString();
        ContactPerson = in.readString();
        Mobile= in.readString();
        EMail = in.readString();
        BirthDate = in.readString();

    }
}


