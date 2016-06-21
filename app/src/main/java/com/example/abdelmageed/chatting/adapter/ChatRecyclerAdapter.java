package com.example.abdelmageed.chatting.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.activities.ChatPage;
import com.example.abdelmageed.chatting.model.Contact;

import java.util.ArrayList;

/**
 * Created by mohamed on 20/06/16.
 */
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
        if (position < getItemCount() - 1)
            holder.line.setVisibility(View.VISIBLE);
        else
            holder.line.setVisibility(View.INVISIBLE);
        holder.tv_name.setText(contacts.get(position).getUserName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
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
        View line;
        TextView tv_name;

        public MyHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            tv_name = (TextView) itemView.findViewById(R.id.tv_usr_name);


        }
    }
}

