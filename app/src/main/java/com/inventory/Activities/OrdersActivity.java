package com.inventory.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.inventory.R;

import java.util.ArrayList;

/**
 * Created by BookMEds on 02-02-2018.
 */

public class OrdersActivity extends AppCompatActivity implements AppConstants {

    Utility utility = Utility.getInstance();
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity = MainActivity.getInstance();


    private Toolbar toolbar;
    TextView toolbar_title;

    RecyclerView recyclerView;
    ArrayList<DrugModel> orderArrayList = new ArrayList<>();


    OrdersAdapter ordersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(OrdersActivity.this);

        RecreateLayout();

        GetOrdersLocally(null);

    }

    private void InitializeIDS() {
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void RecreateLayout() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(OrdersActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.order), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void GetOrdersLocally(String searchedText) {
        try {

            orderArrayList = mainActivity.GetOrderListFromOrderDB(OrdersActivity.this, searchedText);

            SetAdapter(orderArrayList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetAdapter(ArrayList<DrugModel> orderArrayList) {
        try {
            ordersAdapter = new OrdersAdapter(OrdersActivity.this, orderArrayList, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(OrdersActivity.this));
            recyclerView.addItemDecoration(new DividerItemDecoration(OrdersActivity.this));
            recyclerView.setAdapter(ordersAdapter);
            ordersAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void GotoOrdersActivity(Context context) {
        try {
            Intent intent = new Intent(context, OrdersActivity.class);
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
            HomeActivity.GotoHomeActivity(OrdersActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
