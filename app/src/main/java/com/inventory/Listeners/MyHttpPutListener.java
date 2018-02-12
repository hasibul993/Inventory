package com.inventory.Listeners;

/**
 * Created by BookMEds on 09-11-2016.
 */

public interface MyHttpPutListener {
    public void preExecute();

    public void onProgress(int i);

    public void callback(String response);

    public void connectionFailed();
}
