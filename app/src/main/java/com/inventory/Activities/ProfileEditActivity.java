package com.inventory.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Helper.ViewImageCircle;
import com.inventory.MediaPermission.PermissionsChecker;
import com.inventory.MediaPermission.PickMediaActivity;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hasib on 13-02-2018.
 */

public class ProfileEditActivity extends AppCompatActivity implements AppConstants {


    ImageView profileImage;

    EditText editProfileName, editProfileEmail;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    String imagePath = "";

    MainActivity mainActivity = MainActivity.getInstance();
    ViewImageCircle viewImageCircle = ViewImageCircle.getInstance();
    PickMediaActivity pickMediaActivity = PickMediaActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_activity);

        InitializeIDS();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(ProfileEditActivity.this);
        imagePath = userKeyDetailsModel.ProfilePicture;

        FillLayoutData();

    }

    private void InitializeIDS() {
        try {
            profileImage = (ImageView) findViewById(R.id.profile_image);

            editProfileName = (EditText) findViewById(R.id.edit_name);
            editProfileEmail = (EditText) findViewById(R.id.edit_email_address);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            SupportActionBar(this);

            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void FillLayoutData() {
        try {

            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_check_right_white_icon);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            collapsingToolbarLayout.setTitle(userKeyDetailsModel.NickName);
            collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ProfileTitleStyle);

            editProfileName.setText(userKeyDetailsModel.NickName);
            editProfileName.setSelection(editProfileName.length());
            editProfileEmail.setText(userKeyDetailsModel.EmailAddress);


            if (!StringUtils.isBlank(userKeyDetailsModel.ProfilePicture)) {
                viewImageCircle.SetProfileIconProfileView(ProfileEditActivity.this, profileImage, userKeyDetailsModel.ProfilePicture, false);
            }

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

    public static void GotoProfileEditActivity(Context context) {
        try {
            Intent intent = new Intent(context, ProfileEditActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void SetActivityIcon(Bitmap bitmap, String filePath) {
        try {

            if (bitmap != null) {
                imagePath = filePath;
                profileImage.setImageBitmap(null);
                profileImage.setImageBitmap(bitmap);
            } else {
                MainActivity.ShowToast(ProfileEditActivity.this, getString(R.string.upload_fail));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void SetActivityNull() {
        try {
            imagePath = null;
            profileImage.setImageBitmap(null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_profile_activity, menu);
            MenuItem moreIcon = menu.findItem(R.id.camera);
            moreIcon.setVisible(true);
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
            case R.id.camera:
                pickMediaActivity.checkPermission(ProfileEditActivity.this, PERMISSIONS_CAMERA, getString(R.string.cameraNeverAskAgain));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        String name, email;
        try {
            name = editProfileName.getText().toString().trim();
            email = editProfileEmail.getText().toString().trim();

            if (StringUtils.isBlank(name)) {
                MainActivity.ShowToast(ProfileEditActivity.this, getString(R.string.enterNickName));
                return;
            } else if (!StringUtils.isBlank(email) && !isValidEmail(email)) {
                MainActivity.ShowToast(ProfileEditActivity.this, getString(R.string.enterValidEmail));
                return;
            } else {
                userKeyDetailsModel.NickName = name;
                userKeyDetailsModel.EmailAddress = email;

                userKeyDetailsModel.ProfilePicture = imagePath;
                mainActivity.InsertUpdateUserKeyDetails(this, userKeyDetailsModel);

                ProfileActivity.GotoProfileActivity(ProfileEditActivity.this);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            PermissionsChecker checker = new PermissionsChecker(ProfileEditActivity.this);
            if (!checker.lacksPermissions(PERMISSIONS_CAMERA)) {
                pickMediaActivity.SetToSharePreference(ProfileEditActivity.this, getString(R.string.cameraNeverAskAgain), false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ///this for marshmallow permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {

            pickMediaActivity.activityPermissionsResult(ProfileEditActivity.this, requestCode, permissions, grantResults);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            pickMediaActivity.activityResult(ProfileEditActivity.this, getString(R.string.profileEditActivity), requestCode, resultCode, data);
            // passing set_location tv as parameter for location set.
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

}
