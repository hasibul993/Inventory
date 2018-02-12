package com.inventory.Listeners;

/**
 * Created by Suraj on 2/18/2015.
 */
public interface WebServiceResponseListener {
    public void jsonParsingError();
    public void positiveResponse();
    public void preExecute();
    public void noConnectivity();
    public void connectionFailed();
}
