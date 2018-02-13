package com.inventory.Operation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BookMEds on 20-09-2017.
 */

public class LoadBitmapFromURL extends AsyncTask<String, Void, Bitmap> {

    Bitmap myBitmap = null;
    String imageURL;

    public LoadBitmapFromURL(String imageURL) {
        this.imageURL = imageURL;
    }

    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Bitmap bitmap) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
