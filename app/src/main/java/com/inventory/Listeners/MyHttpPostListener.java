package com.inventory.Listeners;

public interface MyHttpPostListener {

    public void preExecute();

    public void onProgress(int i);

    public void callback(String response);

    public void  connectionFailed();

}
