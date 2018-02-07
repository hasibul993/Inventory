package com.inventory.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.inventory.Adapter.CustomSpinnerAdapter;
import com.inventory.Adapter.ProcurementAdapter;
import com.inventory.Helper.Utility;
import com.inventory.MainActivity;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class ProcurementFragment extends Fragment {

    View rootView;
    RecyclerView recyclerView;
    ProcurementAdapter procurementAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
    Utility utility = new Utility();
    boolean isModify = false;
    public static ProcurementFragment procurementFragment;

    ArrayList<String> drugCategories;
    ;

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
        TextInputLayout drugNameET_Hint, mrpET_Hint, quantityET_Hint, discountET_Hint;
        RelativeLayout cancelOkRelaLayout;
        final EditText drugNameET, mrpET, quantityET, discountET;
        final RobotoTextView okTV, cancelTV, expiryDateTV, transactionDateTV;
        final Spinner spinnerCategory;
        String prevDrugCategory = null;
        try {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.add_drug);
            dialog.setCancelable(false);

            //product ids
            drugNameET_Hint = (TextInputLayout) dialog.findViewById(R.id.drugNameET_Hint);
            mrpET_Hint = (TextInputLayout) dialog.findViewById(R.id.mrpET_Hint);
            quantityET_Hint = (TextInputLayout) dialog.findViewById(R.id.quantityET_Hint);
            discountET_Hint = (TextInputLayout) dialog.findViewById(R.id.discountET_Hint);

            drugNameET_Hint.setHint(getString(R.string.drugName));
            mrpET_Hint.setHint(getString(R.string.drugMRP));
            quantityET_Hint.setHint(getString(R.string.drugQuantity));
            discountET_Hint.setHint(getString(R.string.drugDiscount));

            spinnerCategory = (Spinner) dialog.findViewById(R.id.spinnerCategory);

            drugNameET = (EditText) dialog.findViewById(R.id.drugNameET);
            mrpET = (EditText) dialog.findViewById(R.id.mrpET);
            quantityET = (EditText) dialog.findViewById(R.id.quantityET);
            discountET = (EditText) dialog.findViewById(R.id.discountET);

            expiryDateTV = (RobotoTextView) dialog.findViewById(R.id.expiryDateTV);
            transactionDateTV = (RobotoTextView) dialog.findViewById(R.id.transactionDateTV);

            cancelOkRelaLayout = (RelativeLayout) dialog.findViewById(R.id.cancelOkRelaLayout);
            GradientDrawable cancelOkRelaLayoutBackg = (GradientDrawable) cancelOkRelaLayout.getBackground();
            cancelOkRelaLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));


            transactionDateTV.setText(mainActivity.GetCurrentDate());


            if (editModel != null) {

                isModify = true;
                drugNameET.setText(editModel.DrugName);
                drugNameET.setSelection(drugNameET.length());
                mrpET.setText(editModel.DrugMRPString);
                quantityET.setText(editModel.DrugQuantity + "");
                discountET.setText(editModel.DrugDiscountString);
                expiryDateTV.setText(editModel.DrugExpiryDate);
                prevDrugCategory = editModel.DrugCategory;
            } else {
                isModify = false;
            }

            SetSpinnerAdapter(spinnerCategory, prevDrugCategory);

            //cancel ok btn ids
            okTV = (RobotoTextView) dialog.findViewById(R.id.okTV);
            cancelTV = (RobotoTextView) dialog.findViewById(R.id.cancelTV);
            okTV.setTextColor(getResources().getColor(R.color.White));
            cancelTV.setTextColor(getResources().getColor(R.color.White));

            expiryDateTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        utility.ShowDatePicker(getActivity(), expiryDateTV);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            okTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String drugName, drugDiscount, drugQuantity, drugMRP, expiryDate, transactionDate;
                    try {
                        drugName = drugNameET.getText().toString().trim();
                        drugMRP = mrpET.getText().toString().trim();
                        drugQuantity = quantityET.getText().toString().trim();
                        drugDiscount = discountET.getText().toString().trim();
                        expiryDate = expiryDateTV.getText().toString().trim();
                        transactionDate = transactionDateTV.getText().toString().trim();

                        if (StringUtils.isBlank(drugName)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugName));
                            return;
                        } else if (StringUtils.isBlank(drugMRP)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugMRP));
                            return;
                        } else if (StringUtils.isBlank(drugQuantity)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugQuantity));
                            return;
                        } else if (StringUtils.isBlank(drugDiscount)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.enterDrugDiscount));
                            return;
                        } else if (StringUtils.isBlank(expiryDate)) {
                            MainActivity.ShowToast(getActivity(), getString(R.string.chooseExpiryDate));
                            return;
                        } else {
                            DrugModel drugModel = new DrugModel();
                            if (isModify)
                                drugModel.DrugID = editModel.DrugID;
                            else
                                drugModel.DrugID = utility.CreateID();

                            drugModel.DrugName = drugName;
                            drugModel.DrugMRP = Double.parseDouble(drugMRP);
                            drugModel.DrugQuantity = Integer.valueOf(drugQuantity);
                            drugModel.DrugDiscount = Float.valueOf(drugDiscount);
                            drugModel.DrugExpiryDate = expiryDate;
                            drugModel.DrugTransactionDate = transactionDate;
                            drugModel.DrugCategory = spinnerCategory.getSelectedItem().toString();
                            mainActivity.InsertUpdateDrugs(getActivity(), drugModel, isModify);

                            if (isModify)
                                procurementAdapter.UpdateItem(drugModel,position);
                            else
                                procurementAdapter.AddItem(drugModel);

                            dialog.dismiss();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            cancelTV.setOnClickListener(new View.OnClickListener() {
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

}
