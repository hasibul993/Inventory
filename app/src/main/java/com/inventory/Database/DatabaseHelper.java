package com.inventory.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "InVentoryDB";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "DatabaseHelper";
    protected static Context con;


    ////query
    public static final String SELECT_ALL = "select * from ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ALPHABETICAL_OREDER = " COLLATE NOCASE ";
    public static final String LIMIT_8 = " LIMIT 8 ";

    // Table name
    protected static final String TABLE_USER_KEY_DETAILS = "UserKeyDetails";
    protected static final String TABLE_SETTINGS = "Settings";
    protected static final String TABLE_PRODUCTS = "Products";

    protected static final String TABLE_MASTER_DB = "MasterDB";
    protected static final String TABLE_PHARMACY_DB = "PharmacyDB";

    // Table Columns names - TABLE_SETTINGS
    protected static final String COLUMN_USERGUID = "UserGuid";
    protected static final String COLUMN_IS_DEFAUL_TTHEME = "IsDefaultTheme";
    protected static final String COLUMN_THEME_COLORCODE = "ThemeColorCode";

    // Table Columns names - TABLE_USER_KEY_DETAILS
    protected static final String COLUMN_DEVICE_ID = "DeviceID";
    protected static final String COLUMN_DEVICE_UNIQUE_ID = "DeviceUniqueID";
    protected static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
    protected static final String COLUMN_NICK_NAME = "NickName";
    protected static final String COLUMN_EMAIL_ADDRESS = "EmailAddress";
    protected static final String COLUMN_PROFILE_PICTURE = "ProfilePicture";

    // Table Columns names - TABLE_PRODUCTS
    protected static final String COLUMN_DRUG_ID = "DrugID";
    protected static final String COLUMN_BATCH_NUMBER = "BatchNumber";
    protected static final String COLUMN_DRUG_NAME = "DrugName";
    protected static final String COLUMN_DRUG_CATEGORY = "DrugCategory";
    protected static final String COLUMN_DRUG_TRANSACTION_DATE = "DrugTransactionDate";
    protected static final String COLUMN_DRUG_MRP = "DrugMRP";
    protected static final String COLUMN_DRUG_QUANTITY = "DrugQuantity";
    protected static final String COLUMN_DRUG_EXPIRY_DATE = "DrugExpiryDate";
    protected static final String COLUMN_DRUG_DISCOUNT = "DrugDiscount";
    protected static final String COLUMN_DRUG_MANUFACTURER = "DrugManufacturer";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(QueryToCreateUserKeyDetailsTable());
            db.execSQL(QueryToCreateSettingsTable());
            db.execSQL(QueryToCreateProductTable());
            db.execSQL(QueryToCreateMasterDBTable());
            db.execSQL(QueryToCreatePharmacyDBTable());

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " onCreate : " + ex.getMessage());
        }


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " onUpgrade : " + ex.getMessage());
        }
    }

    ///this is to see the database table
    public ArrayList<Cursor> getData(String Query) {
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        try {
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[]{"message"};
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered

            MatrixCursor Cursor2 = new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);


            try {
                String maxQuery = Query;
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(maxQuery, null);


                //add value to cursor2
                Cursor2.addRow(new Object[]{"Success"});

                alc.set(1, Cursor2);
                if (null != c && c.getCount() > 0) {


                    alc.set(0, c);
                    c.moveToFirst();

                    return alc;
                }
                return alc;
            } catch (SQLException sqlEx) {
                Log.d("printing exception", sqlEx.getMessage());
                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
                alc.set(1, Cursor2);
                return alc;
            } catch (Exception ex) {

                Log.d("printing exception", ex.getMessage());

                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[]{"" + ex.getMessage()});
                alc.set(1, Cursor2);
                return alc;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alc;
    }


    public String QueryToCreateUserKeyDetailsTable() {
        return "CREATE TABLE "
                + TABLE_USER_KEY_DETAILS + "(" + COLUMN_USERGUID + " TEXT ,"
                + COLUMN_PHONE_NUMBER + " TEXT PRIMARY KEY NOT NULL ,"
                + COLUMN_NICK_NAME + " TEXT ,"
                + COLUMN_DEVICE_UNIQUE_ID + " TEXT ,"
                + COLUMN_DEVICE_ID + " TEXT , "
                + COLUMN_EMAIL_ADDRESS + " TEXT , "
                + COLUMN_PROFILE_PICTURE + " TEXT )";
    }

    public String QueryToCreateSettingsTable() {
        return "CREATE TABLE "
                + TABLE_SETTINGS + "(" + COLUMN_USERGUID + " TEXT NOT NULL , "
                + COLUMN_THEME_COLORCODE + " TEXT ,"
                + COLUMN_IS_DEFAUL_TTHEME + " INTEGER,"
                + "PRIMARY KEY (" + COLUMN_USERGUID + "))";
        //+ "PRIMARY KEY (" + COLUMN_USERGUID + "," + KEY_USER_ID_TRIMMED + "))";
    }

    public String QueryToCreateProductTable() {
        return "CREATE TABLE "
                + TABLE_PRODUCTS + "(" + COLUMN_DRUG_NAME + " TEXT NOT NULL , "
                + COLUMN_DRUG_ID + " TEXT NOT NULL ,"
                + COLUMN_BATCH_NUMBER + " TEXT ,"
                + COLUMN_DRUG_CATEGORY + " TEXT ,"
                + COLUMN_DRUG_EXPIRY_DATE + " TEXT,"
                + COLUMN_DRUG_MRP + " REAL,"
                + COLUMN_DRUG_QUANTITY + " TEXT,"
                + COLUMN_DRUG_DISCOUNT + " REAL,"
                + COLUMN_DRUG_MANUFACTURER + " TEXT,"
                + COLUMN_DRUG_TRANSACTION_DATE + " TEXT,"
                + "PRIMARY KEY (" + COLUMN_BATCH_NUMBER + "," + COLUMN_DRUG_ID + "))";
        //+ "PRIMARY KEY (" + COLUMN_USERGUID + "," + KEY_USER_ID_TRIMMED + "))";
    }

    public String QueryToCreateMasterDBTable() {
        return "CREATE TABLE "
                + TABLE_MASTER_DB + "(" + COLUMN_DRUG_NAME + " TEXT NOT NULL , "
                + COLUMN_DRUG_ID + " TEXT,"
                + COLUMN_DRUG_CATEGORY + " TEXT ,"
                + COLUMN_DRUG_MANUFACTURER + " TEXT,"
                + "PRIMARY KEY (" + COLUMN_DRUG_NAME + "))";
        //+ "PRIMARY KEY (" + COLUMN_USERGUID + "," + KEY_USER_ID_TRIMMED + "))";
    }

    public String QueryToCreatePharmacyDBTable() {
        return "CREATE TABLE "
                + TABLE_PHARMACY_DB + "(" + COLUMN_DRUG_NAME + " TEXT NOT NULL , "
                + COLUMN_DRUG_ID + " TEXT,"
                + COLUMN_DRUG_CATEGORY + " TEXT ,"
                + COLUMN_DRUG_MANUFACTURER + " TEXT,"
                + "PRIMARY KEY (" + COLUMN_DRUG_NAME + "))";
        //+ "PRIMARY KEY (" + COLUMN_USERGUID + "," + KEY_USER_ID_TRIMMED + "))";
    }

}
