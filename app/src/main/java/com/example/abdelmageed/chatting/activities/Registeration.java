package com.example.abdelmageed.chatting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdelmageed.chatting.GCM.RegistrationServices;
import com.example.abdelmageed.chatting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {

    EditText txtUserName, txtUserPassword, txtUserFaculity, txtPersonalID, ColleagueID;
    Button register;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String userID;
    SharedPreferences pref;

    public static String url = "http://emtyazna.com/mohamed/chating/index.php/activities/registerUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        startService(new Intent(this, RegistrationServices.class));

        txtUserName = (EditText) findViewById(R.id.editTextName);
        txtUserPassword = (EditText) findViewById(R.id.editpassword);
        txtUserFaculity = (EditText) findViewById(R.id.editFaculityname);
        txtPersonalID = (EditText) findViewById(R.id.editTextpersonalId);
        ColleagueID = (EditText) findViewById(R.id.editTextId);
        register = (Button) findViewById(R.id.buutonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtUserName.getText().toString();
                String personalId = txtPersonalID.getText().toString();
                String id = ColleagueID.getText().toString();
                String faculity = txtUserFaculity.getText().toString();
                String password = txtUserPassword.getText().toString();
                if (name.equals("") || personalId.equals("") || id.equals("") || faculity.equals("") || password.equals("")) {
                    Toast.makeText(Registeration.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
                } else
                    uploadData();

            }
        });
    }

    public void uploadData() {
        final ProgressDialog progressDialog = new ProgressDialog(Registeration.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    userID = object.getString("userId");
                    Toast.makeText(Registeration.this, userID, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.hide();
                startActivity(new Intent(Registeration.this, Login_Activity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registeration.this, "Error", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String name = txtUserName.getText().toString();
                String personalId = txtPersonalID.getText().toString();
                String id = ColleagueID.getText().toString();
                String faculity = txtUserFaculity.getText().toString();
                String password = txtUserPassword.getText().toString();
                params.put("userCollegeId", id);
                params.put("personId", personalId);
                params.put("userPassword", password);
                params.put("userName", name);
                return params;

            }
        };
        requestQueue = Volley.newRequestQueue(Registeration.this);
        requestQueue.add(stringRequest);
    }
}
