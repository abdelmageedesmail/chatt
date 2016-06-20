package com.example.abdelmageed.chatting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.abdelmageed.chatting.activities.Login_Activity;
import com.example.abdelmageed.chatting.activities.Registeration;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    Button LogIn,Regesteration;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ImageView imageicon=(ImageView)findViewById(R.id.imageicon);
        imageicon.setImageResource(R.mipmap.icon);
        LogIn=(Button)findViewById(R.id.btnlogin);
        Regesteration=(Button)findViewById(R.id.btnRgesteration);
        LogIn.setOnClickListener(this);
        Regesteration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int data=view.getId();
        switch (data){
            case R.id.btnlogin:
                startActivity(new Intent(FirstActivity.this, Login_Activity.class));
                break;
            case R.id.btnRgesteration:
                startActivity(new Intent(FirstActivity.this, Registeration.class));
                break;
        }
    }
}
