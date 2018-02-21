package com.inventory.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.inventory.Adapter.SliderMenuAdapter;
import com.inventory.Adapter.ViewPagerAdapter;
import com.inventory.Database.AndroidDatabaseViewer;
import com.inventory.Fragments.ExpiredDurationFragment;
import com.inventory.Fragments.InventoryFragment;
import com.inventory.Fragments.ExpiredFragment;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Helper.ViewImageCircle;
import com.inventory.Model.SliderMenuModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.Operation.Post;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class InventoryActivity extends AppCompatActivity implements AppConstants {

    public static TabLayout tabLayout;
    Toolbar toolbar;
    TextView toolbar_title;

    private ViewPager viewPager;

    FloatingActionButton floatActionButton;

    int tabPosition = 0;
    ViewPagerAdapter viewPagerAdapter;

    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity;
    Utility utility = Utility.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.inventory_activity);

            InitializeIDS();

            //Configuration config = getResources().getConfiguration();
            //int size=config.smallestScreenWidthDp;

            Intent intent = getIntent();
            tabPosition = intent.getIntExtra(getString(R.string.tabPosition), 0);

            mainActivity = MainActivity.getInstance();
            userKeyDetailsModel = mainActivity.GetUserKeyDetails(InventoryActivity.this);

            SetToolbar();

            SetViewPagerAdapter(viewPager, null);

            floatActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = -1;
                    Fragment currentFragment;
                    try {

                        AddInventoryActivity.GotoAddInventoryActivity(InventoryActivity.this, null, false);

                        /*index = viewPager.getCurrentItem();
                        viewPagerAdapter = ((ViewPagerAdapter) viewPager.getAdapter());
                        currentFragment = viewPagerAdapter.getItem(index);
                        if (currentFragment instanceof ExpiredDurationFragment) {
                            ExpiredDurationFragment expiredDurationFragment = (ExpiredDurationFragment) currentFragment;
                        } else if (currentFragment instanceof InventoryFragment) {
                            *//*InventoryFragment inventoryFragment = (InventoryFragment) currentFragment;
                            inventoryFragment.ShowDialogAddUpdateDrug(null, -1);*//*
                            //AddInventoryActivity.GotoAddInventoryActivity(InventoryActivity.this, null, false);
                        } else if (currentFragment instanceof ExpiredFragment) {
                            //SalesActivity.GotoSalesActivity(InventoryActivity.this);
                        }*/
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void InitializeIDS() {

        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setVisibility(VISIBLE);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

            utility.SetFabColor(InventoryActivity.this, floatActionButton);

            tabLayout.setBackgroundColor(Color.parseColor(MainActivity.GetThemeColor()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetToolbar() {
        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(InventoryActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.app_name), false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetViewPagerAdapter(ViewPager viewPager, String searchedText) {
        try {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new InventoryFragment(), getString(R.string.inventoryAll));
            viewPagerAdapter.addFragment(new ExpiredDurationFragment(), getString(R.string.expiredDuration));
            viewPagerAdapter.addFragment(new ExpiredFragment(), getString(R.string.expired));
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            viewPager.setAdapter(null);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(tabPosition);
            viewPager.getAdapter().notifyDataSetChanged();

            tabLayout.setupWithViewPager(viewPager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void GotoInventoryActivity(Context context) {
        try {
            Intent intent = new Intent(context, InventoryActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_inventory_activity, menu);

            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    int index = -1;
                    Fragment currentFragment;
                    try {
                        index = viewPager.getCurrentItem();
                        viewPagerAdapter = ((ViewPagerAdapter) viewPager.getAdapter());
                        currentFragment = viewPagerAdapter.getItem(index);
                        if (currentFragment instanceof InventoryFragment) {
                            InventoryFragment inventoryFragment = (InventoryFragment) currentFragment;
                            inventoryFragment.GetDrugsLocally(newText);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                }

            });

            MenuItem item = menu.findItem(R.id.action_search);
            MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    tabLayout.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    tabLayout.setVisibility(View.VISIBLE);
                    return false;
                }
            });

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
            case R.id.viewDataBase:
                AndroidDatabaseViewer.GotoAndroidDatabaseViewer(InventoryActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        try {
            HomeActivity.GotoHomeActivity(InventoryActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
