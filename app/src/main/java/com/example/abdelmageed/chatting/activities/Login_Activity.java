package com.example.abdelmageed.chatting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    Button logIn;
    EditText txtuserEmail, txtUserPassword;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    public static String userName, userPhone, userId;
    SharedPreferences pref;

    public static String url = "http://emtyazna.com/mohamed/chating/index.php/activities/userLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        Utils utils = new Utils(this, "JF_Flat_regular.ttf");
        TextView header = (TextView) findViewById(R.id.signin_header);
        utils.FonTChange(header);
        logIn = (Button) findViewById(R.id.buutonLogin);
       // ImageView img = (ImageView) findViewById(R.id.imagelogin);
        txtuserEmail = (EditText) findViewById(R.id.TextEmail);
        txtUserPassword = (EditText) findViewById(R.id.TextPassword);
        pref = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
      //  img.setImageResource(R.mipmap.icon);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtuserEmail.getText().toString().equals("") || txtUserPassword.getText().toString().equals("")) {
                    Toast.makeText(Login_Activity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();

                } else
                    userLogin();
            }
        });
    }


    public void userLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loging In...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.keys().next().equals("error")) {

                    } else {
                        userName = object.getString("userName");
                        userId = object.getString("id");
                        //     Toast.makeText(Login_Activity.this,userId,Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("userId", userId);
                        edit.putString("userName",userName);
                        edit.commit();

                        startActivity(new Intent(Login_Activity.this, InnerActivity.class));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String email = txtuserEmail.getText().toString();
                String password = txtUserPassword.getText().toString();
                params.put("userCollegeId", email);
                params.put("userPassword", password);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(Login_Activity.this);
        requestQueue.add(stringRequest);
    }

}
