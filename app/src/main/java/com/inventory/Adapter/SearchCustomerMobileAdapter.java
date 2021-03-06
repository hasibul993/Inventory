package com.inventory.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inventory.Activities.MainActivity;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * Created by Hasib on 06/01/2018.
 */


public class SearchCustomerMobileAdapter extends RecyclerView.Adapter<SearchCustomerMobileAdapter.ViewHolder> {


    Context context;
    ArrayList<DrugModel> modelArrayList;
    MainActivity mainActivity;

    public SearchCustomerMobileAdapter(Context context, ArrayList<DrugModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        mainActivity = MainActivity.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_drug_manufacturer_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RobotoTextView customerMobileTV;
        RelativeLayout mainLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            customerMobileTV = (RobotoTextView) itemView.findViewById(R.id.drugNameTV);
        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final DrugModel drugModel = modelArrayList.get(position);
            holder.customerMobileTV.setText(drugModel.CustomerMobile);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        OnClickScreenTransition(drugModel.DrugID, position);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return modelArrayList != null ? modelArrayList.size() : 0;
    }

    public DrugModel getItem(int position) {
        // TODO Auto-generated method stub
        return modelArrayList.get(position);
    }


    public void AddItem(DrugModel drugModel) {
        int position = 0;
        try {
            modelArrayList.add(position, drugModel);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, modelArrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateItem(DrugModel drugModel, int position) {
        try {
            modelArrayList.remove(position);
            modelArrayList.add(position, drugModel);

            notifyItemChanged(position);
            notifyItemRangeChanged(position, modelArrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RemoveItem(int position) {
        try {
            modelArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, modelArrayList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void OnClickScreenTransition(String drugID, int position) {
        try {
            if (!StringUtils.isBlank(drugID)) {
                //DrugModel drugModel = mainActivity.GetDrugDetails(context, drugID);
                //InventoryFragment.inventoryFragment.ShowDialogAddUpdateDrug(getItem(position), position);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}