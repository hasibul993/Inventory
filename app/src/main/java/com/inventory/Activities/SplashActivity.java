package com.inventory.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;

import com.inventory.Helper.Utility;

import com.inventory.Login.AppSettings;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.R;


import org.apache.commons.lang3.StringUtils;


public class SplashActivity extends AppCompatActivity {


    private String TAG = "SplashActivity";

    MainActivity mainActivity = MainActivity.getInstance();
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    SettingsModel settingsModel = new SettingsModel();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(SplashActivity.this);
        settingsModel = mainActivity.GetSettings(this, userKeyDetailsModel.UserGuid);

        if (!settingsModel.IsDefaultTheme && !StringUtils.isBlank(settingsModel.ThemeColorCode))
            MainActivity.SetThemeColor(settingsModel.ThemeColorCode);

        SupportActionBar(this);

        startAnimating();
    }

    // Helper method to start the animation on the splash screen

    private void startAnimating() {
        try {
            // Load animations for all views within the TableLayout
            Animation spinin = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            LayoutAnimationController controller = new LayoutAnimationController(spinin);
            RelativeLayout table = (RelativeLayout) findViewById(R.id.splashLayout);
            table.setLayoutAnimation(controller);
            // Transition to Main Menu when bottom title finishes animating
            spinin.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationEnd(Animation animation) {
                    // The animation has ended, transition to the Main Menu screen
                    if (!StringUtils.isBlank(userKeyDetailsModel.PhoneNumber)) {
                        HomeActivity.GotoHomeActivity(SplashActivity.this);
                    } else {
                        AppSettings.GotoAppSettings(SplashActivity.this);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*finish();
            System.exit(0);*/
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void SupportActionBar(Activity activity) {
        String color_dark;
        try {
            color_dark = MainActivity.GetThemeColor();

            try {
                int color = Utility.MergeColors(Color.parseColor(color_dark), Color.parseColor("#33000000"));
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

}
