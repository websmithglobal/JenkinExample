package com.ornate.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

public class ZoomScreen extends AppCompatActivity implements View.OnClickListener {

    TouchImageView imgZoom;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_screen);

        imgZoom = (TouchImageView) findViewById(R.id.imgZoom);

      /*  BaseActivity.imageLoader.init(ImageLoaderConfiguration
                .createDefault(getBaseContext()));
*/
        File file=new File(getIntent().getStringExtra("img"));
       // final BitmapFactory.Options options = new BitmapFactory.Options();
       // options.inSampleSize = 8;


        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap loadedImage = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        imgZoom.setImageBitmap(loadedImage);
        /*String imageUri = "file://"+file.getAbsolutePath();
        BaseActivity.imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {




                    imgZoom.setImageBitmap(loadedImage);



            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }
        });*/


    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(),Compare_Screen.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(btnBack==view){
            onBackPressed();
        }
    }
}
