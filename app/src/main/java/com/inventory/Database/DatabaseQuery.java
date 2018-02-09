package com.inventory.Database;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hasib on 05-Feb-18.
 */

public class DatabaseQuery extends DatabaseHelper {

    public DatabaseQuery(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public static String GetQueryForUserKeyDetails() {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_USER_KEY_DETAILS + " LIMIT 1 ";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSettings(String userID) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_SETTINGS + " Where " + COLUMN_USERGUID + " = '" + userID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForDrugDetails(String drugBatchNo, String drugID) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_PRODUCTS + " Where " + COLUMN_BATCH_NUMBER + " = '"
                    + drugBatchNo + "' AND " + COLUMN_DRUG_ID + " = '" + drugID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchDrug(String searchText) {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_PRODUCTS + " where " + COLUMN_DRUG_NAME + " like '" + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_PRODUCTS + ORDER_BY + COLUMN_DRUG_NAME + ALPHABETICAL_OREDER;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }


    public static String GetQueryForSearchDrugMnaufacturer(String searchText) {
        String query = "";
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_PRODUCTS + " where " + COLUMN_DRUG_MANUFACTURER + " like '"
                        + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_PRODUCTS + ORDER_BY + COLUMN_DRUG_MANUFACTURER + ALPHABETICAL_OREDER;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForIncrement(String drugBatchNo, String drugName, int incrementValue) {
        String query = "";
        try {
            query = "UPDATE " + TABLE_PRODUCTS + " SET " + COLUMN_DRUG_QUANTITY + "="
                    + COLUMN_DRUG_QUANTITY + "+" + incrementValue + " WHERE " + COLUMN_BATCH_NUMBER + "= '"
                    + drugBatchNo + "' AND " + COLUMN_DRUG_NAME + "= '" + drugName + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

}
