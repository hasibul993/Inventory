package com.inventory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.inventory.Activities.MainActivity;
import com.inventory.Activities.SalesActivity;
import com.inventory.Activities.SettingActivity;
import com.inventory.Model.SliderMenuModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * Created by PCCS on 4/19/2017.
 */


public class SliderMenuAdapter extends RecyclerView.Adapter<SliderMenuAdapter.ViewHolder> {


    Context context;
    ArrayList<SliderMenuModel> modelArrayList;

    public SliderMenuAdapter(Context context, ArrayList<SliderMenuModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_menu_adapter_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final SliderMenuModel sliderMenuModel = modelArrayList.get(position);
            holder.titleTV.setText(sliderMenuModel.Title);

            if (StringUtils.equalsIgnoreCase(sliderMenuModel.Title, context.getString(R.string.masterDB))
                    || StringUtils.equalsIgnoreCase(sliderMenuModel.Title, context.getString(R.string.pharmacyDB)))
                holder.syncImageV.setVisibility(View.VISIBLE);
            else
                holder.syncImageV.setVisibility(View.GONE);


            holder.titleImageV.setBackgroundResource(sliderMenuModel.drawableInt);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        OnClickScreenTransition(sliderMenuModel.Title);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            holder.syncImageV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MainActivity.ShowToast(context, "Will sync later");
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

        RobotoTextView titleTV;
        RelativeLayout mainLayout;
        ImageView syncImageV, titleImageV;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            titleTV = (RobotoTextView) itemView.findViewById(R.id.titleTV);
            titleImageV = (ImageView) itemView.findViewById(R.id.titleImageV);
            syncImageV = (ImageView) itemView.findViewById(R.id.syncImageV);

        }
    }

    private void OnClickScreenTransition(String title) {
        try {
            if (StringUtils.equalsIgnoreCase(title, context.getString(R.string.settings))) {
                SettingActivity.GotoSettingActivity(context);
            } else if (StringUtils.equalsIgnoreCase(title, context.getString(R.string.sale))) {
                SalesActivity.GotoSalesActivity(context);
            } else if (StringUtils.equalsIgnoreCase(title, context.getString(R.string.share))) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this free app that I'm using to organise my pharmacy inventory! https://play.google.com/store/apps/details?id=com.xampr&hl=en");
                context.startActivity(Intent.createChooser(intent, "Share via"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}