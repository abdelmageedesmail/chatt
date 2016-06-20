package com.example.abdelmageed.chatting.parser;

import com.example.abdelmageed.chatting.model.Contact;
import com.example.abdelmageed.chatting.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdelmageed on 20/06/16.
 * Chat parser to get data from get messages url
 */
public class ChatParser {

    public static List<Message> MessageParser(String content)  {

        try {
            JSONArray array=new JSONArray(content);
            List<Message> tripList=new ArrayList<>();
            for (int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                Message contact=new Message();
                contact.setMessage(object.getString("messageDescription"));
                tripList.add(contact);
            }
            return tripList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

