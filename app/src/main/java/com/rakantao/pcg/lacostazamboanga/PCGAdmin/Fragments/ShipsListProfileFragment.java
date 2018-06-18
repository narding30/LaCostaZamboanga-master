package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.PCGAdminHome;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.RegisterVessel;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.SetVesselScheduleActivity;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Datas.DataSetVesselSched;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Datas.DataShippingProfileList;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders.ProfileShippingListViewHolder;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders.SetVesselScheduleViewHolder;
import com.rakantao.pcg.lacostazamboanga.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipsListProfileFragment extends Fragment {


    View view;
    FloatingActionButton btnAddVessel;
    private RecyclerView Recyclerview;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef,databaseReference;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mUserDatabase;

    public ShipsListProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_ships_list_profile, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        Recyclerview = view.findViewById(R.id.recyclerShipsList);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        childRef = mDatabaseRef.child("VesselDetails");

        Recyclerview.setLayoutManager(linearLayoutManager);

        btnAddVessel = view.findViewById(R.id.btnAddVessel);

        btnAddVessel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterVessel.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataShippingProfileList, ProfileShippingListViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataShippingProfileList, ProfileShippingListViewHolder>(

                        DataShippingProfileList.class,
                        R.layout.profile_shippinglist,
                        ProfileShippingListViewHolder.class,
                        childRef

                ) {
                    @Override
                    protected void populateViewHolder(final ProfileShippingListViewHolder viewHolder, DataShippingProfileList model, int position) {
                        viewHolder.vesselname.setText(model.getVesselName());
                        viewHolder.vesseltype.setText(model.getVesselType());
                        viewHolder.vesselorigin.setText(model.getOrigin());
                        viewHolder.vesseldestination.setText(model.getDestination());

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
                                                .into(viewHolder.imagevessel, new Callback() {
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


                        viewHolder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent myIntent = new Intent(getContext(), SetVesselScheduleActivity.class);
                                myIntent.putExtra("vesselName", viewHolder.vesselname.getText().toString());
                                myIntent.putExtra("vesselType", viewHolder.vesseltype.getText().toString());

                                startActivity(myIntent);
                            }
                        });


                    }
                };

        Recyclerview.setAdapter(firebaseRecyclerAdapter);
    }

}
