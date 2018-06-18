package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakantao.pcg.lacostazamboanga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {

    View view;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_feed, container, false);



        return view;
    }
}
