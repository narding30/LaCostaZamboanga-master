package com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rakantao.pcg.lacostazamboanga.PublicUser.Activities.AndroidCameraApi;
import com.rakantao.pcg.lacostazamboanga.R;

public class UserReportFragment extends Fragment{

    View view;
    Button btnReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_report, container, false);

        btnReport = view.findViewById(R.id.btnSendReport);

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AndroidCameraApi.class));
            }
        });
        return view;
    }
}
