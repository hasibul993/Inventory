package com.inventory.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.inventory.Activities.MainActivity;
import com.inventory.Adapter.CustomSpinnerAdapter;
import com.inventory.Adapter.ProcurementAdapter;
import com.inventory.Adapter.SearchDrugAdapter;
import com.inventory.Adapter.SearchDrugManufacturerAdapter;
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

public class InventoryFragment extends Fragment {

    private static final String TAG = "InventoryFragment";
    View rootView;
    RecyclerView recyclerView;
    ProcurementAdapter procurementAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
    HashMap<String, DrugModel> drugModelHashMap = new HashMap<>();
    Utility utility = new Utility();
    boolean isModify = false, isSearchMedClicked = false, isSearchManufacturerClicked = false;
    public static InventoryFragment inventoryFragment;

    ArrayList<String> drugCategories;

    private Timer timer;
    DrugModel searchDrugModel = null;
    SearchDrugAdapter searchDrugAdapter;
    SearchDrugManufacturerAdapter searchDrugManufacturerAdapter;

    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.inventory_fragment, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            mainActivity = MainActivity.getInstance();

            //drugModelHashMap = mainActivity.GetDrugHashMap(drugModelArrayList);
            GetDrugsLocally(null);

            drugCategories = Utility.GetDrugCategoryList(getActivity());

            inventoryFragment = this;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void GetDrugsLocally(String searchedText) {
        try {

            drugModelArrayList = mainActivity.GetInventoryListFromInventoryDB(getActivity(), searchedText);

            SetAdapter(drugModelArrayList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetAdapter(ArrayList<DrugModel> drugModelArrayList) {
        try {
            procurementAdapter = new ProcurementAdapter(getActivity(), drugModelArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(procurementAdapter);
            procurementAdapter.notifyDataSetChanged();
        } catch (Exception ex) {

        }
    }

    public void ShowDialogAddUpdateDrug(final DrugModel editModel, final int position) {

        RelativeLayout cancelOkRelaLayout;
        String prevDrugCategory = null;

        final ViewIDModel viewIDModel = new ViewIDModel();

        try {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.add_drug);
            dialog.setCancelable(false);

            //product ids
            viewIDModel.DrugNameEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.drugNameET_Hint);
            viewIDModel.MrpEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.mrpET_Hint);
            viewIDModel.QuantityEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.quantityET_Hint);
            viewIDModel.DiscountEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.discountET_Hint);
            viewIDModel.ManufacturerEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.manufacturerET_Hint);
            viewIDModel.BatchNumberEditTextTHint = (TextInputLayout) dialog.findViewById(R.id.batchNumberET_Hint);

            viewIDModel.SpinnerCategory = (Spinner) dialog.findViewById(R.id.spinnerCategory);

            viewIDModel.DrugNameEditText = (EditText) dialog.findViewById(R.id.drugNameET);
            viewIDModel.MrpEditText = (EditText) dialog.findViewById(R.id.mrpET);
            viewIDModel.QuantityEditText = (EditText) dialog.findViewById(R.id.quantityET);
            viewIDModel.DiscountEditText = (EditText) dialog.findViewById(R.id.discountET);
            viewIDModel.ManufacturerEditText = (EditText) dialog.findViewById(R.id.manufacturerET);
            viewIDModel.BatchNumberEditText = (EditText) dialog.findViewById(R.id.batchNumberET);

            viewIDModel.ExpiryDateTextView = (RobotoTextView) dialog.findViewById(R.id.expiryDateTV);
            viewIDModel.TransactionDateTextView = (RobotoTextView) dialog.findViewById(R.id.transactionDateTV);

           /*Below ids isfor drug search RecyclerView and manufacturer search RecyclerView*/
            viewIDModel.DrugNameRecyclerView = (RecyclerView) dialog.findViewById(R.id.drugNameRecyclerView);
            viewIDModel.DrugManufacturerRecyclerView = (RecyclerView) dialog.findViewById(R.id.drugManufacturerRecyclerView);
            viewIDModel.DrugNameRecyclerViewLayout = (LinearLayout) dialog.findViewById(R.id.drugNameRecyclerViewLayout);
            viewIDModel.DrugManufacturerRecyclerViewLayout = (LinearLayout) dialog.findViewById(R.id.drugManufacturerRecyclerViewLayout);

