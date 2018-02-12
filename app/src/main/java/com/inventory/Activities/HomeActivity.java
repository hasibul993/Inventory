package com.inventory.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.inventory.Adapter.SliderMenuAdapter;
import com.inventory.Adapter.ViewPagerAdapter;
import com.inventory.Database.AndroidDatabaseViewer;
import com.inventory.Fragments.DashboardFragment;
import com.inventory.Fragments.InventoryFragment;
import com.inventory.Fragments.SalesFragment;
import com.inventory.Helper.Utility;
import com.inventory.Model.SliderMenuModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class HomeActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    CharSequence mDrawerTitle;
    Toolbar toolbar;
    TextView toolbar_title;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private ViewPager viewPager;

    RecyclerView recyclerView;
    FloatingActionButton floatActionButton;

    SliderMenuAdapter sliderMenuAdapter;

    int tabPosition = 0;
    ViewPagerAdapter viewPagerAdapter;

    View sliderHeaderLayout;
    ImageView header_UserIcon;
    RobotoTextView header_usernameTV, header_mobileTV, header_emailTV;
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.home_activity);

            InitializeIDS();

            //Configuration config = getResources().getConfiguration();
            //int size=config.smallestScreenWidthDp;

            Intent intent = getIntent();
            tabPosition = intent.getIntExtra(getString(R.string.tabPosition), 0);

            mainActivity = MainActivity.getInstance();
            userKeyDetailsModel = mainActivity.GetUserKeyDetails(HomeActivity.this);

            SetDrawerToggleAction();

            SetAdapterSliderMenu();

            SetViewPagerAdapter(viewPager, null);

            floatActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = -1;
                    Fragment currentFragment;
                    try {
                        index = viewPager.getCurrentItem();
                        viewPagerAdapter = ((ViewPagerAdapter) viewPager.getAdapter());
                        currentFragment = viewPagerAdapter.getItem(index);
                        if (currentFragment instanceof DashboardFragment) {
                            DashboardFragment dashboardFragment = (DashboardFragment) currentFragment;
                        } else if (currentFragment instanceof InventoryFragment) {
                            InventoryFragment inventoryFragment = (InventoryFragment) currentFragment;
                            inventoryFragment.ShowDialogAddUpdateDrug(null, -1);
                        } else if (currentFragment instanceof SalesFragment) {
                            SalesFragment salesFragment = (SalesFragment) currentFragment;
                        }
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
            // SLider header ids
            header_UserIcon = (ImageView) findViewById(R.id.header_navigation_drawer_image);
            header_usernameTV = (RobotoTextView) findViewById(R.id.header_usernameTV);
            header_mobileTV = (RobotoTextView) findViewById(R.id.header_mobileTV);
            header_emailTV = (RobotoTextView) findViewById(R.id.header_emailTV);


            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setVisibility(VISIBLE);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

            sliderHeaderLayout = (View) findViewById(R.id.drawerHeaderLayout);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


            if (Build.VERSION.SDK_INT <= 22) {
                floatActionButton.getBackground().setColorFilter(Color.parseColor(MainActivity.GetThemeColor()), PorterDuff.Mode.SRC_ATOP);
            } else {
                floatActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(MainActivity.GetThemeColor())));
            }

            sliderHeaderLayout.setBackgroundColor(Color.parseColor(MainActivity.GetThemeColor()));
            tabLayout.setBackgroundColor(Color.parseColor(MainActivity.GetThemeColor()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetDrawerToggleAction() {
        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(HomeActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.app_name), true);

            mTitle = mDrawerTitle = getTitle();

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                    R.string.drawer_open, R.string.drawer_close) {
                public void onDrawerClosed(View view) {
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    tabLayout.requestDisallowInterceptTouchEvent(true);
                    invalidateOptionsMenu();
                }
            };
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            });

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
            mDrawerToggle.setDrawerIndicatorEnabled(true);//if set true it will show default hamburger icon with spinner action when opne/close.
//        mDrawerToggle.setHomeAsUpIndicator(getToolbarBackButtonDrawble(HomeScreenActivity.this, R.drawable.hamburger_icon));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetAdapterSliderMenu() {
        ArrayList<SliderMenuModel> sliderMenuModels = new ArrayList<>();
        Utility utility = new Utility();
        try {
            header_usernameTV.setText(userKeyDetailsModel.NickName);
            header_mobileTV.setText(userKeyDetailsModel.PhoneNumber);

            sliderMenuModels = utility.GetSliderMenuList(HomeActivity.this);

            sliderMenuAdapter = new SliderMenuAdapter(HomeActivity.this, sliderMenuModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //recyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this, LinearLayoutManager.VERTICAL, 0, 1));
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            recyclerView.setAdapter(sliderMenuAdapter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetViewPagerAdapter(ViewPager viewPager, String searchedText) {
        try {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new DashboardFragment(), getString(R.string.dashboard));
            viewPagerAdapter.addFragment(new InventoryFragment(), getString(R.string.inventory));
            viewPagerAdapter.addFragment(new SalesFragment(), getString(R.string.sale));
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


    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mTitle = title;
        String coloredName = "<font color=" + getResources().getColor(R.color.colorPrimary) + ">" + mTitle + "</font>";
        getSupportActionBar().setTitle(Html.fromHtml(coloredName));
    }

    public static void GotoHomeActivity(Context context) {
        try {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_home_activity, menu);
            MenuItem moreIcon = menu.findItem(R.id.menu_more);
            moreIcon.setVisible(true);

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
                AndroidDatabaseViewer.GotoAndroidDatabaseViewer(HomeActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
