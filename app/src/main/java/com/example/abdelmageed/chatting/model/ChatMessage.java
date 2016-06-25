package com.example.abdelmageed.chatting.model;

import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;


public class ChatMessage {
    public static String message;
    public boolean left;
    public static final String mypreference = "mypref";
    SharedPreferences sh;
    StringRequest stringRequest;

    public ChatMessage(boolean left, String message) {
        super();
        this.message=message;
        this.left=left;
    }
}
