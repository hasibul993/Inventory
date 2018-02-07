package com.inventory.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inventory.Model.DrugModel;
import com.inventory.Model.SliderMenuModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;
import com.inventory.SettingActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * Created by Hasib on 06/01/2018.
 */


public class ProcurementAdapter extends RecyclerView.Adapter<ProcurementAdapter.ViewHolder> {


    Context context;
    ArrayList<DrugModel> modelArrayList;

    public ProcurementAdapter(Context context, ArrayList<DrugModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.procurement_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final DrugModel sliderMenuModel = modelArrayList.get(position);
            holder.drugNameTV.setText(sliderMenuModel.DrugName);
            holder.drugQuantityTV.setText(sliderMenuModel.DrugQuantity);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        OnClickScreenTransition(sliderMenuModel.DrugName);
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


    public class ViewHolder extends RecyclerView.ViewHolder {

        RobotoTextView drugNameTV, drugQuantityTV;
        RelativeLayout mainLayout, iconLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            iconLayout = (RelativeLayout) itemView.findViewById(R.id.iconLayout);
            drugNameTV = (RobotoTextView) itemView.findViewById(R.id.drugNameTV);
            drugQuantityTV = (RobotoTextView) itemView.findViewById(R.id.drugQuantityTV);

        }
    }

    private void OnClickScreenTransition(String title) {
        try {
            if (StringUtils.equalsIgnoreCase(title, context.getString(R.string.settings))) {
                //SettingActivity.GotoSettingActivity(context);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}