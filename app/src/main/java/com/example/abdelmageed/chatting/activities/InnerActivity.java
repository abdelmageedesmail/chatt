package com.example.abdelmageed.chatting.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.fragment.ChatFragment;
import com.example.abdelmageed.chatting.fragment.FragmentDrawer;

/**
 * Inner acivity have 3 sliding tabs fragment call ,chat ,contact
 * navigation drawer has 3 items Location have GpsTracker in the map done with it services ,setting ,logout
 */
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
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 1:
                startActivity(new Intent(InnerActivity.this, MapsActivity.class));
                break;

            case 2:
                SharedPreferences sh = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);
                sh.edit().putString("userId", "").commit();
                startActivity(new Intent(InnerActivity.this, Login_Activity.class));
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


//    public static class MyFragment extends Fragment {
//        TextView txt;
//
//        public static MyFragment getInstance(int position) {
//            MyFragment fragment = new MyFragment();
//            Bundle args = new Bundle();
//            args.putInt("position", position);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            View view = inflater.inflate(R.layout.fragmentapp, container, false);
//            txt = (TextView) view.findViewById(R.id.position);
//            Bundle b = getArguments();
//            if (b != null) {
//                txt.setText("page Selected is" + b.getInt("position"));
//            }
//            return view;
//        }
//    }
}