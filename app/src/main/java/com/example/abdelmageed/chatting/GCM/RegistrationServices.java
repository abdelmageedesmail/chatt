package com.example.abdelmageed.chatting.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.activities.Login_Activity;
import com.example.abdelmageed.chatting.activities.Registeration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class RegistrationServices extends IntentService {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String userID;
    String id, personalId, password, name;



    public RegistrationServices() {
        super("RegistrationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = preferences.edit();
        id = intent.getExtras().getString("id");
        personalId = intent.getExtras().getString("personalId");
        password = intent.getExtras().getString("password");
        name = intent.getExtras().getString("name");


        /**
         * Instance id to create token
         */
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken("937347683419", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if (!preferences.getBoolean("token_sent", false))
                uploadData(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void uploadData(final String token) {
        String url = "http://emtyazna.com/mohamed/chating/index.php/activities/registerUser";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.keys().next().equals("error")) {

                        Toast.makeText(RegistrationServices.this, "Check Inputs may be the user is aleady exist", Toast.LENGTH_LONG).show();

                    } else {

                        userID = object.getString("userId");
                        Intent i = new Intent(RegistrationServices.this, Login_Activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Registeration.activity.finish();
                        startActivity(i);
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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userCollegeId", id);
                params.put("personId", personalId);
                params.put("userPassword", password);
                params.put("userName", name);
                params.put("gcmToken", token);
                return params;

            }
        };
        requestQueue = Volley.newRequestQueue(RegistrationServices.this);
        requestQueue.add(stringRequest);
    }
}
