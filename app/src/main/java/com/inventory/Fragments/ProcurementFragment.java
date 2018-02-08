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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.inventory.Activities.MainActivity;
import com.inventory.Adapter.CustomSpinnerAdapter;
import com.inventory.Adapter.ProcurementAdapter;
import com.inventory.Adapter.SearchDrugAdapter;
import com.inventory.Adapter.SearchDrugManufacturerAdapter;
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

public class ProcurementFragment extends Fragment {

    private static final String TAG = "ProcurementFragment";
    View rootView;
    RecyclerView recyclerView;
    ProcurementAdapter procurementAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
    Utility utility = new Utility();
    boolean isModify = false;
    public static ProcurementFragment procurementFragment;

    ArrayList<String> drugCategories;

    private Timer timer;

    SearchDrugAdapter searchDrugAdapter;
    SearchDrugManufacturerAdapter searchDrugManufacturerAdapter;

    public ProcurementFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        try {

            Bundle args = new Bundle();
            //args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.procurement_fragment, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            mainActivity = MainActivity.getInstance();
            drugModelArrayList = mainActivity.GetDrugList(getActivity(), null);

            SetAdapter(drugModelArrayList);

            drugCategories = Utility.GetDrugCategoryList(getActivity());
            procurementFragment = this;
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

    private void SetAdapter(ArrayList<DrugModel> drugModelArrayList) {
        try {
            procurementAdapter = new ProcurementAdapter(getActivity(), drugModelArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(procurementAdapter);
        } catch (Exception ex) {

        }
    }

    public void ShowDialogAddDrug(final DrugModel editModel, final int position) {
        TextInputLayout drugNameET_Hint, mrpET_Hint, quantityET_Hint, discountET_Hint, manufacturerET_Hint;
        RelativeLayout cancelOkRelaLayout;
        final EditText drugNameET, mrpET, quantityET, discountET, manufacturerET;
        final RobotoTextView okTV, cancelTV, expiryDateTV, transactionDateTV;
        final Spinner spinnerCategory;
        String prevDrugCategory = null;
        final RecyclerView drugManufacturerRecyclerView, drugNameRecyclerView;

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

            SetHintText(viewIDModel);


            viewIDModel.SpinnerCategory = (Spinner) dialog.findViewById(R.id.spinnerCategory);

            viewIDModel.DrugNameEditText = (EditText) dialog.findViewById(R.id.drugNameET);
            viewIDModel.MrpEditText = (EditText) dialog.findViewById(R.id.mrpET);
            viewIDModel.QuantityEditText = (EditText) dialog.findViewById(R.id.quantityET);
            viewIDModel.DiscountEditText = (EditText) dialog.findViewById(R.id.discountET);
            viewIDModel.ManufacturerEditText = (EditText) dialog.findViewById(R.id.manufacturerET);

            viewIDModel.DrugNameRecyclerView = (RecyclerView) dialog.findViewById(R.id.drugNameRecyclerView);
            viewIDModel.DrugManufacturerRecyclerView = (RecyclerView) dialog.findViewById(R.id.drugManufacturerRecyclerView);

            viewIDModel.ExpiryDateTextView = (RobotoTextView) dialog.findViewById(R.id.expiryDateTV);
            viewIDModel.TransactionDateTextView = (RobotoTextView) dialog.findViewById(R.id.transactionDateTV);

            cancelOkRelaLayout = (RelativeLayout) dialog.findViewById(R.id.cancelOkRelaLayout);
            GradientDrawable cancelOkRelaLayoutBackg = (GradientDrawable) cancelOkRelaLayout.getBackground();
            cancelOkRelaLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));

            //cancel ok btn ids
            viewIDModel.OkTextView = (RobotoTextView) dialog.findViewById(R.id.okTV);
            viewIDModel.CancelTextView = (RobotoTextView) dialog.findViewById(R.id.cancelTV);

            // drugNameET_Hint.set(Color.parseColor(MainActivity.GetThemeColor()));


            if (editModel != null) {

                isModify = true;
                viewIDModel.DrugNameEditText.setText(editModel.DrugName);
                viewIDModel.DrugNameEditText.setSelection(viewIDModel.DrugNameEditText.length());
                viewIDModel.MrpEditText.setText(editModel.DrugMRPString);
                viewIDModel.QuantityEditText.setText(editModel.DrugQuantity + "");
                viewIDModel.DiscountEditText.setText(editModel.DrugDiscountString);
                viewIDModel.ExpiryDateTextView.setText(editModel.DrugExpiryDate);
                viewIDModel.ManufacturerEditText.setText(editModel.DrugManufacturer);

                prevDrugCategory = editModel.DrugCategory;
            } else {
                isModify = false;
            }

            SetSpinnerAdapter(viewIDModel.SpinnerCategory, prevDrugCategory);

            viewIDModel.DrugNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        String searchText = viewIDModel.DrugNameEditText.getText().toString();

                        if ((searchText.length() >= 2)) {
                            EditTypeStop(viewIDModel, searchText, 400, viewIDModel.DrugNameRecyclerView, true);
                        } else {
                            viewIDModel.DrugNameRecyclerView.setAdapter(null);
                            viewIDModel.DrugNameRecyclerView.setVisibility(View.GONE);
                        }

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
                        if ((searchText.length() >= 2)) {
                            EditTypeStop(viewIDModel, searchText, 400, viewIDModel.DrugManufacturerRecyclerView, false);
                        } else {
                            viewIDModel.DrugManufacturerRecyclerView.setAdapter(null);
                            viewIDModel.DrugManufacturerRecyclerView.setVisibility(View.GONE);
                        }
                        //}

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

                        if (StringUtils.isBlank(stringHolderModel.drugName)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugName));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugMRP)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugMRP));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugQuantity)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugQuantity));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugDiscount)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugDiscount));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugManufacturer)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugManufacturer));
                            return;
                        } else if (StringUtils.isBlank(stringHolderModel.drugExpiryDate)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.chooseExpiryDate));
                            return;
                        } else {
                            AddUpdateItem(stringHolderModel, editModel, viewIDModel.SpinnerCategory, position);
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


            dialog.show();

        } catch (Exception ex) {

        }
    }

    private void SetHintText(ViewIDModel viewIDModel) {
        try {
            viewIDModel.DrugNameEditTextTHint.setHint(getString(R.string.drugName));
            viewIDModel.MrpEditTextTHint.setHint(getString(R.string.drugMRP));
            viewIDModel.QuantityEditTextTHint.setHint(getString(R.string.drugQuantity));
            viewIDModel.DiscountEditTextTHint.setHint(getString(R.string.drugDiscount));
            viewIDModel.ManufacturerEditTextTHint.setHint(getString(R.string.drugManufacturer));

            viewIDModel.OkTextView.setTextColor(getActivity().getResources().getColor(R.color.White));
            viewIDModel.CancelTextView.setTextColor(getActivity().getResources().getColor(R.color.White));

            viewIDModel.TransactionDateTextView.setText(mainActivity.GetCurrentDate());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void AddUpdateItem(StringHolderModel stringHolderModel, DrugModel editModel, Spinner spinnerCategory, int position) {
        try {
            DrugModel drugModel = new DrugModel();
            if (isModify)
                drugModel.DrugID = editModel.DrugID;
            else
                drugModel.DrugID = utility.CreateID();

            drugModel.DrugName = stringHolderModel.drugName;
            drugModel.DrugMRP = Double.parseDouble(stringHolderModel.drugMRP);

            try {
                drugModel.DrugQuantity = Integer.parseInt(stringHolderModel.drugQuantity);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                MainActivity.ShowToast(getActivity(), getString(R.string.enterValidQuantity));
                return;
            }

            drugModel.DrugDiscount = Float.valueOf(stringHolderModel.drugDiscount);
            drugModel.DrugExpiryDate = stringHolderModel.drugExpiryDate;
            drugModel.DrugTransactionDate = stringHolderModel.drugTransactionDate;
            drugModel.DrugManufacturer = stringHolderModel.drugManufacturer;
            drugModel.DrugCategory = spinnerCategory.getSelectedItem().toString();

            mainActivity.InsertUpdateDrugs(getActivity(), drugModel, isModify);

            if (isModify)
                procurementAdapter.UpdateItem(drugModel, position);
            else
                procurementAdapter.AddItem(drugModel);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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


    public void EditTypeStop(final ViewIDModel viewIDModel, final String searchText, long timeMilliSecond, final RecyclerView recyclerView, final boolean isDrugSearch) {
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
                            if (isDrugSearch)
                                SetSearchDrugAdapter(searchText, recyclerView, viewIDModel);
                            else
                                SetSearchDrugManufacturerAdapter(searchText, recyclerView, viewIDModel.ManufacturerEditText);
                        }
                    });
                }
            }, timeMilliSecond); //  600ms delay before the timer executes the „run“ method from TimerTask

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SetSearchDrugAdapter(final String searchText, final RecyclerView searchRecyclerView, ViewIDModel viewIDModel) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = mainActivity.GetDrugList(getActivity(), searchText);
            if (drugModelArrayList.size() > 0) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                searchDrugAdapter = new SearchDrugAdapter(getActivity(), drugModelArrayList);
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
                searchRecyclerView.setAdapter(searchDrugAdapter);
            } else {
                searchRecyclerView.setVisibility(View.GONE);
            }
            // Setup item Clicks
            searchRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {

                                DrugModel drugModel = searchDrugAdapter.getItem(position);
                                Log.i(TAG, "SetSearchDrugAdapter item click : " + drugModel);
                                searchRecyclerView.setAdapter(null);
                                searchRecyclerView.setVisibility(View.VISIBLE);
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

    private void SetSearchDrugManufacturerAdapter(String searchText, final RecyclerView searchRecyclerView, final EditText ManufacturerEditText) {
        ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
        try {
            drugModelArrayList = mainActivity.GetDrugList(getActivity(), searchText);
            if (drugModelArrayList.size() > 0) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                searchDrugManufacturerAdapter = new SearchDrugManufacturerAdapter(getActivity(), drugModelArrayList);
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                searchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
                searchRecyclerView.setAdapter(searchDrugManufacturerAdapter);
            } else {
                searchRecyclerView.setVisibility(View.GONE);
            }

            // Setup item Clicks
            searchRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                DrugModel drugModel = searchDrugAdapter.getItem(position);
                                ManufacturerEditText.setText(drugModel.DrugManufacturer);
                                Log.i(TAG, "SetSearchDrugAdapter item click : " + drugModel);
                                searchRecyclerView.setAdapter(null);
                                searchRecyclerView.setVisibility(View.VISIBLE);
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

}
