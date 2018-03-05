package com.inventory.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.inventory.Activities.HomeActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.Helper.Utility;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class RegistrationActivity extends AppCompatActivity {


    EditText enterNameET;

    TextView submitTV;

    UserKeyDetailsModel userKeyDetailsModel;

    MainActivity mainActivity = MainActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(RegistrationActivity.this);

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnSubmitPressed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    private void OnSubmitPressed() {
        String nickName;
        try {

            nickName = enterNameET.getText().toString().trim();

            if (StringUtils.isBlank(nickName)) {
                MainActivity.ShowToast(this, getString(R.string.enterNickName));
                return;
            } else {

                userKeyDetailsModel.NickName = nickName;
                mainActivity.InsertUpdateUserKeyDetails(this, userKeyDetailsModel);

                SettingsModel settingsModel = new SettingsModel();
                settingsModel.UserGuid = userKeyDetailsModel.UserGuid;
                settingsModel.IsDefaultTheme = true;
                settingsModel.ThemeColorCode = MainActivity.GetThemeColor();

                mainActivity.InsertUpdateSettings(this, settingsModel);

                if (!mainActivity.IsAnyMedicineExist(RegistrationActivity.this)) {
                    mainActivity.InsertDrugsFromRawDirectory(RegistrationActivity.this,userKeyDetailsModel.UserGuid);
                }

                HomeActivity.GotoHomeActivity(RegistrationActivity.this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void InitializeIDS() {
        String color_dark;
        try {
            color_dark = MainActivity.GetThemeColor();

            enterNameET = (EditText) findViewById(R.id.enterNameET);

            submitTV = (TextView) findViewById(R.id.submitTV);

            submitTV.setBackgroundColor(MainActivity.GetThemeColorInt());

            try {
                int color = Utility.MergeColors(MainActivity.GetThemeColorInt(), Color.parseColor("#33000000"));
                color_dark = String.format("#%06X", (0xFFFFFF & color));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(color_dark));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void GotoRegistrationActivity(Context context) {
        try {
            Intent intent = new Intent(context, RegistrationActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
