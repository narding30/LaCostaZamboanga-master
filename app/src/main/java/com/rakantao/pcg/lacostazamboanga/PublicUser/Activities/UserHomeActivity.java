package com.rakantao.pcg.lacostazamboanga.PublicUser.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.ChatAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NewsFeedAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Fragments.NotifAdminFragment;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Fragments.NewsFeedFragment;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.AdvisoryFragment;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.FragmentWeather;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Fragments.UserReportFragment;
import com.rakantao.pcg.lacostazamboanga.R;
import com.rakantao.pcg.lacostazamboanga.RegisterUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserHomeActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_user_home);

        firebaseAuth = FirebaseAuth.getInstance();

        boomMenuButton = findViewById(R.id.boom);
        boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
        boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
        initBoomMenu();

        viewPager = findViewById(R.id.viewpagerUser);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabsUser);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.newspaper);
        tabLayout.getTabAt(1).setIcon(R.drawable.clouds);
        tabLayout.getTabAt(2).setIcon(R.drawable.notification);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdvisoryFragment(), "");
        adapter.addFragment(new FragmentWeather(), "");
        adapter.addFragment(new UserReportFragment(), "");
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
                startActivity(new Intent(UserHomeActivity.this, UserProfileActivity.class));
            }
        });
        TextOutsideCircleButton.Builder newBuilder2 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                startActivity(new Intent(UserHomeActivity.this, UserSettingsActivity.class));
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
                loadEmergencyCall();
            }
        });
        TextOutsideCircleButton.Builder newBuilder6 = new TextOutsideCircleButton.Builder().listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                firebaseAuth.signOut();
                startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        newBuilder1.normalImageRes(R.drawable.prof).normalText("Profile").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder2.normalImageRes(R.drawable.set).normalText("Settings").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder3.normalImageRes(R.drawable.question).normalText("About us").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder4.normalImageRes(R.drawable.bug).normalText("Report Problem").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder5.normalImageRes(R.drawable.emergency).normalText("Emergency Call").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);
        newBuilder6.normalImageRes(R.drawable.powerbutton).normalText("Log Out").imagePadding(new Rect(25, 25, 25, 25)).textSize(14);

        this.boomMenuButton.addBuilder(newBuilder1);
        this.boomMenuButton.addBuilder(newBuilder2);
        this.boomMenuButton.addBuilder(newBuilder3);
        this.boomMenuButton.addBuilder(newBuilder4);
        this.boomMenuButton.addBuilder(newBuilder5);
        this.boomMenuButton.addBuilder(newBuilder6);
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

    public void loadEmergencyCall(){
        Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.setTitle("PCG Hotline");
        dialog.setContentView(R.layout.custom_layout_pcghotline);
        dialog.show();
    }
}
