package com.example.abdelmageed.chatting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdelmageed.chatting.R;

/**
 * Created by abdelmageed on 10/06/16.
 */
public class ChatFragment extends Fragment {
    public ChatFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chatfragment,container,false);

        return view;
    }
}
