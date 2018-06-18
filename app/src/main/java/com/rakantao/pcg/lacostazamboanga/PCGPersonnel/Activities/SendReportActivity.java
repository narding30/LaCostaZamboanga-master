package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rakantao.pcg.lacostazamboanga.DataUser;
import com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities.SetVesselScheduleActivity;
import com.rakantao.pcg.lacostazamboanga.PCGPersonnel.UploadListAdapter;
import com.rakantao.pcg.lacostazamboanga.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SendReportActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private Button mSelectBtn;
    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;
    private UploadListAdapter uploadListAdapter;

    private EditText etSelectVesselName;

    public TextView fullname;

    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,getPersonnalDatas;
    public String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);

        mStorage = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        getPersonnalDatas = FirebaseDatabase.getInstance().getReference();




        fullname = findViewById(R.id.fullname);
        mSelectBtn = findViewById(R.id.btnPersonnelSendReport);
        mUploadList = findViewById(R.id.recyclerPersonnelImageList);

        etSelectVesselName = findViewById(R.id.etSelectVesselName);

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);

        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);

        etSelectVesselName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "MV Stephanie", "MV Katrina","MV Mary Joy"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SendReportActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etSelectVesselName.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

            }
        });

        getData();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get vessel name before uploading the images
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        final String format = simpleDateFormat.format(new Date());

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Report").child(uid);

        final String vesselName = etSelectVesselName.getText().toString().trim();
        final String getFullname = fullname.getText().toString().trim();

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null){

                //Toast.makeText(MainActivity.this, "Select files ", Toast.LENGTH_SHORT).show();

                    if (TextUtils.isEmpty(vesselName)){
                        Toast.makeText(SendReportActivity.this, "Please, Select Vessel Name", Toast.LENGTH_SHORT).show();
                    } else {
                        int totalItemSelected = data.getClipData().getItemCount();

                        for (int i = 0; i < totalItemSelected; i++){

                            final Uri fileUri = data.getClipData().getItemAt(i).getUri();

                            final String filename = getFileName(fileUri);

                            fileNameList.add(filename);
                            fileDoneList.add("Uploading");
                            uploadListAdapter.notifyDataSetChanged();


                            //StorageReference fileToUpload = mStorage.child(uid).child(vesselName).child(format).child("Images").child(filename);

                            StorageReference fileToUpload = mStorage.child(format).child(uid).child(vesselName).child(filename);

                            final int finalI = i;

                            final int counter = finalI;

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                            HashMap<String, String> HashString = new HashMap<String, String>();

                            if (finalI == counter){
                                HashString.put("imageUrl" + finalI, filename);
                                databaseReference.child("PersonnelReport").push().setValue(HashString);
                            }

                            fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

                                    fileDoneList.remove(finalI);
                                    fileDoneList.add(finalI, "done");
                                    //uploadListAdapter.notifyDataSetChanged();

                                    HashMap<String, String> HashString = new HashMap<String, String>();

                                    HashString.put("date", format);
                                    HashString.put("vesselname", vesselName);
                                    HashString.put("InspectorName", getFullname);

                                    mDatabase.setValue(HashString).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SendReportActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    uploadListAdapter.notifyDataSetChanged();

                                }
                            });

                    }

//                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            //Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
//
//                            fileDoneList.remove(finalI);
//                            fileDoneList.add(finalI, "done");
//
//
//                            uploadListAdapter.notifyDataSetChanged();
//
//                        }
//                    });

                }

            } else if (data.getData() != null){

                Toast.makeText(SendReportActivity.this, "Select single file ", Toast.LENGTH_SHORT).show();

            }
        }
    }

//    private void vesselName() {
//
////        @SuppressLint("SimpleDateFormat")
////        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
////        String format = simpleDateFormat.format(new Date());
////
////        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
////        String uid = current_user.getUid();
////        mDatabase = FirebaseDatabase.getInstance().getReference().child("Report").child(uid);
////
////        String vesselName = etSelectVesselName.getText().toString().trim();
////
////        if (TextUtils.isEmpty(vesselName)){
////            Toast.makeText(SetVesselScheduleActivity.this, "Please, Select Vessel Name", Toast.LENGTH_SHORT).show();
////        }
//
//    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    void getData(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID =  currentUser.getUid();
        getPersonnalDatas.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataUser user = dataSnapshot.getValue(DataUser.class);
                if (dataSnapshot.exists()){

                    String firstname = user.FirstName;
                    String middlename = user.MiddleName;
                    String lastname = user.LastName;
                    
                    fullname.setText(lastname + ", "+ firstname +" " + middlename);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
