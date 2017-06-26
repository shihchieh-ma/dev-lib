package com.example.admin.mydemo.bean;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class SmsBean {
    public SmsBean() {
    }

    private String phoneNumber;
    private String smsbody;
    private String date;
    private String type;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsbody() {
        return smsbody;
    }

    public void setSmsbody(String smsbody) {
        this.smsbody = smsbody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmsBean{" +

                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsbody='" + smsbody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
