package com.inventory.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class SalesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView toolbar_title;

    MainActivity mainActivity = MainActivity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_activity);

        InitializeIDS();

        RecreateLayout();

       /* submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnSubmitPressed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });*/
    }

    private void OnSubmitPressed() {

        try {


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void InitializeIDS() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RecreateLayout() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(SalesActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.sale), false);
            toolbar_title.setTextSize(getResources().getDimension(R.dimen.toolbar_title_8sp));

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
