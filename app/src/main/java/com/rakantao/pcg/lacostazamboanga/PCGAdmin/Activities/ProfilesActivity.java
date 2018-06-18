package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.ChatAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NewsFeedAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NotifAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.ParentTabTimeMonitorFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.ShipsListProfileFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.UserListProfileFragment;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.FragmentWeather;
import com.rakantao.pcg.lacostazamboanga.R;

import java.util.ArrayList;
import java.util.List;

public class ProfilesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        btnBack = findViewById(R.id.btnBackProfile);
        viewPager = findViewById(R.id.viewpagerProfile);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabsProfile);
        tabLayout.setupWithViewPager(viewPager);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new AlertDialog.Builder(ProfilesActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure you want to go back?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    startActivity(new Intent(ProfilesActivity.this, PCGAdminHome.class));
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserListProfileFragment(), "Personnel");
        adapter.addFragment(new ShipsListProfileFragment(), "Vessels");
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ProfilesActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(ProfilesActivity.this, PCGAdminHome.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
