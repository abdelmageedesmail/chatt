package com.example.abdelmageed.chatting.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.fragment.ChatFragment;
import com.example.abdelmageed.chatting.fragment.FragmentDrawer;
import com.example.abdelmageed.chatting.fragment.RssFragment;


public class InnerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //DrawerLayout
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment()).commit();

        // display the first navigation drawer view on app launch
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }


    private void displayView(int position) {

        switch (position) {

            case 1:
                startActivity(new Intent(InnerActivity.this, MapsActivity.class));
                break;

            case 3:
                SharedPreferences sh = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
                sh.edit().putString("userId", "").commit();
                startActivity(new Intent(InnerActivity.this, Login_Activity.class));
                finish();
                break;
            case 2:
                    Fragment fragment = new RssFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                break;
            case 4:
                startActivity(new Intent(InnerActivity.this, Login_Activity.class));
                finish();
            default:
                break;
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putBoolean("fragment_added", true);
    }
}