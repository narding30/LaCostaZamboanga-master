package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rakantao.pcg.lacostazamboanga.LoginActivity;
import com.rakantao.pcg.lacostazamboanga.R;
import com.rakantao.pcg.lacostazamboanga.RegisterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

     Button btnLogout;
     Button btnHelp;
     Button btnSettings;
     Button btnAbout;
     Button btnReport;
    private FirebaseAuth firebaseAuth;

    View view;
    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_more, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        btnAbout = view.findViewById(R.id.btnAbout);
        btnLogout = view.findViewById(R.id.btnlogout);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnReport = view.findViewById(R.id.btnReport);


        btnReport.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogout:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Sign out")
                        .setMessage("Are you sure to want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth.signOut();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
    }
}
