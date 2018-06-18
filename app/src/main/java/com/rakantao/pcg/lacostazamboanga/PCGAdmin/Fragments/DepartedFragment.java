package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Datas.DataVesselSched;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders.DepartedViewHolder;
import com.rakantao.pcg.lacostazamboanga.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class DepartedFragment extends Fragment {

    private RecyclerView Recyclerview;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mUserDatabase;
    View view;

    public DepartedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_departed, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        Recyclerview = view.findViewById(R.id.recyclerDeparted);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        childRef = mDatabaseRef.child("VesselDetails");

        Recyclerview.setLayoutManager(linearLayoutManager);



        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataVesselSched, DepartedViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataVesselSched, DepartedViewHolder>(

                        DataVesselSched.class,
                        R.layout.departed_listrow,
                        DepartedViewHolder.class,
                        childRef.orderByChild("VesselStatus").equalTo("Departed")

                ) {
                    @Override
                    protected void populateViewHolder(final DepartedViewHolder viewHolder, DataVesselSched model, int position) {

                        viewHolder.vesseltype.setText(model.getVesselType());
                        viewHolder.vesselname.setText(model.getVesselName());
                        viewHolder.vesselorigin.setText(model.getOrigin());
                        viewHolder.vesseldesination.setText(model.getDestination());
                        viewHolder.vesseldeparttime.setText(model.getDepartureTime());
                        viewHolder.vesselarrivetime.setText(model.getArrivalTime());
                        viewHolder.vesselschedday.setText(model.getScheduleDay());


                        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("VesselImage").child(model.getVesselName());

                        mUserDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    final String image = dataSnapshot.child("image").getValue().toString();

                                    if (!image.equals("default")){
                                        Picasso.with(getContext())
                                                .load(image)
                                                .fit().centerCrop()
                                                .networkPolicy(NetworkPolicy.OFFLINE)
                                                .placeholder(R.drawable.zz)
                                                .into(viewHolder.imagevessel , new Callback() {
                                                    @Override
                                                    public void onSuccess() {

                                                    }

                                                    @Override
                                                    public void onError() {
                                                        Picasso.with(getContext())
                                                                .load(image)
                                                                .placeholder(R.drawable.zz)
                                                                .into(viewHolder.imagevessel);
                                                    }
                                                });
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                };
        Recyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