            viewIDModel.ResultOfTextViewDrugNameRecyclerView = (RobotoTextView) dialog.findViewById(R.id.resultOfTextViewDrugNameRecyclerView);
            viewIDModel.ResultOfTextViewManufacturerRecyclerView = (RobotoTextView) dialog.findViewById(R.id.resultOfTextViewManufacturerRecyclerView);
            viewIDModel.DeleteIconDrugNameRecyclerView = (ImageView) dialog.findViewById(R.id.deleteIconDrugNameRecyclerView);
            viewIDModel.DeleteIconManufacturerRecyclerView = (ImageView) dialog.findViewById(R.id.deleteIconManufacturerRecyclerView);

            //cancel ok btn ids
            viewIDModel.OkTextView = (RobotoTextView) dialog.findViewById(R.id.okTV);
            viewIDModel.CancelTextView = (RobotoTextView) dialog.findViewById(R.id.cancelTV);

            cancelOkRelaLayout = (RelativeLayout) dialog.findViewById(R.id.cancelOkRelaLayout);
            GradientDrawable cancelOkRelaLayoutBackg = (GradientDrawable) cancelOkRelaLayout.getBackground();
            cancelOkRelaLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));

            // drugNameET_Hint.set(Color.parseColor(MainActivity.GetThemeColor()));
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

                viewIDModel.OkTextView.setText(getString(R.string.update));

            } else {
                isModify = false;
            }

            SetSpinnerAdapter(viewIDModel.SpinnerCategory, prevDrugCategory);

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
                        utility.ShowDatePicker(getActivity(), viewIDModel.ExpiryDateTextView);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
                        stringHolderModel.drugExpiryDate = viewIDModel.ExpiryDateTextView.getText().toString().trim();
                        stringHolderModel.drugTransactionDate = viewIDModel.TransactionDateTextView.getText().toString().trim();
                        stringHolderModel.drugManufacturer = viewIDModel.ManufacturerEditText.getText().toString().trim();
                        stringHolderModel.batchNumber = viewIDModel.BatchNumberEditText.getText().toString().trim();

                        if (StringUtils.isBlank(stringHolderModel.drugName)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugName));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugMRP)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugMRP));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugQuantity)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugQuantity));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugManufacturer)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugManufacturer));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.batchNumber)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterBatchNumber));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugExpiryDate)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.chooseExpiryDate));
                            return;
                        } else {
                            boolean isDataOk = AddUpdateItem(stringHolderModel, editModel, viewIDModel.SpinnerCategory, position);
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


            dialog.show();

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

            viewIDModel.OkTextView.setTextColor(getActivity().getResources().getColor(R.color.White));
            viewIDModel.CancelTextView.setTextColor(getActivity().getResources().getColor(R.color.White));

            viewIDModel.TransactionDateTextView.setText(mainActivity.GetCurrentDate());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean AddUpdateItem(StringHolderModel stringHolderModel, DrugModel editModel, Spinner spinnerCategory, int position) {
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
                MainActivity.ShowToast(getActivity(), getString(R.string.enterValidQuantity));
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
            drugModel.DrugTransactionDate = stringHolderModel.drugTransactionDate;
            drugModel.DrugManufacturer = stringHolderModel.drugManufacturer;
            drugModel.BatchNumber = stringHolderModel.batchNumber;
            drugModel.DrugCategory = spinnerCategory.getSelectedItem().toString();

            if (isModify)
                procurementAdapter.UpdateItem(drugModel, position);
            else {
                if (searchDrugModel == null) {
                    procurementAdapter.AddItem(drugModel);
                    recyclerView.scrollToPosition(0);
                } else {
                    if (StringUtils.equalsIgnoreCase(stringHolderModel.drugName, searchDrugModel.DrugName)) {
                        int itemIndex = mainActivity.GetItemPosition(drugModelArrayList, drugModel.DrugID, drugModel.BatchNumber);
                        if (itemIndex >= 0) {
                            DrugModel existDrugModel = procurementAdapter.getItem(itemIndex);
                            existDrugModel.DrugName=drugModel.DrugName;
                            existDrugModel.DrugMRP=drugModel.DrugMRP;
                            existDrugModel.DrugQuantity=drugModel.DrugQuantity + existDrugModel.DrugQuantity;
                            existDrugModel.DrugDiscount=drugModel.DrugDiscount;
                            existDrugModel.DrugManufacturer=drugModel.DrugManufacturer;
                            existDrugModel.DrugCategory=drugModel.DrugCategory;
                            existDrugModel.BatchNumber=drugModel.BatchNumber;
                            existDrugModel.DrugExpiryDate=drugModel.DrugExpiryDate;
                            existDrugModel.DrugTransactionDate=drugModel.DrugTransactionDate;
                            procurementAdapter.notifyItemChanged(itemIndex);
                           // procurementAdapter.UpdateItem(drugModel, itemIndex);// when select search drug and then edit it
                        } else {
                            procurementAdapter.AddItem(drugModel);
                            recyclerView.scrollToPosition(0);
                        }
                    } else {
                        procurementAdapter.AddItem(drugModel);
                        recyclerView.scrollToPosition(0);
                    }
                }

            }

            mainActivity.InsertUpdateDrugs(getActivity(), drugModel, isModify);

            searchDrugModel = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            searchDrugModel = null;
        }
        return true;
    }

    private void SetSpinnerAdapter(Spinner categorySpinner, String selectedItem) {

        try {

            CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, drugCategories);
            categorySpinner.setAdapter(adapter);

            if (!StringUtils.isBlank(selectedItem))
                categorySpinner.setSelection(drugCategories.indexOf(selectedItem));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void EditTypeStop(final ViewIDModel viewIDModel, final String searchText, long timeMilliSecond, final boolean isDrugSearch) {
        try {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // When you need to modify a UI element, do so on the UI thread.
                    // 'getActivity()' is required as this is being ran from a Fragment.
                    getActivity().runOnUiThread(new Runnable() {
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

            drugModelArrayList = mainActivity.GetSearchDrugListFromMasterDB(getActivity(), searchText);

            if (drugModelArrayList.size() > 0) {
                viewIDModel.DrugNameRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchDrugAdapter = new SearchDrugAdapter(getActivity(), drugModelArrayList);
                viewIDModel.DrugNameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewIDModel.DrugNameRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
                viewIDModel.DrugNameRecyclerView.setAdapter(searchDrugAdapter);
            } else {
                SetRecyclerViewVisibility(viewIDModel, false);
            }
            // Setup item Clicks
            viewIDModel.DrugNameRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
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

            drugModelArrayList = mainActivity.GetDrugManufacturerListFromMasterDB(getActivity(), searchText);

            if (drugModelArrayList.size() > 0) {
                viewIDModel.DrugManufacturerRecyclerViewLayout.setVisibility(View.VISIBLE);
                searchDrugManufacturerAdapter = new SearchDrugManufacturerAdapter(getActivity(), drugModelArrayList);
                viewIDModel.DrugManufacturerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                viewIDModel.DrugManufacturerRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
                viewIDModel.DrugManufacturerRecyclerView.setAdapter(searchDrugManufacturerAdapter);
            } else {
                SetRecyclerViewVisibility(viewIDModel, true);
            }

            // Setup item Clicks
            viewIDModel.DrugManufacturerRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
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
            viewIDModel.SpinnerCategory.setSelection(drugCategories.indexOf(drugModel.DrugCategory));
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

    private void UpdateHashMap(DrugModel drugModel) {
        try {
            String uniqueKey = mainActivity.GetUniqueKey(drugModel.DrugID, drugModel.BatchNumber);
            if (!drugModelHashMap.containsKey(uniqueKey))
                drugModelHashMap.put(uniqueKey, drugModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
