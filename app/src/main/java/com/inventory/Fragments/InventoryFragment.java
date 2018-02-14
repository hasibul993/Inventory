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
    Utility utility = Utility.getInstance();
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
            procurementAdapter = new ProcurementAdapter(getActivity(), drugModelArrayList, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(procurementAdapter);
            procurementAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
