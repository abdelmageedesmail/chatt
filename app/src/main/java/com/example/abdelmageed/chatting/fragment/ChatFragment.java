package com.example.abdelmageed.chatting.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.adapter.ChatRecyclerAdapter;
import com.example.abdelmageed.chatting.model.AddFriendDialog;
import com.example.abdelmageed.chatting.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ChatFragment extends Fragment {

    ChatRecyclerAdapter adapter;
    SharedPreferences sh;
    String userId, userLat, userLong;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatfragment, container, false);
        sh = getActivity().getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        userId = sh.getString("userId", "");
        userLat = sh.getString("latUser", "");
        userLong = sh.getString("lonUser", "");
        init(view);

        return view;
    }


    private void init(View view) {

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler_chat);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ChatRecyclerAdapter(getActivity());
        recycler.setAdapter(adapter);

        FloatingActionButton bt_add = (FloatingActionButton) view.findViewById(R.id.bt_add_friend);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddFriendDialog(getActivity()).show();
            }
        });

        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://emtyazna.com/mohamed/chating/index.php/activities/getMessages";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null)
                            getChat(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.setMessage("Error connection...");

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                if (userId != null)
                    params.put("userId", userId);

                return params;
            }
        };

        queue.add(stringRequest);

    }


    public void getChat(String content) {

        try {
            JSONArray array = new JSONArray(content);
            ArrayList<Contact> tripList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Contact contact = new Contact();
                contact.setMessage(object.getString("messageDescription"));
                contact.setDate(object.getString("messageTime"));
                contact.setUserName(object.getString("userName"));
                contact.setUserID(object.getString("withUser"));

                tripList.add(contact);
            }

            adapter.add(tripList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getContact(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getContacts", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                getFriend(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("userId", userId);
                param.put("userLang", userLong);
                param.put("userLat", userLat);
                return param;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getFriend(String content){
        try {
            JSONArray array = new JSONArray(content);
            ArrayList<Contact> tripList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Contact contact = new Contact();
                contact.setUserName(object.getString("userName"));
                contact.setUserID(object.getString("id"));

                tripList.add(contact);
            }

            adapter.add(tripList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
