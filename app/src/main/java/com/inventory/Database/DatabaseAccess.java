package com.inventory.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inventory.Helper.AppConstants;
import com.inventory.Model.DrugModel;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Hasib on 05-02-2018.
 */

public class DatabaseAccess extends DatabaseHelper {

    private static String TAG = "DatabaseAccess";


    public DatabaseAccess(Context context) {
        super(context);
    }


    /*All Insert/Update Method - end*/

    public void InsertUpdateSettings(SettingsModel settingsModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            values.put(COLUMN_USERGUID, settingsModel.UserGuid);
            values.put(COLUMN_IS_DEFAUL_TTHEME, settingsModel.IsDefaultTheme);
            values.put(COLUMN_THEME_COLORCODE, settingsModel.ThemeColorCode);

            long _id = db.insertWithOnConflict(TABLE_SETTINGS, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {
                db.update(TABLE_SETTINGS, values, COLUMN_USERGUID + "= '" + settingsModel.UserGuid + "'", null);
            }

            db.setTransactionSuccessful();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertUpdateUserKeyDetails(UserKeyDetailsModel keyDetailsModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            if (keyDetailsModel.PhoneNumber != null)
                values.put(COLUMN_PHONE_NUMBER, keyDetailsModel.PhoneNumber);

            if (!StringUtils.isBlank(keyDetailsModel.NickName))
                values.put(COLUMN_NICK_NAME, keyDetailsModel.NickName);

            if (keyDetailsModel.EmailAddress != null)
                values.put(COLUMN_EMAIL_ADDRESS, keyDetailsModel.EmailAddress);

            if (keyDetailsModel.UserGuid != null)
                values.put(COLUMN_USERGUID, keyDetailsModel.UserGuid);

            if (keyDetailsModel.ProfilePicture != null)
                values.put(COLUMN_PROFILE_PICTURE, keyDetailsModel.ProfilePicture);

            if (keyDetailsModel.DeviceID != null)
                values.put(COLUMN_DEVICE_ID, keyDetailsModel.DeviceID);

            if (keyDetailsModel.DeviceUniqueID != null)
                values.put(COLUMN_DEVICE_UNIQUE_ID, keyDetailsModel.DeviceUniqueID);

            long _id = db.insertWithOnConflict(TABLE_USER_KEY_DETAILS, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {
                db.update(TABLE_USER_KEY_DETAILS, values, COLUMN_PHONE_NUMBER + "= '" + keyDetailsModel.PhoneNumber + "'", null);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " AddUpdateContacts : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }


    public void InsertUpdateDrugs(DrugModel drugModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            if (drugModel.DrugName != null)
                values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

            if (drugModel.BatchNumber != null)
                values.put(COLUMN_BATCH_NUMBER, drugModel.BatchNumber);


            values.put(COLUMN_DRUG_MRP, drugModel.DrugMRP);

            values.put(COLUMN_DRUG_QUANTITY, drugModel.DrugQuantity);

            if (drugModel.DrugExpiryDate != null)
                values.put(COLUMN_DRUG_EXPIRY_DATE, drugModel.DrugExpiryDate);

            values.put(COLUMN_DRUG_DISCOUNT, drugModel.DrugDiscount);

            if (drugModel.DrugTransactionDate != null)
                values.put(COLUMN_DRUG_TRANSACTION_DATE, drugModel.DrugTransactionDate);


            long _id = db.insertWithOnConflict(TABLE_PRODUCTS, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {

                if (values.containsKey(COLUMN_DRUG_QUANTITY))// remove quantity other wise it will update the current quantity -- and then do increment
                    values.remove(COLUMN_DRUG_QUANTITY);

                db.update(TABLE_PRODUCTS, values, COLUMN_DRUG_NAME + "= '" + drugModel.DrugName + "'", null);
                db.execSQL(DatabaseQuery.GetQueryForIncrement(drugModel.DrugName, drugModel.DrugQuantity));
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " AddUpdateContacts : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    /*All Insert/Update Method - end*/

    /*All Get Method - start*/

    public UserKeyDetailsModel GetUserKeyDetails() {

        UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForUserKeyDetails(), null);

            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                userKeyDetailsModel.PhoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                userKeyDetailsModel.NickName = cursor.getString(cursor.getColumnIndex(COLUMN_NICK_NAME));
                userKeyDetailsModel.UserGuid = cursor.getString(cursor.getColumnIndex(COLUMN_USERGUID));
                userKeyDetailsModel.EmailAddress = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS));
                userKeyDetailsModel.ProfilePicture = cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_PICTURE));
                userKeyDetailsModel.DeviceID = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_ID));
                userKeyDetailsModel.DeviceUniqueID = cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_UNIQUE_ID));
            }
            if (cursor != null)
                cursor.close();
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetUserKeyDetails() : " + ex.getMessage());
        }

        return userKeyDetailsModel;
    }


    public SettingsModel GetSettings(String userID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        SettingsModel settingsModel = new SettingsModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForSettings(userID), null);

            if (cursor.moveToFirst()) {

                settingsModel.UserGuid = cursor.getString(cursor.getColumnIndex(COLUMN_USERGUID));
                settingsModel.ThemeColorCode = cursor.getString(cursor.getColumnIndex(COLUMN_THEME_COLORCODE));

                if (cursor.getInt(cursor.getColumnIndex(COLUMN_IS_DEFAUL_TTHEME)) == 1)
                    settingsModel.IsDefaultTheme = true;
                else
                    settingsModel.IsDefaultTheme = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetSettings() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return settingsModel;
    }

    public DrugModel GetDrugDetails(String drugID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForDrugDetails(drugID), null);

            if (cursor.moveToFirst()) {

                drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                drugModel.DrugMRPString = AppConstants.decimalFormat.format(drugModel.DrugMRP);
                drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                drugModel.DrugDiscount = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugDetails() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModel;
    }


    public ArrayList<DrugModel> GetDrugList(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrug(searchText), null);

            if (cursor.moveToFirst()) {
                DrugModel drugModel = new DrugModel();
                drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                drugModel.DrugMRPString = AppConstants.decimalFormat.format(drugModel.DrugMRP);
                drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                drugModel.DrugDiscount = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));
                drugModelArrayList.add(drugModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugList() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

/*All Get Method - end*/
}
