package com.example.abdelmageed.chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.abdelmageed.chatting.activities.InnerActivity;
import com.example.abdelmageed.chatting.activities.Login_Activity;
import com.example.abdelmageed.chatting.activities.Registeration;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    Button LogIn, Regesteration;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sh = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
        LogIn = (Button) findViewById(R.id.btnlogin);
        Regesteration = (Button) findViewById(R.id.btnRgesteration);
        LogIn.setOnClickListener(this);
        Regesteration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int data = view.getId();
        switch (data) {
            case R.id.btnlogin:
                if (sh.getString("userId", "").equals(""))
                    startActivity(new Intent(FirstActivity.this, Login_Activity.class));
                else
                    startActivity(new Intent(FirstActivity.this, InnerActivity.class));
                break;
            case R.id.btnRgesteration:
                startActivity(new Intent(FirstActivity.this, Registeration.class));
                break;
        }
    }
}
