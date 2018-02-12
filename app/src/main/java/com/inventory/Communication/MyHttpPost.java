package com.inventory.Communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.inventory.Listeners.MyHttpPostListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyHttpPost extends AsyncTask<String, Integer, String> {
    private MyHttpPostListener listener;
    public String url = null;
    public String postData = "";
    public final String TAG = "HTTP_POST - ";
    public boolean flag;
    Context context;


    public MyHttpPost(MyHttpPostListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public MyHttpPost(MyHttpPostListener listener, boolean flag, Context context) {
        this.listener = listener;
        this.flag = flag;
        this.context = context;
    }


    //required methods
    protected void onPreExecute() {
        listener.preExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            try {

                this.listener.callback(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.listener.connectionFailed();
        }
    }

    @Override
    protected String doInBackground(String... params) {


        url = params[0];
        postData = params[1];

        String responseJson = null;

        try {

            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            org.apache.http.client.methods.HttpPost httppost = new org.apache.http.client.methods.HttpPost(url);
            if (flag)
                httppost.setHeader("Content-type", "application/json");


//
// httppost.setHeader("Content-type", "application/json");
            BufferedReader in = null;


            // Add your data

            Log.i(TAG, postData);

            if (postData.length() > 1) {

                // set json to StringEntity
                StringEntity se = new StringEntity(postData);

                // set httpPost Entity
                httppost.setEntity(se);

                // Set some headers to inform server about the type of the content
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");


            }


            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            //Header header = response.getFirstHeader("Set-Cookie");


            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            responseJson = sb.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return responseJson;
    }
}
