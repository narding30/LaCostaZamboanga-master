package com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Adapters.AdvisoryAdapter;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Datas.DataUserHome;
import com.rakantao.pcg.lacostazamboanga.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvisoryFragment extends Fragment {


    SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference database;
    AdvisoryAdapter adapter;
    RecyclerView recyclerView;
    TextView textresult;
    View view;

    public AdvisoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_advisory, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        final List<DataUserHome> userHomeList = new ArrayList();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutUserhome);
        textresult = view.findViewById(R.id.TVreview_resultUserhome);
        recyclerView = view.findViewById(R.id.recycler_viewuserhome);
        adapter = new AdvisoryAdapter(userHomeList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fill_with_data();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }
    private void fill_with_data() {
        database.child("user_home").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                if (d.exists()) {
                    List<DataUserHome> list = new ArrayList<>();
                    for (DataSnapshot dataw : d.getChildren()) {
                        DataUserHome data = dataw.getValue(DataUserHome.class);
                        list.add(data);
                    }
                    Collections.reverse(list);
                    for (DataUserHome data : list) {
                        adapter.insert(new DataUserHome(data.user, data.time, data.content));
                    }
                }else {
                    textresult.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(adapter);

    }

    void refreshItems() {
        adapter.clear();
        // ...
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...
        fill_with_data();
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
