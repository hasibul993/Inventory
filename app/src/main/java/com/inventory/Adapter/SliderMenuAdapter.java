package com.inventory.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.mainLayout);
            titleTV = (RobotoTextView) itemView.findViewById(R.id.titleTV);

        }
    }

    private void OnClickScreenTransition(String title) {
        try {
            if (StringUtils.equalsIgnoreCase(title, context.getString(R.string.settings))) {
                SettingActivity.GotoSettingActivity(context);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}