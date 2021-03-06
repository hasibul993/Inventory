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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inventory.Adapter.SalesAdapter;
import com.inventory.Adapter.SearchCustomerMobileAdapter;
import com.inventory.Adapter.SearchDrugAdapter;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.RecyclerItemClickListener;
import com.inventory.Helper.Utility;
import com.inventory.Model.DrugModel;
import com.inventory.Model.StringHolderModel;
import com.inventory.Model.UserKeyDetailsModel;
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

    RobotoTextView slNoTV, drugNameTV, drugQuantityTV, drugMRPTV, drugDiscountTV, drugTotalTV, orderTotalTV,
            orderTotalValueTV, orderTotalActualTV, orderTotalActualValueTV, orderTotalDiscTV, orderTotalDiscValueTV,
            orderTotalFinalTV, orderTotalFinalValueTV, resultOfTextViewCustomerMobileRecyclerView, orderTotalTaxTV, orderTotalTaxValueTV;

    EditText cutomerNameET, cutomerMobileET, patientNameET, ageET;

    RelativeLayout orderTotalDiscLayout, orderTotalTaxLayout;

    ImageView deleteIconCustomerMobileRecyclerView;

    RadioGroup radioGroup;

    View recyclerViewHeaderID, bottomOrderTotalLayout;

    RadioButton maleRadioButton, femaleRadioButton;

    SalesAdapter salesAdapter;
    RecyclerView recyclerView, customerMobileRecyclerView;

    LinearLayout customerMobileSearchRecyclerViewLayout;

    boolean isModify = false, isSearchMedClicked = false, isSearchCustomerMobileClicked;

    FloatingActionButton floatActionButton;

    Utility utility = Utility.getInstance();

    MainActivity mainActivity = MainActivity.getInstance();

    SearchDrugAdapter searchDrugAdapter;

    SearchCustomerMobileAdapter searchCustomerMobileAdapter;

    DrugModel searchDrugModel = null;
    private Timer timer;

    UserKeyDetailsModel userKeyDetailsModel;

    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

    String orderNo;

    boolean isNotEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_activity);

        InitializeIDS();

        userKeyDetailsModel = mainActivity.GetUserKeyDetails(SalesActivity.this);

        Intent intent = getIntent();
        orderNo = intent.getStringExtra(getString(R.string.orderNo));
        isNotEditable = intent.getBooleanExtra(getString(R.string.isNotEdiatable), false);

        SetActionBar();

        FillLayoutWithData();

        DisableLayout();

        SetAdapter();

        SetListHeaderBottomVisibility();

        CalculateTotalCost();

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

        deleteIconCustomerMobileRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SetCustomerMobileRecyclerViewVisibility();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        cutomerMobileET.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String searchText = cutomerMobileET.getText().toString().trim();

                    if (!isSearchCustomerMobileClicked && (searchText.length() >= 2)) {
                        resultOfTextViewCustomerMobileRecyclerView.setText(getString(R.string.resultOf) + " '" + searchText + "'");
                        CustomerEditTypeStop(searchText, 400);
                    } else {
                        SetCustomerMobileRecyclerViewVisibility();
                    }
                    isSearchCustomerMobileClicked = false;
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

            /*customer mobile search ids*/
            customerMobileSearchRecyclerViewLayout = (LinearLayout) findViewById(R.id.customerMobileSearchRecyclerViewLayout);
            resultOfTextViewCustomerMobileRecyclerView = (RobotoTextView) findViewById(R.id.resultOfTextViewCustomerMobileRecyclerView);
            deleteIconCustomerMobileRecyclerView = (ImageView) findViewById(R.id.deleteIconCustomerMobileRecyclerView);
            customerMobileRecyclerView = (RecyclerView) findViewById(R.id.customerMobileRecyclerView);

            /*listview header ids*/
            recyclerViewHeaderID = (View) findViewById(R.id.recyclerViewHeaderID);
            slNoTV = (RobotoTextView) findViewById(R.id.slNoTV);
            drugNameTV = (RobotoTextView) findViewById(R.id.drugNameTV);
            drugQuantityTV = (RobotoTextView) findViewById(R.id.drugQuantityTV);
            drugMRPTV = (RobotoTextView) findViewById(R.id.drugMRPTV);
            drugDiscountTV = (RobotoTextView) findViewById(R.id.drugDiscountTV);
            drugTotalTV = (RobotoTextView) findViewById(R.id.drugTotalTV);

            /*recyclerview bottom order total ids*/
            bottomOrderTotalLayout = (View) findViewById(R.id.bottomOrderTotalLayout);
            orderTotalTV = (RobotoTextView) findViewById(R.id.orderTotalTV);
            orderTotalValueTV = (RobotoTextView) findViewById(R.id.orderTotalValueTV);
            orderTotalActualTV = (RobotoTextView) findViewById(R.id.orderTotalActualTV);
            orderTotalActualValueTV = (RobotoTextView) findViewById(R.id.orderTotalActualValueTV);
            orderTotalDiscTV = (RobotoTextView) findViewById(R.id.orderTotalDiscTV);
            orderTotalDiscValueTV = (RobotoTextView) findViewById(R.id.orderTotalDiscValueTV);
            orderTotalTaxTV = (RobotoTextView) findViewById(R.id.orderTotalTaxTV);
            orderTotalTaxValueTV = (RobotoTextView) findViewById(R.id.orderTotalTaxValueTV);
            orderTotalFinalTV = (RobotoTextView) findViewById(R.id.orderTotalFinalTV);
            orderTotalFinalValueTV = (RobotoTextView) findViewById(R.id.orderTotalFinalValueTV);

            orderTotalDiscLayout = (RelativeLayout) findViewById(R.id.orderTotalDiscLayout);
            orderTotalTaxLayout = (RelativeLayout) findViewById(R.id.orderTotalTaxLayout);


            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            floatActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetActionBar() {

        try {

            setSupportActionBar(toolbar);

            MainActivity.getInstance().SupportActionBar(SalesActivity.this, getSupportActionBar(), MainActivity.GetThemeColor(), toolbar_title, getString(R.string.sale), false);

            final Drawable upArrow = getResources().getDrawable(R.drawable.vector_cross_white_icon);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            utility.SetFabColor(SalesActivity.this, floatActionButton);

            SetRecyclerViewHeaderBold();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void FillLayoutWithData() {
        DrugModel drugModel = new DrugModel();
        try {

            if (!StringUtils.isBlank(orderNo)) {
                drugModel = mainActivity.GetOrderDetailsFromOrderDB(SalesActivity.this, orderNo);
                drugModelArrayList = mainActivity.GetOrderItemListFromOrderItemDB(SalesActivity.this, orderNo);

                SetSearchedCustomerDetails(drugModel, true);

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void DisableLayout() {
        try {
            if (isNotEditable) {
                cutomerNameET.setEnabled(false);
                cutomerMobileET.setEnabled(false);
                patientNameET.setEnabled(false);
                ageET.setEnabled(false);
                maleRadioButton.setEnabled(false);
                femaleRadioButton.setEnabled(false);

                floatActionButton.setVisibility(View.GONE);
            }

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
            salesAdapter = new SalesAdapter(SalesActivity.this, drugModelArrayList, isNotEditable);
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
            viewIDModel.BatchNumberEditText = (EditText) dialog.findViewById(R.id.batchNumberET);

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
                viewIDModel.BatchNumberEditText.setText(editModel.BatchNumber);
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
                            viewIDModel.QtyAvailableTV.setText("");
                            DrugEditTypeStop(viewIDModel, searchText, 400);
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
                public void onClick(View view) {
                    StringHolderModel stringHolderModel = new StringHolderModel();
                    try {
                        stringHolderModel.drugName = viewIDModel.DrugNameEditText.getText().toString().trim();
                        stringHolderModel.drugMRP = viewIDModel.MrpEditText.getText().toString().trim();
                        stringHolderModel.drugQuantity = viewIDModel.QuantityEditText.getText().toString().trim();
                        stringHolderModel.drugDiscount = viewIDModel.DiscountEditText.getText().toString().trim();
                        stringHolderModel.batchNumber = viewIDModel.BatchNumberEditText.getText().toString().trim();

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
                            if (isDataOk) {
                                dialog.dismiss();
                                utility.HideDialogSoftKeyboard(SalesActivity.this);
                            }

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
                        utility.HideDialogSoftKeyboard(SalesActivity.this);
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
            utility.OpenDialogSoftKeyboard(SalesActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean AddUpdateItem(StringHolderModel stringHolderModel, DrugModel editModel) {
        int stockQty = 0;
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
            drugModel.BatchNumber = stringHolderModel.batchNumber;
            drugModel.DrugMRP = Double.parseDouble(stringHolderModel.drugMRP);
            drugModel.DrugMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugMRP);
            drugModel.PharmacyID = userKeyDetailsModel.UserGuid;

            stockQty = mainActivity.GetDrugQuantityFromInventoryDB(SalesActivity.this, drugModel.BatchNumber, drugModel.DrugID);

            try {
                drugModel.DrugQuantity = Integer.parseInt(stringHolderModel.drugQuantity);

                if (drugModel.DrugQuantity == 0) {
                    MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterValidQuantity));
                    return false;
                } else if (stockQty == 0) {
                    MainActivity.ShowToast(SalesActivity.this, drugModel.DrugName + " " + getString(R.string.itemOutOfStock));
                    return false;
                } else if (drugModel.DrugQuantity > stockQty) {
                    MainActivity.ShowToast(SalesActivity.this, getString(R.string.quantityStockLimit));
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

                if (drugModel.DrugDiscount > 100) {
                    MainActivity.ShowToast(SalesActivity.this, getString(R.string.discountLimit100));
                    return false;
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            try {
                Double totalPrice = drugModel.DrugMRP * drugModel.DrugQuantity;
                Double discount_for_each_product = totalPrice * (Double.valueOf(drugModel.DrugDiscount) / 100);
                drugModel.DrugTotalMRPWithoutDisc = totalPrice;
                drugModel.DrugTotalMRP = totalPrice - discount_for_each_product;
                drugModel.DrugTotalMRPString = AppConstants.decimalFormatTwoPlace.format(drugModel.DrugTotalMRP);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (isModify) {
                if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, editModel.DrugName)) {
                    int itemIndex = mainActivity.GetItemPosition(drugModelArrayList, drugModel.DrugID, drugModel.BatchNumber);
                    if (itemIndex >= 0) {
                        DrugModel existDrugModel = salesAdapter.getItem(itemIndex);
                        int netQty = drugModel.DrugQuantity;
                        if (netQty > stockQty) {
                            MainActivity.ShowToast(SalesActivity.this, stringHolderModel.drugName + " " + getString(R.string.itemExistInList));
                            return false;
                        }
                        existDrugModel.DrugQuantity = netQty;
                        existDrugModel.DrugTotalMRP = drugModel.DrugTotalMRP;
                        existDrugModel.DrugTotalMRPString = AppConstants.decimalFormatTwoPlace.format(existDrugModel.DrugTotalMRP);
                        existDrugModel.DrugTotalMRPWithoutDisc = drugModel.DrugTotalMRPWithoutDisc;
                    }
                    UpdateItem(itemIndex, drugModel);
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
                        int itemIndex = mainActivity.GetItemPosition(drugModelArrayList, drugModel.DrugID, drugModel.BatchNumber);
                        if (itemIndex >= 0) {
                            DrugModel existDrugModel = salesAdapter.getItem(itemIndex);
                            int netQty = existDrugModel.DrugQuantity + drugModel.DrugQuantity;
                            if (netQty > stockQty) {
                                MainActivity.ShowToast(SalesActivity.this, stringHolderModel.drugName + " " + getString(R.string.itemExistInList));
                                return false;
                            }
                            existDrugModel.DrugQuantity = netQty;
                            existDrugModel.DrugTotalMRP = existDrugModel.DrugTotalMRP + drugModel.DrugTotalMRP;
                            existDrugModel.DrugTotalMRPString = AppConstants.decimalFormatTwoPlace.format(existDrugModel.DrugTotalMRP);
                            existDrugModel.DrugTotalMRPWithoutDisc = existDrugModel.DrugTotalMRPWithoutDisc + drugModel.DrugTotalMRPWithoutDisc;
                        }
                        UpdateItem(itemIndex, drugModel);
                    } else {
                        salesAdapter.AddItem(drugModel);
                        recyclerView.scrollToPosition(0);
                    }
                }

            }
            SetListHeaderBottomVisibility();
            CalculateTotalCost();
            //mainActivity.InsertUpdateDrugsInInventoryDB(SalesActivity.this, drugModel, isModify);

            searchDrugModel = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            searchDrugModel = null;
        }
        return true;
    }

    private void UpdateItem(int itemIndex, DrugModel drugModel) {
        try {
            if (itemIndex >= 0) {
                DrugModel existDrugModel = salesAdapter.getItem(itemIndex);
                existDrugModel.DrugName = drugModel.DrugName;
                existDrugModel.DrugMRP = drugModel.DrugMRP;
                existDrugModel.DrugMRPString = drugModel.DrugMRPString;
                existDrugModel.DrugDiscount = drugModel.DrugDiscount;
                existDrugModel.DrugDiscountString = drugModel.DrugDiscountString;
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

    public void DrugEditTypeStop(final ViewIDModel viewIDModel, final String searchText, long timeMilliSecond) {
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

    public void CustomerEditTypeStop(final String searchText, long timeMilliSecond) {
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
                            SetSearchCustomerMobileAdapter(searchText);
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
            viewIDModel.BatchNumberEditText.setText(drugModel.BatchNumber);

            viewIDModel.QtyAvailableTV.setText(getString(R.string.availableQuantity) + " " + getString(R.string.semicolon) + " " + drugModel.DrugQuantity);
            viewIDModel.QtyAvailableTV.setTextColor(getResources().getColor(R.color.Crimson));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSearchedCustomerDetails(DrugModel drugModel, boolean isreadble) {
        try {

            cutomerMobileET.setText(drugModel.CustomerMobile);
            cutomerMobileET.setSelection(cutomerMobileET.length());
            cutomerNameET.setText(drugModel.CustomerName);

            if (isreadble) {
                patientNameET.setText(drugModel.PatientName);
                ageET.setText(drugModel.Age);
                if (StringUtils.equalsIgnoreCase(drugModel.Gender, getString(R.string.male)))
                    maleRadioButton.setChecked(true);
                else if (StringUtils.equalsIgnoreCase(drugModel.Gender, getString(R.string.male)))
                    femaleRadioButton.setChecked(true);
            }


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

    private void SetSearchCustomerMobileAdapter(final String searchText) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {

            drugModelArrayList = mainActivity.GetCustomerMobileListFromOrderDB(SalesActivity.this, searchText, true);

            if (drugModelArrayList.size() > 0) {
                customerMobileSearchRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchCustomerMobileAdapter = new SearchCustomerMobileAdapter(SalesActivity.this, drugModelArrayList);
                customerMobileRecyclerView.setLayoutManager(new LinearLayoutManager(SalesActivity.this));
                customerMobileRecyclerView.addItemDecoration(new DividerItemDecoration(SalesActivity.this));
                customerMobileRecyclerView.setAdapter(searchCustomerMobileAdapter);
            } else {
                SetCustomerMobileRecyclerViewVisibility();
            }
            // Setup item Clicks
            customerMobileRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(SalesActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                if (timer != null) {
                                    timer.cancel();
                                    timer.purge();
                                    isSearchCustomerMobileClicked = true;
                                }
                                DrugModel searchCustomerModel = searchCustomerMobileAdapter.getItem(position);
                                SetSearchedCustomerDetails(searchCustomerModel, false);
                                Log.i(TAG, "SetSearchCustomerMobileAdapter item click : " + searchCustomerModel);
                                SetCustomerMobileRecyclerViewVisibility();
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

    private void SetCustomerMobileRecyclerViewVisibility() {
        try {
            customerMobileRecyclerView.setAdapter(null);
            customerMobileSearchRecyclerViewLayout.setVisibility(View.GONE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetListHeaderBottomVisibility() {
        try {
            if (salesAdapter.getItemCount() > 0) {
                recyclerViewHeaderID.setVisibility(View.VISIBLE);
                bottomOrderTotalLayout.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHeaderID.setVisibility(View.GONE);
                bottomOrderTotalLayout.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void CalculateTotalCost() {
        ArrayList<DrugModel> drugModelList = new ArrayList<>();
        double overAllPrice = 0,overAllPriceWithTax = 0, overAllPriceWithoutDisc = 0, overAllDiscount, tax = 0;
        String orderTotalString, overAllPriceWithoutDiscString, overAllDiscountString, taxString;
        try {
            drugModelList = salesAdapter.getDrugList();
            for (DrugModel drug : drugModelList) {
                try {
                    overAllPrice = overAllPrice + drug.DrugTotalMRP;
                    overAllPriceWithoutDisc = overAllPriceWithoutDisc + drug.DrugTotalMRPWithoutDisc;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            overAllDiscount = overAllPriceWithoutDisc - overAllPrice;
            overAllPriceWithTax=overAllPrice+tax;

            overAllPriceWithoutDiscString = AppConstants.decimalFormatTwoPlace.format(overAllPriceWithoutDisc);
            orderTotalString = AppConstants.decimalFormatTwoPlace.format(overAllPriceWithTax);
            overAllDiscountString = AppConstants.decimalFormatTwoPlace.format(overAllDiscount);
            taxString = AppConstants.decimalFormatTwoPlace.format(tax);

            orderTotalValueTV.setText(orderTotalString);

            if (tax > 0)
                orderTotalTaxValueTV.setText(taxString);
            else
                orderTotalTaxValueTV.setText(getString(R.string.dash));

            if (overAllDiscount > 0)
                orderTotalDiscValueTV.setText(getString(R.string.dash) + " " + overAllDiscountString);
            else
                orderTotalDiscValueTV.setText(getString(R.string.dash));

            orderTotalActualValueTV.setText(overAllPriceWithoutDiscString);
            orderTotalFinalValueTV.setText(orderTotalString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void GotoSalesActivity(Context context, String orderNo, boolean isNotEditable) {
        try {
            Intent intent = new Intent(context, SalesActivity.class);
            intent.putExtra(context.getString(R.string.orderNo), orderNo);
            intent.putExtra(context.getString(R.string.isNotEdiatable), isNotEditable);
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_sales_activity, menu);
            MenuItem moreIcon = menu.findItem(R.id.menu_save);
            if (isNotEditable)
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
        String customerName, customerMobile, patientName, gender = "", age;

        try {
            customerName = cutomerNameET.getText().toString().trim();
            customerMobile = cutomerMobileET.getText().toString().trim();
            patientName = patientNameET.getText().toString().trim();
            age = ageET.getText().toString().trim();

            if (maleRadioButton.isChecked())
                gender = getString(R.string.male);
            else if (femaleRadioButton.isChecked())
                gender = getString(R.string.female);

            if (StringUtils.isBlank(customerName)) {
                MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterCustomerName));
                return;
            } else if (StringUtils.isBlank(customerMobile)) {
                MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterCustomerMobile));
                return;
            } else if (!utility.isValidPhoneNumber(customerMobile)) {
                MainActivity.ShowToast(SalesActivity.this, getString(R.string.enterValidCustomerMobile));
                return;
            } else if (salesAdapter.getItemCount() == 0) {
                MainActivity.ShowToast(SalesActivity.this, getString(R.string.addDrugToList));
                return;
            } else {
                InsertUpdateOrder(customerName, customerMobile, patientName, age, gender);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void InsertUpdateOrder(String customerName, String customerMobile, String patientName, String age, String gender) {
        ArrayList<DrugModel> drugModelList = new ArrayList<>();
        double overAllPrice = 0, overAllPriceWithoutDisc = 0;
        try {
            DrugModel drugModel = new DrugModel();

            drugModel.OrderNo = utility.CreateOrderID();
            drugModel.CustomerName = customerName;
            drugModel.CustomerMobile = customerMobile;
            drugModel.PatientName = patientName;
            drugModel.Age = age;
            drugModel.Gender = gender;
            drugModel.PharmacyID = userKeyDetailsModel.UserGuid;
            drugModel.OrderCreatedOn = mainActivity.GetCurrentDate(AppConstants.SIMPLE_DATE_TIME_FORMAT);

            drugModelList = salesAdapter.getDrugList();
            for (DrugModel drug : drugModelList) {
                try {
                    drug.OrderNo = drugModel.OrderNo;
                    overAllPrice = overAllPrice + drug.DrugTotalMRP;
                    overAllPriceWithoutDisc = overAllPriceWithoutDisc + drug.DrugTotalMRPWithoutDisc;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            drugModel.OrderTotal = overAllPrice;
            drugModel.OrderTotalString = AppConstants.decimalFormatTwoPlace.format(drugModel.OrderTotal);

            drugModel.OrderTotalDiscountPerc = (float) (((overAllPriceWithoutDisc - overAllPrice) / overAllPriceWithoutDisc) * 100);
            drugModel.OrderTotalDiscountString = AppConstants.decimalFormatTwoPlace.format(drugModel.OrderTotalDiscountPerc);

            mainActivity.InsertUpdateOrderInOrderDB(SalesActivity.this, drugModel);
            mainActivity.InsertOrderItemsInBatchInOrderItemsDB(SalesActivity.this, drugModelList);
            onBackPressed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onBackPressed() {
        try {
            if (isNotEditable)
                OrdersActivity.GotoOrdersActivity(SalesActivity.this);
            else
                HomeActivity.GotoHomeActivity(SalesActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
