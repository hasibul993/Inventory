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

    public static String TABLETS_THEMECOLOR = "#DB7093";
    public static String INJECTION_THEMECOLOR = "#A0522D";
    public static String CREAM_THEMECOLOR = "#2E8B57";
    public static String SYRUP_THEMECOLOR = "#FFA500";
    public static String CAPSULES_THEMECOLOR = "#556B2F";
    public static String MISCELLANEOUS_THEMECOLOR = "#008080";

    public static String SIMPLE_DATE_FORMAT = "dd-MM-yyyy";

    public static DecimalFormat decimalFormatTwoPlace = new DecimalFormat("######.##");

    public static DecimalFormat decimalFormatOnePlace = new DecimalFormat("######.#");

    public static String[] PERMISSIONS_CAMERA = {android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

}
