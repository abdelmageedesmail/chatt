package com.example.abdelmageed.chatting.model;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import com.example.abdelmageed.chatting.fragment.ContactFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdelmageed on 16/06/16.
 */
public class PopupClass extends DialogFragment {

    RequestQueue requestQueue;
    StringRequest stringRequest;
    EditText txtSearch;
    ImageButton search,close,startConversation;
    TextView friendName;
    String friend;
    public String id;
    SharedPreferences pref;
    public static final String mypreference = "mypref";
    public static String friendShared;
    public static String idShared;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.popuppage,container,false);
        txtSearch=(EditText)view.findViewById(R.id.txtSearch);
        friendName=(TextView)view.findViewById(R.id.friendName);
        search= (ImageButton) view.findViewById(R.id.imgBtnSearch);
        close=(ImageButton)view.findViewById(R.id.close);
        startConversation=(ImageButton)view.findViewById(R.id.startConversation);
        startConversation.setImageResource(R.drawable.send);
        view.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.popupanimation));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFriend();

            }
        });
        startConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend();
            }
        });
        return view;
    }

    public void getFriend(){
        stringRequest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getUsersWithCollageId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    friend=object.getString("userName");
                    id=object.getString("id");
                    friendName.setText(friend);
                    friendShared=friend;
                    idShared=id;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                friend=txtSearch.getText().toString();
                param.put("userCollegeId",friend);
                return param;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void addFriend(){

        Toast.makeText(getContext(),friendShared,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),idShared,Toast.LENGTH_SHORT).show();
        pref = getContext().getSharedPreferences(mypreference, getContext().MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("friendId",idShared);
        edit.putString("friend",friendShared);
        edit.commit();
    }

}
