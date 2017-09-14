package com.example.lenovo.smsgo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by LENOVO on 8/09/2017.
 */

public class ModelSms {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("e_mail")
    @Expose
    private String Email;
    @SerializedName("message_sent")
    @Expose
    private Boolean Message_sent;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getMessage_sent() {
        return Message_sent;
    }

    public void setMessage_sent(Boolean message_sent) {
        Message_sent = message_sent;
    }

    @Override
    public String toString() {
        return "ModelSms{" +
                "number='" + number + '\'' +
                ", message='" + message + '\'' +
                ", fullName='" + fullName + '\'' +
                ", Email='" + Email + '\'' +
                ", Message_sent='" + Message_sent + '\'' +
                '}';
    }



}
