package com.example.fezai.securemessage.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fezai.securemessage.DataBase.SqliteDB;
import com.example.fezai.securemessage.R;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private EditText loginBox;
    private EditText passBox;


    private SqliteDB sqliteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SECURE MESSENGER");
        loginBox = findViewById(R.id.user_box);
        passBox = findViewById(R.id.pass_box);

        sqliteDb = new SqliteDB(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }



        public void login(View v) {

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("username", loginBox.getText().toString());
            params.put("password", passBox.getText().toString());

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://baobab.tokidev.fr/api/login";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (url, new JSONObject(params),

                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    SharedPreferences mPreferences = getSharedPreferences("session" ,MODE_PRIVATE);

                                    try {
                                        SharedPreferences.Editor editor = mPreferences.edit();

                                        editor.putString("token", response.getString("access_token"));  // Saving string
                                        editor.putString("login", loginBox.getText().toString());
                                        editor.commit();
                                        Intent intent   = new Intent(context, AccountActivity.class);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Log.d("login", e.getMessage());


                                    }
                                }
                            },

                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

            queue.add(jsonObjectRequest);

        }


        public void Register(View v) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }


        void handleSendText(Intent intent) {
            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (sharedText != null) {
            }
        }


}
