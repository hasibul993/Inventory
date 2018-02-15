package com.inventory.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inventory.Adapter.SearchDrugAdapter;
import com.inventory.Adapter.SearchDrugManufacturerAdapter;
import com.inventory.Fragments.FragmentForIcons;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.RecyclerItemClickListener;
import com.inventory.Helper.Utility;
import com.inventory.Model.DrugModel;
import com.inventory.Model.StringHolderModel;
import com.inventory.Model.ViewIDModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class AddInventoryActivity extends AppCompatActivity {


    private static final String TAG = "AddInventoryAct";

    private Toolbar toolbar;
    TextView toolbar_title;

    FloatingActionButton floatActionButton;

    ViewIDModel viewIDModel = new ViewIDModel();
    String prevDrugCategory = null;

    DrugModel editModel = null;

    int position = -1;
    boolean isModify = false, isSearchMedClicked = false, isSearchManufacturerClicked = false;

    MainActivity mainActivity = MainActivity.getInstance();
    Utility utility = Utility.getInstance();

    ArrayList<String> drugCategories;

    private Timer timer;
    DrugModel searchDrugModel = null;
    SearchDrugAdapter searchDrugAdapter;
    SearchDrugManufacturerAdapter searchDrugManufacturerAdapter;


    /*Medicine icon*/
    String iconFont = "", fromDrugIcon = "";

    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_inventory_activity);

        InitializeIDS();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        editModel = (DrugModel) bundle.getSerializable(getString(R.string.drugModel));
        isModify = (boolean) bundle.getSerializable(getString(R.string.isModify));

        if (editModel == null)
            fromDrugIcon = getString(R.string.tablet);
        else
            fromDrugIcon = editModel.DrugCategory;

        RecreateLayout();

        viewIDModel.DrugNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String searchText = viewIDModel.DrugNameEditText.getText().toString();

                    if (!isSearchMedClicked && (searchText.length() >= 2)) {
                        viewIDModel.ResultOfTextViewDrugNameRecyclerView.setText(getString(R.string.resultOf) + " '" + searchText + "'");
                        EditTypeStop(viewIDModel, searchText, 400, true);
                    } else {
                        SetRecyclerViewVisibility(viewIDModel, false);
                    }
                    isSearchMedClicked = false;
                    Log.i(TAG, "onTextChanged : " + searchText);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        viewIDModel.ManufacturerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                try {
                    String searchText = viewIDModel.ManufacturerEditText.getText().toString();
                    // if (getActivity().getCurrentFocus() == viewIDModel.ManufacturerEditText) {
                    if (!isSearchManufacturerClicked && (searchText.length() >= 2)) {
                        viewIDModel.ResultOfTextViewManufacturerRecyclerView.setText(getString(R.string.resultOf) + " '" + searchText + "'");
                        EditTypeStop(viewIDModel, searchText, 400, false);
                    } else {
                        SetRecyclerViewVisibility(viewIDModel, true);
                    }
                    //}
                    isSearchManufacturerClicked = false;
                    Log.i(TAG, "onTextChanged : " + searchText);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        viewIDModel.ExpiryDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    utility.ShowDatePicker(AddInventoryActivity.this, viewIDModel.ExpiryDateTextView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnSubmitPressed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

       /* viewIDModel.CancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HomeActivity.GotoHomeActivity(AddInventoryActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });*/

        viewIDModel.DeleteIconDrugNameRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SetRecyclerViewVisibility(viewIDModel, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        viewIDModel.DeleteIconManufacturerRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SetRecyclerViewVisibility(viewIDModel, true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    private void OnSubmitPressed() {
        StringHolderModel stringHolderModel = new StringHolderModel();
        try {
            stringHolderModel.drugName = viewIDModel.DrugNameEditText.getText().toString().trim();
            stringHolderModel.drugMRP = viewIDModel.MrpEditText.getText().toString().trim();
            stringHolderModel.drugQuantity = viewIDModel.QuantityEditText.getText().toString().trim();
            stringHolderModel.drugDiscount = viewIDModel.DiscountEditText.getText().toString().trim();
            stringHolderModel.drugExpiryDate = viewIDModel.ExpiryDateTextView.getText().toString().trim();
            stringHolderModel.drugTransactionDate = viewIDModel.TransactionDateTextView.getText().toString().trim();
            stringHolderModel.drugManufacturer = viewIDModel.ManufacturerEditText.getText().toString().trim();
            stringHolderModel.batchNumber = viewIDModel.BatchNumberEditText.getText().toString().trim();

            if (StringUtils.isBlank(stringHolderModel.drugName)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterDrugName));
                return;
            } else if (StringUtils.isBlank(stringHolderModel.drugMRP)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterDrugMRP));
                return;
            } else if (StringUtils.isBlank(stringHolderModel.drugQuantity)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterDrugQuantity));
                return;
            } else if (StringUtils.isBlank(stringHolderModel.drugManufacturer)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterDrugManufacturer));
                return;
            } else if (StringUtils.isBlank(stringHolderModel.batchNumber)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterBatchNumber));
                return;
            } else if (StringUtils.isBlank(stringHolderModel.drugExpiryDate)) {
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.chooseExpiryDate));
                return;
            } else {
                boolean isDataOk = AddUpdateItem(stringHolderModel, editModel, fromDrugIcon, position);
                if (isDataOk)
                    HomeActivity.GotoHomeActivity(AddInventoryActivity.this);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void InitializeIDS() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbarId);
            toolbar_title = (TextView) findViewById(R.id.toolbar_title);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

            //product ids
            viewIDModel.DrugNameEditTextTHint = (TextInputLayout) findViewById(R.id.drugNameET_Hint);
            viewIDModel.MrpEditTextTHint = (TextInputLayout) findViewById(R.id.mrpET_Hint);
            viewIDModel.QuantityEditTextTHint = (TextInputLayout) findViewById(R.id.quantityET_Hint);
            viewIDModel.DiscountEditTextTHint = (TextInputLayout) findViewById(R.id.discountET_Hint);
            viewIDModel.ManufacturerEditTextTHint = (TextInputLayout) findViewById(R.id.manufacturerET_Hint);
            viewIDModel.BatchNumberEditTextTHint = (TextInputLayout) findViewById(R.id.batchNumberET_Hint);

            viewIDModel.viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewIDModel.tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            viewIDModel.DrugNameEditText = (EditText) findViewById(R.id.drugNameET);
            viewIDModel.MrpEditText = (EditText) findViewById(R.id.mrpET);
            viewIDModel.QuantityEditText = (EditText) findViewById(R.id.quantityET);
            viewIDModel.DiscountEditText = (EditText) findViewById(R.id.discountET);
            viewIDModel.ManufacturerEditText = (EditText) findViewById(R.id.manufacturerET);
            viewIDModel.BatchNumberEditText = (EditText) findViewById(R.id.batchNumberET);

            viewIDModel.ExpiryDateTextView = (RobotoTextView) findViewById(R.id.expiryDateTV);
            viewIDModel.TransactionDateTextView = (RobotoTextView) findViewById(R.id.transactionDateTV);

           /*Below ids isfor drug search RecyclerView and manufacturer search RecyclerView*/
            viewIDModel.DrugNameRecyclerView = (RecyclerView) findViewById(R.id.drugNameRecyclerView);
            viewIDModel.DrugManufacturerRecyclerView = (RecyclerView) findViewById(R.id.drugManufacturerRecyclerView);
            viewIDModel.DrugNameRecyclerViewLayout = (LinearLayout) findViewById(R.id.drugNameRecyclerViewLayout);
            viewIDModel.DrugManufacturerRecyclerViewLayout = (LinearLayout) findViewById(R.id.drugManufacturerRecyclerViewLayout);

            viewIDModel.ResultOfTextViewDrugNameRecyclerView = (RobotoTextView) findViewById(R.id.resultOfTextViewDrugNameRecyclerView);
            viewIDModel.ResultOfTextViewManufacturerRecyclerView = (RobotoTextView) findViewById(R.id.resultOfTextViewManufacturerRecyclerView);
            viewIDModel.DeleteIconDrugNameRecyclerView = (ImageView) findViewById(R.id.deleteIconDrugNameRecyclerView);
            viewIDModel.DeleteIconManufacturerRecyclerView = (ImageView) findViewById(R.id.deleteIconManufacturerRecyclerView);

            //cancel ok btn ids
            //viewIDModel.OkTextView = (RobotoTextView) findViewById(R.id.okTV);
            //viewIDModel.CancelTextView = (RobotoTextView) findViewById(R.id.cancelTV);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RecreateLayout() {

        try {

            drugCategories = Utility.GetDrugCategoryList(AddInventoryActivity.this);

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(AddInventoryActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.addMedicine), false);
            toolbar_title.setTextSize(getResources().getDimension(R.dimen.toolbar_title_8sp));
            utility.SetFabColor(AddInventoryActivity.this, floatActionButton);
            try {
                final Drawable upArrow = getResources().getDrawable(R.drawable.vector_cross_white_icon);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            } catch (Exception e) {
                e.printStackTrace();
            }

            SetHintText(viewIDModel);

            if (editModel != null) {
                isModify = true;
                viewIDModel.DrugNameEditText.setText(editModel.DrugName);
                viewIDModel.DrugNameEditText.setSelection(viewIDModel.DrugNameEditText.length());
                viewIDModel.MrpEditText.setText(editModel.DrugMRPString);
                viewIDModel.QuantityEditText.setText(editModel.DrugQuantity + "");
                viewIDModel.DiscountEditText.setText(editModel.DrugDiscountString);
                viewIDModel.ExpiryDateTextView.setText(editModel.DrugExpiryDate);
                viewIDModel.ManufacturerEditText.setText(editModel.DrugManufacturer);
                viewIDModel.BatchNumberEditText.setText(editModel.BatchNumber);
                prevDrugCategory = editModel.DrugCategory;

                toolbar_title.setText(getString(R.string.update));


            } else {
                isModify = false;
            }

            SetDrugIconAdapter();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetDrugIconAdapter() {
        try {
            myAdapter = new MyAdapter(getSupportFragmentManager());
            viewIDModel.viewPager.setAdapter(myAdapter);
            viewIDModel.tabLayout.setupWithViewPager(viewIDModel.viewPager, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetHintText(ViewIDModel viewIDModel) {
        try {
            viewIDModel.DrugNameEditTextTHint.setHint(getString(R.string.drugName));
            viewIDModel.MrpEditTextTHint.setHint(getString(R.string.drugMRP));
            viewIDModel.QuantityEditTextTHint.setHint(getString(R.string.drugQuantity));
            viewIDModel.DiscountEditTextTHint.setHint(getString(R.string.drugDiscount));
            viewIDModel.ManufacturerEditTextTHint.setHint(getString(R.string.drugManufacturer));
            viewIDModel.BatchNumberEditTextTHint.setHint(getString(R.string.batchNumber));

            //viewIDModel.OkTextView.setTextColor(getResources().getColor(R.color.White));
            // viewIDModel.CancelTextView.setTextColor(getResources().getColor(R.color.White));

            viewIDModel.TransactionDateTextView.setText(mainActivity.GetCurrentDate());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean AddUpdateItem(StringHolderModel stringHolderModel, DrugModel editModel, String fromIconFont, int position) {
        try {
            DrugModel drugModel = new DrugModel();

            if (searchDrugModel == null) {
                if (isModify)
                    drugModel.DrugID = editModel.DrugID;
                else
                    drugModel.DrugID = utility.CreateID();
            } else {

                if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, searchDrugModel.DrugName))
                    drugModel.DrugID = searchDrugModel.DrugID;// when select search drug and then edit it
                else
                    drugModel.DrugID = utility.CreateID();

            }

            drugModel.DrugName = stringHolderModel.drugName;
            drugModel.DrugMRP = Double.parseDouble(stringHolderModel.drugMRP);
            drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);

            try {
                drugModel.DrugQuantity = Integer.parseInt(stringHolderModel.drugQuantity);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                MainActivity.ShowToast(AddInventoryActivity.this, getString(R.string.enterValidQuantity));
                return false;
            }

            try {
                if (!StringUtils.isBlank(stringHolderModel.drugDiscount)) {
                    drugModel.DrugDiscount = Float.valueOf(stringHolderModel.drugDiscount);
                } else {
                    drugModel.DrugDiscount = 0;
                }
                drugModel.DrugDiscountString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugDiscount);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            drugModel.DrugExpiryDate = stringHolderModel.drugExpiryDate;
            drugModel.DateInMilliSecond = mainActivity.GetMilliSecondsFromDate(drugModel.DrugExpiryDate);
            drugModel.DrugTransactionDate = stringHolderModel.drugTransactionDate;
            drugModel.DrugManufacturer = stringHolderModel.drugManufacturer;
            drugModel.BatchNumber = stringHolderModel.batchNumber;
            drugModel.DrugCategory = fromIconFont;

            mainActivity.InsertUpdateDrugsInInventoryDB(AddInventoryActivity.this, drugModel, isModify);

            if (searchDrugModel == null && !isModify) {
                drugModel.IsNeedSync = true;
                mainActivity.InsertDrugsInMasterDB(AddInventoryActivity.this, drugModel);
            }

            searchDrugModel = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            searchDrugModel = null;
        }
        return true;
    }


    public void EditTypeStop(final ViewIDModel viewIDModel, final String searchText, long timeMilliSecond, final boolean isDrugSearch) {
        try {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // When you need to modify a UI element, do so on the UI thread.
                    // 'getActivity()' is required as this is being ran from a Fragment.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            if (isDrugSearch) {
                                SetSearchDrugAdapter(searchText, viewIDModel);
                            } else {
                                SetSearchDrugManufacturerAdapter(searchText, viewIDModel);
                            }

                        }
                    });
                }
            }, timeMilliSecond); //  600ms delay before the timer executes the „run“ method from TimerTask

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSearchDrugAdapter(final String searchText, final ViewIDModel viewIDModel) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {

            drugModelArrayList = mainActivity.GetSearchDrugListFromMasterDB(AddInventoryActivity.this, searchText);

            if (drugModelArrayList.size() > 0) {
                viewIDModel.DrugNameRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchDrugAdapter = new SearchDrugAdapter(AddInventoryActivity.this, drugModelArrayList);
                viewIDModel.DrugNameRecyclerView.setLayoutManager(new LinearLayoutManager(AddInventoryActivity.this));
                viewIDModel.DrugNameRecyclerView.addItemDecoration(new DividerItemDecoration(AddInventoryActivity.this));
                viewIDModel.DrugNameRecyclerView.setAdapter(searchDrugAdapter);
            } else {
                SetRecyclerViewVisibility(viewIDModel, false);
            }
            // Setup item Clicks
            viewIDModel.DrugNameRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(AddInventoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                if (timer != null) {
                                    timer.cancel();
                                    timer.purge();
                                    isSearchMedClicked = true;
                                    isSearchManufacturerClicked = true;
                                }
                                searchDrugModel = searchDrugAdapter.getItem(position);
                                SetSearchedDrugDetails(searchDrugModel, viewIDModel);
                                Log.i(TAG, "SetSearchDrugAdapter item click : " + searchDrugModel);
                                SetRecyclerViewVisibility(viewIDModel, false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    })
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSearchDrugManufacturerAdapter(String searchText, final ViewIDModel viewIDModel) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {

            drugModelArrayList = mainActivity.GetDrugManufacturerListFromManufacturerDB(AddInventoryActivity.this, searchText);

            if (drugModelArrayList.size() > 0) {
                viewIDModel.DrugManufacturerRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchDrugManufacturerAdapter = new SearchDrugManufacturerAdapter(AddInventoryActivity.this, drugModelArrayList);
                viewIDModel.DrugManufacturerRecyclerView.setLayoutManager(new LinearLayoutManager(AddInventoryActivity.this));
                viewIDModel.DrugManufacturerRecyclerView.addItemDecoration(new DividerItemDecoration(AddInventoryActivity.this));
                viewIDModel.DrugManufacturerRecyclerView.setAdapter(searchDrugManufacturerAdapter);
            } else {
                SetRecyclerViewVisibility(viewIDModel, true);
            }

            // Setup item Clicks
            viewIDModel.DrugManufacturerRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(AddInventoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                if (timer != null) {
                                    timer.cancel();
                                    timer.purge();
                                    isSearchMedClicked = true;
                                    isSearchManufacturerClicked = true;
                                }
                                DrugModel drugModel = searchDrugManufacturerAdapter.getItem(position);
                                viewIDModel.ManufacturerEditText.setText(drugModel.DrugManufacturer);
                                viewIDModel.ManufacturerEditText.setSelection(viewIDModel.ManufacturerEditText.length());
                                Log.i(TAG, "SetSearchDrugAdapter item click : " + drugModel);
                                SetRecyclerViewVisibility(viewIDModel, true);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSearchedDrugDetails(DrugModel drugModel, ViewIDModel viewIDModel) {
        try {
            viewIDModel.DrugNameEditText.setText(drugModel.DrugName);
            viewIDModel.DrugNameEditText.setSelection(viewIDModel.DrugNameEditText.length());
            viewIDModel.ManufacturerEditText.setText(drugModel.DrugManufacturer);
            viewIDModel.MrpEditText.setText(drugModel.DrugMRPString);
            viewIDModel.DiscountEditText.setText(drugModel.DrugDiscountString);

            viewIDModel.TransactionDateTextView.setText(mainActivity.GetCurrentDate());
            // viewIDModel.SpinnerCategory.setSelection(drugCategories.indexOf(drugModel.DrugCategory));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetRecyclerViewVisibility(ViewIDModel viewIDModel, boolean isManufacturer) {
        try {
            if (isManufacturer) {
                viewIDModel.DrugManufacturerRecyclerView.setAdapter(null);
                viewIDModel.DrugManufacturerRecyclerViewLayout.setVisibility(View.GONE);
            } else {
                viewIDModel.DrugNameRecyclerView.setAdapter(null);
                viewIDModel.DrugNameRecyclerViewLayout.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void GotoAddInventoryActivity(Context context, DrugModel drugEditModel, boolean isModify) {
        try {

            Bundle bundle = new Bundle();
            bundle.putSerializable(context.getString(R.string.drugModel), drugEditModel);
            bundle.putSerializable(context.getString(R.string.isModify), isModify);

            Intent intent = new Intent(context, AddInventoryActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    class MyAdapter extends FragmentStatePagerAdapter {
        ArrayList<HashMap> mapList = null;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mapList = utility.GetDrugIcon(AddInventoryActivity.this);
        }

        @Override
        public Fragment getItem(int position) {
            for (int i = 0; i < mapList.size(); i++) {
                HashMap hashMapsList = mapList.get(i);
                if (i == position) {
                    return FragmentForIcons.newInstance(hashMapsList, fromDrugIcon);
                }
            }
            return FragmentForIcons.newInstance(null, fromDrugIcon);
        }

        @Override
        public int getCount() {
            return mapList != null ? mapList.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return "";
        }
    }

    public void SetDrugIcon(String iconFont) {
        this.iconFont = iconFont;
        fromDrugIcon = iconFont;
        Log.i("tag", "new iconfont" + iconFont);
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
            HomeActivity.GotoHomeActivity(AddInventoryActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
