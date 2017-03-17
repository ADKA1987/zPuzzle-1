package com.teamzenith.game.zpuzzle.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Kids;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;
import com.teamzenith.game.zpuzzle.util.ImageSplit;
import com.teamzenith.game.zpuzzle.util.ShufflingImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game extends AppCompatActivity implements View.OnClickListener {


    private static final int CAMERA_REQUEST = 1888;
    // private ImageView imageViewMain;
    ArrayList<Integer> images;
    TextView textView;
    Button photoButton;
    TableLayout ll;
    TableRow tableRow;

    int row;
    int column;
    Level level;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        createComponents();
        initComponent();
        actions();
        ActivityCompat.requestPermissions(Game.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }


    private void createComponents() {
        //imageViewMain = (ImageView) findViewById(R.id.selected_image);
        photoButton = (Button) this.findViewById(R.id.takePicture);
        textView = (TextView) findViewById(R.id.niveauText);


    }

    private void initComponent() {
        Intent intent = this.getIntent();
        Level level = (Level) intent.getSerializableExtra("Level");
        if (level instanceof Hard) {
            row = Hard.ROW;
            column = Hard.COLUMN;
        } else if (level instanceof Medium) {
            row = Medium.ROW;
            column = Medium.COLUMN;
        } else {
            row = Kids.ROW;
            column = Kids.COLUMN;

        }

        textView.setText(String.valueOf(level));
    }

    private void actions() {
        photoButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(ll!=null){
           // setContentView(R.layout.activity_game);
            //invalidate();
            //ViewGroup vg = (ViewGroup) findViewById (R.id.videoTitleTxtView);
            ll.removeAllViews();
            ll.refreshDrawableState();
        }

        Uri relativePath = Uri.fromFile(new File("/sdcard/Download/images.jpeg"));
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, relativePath);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            settingImages();
        }
    }


    private void settingImages() {
        File imgFile1 = new File("/sdcard/Download/images.jpeg");
        Bitmap photo;
        if (level instanceof Hard) {
            photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 1000, 1000, true);
        } else if (level instanceof Medium) {
            photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 750, 750, true);
        } else
            photo = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imgFile1.getAbsolutePath()), 500, 500, true);

        //   Bitmap resized1 = Bitmap.createScaledBitmap(photo, 750, 750, true);
        // imageViewMain = (ImageView) findViewById(R.id.selected_image);
        // imageViewMain.setImageBitmap(photo);


      /*  images = new ArrayList<>();
        ImageButton[] imageButtonVersions = new ImageButton[row * column];
        for (int i = 0; i < row * column; i++) {

            imageButtonVersions[i] = new ImageButton(this);
            imageButtonVersions[i].setId(i);
            //imageButtonVersions[i].setW

            System.out.println(imageButtonVersions[i].getId());

            images.add(i, imageButtonVersions[i].getId());


        }*/

        Bitmap[] bmp;

        // imageViewMain.setImageBitmap(photo);

        Bitmap[] tmpbmp;
        ImageSplit imageSplit = new ImageSplit();
        try {
            bmp = imageSplit.get(photo, row, column);


            ShufflingImage shufflingImage = new ShufflingImage();
            // tmpbmp = shufflingImage.shuffle(bmp);
            tmpbmp = bmp;


            int j = 0;

            ll = (TableLayout) findViewById(R.id.table);

            tableRow = new TableRow(this);
            for (int i = 0; i < row * column; i++) {

                if (j == ((row * column) / row) - 1) {

                    ImageView im = new ImageView(this);
                    im.setId(i);
                    Bitmap resized = Bitmap.createScaledBitmap(tmpbmp[i], 250, 250, true);
                    im.setImageBitmap(resized);
              /*      im.setScaleType(ImageView.ScaleType.CENTER);
                    im.setAdjustViewBounds(true);*/

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.setMargins(1, 1, 1, 1);
                    im.setLayoutParams(params);
                    tableRow.addView(im, params);

                    ll.addView(tableRow);
                    tableRow = new TableRow(this);
                    j = 0;
                } else {

                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tableRow.setLayoutParams(lp);

                    ImageView im = new ImageView(this);

                    /*im.setScaleType(ImageView.ScaleType.CENTER);
                    im.setAdjustViewBounds(true);*/
                    im.setId(i);

                    Bitmap resized = Bitmap.createScaledBitmap(tmpbmp[i], 250, 250, true);
                    im.setImageBitmap(resized);

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    //params.leftMargin = 5;
                    params.setMargins(1, 1, 1, 1);

                    im.setLayoutParams(params);

                    tableRow.addView(im, params);

                    j++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Game.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("h",outState);
        outState.putInt("req",RESULT_OK);
        outState.putInt("res",1888);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        Bundle b=savedInstanceState;
       int s= b.getInt("req");
       int h= b.getInt("res");

        onActivityResult(h, s,null);
    }*/

}