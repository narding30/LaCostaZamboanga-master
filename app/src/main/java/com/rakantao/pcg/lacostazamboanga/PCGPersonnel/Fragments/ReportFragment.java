package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Activities.SendReportActivity;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Datas.DataSendReport;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.ViewHolders.ReportsViewHiolder;
import com.rakantao.pcg.lacostazamboanga.R;


public class ReportFragment extends Fragment {

    Button btnreport;
    RecyclerView recyclerView;
    View view;

    private DatabaseReference mDatabase;
    private LinearLayoutManager linearLayoutManager;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report, container, false);


        btnreport = view.findViewById(R.id.btnGoSendReport);
        recyclerView = view.findViewById(R.id.recyclerListOfReports);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Report");
        recyclerView.setLayoutManager(linearLayoutManager);


        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SendReportActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter <DataSendReport, ReportsViewHiolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataSendReport, ReportsViewHiolder>(

                DataSendReport.class,
                R.layout.list_report,
                ReportsViewHiolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ReportsViewHiolder viewHolder, DataSendReport model, int position) {

                viewHolder.tvVesselName.setText(model.vesselName);
                viewHolder.tvInspector.setText(model.inspector);
                viewHolder.tvTimeUploaded.setText(model.timeUploaded);

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
