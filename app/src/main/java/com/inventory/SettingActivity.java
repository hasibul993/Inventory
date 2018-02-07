package com.inventory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inventory.Adapter.CircleColorAdapter;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Model.SettingsModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.MyGridView;
import com.inventory.NewUi.RobotoTextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BookMEds on 02-02-2018.
 */

public class SettingActivity extends AppCompatActivity implements AppConstants {

    Utility utility = new Utility();
    SettingsModel settingsModel = new SettingsModel();
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity = MainActivity.getInstance();

    RadioGroup radioGroup;
    RadioButton radioButton_Default, radioButton_ChooseTheme;
    RelativeLayout colorLayoutDafault, colorLayoutChooseTheme;

    private Toolbar toolbar;
    TextView toolbar_title;

    String selectedColorCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(SettingActivity.this);
        settingsModel = mainActivity.GetSettings(SettingActivity.this, userKeyDetailsModel.UserGuid);

        RecreateLayout();


        colorLayoutChooseTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShowColorPicker();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButtonStateChanged(checkedId);

            }
        });


    }

    private void InitializeIDS() {
        try {


            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

            radioButton_Default = (RadioButton) findViewById(R.id.radioButton_Default);
            radioButton_ChooseTheme = (RadioButton) findViewById(R.id.radioButton_ChooseTheme);

            colorLayoutDafault = (RelativeLayout) findViewById(R.id.colorLayoutDafault);
            colorLayoutChooseTheme = (RelativeLayout) findViewById(R.id.colorLayoutChooseTheme);

            utility.SetRadioButtonColor(radioButton_Default, Color.parseColor(MainActivity.GetThemeColor()), Color.parseColor(getString(R.string.cbUncheckColor)));
            utility.SetRadioButtonColor(radioButton_ChooseTheme, Color.parseColor(MainActivity.GetThemeColor()), Color.parseColor(getString(R.string.cbUncheckColor)));

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void RecreateLayout() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(SettingActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.settings), false);

            ChangeLayoutOnRadioButtonSelect(settingsModel.IsDefaultTheme);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RadioButtonStateChanged(int checkedId) {
        try {
            switch (checkedId) {
                case R.id.radioButton_Default:
                    ChangeLayoutOnRadioButtonSelect(true);
                    UpdateSettings(true, THEMECOLOR);
                    break;
                case R.id.radioButton_ChooseTheme:
                    ChangeLayoutOnRadioButtonSelect(false);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ChangeLayoutOnRadioButtonSelect(boolean isDefault) {
        GradientDrawable colorThemeBackground;
        try {
            colorLayoutDafault.setVisibility(View.INVISIBLE);

            if (isDefault) {
                radioButton_Default.setChecked(true);
                colorLayoutChooseTheme.setVisibility(View.INVISIBLE);
                colorThemeBackground = (GradientDrawable) colorLayoutDafault.getBackground();
            } else {
                radioButton_ChooseTheme.setChecked(true);
                colorLayoutChooseTheme.setVisibility(View.VISIBLE);
                colorThemeBackground = (GradientDrawable) colorLayoutChooseTheme.getBackground();
            }
            colorThemeBackground.setColor(Color.parseColor(MainActivity.GetThemeColor()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void ShowColorPicker() {

        try {

            ArrayList arrayList = Utility.GetThemeList(this);

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.gridview_dialog);
            dialog.setCancelable(false);
            MyGridView gridView = (MyGridView) dialog.findViewById(R.id.gridView);
            RelativeLayout cancelOkRelaLayout = (RelativeLayout) dialog.findViewById(R.id.cancelOkRelaLayout);
            RobotoTextView okTV = (RobotoTextView) dialog.findViewById(R.id.okTV);
            RobotoTextView cancelTV = (RobotoTextView) dialog.findViewById(R.id.cancelTV);

            okTV.setTextColor(getResources().getColor(R.color.White));
            cancelTV.setTextColor(getResources().getColor(R.color.White));

            GradientDrawable cancelOkRelaLayoutBackg = (GradientDrawable) cancelOkRelaLayout.getBackground();
            cancelOkRelaLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));

            final CircleColorAdapter circleColorAdapter = new CircleColorAdapter(this, arrayList, MainActivity.GetThemeColor());
            gridView.setAdapter(circleColorAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    SettingsModel settingsModel = new SettingsModel();
                    try {
                        selectedColorCode = circleColorAdapter.getItem(position);
                        circleColorAdapter.SetSelectedPosition(position);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SettingsModel settingsModel = new SettingsModel();
                    try {
                        if (!StringUtils.isBlank(selectedColorCode)) {
                            UpdateSettings(false, selectedColorCode);
                            selectedColorCode = "";
                            dialog.dismiss();
                        } else {
                            MainActivity.ShowToast(SettingActivity.this, getString(R.string.selectColor));
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            cancelTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        selectedColorCode = "";
                        dialog.dismiss();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });


            dialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void UpdateSettings(boolean isDefault, String selectedColorCode) {
        SettingsModel settingsModel = new SettingsModel();
        try {
            settingsModel.UserGuid = userKeyDetailsModel.UserGuid;
            settingsModel.IsDefaultTheme = isDefault;
            settingsModel.ThemeColorCode = selectedColorCode;
            mainActivity.InsertUpdateSettings(SettingActivity.this, settingsModel);
            MainActivity.SetThemeColor(selectedColorCode);
            mainActivity.RefreshScreen(SettingActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static void GotoSettingActivity(Context context) {
        try {
            Intent intent = new Intent(context, SettingActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_home_activity, menu);
            MenuItem moreIcon = menu.findItem(R.id.menu_more);
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
            HomeActivity.GotoHomeActivity(SettingActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
