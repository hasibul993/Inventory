package com.inventory.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inventory.Helper.Utility;
import com.inventory.Helper.ViewImageCircle;
import com.inventory.MediaPermission.PermissionsChecker;
import com.inventory.MediaPermission.PickMediaActivity;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by BookMEds on 12-02-2018.
 */

public class ProfileActivity extends AppCompatActivity {

    private static final int ALPHA_ANIMATIONS_DURATION = 400;

    ImageView profileImage;

    RelativeLayout profilePhoneLayout, profileEmailLayout;

    TextView profilePhoneTextView, profileHeaderUserName, profileEmailTextView, versionName;

    FloatingActionButton floatActionButton;

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;

    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();

    MainActivity mainActivity = MainActivity.getInstance();
    ViewImageCircle viewImageCircle = ViewImageCircle.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(ProfileActivity.this);

        FillLayoutData();

        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ProfileEditActivity.GotoProfileEditActivity(ProfileActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(userKeyDetailsModel.NickName);
                    toolbar.setBackgroundColor(MainActivity.GetThemeColorInt());
                    isShow = true;
                    startAlphaAnimation(toolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    toolbar.setBackgroundColor(0);
                    isShow = false;
                    startAlphaAnimation(toolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                }
            }
        });
    }

    private void InitializeIDS() {
        try {
            profileImage = (ImageView) findViewById(R.id.profile_image);
            profilePhoneTextView = (TextView) findViewById(R.id.profile_phone_number_textview);
            profileEmailTextView = (TextView) findViewById(R.id.profile_email_address_textview);
            profileHeaderUserName = (TextView) findViewById(R.id.profile_user_name);

            profilePhoneLayout = (RelativeLayout) findViewById(R.id.phoneLayout);
            profileEmailLayout = (RelativeLayout) findViewById(R.id.emailAddressLayout);

            versionName = (TextView) findViewById(R.id.version_name);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            SupportActionBar(this);

            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void FillLayoutData() {
        PackageInfo pInfo = null;

        try {
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionName.setText(getString(R.string.version) + " " + pInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            collapsingToolbarLayout.setTitle(" ");
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ProfileTitleStyle);

            profileHeaderUserName.setText(userKeyDetailsModel.NickName);
            profilePhoneTextView.setText(userKeyDetailsModel.PhoneNumber);

            if (!StringUtils.isBlank(userKeyDetailsModel.EmailAddress)) {
                profileEmailLayout.setVisibility(View.VISIBLE);
                profileEmailTextView.setText(userKeyDetailsModel.EmailAddress);
            } else
                profileEmailLayout.setVisibility(View.GONE);


            if (!StringUtils.isBlank(userKeyDetailsModel.ProfilePicture)) {
                viewImageCircle.SetProfileIconProfileView(ProfileActivity.this, profileImage, userKeyDetailsModel.ProfilePicture, true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        try {
            AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                    ? new AlphaAnimation(0f, 1f)
                    : new AlphaAnimation(1f, 0f);

            alphaAnimation.setDuration(duration);
            alphaAnimation.setFillAfter(true);
            v.startAnimation(alphaAnimation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void SupportActionBar(Activity activity) {
        String color_dark;
        try {
            color_dark = MainActivity.GetThemeColor();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public static void GotoProfileActivity(Context context) {
        try {
            Intent intent = new Intent(context, ProfileActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_profile_activity, menu);
            MenuItem moreIcon = menu.findItem(R.id.camera);
            moreIcon.setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        try {
            HomeActivity.GotoHomeActivity(ProfileActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
