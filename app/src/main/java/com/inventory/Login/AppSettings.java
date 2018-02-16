package com.inventory.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.inventory.Activities.MainActivity;
import com.inventory.Activities.ProfileEditActivity;
import com.inventory.Helper.AppConstants;
import com.inventory.MediaPermission.PickMediaActivity;
import com.inventory.R;

import java.util.ArrayList;
import java.util.List;

public class AppSettings extends AppCompatActivity implements AppConstants {

    public static Button bar1, bar2, bar3, bar4;
    public static LockableViewPager pager;
    MyPageAdapter pageAdapter;

    Fragment_Authentication fragment_authentication;

    private Toolbar toolbar;
    TextView toolbar_title;

    PickMediaActivity pickMediaActivity = PickMediaActivity.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.app_settings);
            FirebaseApp.initializeApp(this);

            InitializeIDS();

            SetPagerAdapter();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void InitializeIDS() {
        try {
            pager = (LockableViewPager) findViewById(R.id.pager);
            bar1 = (Button) findViewById(R.id.bar1);
            bar2 = (Button) findViewById(R.id.bar2);
            bar3 = (Button) findViewById(R.id.bar3);
            bar4 = (Button) findViewById(R.id.bar4);

            toolbar = (Toolbar) findViewById(R.id.toolbarID);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);
            toolbar_title.setTextSize(getResources().getDimension(R.dimen.toolbar_title_8sp));
            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(AppSettings.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.login), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetPagerAdapter() {
        try {
            List<Fragment> fragments = getFragments();
            pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

            pager.setAdapter(pageAdapter);
            pager.setSwipeable(false);

            if (pager.getCurrentItem() == 0) {
                bar1.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar2.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
                bar3.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
                bar4.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
            }
            if (pager.getCurrentItem() == 1) {
                bar1.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar2.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar3.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
                bar4.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
            }
            if (pager.getCurrentItem() == 2) {
                bar1.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar2.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar3.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar4.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_200), PorterDuff.Mode.SRC_ATOP);
            }
            if (pager.getCurrentItem() == 3) {
                bar1.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar2.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar3.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
                bar4.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        try {

            fragment_authentication = new Fragment_Authentication();
            fList.add(fragment_authentication);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fList;
    }


    @Override
    protected void onResume() {
        try {

            boolean isGranted=pickMediaActivity.checkPermission(AppSettings.this, PERMISSIONS_PHONE_STATE, getString(R.string.phoneStateNeverAskAgain));

            if(isGranted){
                bar1.setVisibility(View.VISIBLE);
                pager.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pager.setCurrentItem(0);
                    }
                }, 100);
            }else{
                pager.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (pager.getCurrentItem() == 2) {

                        } else {
                            pager.setCurrentItem(1);
                        }

                    }
                }, 100);


                bar1.setVisibility(View.GONE);
                bar2.getBackground().setColorFilter(getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onResume();
    }

    public static void GotoAppSettings(Context context) {
        try {
            Intent intent = new Intent(context, AppSettings.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
