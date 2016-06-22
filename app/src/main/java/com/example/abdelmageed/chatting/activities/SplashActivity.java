package com.example.abdelmageed.chatting.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.abdelmageed.chatting.FirstActivity;
import com.example.abdelmageed.chatting.R;


/**
 * Created by Amer Elsayed on 11/21/2015.
 */
public class SplashActivity extends Activity {
    Runnable run;
    Handler handler = new Handler();
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);


        run = new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);


                finish();

            }
        };
        handler.postDelayed(run, 3000);


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        handler.removeCallbacks(run);
        super.onDestroy();
    }


}
