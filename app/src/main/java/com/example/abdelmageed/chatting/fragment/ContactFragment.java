package com.example.abdelmageed.chatting.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.Sqllit.DbConnection;
import com.example.abdelmageed.chatting.activities.ChatPage;
import com.example.abdelmageed.chatting.model.PopupClass;
import java.util.*;

/**
 * List view and floatting button show popup
 * get friend by faculity id
 */
public class ContactFragment extends Fragment {
    FloatingActionButton buttonAdd;
    ListView list;
    SharedPreferences sh;
    public static final String mypreference = "mypref";

    public static String Name;
    public static String Userid;

    String friendName;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    EditText txtSearch;
    ImageButton search,close,startConversation;
    TextView friendname;
    public String id;
    ArrayAdapter<String> adapter;

    String friend;
    DbConnection db;
    ArrayList<String>arr;
    public ContactFragment() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  adapter=new ArrayAdapter<String>(getActivity(),R.layout.contactitem,R.id.username);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.contactfragment, container, false);
        buttonAdd=(FloatingActionButton)view.findViewById(R.id.floatingButton);
        list=(ListView)view.findViewById(R.id.Contaactfragmentlist);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                PopupClass popUp = new PopupClass();
                popUp.show(manager, null);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ChatPage.class));
            }
        });

        sh = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        friend = sh.getString("friend", null);
        db=new DbConnection(getActivity());
        friend=sh.getString("", null);
        db.insertRowAdmin(friend);
        arr=db.getAllRecord();
        list.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arr));

/*        adapter.add(friend);
        list.setAdapter(adapter);*/
        return view;
    }

}
