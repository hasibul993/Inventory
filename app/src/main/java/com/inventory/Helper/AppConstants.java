package com.inventory.Helper;

import android.Manifest;
import android.content.SharedPreferences;

import java.text.DecimalFormat;

/**
 * Created by BookMEds on 02-02-2018.
 */

public interface AppConstants {

    public static String URL = "http://xamprdemo.cloudapp.net/";

    public static String SOCKET_TIMEOUT = "java.net.SocketTimeoutException";
    public static String INVALID_HOSTNAME = "java.net.UnknownHostException";
    public static String CONNECTION_GONE = "failed to connect";


    public static final String MyPREFERENCES = "share_preference_key";

    public static String THEMECOLOR = "#008B8B";

    /*Medicine icon colors*/
    public static String TABLETS_THEMECOLOR = "#cccc99";
    public static String INJECTION_THEMECOLOR = "#ecc6c6";
    public static String CREAM_THEMECOLOR = "#e6b3cc";
    public static String SYRUP_THEMECOLOR = "#ffcc99";
    public static String CAPSULES_THEMECOLOR = "#c6d9ec";
    public static String MISCELLANEOUS_THEMECOLOR = "#ffaa80";

    public static String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";

   /*Fonts*/
    public static String ROBOTO_MEDIUM = "fonts/Roboto-Medium.ttf";


    public static String SHARE_TITLE = "Hey! Check out this free app that I'm using to organise my pharmacy inventory! https://play.google.com/store/apps/details?id=com.xampr&hl=en";

    public final String DEFAULT_ITEM_FONT = "&#xE8FD;";

    public static DecimalFormat decimalFormatTwoPlace = new DecimalFormat("######.##");

    public static DecimalFormat decimalFormatOnePlace = new DecimalFormat("######.#");

    public static String[] PERMISSIONS_CAMERA = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static String[] PERMISSIONS_PHONE_STATE = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
