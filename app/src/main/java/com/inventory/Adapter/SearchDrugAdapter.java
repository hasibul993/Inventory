package com.inventory.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class SearchDrugAdapter extends RecyclerView.Adapter<SearchDrugAdapter.ViewHolder> {


    Context context;
    ArrayList<DrugModel> modelArrayList;
    MainActivity mainActivity;

    public SearchDrugAdapter(Context context, ArrayList<DrugModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        mainActivity = MainActivity.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_drug_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RobotoTextView drugNameTV, drugQuantityTV;
        RelativeLayout mainLayout, iconLayout;
        GradientDrawable iconLayoutBackg;
        ImageView iconImageV;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            iconLayout = (RelativeLayout) itemView.findViewById(R.id.iconLayout);
            drugNameTV = (RobotoTextView) itemView.findViewById(R.id.drugNameTV);
            drugQuantityTV = (RobotoTextView) itemView.findViewById(R.id.drugQuantityTV);
            iconImageV = (ImageView) itemView.findViewById(R.id.iconImageV);
            iconLayoutBackg = (GradientDrawable) iconLayout.getBackground();
        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final DrugModel drugModel = modelArrayList.get(position);
            holder.drugNameTV.setText(drugModel.DrugName);
            holder.drugQuantityTV.setText(context.getString(R.string.rs) + " " + drugModel.DrugMRPString);
            holder.iconLayoutBackg.setColor(context.getResources().getColor(R.color.main_color_grey_400));

            if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.tablet)))
                holder.iconImageV.setImageResource(R.drawable.tablets_icon);
            else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.injection)))
                holder.iconImageV.setImageResource(R.drawable.injection_icon);
            else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.syrup)))
                holder.iconImageV.setImageResource(R.drawable.syrup_icon);
            else
                holder.iconImageV.setImageResource(R.drawable.stocking);

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
                //DrugModel drugModel = mainActivity.GetDrugDetailsFromInventoryDB(context, drugID);
                //InventoryFragment.inventoryFragment.ShowDialogAddUpdateDrug(getItem(position), position);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}