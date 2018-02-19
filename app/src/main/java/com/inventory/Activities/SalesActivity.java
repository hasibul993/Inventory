package com.inventory.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.inventory.Helper.Utility;
import com.inventory.R;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class SalesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView toolbar_title;

    EditText cutomerNameET, cutomerMobileET, patientNameET, ageET;

    RadioGroup radioGroup;

    RadioButton maleRadioButton, femaleRadioButton;

    RecyclerView recyclerView;

    FloatingActionButton floatActionButton;

    Utility utility = Utility.getInstance();

    MainActivity mainActivity = MainActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_activity);

        InitializeIDS();

        RecreateLayout();

        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnFabPressed();
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

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            cutomerNameET = (EditText) findViewById(R.id.cutomerNameET);
            cutomerMobileET = (EditText) findViewById(R.id.cutomerMobileET);
            patientNameET = (EditText) findViewById(R.id.patientNameET);
            ageET = (EditText) findViewById(R.id.ageET);

            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
            femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioButton);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RecreateLayout() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(SalesActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.sale), false);
            toolbar_title.setTextSize(getResources().getDimension(R.dimen.toolbar_title_8sp));

            utility.SetFabColor(SalesActivity.this, floatActionButton);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void OnFabPressed() {
        try {


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RadioButtonStateChanged(int checkedId) {
        try {
            switch (checkedId) {
                case R.id.maleRadioButton:
                    break;
                case R.id.femaleRadioButton:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void GotoSalesActivity(Context context) {
        try {
            Intent intent = new Intent(context, SalesActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_setting_activity, menu);
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
            HomeActivity.GotoHomeActivity(SalesActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
