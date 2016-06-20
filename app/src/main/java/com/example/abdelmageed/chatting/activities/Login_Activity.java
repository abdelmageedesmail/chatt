package com.example.abdelmageed.chatting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    Button logIn;
    EditText txtuserEmail,txtUserPassword;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String userName,userPhone,userId;
    SharedPreferences pref;
    public static final String mypreference = "mypref";

    public static String url="http://emtyazna.com/mohamed/chating/index.php/activities/userLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        logIn=(Button)findViewById(R.id.buutonLogin);
        ImageView img=(ImageView)findViewById(R.id.imagelogin);
        txtuserEmail=(EditText)findViewById(R.id.TextEmail);
        txtUserPassword=(EditText)findViewById(R.id.TextPassword);
        img.setImageResource(R.mipmap.icon);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }


    public void userLogin(){
        final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loging In...");
        progressDialog.show();
        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    userName=object.getString("userName");
                    userPhone=object.getString("userPhone");
                    userId=object.getString("id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
                startActivity(new Intent(Login_Activity.this, InnerActivity.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String email=txtuserEmail.getText().toString();
                String password=txtUserPassword.getText().toString();
                params.put("userCollegeId",email);
                params.put("userPassword",password);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(Login_Activity.this);
        requestQueue.add(stringRequest);
    }
}
