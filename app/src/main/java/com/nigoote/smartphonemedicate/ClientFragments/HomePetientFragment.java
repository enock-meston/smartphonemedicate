package com.nigoote.smartphonemedicate.ClientFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nigoote.smartphonemedicate.R;

public class HomePetientFragment extends Fragment {
    TextView name1;
    public HomePetientFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_petient_home,container,false);
//        name1 = (TextView) view.findViewById(R.id.names1);
//        name1.setText(this.getArguments().getString("namess"));
//            if (getArguments() != null){
//                String nametxt= getArguments().getString("namess");
//                name1.setText(nametxt);
//            }

        return view;
    }



}
