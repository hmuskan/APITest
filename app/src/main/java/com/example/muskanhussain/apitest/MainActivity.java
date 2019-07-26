package com.example.muskanhussain.apitest;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText emailText, passwordText;
    private Button loginButton;
    public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(email != null && password != null) {
            Map<String, String> param = new HashMap<>();
            param.put("email", email);
            param.put("password", password);
            JSONObject reqParams = new JSONObject(param);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.BASE_URL + "/user/login", reqParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        int id = data.getInt("id");
                        String recdemail = data.getString("email");
                        Toast.makeText(MainActivity.this, "Login Successful with id " + id + " and email " + recdemail,
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MH", error.toString());

                }
            });
            queue.add(request);
        }
    }

    private void setUpUI() {
        emailText = findViewById(R.id.emailEditText);
        passwordText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        queue = Volley.newRequestQueue(this);
    }
}
