package com.ppp_admin.ppp_order_app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Admin on 10/13/2015.
 */
public class Account_Master implements Serializable
{
    /*****************************************************
     Section declare variables
     ****************************************************/
    private String ID;
    private String AccountName;
    private String NameOfOwner;
    private String Address1;
    private String Address2;
    private String Address3;
    private String City;
    private String State;
    private String PinCode;
    private String FirmAnniversaryDate;
    private String ContactPerson;
    private String ContactPersonMobileNo;
    private String OwnerMobileNo;
    private String LandlineNo;
    private String BirthDate;
    private String AnniversaryDate;
    private String EmailID;
    private String FaxNo;
    private String Website;
    private String PanNo;

    private String GST_No;

    // private String VatTinNo;
   // private String CSTNo;

    private String AgentID;
    private String Discount_Rate_Percent;
    private String Remarks;
    private  String Transport;
    private String __deleted;
    private String AccountId;
    private String LastUpdatedDate;

    // create getter and setter of Account_Master
    @JsonProperty("LastUpdatedDate")
    public String getLastUpdatedDate() {return LastUpdatedDate;}
    @JsonProperty("LastUpdatedDate")
    public void setLastUpdatedDate(String lastUpdatedDate) {LastUpdatedDate = lastUpdatedDate;}
    @JsonProperty("AccountId")
    public String getAccountId() {return AccountId;}
    @JsonProperty("AccountId")
    public void setAccountId(String accountId) {AccountId = accountId;}
    @JsonProperty("ID")
    public String getID() {return ID;}
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }
    @JsonProperty("AccountName")
    public String getAccountName() {
        return AccountName;
    }
    @JsonProperty("AccountName")
    public void setAccountName(String accountName) {
        AccountName = accountName;
    }
    @JsonProperty("NameOfOwner")
    public String getNameOfOwner() {
        return NameOfOwner;
    }
    @JsonProperty("NameOfOwner")
    public void setNameOfOwner(String nameOfOwner) {
        NameOfOwner = nameOfOwner;
    }
    @JsonProperty("Address1")
    public String getAddress1() {
        return Address1;
    }
    @JsonProperty("Address1")
    public void setAddress1(String address1) {
        Address1 = address1;
    }
    @JsonProperty("Address2")
    public String getAddress2() {
        return Address2;
    }
    @JsonProperty("Address2")
    public void setAddress2(String address2) {
        Address2 = address2;
    }
    @JsonProperty("Address3")
    public String getAddress3() {
        return Address3;
    }
    @JsonProperty("Address3")
    public void setAddress3(String address3) {
        Address3 = address3;
    }
    @JsonProperty("City")
    public String getCity() {
        return City;
    }
    @JsonProperty("City")
    public void setCity(String city) {
        City = city;
    }
    @JsonProperty("PinCode")
    public String getPinCode() {
        return PinCode;
    }
    @JsonProperty("PinCode")
    public void setPinCode(String Pincode) {PinCode = Pincode;}
    @JsonProperty("FirmAnniversaryDate")
    public String getFirmAnniversaryDate() {
        return FirmAnniversaryDate;
    }
    @JsonProperty("FirmAnniversaryDate")
    public void setFirmAnniversaryDate(String firmAnniversaryDate) {
        FirmAnniversaryDate = firmAnniversaryDate;}
    @JsonProperty("ContactPerson")
    public String getContactPerson() {
        return ContactPerson;
    }
    @JsonProperty("ContactPerson")
    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }
    @JsonProperty("ContactPersonMobileNo")
    public String getContactPersonMobileNo() {
        return ContactPersonMobileNo;
    }
    @JsonProperty("ContactPersonMobileNo")
    public void setContactPersonMobileNo(String customerMobileNo) {ContactPersonMobileNo = customerMobileNo;}
    @JsonProperty("OwnerMobileNo")
    public String getOwnerMobileNo() {
        return OwnerMobileNo;
    }
    @JsonProperty("OwnerMobileNo")
    public void setOwnerMobileNo(String ownerMobileNo) {
        OwnerMobileNo = ownerMobileNo;
    }
    @JsonProperty("LandlineNo")
    public String getLandlineNo() {
        return LandlineNo;
    }
    @JsonProperty("LandlineNo")
    public void setLandlineNo(String landlineNo) {
        LandlineNo = landlineNo;
    }
    @JsonProperty("BirthDate")
    public String getBirthDate() {
        return BirthDate;
    }
    @JsonProperty("BirthDate")
    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }
    @JsonProperty("AnniversaryDate")
    public String getAnniversaryDate() {
        return AnniversaryDate;
    }
    @JsonProperty("AnniversaryDate")
    public void setAnniversaryDate(String anniversaryDate) {
        AnniversaryDate = anniversaryDate;
    }
    @JsonProperty("EmailID")
    public String getEmailID() {
        return EmailID;
    }
    @JsonProperty("EmailID")
    public void setEmailID(String email) {
        EmailID = email;
    }
    @JsonProperty("FaxNo")
    public String getFaxNo() {
        return FaxNo;
    }
    @JsonProperty("FaxNo")
    public void setFaxNo(String faxNo) {
        FaxNo = faxNo;
    }
    @JsonProperty("Website")
    public String getWebsite() {
        return Website;
    }
    @JsonProperty("Website")
    public void setWebsite(String website) {
        Website = website;
    }
    @JsonProperty("PanNo")
    public String getPanNo() {
        return PanNo;
    }
    @JsonProperty("PanNo")
    public void setPanNo(String panNo) {
        PanNo = panNo;
    }
    @JsonProperty("GST_No")
    public String getGST() { return GST_No; }
    @JsonProperty("GST_No")
    public void setGST(String GST) { this.GST_No = GST; }
    @JsonProperty("AgentID")
    public String getAgentID() {
        return AgentID;
    }
    @JsonProperty("AgentID")
    public void setAgentID(String agentID) {
        AgentID = agentID;
    }
    @JsonProperty("Remarks")
    public String getRemarks() {
        return Remarks;
    }
    @JsonProperty("Remarks")
    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
    @JsonProperty("Discount_Rate_Percent")
    public String getDiscount_Rate_Percent() {
        return Discount_Rate_Percent;
    }
    @JsonProperty("Discount_Rate_Percent")
    public void setDiscount_Rate_Percent(String discount_Rate_Percent) {Discount_Rate_Percent = discount_Rate_Percent;}
    @JsonProperty("__deleted")
    public String get__deleted() {
        return __deleted;
    }
    @JsonProperty("__deleted")
    public void set__deleted(String __deleted) {
        this.__deleted = __deleted;
    }
    @JsonProperty("State")
    public String getState() {
        return State;
    }
    @JsonProperty("State")
    public void setState(String state) {
        State = state;
    }
    @JsonProperty("Transport")
    public String getTransport() {
        return Transport;
    }
    @JsonProperty("Transport")
    public void setTransport(String transport) {
        Transport = transport;
    }
}
