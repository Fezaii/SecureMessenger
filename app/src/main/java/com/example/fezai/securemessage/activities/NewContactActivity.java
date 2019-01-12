package com.example.fezai.securemessage.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.example.fezai.securemessage.R;
import com.example.fezai.securemessage.services.KeyGenerator;
import com.example.fezai.securemessage.models.Contact;
import com.example.fezai.securemessage.DataBase.SqliteDB;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.PrivateKey;


public class NewContactActivity extends AppCompatActivity {

    private SqliteDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        setTitle("New contact");
        db = new SqliteDB(getApplicationContext());
         //db.deleteDatabase(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void AddContact(View v)throws
                NoSuchPaddingException,
                NoSuchAlgorithmException,
                InvalidKeyException,
                IllegalBlockSizeException,
                BadPaddingException {
            String author = getSharedPreferences("session", MODE_PRIVATE).getString("login", "");
            String receiver = ((EditText)findViewById(R.id.name_box)).getText().toString();
            String phone = ((EditText)findViewById(R.id.phone_box)).getText().toString();
            HashMap<String, String> params = new HashMap<String, String>();
            try {
                KeyGenerator kg = new KeyGenerator();
                PublicKey pbk = kg.getPk();
                PrivateKey prk = kg.getPrk();
                String[] separated = pbk.toString().split("=");
                String[] separated1 = separated[1].split(",");

                String[] separated2 = prk.toString().split("=");
                String[] separated3 = separated2[1].split(",");

                Contact contact =new Contact(receiver,phone,separated1[0],separated3[0],"PING");
                db.InsertContact(contact,db.getWritableDatabase(),author);
                params.put("message","PING [cle: '"+separated1[0]+"']" );
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
                                    SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);


                                    try {

                                    } catch (Exception e) {
                                        Log.d("error add contact", e.getMessage());
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
                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {

                    SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);
                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + mPreferences.getString("token", ""));

                    return headers;
                }
            };

            queue.add(jsonObjectRequest);
        Toast.makeText(this, "contact added successfully", Toast.LENGTH_LONG).show();
        Intent intent   = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }

}
