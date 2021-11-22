package com.nigoote.smartphonemedicate.ClientFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nigoote.smartphonemedicate.R;

public class ViewAvailablePillsFragment extends Fragment {
    // this fragments helps user to View and get an available pills that was sets by the doctors
    public ViewAvailablePillsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        return view;
    }



}
