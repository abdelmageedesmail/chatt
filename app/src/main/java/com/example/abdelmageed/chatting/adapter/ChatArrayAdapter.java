package com.example.abdelmageed.chatting.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.model.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by abdelmageed on 19/06/16.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{
    private TextView txtItem;
    private List<ChatMessage> messageList=new ArrayList<ChatMessage>();
    ArrayList<String> array=new ArrayList<>();
    private LinearLayout layout;
    StringRequest sringRquest;
    public static final String mypreference = "mypref";
    SharedPreferences sh;
    public static String messagedata;
    

    public ChatArrayAdapter(Context applicationContext, int chatmessageitem) {
        super(applicationContext, chatmessageitem);
    }


    public void add(ChatMessage object) {
        messageList.add(object);
        super.add(object);
    }

    public int getCount(){
        return this.messageList.size();
    }

    public ChatMessage getItem(int index){
        return this.messageList.get(index);
    }

    public View getView(int position,View converView,ViewGroup parent){
        View v=converView;
        if(v==null){
            LayoutInflater inflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.chatmessageitem,parent,false);
        }
        layout=(LinearLayout)v.findViewById(R.id.message);
        ChatMessage messageObject=getItem(position);
        txtItem=(TextView)v.findViewById(R.id.txtmessagecontent);
        txtItem.setText(messageObject.message);
        txtItem.setBackgroundResource(messageObject.left ? R.color.colorPrimary : R.color.framecolor);
        layout.setGravity(messageObject.left? Gravity.LEFT:Gravity.RIGHT);
        return v;
    }

    public void getData(){
        sringRquest=new StringRequest(Request.Method.POST, "http://emtyazna.com/mohamed/chating/index.php/activities/getMessages", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    messagedata=object.getString("messageDescription");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                sh = getContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                String friendId=sh.getString("friendId", null);
                param.put("userId",friendId);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(sringRquest);
    }
}
