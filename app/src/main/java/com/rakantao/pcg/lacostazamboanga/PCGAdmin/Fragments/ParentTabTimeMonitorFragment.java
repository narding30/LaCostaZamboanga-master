package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.PCGAdminHome;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.FragmentWeather;
import com.rakantao.pcg.lacostazamboanga.R;

import java.util.ArrayList;
import java.util.List;


public class ParentTabTimeMonitorFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;

    public ParentTabTimeMonitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_parent_tab_time_monitor, container, false);


        viewPager = view.findViewById(R.id.viewpagerVoyage);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabsMonitoring);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ForDepartFragment(), "PENDING");
        adapter.addFragment(new DepartedFragment(), "DEPARTED");
        adapter.addFragment(new ArrivedFragment(), "ARRIVED");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
