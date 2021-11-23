package com.nigoote.smartphonemedicate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class AddPersonFragment extends Fragment {
    EditText Names,PhoneNumber,Username,Password,Disease;
    Button SaveBTN;
    String URLpath ="http://192.168.43.29:8080/";
    public AddPersonFragment() {
        //fragment Constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addperson,container,false);
            Names = (EditText) view.findViewById(R.id.editTextTextnames);
            PhoneNumber = (EditText) view.findViewById(R.id.editTextphone);
            Username = (EditText) view.findViewById(R.id.editTextusername);
            Password = (EditText) view.findViewById(R.id.editTextpass);
            Disease = (EditText) view.findViewById(R.id.editTextdisease);
            SaveBTN = (Button) view.findViewById(R.id.savebtn);

            SaveBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nm = Names.getText().toString();
                    String ph = PhoneNumber.getText().toString();
                    String user = Username.getText().toString();
                    String pass = Password.getText().toString();
                    String dis = Disease.getText().toString();

                    if (TextUtils.isEmpty(nm) || TextUtils.isEmpty(ph) || TextUtils.isEmpty(user)
                    || TextUtils.isEmpty(pass) || TextUtils.isEmpty(dis)){
                        openDialog("All Fields are Required Please fill all Fields...");
                    }else {
                        registrationMethod(nm,ph,user,pass,dis);
                    }

                }

                private void registrationMethod(String nm, String ph, String user, String pass, String dis) {
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(false);
                    progressDialog.setTitle("Registering New Account");
                    progressDialog.show();
                    String url =URLpath+"emedical/registration.php";
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Successfully_Registered")){
                                Log.d("enock",response+"working");
                                progressDialog.dismiss();
                                openDialog(response);
                                startActivity(new Intent(getActivity() ,MainActivity.class));
                            }else{
                                progressDialog.dismiss();
                                openDialog(response);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param = new HashMap<String, String>();
                            param.put("names",nm);
                            param.put("phonenumber",ph);
                            param.put("username",user);
                            param.put("password",pass);
                            param.put("disease",dis);
                            return param;
                        }
                    };
                    requestQueue.add(request);
                } // ends of registrationMethod
            });
        return view;
    }

    public void openDialog(String message){
        AlertDialog dlg = new AlertDialog.Builder(getActivity())
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();;
                    }
                })
                .create();
        dlg.show();

    }
}
