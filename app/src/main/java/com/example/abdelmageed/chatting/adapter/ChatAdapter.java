package com.example.abdelmageed.chatting.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.model.Message;

import java.util.List;

/**
 * Created by abdelmageed on 20/06/16.
 */

/**
 * chat Adapter for get parsing data from getMessages
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private Context context;
    private List<Message> tripList;
    public ChatAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.context=context;
        this.tripList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.chatmessageitem, parent, false);
        Message message=tripList.get(position);
        TextView txtDate=(TextView)v.findViewById(R.id.txtmessage);
        txtDate.setText(message.getMessage());

        return v;
    }

}