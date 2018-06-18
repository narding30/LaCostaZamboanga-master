package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.PostContentActivity;
import com.rakantao.pcg.lacostazamboanga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedAdminFragment extends Fragment implements View.OnClickListener {

    TextView tvGo;
    View view;

    public NewsFeedAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_feed_admin, container, false);

        tvGo = view.findViewById(R.id.TVGoCreateContent);

        tvGo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TVGoCreateContent:
                startActivity(new Intent(getActivity(), PostContentActivity.class));
                break;
        }
    }
}
