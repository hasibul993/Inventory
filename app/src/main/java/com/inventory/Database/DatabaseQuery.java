package com.inventory.Database;

import android.content.Context;

import com.inventory.Activities.MainActivity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hasib on 05-Feb-18.
 */

public class DatabaseQuery extends DatabaseHelper {

    MainActivity mainActivity = MainActivity.getInstance();

    public DatabaseQuery(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public static String GetQueryForUserKeyDetails() {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_USER_KEY_DETAILS_DB + LIMIT_1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSettings(String userID) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_SETTINGS_DB + WHERE + COLUMN_PHARMACY_ID + " = '" + userID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }


    /*Below are Drugs query*/

    public static String GetQueryForDrugDetailsInInventoryDB(String drugBatchNo, String drugID) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_BATCH_NUMBER + " = '"
                    + drugBatchNo + "' AND " + COLUMN_DRUG_ID + " = '" + drugID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchDrugInInventoryDB(String searchText, boolean isLimit) {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {
            if (!StringUtils.isBlank(searchText)) {
                if (isLimit)
                    query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DRUG_NAME + " like '" + searchText + "%' " + LIMIT_8;
                else
                    query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DRUG_NAME + " like '" + searchText + "%' ";
            } else
                query = SELECT_ALL + TABLE_INVENTORY_DB + ORDER_BY + COLUMN_DRUG_NAME + ALPHABETICAL_OREDER;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchDrugInMasterDB(String searchText) {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_MASTER_DB + WHERE + COLUMN_DRUG_NAME + " like '" + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_MASTER_DB + ORDER_BY + COLUMN_DRUG_NAME + ALPHABETICAL_OREDER;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchDrugManufacturerInMasterDB(String searchText) {
        String query = "";
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_MASTER_DB + WHERE + COLUMN_DRUG_MANUFACTURER + " like '"
                        + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_MASTER_DB + ORDER_BY + COLUMN_DRUG_MANUFACTURER + ALPHABETICAL_OREDER;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForManufacturerInManufacturerDB(String searchText) {
        String query = "";
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_MANUFACTURER_DB + WHERE + COLUMN_DRUG_MANUFACTURER + " like '"
                        + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_MANUFACTURER_DB + ORDER_BY + COLUMN_DRUG_MANUFACTURER + ALPHABETICAL_OREDER;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchDrugManufacturerInPharmacyDB(String searchText) {
        String query = "";
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_ORDERS_DB + WHERE + COLUMN_DRUG_MANUFACTURER + " like '"
                        + searchText + "%' " + LIMIT_8;
            else
                query = SELECT_ALL + TABLE_ORDERS_DB + ORDER_BY + COLUMN_DRUG_MANUFACTURER + ALPHABETICAL_OREDER;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForDrugBetweenDatesInInventoryDB(String searchText, long startInMilliSecond, long endInMilliSecond) {
        String query = "";
        try {

            if (!StringUtils.isBlank(searchText)) {
                query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DRUG_NAME + " like '" + searchText + "%' AND "
                        + COLUMN_DATE_IN_MILLISECOND + " BETWEEN " + startInMilliSecond + " AND "
                        + endInMilliSecond + ORDER_BY + COLUMN_DRUG_NAME + ALPHABETICAL_OREDER;
            } else
                query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DATE_IN_MILLISECOND + " BETWEEN "
                        + startInMilliSecond + " AND " + endInMilliSecond + ORDER_BY + COLUMN_DRUG_NAME
                        + ALPHABETICAL_OREDER;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForExpiredDrugInInventoryDB(String searchText, long today) {
        String query = "";
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DRUG_NAME + " like '" + searchText + "%' AND "
                        + COLUMN_DATE_IN_MILLISECOND + "<=" + today + ORDER_BY + COLUMN_DRUG_NAME
                        + ALPHABETICAL_OREDER;
            else
                query = SELECT_ALL + TABLE_INVENTORY_DB + WHERE + COLUMN_DATE_IN_MILLISECOND + "<=" + today
                        + ORDER_BY + COLUMN_DRUG_NAME + ALPHABETICAL_OREDER;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForIncrement(String drugBatchNo, String drugID, int incrementValue) {
        String query = "";
        try {
            query = "UPDATE " + TABLE_INVENTORY_DB + " SET " + COLUMN_DRUG_QUANTITY + "="
                    + COLUMN_DRUG_QUANTITY + "+" + incrementValue + WHERE + COLUMN_BATCH_NUMBER + "= '"
                    + drugBatchNo + "' AND " + COLUMN_DRUG_ID + "= '" + drugID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForDecrement(String drugBatchNo, String drugID, int incrementValue) {
        String query = "";
        try {
            query = "UPDATE " + TABLE_INVENTORY_DB + " SET " + COLUMN_DRUG_QUANTITY + "="
                    + COLUMN_DRUG_QUANTITY + "-" + incrementValue + WHERE + COLUMN_BATCH_NUMBER + "= '"
                    + drugBatchNo + "' AND " + COLUMN_DRUG_ID + "= '" + drugID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }


    /*Below are Orders query*/

    public static String GetQueryForOrderDetailsInOrderDB(String orderNo) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_ORDERS_DB + WHERE + COLUMN_ORDER_NO + " = '" + orderNo + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForOrderItemDetailsInOrderItemsDB(String orderNo, String drugID) {
        String query = "";
        try {
            query = SELECT_ALL + TABLE_ORDERS_ITEMS_DB + WHERE + COLUMN_ORDER_NO + " = '"
                    + orderNo + "' AND " + COLUMN_DRUG_ID + " = '" + drugID + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForSearchOrderInOrderDB(String searchText) {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {
            if (!StringUtils.isBlank(searchText))
                query = SELECT_ALL + TABLE_ORDERS_DB + WHERE + COLUMN_ORDER_NO + " like '" + searchText + "%' ";
            else
                query = SELECT_ALL + TABLE_ORDERS_DB + ORDER_BY + COLUMN_DATE_IN_MILLISECOND + DESCENDING_OREDER;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

    public static String GetQueryForOrderItemInOrderItemDB(String orderNo) {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {
            query = SELECT_ALL + TABLE_ORDERS_ITEMS_DB + WHERE + COLUMN_ORDER_NO + " = '" + orderNo + "'";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

}
