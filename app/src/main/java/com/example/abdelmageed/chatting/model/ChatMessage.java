package com.example.abdelmageed.chatting.model;

import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;
import com.example.abdelmageed.chatting.adapter.ChatArrayAdapter;

/**
 * Created by abdelmageed on 19/06/16.
 */
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
