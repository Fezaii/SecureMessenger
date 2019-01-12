package com.example.fezai.securemessage.entries;

import android.provider.BaseColumns;

public class MessageEntry implements BaseColumns {
    public static final String TABLE_NAME = "message";
    public static final String MESSAGE_SENDER = "sender";
    public static final String MESSAGE_RECEIVER = "receiver";
    public static final String MESSAGE_CONTENT = "content";
    public static final String MESSAGE_TYPE = "type";

}
