package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.rakantao.pcg.lacostazamboanga.LoginActivity;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.PCGAdminHome;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.TrackActivity;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.ChatAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NewsFeedAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NotifAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments.ChatFragment;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments.MoreFragment;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments.NewsFeedFragment;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments.ReportFragment;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.FragmentWeather;
import com.rakantao.pcg.lacostazamboanga.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PCGHomeActivity extends AppCompatActivity {

    BoomMenuButton boomMenuButton;
    FirebaseAuth firebaseAuth;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcghome);

        firebaseAuth = FirebaseAuth.getInstance();

        boomMenuButton = findViewById(R.id.boomPersonnel);
        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_1);
        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_1);
        initBoomMenu();

        viewPager = findViewById(R.id.viewpagerPersonnel);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabsPersonnel);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.newspaper);
        tabLayout.getTabAt(1).setIcon(R.drawable.clouds);
        tabLayout.getTabAt(2).setIcon(R.drawable.chat1);
        tabLayout.getTabAt(3).setIcon(R.drawable.notification);
        tabLayout.getTabAt(4).setIcon(R.drawable.reportz);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment(), "");
        adapter.addFragment(new FragmentWeather(), "");
        adapter.addFragment(new ChatFragment(), "");
        adapter.addFragment(new NotifAdminFragment(), "");
        adapter.addFragment(new ReportFragment(), "");
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


    private void initBoomMenu() {
        this.boomMenuButton.setButtonEnum(ButtonEnum.TextOutsideCircle);
        TextOutsideCircleButton.Builder newBuilder1 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                startActivity(new Intent(PCGHomeActivity.this, ProfileActivity.class));
            }
        });
        TextOutsideCircleButton.Builder newBuilder2 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {

            }
        });
        TextOutsideCircleButton.Builder newBuilder3 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {

            }
        });
        TextOutsideCircleButton.Builder newBuilder4 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {

            }
        });
        TextOutsideCircleButton.Builder newBuilder5 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                firebaseAuth.signOut();
                startActivity(new Intent(PCGHomeActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        newBuilder1.normalImageRes(R.drawable.prof).normalText("Profile").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder2.normalImageRes(R.drawable.set).normalText("Settings").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder3.normalImageRes(R.drawable.question).normalText("About us").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder4.normalImageRes(R.drawable.bug).normalText("Report Problem").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder5.normalImageRes(R.drawable.powerbutton).normalText("Log Out").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);

        this.boomMenuButton.addBuilder(newBuilder1);
        this.boomMenuButton.addBuilder(newBuilder2);
        this.boomMenuButton.addBuilder(newBuilder3);
        this.boomMenuButton.addBuilder(newBuilder4);
        this.boomMenuButton.addBuilder(newBuilder5);

    }

    boolean twice;
    @Override
    public void onBackPressed() {
        if (twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);
        twice = true;
    }
}

