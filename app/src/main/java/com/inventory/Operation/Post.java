package com.inventory.Operation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.inventory.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public abstract class Post extends AsyncTask<String, Void, String> {

    private static String TAG = "Post";
    ProgressDialog progressDialog;
    private String url, json;
    Context context;
    boolean isWaiting;


    public Post(Context context, String url, String json, boolean isWaiting) {
        this.url = url;
        this.json = json;
        this.context = context;
        this.isWaiting = isWaiting;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        try {
            if (isWaiting) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(context.getString(R.string.pleaseWait));
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //  QKLogs.e(TAG, "OnPreExecute");
    }

    @Override
    protected String doInBackground(String... urls) {
        // TODO Auto-generated method stub
        String result = "";
        try {
            result = POST_DATA();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();
            onResponseReceived(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "onPostExecute: " + ex.getMessage());
        }

    }

    // Making POST request with given values
    public String POST_DATA() {
        URL url;
        HttpURLConnection conn = null;
        String response = "";
        try {

            url = new URL(this.url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(json.getBytes().length);
            conn.setRequestProperty("Content-type", "application/json");
            //   conn.setConnectTimeout(15000);
            //    conn.setReadTimeout(15000);

            if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                conn.setRequestProperty("Connection", "close");
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            Log.i(TAG, "Request URI : " + url);
            Log.i(TAG, "Request Json : " + json);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            os.close();

            //When we get response from the server
            int responseCode = conn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == 201) {
                String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                response = result.toString();
            } else if (responseCode == 400 || responseCode == 401 || responseCode == 404) {
                if (responseCode == 401) {
                }
                //     QKLogs.w(TAG, "ResponseProblem : "+ conn.getErrorStream());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Response", 2);
                response = jsonObject.toString();
            } else if (responseCode == 500) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Response", 3);
                response = jsonObject.toString();
            } else if (responseCode == 412) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Response", 5);
                response = jsonObject.toString();
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Response", 4);
                response = jsonObject.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ex.toString();
        } finally {
            conn.disconnect();
        }


        return response;
    }


    public abstract void onResponseReceived(String result);
}