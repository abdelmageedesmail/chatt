package com.example.abdelmageed.chatting.model;

import com.example.abdelmageed.chatting.activities.ChatPage;

import java.util.List;

/**
 * Created by abdelmageed on 14/06/16.
 */
public class Contact {
    public int Image;
    public String userName;
    public String userPhone;
    private static String message= ChatPage.messageCont;
    private String data;
    private String seen;
    private int imagechat;
    public String messagecont;
    public String date;
    public String state;
    public int Imagechat;
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    List<String>add;
    public Contact(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImagechat() {
        return imagechat;
    }

    public void setImagechat(int imagechat) {
        this.imagechat = imagechat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
