package com.rakantao.pcg.lacostazamboanga;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    private Spinner spnRank;
    private EditText etLastName;
    private EditText etMiddleName;
    private EditText etFirstName;
    private EditText etAdress;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private ProgressBar mProgressBar;
    private RadioGroup rgGender;
    private String rank = null;
    private String mGender = null;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ImageButton btnBack;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnBack = findViewById(R.id.btnBackReg);
        spnRank =  findViewById(R.id.spnRank);
        etLastName =  findViewById(R.id.tvLastName);
        etMiddleName =  findViewById(R.id.tvMiddleName);
        etFirstName =  findViewById(R.id.tvFirstName);
        etAdress = findViewById(R.id.tvAddress);
        etEmail = findViewById(R.id.tvEmail);
        etPassword = findViewById(R.id.tvPassword);
        btnRegister =  findViewById(R.id.btnRegister);
        mProgressBar =  findViewById(R.id.progressBarReg);
        rgGender =  findViewById(R.id.rgGender);

        selectGender();
        spinnerRank();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to go back?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    private void spinnerRank() {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.rank, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRank.setAdapter(arrayAdapter);

        spnRank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rank = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void selectGender() {

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rbMale:
                        mGender = "Male";
                        break;
                    case R.id.rbFemale:
                        mGender = "Female";
                        break;
                }
            }
        });
    }

    private void registerUser() {

        final String lastname = etLastName.getText().toString().trim();
        final String firstname = etFirstName.getText().toString().trim();
        final String middlename = etMiddleName.getText().toString().trim();
        final String address = etAdress.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(lastname) ||
                TextUtils.isEmpty(firstname) ||
                TextUtils.isEmpty(middlename) ||
                TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password)){

            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();

        } else if (rank.equals("Select Rank")){
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Select Rank", Toast.LENGTH_SHORT).show();
        } else if (mGender.isEmpty()){
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
        } else {
            mProgressBar.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String user_id = currentUser.getUid();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                                HashMap<String, String> User = new HashMap<String, String>();
                                User.put("FirstName", firstname);
                                User.put("LastName", lastname);
                                User.put("MiddleName", middlename);
                                User.put("Address", address);
                                User.put("Gender", mGender);
                                User.put("Email", email);
                                User.put("Password", password);
                                User.put("Rank", rank);
                                User.put("Usertype", "personnel");


                                databaseReference = firebaseDatabase.getReference("Users").child(user_id);
                                databaseReference.setValue(User);

                                Toast.makeText(RegisterActivity.this, "You've Successfully Registered", Toast.LENGTH_LONG).show();
                                mProgressBar.setVisibility(View.INVISIBLE);

                                mAuth.signOut();
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(RegisterActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
