package com.inventory.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.inventory.Activities.MainActivity;
import com.inventory.Activities.SalesActivity;
import com.inventory.Helper.AppConstants;
import com.inventory.Model.DrugModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import java.util.ArrayList;


/**
 * Created by Hasib on 06/01/2018.
 */


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> implements AppConstants {


    Context context;
    ArrayList<DrugModel> modelArrayList;
    MainActivity mainActivity;
    boolean isAllInventory;

    public OrdersAdapter(Context context, ArrayList<DrugModel> modelArrayList, boolean isAllInventory) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.isAllInventory = isAllInventory;
        mainActivity = MainActivity.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RobotoTextView orderNoTV, customerMobileTV, orderTimeTV;
        RelativeLayout parentLayout, iconLayout;
        GradientDrawable iconLayoutBackg;
        ImageView iconImageV;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
            iconLayout = (RelativeLayout) itemView.findViewById(R.id.iconLayout);
            orderNoTV = (RobotoTextView) itemView.findViewById(R.id.orderNoTV);
            customerMobileTV = (RobotoTextView) itemView.findViewById(R.id.customerMobileTV);
            orderTimeTV = (RobotoTextView) itemView.findViewById(R.id.orderTimeTV);
            iconImageV = (ImageView) itemView.findViewById(R.id.iconImageV);
            iconLayoutBackg = (GradientDrawable) iconLayout.getBackground();
            //Typeface tMedium = Typeface.createFromAsset(context.getAssets(), AppConstants.ROBOTO_MEDIUM);
            Typeface tItalic = Typeface.createFromAsset(context.getAssets(), AppConstants.ROBOTO_ITALIC);
            //orderNoTV.setTypeface(tMedium);
            orderTimeTV.setTypeface(tItalic);

        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final DrugModel drugModel = modelArrayList.get(position);
            holder.orderNoTV.setText(" : " + drugModel.OrderNo);
            holder.orderTimeTV.setText(context.getString(R.string.createdOn) + " " + drugModel.OrderCreatedOn);
            holder.customerMobileTV.setText(drugModel.CustomerName + " , " + drugModel.CustomerMobile);

            holder.iconLayoutBackg.setColor(MainActivity.GetThemeColorInt());

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        SalesActivity.GotoSalesActivity(context, drugModel.OrderNo, true);
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


}