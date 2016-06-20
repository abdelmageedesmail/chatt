package com.example.abdelmageed.chatting.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.adapter.ChatAdapter;
import com.example.abdelmageed.chatting.model.ChatMessage;
import com.example.abdelmageed.chatting.model.Message;
import com.example.abdelmageed.chatting.parser.ChatParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatPage extends AppCompatActivity {

    Toolbar toolbar;
    ListView list;
    List<Message> listMessage;
    EditText txtcontentMessage;
    ImageButton imgbtnSend;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    SharedPreferences sh;
    public static String friendId;
    public static String UserId;
    public static String messageCont;
    TextView txtUsername;
    public static final String mypreference = "mypref";
    String userId;
    public static String message;
    public static ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        toolbar = (Toolbar) findViewById(R.id.toolbarchat);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        txtUsername = (TextView) findViewById(R.id.txtfriendname);
        list = (ListView) findViewById(R.id.listmessage);
        txtcontentMessage = (EditText) findViewById(R.id.txtmessage);
        imgbtnSend = (ImageButton) findViewById(R.id.send);

        sh = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        friendId=sh.getString("friendId", null);
        UserId=sh.getString("userId", null);

        imgbtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        getMessages();
    }


    //send message
    public void sendData(){
        stringRequest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/insertMessage", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ChatPage.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatPage.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("messageDescription",txtcontentMessage.getText().toString());
                param.put("messageFrom",UserId);
                param.put("messageTo",friendId);
                return param;
            }
        };
        requestQueue=Volley.newRequestQueue(ChatPage.this);
        requestQueue.add(stringRequest);
    }


    //get message by friend id
    public void getMessages(){
        stringRequest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getMessages", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listMessage= ChatParser.MessageParser(response);
                uploadMessage();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("userID",friendId);
                return param;
            }
        };
        requestQueue=Volley.newRequestQueue(ChatPage.this);
        requestQueue.add(stringRequest);
    }
    public void uploadMessage(){
        ChatAdapter adapter=new ChatAdapter(ChatPage.this,R.layout.chatmessageitem,listMessage);
        list.setAdapter(adapter);
    }
}