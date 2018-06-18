package com.rakantao.pcg.lacostazamboanga;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.PCGAdminHome;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Activities.PCGHomeActivity;
import com.rakantao.pcg.lacostazamboanga.PublicUser.Activities.UserHomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private int timedelay = 4000;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public String  userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        changeStatusBarColor();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUserData();
                startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, timedelay);
    }

    private void getUserData() {
        if (firebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            userID =  currentUser.getUid();
            databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataUser user = dataSnapshot.getValue(DataUser.class);
                    if(dataSnapshot.exists()){

                        String usertype = (user.Usertype);

                        if (usertype.equals("admin")){
                            startActivity(new Intent(SplashScreenActivity.this, PCGAdminHome.class));
                        }else if (usertype.equals("personnel")){
                            startActivity(new Intent(SplashScreenActivity.this, PCGHomeActivity.class));
                        }else if (usertype.equals("user")){
                            startActivity(new Intent(SplashScreenActivity.this, UserHomeActivity.class));
                        } else {
                            Toast.makeText(SplashScreenActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            firebaseAuth.signOut();
                            startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
        }

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }
}
