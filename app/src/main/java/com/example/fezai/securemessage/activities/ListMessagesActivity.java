package com.example.fezai.securemessage.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.*;
import android.view.View;

import com.example.fezai.securemessage.R;

import java.util.*;
import android.text.TextUtils;

import com.example.fezai.securemessage.Utils.MessageAdapter;
import com.example.fezai.securemessage.models.Contact;
import com.example.fezai.securemessage.models.Message;
import com.example.fezai.securemessage.entries.MessageEntry;
import com.example.fezai.securemessage.DataBase.SqliteDB;

public class ListMessagesActivity extends AppCompatActivity {


    private SqliteDB db;


    private List<Message> messages;

    private RecyclerView messagesLv;

    private MessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_messages);

        db = new SqliteDB(this);

        String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
        messages = db.MessagesList(db.getReadableDatabase(),author);

        final RecyclerView messagesLv = (RecyclerView) findViewById(R.id.messages_list_lv);

        messagesLv.setLayoutManager(new LinearLayoutManager(this));

        messagesLv.setHasFixedSize(true);

        /*final List<Message> msgDtoList = new ArrayList<Message>();
        Message msgDto1 = new Message("ahmed","nada","nhebek",Message.MSG_TYPE_RECEIVED);
        msgDtoList.add(msgDto1);*/

        adapter = new MessageAdapter(messages);
        messagesLv.setAdapter(adapter);


        setTitle("Messages");



        final EditText msgInputText = (EditText)findViewById(R.id.chat_input_msg);

        Button msgSendButton = (Button)findViewById(R.id.chat_send_msg);

        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    // Add a new sent message to the list.
                    Message msgDto = new Message("ahmed","nada",msgContent,Message.MSG_TYPE_SENT);
                    messages.add(msgDto);

                    int newMsgPosition = messages.size() - 1;

                    // Notify recycler view insert one new data.
                    adapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    messagesLv.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    msgInputText.setText("");
                }
            }
        });

        }



        public List<String> getallcontacts(List<Message> list){
            List<String> list01 = new ArrayList<String>();
            for (Message m : list){
                if (m.getSender() != null){
                    list01.add(m.getSender());
                }
                if (m.getReceiver() != null){
                    list01.add(m.getReceiver());
                }
            }
            Set<String> set = new HashSet<>(list01);
            list01.clear();
            list01.addAll(set);

            return  list01;
        }

        public List<String> getcontactMessages(List<Message> msgs,String contact){
            List<String> list01 = new ArrayList<String>();
            for (Message m : msgs ){
                if ((m.getSender().equals(contact)) || (m.getReceiver().equals(contact))){
                    list01.add(m.getContent());
                }
            }
            return list01;

        }
}
