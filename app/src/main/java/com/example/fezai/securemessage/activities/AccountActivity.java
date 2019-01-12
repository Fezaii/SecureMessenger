package com.example.fezai.securemessage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.fezai.securemessage.R;
import com.example.fezai.securemessage.services.MessageService;

public class AccountActivity extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_account);
            setTitle("ACCOUNT");
            Intent i = new Intent(this, MessageService.class);
            this.startService(i);
        }


        public void AddContact(View v) {
            Intent intent   = new Intent(this, NewContactActivity.class);
            startActivity(intent);
        }

        public void CreateMessage(View v) {
            Intent intent = new Intent(this, NewMessageActivity.class);
            startActivity(intent);
        }

        public void Contacts(View v) {
            Intent intent = new Intent(this, ListContactsActivity.class);
            startActivity(intent);
        }

        public void Messages(View v) {
            Intent intent = new Intent(this, ListMessagesActivity.class);
            startActivity(intent);
        }

}
