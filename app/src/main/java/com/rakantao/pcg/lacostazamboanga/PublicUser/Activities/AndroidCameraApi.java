package com.rakantao.pcg.lacostazamboanga.PublicUser.Activities;


import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rakantao.pcg.lacostazamboanga.PublicUser.Class.ShowCamera;
import com.rakantao.pcg.lacostazamboanga.R;

public class AndroidCameraApi extends AppCompatActivity implements View.OnClickListener{

    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;
    Button btnTakePic;
    Button btnretake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camera_api);

        frameLayout = findViewById(R.id.camContainer);
        btnTakePic = findViewById(R.id.takePicture);
        btnretake = findViewById(R.id.btnRetake);

        camera = Camera.open();


        showCamera = new ShowCamera(this,camera);
        frameLayout.addView(showCamera);
        btnTakePic.setOnClickListener(this);
        btnretake.setOnClickListener(this);
    }

    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePicture:
                if (camera!=null){
                    camera.takePicture(null,null,mPictureCallBack);
                    btnretake.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btnRetake:
                camera.startPreview();
                btnretake.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
