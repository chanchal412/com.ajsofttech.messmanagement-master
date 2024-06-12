package com.project.messmanagement.models;

public class ModelRegisterUser {


    public ModelRegisterUser(String accountType, String userName, String emailAddress, String password, String contactNumber, String address) {
        this.accountType = accountType;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public  String accountType;
    public  String userName;
    public  String emailAddress;
    public  String password;
    public  String contactNumber;

    public String address;



    public ModelRegisterUser() {
    }
}
