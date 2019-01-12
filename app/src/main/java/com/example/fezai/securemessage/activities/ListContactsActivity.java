package com.example.fezai.securemessage.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.fezai.securemessage.DataBase.SqliteDB;
import com.example.fezai.securemessage.R;
import com.example.fezai.securemessage.models.Contact;
import com.example.fezai.securemessage.Utils.ContactAdapter;


import java.util.List;

public class ListContactsActivity extends AppCompatActivity {





    private SqliteDB db;

    //private Cursor csr;

    private List<Contact> contacts;

    private RecyclerView usersLv;

    private ContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_conctacts);

        db = new SqliteDB(this);

        String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
        contacts = db.ContactsList(db.getReadableDatabase(),author);
        setTitle("Contacts");
        usersLv = findViewById(R.id.contacts_list);

        usersLv.setHasFixedSize(true);

        adapter = new ContactAdapter(this, contacts);
        usersLv.setLayoutManager(new LinearLayoutManager(this));
        usersLv.setAdapter(adapter);

    }


}
