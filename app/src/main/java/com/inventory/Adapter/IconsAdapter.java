package com.inventory.Adapter;

/**
 * Created by PCCS on 9/15/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.inventory.Activities.AddInventoryActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.Model.DrugModel;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.MyViewHolder> {

    Context context;
    HashMap hashMap;

    static HashMap selectedIconsMap = new HashMap();
    int selectedPosition = -1;
    String selectedDrugIcon;

    public IconsAdapter(Context context, HashMap hashMap, String selectedDrugIcon) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.hashMap = hashMap;
        this.selectedDrugIcon = selectedDrugIcon;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_adapter_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView tickIcon;
        RelativeLayout circleLayout;
        GradientDrawable circleLayoutBackg;

        public MyViewHolder(View view) {
            super(view);
            circleLayout = (RelativeLayout) view.findViewById(R.id.circleLayout);
            tickIcon = (ImageView) view.findViewById(R.id.tickIcon);
            circleLayoutBackg = (GradientDrawable) circleLayout.getBackground();

        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {

            DrugModel drugModel = (DrugModel) hashMap.get(position);

            holder.tickIcon.setBackgroundResource(0);
            holder.tickIcon.setImageBitmap(null);

            holder.tickIcon.setBackgroundResource(drugModel.DrugIcon);

            if (selectedPosition == position) {
                holder.circleLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));
                holder.tickIcon.setColorFilter(ContextCompat.getColor(context, R.color.White), android.graphics.PorterDuff.Mode.MULTIPLY);
                //holder.tickIcon.setColorFilter(context.getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
            } else {
                holder.circleLayoutBackg.setColor(context.getResources().getColor(R.color.main_color_grey_400));
                holder.tickIcon.setColorFilter(ContextCompat.getColor(context, R.color.White), android.graphics.PorterDuff.Mode.SRC_ATOP);
                //holder.tickIcon.setColorFilter(context.getResources().getColor(R.color.main_color_grey_500), PorterDuff.Mode.SRC_ATOP);

            }

            holder.tickIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DrugModel drugModelClicked = (DrugModel) hashMap.get(position);

                        holder.circleLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));
                        holder.tickIcon.setColorFilter(context.getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);

                        if (context instanceof AddInventoryActivity) {
                            ((AddInventoryActivity) context).SetDrugIcon(drugModelClicked.DrugCategory);
                        }

                        selectedPosition = position;
                        notifyDataSetChanged();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            if (selectedPosition == -1) {
                if (StringUtils.equalsIgnoreCase(selectedDrugIcon, drugModel.DrugCategory)) {
                    holder.circleLayoutBackg.setColor(Color.parseColor(MainActivity.GetThemeColor()));
                    holder.tickIcon.setColorFilter(context.getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return hashMap != null ? hashMap.size() : 0;
    }


    private void putColorIntoMap(String value) {
        selectedIconsMap.put("icon_font", value);
    }

    public static String getColorIntoMap() {
        if (selectedIconsMap != null && selectedIconsMap.size() > 0) {
            return selectedIconsMap.get("icon_font").toString();
        }
        return "";
    }

}