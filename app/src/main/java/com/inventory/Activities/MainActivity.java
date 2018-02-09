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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by BookMEds on 02-02-2018.
 */

public class MainActivity implements AppConstants {

    public static String URL = "http://xamprdemo.cloudapp.net/";

    public static String THEMECOLOR = "#673ab7";

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

    public void InsertUpdateDrugs(Context context, DrugModel drugModel, boolean isModify) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateDrugs(drugModel, isModify);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void InsertUpdateDrugsInBatch(Context context, ArrayList<DrugModel> drugModelArrayList, boolean isModify) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateDrugsInBatch(drugModelArrayList, isModify);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public DrugModel GetDrugDetails(Context context, String drugBatchNo, String drugID) {
        databaseAccess = new DatabaseAccess(context);
        DrugModel drugModel = new DrugModel();
        try {
            drugModel = databaseAccess.GetDrugDetails(drugBatchNo, drugID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModel;
    }

    public ArrayList<DrugModel> GetDrugList(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetDrugList(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

    public HashMap<String, DrugModel> GetInventoryHashMap(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        HashMap<String, DrugModel> drugModelHashMap = new HashMap<>();
        try {
            drugModelHashMap = databaseAccess.GetInventoryHashMap(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelHashMap;
    }

    public ArrayList<DrugModel> GetDrugManufacturerList(Context context, String searchText) {
        databaseAccess = new DatabaseAccess(context);
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = databaseAccess.GetDrugManufacturerList(searchText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return drugModelArrayList;
    }

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

   /*End - All DB Data insert update fetch*/

    public void SupportActionBar(Activity activity, android.support.v7.app.ActionBar actionBar, String color_primary, TextView toolbarTitleTV, String title, boolean isHomeActivity) {
        String color_dark;
        try {
            color_dark = THEMECOLOR;

            actionBar.setTitle("");

            if (isHomeActivity) {
                Typeface tfaerial = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-MediumItalic.ttf");
                toolbarTitleTV.setTypeface(tfaerial);
                toolbarTitleTV.setText(activity.getString(R.string.app_name));
            } else {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setElevation(0);
                toolbarTitleTV.setTextSize(activity.getResources().getDimension(R.dimen.text));
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

    public String GetCurrentDate() {
        String strDate = "";
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);//dd/MM/yyyy
            Date now = new Date();
            strDate = sdfDate.format(now);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strDate;
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
            InsertUpdateDrugsInBatch(context, entityArrayList, false);
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

   /* public String ReadFileFromRaw(Context context) {
        InputStream iStream;
        ByteArrayOutputStream byteStream = null;

        try {
            iStream = context.getResources().openRawResource(R.raw.team);
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();

            Model model = new Gson().fromJson(byteStream.toString(), Model.class);
            Log.i("", " onCreate : " + "");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteStream.toString();
    }*/
}

