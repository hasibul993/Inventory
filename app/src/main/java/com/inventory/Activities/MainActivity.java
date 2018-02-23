package com.inventory.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inventory.Database.DatabaseAccess;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Model.DrugModel;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by BookMEds on 02-02-2018.
 */

public class MainActivity implements AppConstants {

    public static String THEMECOLOR = "#263238";

    private static String TAG = "MainActivity";

    DatabaseAccess databaseAccess;

    private static final MainActivity instance = new MainActivity();


    //private constructor to avoid client applications to use constructor
    private MainActivity() {
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public static String GetThemeColor() {
        return THEMECOLOR;
    }

    public static void SetThemeColor(String color) {
        THEMECOLOR = color;
    }

    public static int GetThemeColorInt() {
        int colorInt = 0;
        try {
            colorInt = Color.parseColor(THEMECOLOR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return colorInt;
    }

    /*Start - All DB Data insert update fetch*/

    public void InsertUpdateSettings(Context context, SettingsModel settingsModel) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateSettings(settingsModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void InsertUpdateUserKeyDetails(Context context, UserKeyDetailsModel userKeyDetailsModel) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateUserKeyDetails(userKeyDetailsModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public SettingsModel GetSettings(Context context, String userID) {
        databaseAccess = new DatabaseAccess(context);
        SettingsModel settingsModel = new SettingsModel();
        try {
            settingsModel = databaseAccess.GetSettings(userID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return settingsModel;

    }

    public UserKeyDetailsModel GetUserKeyDetails(Context context) {
        databaseAccess = new DatabaseAccess(context);
        UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
        try {
            userKeyDetailsModel = databaseAccess.GetUserKeyDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userKeyDetailsModel;

    }

    /*Below are drug insertion and fetching*/

    public void InsertUpdateDrugsInInventoryDB(Context context, DrugModel drugModel, boolean isModify) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateDrugsInInventoryDB(drugModel, isModify);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void InsertUpdateDrugsInBatchInInventoryDB(Context context, ArrayList<DrugModel> drugModelArrayList, boolean isModify) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateDrugsInBatchInInventoryDB(drugModelArrayList, isModify);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void InsertDrugsInBatchInMasterDB(Context context, ArrayList<DrugModel> drugModelArrayList) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertDrugsInBatchInMasterDB(drugModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InsertDrugsInMasterDB(Context context, DrugModel drugModel) {
        try {
            ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
            drugModelArrayList.add(drugModel);
            InsertDrugsInBatchInMasterDB(context, drugModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertDrugsInMasterDB : " + ex.getMessage());
        }
    }

    public void InsertManufacturerInManufacturerDB(Context context, DrugModel drugModel) {
        try {
            ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
            drugModelArrayList.add(drugModel);
            InsertManufacturerInBatchInManufacturerDB(context, drugModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertManufacturerInManufacturerDB : " + ex.getMessage());
        }
    }

    public void InsertManufacturerInBatchInManufacturerDB(Context context, ArrayList<DrugModel> drugModelArrayList) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertManufacturerInBatchInManufacturerDB(drugModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DrugModel GetDrugDetailsFromInventoryDB(Context context, String drugBatchNo, String drugID) {
        databaseAccess = new DatabaseAccess(context);
        DrugModel drugModel = new DrugModel();
        try {
            drugModel = databaseAccess.GetDrugDetailsFromInventoryDB(drugBatchNo, drugID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModel;
    }

    public int GetDrugQuantityFromInventoryDB(Context context, String drugBatchNo, String drugID) {
        databaseAccess = new DatabaseAccess(context);
        int qty = 0;
        try {
            qty = databaseAccess.GetDrugQuantityFromInventoryDB(drugBatchNo, drugID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return qty;
    }

    public ArrayList<DrugModel> GetInventoryListFromInventoryDB(Context context, String searchText, boolean isLimit) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetInventoryListFromInventoryDB(searchText, isLimit);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetExpiredDurationInventoryFromInventoryDB(Context context, String searchText, int month) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        String startDate, endDate;
        try {
            startDate = GetCurrentDate(AppConstants.SIMPLE_DATE_FORMAT);
            endDate = GetDatePlusOneMonth(month);

            drugModelArrayList = databaseAccess.GetExpiredDurationInventoryFromInventoryDB(searchText, startDate, endDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetExpiredInventoryFromInventoryDB(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            String startDate = GetCurrentDate(AppConstants.SIMPLE_DATE_FORMAT);
            drugModelArrayList = databaseAccess.GetExpiredInventoryFromInventoryDB(searchText, startDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetSearchDrugListFromMasterDB(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetSearchDrugListFromMasterDB(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public ArrayList<DrugModel> GetDrugManufacturerListFromManufacturerDB(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetDrugManufacturerListFromManufacturerDB(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    /*Bwlow are Order and Details insertion/Fetching*/

    public void InsertUpdateOrderInOrderDB(Context context, DrugModel drugModel) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateOrderInOrderDB(drugModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void InsertOrderItemsInBatchInOrderItemsDB(Context context, ArrayList<DrugModel> drugModelArrayList) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertOrderItemsInBatchInOrderItemsDB(drugModelArrayList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public DrugModel GetOrderDetailsFromOrderDB(Context context, String orderNo) {
        databaseAccess = new DatabaseAccess(context);
        DrugModel drugModel = new DrugModel();
        try {
            drugModel = databaseAccess.GetOrderDetailsFromOrderDB(orderNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModel;
    }

    public ArrayList<DrugModel> GetOrderListFromOrderDB(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetOrderListFromOrderDB(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public DrugModel GetOrderItemDetailsFromOrderItemDB(Context context, String orderNo, String drugID) {
        databaseAccess = new DatabaseAccess(context);
        DrugModel drugModel = new DrugModel();
        try {
            drugModel = databaseAccess.GetOrderItemDetailsFromOrderItemDB(orderNo, drugID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModel;
    }

    public ArrayList<DrugModel> GetOrderItemListFromOrderItemDB(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetOrderItemListFromOrderItemDB(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    /*Below is for getting customer details based on mobile no*/

    public ArrayList<DrugModel> GetCustomerMobileListFromOrderDB(Context context, String searchText, boolean isLimit) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> customerArrayList = new ArrayList<>();
        try {
            customerArrayList = databaseAccess.GetCustomerMobileListFromOrderDB(searchText, isLimit);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customerArrayList;
    }


    /*End - All DB Data insert update fetch*/


    public boolean IsAnyMedicineExist(Context context) {
        databaseAccess = new DatabaseAccess(context);
        boolean isExist = false;
        try {
            isExist = databaseAccess.IsAnyMedicineExist();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isExist;
    }

    public void SupportActionBar(Activity activity, android.support.v7.app.ActionBar actionBar, String color_primary, TextView toolbarTitleTV, String title, boolean isHomeActivity) {
        String color_dark;
        try {
            color_dark = THEMECOLOR;

            actionBar.setTitle("");

            if (isHomeActivity) {
                Typeface tfaerial = Typeface.createFromAsset(activity.getAssets(), AppConstants.ROBOTO_MEDIUM_ITALIC);
                toolbarTitleTV.setTypeface(tfaerial);
                toolbarTitleTV.setText(title);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setElevation(0);
                toolbarTitleTV.setTextSize(activity.getResources().getDimension(R.dimen.body));
                try {
                    final Drawable upArrow = activity.getResources().getDrawable(R.drawable.vector_back_white_icon);
                    upArrow.setColorFilter(activity.getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
                    actionBar.setHomeAsUpIndicator(upArrow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            toolbarTitleTV.setText(title);

            try {
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color_primary)));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                int color = Utility.MergeColors(Color.parseColor(color_primary), Color.parseColor("#33000000"));
                color_dark = String.format("#%06X", (0xFFFFFF & color));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(color_dark));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int GetColorCodePosition(String colorcode, ArrayList colorslist) {
        int itemPosition = 1;
        try {

            if (StringUtils.isBlank(colorcode))
                colorcode = MainActivity.GetThemeColor();

            boolean isMatched = colorslist.contains(colorcode.toLowerCase());
            if (isMatched) {
                itemPosition = colorslist.indexOf(colorcode.toLowerCase());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return itemPosition;
    }

    public static void ShowToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void RefreshScreen(Activity activity) {
        try {

            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra(activity.getString(R.string.tabPosition), 1);
            activity.finish();
            activity.overridePendingTransition(0, 0);
            activity.startActivity(intent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void MinimizeActivity(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String GetCurrentDate(String SIMPLE_DATE_FORMAT) {
        String strDate = "";
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.ENGLISH);//dd/MM/yyyy
            Date now = new Date();
            strDate = sdfDate.format(now);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strDate;
    }

    public String GetDatePlusOneMonth(int month) {
        String newDate = "";
        try {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, month);

            SimpleDateFormat sdfDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.ENGLISH);//dd/MM/yyyy
            Date now = cal.getTime();
            newDate = sdfDate.format(now);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDate;
    }

    public static long GetMilliSecondsFromDate(String date, boolean isDateTime) {
        long timeInMilliseconds = 0;
        SimpleDateFormat sdfDate;
        try {
            if (isDateTime)
                sdfDate = new SimpleDateFormat(SIMPLE_DATE_TIME_FORMAT, Locale.ENGLISH);//dd-MM-yyyy , h:mm a
            else
                sdfDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.ENGLISH);//dd/MM/yyyy

            try {
                Date mDate = sdfDate.parse(date);
                timeInMilliseconds = mDate.getTime();
                Log.i(TAG, " " + timeInMilliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String GetDateFromMilliseconds(long milliSeconds) {
        String date = "";
        try {
            // Create a DateFormatter object for displaying date in specified format.
            SimpleDateFormat sdfDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.ENGLISH);//dd/MM/yyyy

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            date = sdfDate.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public String GetUniqueKey(String drugID, String batchNumber) {
        String key = "";
        try {
            key = drugID + "_@_" + batchNumber;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return key;
    }

    private String ReadFileFromRawDirectory(Context context) {
        InputStream iStream;
        ByteArrayOutputStream byteStream = null;

        try {
            iStream = context.getResources().openRawResource(R.raw.medicinelist);
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteStream.toString();
    }

    public ArrayList<DrugModel> GetDrugModelList(String rawData) {
        List<DrugModel> entityList = null;
        Gson gson = new Gson();
        ArrayList<DrugModel> entityArrayList = new ArrayList<DrugModel>();
        try {
            Type typeOfObjectsList = new TypeToken<ArrayList<DrugModel>>() {
            }.getType();
            entityList = gson.fromJson(rawData, typeOfObjectsList);

            entityArrayList = new ArrayList<>(entityList.size());
            entityArrayList.addAll(entityList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entityArrayList;
    }

    public void InsertDrugsFromRawDirectory(Context context) {
        ArrayList<DrugModel> entityArrayList = new ArrayList<DrugModel>();
        String rawData = "";
        try {
            rawData = ReadFileFromRawDirectory(context);
            entityArrayList = GetDrugModelList(rawData);
            InsertDrugsInBatchInMasterDB(context, entityArrayList);
            InsertManufacturerInBatchInManufacturerDB(context, entityArrayList);
            InsertUpdateDrugsInBatchInInventoryDB(context, entityArrayList, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<DrugModel> GetDrugModelListFromHashMap(HashMap<String, DrugModel> drugModelHashMap) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<DrugModel>();
        try {
            Set<String> keys = drugModelHashMap.keySet();
            for (String key : keys) {
                try {
                    drugModelArrayList.add(drugModelHashMap.get(key));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public HashMap<String, DrugModel> GetDrugHashMap(ArrayList<DrugModel> drugModelArrayList) {

        HashMap<String, DrugModel> drugModelHashMap = new HashMap<>();
        try {

            for (DrugModel drugModel : drugModelArrayList) {
                String uniqueKey;
                try {
                    uniqueKey = GetUniqueKey(drugModel.DrugID, drugModel.BatchNumber);
                    if (!drugModelHashMap.containsKey(uniqueKey))
                        drugModelHashMap.put(uniqueKey, drugModel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return drugModelHashMap;
    }

    public int GetHashMapPosition(HashMap<String, DrugModel> drugModelHashMap, String uniqueKey) {
        int position = -1;
        try {
            List<String> keyList = new ArrayList<String>(drugModelHashMap.keySet()); // <== Set to List
            if (keyList.contains(uniqueKey))
                position = keyList.indexOf(uniqueKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return position;
    }

    public int GetItemPosition(ArrayList<DrugModel> drugModelArrayList, String drugID, String batchNumber) {
        try {
            for (int i = 0; i < drugModelArrayList.size(); ++i) {
                if (StringUtils.equalsIgnoreCase(drugModelArrayList.get(i).DrugID, drugID)
                        && StringUtils.equalsIgnoreCase(drugModelArrayList.get(i).BatchNumber, batchNumber))
                    return i;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

}

