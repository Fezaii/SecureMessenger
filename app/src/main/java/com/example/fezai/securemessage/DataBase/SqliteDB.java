package com.example.fezai.securemessage.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;
import android.database.Cursor;

import com.example.fezai.securemessage.entries.MessageEntry;
import com.example.fezai.securemessage.entries.ContactEntry;
import com.example.fezai.securemessage.models.Contact;
import com.example.fezai.securemessage.models.Message;

public class SqliteDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "accountpersistance.db";
    public static final String USER = "user";

    private final static String CREATE_MESSAGE =
            "CREATE TABLE " + MessageEntry.TABLE_NAME + "(" +
                    MessageEntry._ID + " INTEGER PRIMARY KEY," +
                    MessageEntry.MESSAGE_SENDER + " TEXT, " +
                    MessageEntry.MESSAGE_RECEIVER + " TEXT, " +
                    MessageEntry.MESSAGE_CONTENT + " TEXT," +
                    MessageEntry.MESSAGE_TYPE + " TEXT" +
                    ");";

    private final static String DROP_MESSAGE =
            "DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME;

    private static final String CREATE_USER =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + "(" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ContactEntry.CONTACT_NAME + " VARCHAR(20) NOT NULL, " +
                    ContactEntry.USER_PHONE + " VARCHAR(10) NOT NULL, " +
                    ContactEntry.CONTACT_PUBKEY + " VARCHAR(200) NOT NULL, " +
                    ContactEntry.CONTACT_PRIVATEKEY + " VARCHAR(200) NOT NULL, " +
                    ContactEntry.CONTACT_STATUS + " VARCHAR(200) NOT NULL , " +
                    USER + " VARCHAR(200) NOT NULL " +");";

    private static final String DROP_CONTACT =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;


    public SqliteDB(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_MESSAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_MESSAGE);
        db.execSQL(DROP_CONTACT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static void  InsertContact(Contact contact, SQLiteDatabase db,String user){
        String sql ="INSERT or replace INTO " + ContactEntry.TABLE_NAME +  "(" + ContactEntry.CONTACT_NAME + "," +
                ContactEntry.USER_PHONE + "," + ContactEntry.CONTACT_PUBKEY + "," + ContactEntry.CONTACT_PRIVATEKEY + "," + ContactEntry.CONTACT_STATUS + "," + USER + ") VALUES('" + contact.getUsername() +
                "','" + contact.getPhone() + "','" + contact.getPubkey() + "','" + contact.getPrivatekey() + "','" + contact.getStatus() + "','" + user + "');" ;
        db.execSQL(sql);
    }


    public static List<Contact> ContactsList(SQLiteDatabase db,String user){

        List<Contact> contact_list = new ArrayList<>();
        String[] field = {ContactEntry.CONTACT_NAME,ContactEntry.USER_PHONE};
        Cursor c = db.query(ContactEntry.TABLE_NAME, field, "user=?", new String[] {user}, ContactEntry.CONTACT_NAME, null, ContactEntry.CONTACT_NAME);

        int iname= c.getColumnIndex(ContactEntry.CONTACT_NAME);
        int iphone = c.getColumnIndex(ContactEntry.USER_PHONE);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String name = c.getString(iname);
            String phone = c.getString(iphone);
            contact_list.add(new Contact(name, phone,null,null,null));
        }

        return contact_list;
    }

    public static void  InsertMessage(Message msg, SQLiteDatabase db){
        String sql ="INSERT or replace INTO " + MessageEntry.TABLE_NAME +  "(" + MessageEntry.MESSAGE_SENDER + "," +
                MessageEntry.MESSAGE_RECEIVER + "," + MessageEntry.MESSAGE_CONTENT + "," + MessageEntry.MESSAGE_TYPE + ") VALUES('" + msg.getSender() +
                "','" + msg.getReceiver() + "','" + msg.getContent() + "','" + msg.getType() + "');" ;
        db.execSQL(sql);
    }

    public static List<Message> MessagesList(SQLiteDatabase db,String user){

        List<Message> message_list = new ArrayList<>();
        //String[] field = {MessageEntry.MESSAGE_SENDER,MessageEntry.MESSAGE_RECEIVER,MessageEntry.MESSAGE_CONTENT,MessageEntry.MESSAGE_TYPE};
        //Cursor c = db.query(MessageEntry.TABLE_NAME, field, "sender=?", new String[] {user}, MessageEntry.MESSAGE_SENDER, null,MessageEntry.MESSAGE_SENDER);
        Cursor c = db.rawQuery("SELECT * FROM " + MessageEntry.TABLE_NAME + " WHERE " + MessageEntry.MESSAGE_SENDER + " = '" + user + "' OR " + MessageEntry.MESSAGE_RECEIVER + " = '" + user + "'",null);
        int isender= c.getColumnIndex(MessageEntry.MESSAGE_SENDER);
        int ireceiver = c.getColumnIndex(MessageEntry.MESSAGE_RECEIVER);
        int icontent = c.getColumnIndex(MessageEntry.MESSAGE_CONTENT);
        int itype = c.getColumnIndex(MessageEntry.MESSAGE_TYPE);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String sender = c.getString(isender);
            String receiver = c.getString(ireceiver);
            String content = c.getString(icontent);
            String type = c.getString(itype);
            message_list.add(new Message(sender,receiver,content,type));
        }

        return message_list;
    }

    public void deleteDatabase(Context ctx) {
        ctx.deleteDatabase(DATABASE_NAME);
    }




}

