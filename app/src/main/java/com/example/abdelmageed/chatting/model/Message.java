package com.example.abdelmageed.chatting.model;

/**
 * Created by abdelmageed on 20/06/16.
 */
public class Message {
    private static String message;


    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Message.message = message;
    }
}
