package com.nigoote.smartphonemedicate.ClientFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nigoote.smartphonemedicate.Constant;
import com.nigoote.smartphonemedicate.DataModel;
import com.nigoote.smartphonemedicate.MyPillAdapter;
import com.nigoote.smartphonemedicate.R;
import com.nigoote.smartphonemedicate.SelectedUserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewAvailablePillsFragment extends Fragment {
    // this fragments helps user to View and get an available pills that was sets by the doctors
    DataModel dataModel;
    String URLPath = Constant.host;
    TextView pillName;
    public String id,phone,name;
    public SharedPreferences sharedPreferences;
    public RecyclerView recyclerPills;
    public LinearLayoutManager layoutManager;

    public ViewAvailablePillsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_availabe_pills,container,false);
        Toast.makeText(view.getContext(),"Pill fragment selected",Toast.LENGTH_SHORT).show();
        sharedPreferences = view.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Toast.makeText(view.getContext(),sharedPreferences.getString("phone","Empty"),Toast.LENGTH_SHORT).show();
        pillName = (TextView) view.findViewById(R.id.pillsname);
        recyclerPills = view.findViewById(R.id.recyclerPills);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerPills.setLayoutManager(layoutManager);
        selectPillsMethod();
        return view;
    }


    private void selectPillsMethod() {
//        RequestQueue requestQueue = Volley.newRequestQueue(SelectedUserActivity.this);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, URLPath+"emedical/selectMyPills.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("message2", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    MyPillAdapter adapter = new MyPillAdapter(getContext(),jsonArray);
                    recyclerPills.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("jsonerr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PillError",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                Log.d("UserPhone",sharedPreferences.getString("id",""));
                param.put("id",sharedPreferences.getString("id",""));
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.requireActivity());

        requestQueue.add(jsonObjectRequest);
    }





}
