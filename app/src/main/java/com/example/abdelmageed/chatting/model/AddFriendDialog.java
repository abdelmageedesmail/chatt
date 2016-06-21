package com.example.abdelmageed.chatting.model;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.abdelmageed.chatting.activities.ChatPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdelmageed on 16/06/16.
 */
public class AddFriendDialog extends Dialog {

    RequestQueue requestQueue;
    StringRequest stringRequest;
    EditText txtSearch;
    ImageButton search, close, startConversation;
    TextView friendName;
    String friend;
    public String id;
    SharedPreferences pref;
    public static String friendShared;
    public static String idShared;
    Context context;
    boolean found;

    public AddFriendDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_friend);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        friendName = (TextView) findViewById(R.id.friendName);
        search = (ImageButton) findViewById(R.id.imgBtnSearch);
        close = (ImageButton) findViewById(R.id.close);
        startConversation = (ImageButton) findViewById(R.id.startConversation);
        startConversation.setImageResource(R.drawable.send);
        // getWindow().setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popupanimation));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFriend();

            }
        });
        startConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFriend();
                addFriend();
            }
        });
    }


    public void getFriend() {
        stringRequest = new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getUsersWithCollageId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.keys().next().equals("error")) {
                        found = false;
                        Toast.makeText(context, "Not Found user", Toast.LENGTH_SHORT).show();
                    } else {
                        friend = object.getString("userName");
                        id = object.getString("id");
                        friendName.setText(friend);
                        friendShared = friend;
                        idShared = id;
                        found = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                friend = txtSearch.getText().toString();
                param.put("userCollegeId", friend);
                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void addFriend() {
        if (found) {
            Intent i = new Intent(context, ChatPage.class);
            i.putExtra("friendId", idShared);
            i.putExtra("friendName", friendShared);

            context.startActivity(i);
            dismiss();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismiss();
    }
}
