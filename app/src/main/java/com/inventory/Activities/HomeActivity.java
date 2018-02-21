package com.inventory.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.inventory.Adapter.HomeGridAdapter;
import com.inventory.Adapter.SliderMenuAdapter;
import com.inventory.Database.AndroidDatabaseViewer;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Helper.ViewImageCircle;
import com.inventory.Model.HomeModel;
import com.inventory.Model.SliderMenuModel;
import com.inventory.Model.UserKeyDetailsModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.Operation.Post;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class HomeActivity extends AppCompatActivity implements AppConstants {

    CharSequence mDrawerTitle;
    Toolbar toolbar;
    TextView toolbar_title;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;

    RecyclerView recyclerViewMenu, recyclerView;

    SliderMenuAdapter sliderMenuAdapter;
    HomeGridAdapter homeGridAdapter;

    View sliderHeaderLayout;
    ImageView header_UserIcon;
    RobotoTextView header_usernameTV, header_mobileTV, header_emailTV;
    UserKeyDetailsModel userKeyDetailsModel = new UserKeyDetailsModel();
    MainActivity mainActivity;
    Utility utility = Utility.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.home_activity);

            InitializeIDS();

            //Configuration config = getResources().getConfiguration();
            //int size=config.smallestScreenWidthDp;

            Intent intent = getIntent();

            mainActivity = MainActivity.getInstance();
            userKeyDetailsModel = mainActivity.GetUserKeyDetails(HomeActivity.this);

            SetDrawerToggleAction();

            SetSliderMenuHeaderData();

            SetAdapterSliderMenu();

            SetAdapter();

            sliderHeaderLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ProfileActivity.GotoProfileActivity(HomeActivity.this);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void OnSubmitPressed() {

        try {


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void InitializeIDS() {
        try {
            // Slider header ids
            header_UserIcon = (ImageView) findViewById(R.id.header_navigation_drawer_image);
            header_usernameTV = (RobotoTextView) findViewById(R.id.header_usernameTV);
            header_mobileTV = (RobotoTextView) findViewById(R.id.header_mobileTV);
            header_emailTV = (RobotoTextView) findViewById(R.id.header_emailTV);


            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            sliderHeaderLayout = (View) findViewById(R.id.drawerHeaderLayout);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            recyclerViewMenu = (RecyclerView) findViewById(R.id.recyclerViewMenu);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerViewMenu.setBackgroundColor(getResources().getColor(R.color.White));

            sliderHeaderLayout.setBackgroundColor(Color.parseColor(MainActivity.GetThemeColor()));

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
            //mDrawerToggle.setHomeAsUpIndicator(getToolbarBackButtonDrawble(HomeScreenActivity.this, R.drawable.hamburger_icon));
            mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.White));// will change hamburger icon color
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSliderMenuHeaderData() {
        ViewImageCircle viewImageCircle = ViewImageCircle.getInstance();
        try {
            header_usernameTV.setText(userKeyDetailsModel.NickName);
            header_mobileTV.setText(userKeyDetailsModel.PhoneNumber);

            if (!StringUtils.isBlank(userKeyDetailsModel.ProfilePicture)) {
                viewImageCircle.SetProfileIconProfileView(HomeActivity.this, header_UserIcon, userKeyDetailsModel.ProfilePicture, true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetAdapterSliderMenu() {
        ArrayList<SliderMenuModel> sliderMenuModels = new ArrayList<>();

        try {


            sliderMenuModels = utility.GetSliderMenuList(HomeActivity.this);

            sliderMenuAdapter = new SliderMenuAdapter(HomeActivity.this, sliderMenuModels);
            recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
            //recyclerViewMenu.addItemDecoration(new DividerItemDecoration(InventoryActivity.this, LinearLayoutManager.VERTICAL, 0, 1));
            recyclerViewMenu.addItemDecoration(new DividerItemDecoration(this));
            recyclerViewMenu.setAdapter(sliderMenuAdapter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetAdapter() {
        ArrayList<HomeModel> homeModels = new ArrayList<>();

        try {

            homeModels = utility.GetHomeMenuList(HomeActivity.this);

            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(1, dpToPx(0), true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);

            homeGridAdapter = new HomeGridAdapter(HomeActivity.this, homeModels);
            recyclerView.setAdapter(homeGridAdapter);

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

    public void onBackPressed() {
        try {
            MainActivity.MinimizeActivity(HomeActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                //outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.left = 0;
                //outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                outRect.right = 0;
                if (position < spanCount) { // top edge
                    //outRect.top = spacing;
                    outRect.top = 0;
                }
                //outRect.bottom = spacing; // item bottom
                outRect.bottom = 0;
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void SyncMasterDB() {

        String url = "", json = "";
        try {
            if (utility.IsInternetConnected(this)) {
                new Post(this, url, json, false) {
                    @Override
                    public void onResponseReceived(String response) {
                        try {
                            if (response.contains(SOCKET_TIMEOUT)) {
                                String messageBody = getString(R.string.internet_slow);
                            } else if (response.contains(INVALID_HOSTNAME) || response.contains(CONNECTION_GONE)) {
                                String messageBody = getString(R.string.internet_gone);

                            } else {

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.execute();
            } else
                MainActivity.ShowToast(this, getString(R.string.check_internet));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
