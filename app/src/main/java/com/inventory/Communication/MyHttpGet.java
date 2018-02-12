package com.inventory.Communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.inventory.Listeners.MyHttpGetListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyHttpGet extends AsyncTask<String, Integer, String>
{
    private MyHttpGetListener listener;
    public String url=null;
    public final String TAG="HTTP_GET - ";
    Context context;
    org.apache.http.client.methods.HttpGet httpGet;

    public MyHttpGet(MyHttpGetListener listener, Context context){
        this.listener=listener;
        this.context = context;
    }

    //required methods
    protected void onPreExecute() {
        listener.preExecute();
    }

    @Override
    protected void onProgressUpdate(Integer...values)
    {
    }

    @Override
    protected void onPostExecute(String response){
        if(response!=null){
            try{
                JSONObject jsonObject = new JSONObject(response);
                this.listener.callback(response);
            }catch (Exception e){

            }
        }
        else{
            this.listener.connectionFailed();
        }
    }

    public void abortHttpRequest()
    {
        Log.v(TAG, "-------------------aborted------------------------");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (httpGet!=null) {
                    try {
                        httpGet.abort();
                    }
                    catch (Exception e)
                    {
                        Log.v(TAG, e.getLocalizedMessage());
                    }
                }
            }
        };
        new Thread(runnable).start();

    }
    @Override
    protected String doInBackground(String... params) {

        url=params[0];

        String responseJson=null;

        // Create a new HttpClient and Post Header
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpGet = new org.apache.http.client.methods.HttpGet(url);


        BufferedReader in = null;

        try {
            // Add your data

            // Execute HTTP Get Request
            HttpResponse response = httpclient.execute(httpGet);


            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null)
            {
                sb.append(line + NL);
            }
            in.close();
            responseJson = sb.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  responseJson;
    }
}
