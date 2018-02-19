package com.inventory.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.inventory.Adapter.InventoryAdapter;
import com.inventory.Adapter.SalesAdapter;
import com.inventory.Adapter.SearchDrugAdapter;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class SalesActivity extends AppCompatActivity {

    private static final String TAG = "SalesActivity";

    private Toolbar toolbar;
    TextView toolbar_title;

    RobotoTextView slNoTV, drugNameTV, drugQuantityTV, drugMRPTV, drugDiscountTV, drugTotalTV;

    EditText cutomerNameET, cutomerMobileET, patientNameET, ageET;

    RadioGroup radioGroup;

    View recyclerViewHeaderID;

    RadioButton maleRadioButton, femaleRadioButton;

    SalesAdapter salesAdapter;
    RecyclerView recyclerView;

    boolean isModify = false, isSearchMedClicked = false;

    FloatingActionButton floatActionButton;

    Utility utility = Utility.getInstance();

    MainActivity mainActivity = MainActivity.getInstance();

    SearchDrugAdapter searchDrugAdapter;

    DrugModel searchDrugModel = null;
    private Timer timer;

    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_activity);

        InitializeIDS();

        RecreateLayout();

        SetAdapter();

        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ShowDialogAddDrug(null, -1);
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

            /*listview header ids*/
            recyclerViewHeaderID = (View) findViewById(R.id.recyclerViewHeaderID);
            slNoTV = (RobotoTextView) findViewById(R.id.slNoTV);
            drugNameTV = (RobotoTextView) findViewById(R.id.drugNameTV);
            drugQuantityTV = (RobotoTextView) findViewById(R.id.drugQuantityTV);
            drugMRPTV = (RobotoTextView) findViewById(R.id.drugMRPTV);
            drugDiscountTV = (RobotoTextView) findViewById(R.id.drugDiscountTV);
            drugTotalTV = (RobotoTextView) findViewById(R.id.drugTotalTV);

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

            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_cross_white_icon);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            utility.SetFabColor(SalesActivity.this, floatActionButton);

            SetRecyclerViewHeaderBold();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetRecyclerViewHeaderBold() {
        try {
            Typeface tMedium = Typeface.createFromAsset(getAssets(), AppConstants.ROBOTO_MEDIUM);
            slNoTV.setTypeface(tMedium);
            drugNameTV.setTypeface(tMedium);
            drugQuantityTV.setTypeface(tMedium);
            drugMRPTV.setTypeface(tMedium);
            drugDiscountTV.setTypeface(tMedium);
            drugTotalTV.setTypeface(tMedium);

            slNoTV.setTextColor(getResources().getColor(R.color.Black));
            drugNameTV.setTextColor(getResources().getColor(R.color.Black));
            drugQuantityTV.setTextColor(getResources().getColor(R.color.Black));
            drugMRPTV.setTextColor(getResources().getColor(R.color.Black));
            drugDiscountTV.setTextColor(getResources().getColor(R.color.Black));
            drugTotalTV.setTextColor(getResources().getColor(R.color.Black));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetAdapter() {
        try {
            salesAdapter = new SalesAdapter(SalesActivity.this, drugModelArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
            recyclerView.addItemDecoration(new DividerItemDecoration(SalesActivity.this));
            recyclerView.setAdapter(salesAdapter);
            salesAdapter.notifyDataSetChanged();
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


    public void ShowDialogAddDrug(final DrugModel editModel, final int position) {

        RelativeLayout cancelOkRelaLayout;
        final ViewIDModel viewIDModel = new ViewIDModel();

        try {

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.sales_add_drug);
            dialog.setCancelable(false);

            viewIDModel.DrugNameEditText = (EditText) dialog.findViewById(R.id.drugNameET);
            viewIDModel.MrpEditText = (EditText) dialog.findViewById(R.id.mrpET);
            viewIDModel.QuantityEditText = (EditText) dialog.findViewById(R.id.quantityET);
            viewIDModel.DiscountEditText = (EditText) dialog.findViewById(R.id.discountET);

            viewIDModel.DrugNameRecyclerView = (RecyclerView) dialog.findViewById(R.id.drugNameRecyclerView);
            viewIDModel.DrugNameRecyclerViewLayout = (LinearLayout) dialog.findViewById(R.id.drugNameRecyclerViewLayout);
            //cancel ok btn ids
            viewIDModel.OkTextView = (RobotoTextView) dialog.findViewById(R.id.okTV);
            viewIDModel.CancelTextView = (RobotoTextView) dialog.findViewById(R.id.cancelTV);
            viewIDModel.OkTextView.setTextColor(getResources().getColor(R.color.White));
            viewIDModel.CancelTextView.setTextColor(getResources().getColor(R.color.White));

            viewIDModel.QtyAvailableTV = (RobotoTextView) dialog.findViewById(R.id.QtyAvailableTV);
            viewIDModel.ResultOfTextViewDrugNameRecyclerView = (RobotoTextView) dialog.findViewById(R.id.resultOfTextViewDrugNameRecyclerView);
            viewIDModel.DeleteIconDrugNameRecyclerView = (ImageView) dialog.findViewById(R.id.deleteIconDrugNameRecyclerView);

            cancelOkRelaLayout = (RelativeLayout) dialog.findViewById(R.id.cancelOkRelaLayout);
            GradientDrawable cancelOkRelaLayoutBackg = (GradientDrawable) cancelOkRelaLayout.getBackground();
            cancelOkRelaLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));


            if (editModel != null) {
                isModify = true;
                viewIDModel.DrugNameEditText.setText(editModel.DrugName);
                viewIDModel.DrugNameEditText.setSelection(viewIDModel.DrugNameEditText.length());
                viewIDModel.MrpEditText.setText(editModel.DrugMRPString);
                viewIDModel.QuantityEditText.setText(editModel.DrugQuantity + "");
                viewIDModel.DiscountEditText.setText(editModel.DrugDiscountString);
            } else {
                isModify = false;
            }

            viewIDModel.DrugNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        String searchText = viewIDModel.DrugNameEditText.getText().toString();

                        if (!isSearchMedClicked && (searchText.length() >= 2)) {
                            viewIDModel.ResultOfTextViewDrugNameRecyclerView.setText(getString(R.string.resultOf) + " '" + searchText + "'");
                            EditTypeStop(viewIDModel, searchText, 400);
                        } else {
                            SetRecyclerViewVisibility(viewIDModel);
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


            viewIDModel.OkTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringHolderModel stringHolderModel = new StringHolderModel();
                    try {
                        stringHolderModel.drugName = viewIDModel.DrugNameEditText.getText().toString().trim();
                        stringHolderModel.drugMRP = viewIDModel.MrpEditText.getText().toString().trim();
                        stringHolderModel.drugQuantity = viewIDModel.QuantityEditText.getText().toString().trim();
                        stringHolderModel.drugDiscount = viewIDModel.DiscountEditText.getText().toString().trim();


                        if (StringUtils.isBlank(stringHolderModel.drugName)) {
                            MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterDrugName));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugMRP)) {
                            MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterDrugMRP));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugQuantity)) {
                            MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterDrugQuantity));
                            return;
                        } else {
                            boolean isDataOk = AddUpdateItem(stringHolderModel, editModel);
                            if (isDataOk)
                                dialog.dismiss();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            viewIDModel.CancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            viewIDModel.DeleteIconDrugNameRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        SetRecyclerViewVisibility(viewIDModel);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });


            dialog.show();

        } catch (Exception ex) {

        }
    }

    private boolean AddUpdateItem(StringHolderModel stringHolderModel, DrugModel editModel) {
        try {
            DrugModel drugModel = new DrugModel();

            if (searchDrugModel == null) {
                if (isModify)
                    drugModel.DrugID = editModel.DrugID;
                else
                    drugModel.DrugID = utility.CreateID();
            } else {

                if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, searchDrugModel.DrugName)) {
                    drugModel.DrugID = searchDrugModel.DrugID;// when select search drug and then edit it
                } else {
                    drugModel.DrugID = utility.CreateID();
                    searchDrugModel = null;
                }

            }

            drugModel.DrugName = stringHolderModel.drugName.toUpperCase();
            drugModel.DrugMRP = Double.parseDouble(stringHolderModel.drugMRP);
            drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);

            try {
                drugModel.DrugQuantity = Integer.parseInt(stringHolderModel.drugQuantity);
                if (drugModel.DrugQuantity == 0) {
                    MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterValidQuantity));
                    return false;
                }

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterValidQuantity));
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

            try {
                Double totalPrice = drugModel.DrugMRP * drugModel.DrugQuantity;
                Double discount_for_each_product = totalPrice * (Double.valueOf(drugModel.DrugDiscount) / 100);
                drugModel.DrugTotalMRP = totalPrice - discount_for_each_product;
                drugModel.DrugTotalMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugTotalMRP);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (isModify) {
                if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, editModel.DrugName)) {
                    UpdateItem(drugModel);
                } else {
                    salesAdapter.AddItem(drugModel);
                    recyclerView.scrollToPosition(0);
                }
            } else {
                if (searchDrugModel == null) {
                    salesAdapter.AddItem(drugModel);
                    recyclerView.scrollToPosition(0);
                } else {
                    if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, searchDrugModel.DrugName)) {
                        UpdateItem(drugModel);
                    } else {
                        salesAdapter.AddItem(drugModel);
                        recyclerView.scrollToPosition(0);
                    }
                }

            }


            //mainActivity.InsertUpdateDrugsInInventoryDB(SalesActivity.this, drugModel, isModify);

            if (searchDrugModel == null && !isModify) {
                drugModel.IsNeedSync = true;
                // mainActivity.InsertDrugsInMasterDB(SalesActivity.this, drugModel);
            } else {
                drugModel.DateInMilliSecond = mainActivity.GetMilliSecondsFromDate(searchDrugModel.DrugExpiryDate);
                drugModel.DrugManufacturer = searchDrugModel.DrugManufacturer.toUpperCase();
                drugModel.BatchNumber = searchDrugModel.BatchNumber;
            }

            searchDrugModel = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            searchDrugModel = null;
        }
        return true;
    }

    private void UpdateItem(DrugModel drugModel) {
        try {
            int itemIndex = mainActivity.GetItemPosition(drugModelArrayList, drugModel.DrugID, drugModel.BatchNumber);
            if (itemIndex >= 0) {
                DrugModel existDrugModel = salesAdapter.getItem(itemIndex);
                existDrugModel.DrugName = drugModel.DrugName;
                existDrugModel.DrugMRP = drugModel.DrugMRP;
                existDrugModel.DrugMRPString = drugModel.DrugMRPString;
                existDrugModel.DrugQuantity = drugModel.DrugQuantity;
                existDrugModel.DrugQuantity = drugModel.DrugQuantity;
                existDrugModel.DrugDiscount = drugModel.DrugDiscount;
                existDrugModel.DrugDiscountString = drugModel.DrugDiscountString;
                existDrugModel.DrugTotalMRP = drugModel.DrugTotalMRP;
                existDrugModel.DrugTotalMRPString = drugModel.DrugTotalMRPString;
                salesAdapter.notifyItemChanged(itemIndex);
                // procurementAdapter.UpdateItem(drugModel, itemIndex);// when select search drug and then edit it
            } else {
                salesAdapter.AddItem(drugModel);
                recyclerView.scrollToPosition(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void EditTypeStop(final ViewIDModel viewIDModel, final String searchText, long timeMilliSecond) {
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
                            SetSearchDrugAdapter(searchText, viewIDModel);
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

            drugModelArrayList = mainActivity.GetInventoryListFromInventoryDB(SalesActivity.this, searchText, true);

            if (drugModelArrayList.size() > 0) {
                viewIDModel.DrugNameRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchDrugAdapter = new SearchDrugAdapter(SalesActivity.this, drugModelArrayList);
                viewIDModel.DrugNameRecyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
                viewIDModel.DrugNameRecyclerView.addItemDecoration(new DividerItemDecoration(SalesActivity.this));
                viewIDModel.DrugNameRecyclerView.setAdapter(searchDrugAdapter);
            } else {
                SetRecyclerViewVisibility(viewIDModel);
            }
            // Setup item Clicks
            viewIDModel.DrugNameRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(SalesActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                if (timer != null) {
                                    timer.cancel();
                                    timer.purge();
                                    isSearchMedClicked = true;
                                }
                                searchDrugModel = searchDrugAdapter.getItem(position);
                                SetSearchedDrugDetails(searchDrugModel, viewIDModel);
                                Log.i(TAG, "SetSearchDrugAdapter item click : " + searchDrugModel);
                                SetRecyclerViewVisibility(viewIDModel);
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
            viewIDModel.DrugNameEditText.setText(drugModel.DrugName.toUpperCase());
            viewIDModel.DrugNameEditText.setSelection(viewIDModel.DrugNameEditText.length());
            viewIDModel.MrpEditText.setText(drugModel.DrugMRPString);
            viewIDModel.DiscountEditText.setText(drugModel.DrugDiscountString);

            viewIDModel.QtyAvailableTV.setText(getString(R.string.orders) + " " + drugModel.DrugQuantity);
            viewIDModel.QtyAvailableTV.setTextColor(MainActivity.GetThemeColorInt());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetRecyclerViewVisibility(ViewIDModel viewIDModel) {
        try {

            viewIDModel.DrugNameRecyclerView.setAdapter(null);
            viewIDModel.DrugNameRecyclerViewLayout.setVisibility(View.GONE);

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
            getMenuInflater().inflate(R.menu.menu_sales_activity, menu);
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
            case R.id.menu_save:
                OnDonePressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void OnDonePressed() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
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
