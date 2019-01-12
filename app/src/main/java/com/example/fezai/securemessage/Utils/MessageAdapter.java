package com.example.fezai.securemessage.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import  java.util.*;

import com.example.fezai.securemessage.R;


import com.example.fezai.securemessage.models.Message;


import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> msgDtoList;
    private Context context;



    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftMsgLayout;

        LinearLayout rightMsgLayout;

        TextView leftMsgTextView;

        TextView rightMsgTextView;

        public ViewHolder(View itemView) {
            super(itemView);
                leftMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_msg_layout);
                rightMsgLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_msg_layout);
                leftMsgTextView = (TextView) itemView.findViewById(R.id.chat_left_msg_text_view);
                rightMsgTextView = (TextView) itemView.findViewById(R.id.chat_right_msg_text_view);
        }
    }



    public MessageAdapter(List<Message> msgDtoList) {
        this.msgDtoList = msgDtoList;
        //this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message msgDto = this.msgDtoList.get(position);
        // If the message is a received message.
        if(msgDto.getType().equals(Message.MSG_TYPE_RECEIVED))
        {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftMsgTextView.setText(msgDto.getContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
        }
        // If the message is a sent message.
        else if(msgDto.getType().equals(Message.MSG_TYPE_SENT))
        {
            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightMsgTextView.setText(msgDto.getContent());
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(msgDtoList==null)
        {
            msgDtoList = new ArrayList<Message>();
        }
        return msgDtoList.size();
    }
}
