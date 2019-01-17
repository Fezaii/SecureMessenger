package com.example.fezai.securemessage.services;


import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.fezai.securemessage.DataBase.SqliteDB;
import com.example.fezai.securemessage.entries.MessageEntry;
import com.example.fezai.securemessage.models.Message;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MessageService extends Service {

    private boolean firstTime;
    private Handler mHandler;
    private Context context = this;
    private SqliteDB db;

    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 30 * 1000;

    SharedPreferences mPreferences;

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            syncData();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firstTime = true;

        mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

        new SqliteDB(this).deleteDatabase(this);
        db = new SqliteDB(getApplicationContext());
        //db = new SqliteDB(this).getWritableDatabase();
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private synchronized void syncData() {
        String tok;
        if(mPreferences.getString("token", null) != null)
            tok = mPreferences.getString("token", null);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/fetchMessages";

        JsonRequest req = new JsonRequest<JSONArray>(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("polli", response.toString());
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString =
                            new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                    JSONArray arr = new JSONArray(jsonString);

                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        if(o.getBoolean("alreadyReturned") && !firstTime)
                            continue;
                        final String receiver = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
                        //if(Message.isPing(o.getString("msg")) || Message.isPong(o.getString("msg")))
                            //continue;
                        Message msg =new Message(receiver,o.getString("author"),o.getString("msg"),Message.MSG_TYPE_RECEIVED);
                        db.InsertMessage(msg,db.getWritableDatabase());

                    }

                    firstTime = false;
                    return Response.error(new VolleyError());

                } catch (Exception e) {

                    return Response.error(new VolleyError());
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                if(mPreferences.getString("token", null) != null)
                    headers.put("Authorization", "Bearer " + mPreferences.getString("token", null) );
                return headers;
            }
        };

        queue.add(req);

    }
}
