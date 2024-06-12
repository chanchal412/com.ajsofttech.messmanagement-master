package com.project.messmanagement.models;

public class ModelRateReview {
    public ModelRateReview() {

    }


    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public String getRatedByUserNm() {
        return ratedByUserNm;
    }

    public void setRatedByUserNm(String ratedByUserNm) {
        this.ratedByUserNm = ratedByUserNm;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public  String reviewMessage;
    public  String ratedByUserNm;
    public String rateValue;
    public String userNm;

    public ModelRateReview(String reviewMessage, String ratedByUserNm, String rateValue, String userNm) {
        this.reviewMessage = reviewMessage;
        this.ratedByUserNm = ratedByUserNm;
        this.rateValue = rateValue;
        this.userNm = userNm;
    }
}
