package com.project.messmanagement.models;

public class ModelCreatMess {




    public ModelCreatMess() {
    }

    public ModelCreatMess(String messName, String items, String oneTimePrice, String messAddress, String startAt, String closeAt, String contactNumber) {
        this.messName = messName;
        this.items = items;
        this.oneTimePrice = oneTimePrice;
        this.messAddress = messAddress;
        this.startAt = startAt;
        this.closeAt = closeAt;
        this.contactNumber = contactNumber;
    }

    public String messName;
    public String items;
    public String oneTimePrice;
    public String messAddress;
    public String startAt;
    public String closeAt;
    public String contactNumber;






}
