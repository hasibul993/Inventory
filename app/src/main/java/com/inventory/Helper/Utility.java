package com.inventory.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.widget.CompoundButtonCompat;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.inventory.Model.SliderMenuModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by BookMEds on 01-02-2018.
 */

public class Utility {

    int myear, mmonth, mday, mhour, mmin;

    public static int MergeColors(int backgroundColor, int foregroundColor) {
        final byte ALPHA_CHANNEL = 24;
        final byte RED_CHANNEL = 16;
        final byte GREEN_CHANNEL = 8;
        final byte BLUE_CHANNEL = 0;
        int a = 0, r = 0, g = 0, b = 0;
        try {
            final double ap1 = (double) (backgroundColor >> ALPHA_CHANNEL & 0xff) / 255d;
            final double ap2 = (double) (foregroundColor >> ALPHA_CHANNEL & 0xff) / 255d;
            final double ap = ap2 + (ap1 * (1 - ap2));

            final double amount1 = (ap1 * (1 - ap2)) / ap;
            final double amount2 = amount1 / ap;

            a = ((int) (ap * 255d)) & 0xff;

            r = ((int) (((float) (backgroundColor >> RED_CHANNEL & 0xff) * amount1) +
                    ((float) (foregroundColor >> RED_CHANNEL & 0xff) * amount2))) & 0xff;
            g = ((int) (((float) (backgroundColor >> GREEN_CHANNEL & 0xff) * amount1) +
                    ((float) (foregroundColor >> GREEN_CHANNEL & 0xff) * amount2))) & 0xff;
            b = ((int) (((float) (backgroundColor & 0xff) * amount1) +
                    ((float) (foregroundColor & 0xff) * amount2))) & 0xff;

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
    }


    public void SetRadioButtonColor(RadioButton radioButton, int checkedColor, int uncheckedColor) {
        try {
            int states[][] = {{android.R.attr.state_checked}, {}};
            int colors[] = {checkedColor, uncheckedColor};
            CompoundButtonCompat.setButtonTintList(radioButton, new
                    ColorStateList(states, colors));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void ShowDatePicker(final Activity activity, final TextView dateTimeTV) {
        String presetDate = "";

        try {

            presetDate = dateTimeTV.getText().toString().trim();

            PreSetCalender(presetDate);

            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myear = year;
                    mmonth = monthOfYear;
                    mday = dayOfMonth;
                    updateDate(activity, dateTimeTV);
                }
            }, myear, mmonth, mday);
            //datePickerDialog.setTitle("Start Time");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
            // Date newDate = calendar.getTime();
            //datePickerDialog.getDatePicker().setMinDate(newDate.getTime() - (newDate.getTime() % (24 * 60 * 60 * 1000)));
            datePickerDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateDate(Activity activity, TextView dateTV) {
        String formatDay, formatMonth;
        int monthCount;
        try {

            if (mday < 10) {
                formatDay = "0" + mday;
            } else
                formatDay = String.valueOf(mday);

            monthCount = mmonth + 1;
            if (monthCount < 10) {
                formatMonth = "0" + monthCount;
            } else
                formatMonth = String.valueOf(monthCount);

            String dateFormat = new StringBuilder().append(formatDay).append("-").append(formatMonth).append("-").append(myear).append(" ").toString();

            dateTV.setText(dateFormat);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // TODO Auto-generated method stub
    }


    public void PreSetCalender(String existDate) {
        try {
            Calendar caln = Calendar.getInstance();


            if (!StringUtils.isBlank(existDate)) {
                SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = dFormat.parse(existDate);
                caln.setTime(date);
            }


            myear = caln.get(Calendar.YEAR);
            mmonth = caln.get(Calendar.MONTH);
            mday = caln.get(Calendar.DAY_OF_MONTH);
            mhour = caln.get(Calendar.HOUR_OF_DAY);
            mmin = caln.get(Calendar.MINUTE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // TODO Auto-generated method stub
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


    public static ArrayList GetDrugCategoryList(Context context) {
        ArrayList<String> arrayList = new ArrayList();
        try {
            arrayList.add(context.getString(R.string.tablet));
            arrayList.add(context.getString(R.string.injection));
            arrayList.add(context.getString(R.string.capsules));
            arrayList.add(context.getString(R.string.syrup));
            arrayList.add(context.getString(R.string.cream));
            arrayList.add(context.getString(R.string.miscellaneous));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<SliderMenuModel> GetSliderMenuList(Context context) {
        ArrayList<SliderMenuModel> sliderMenuModels = new ArrayList();
        SliderMenuModel sliderMenuModel;
        try {
            sliderMenuModel = new SliderMenuModel();
            sliderMenuModel.Title = context.getString(R.string.masterDB);
            sliderMenuModel.drawableInt = R.drawable.database_icon;
            sliderMenuModels.add(sliderMenuModel);

            sliderMenuModel = new SliderMenuModel();
            sliderMenuModel.Title = context.getString(R.string.pharmacyDB);
            sliderMenuModel.drawableInt = R.drawable.database_icon;
            sliderMenuModels.add(sliderMenuModel);

            sliderMenuModel = new SliderMenuModel();
            sliderMenuModel.Title = context.getString(R.string.settings);
            sliderMenuModel.drawableInt = R.drawable.settings_black_24dp;
            sliderMenuModels.add(sliderMenuModel);

            sliderMenuModel = new SliderMenuModel();
            sliderMenuModel.Title = context.getString(R.string.share);
            sliderMenuModel.drawableInt = R.drawable.share_black_24dp;
            sliderMenuModels.add(sliderMenuModel);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sliderMenuModels;
    }


    public String CreateID() throws Exception {
        return UUID.randomUUID().toString();
        //return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }


}
