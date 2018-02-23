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
                values.put(COLUMN_PHARMACY_ID, keyDetailsModel.UserGuid);


            values.put(COLUMN_PROFILE_PICTURE, keyDetailsModel.ProfilePicture);

            if (keyDetailsModel.DeviceID != null)
                values.put(COLUMN_DEVICE_ID, keyDetailsModel.DeviceID);

            if (keyDetailsModel.DeviceUniqueID != null)
                values.put(COLUMN_DEVICE_UNIQUE_ID, keyDetailsModel.DeviceUniqueID);

            long _id = db.insertWithOnConflict(TABLE_USER_KEY_DETAILS_DB, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {
                db.update(TABLE_USER_KEY_DETAILS_DB, values, COLUMN_PHONE_NUMBER + "= '" + keyDetailsModel.PhoneNumber + "'", null);
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

    public void InsertUpdateSettings(SettingsModel settingsModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            values.put(COLUMN_PHARMACY_ID, settingsModel.UserGuid);
            values.put(COLUMN_IS_DEFAUL_TTHEME, settingsModel.IsDefaultTheme);
            values.put(COLUMN_THEME_COLORCODE, settingsModel.ThemeColorCode);

            long _id = db.insertWithOnConflict(TABLE_SETTINGS_DB, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {
                db.update(TABLE_SETTINGS_DB, values, COLUMN_PHARMACY_ID + "= '" + settingsModel.UserGuid + "'", null);
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

    /*below drug insertion*/

    public void InsertUpdateDrugsInInventoryDB(DrugModel drugModel, boolean isModify) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            if (drugModel.BatchNumber != null)
                values.put(COLUMN_BATCH_NUMBER, drugModel.BatchNumber);

            if (drugModel.DrugID != null)
                values.put(COLUMN_DRUG_ID, drugModel.DrugID);

            if (drugModel.PharmacyID != null)
                values.put(COLUMN_PHARMACY_ID, drugModel.PharmacyID);

            if (drugModel.DrugName != null)
                values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

            if (drugModel.DrugCategory != null)
                values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

            if (drugModel.DrugManufacturer != null)
                values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

            values.put(COLUMN_DRUG_MRP, drugModel.DrugMRP);

            values.put(COLUMN_DRUG_QUANTITY, drugModel.DrugQuantity);

            values.put(COLUMN_DRUG_DISCOUNT, drugModel.DrugDiscount);

            if (drugModel.DrugExpiryDate != null) {
                values.put(COLUMN_DRUG_EXPIRY_DATE, drugModel.DrugExpiryDate);
                values.put(COLUMN_DATE_IN_MILLISECOND, mainActivity.GetMilliSecondsFromDate(drugModel.DrugExpiryDate, false));
            }


            if (drugModel.DrugTransactionDate != null)
                values.put(COLUMN_DRUG_TRANSACTION_DATE, drugModel.DrugTransactionDate);


            long _id = db.insertWithOnConflict(TABLE_INVENTORY_DB, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {

                if (values.containsKey(COLUMN_DRUG_QUANTITY) && !isModify)// remove quantity other wise it will update the current quantity -- and then do increment
                    values.remove(COLUMN_DRUG_QUANTITY);

                db.update(TABLE_INVENTORY_DB, values, COLUMN_BATCH_NUMBER + "= '"
                        + drugModel.BatchNumber + "' AND " + COLUMN_DRUG_ID + " = '"
                        + drugModel.DrugID + "'", null);

                if (!isModify)
                    db.execSQL(DatabaseQuery.GetQueryForIncrement(drugModel.BatchNumber, drugModel.DrugID, drugModel.DrugQuantity));
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertUpdateDrugsInInventoryDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertDrugsInBatchInMasterDB(ArrayList<DrugModel> drugModelArrayList) {
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

                    values.put(COLUMN_DRUG_MRP, drugModel.DrugMRP);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);


                    long _id = db.insertWithOnConflict(TABLE_MASTER_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        db.update(TABLE_MASTER_DB, values, COLUMN_DRUG_NAME + "= '"
                                + drugModel.DrugName + "' AND " + COLUMN_DRUG_ID + " = '"
                                + drugModel.DrugID + "'", null);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertDrugsInBatchInMasterDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertUpdateDrugsInBatchInInventoryDB(ArrayList<DrugModel> drugModelArrayList, boolean isModify) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {

                    if (drugModel.BatchNumber != null)
                        values.put(COLUMN_BATCH_NUMBER, drugModel.BatchNumber);

                    if (drugModel.DrugID != null)
                        values.put(COLUMN_DRUG_ID, drugModel.DrugID);

                    if (drugModel.PharmacyID != null)
                        values.put(COLUMN_PHARMACY_ID, drugModel.PharmacyID);

                    if (drugModel.DrugName != null)
                        values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

                    values.put(COLUMN_DRUG_MRP, drugModel.DrugMRP);

                    values.put(COLUMN_DRUG_QUANTITY, drugModel.DrugQuantity);

                    values.put(COLUMN_DRUG_DISCOUNT, drugModel.DrugDiscount);

                    if (drugModel.DrugExpiryDate != null) {
                        values.put(COLUMN_DRUG_EXPIRY_DATE, drugModel.DrugExpiryDate);
                        values.put(COLUMN_DATE_IN_MILLISECOND, mainActivity.GetMilliSecondsFromDate(drugModel.DrugExpiryDate, false));
                    }

                    if (drugModel.DrugTransactionDate != null)
                        values.put(COLUMN_DRUG_TRANSACTION_DATE, drugModel.DrugTransactionDate);

                    long _id = db.insertWithOnConflict(TABLE_INVENTORY_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        if (values.containsKey(COLUMN_DRUG_QUANTITY) && !isModify)// remove quantity other wise it will update the current quantity -- and then do increment
                            values.remove(COLUMN_DRUG_QUANTITY);

                        db.update(TABLE_INVENTORY_DB, values, COLUMN_BATCH_NUMBER + "= '"
                                + drugModel.BatchNumber + "' AND " + COLUMN_DRUG_ID + " = '"
                                + drugModel.DrugID + "'", null);

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
            Log.i(TAG, " InsertUpdateDrugsInBatchInInventoryDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertManufacturerInBatchInManufacturerDB(ArrayList<DrugModel> drugModelArrayList) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {
                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

                    if (drugModel.PharmacyID != null)
                        values.put(COLUMN_PHARMACY_ID, drugModel.PharmacyID);

                    long _id = db.insertWithOnConflict(TABLE_MANUFACTURER_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        db.update(TABLE_MANUFACTURER_DB, values, COLUMN_DRUG_MANUFACTURER + "= '"
                                + drugModel.DrugManufacturer + "'", null);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertUpdateManufacturerInManufacturerDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }


    /*Below order insertion*/

    public void InsertUpdateOrderInOrderDB(DrugModel drugModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            if (drugModel.OrderNo != null)
                values.put(COLUMN_ORDER_NO, drugModel.OrderNo);

            if (drugModel.PharmacyID != null)
                values.put(COLUMN_PHARMACY_ID, drugModel.PharmacyID);

            if (drugModel.OrderCreatedOn != null) {
                values.put(COLUMN_ORDER_CREATED_ON, drugModel.OrderCreatedOn);
                values.put(COLUMN_DATE_IN_MILLISECOND, mainActivity.GetMilliSecondsFromDate(drugModel.OrderCreatedOn, true));
            }

            if (drugModel.CustomerName != null)
                values.put(COLUMN_CUSTOMER_NAME, drugModel.CustomerName);

            if (drugModel.CustomerMobile != null)
                values.put(COLUMN_CUSTOMER_MOBILE, drugModel.CustomerMobile);

            if (drugModel.CustomerName != null)
                values.put(COLUMN_CUSTOMER_NAME, drugModel.CustomerName);

            if (drugModel.PatientName != null)
                values.put(COLUMN_PATIENT_NAME, drugModel.PatientName);

            if (drugModel.Gender != null)
                values.put(COLUMN_GENDER, drugModel.Gender);

            if (drugModel.Age != null)
                values.put(COLUMN_AGE, drugModel.Age);

            values.put(COLUMN_ORDER_TOTAL, drugModel.OrderTotal);

            values.put(COLUMN_DRUG_DISCOUNT, drugModel.OrderTotalDiscountPerc);

            long _id = db.insertWithOnConflict(TABLE_ORDERS_DB, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {

                db.update(TABLE_ORDERS_DB, values, COLUMN_ORDER_NO + "= '"
                        + drugModel.OrderNo + "'", null);

            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertUpdateOrderInOrderDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public void InsertOrderItemsInBatchInOrderItemsDB(ArrayList<DrugModel> drugModelArrayList) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                try {

                    if (drugModel.DrugID != null)
                        values.put(COLUMN_DRUG_ID, drugModel.DrugID);

                    if (drugModel.OrderNo != null)
                        values.put(COLUMN_ORDER_NO, drugModel.OrderNo);

                    if (drugModel.PharmacyID != null)
                        values.put(COLUMN_PHARMACY_ID, drugModel.PharmacyID);

                    if (drugModel.DrugName != null)
                        values.put(COLUMN_DRUG_NAME, drugModel.DrugName);

                    if (drugModel.BatchNumber != null)
                        values.put(COLUMN_BATCH_NUMBER, drugModel.BatchNumber);

                    if (drugModel.DrugCategory != null)
                        values.put(COLUMN_DRUG_CATEGORY, drugModel.DrugCategory);

                    if (drugModel.DrugManufacturer != null)
                        values.put(COLUMN_DRUG_MANUFACTURER, drugModel.DrugManufacturer);

                    values.put(COLUMN_DRUG_MRP, drugModel.DrugMRP);

                    values.put(COLUMN_ORDER_TOTAL, drugModel.DrugTotalMRP);

                    values.put(COLUMN_DRUG_MRP_TOTAL_WITHOUT_DISC, drugModel.DrugTotalMRPWithoutDisc);

                    values.put(COLUMN_DRUG_QUANTITY, drugModel.DrugQuantity);

                    values.put(COLUMN_DRUG_DISCOUNT, drugModel.DrugDiscount);

                    if (drugModel.DrugExpiryDate != null) {
                        values.put(COLUMN_DRUG_EXPIRY_DATE, drugModel.DrugExpiryDate);
                        values.put(COLUMN_DATE_IN_MILLISECOND, mainActivity.GetMilliSecondsFromDate(drugModel.DrugExpiryDate, false));
                    }

                    try {
                        db.execSQL(DatabaseQuery.GetQueryForDecrement(drugModel.BatchNumber, drugModel.DrugID, drugModel.DrugQuantity));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    long _id = db.insertWithOnConflict(TABLE_ORDERS_ITEMS_DB, null,
                            values, SQLiteDatabase.CONFLICT_IGNORE);

                    if (_id == -1) {

                        db.update(TABLE_ORDERS_ITEMS_DB, values, COLUMN_ORDER_NO + "= '"
                                + drugModel.OrderNo + "' AND " + COLUMN_DRUG_ID + " = '"
                                + drugModel.DrugID + "'", null);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertOrderItemsInBatchInOrderItemsDB : " + ex.getMessage());
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
                userKeyDetailsModel.UserGuid = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
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

                settingsModel.UserGuid = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
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

    /*Drug details fetching*/

    public DrugModel GetDrugDetailsFromInventoryDB(String drugBatchNo, String drugID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForDrugDetailsInInventoryDB(drugBatchNo, drugID), null);

            if (cursor.moveToFirst()) {

                drugModel = GetDrugModel(cursor);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugDetailsFromInventoryDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModel;
    }

    public int GetDrugQuantityFromInventoryDB(String drugBatchNo, String drugID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();
        int qty = 0;
        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForDrugDetailsInInventoryDB(drugBatchNo, drugID), null);

            if (cursor.moveToFirst()) {
                drugModel = GetDrugModel(cursor);
                qty = drugModel.DrugQuantity;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugQuantityFromInventoryDB() : " + ex.getMessage());
        }
        if (cursor != null)
            cursor.close();
        db.close();
        return qty;
    }


    public ArrayList<DrugModel> GetInventoryListFromInventoryDB(String searchText, boolean isLimit) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrugInInventoryDB(searchText, isLimit), null);

            if (cursor.getCount() > 0)
                drugModelArrayList = GetDrugList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetInventoryListFromInventoryDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetExpiredDurationInventoryFromInventoryDB(String searchText, String startDate, String endDate) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        long startInMilliSecond, endInMilliSecond;
        try {
            startInMilliSecond = mainActivity.GetMilliSecondsFromDate(startDate, false);
            endInMilliSecond = mainActivity.GetMilliSecondsFromDate(endDate, false);

            cursor = db.rawQuery(DatabaseQuery.GetQueryForDrugBetweenDatesInInventoryDB(searchText, startInMilliSecond, endInMilliSecond), null);

            if (cursor.getCount() > 0)
                drugModelArrayList = GetDrugList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetExpiredDurationInventoryFromInventoryDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetExpiredInventoryFromInventoryDB(String searchText, String startDate) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {
            long milliSeconds = mainActivity.GetMilliSecondsFromDate(startDate, false);
            cursor = db.rawQuery(DatabaseQuery.GetQueryForExpiredDrugInInventoryDB(searchText, milliSeconds), null);

            if (cursor.getCount() > 0)
                drugModelArrayList = GetDrugList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetExpiredInventoryFromInventoryDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetDrugList(Cursor cursor) {

        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {
                        DrugModel drugModel = new DrugModel();

                        drugModel = GetDrugModel(cursor);

                        if (drugModel != null)
                            drugModelArrayList.add(drugModel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugList() : " + ex.getMessage());
        }

        return drugModelArrayList;
    }

    public DrugModel GetDrugModel(Cursor cursor) {

        DrugModel drugModel = new DrugModel();
        try {

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

            drugModel.DateInMilliSecond = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE_IN_MILLISECOND));
            //String date=mainActivity.GetDateFromMilliseconds(drugModel.DateInMilliSecond );
            drugModel.TimeStamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugModel() : " + ex.getMessage());
            drugModel = null;
        }

        return drugModel;
    }

    public ArrayList<DrugModel> GetSearchDrugListFromMasterDB(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrugInMasterDB(searchText), null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    try {
                        DrugModel drugModel = new DrugModel();
                        drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_ID));
                        drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
                        drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
                        drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
                        drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
                        drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));

                        drugModelArrayList.add(drugModel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetSearchDrugList() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetDrugManufacturerListFromManufacturerDB(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        HashMap<String, String> manufacHashMap = new HashMap<>();
        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForManufacturerInManufacturerDB(searchText), null);

            if (cursor.getCount() == 0)
                cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrugManufacturerInMasterDB(searchText), null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    try {
                        DrugModel drugModel = new DrugModel();
                        drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
                        drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));

                        if (!manufacHashMap.containsKey(drugModel.DrugManufacturer)) {
                            manufacHashMap.put(drugModel.DrugManufacturer, "");
                            drugModelArrayList.add(drugModel);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetDrugManufacturerListFromManufacturerDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModelArrayList;
    }

    /*Order details fetching*/

    public DrugModel GetOrderDetailsFromOrderDB(String orderNo) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForOrderDetailsInOrderDB(orderNo), null);

            if (cursor.moveToFirst()) {

                drugModel = GetOrderModel(cursor);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderDetailsFromOrderDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModel;
    }

    public ArrayList<DrugModel> GetOrderListFromOrderDB(String searchText) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> orderList = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchOrderInOrderDB(searchText), null);

            if (cursor.getCount() > 0)
                orderList = GetOrderList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderListFromOrderDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return orderList;
    }

    public DrugModel GetOrderItemDetailsFromOrderItemDB(String orderNo, String drugID) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        DrugModel drugModel = new DrugModel();

        try {
            cursor = db.rawQuery(DatabaseQuery.GetQueryForOrderItemDetailsInOrderItemsDB(orderNo, drugID), null);

            if (cursor.moveToFirst()) {

                drugModel = GetOrderItemModel(cursor);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderItemDetailsFromOrderItemDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return drugModel;
    }

    public ArrayList<DrugModel> GetOrderItemListFromOrderItemDB(String orderNo) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> orderList = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForOrderItemInOrderItemDB(orderNo), null);

            if (cursor.getCount() > 0)
                orderList = GetOrderItemsList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderItemListFromOrderItemDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return orderList;
    }

    public ArrayList<DrugModel> GetOrderList(Cursor cursor) {

        ArrayList<DrugModel> orderArrayList = new ArrayList<>();

        try {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {
                        DrugModel drugModel = new DrugModel();

                        drugModel = GetOrderModel(cursor);

                        if (drugModel != null)
                            orderArrayList.add(drugModel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderList() : " + ex.getMessage());
        }

        return orderArrayList;
    }

    public DrugModel GetOrderModel(Cursor cursor) {

        DrugModel drugModel = new DrugModel();
        try {
            drugModel.OrderNo = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_NO));
            drugModel.PharmacyID = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
            drugModel.OrderCreatedOn = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_CREATED_ON));

            drugModel.CustomerName = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME));
            drugModel.CustomerMobile = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_MOBILE));
            drugModel.PatientName = cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_NAME));
            drugModel.Gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
            drugModel.Age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));

            drugModel.OrderTotal = cursor.getDouble(cursor.getColumnIndex(COLUMN_ORDER_TOTAL));
            drugModel.OrderTotalString = AppConstants.decimalFormatTwoPlace.format(drugModel.OrderTotal);

            drugModel.OrderTotalDiscountPerc = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
            drugModel.OrderTotalDiscountString = AppConstants.decimalFormatTwoPlace.format(drugModel.OrderTotalDiscountPerc);

            drugModel.DateInMilliSecond = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE_IN_MILLISECOND));

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderModel() : " + ex.getMessage());
            drugModel = null;
        }

        return drugModel;
    }

    public ArrayList<DrugModel> GetOrderItemsList(Cursor cursor) {

        ArrayList<DrugModel> orderItemsArrayList = new ArrayList<>();

        try {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {
                        DrugModel drugModel = new DrugModel();

                        drugModel = GetOrderItemModel(cursor);

                        if (drugModel != null)
                            orderItemsArrayList.add(drugModel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderItemsList() : " + ex.getMessage());
        }

        return orderItemsArrayList;
    }

    public DrugModel GetOrderItemModel(Cursor cursor) {

        DrugModel drugModel = new DrugModel();
        try {
            drugModel.OrderNo = cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_NO));
            drugModel.DrugID = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
            drugModel.BatchNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NUMBER));
            drugModel.DrugName = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_NAME));
            drugModel.PharmacyID = cursor.getString(cursor.getColumnIndex(COLUMN_PHARMACY_ID));
            drugModel.DrugCategory = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_CATEGORY));
            drugModel.DrugMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP));
            drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
            drugModel.DrugTotalMRP = cursor.getDouble(cursor.getColumnIndex(COLUMN_ORDER_TOTAL));
            drugModel.DrugTotalMRPWithoutDisc = cursor.getDouble(cursor.getColumnIndex(COLUMN_DRUG_MRP_TOTAL_WITHOUT_DISC));
            drugModel.DrugTotalMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugTotalMRP);

            drugModel.DrugQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_DRUG_QUANTITY));
            drugModel.DrugDiscount = cursor.getFloat(cursor.getColumnIndex(COLUMN_DRUG_DISCOUNT));
            drugModel.DrugDiscountString = AppConstants.decimalFormatOnePlace.format(drugModel.DrugDiscount);
            drugModel.DrugManufacturer = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_MANUFACTURER));
            drugModel.DrugExpiryDate = cursor.getString(cursor.getColumnIndex(COLUMN_DRUG_EXPIRY_DATE));
            drugModel.DateInMilliSecond = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE_IN_MILLISECOND));

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetOrderItemModel() : " + ex.getMessage());
            drugModel = null;
        }

        return drugModel;
    }

    /*Fetch customer details from order db*/

    public ArrayList<DrugModel> GetCustomerMobileListFromOrderDB(String searchText, boolean isLimit) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchCustomerMobileInOrderDB(searchText, isLimit), null);

            if (cursor.getCount() > 0)
                drugModelArrayList = GetOrderList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetCustomerMobileListFromOrderDB() : " + ex.getMessage());
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
            cursor = db.rawQuery(DatabaseQuery.GetQueryForSearchDrugInInventoryDB(null, false), null);
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
