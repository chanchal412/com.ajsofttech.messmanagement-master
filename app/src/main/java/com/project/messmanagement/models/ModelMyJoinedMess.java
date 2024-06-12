package com.project.messmanagement.models;

public class ModelMyJoinedMess {
    public ModelMyJoinedMess() {
    }

    public String months;
    public String times;
    public String paidAmount;
    public String messAddress;
    public String messNm;
    public String startAt;
    public String closeAt;
    public String contactNumber;
    public String managerEmail;

    public ModelMyJoinedMess(String months, String times, String paidAmount, String messAddress, String messNm, String startAt, String closeAt, String contactNumber, String managerEmail) {
        this.months = months;
        this.times = times;
        this.paidAmount = paidAmount;
        this.messAddress = messAddress;
        this.messNm = messNm;
        this.startAt = startAt;
        this.closeAt = closeAt;
        this.contactNumber = contactNumber;
        this.managerEmail = managerEmail;
    }
}
