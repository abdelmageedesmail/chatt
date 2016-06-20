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

    List<String>add;
    public Contact( String userName, String userPhone){
        this.userName=userName;
        this.userPhone=userPhone;
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
}
