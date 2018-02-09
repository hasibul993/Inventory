package com.inventory.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inventory.Activities.MainActivity;
import com.inventory.Helper.AppConstants;
import com.inventory.Model.DrugModel;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hasib on 05-02-2018.
 */

public class DatabaseAccess extends DatabaseHelper {

    private static String TAG = "DatabaseAccess";

    MainActivity mainActivity;

    public DatabaseAccess(Context context) {
        super(context);
        mainActivity = MainActivity.getInstance();
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

    public void InsertUpdateDrugsInBatch(ArrayList<DrugModel> drugModelArrayList, boolean isModify) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {

                    if (drugModel.DrugID != null)
                        values.put(COLUMN_DRUG_ID, drugModel.DrugID);

                    if (drugModel.DrugName != null)
                        values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

                    if (drugModel.BatchNumber != null)
                        values.put(COLUMN_BATCH_NUMBER, drugModel.BatchNumber);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

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

                        if (values.containsKey(COLUMN_DRUG_QUANTITY) && !isModify)// remove quantity other wise it will update the current quantity -- and then do increment
                            values.remove(COLUMN_DRUG_QUANTITY);

                        // db.update(TABLE_PRODUCTS, values, COLUMN_DRUG_ID + "= '" + drugModel.DrugID + "'", null);

                        db.update(TABLE_PRODUCTS, values, COLUMN_BATCH_NUMBER + "= '"
                                + drugModel.BatchNumber + "' AND " + COLUMN_DRUG_ID + " = '"
                                + drugModel.DrugName + "'", null);

                        if (!isModify)
                            db.execSQL(DatabaseQuery.GetQueryForIncrement(drugModel.BatchNumber, drugModel.DrugName, drugModel.DrugQuantity));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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


    public void InsertUpdateDrugs(DrugModel drugModel, boolean isModify) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            if (drugModel.DrugID != null)
                values.put(COLUMN_DRUG_ID, drugModel.DrugID);

            if (drugModel.DrugName != null)
                values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

            if (drugModel.DrugCategory != null)
                values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

            if (drugModel.DrugManufacturer != null)
                values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

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

                if (values.containsKey(COLUMN_DRUG_QUANTITY) && !isModify)// remove quantity other wise it will update the current quantity -- and then do increment
                    values.remove(COLUMN_DRUG_QUANTITY);

                // db.update(TABLE_PRODUCTS, values, COLUMN_DRUG_ID + "= '" + drugModel.DrugID + "'", null);

                db.update(TABLE_PRODUCTS, values, COLUMN_BATCH_NUMBER + "= '"
                        + drugModel.BatchNumber + "' AND " + COLUMN_DRUG_ID + " = '"
                        + drugModel.DrugName + "'", null);

                if (!isModify)
                    db.execSQL(DatabaseQuery.GetQueryForIncrement(drugModel.BatchNumber, drugModel.DrugName, drugModel.DrugQuantity));
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

    public void InsertMasterDBDrugsInBatch(ArrayList<DrugModel> drugModelArrayList) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {

                    if (drugModel.DrugID != null)
                        values.put(COLUMN_DRUG_ID, drugModel.DrugID);

                    if (drugModel.DrugName != null)
                        values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);


                    long _id = db.insertWithOnConflict(TABLE_MASTER_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        db.update(TABLE_MASTER_DB, values, COLUMN_DRUG_NAME + "= '"
                                + drugModel.DrugName + "'", null);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertMasterDBDrugsInBatch : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertPharmacyDBDrugsInBatch(ArrayList<DrugModel> drugModelArrayList) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {

                    if (drugModel.DrugID != null)
                        values.put(COLUMN_DRUG_ID, drugModel.DrugID);

                    if (drugModel.DrugName != null)
                        values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);


                    long _id = db.insertWithOnConflict(TABLE_PHARMACY_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        db.update(TABLE_PHARMACY_DB, values, COLUMN_DRUG_NAME + "= '"
                                + drugModel.DrugName + "'", null);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertMasterDBDrugsInBatch : " + ex.getMessage());
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

    public DrugModel GetDrugDetails(String drugBatchNo, String drugID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForDrugDetails(drugBatchNo, drugID), null);

            if (cursor.moveToFirst()) {

                drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_ID));
                drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
                drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                drugModel.DrugDiscount = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                drugModel.DrugDiscountString = AppConstants.decimalFormatOnePlace.format(drugModel.DrugDiscount);
                drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));
                drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
                drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));
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
                while (!cursor.isAfterLast()) {
                    DrugModel drugModel = new DrugModel();
                    drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_ID));
                    drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                    drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                    drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                    drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
                    drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                    drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                    drugModel.DrugDiscount = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                    drugModel.DrugDiscountString = AppConstants.decimalFormatOnePlace.format(drugModel.DrugDiscount);
                    drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));
                    drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
                    drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));

                    drugModelArrayList.add(drugModel);

                    cursor.moveToNext();
                }

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


    public HashMap<String, DrugModel> GetInventoryHashMap(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        HashMap<String, DrugModel> drugModelHashMap = new HashMap<>();
        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrug(searchText), null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    DrugModel drugModel = new DrugModel();
                    drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_ID));
                    drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                    drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                    drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                    drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
                    drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                    drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                    drugModel.DrugDiscount = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                    drugModel.DrugDiscountString = AppConstants.decimalFormatOnePlace.format(drugModel.DrugDiscount);
                    drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));
                    drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
                    drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));


                    String uniqueKey = mainActivity.GetUniqueKey(drugModel.DrugID, drugModel.BatchNumber);

                    if (!drugModelHashMap.containsKey(uniqueKey))
                        drugModelHashMap.put(uniqueKey, drugModel);

                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetInventoryList() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();

        return drugModelHashMap;
    }


    public ArrayList<DrugModel> GetDrugManufacturerList(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        HashMap<String, String> manufacHashMap = new HashMap<>();
        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrugMnaufacturer(searchText), null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    DrugModel drugModel = new DrugModel();
                    drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_ID));
                    drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
                    drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                    drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                    drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
                    drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
                    drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
                    drugModel.DrugDiscount = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
                    drugModel.DrugDiscountString = AppConstants.decimalFormatOnePlace.format(drugModel.DrugDiscount);
                    drugModel.DrugTransactionDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_TRANSACTION_DATE));
                    drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
                    drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));

                    if (!manufacHashMap.containsKey(drugModel.DrugManufacturer)) {
                        manufacHashMap.put(drugModel.DrugManufacturer, "");
                        drugModelArrayList.add(drugModel);
                    }

                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugManufacturerList() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }


    public boolean IsAnyMedicineExist() {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrug(null), null);
            if (cursor.getCount() > 1)
                isExist = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " IsAnyMedicineExist() : " + ex.getMessage());
        }
        if (cursor != null)
            cursor.close();
        db.close();
        return isExist;
    }

/*All Get Method - end*/
}
