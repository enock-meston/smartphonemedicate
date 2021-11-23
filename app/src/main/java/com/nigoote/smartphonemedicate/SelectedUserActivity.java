package com.nigoote.smartphonemedicate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectedUserActivity extends AppCompatActivity {
    String URLPath = "http://192.168.13.120/";
    TextView tvUser;
    Spinner spinner;
    TextView pil;
    ArrayList<String> pillsList = new ArrayList<>();
    String[] pillArr;
    ArrayAdapter<String> pillsAdapter;
    Button btnSave;
    DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);
        tvUser = (TextView) findViewById(R.id.selecteduser);
        spinner = (Spinner) findViewById(R.id.spinner);
        pil = (TextView) findViewById(R.id.select1);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            dataModel = (DataModel) intent.getSerializableExtra("data");
            tvUser.setText("Selected Petient is : " + dataModel.getNames() + " Has Disease : " + dataModel.getDisease());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignPillToPatient();
            }
        });

        selectPillsMethod();

    }

    private void selectPillsMethod() {
//        RequestQueue requestQueue = Volley.newRequestQueue(SelectedUserActivity.this);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, URLPath+"emedical/selectpills.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("message1", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    pillArr = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pillsTitle = jsonObject.optString("title");
                        pil.setText(pillsTitle);
                        pillsList.add(pillsTitle);
                        pillArr[i] = pillsTitle;

                    }
                    Log.d("Arr", "Size " + pillArr.length);
                    pillsAdapter = new ArrayAdapter<>(SelectedUserActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, pillArr);
//                        pillsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(pillsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("jsonerr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectedUserActivity.this);

        requestQueue.add(jsonObjectRequest);
    }

    private void assignPillToPatient() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Assigning pills to user");
        progressDialog.show();
        String url = URLPath + "emedical/settings.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              progressDialog.dismiss();
                Toast.makeText(SelectedUserActivity.this,response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SelectedUserActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("userId", dataModel.getPhoneNumber());
                param.put("pillId", spinner.getSelectedItem().toString());
                return param;
            }
        };
        requestQueue.add(request);
    }

}