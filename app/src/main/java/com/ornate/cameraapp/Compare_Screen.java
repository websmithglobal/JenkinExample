package com.ornate.cameraapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class Compare_Screen extends AppCompatActivity implements View.OnClickListener, ResetImage {

    ImageView img1, img2, img3, img4;
    Button btnCapture, btnReset;
    Context context;
    LinearLayout layout1, layout2, layout3, layout4;
    int dect_click = 0;
    File root = Environment.getExternalStorageDirectory();
    @NonNull
    File cameraAppDir = new File(root.getAbsolutePath() + "/CameraApp");
    boolean landscap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (cameraAppDir.listFiles().length < 4 && cameraAppDir.listFiles().length > 1) {
            setContentView(R.layout.activity_compare__screen_three);

        } else {
            setContentView(R.layout.activity_compare__screen);
        }

        context = this;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscap = true;
        }


        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int c = 0; c < 4; c++) {
                    File file = new File(cameraAppDir.getAbsolutePath(), c + ".jpg");
                    if (file.exists()) {
                        file.delete();
                    }
                }
                resetImage();
                finish();
                Intent intent = new Intent(getApplicationContext(), AndroidSurfaceviewExample.class);
                startActivity(intent);
            }
        });

        btnCapture.setOnClickListener(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);

        img1.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext(), img1, 0, Compare_Screen.this));
        img2.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext(), img2, 1, Compare_Screen.this));
        img3.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext(), img3, 2, Compare_Screen.this));
        img4.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext(), img4, 3, Compare_Screen.this));

        img1.setVisibility(View.GONE);

        img2.setVisibility(View.GONE);

        img3.setVisibility(View.GONE);

        img4.setVisibility(View.GONE);


        if (landscap) {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
        }

        resetImage();

    }

    @Override
    public void onClick(View view) {
        if (view == btnCapture) {
            if (cameraAppDir.listFiles().length < 4) {
                finish();
                Intent intent = new Intent(getApplicationContext(), AndroidSurfaceviewExample.class);
                startActivity(intent);
            }
        }


    }

    public void setImage(@NonNull Bitmap bmp, @NonNull ImageView img, @NonNull LinearLayout layout) {
        Bitmap scaled = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), true);
        int w = scaled.getWidth();
        int h = scaled.getHeight();
        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        //mtx.postRotate(90);
        // Rotating Bitmap
        bmp = Bitmap.createBitmap(scaled, 0, 0, w, h, mtx, true);
        img.setVisibility(View.VISIBLE);
        if (landscap) {
            layout.setVisibility(View.VISIBLE);
        }
        img.setImageBitmap(bmp);
    }


    @Override
    public void resetImage() {


        img1.setVisibility(View.GONE);

        img2.setVisibility(View.GONE);

        img3.setVisibility(View.GONE);

        img4.setVisibility(View.GONE);


        if (landscap) {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);
        }

        try {
            for (int i = 0; i < 4; i++) {
                File file = new File(cameraAppDir.getAbsolutePath(), i + ".jpg");
                if (file.exists()) {


                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 2;
                    options.inJustDecodeBounds = false;
                    options.inTempStorage = new byte[16 * 1024];

                    Bitmap loadedImage = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    if (file.getName().equals("0.jpg")) {
                        img1.setImageBitmap(loadedImage);
                        img1.setVisibility(View.VISIBLE);
                        if (landscap) {
                            layout1.setVisibility(View.VISIBLE);
                        }
                    }
                    if (file.getName().equals("1.jpg")) {
                        img2.setImageBitmap(loadedImage);
                        img2.setVisibility(View.VISIBLE);
                        if (landscap) {
                            layout2.setVisibility(View.VISIBLE);
                        }
                    }
                    if (file.getName().equals("2.jpg")) {
                        img3.setImageBitmap(loadedImage);
                        img3.setVisibility(View.VISIBLE);
                        if (landscap) {
                            layout3.setVisibility(View.VISIBLE);
                        }
                    }
                    if (file.getName().equals("3.jpg")) {
                        img4.setImageBitmap(loadedImage);
                        img4.setVisibility(View.VISIBLE);
                        if (landscap) {
                            layout4.setVisibility(View.VISIBLE);
                        }
                    }

              /*  final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;*/
              /*  String imageUri="file://"+file.getAbsolutePath();
                BaseActivity.imageLoader.init(ImageLoaderConfiguration
                        .createDefault(getBaseContext()));
                BaseActivity.imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        imageUri=imageUri.replace("file://","");

                        if(file.getName().equals("0.jpg")){
                            img1.setImageBitmap(loadedImage);
                            img1.setVisibility(View.VISIBLE);
                            if(landscap) {
                                layout1.setVisibility(View.VISIBLE);
                            }
                        }
                        if(file.getName().equals("1.jpg")){
                            img2.setImageBitmap(loadedImage);
                            img2.setVisibility(View.VISIBLE);
                            if(landscap) {
                                layout2.setVisibility(View.VISIBLE);
                            }
                        }
                        if(file.getName().equals("2.jpg")){
                            img3.setImageBitmap(loadedImage);
                            img3.setVisibility(View.VISIBLE);
                            if(landscap) {
                                layout3.setVisibility(View.VISIBLE);
                            }
                        }
                        if(file.getName().equals("3.jpg")){
                            img4.setImageBitmap(loadedImage);
                            img4.setVisibility(View.VISIBLE);
                            if(landscap) {
                                layout4.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }
                });*/


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
