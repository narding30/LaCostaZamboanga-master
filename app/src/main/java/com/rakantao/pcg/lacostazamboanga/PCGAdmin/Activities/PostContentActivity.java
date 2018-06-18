package com.rakantao.pcg.lacostazamboanga.PCGAdmin.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rakantao.pcg.lacostazamboanga.R;

import id.zelory.compressor.Compressor;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PostContentActivity extends AppCompatActivity implements View.OnClickListener {

    private int GALLERY = 1, CAMERA = 2;
    ImageView btnBack;
    Button btnChoose;
    ImageButton camImage;
    Compressor compressor;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);

        camImage = findViewById(R.id.btnCamera);
        btnBack = findViewById(R.id.btnBackPost);
        btnChoose = findViewById(R.id.btnChoose);

        camImage.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnChoose.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBackPost:
                startActivity(new Intent(PostContentActivity.this, PCGAdminHome.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case  R.id.btnChoose:
                final CharSequence[] items2 = {
                        "Everyone", "Personnel","Public User"
                };

                AlertDialog.Builder builder2 = new AlertDialog.Builder(PostContentActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        btnChoose.setText(items2[item]);
                        if (items2[item].equals("Everyone")){
                            btnChoose.setText("Everyone");
                            btnChoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.world, 0, R.drawable.down1, 0);
                        }else if (items2[item].equals("Personnel")){
                            btnChoose.setText("Personnel");
                            btnChoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lieutenant, 0, R.drawable.down1, 0);
                        }else if (items2[item].equals("Public User")){
                            btnChoose.setText("Public User");
                            btnChoose.setCompoundDrawablesWithIntrinsicBounds(R.drawable.userss, 0, R.drawable.down1, 0);
                        }
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
                break;
            case R.id.btnCamera:
                showPictureDialog();
                break;
        }
    }

    public void TakePicture(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void SelectFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SelectFromGallery();
                                break;
                            case 1:
                                TakePicture();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode == RESULT_OK || requestCode == CAMERA && resultCode == RESULT_OK){


        }
    }
}
