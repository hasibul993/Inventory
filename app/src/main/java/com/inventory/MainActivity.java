package com.inventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.inventory.Database.DatabaseAccess;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Model.DrugModel;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    public void InsertUpdateDrugs(Context context, DrugModel drugModel) {
        databaseAccess = new DatabaseAccess(context);
        try {
            databaseAccess.InsertUpdateDrugs(drugModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public DrugModel GetDrugDetails(Context context, String drugID) {
        databaseAccess = new DatabaseAccess(context);
        DrugModel drugModel = new DrugModel();
        try {
            drugModel = databaseAccess.GetDrugDetails(drugID);
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

    public static ArrayList GetThemeList(Context context) {
        ArrayList hsColors = new ArrayList();
        try {
            hsColors.add(context.getString(R.string.popupcolor_1));
            hsColors.add(context.getString(R.string.popupcolor_2));
            hsColors.add(context.getString(R.string.popupcolor_3));
            hsColors.add(context.getString(R.string.popupcolor_4));
            hsColors.add(context.getString(R.string.popupcolor_5));
            hsColors.add(context.getString(R.string.popupcolor_6));
            hsColors.add(context.getString(R.string.popupcolor_7));
            hsColors.add(context.getString(R.string.popupcolor_8));
            hsColors.add(context.getString(R.string.popupcolor_9));
            hsColors.add(context.getString(R.string.popupcolor_10));
            hsColors.add(context.getString(R.string.popupcolor_11));
            hsColors.add(context.getString(R.string.popupcolor_12));
            hsColors.add(context.getString(R.string.popupcolor_13));
            hsColors.add(context.getString(R.string.popupcolor_14));
            hsColors.add(context.getString(R.string.popupcolor_15));
            hsColors.add(context.getString(R.string.popupcolor_16));
            hsColors.add(context.getString(R.string.popupcolor_17));
            hsColors.add(context.getString(R.string.popupcolor_18));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return hsColors;
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
}
