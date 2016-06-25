package com.example.abdelmageed.chatting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.Utils.Utils;
import com.example.abdelmageed.chatting.activities.ChatPage;
import com.example.abdelmageed.chatting.model.Contact;

import java.util.ArrayList;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyHolder> {

    Context context;
    ArrayList<Contact> contacts;

    public ChatRecyclerAdapter(Context context) {
        this.context = context;
        contacts = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.chat_row, parent, false);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        holder.tv_name.setText(contacts.get(position).getUserName());

        holder.tv_message.setText(contacts.get(position).getMessage());


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChatPage.class);
                i.putExtra("friendId", contacts.get(position).getUserID());
                i.putExtra("friendName", contacts.get(position).getUserName());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void add(ArrayList<Contact> tripList) {
        contacts.clear();
        contacts.addAll(tripList);
        notifyDataSetChanged();


    }

    class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linear;
        TextView tv_name, tv_message;

        public MyHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_usr_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_last_message);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
            Utils utils = new Utils(context, "JF_Flat_regular.ttf");
            utils.FonTChange(tv_name);


        }
    }
}

