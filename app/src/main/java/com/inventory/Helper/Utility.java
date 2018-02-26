package com.inventory.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CompoundButtonCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.inventory.Activities.HomeActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.Model.DrugModel;
import com.inventory.Model.HomeModel;
import com.inventory.Model.SliderMenuModel;
import com.inventory.Operation.LoadBitmapFromURL;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by BookMEds on 01-02-2018.
 */

public class Utility {

    int myear, mmonth, mday, mhour, mmin;
    private static final Utility instance = new Utility();

    //private constructor to avoid client applications to use constructor
    private Utility() {
    }

    public static Utility getInstance() {
        return instance;
    }

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

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        try {
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            final float totalPixels = width * height;
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inSampleSize;
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
            sliderMenuModel.Title = context.getString(R.string.inventoryDB);
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

    public static ArrayList<HomeModel> GetHomeMenuList(Context context) {
        ArrayList<HomeModel> homeModelArrayList = new ArrayList();
        HomeModel homeModel;
        try {
            homeModel = new HomeModel();
            homeModel.Title = context.getString(R.string.inventory);
            homeModelArrayList.add(homeModel);

            homeModel = new HomeModel();
            homeModel.Title = context.getString(R.string.pos);
            homeModelArrayList.add(homeModel);

            homeModel = new HomeModel();
            homeModel.Title = context.getString(R.string.orders);
            homeModelArrayList.add(homeModel);

            /*homeModel = new HomeModel();
            homeModel.Title = context.getString(R.string.report);
            homeModelArrayList.add(homeModel);*/


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return homeModelArrayList;
    }

    public ArrayList<HashMap> GetDrugIcon(Context context) {
        ArrayList<HashMap> hashMapList = new ArrayList<>();
        DrugModel drugModel;
        try {
            HashMap hashMap = new HashMap();
            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.tablets_icon;
            drugModel.DrugCategory = context.getString(R.string.tablet);
            hashMap.put(0, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.capsules_icon;
            drugModel.DrugCategory = context.getString(R.string.capsules);
            hashMap.put(1, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.injection_icon;
            drugModel.DrugCategory = context.getString(R.string.injection);
            hashMap.put(2, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.cream_icon;
            drugModel.DrugCategory = context.getString(R.string.cream);
            hashMap.put(3, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.syrup_icon;
            drugModel.DrugCategory = context.getString(R.string.syrup);
            hashMap.put(4, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.drops_icon;
            drugModel.DrugCategory = context.getString(R.string.drops);
            hashMap.put(5, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.liquid_icon;
            drugModel.DrugCategory = context.getString(R.string.liquid);
            hashMap.put(6, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.ointment_icon;
            drugModel.DrugCategory = context.getString(R.string.ointment);
            hashMap.put(7, drugModel);

            drugModel = new DrugModel();
            drugModel.DrugIcon = R.drawable.stocking;
            drugModel.DrugCategory = context.getString(R.string.miscellaneous);
            hashMap.put(8, drugModel);

            hashMapList.add(hashMap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hashMapList;
    }


    public String CreateID() throws Exception {
        return UUID.randomUUID().toString();
        //return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public String CreateOrderID() throws Exception {
        String orderID = "";
        int digits = 7;// it will create a 7 digits no
        try {
            int max = (int) Math.pow(10, (digits)) - 1; //for digits =7, max will be 9999999
            int min = (int) Math.pow(10, digits - 1); //for digits = 7, min will be 1000000
            int range = max - min; //This is 8999999
            Random r = new Random();
            int x = r.nextInt(range);// This will generate random integers in range 0 - 8999999
            int nDigitRandomNo = x + min; //Our random rumber will be any random number x + min
            orderID = String.valueOf(nDigitRandomNo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orderID;
    }


    public boolean IsInternetConnected(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                    }
                }
            }
        }
        return isConnected;
    }


    public String SaveImageGallery(Context context, Bitmap bitmap, String filename, String imgActualPath) {
        File file = null, galleryDirectory, directory;
        String root;
        byte[] byteArrayData;
        Bitmap localBitMap;
        FileOutputStream fos;
        boolean isGallery = false;
        try {
            root = Environment.getExternalStorageDirectory().toString();
            directory = new File(root + "/" + context.getString(R.string.app_name));
            if (!directory.exists())
                directory.mkdirs();

            galleryDirectory = directory.getParentFile();

            galleryDirectory = new File(directory + "/" + context.getString(R.string.app_name) + " Images");


            if (!galleryDirectory.exists())
                galleryDirectory.mkdirs();

            if (filename != null) {
                if (filename.toString().endsWith(".jpg")
                        || filename.toString().endsWith(".png")
                        || filename.toString().endsWith(".jpeg")) {

                    file = new File(galleryDirectory, filename);
                    isGallery = true;
                }
            }
            if (file != null && file.exists())
                file.delete();
            try {
                if (file != null) {
                    fos = new FileOutputStream(file, false);
                    if (isGallery) {
                        if (imgActualPath != null) {
                            localBitMap = BitmapFactory.decodeFile(imgActualPath);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            localBitMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byteArrayData = stream.toByteArray();
                            fos.write(byteArrayData);
                        } else
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
                    }
                    fos.flush();
                    fos.close();
                    return galleryDirectory + "/" + filename;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return "";
    }


    public Bitmap GetBitmapFromURI(Context context, String picURI) {
        Bitmap bitmap = null;
        try {
            String actualFileName = picURI.substring(picURI.lastIndexOf("/") + 1);
            LoadBitmapFromURL loadBitmapFromURL = new LoadBitmapFromURL(picURI);
            boolean isLive = IsInternetConnected(context);
            if (isLive) {
                bitmap = loadBitmapFromURL.execute().get();
                SaveImageGallery(context, bitmap, actualFileName, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitmap;
    }

    public void SetFabColor(Context context, FloatingActionButton floatingActionButton) {
        try {
            if (Build.VERSION.SDK_INT <= 22) {
                floatingActionButton.getBackground().setColorFilter(Color.parseColor(MainActivity.GetThemeColor()), PorterDuff.Mode.SRC_ATOP);
            } else {
                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(MainActivity.GetThemeColor())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void HideSoftKeyboard(Context context, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            // inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void ShowSoftKeyboard(Context context, EditText editText) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void HideDialogSoftKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(0, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void OpenDialogSoftKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static final boolean isValidPhoneNumber(String phone) {
        try {
            if (phone.length() != 10) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void SetImageTint(ImageView imageView) {
        try {
            if (Build.VERSION.SDK_INT <= 22) {
                imageView.getBackground().setColorFilter(Color.parseColor(MainActivity.GetThemeColor()), PorterDuff.Mode.SRC_ATOP);
            } else {
                imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(MainActivity.GetThemeColor())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
