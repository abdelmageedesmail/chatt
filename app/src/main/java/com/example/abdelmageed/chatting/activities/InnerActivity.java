package com.example.abdelmageed.chatting.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.SlidingTab.SlidingTabLayout;
import com.example.abdelmageed.chatting.fragment.CallsFragment;
import com.example.abdelmageed.chatting.fragment.ChatFragment;
import com.example.abdelmageed.chatting.fragment.ContactFragment;
import com.example.abdelmageed.chatting.fragment.FragmentDrawer;

/**
 * Inner acivity have 3 sliding tabs fragment call ,chat ,contact
 * navigation drawer has 3 items Location have GpsTracker in the map done with it services ,setting ,logout
 */
public class InnerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    private SlidingTabLayout mTab;
    private ViewPager pager;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        pager=(ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTab=(SlidingTabLayout) findViewById(R.id.tabs);
        mTab.setDistributeEvenly(true);
        mTab.setCustomTabView(R.layout.customtab, R.id.customtext);
        mTab.setViewPager(pager);
        mTab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.backfirst);
            }
        });

        //DrawerLayout
            drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
            drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }



    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 1:
                startActivity(new Intent(InnerActivity.this,MapsActivity.class));
                break;

            case 2:
                startActivity(new Intent(InnerActivity.this, Setting.class));
                break;
            default:
                break;
        }

        /*if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contain_tab, fragment);
            fragmentTransaction.commit();
        }*/

        }





    class MyPagerAdapter extends FragmentPagerAdapter {
        String []tabs=getResources().getStringArray(R.array.tabs);
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.tabs);
        }


        @Override
        public Fragment getItem(int position) {
            // MyFragment myFragment= MyFragment.getInstance(position);
            if(position==0){
                CallsFragment call=new CallsFragment();
                return call;
            }
            else if (position==1){
                ChatFragment chat=new ChatFragment();
                return chat;
            }
            else{
                ContactFragment contact=new ContactFragment();
                return contact;
            }
            //return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



    public static class MyFragment extends Fragment{
        TextView txt;
        public static MyFragment getInstance(int position){
            MyFragment fragment=new MyFragment();
            Bundle args=new Bundle();
            args.putInt("position",position);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragmentapp,container,false);
            txt=(TextView)view.findViewById(R.id.position);
            Bundle b=getArguments();
            if(b!=null){
                txt.setText("page Selected is"+b.getInt("position"));
            }
            return view;
        }
    }
}