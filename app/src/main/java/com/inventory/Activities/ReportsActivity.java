package com.inventory.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.inventory.Adapter.OrdersAdapter;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Model.DrugModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by BookMEds on 02-02-2018.
 */

public class ReportsActivity extends AppCompatActivity implements AppConstants {

    Utility utility = Utility.getInstance();
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity = MainActivity.getInstance();


    private Toolbar toolbar;
    TextView toolbar_title;

    RecyclerView recyclerView;

    ArrayList<DrugModel> orderArrayList = new ArrayList<>();

    RobotoTextView todaysSaleTV, todaysSaleValueTV, todaysOrderTV, todaysOrderValueTV;


    OrdersAdapter ordersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(ReportsActivity.this);

        RecreateLayout();

        GetTodaysOrdersLocally(mainActivity.GetCurrentDate(AppConstants.SIMPLE_DATE_FORMAT));

        // GetOrdersLocally(null);

    }

    private void InitializeIDS() {
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            todaysSaleTV = (RobotoTextView) findViewById(R.id.todaysSaleTV);
            todaysSaleValueTV = (RobotoTextView) findViewById(R.id.todaysSaleValueTV);
            todaysOrderTV = (RobotoTextView) findViewById(R.id.todaysOrderTV);
            todaysOrderValueTV = (RobotoTextView) findViewById(R.id.todaysOrderValueTV);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void RecreateLayout() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(ReportsActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.report), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void GetTodaysOrdersLocally(String today) {
        try {

            orderArrayList = mainActivity.GetOrderListByDateFromOrderDB(ReportsActivity.this, today);

            todaysOrderTV.setText(getString(R.string.today) + "'s " + getString(R.string.order));
            todaysOrderValueTV.setText(orderArrayList.size() + "");

            todaysSaleTV.setText(getString(R.string.today) + "'s " + getString(R.string.sales));
            todaysSaleValueTV.setText(getString(R.string.rs) + " " + GetTodaysSales(orderArrayList));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void GetOrdersLocally(String searchedText) {
        try {

            orderArrayList = mainActivity.GetOrderListFromOrderDB(ReportsActivity.this, searchedText);

            SetAdapter(orderArrayList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetAdapter(ArrayList<DrugModel> orderArrayList) {
        try {
            ordersAdapter = new OrdersAdapter(ReportsActivity.this, orderArrayList, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(ReportsActivity.this));
            recyclerView.addItemDecoration(new DividerItemDecoration(ReportsActivity.this));
            recyclerView.setAdapter(ordersAdapter);
            ordersAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String GetTodaysSales(ArrayList<DrugModel> todayOrderArrayList) {
        double totalSales = 0;
        String OrderTotalString = "";
        try {
            for (DrugModel drug : todayOrderArrayList) {
                try {
                    totalSales = totalSales + drug.OrderTotal;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            OrderTotalString = AppConstants.decimalFormatTwoPlace.format(totalSales);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return OrderTotalString;
    }


    public static void GotoReportsActivity(Context context) {
        try {
            Intent intent = new Intent(context, ReportsActivity.class);
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
            HomeActivity.GotoHomeActivity(ReportsActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
