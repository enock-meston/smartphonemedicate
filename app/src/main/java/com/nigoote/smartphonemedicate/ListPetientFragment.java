package com.nigoote.smartphonemedicate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListPetientFragment extends Fragment implements RvAdapter.SelectedUser{

    private String URLstring = "http://192.168.43.29:8080/emedical/list.php";
    private static ProgressDialog mProgressDialog;
    ArrayList<DataModel> dataModelArrayList;
    RvAdapter rvAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        fetchingJSON();
        dataModelArrayList = new ArrayList<>();
        setupRecycler();
        return v;
    }

    private void fetchingJSON() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        Gson gson = new Gson();

                        DataModel[] dataModels = gson.fromJson(response, DataModel[].class);
                        System.out.println(dataModels);
                        for (DataModel obj1 : dataModels) {
                            System.out.println(obj1);
                            //DataModel obj = (DataModel) obj1;

                            if (obj1.getCode1().equals("selected")) {
                                dataModelArrayList.add(obj1);
                            }

                            setupRecycler();
                        }
                        System.out.println(dataModelArrayList); // i'm so

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(stringRequest);

//        ========
    }
    private void setupRecycler(){
        rvAdapter = new RvAdapter(getContext(),dataModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.notifyDataSetChanged();


    }

    @Override
    public void selectedUser(DataModel dataModel) {
        startActivity(new Intent(getActivity(),SelectedUserActivity.class).putExtra("data",dataModel));
    }
}
