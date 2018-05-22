package com.grupodespo.appcatalogo.helpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.grupodespo.appcatalogo.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class saveImage {
    String imageUrl;
    String imageName;
    Context context = MainActivity.getContext();

    public saveImage(String imgU, String imgN) {
        this.imageUrl = imgU;
        this.imageName = imgN;
        context =  MainActivity.getContext();
        new DownloadImage().execute(imageUrl);
    }



    /**********************************************************************************************************
     * Download image and save it to disk
     * <String, Void, Bitmap> String parameter, Void for progress, Bitmap for return
     **********************************************************************************************************/
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                Log.d("DownloadImage","imageName " + imageName + "sUrl " + sUrl);
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            saveImage(context, result, imageName);
        }
    }

    // files are saved to /data/data/com.codexpedia.picassosaveimage/files
    public void saveImage(Context context, Bitmap b, String imgName){
        FileOutputStream foStream;
        try {
            Log.d("saveImage","imgName " + imgName );
            foStream = context.openFileOutput(imgName, Context.MODE_PRIVATE);
            //b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
        checkIfImageExist();
    }

    public void checkIfImageExist() {
        File file = context.getFileStreamPath(imageName);
        if (file.exists())
        {
            Log.i("checkIfImageExist","The image is there >>>" + file.getAbsolutePath());
        }else{
            Log.i("checkIfImageExist","The image is not there, >>>" + file.getAbsolutePath());
        }
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
}
