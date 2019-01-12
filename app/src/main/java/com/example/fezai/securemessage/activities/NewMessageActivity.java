package com.example.fezai.securemessage.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fezai.securemessage.DataBase.SqliteDB;
import com.example.fezai.securemessage.R;
import com.example.fezai.securemessage.models.Contact;
import com.example.fezai.securemessage.models.Message;
import com.example.fezai.securemessage.services.KeyGenerator;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class NewMessageActivity extends AppCompatActivity {

    private SqliteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        setTitle("New message");
        db = new SqliteDB(getApplicationContext());
        //db.deleteDatabase(this);
    }


    public void Send(View v) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        final String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
        String receiver = ((EditText) findViewById(R.id.receiver_box)).getText().toString();
        String content = ((EditText) findViewById(R.id.content_box)).getText().toString();

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            KeyGenerator kg = new KeyGenerator();
            PublicKey pbk = kg.getPk();
            PrivateKey prk = kg.getPrk();
            String[] separated = pbk.toString().split("=");
            String[] separated1 = separated[1].split(",");

            String[] separated2 = prk.toString().split("=");
            String[] separated3 = separated2[1].split(",");
            Message msg =new Message(receiver,author,content,Message.MSG_TYPE_SENT);
            db.InsertMessage(msg,db.getWritableDatabase());
            params.put("message", content );
            params.put("receiver", receiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/sendMsg";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences mPreferences = getSharedPreferences("session", MODE_PRIVATE);

                                try {

                                } catch (Exception e) {
                                    Log.d("error send", e.getMessage());
                                    return;
                                }
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error", "error adding contact");
                            }
                        }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {

                SharedPreferences mPreferences = getSharedPreferences("session", MODE_PRIVATE);
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + mPreferences.getString("token", ""));

                return headers;
            }
        };

        queue.add(jsonObjectRequest);
        Toast.makeText(this, "message sended successfully", Toast.LENGTH_LONG).show();
        Intent intent   = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
