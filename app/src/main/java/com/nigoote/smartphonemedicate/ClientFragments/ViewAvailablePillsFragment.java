package com.nigoote.smartphonemedicate.ClientFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nigoote.smartphonemedicate.DataModel;
import com.nigoote.smartphonemedicate.R;
import com.nigoote.smartphonemedicate.SelectedUserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewAvailablePillsFragment extends Fragment {
    // this fragments helps user to View and get an available pills that was sets by the doctors
    DataModel dataModel;
    String URLPath = "http://192.168.43.29:8080/";
    TextView pillName;

    public ViewAvailablePillsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_availabe_pills,container,false);
        pillName = (TextView) view.findViewById(R.id.pillsname);
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
                   JSONObject jsonObject = jsonArray.getJSONObject(0);
                   String pill = jsonObject.getString("title");
                   pillName.setText(dataModel.getPhoneNumber());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("jsonerr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id",dataModel.getPhoneNumber());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonObjectRequest);
    }





}
