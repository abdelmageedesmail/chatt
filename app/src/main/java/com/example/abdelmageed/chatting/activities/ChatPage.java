package com.example.abdelmageed.chatting.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.abdelmageed.chatting.Utils.Utils;
import com.example.abdelmageed.chatting.adapter.MessagesAdapter;
import com.example.abdelmageed.chatting.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatPage extends AppCompatActivity {

    Toolbar toolbar;
    EditText txtcontentMessage;
    Button imgbtnSend;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    SharedPreferences sh;
    public static String friendId;
    public static String messageCont;
    TextView txtUsername;
    String userId;
    public static String message;
    MessagesAdapter adapter;
    RecyclerView messages_recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_page);
        Utils utils = new Utils(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        txtUsername = (TextView) findViewById(R.id.friend_name);
        txtUsername.setText(getIntent().getExtras().getString("friendName"));
        txtcontentMessage = (EditText) findViewById(R.id.txtmessage);
        imgbtnSend = (Button) findViewById(R.id.send);

        utils.AwSomeFont(imgbtnSend);
        adapter = new MessagesAdapter(this);
        messages_recycler = (RecyclerView) findViewById(R.id.chat_page);
        messages_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messages_recycler.setAdapter(adapter);


        sh = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
        userId = sh.getString("userId", "");
        friendId = getIntent().getExtras().getString("friendId");

        imgbtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();

            }
        });
        getMessages();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter("refresh"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
           message = intent.getStringExtra("message");
            getMessages();
            //do other stuff here
        }
    };

    //send message
    public void sendData() {
        stringRequest = new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/insertMessage", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getMessages();
                txtcontentMessage.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatPage.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("messageDescription", txtcontentMessage.getText().toString());
                param.put("messageFrom", userId);
                param.put("messageTo", friendId);
                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(ChatPage.this);
        requestQueue.add(stringRequest);
    }


    public void getMessages() {

        stringRequest = new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getMessagesBetween", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                addMessages(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("nowUserId", userId);
                param.put("withUserId", friendId);
                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(ChatPage.this);
        requestQueue.add(stringRequest);
    }

    private void addMessages(String response) {
        try {
            adapter.clear();
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object = arr.getJSONObject(i);
                Message message = new Message();
                message.setMessage(object.getString("messageDescription"));
                message.setDate(object.getString("messageTime"));
                message.setSeen(object.getString("seen"));
                message.setMessageFrom(object.getString("messageFrom"));
                adapter.add(message);

            }
            if (adapter.getItemCount() > 0)
                messages_recycler.smoothScrollToPosition((adapter.getItemCount()) - 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}