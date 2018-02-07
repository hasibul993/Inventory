package com.inventory.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.inventory.Adapter.ProcurementAdapter;
import com.inventory.Adapter.SliderMenuAdapter;
import com.inventory.Helper.Utility;
import com.inventory.HomeActivity;
import com.inventory.MainActivity;
import com.inventory.Model.DrugModel;
import com.inventory.Model.SettingsModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;
import com.inventory.SettingActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class ProcurementFragment extends Fragment {
    View rootView;
    RecyclerView recyclerView;
    ProcurementAdapter procurementAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
    Utility utility = new Utility();

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

    public void ShowDialogAddDrug() {
        TextInputLayout drugNameET_Hint, mrpET_Hint, quantityET_Hint, discountET_Hint;
        final EditText drugNameET, mrpET, quantityET, discountET;
        final RobotoTextView okTV, cancelTV, expiryDateTV, transactionDateTV;
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


            drugNameET = (EditText) dialog.findViewById(R.id.drugNameET);
            mrpET = (EditText) dialog.findViewById(R.id.mrpET);
            quantityET = (EditText) dialog.findViewById(R.id.quantityET);
            discountET = (EditText) dialog.findViewById(R.id.discountET);

            expiryDateTV = (RobotoTextView) dialog.findViewById(R.id.expiryDateTV);
            transactionDateTV = (RobotoTextView) dialog.findViewById(R.id.transactionDateTV);

            transactionDateTV.setText(mainActivity.GetCurrentDate());


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
                            drugModel.DrugName = drugName;
                            drugModel.DrugMRP = Double.parseDouble(drugMRP);
                            drugModel.DrugQuantity = Integer.valueOf(drugQuantity);
                            drugModel.DrugDiscount = Integer.valueOf(drugDiscount);
                            drugModel.DrugExpiryDate = expiryDate;
                            drugModel.DrugTransactionDate = transactionDate;
                            mainActivity.InsertUpdateDrugs(getActivity(), drugModel);
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

}
