package com.inventory.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inventory.Activities.MainActivity;
import com.inventory.Adapter.InventoryAdapter;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.DividerItemDecoration;
import com.inventory.R;

import java.util.ArrayList;


public class ExpiredDurationFragment extends Fragment {

    View rootView;

    public static ExpiredDurationFragment expiredDurationFragment;
    RecyclerView recyclerView;
    InventoryAdapter inventoryAdapter;
    MainActivity mainActivity;
    ArrayList<DrugModel> drugModelArrayList = new ArrayList<>();
    boolean isOncreateCalled = false;

    public ExpiredDurationFragment() {
        // Required empty public constructor
    }


    public static ExpiredDurationFragment newInstance(String param1, String param2) {
        ExpiredDurationFragment fragment = new ExpiredDurationFragment();
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
            rootView = inflater.inflate(R.layout.expired_duration_fragment, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            mainActivity = MainActivity.getInstance();

            GetDrugsLocally(null);

            expiredDurationFragment = this;
            isOncreateCalled = true;
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
                if (isOncreateCalled)
                    GetDrugsLocally(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void GetDrugsLocally(String searchedText) {
        try {

            drugModelArrayList = mainActivity.GetExpiredDurationInventoryFromInventoryDB(getActivity(), searchedText, 1);

            SetAdapter(drugModelArrayList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void SetAdapter(ArrayList<DrugModel> drugModelArrayList) {
        try {
            inventoryAdapter = new InventoryAdapter(getActivity(), drugModelArrayList,false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(inventoryAdapter);
            inventoryAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
