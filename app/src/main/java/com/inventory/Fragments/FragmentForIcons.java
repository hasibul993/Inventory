package com.inventory.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inventory.Adapter.IconsAdapter;
import com.inventory.R;

import java.util.HashMap;

/**
 * Created by Hasib on 14/02/2018.
 */


public class FragmentForIcons extends Fragment {

    HashMap hashMapArrayList;
    RecyclerView recyclerView;
    IconsAdapter iconsAdapter;
    String selectedDrugIcon;

    // newInstance constructor for creating fragment with arguments
    public static FragmentForIcons newInstance(HashMap hashMapArrayList, String selectedIconFont) {
        FragmentForIcons fragmentFirst = new FragmentForIcons();
        Bundle args = new Bundle();
        args.putSerializable("map", hashMapArrayList);
        args.putString("iconfont", selectedIconFont);
        fragmentFirst.setArguments(args);
        return fragmentFirst;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drug_icon_recyclerview, container, false);

        hashMapArrayList = (HashMap) getArguments().getSerializable("map");
        selectedDrugIcon = getArguments().getString("iconfont");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //define columns
        int numberOfColumns = 5;


        iconsAdapter = new IconsAdapter(getActivity(), hashMapArrayList, selectedDrugIcon);
        //recyclerView.setAdapter(null);
        recyclerView.setAdapter(iconsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}


