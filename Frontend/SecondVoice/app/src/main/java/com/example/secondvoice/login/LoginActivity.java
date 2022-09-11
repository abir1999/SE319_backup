package com.example.secondvoice.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.*;
import android.view.View.OnClickListener;

import com.android.volley.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.secondvoice.R;
import com.example.secondvoice.common.SecondVoiceConstants;
import com.example.secondvoice.main.MainActivity;

public final class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final RequestQueue requests = Volley.newRequestQueue(this);

        name = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

                JSONObject parameter = new JSONObject(params);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SecondVoiceConstants.URL_LOGIN, parameter,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                try {
                                    String status = response.getString("Status");
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

                                    if (status.equals("Login Success")) {
                                        intent.putExtra("username",name.getText().toString().trim());
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requests.add(request);
            }
        });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

                JSONObject parameter = new JSONObject(params);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SecondVoiceConstants.URL_REGISTER, parameter,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                try {
                                    String status = response.getString("Status");
                                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                                    if (status.equals("Successful")) {
                                        intent.putExtra("username",name.getText().toString().trim());
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requests.add(request);
            }
        });


    }
}
