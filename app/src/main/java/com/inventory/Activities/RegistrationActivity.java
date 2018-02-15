package com.inventory.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    TextInputLayout enterNameET_Hint, enterMobileET_Hint;

    EditText enterNameET, enterMobileET;

    TextView submitTV;

    MainActivity mainActivity = MainActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        InitializeIDS();

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
        String nickName, mobileNo;
        try {

            nickName = enterNameET.getText().toString().trim();
            mobileNo = enterMobileET.getText().toString().trim();

            if (StringUtils.isBlank(nickName)) {
                MainActivity.ShowToast(this, getString(R.string.enterNickName));
                return;
            } else if (StringUtils.isBlank(mobileNo)) {
                MainActivity.ShowToast(this, getString(R.string.enterMobileNo));
                return;
            } else if (mobileNo.length() != 10) {
                MainActivity.ShowToast(this, getString(R.string.enterMobileNoTenDigit));
                return;
            } else {

                UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();

                userKeyDetailsModel.UserGuid = UUID.randomUUID().toString();
                userKeyDetailsModel.NickName = nickName;
                userKeyDetailsModel.PhoneNumber = mobileNo;
                mainActivity.InsertUpdateUserKeyDetails(this, userKeyDetailsModel);

                SettingsModel settingsModel = new SettingsModel();
                settingsModel.UserGuid = userKeyDetailsModel.UserGuid;
                settingsModel.IsDefaultTheme = true;
                settingsModel.ThemeColorCode = MainActivity.GetThemeColor();

                mainActivity.InsertUpdateSettings(this, settingsModel);

                if (!mainActivity.IsAnyMedicineExist(RegistrationActivity.this)) {
                    mainActivity.InsertDrugsFromRawDirectory(RegistrationActivity.this);
                }

                HomeActivity.GotoHomeActivity(RegistrationActivity.this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void InitializeIDS() {
        try {

            enterNameET_Hint = (TextInputLayout) findViewById(R.id.enterNameET_Hint);
            enterMobileET_Hint = (TextInputLayout) findViewById(R.id.enterMobileET_Hint);

            enterNameET = (EditText) findViewById(R.id.enterNameET);
            enterMobileET = (EditText) findViewById(R.id.enterMobileET);

            submitTV = (TextView) findViewById(R.id.submitTV);

            //enterNameET.setHint(getString(R.string.enterNickName));
            //enterMobileET.setHint(getString(R.string.enterMobileNo));
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
