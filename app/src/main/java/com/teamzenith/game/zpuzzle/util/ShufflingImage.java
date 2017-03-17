package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by alaaalkassar on 3/14/17.
 */

public class ShufflingImage {


    public Bitmap[] shuffle(Bitmap[] img){

        //HashMap<Integer, Bitmap> meMap=new HashMap<Integer, Bitmap>();
        Bitmap[] tmpBmp=new Bitmap[img.length];

        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < img.length; i++) {
            list.add(i);
        }

        Random rand = new Random();
        int i=0;
        while(list.size() > 0) {

            int index = rand.nextInt(list.size());
            tmpBmp[i]=img[list.remove(index)];
            i++;
        }
        return tmpBmp;
    }
}
