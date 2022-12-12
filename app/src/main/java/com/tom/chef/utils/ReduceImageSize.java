package com.tom.chef.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ReduceImageSize {

    public static Uri compressImage(Uri old,Context inContext) {
        try {
            Bitmap image= BitmapFactory.decodeStream(inContext.getContentResolver().openInputStream(old));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Compression quality, here 100 means no compression, the storage of compressed data to baos
            int options = 90;
            if (baos.toByteArray().length>6000000){
                options = 70;
            }
            if (baos.toByteArray().length>4000000){
                options = 80;
            }
            Log.i("oldSize",String.valueOf(baos.toByteArray().length));
            while (baos.toByteArray().length / 1024 > 1800  && options>10) {  //Loop if compressed picture is greater than 400kb, than to compression
                baos.reset();//Reset baos is empty baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//The compression options%, storing the compressed data to the baos
                options -= 10;//Every time reduced by 10
            }
            Log.i("oldSizeNew",String.valueOf(baos.toByteArray().length));
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//The storage of compressed data in the baos to ByteArrayInputStream
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//The ByteArrayInputStream data generation
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), bitmap, "Title", null);
            return Uri.parse(path);
        }catch (Exception e){
            e.printStackTrace();
            return old;
        }
    }

}
