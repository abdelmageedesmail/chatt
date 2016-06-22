package com.example.abdelmageed.chatting.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.model.Message;

import java.util.ArrayList;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyHolder> {

    Context context;
    ArrayList<Message> messages;

    public MessagesAdapter(Context context) {
        this.context = context;
        messages = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.message_row, parent, false);
        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        SharedPreferences sh = context.getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        String UserId = sh.getString("userId", "");
        holder.tv_message.setText(messages.get(position).getMessage());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT);
        params.weight = 1.0f;


        if (messages.get(position).getMessageFrom().equals(UserId)) {
            params.gravity = Gravity.LEFT;
            params.leftMargin=10;
        } else if (!messages.get(position).getMessageFrom().equals(UserId)) {
            params.gravity = Gravity.RIGHT;
            params.rightMargin=10;
        }

        holder.tv_message.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void clear() {
        messages.clear();
    }

    public void add(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_message;

        public MyHolder(View itemView) {
            super(itemView);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);


        }
    }
}
