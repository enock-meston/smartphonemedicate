package com.nigoote.smartphonemedicate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nigoote.smartphonemedicate.ClientFragments.PetientActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String UrlPath = Constant.host;
    Button login;
    CheckBox loginState;
    EditText Username, PasswordLg;

    SharedPreferences sharedPreferences;
    private String phone;
    boolean VISIBLE_PASSWORD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Username = (EditText) findViewById(R.id.txtuserlg);
        PasswordLg = (EditText) findViewById(R.id.txtpasslg);
        loginState = (CheckBox) findViewById(R.id.checkBox);
        login = (Button) findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtusername = Username.getText().toString();
                String txtpass = PasswordLg.getText().toString();
                if (TextUtils.isEmpty(txtusername) || TextUtils.isEmpty(txtpass)) {
                    Toast.makeText(MainActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else {
                    login(txtusername, txtpass);
                }
            }
        });

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState), "");

        if (loginStatus.equals("loggedin")) {
            String txtph = Username.getText().toString();
        }
    }

    private void login(String txtusername, String txtpass) {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Logging");
        progressDialog.show();
        String url = UrlPath + "emedical/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                Log.d("LogResp", res);
                try {
                    JSONObject resp = new JSONObject(res);
                    String response = resp.getString("status");
                    if (response.contains("Login_Success")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id",resp.getString("id"));
                        editor.putString("names",resp.getString("names"));
                        editor.putString("phone",resp.getString("phoneNumber"));
                        editor.putString("userType",resp.getString("userType"));

                        if (response.equals("Login_Success")) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            if (loginState.isChecked()) {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                            } else {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                            }
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, AdminActivity.class));

                        } else if (response.equals("Login_Success2")) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            if (loginState.isChecked()) {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedin");
                            } else {
                                editor.putString(getResources().getString(R.string.prefLoginState), "loggedout");
                            }
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, PetientActivity.class));
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException ex) {
                    Log.d("jsonerr", ex.getMessage());
                } ;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("username", txtusername);
                param.put("password", txtpass);
                return param;
            }
        };
        requestQueue.add(request);

//
    }
}