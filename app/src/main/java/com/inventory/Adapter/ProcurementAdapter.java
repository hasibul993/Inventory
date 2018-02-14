package com.inventory.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.inventory.Activities.AddInventoryActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.Fragments.InventoryFragment;
import com.inventory.Helper.AppConstants;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * Created by Hasib on 06/01/2018.
 */


public class ProcurementAdapter extends RecyclerView.Adapter<ProcurementAdapter.ViewHolder> implements AppConstants {


    Context context;
    ArrayList<DrugModel> modelArrayList;
    MainActivity mainActivity;
    boolean isAllInventory;

    public ProcurementAdapter(Context context, ArrayList<DrugModel> modelArrayList, boolean isAllInventory) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.isAllInventory = isAllInventory;
        mainActivity = MainActivity.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RobotoTextView drugNameTV, drugQuantityTV, drugManufacturerTV, drugBatchNumberTV, drugExpiryDateTV;
        RelativeLayout mainLayout, iconLayout;
        GradientDrawable iconLayoutBackg, drugQuantityBackg, drugBatchNumberBackg, drugExpiryDateBackg;
        ImageView iconImageV;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            iconLayout = (RelativeLayout) itemView.findViewById(R.id.iconLayout);
            drugNameTV = (RobotoTextView) itemView.findViewById(R.id.drugNameTV);
            drugQuantityTV = (RobotoTextView) itemView.findViewById(R.id.drugQuantityTV);
            drugManufacturerTV = (RobotoTextView) itemView.findViewById(R.id.drugManufacturerTV);
            drugBatchNumberTV = (RobotoTextView) itemView.findViewById(R.id.drugBatchNumberTV);
            drugExpiryDateTV = (RobotoTextView) itemView.findViewById(R.id.drugExpiryDateTV);
            iconImageV = (ImageView) itemView.findViewById(R.id.iconImageV);

            iconLayoutBackg = (GradientDrawable) iconLayout.getBackground();
            drugQuantityBackg = (GradientDrawable) drugQuantityTV.getBackground();
            drugBatchNumberBackg = (GradientDrawable) drugBatchNumberTV.getBackground();
            drugExpiryDateBackg = (GradientDrawable) drugExpiryDateTV.getBackground();

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final DrugModel drugModel = modelArrayList.get(position);
            holder.drugNameTV.setText(drugModel.DrugName);
            holder.drugManufacturerTV.setText(drugModel.DrugManufacturer);

            if (drugModel.DrugQuantity > 0 && drugModel.DrugQuantity < 10)
                holder.drugQuantityTV.setText("0" + drugModel.DrugQuantity + "");
            else
                holder.drugQuantityTV.setText(drugModel.DrugQuantity + "");

            holder.drugBatchNumberTV.setText(drugModel.BatchNumber);
            holder.drugExpiryDateTV.setText(drugModel.DrugExpiryDate);


            if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.tablet))) {
                holder.iconLayoutBackg.setColor(Color.parseColor(TABLETS_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(TABLETS_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(TABLETS_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(TABLETS_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.tablets_icon);
            } else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.capsules))) {
                holder.iconLayoutBackg.setColor(Color.parseColor(CAPSULES_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(CAPSULES_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(CAPSULES_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(CAPSULES_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.capsules_icon);
            } else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.injection))) {
                holder.iconLayoutBackg.setColor(Color.parseColor(INJECTION_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(INJECTION_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(INJECTION_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(INJECTION_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.injection_icon);
            } else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.syrup))) {
                holder.iconLayoutBackg.setColor(Color.parseColor(SYRUP_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(SYRUP_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(SYRUP_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(SYRUP_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.syrup_icon);
            } else if (StringUtils.equalsIgnoreCase(drugModel.DrugCategory, context.getString(R.string.cream))) {
                holder.iconLayoutBackg.setColor(Color.parseColor(CREAM_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(CREAM_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(CREAM_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(CREAM_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.cream_icon);
            } else {
                holder.iconLayoutBackg.setColor(Color.parseColor(MISCELLANEOUS_THEMECOLOR));
                holder.drugQuantityBackg.setColor(Color.parseColor(MISCELLANEOUS_THEMECOLOR));
                holder.drugBatchNumberBackg.setColor(Color.parseColor(MISCELLANEOUS_THEMECOLOR));
                holder.drugExpiryDateBackg.setColor(Color.parseColor(MISCELLANEOUS_THEMECOLOR));
                holder.iconImageV.setImageResource(R.drawable.stocking);
            }

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
                if (isAllInventory)
                    AddInventoryActivity.GotoAddInventoryActivity(context, getItem(position), true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}