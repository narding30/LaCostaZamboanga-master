package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakantao.pcg.lacostazamboanga.R;

public class ActiveAdminFragment extends Fragment {

    View view;

    public ActiveAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_active_admin, container, false);

        return view;
    }
}
