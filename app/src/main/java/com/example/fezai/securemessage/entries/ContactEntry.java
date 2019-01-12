package com.example.fezai.securemessage.entries;

import android.provider.BaseColumns;

public class ContactEntry implements BaseColumns {
    public static final String TABLE_NAME = "contact";
    public static final String CONTACT_NAME = "name";
    public static final String USER_PHONE = "phone";
    public static final String CONTACT_PUBKEY = "pubkey" ;
    public static final String CONTACT_PRIVATEKEY = "privatekey";
    public static final String CONTACT_STATUS = "status" ;
}


