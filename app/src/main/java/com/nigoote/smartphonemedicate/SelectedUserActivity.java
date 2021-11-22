package com.nigoote.smartphonemedicate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class SelectedUserActivity extends AppCompatActivity {
    String URLPath ="http://192.168.137.1:8080/";
    TextView tvUser;
    Spinner spinner;
    ArrayList<String> pillsList = new ArrayList<>();
    ArrayAdapter<String> pillsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);
        tvUser = (TextView) findViewById(R.id.selecteduser);
        spinner = (Spinner) findViewById(R.id.spinner);

        Intent intent =getIntent();

        if (intent.getExtras() != null){
            DataModel dataModel = (DataModel) intent.getSerializableExtra("data");
            tvUser.setText("Selected Petient is : "+dataModel.getNames() + " Has Disease : "+ dataModel.getDisease());
        }

        selectPillsMethod();

    }

    private void selectPillsMethod() {
//        RequestQueue requestQueue = Volley.newRequestQueue(SelectedUserActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLPath, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("selected");
                    for (int i=0; i <jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pillsTitle =jsonObject.optString("title");
                        pillsList.add(pillsTitle);
                        pillsAdapter = new ArrayAdapter<>(SelectedUserActivity.this,
                                android.R.layout.simple_spinner_item,pillsList);
                        pillsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(pillsAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
}