package com.project.messmanagement.models;

public class ModelMyCustomers {


    public String userNm;

    public ModelMyCustomers(String userNm, String address, String emailAddress, String months, String times, String paidAmount, String contactNumber) {
        this.userNm = userNm;
        this.address = address;
        this.emailAddress = emailAddress;
        this.months = months;
        this.times = times;
        this.paidAmount = paidAmount;
        this.contactNumber = contactNumber;
    }

    public String address;
    public String emailAddress;
    public String months;
    public String times;
    public String paidAmount;
    public String contactNumber;




    public ModelMyCustomers() {
    }
}
