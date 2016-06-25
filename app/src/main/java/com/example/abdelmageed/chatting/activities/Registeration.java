package com.example.abdelmageed.chatting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.abdelmageed.chatting.GCM.RegistrationServices;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.Utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Registeration extends AppCompatActivity {

    EditText txtUserName, txtUserPassword, txtUserFaculity, txtPersonalID, ColleagueID;
    Button register;

    public static ArrayList<String> arrColleageId;
    public static ArrayList<String> arrPersonalId;
    public  static AppCompatActivity activity;
    public static int i;
    public static String url = "http://emtyazna.com/mohamed/chating/index.php/activities/registerUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Utils utils = new Utils(this, "JF_Flat_regular.ttf");
        TextView header = (TextView) findViewById(R.id.sign_up_header);
        activity=this;
        utils.FonTChange(header);
        txtUserName = (EditText) findViewById(R.id.editTextName);
        txtUserPassword = (EditText) findViewById(R.id.editpassword);
        txtUserFaculity = (EditText) findViewById(R.id.editFaculityname);
        txtPersonalID = (EditText) findViewById(R.id.editTextpersonalId);
        ColleagueID = (EditText) findViewById(R.id.editTextId);
        register = (Button) findViewById(R.id.buutonRegister);

        /**
         * Colooage id
         */
        arrColleageId=new ArrayList<>();
        arrColleageId.add("123");
        arrColleageId.add("123456789");
        arrColleageId.add("12345");
        arrColleageId.add("123456");
        arrColleageId.add("1234567");
        arrColleageId.add("1000000017");

        /**
         * personal id
         */
        arrPersonalId=new ArrayList<>();
        arrPersonalId.add("1234567891");
        arrPersonalId.add("123456789852");
        arrPersonalId.add("123456789741");
        arrPersonalId.add("123456789741");
        arrPersonalId.add("123456789963");
        arrPersonalId.add("1478529631258");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from edit text

                String name = txtUserName.getText().toString();
                String personalId = txtPersonalID.getText().toString();

                String id = ColleagueID.getText().toString();
                String faculity = txtUserFaculity.getText().toString();
                String password = txtUserPassword.getText().toString();

                if (name.equals("") || personalId.equals("") || id.equals("") || faculity.equals("") || password.equals("")) {
                    Toast.makeText(Registeration.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
                }

                else if (!arrColleageId.contains(id) && !arrPersonalId.contains(personalId)){
                    Toast.makeText(Registeration.this,"There is not student with this id",Toast.LENGTH_SHORT).show();
                }
                else {

                    // uploadData();
                    Intent i = new Intent(Registeration.this, RegistrationServices.class);

                    /**
                     * start bundle with key and value to send .
                     *
                     * call in other class with key e.x-- "name"
                     */
                    i.putExtra("name", name);
                    i.putExtra("id", id);
                    i.putExtra("password", password);
                    i.putExtra("personalId", personalId);
                    final ProgressDialog progressDialog = new ProgressDialog(Registeration.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();
                    startService(i);

                }
            }

        });
    }

}

//    public void uploadData() {
//        final ProgressDialog progressDialog = new ProgressDialog(Registeration.this);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.show();
//        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject object = new JSONObject(response);
//                    userID = object.getString("userId");
//                    Toast.makeText(Registeration.this, userID, Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                progressDialog.dismiss();
//
//                progressDialog.hide();
//
//                startActivity(new Intent(Registeration.this, Login_Activity.class));
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.setMessage("Error Connection...");
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                String name = txtUserName.getText().toString();
//                String personalId = txtPersonalID.getText().toString();
//                String id = ColleagueID.getText().toString();
//                String faculity = txtUserFaculity.getText().toString();
//                String password = txtUserPassword.getText().toString();
//                params.put("userCollegeId", id);
//                params.put("personId", personalId);
//                params.put("userPassword", password);
//                params.put("userName", name);
//                return params;
//
//            }
//        };
//        requestQueue = Volley.newRequestQueue(Registeration.this);
//        requestQueue.add(stringRequest);
//    }



