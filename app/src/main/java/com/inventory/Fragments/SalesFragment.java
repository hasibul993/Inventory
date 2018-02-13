package com.inventory.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inventory.Activities.MainActivity;
import com.inventory.Adapter.ProcurementAdapter;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.R;

import java.util.ArrayList;


public class SalesFragment extends Fragment {

    View rootView;

    public static SalesFragment salesFragment;
    RecyclerView recyclerView;
    ProcurementAdapter procurementAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();

    public SalesFragment() {
        // Required empty public constructor
    }


    public static SalesFragment newInstance(String param1, String param2) {
        SalesFragment fragment = new SalesFragment();
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
            rootView = inflater.inflate(R.layout.sales_fragment, container, false);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            mainActivity = MainActivity.getInstance();

            GetDrugsLocally(null);

            salesFragment = this;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Inflate the layout for this fragment
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
            ex.printStackTrace();
        }
    }

}
