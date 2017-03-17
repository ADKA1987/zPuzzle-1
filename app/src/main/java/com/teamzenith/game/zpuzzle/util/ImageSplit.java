package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by memmi on 2017-03-13.
 */

public class ImageSplit extends AppCompatActivity {


    public Bitmap[] get(Bitmap photo,int row,int column) throws FileNotFoundException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap[] bmp=new Bitmap[row*column];

        int count= 0;
        int width=photo.getWidth();
        int height=photo.getHeight();
        FileOutputStream out=null;
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                bmp[count]=Bitmap.createBitmap(photo,(width*j)/row,(i*height)/row,width/row,height/row);
                System.out.println("Image number" + count+ " Done");

                String path = Environment.getExternalStorageDirectory().toString();
                File file = new File(path+"/Download/bear"+count+".png");
                out = new FileOutputStream(file);
                bmp[count].compress(Bitmap.CompressFormat.PNG, 100, out);
                count++;
            }
        }
        return bmp;
    }
}
