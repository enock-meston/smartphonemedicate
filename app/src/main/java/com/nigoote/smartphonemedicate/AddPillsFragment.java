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

public class AddPillsFragment extends Fragment {
    EditText pillsName;
    Button savebtn;
    String URLpath =Constant.host;

    public AddPillsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addpills,container,false);

        pillsName = (EditText) view.findViewById(R.id.editTextTextPillsname);
        savebtn = (Button) view.findViewById(R.id.savebtn2);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pname = pillsName.getText().toString();


                if (TextUtils.isEmpty(pname)){
                    openDialog("All Fields are Required Please fill all Fields...");
                }else {
                    addpillsMethod(pname);
                }


            }

            private void addpillsMethod(String pname) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(false);
                progressDialog.setTitle("Registering New Account");
                progressDialog.show();
                String url =URLpath+"emedical/addPills.php";
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Successfully_Registered")){
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
                        param.put("pillsname",pname);
                        return param;
                    }
                };
                requestQueue.add(request);
            }

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
