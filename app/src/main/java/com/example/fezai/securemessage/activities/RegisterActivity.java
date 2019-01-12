package com.example.fezai.securemessage.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fezai.securemessage.R;

import java.util.HashMap;


import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.context = this;
        setTitle("Register");
        username = findViewById(R.id.user_box);
        password = findViewById(R.id.pass_box);
    }

    public void register(View v) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username.getText().toString());
        params.put("password", password.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://baobab.tokidev.fr/api/createUser";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new JSONObject(params),

                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(context, "Account created successfully", Toast.LENGTH_LONG).show();
                                Intent intent   = new Intent(context, MainActivity.class);
                                startActivity(intent);
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Fail to create your account", Toast.LENGTH_LONG).show();

                            }
                        });

        queue.add(jsonObjectRequest);
    }
}

