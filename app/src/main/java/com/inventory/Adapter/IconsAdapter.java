package com.inventory.Adapter;

/**
 * Created by PCCS on 9/15/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.inventory.Activities.AddInventoryActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.MyViewHolder> {

    Context context;
    HashMap hashMap;

    static HashMap selectedIconsMap = new HashMap();
    int selectedPosition = -1;
    String selectedIconFontIs;

    public IconsAdapter(Context context, HashMap hashMap, String selectedIconFontIs) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.hashMap = hashMap;
        this.selectedIconFontIs = selectedIconFontIs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icons_adapter_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fontIcon;

        public MyViewHolder(View view) {
            super(view);
            fontIcon = (TextView) view.findViewById(R.id.font_icon);

        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            String value = hashMap.get(position).toString();

            String val1 = "" + value.charAt(3) + value.charAt(4) + value.charAt(5) + value.charAt(6);

            new String(Character.toChars(Integer.parseInt(val1, 16)));
            holder.fontIcon.setText(new String(Character.toChars(Integer.parseInt(val1, 16))));


            if (selectedPosition == position) {

                Drawable drawable = holder.fontIcon.getBackground();
                drawable.setColorFilter(Color.parseColor(new AddInventoryActivity().GetSelectedColor()), PorterDuff.Mode.SRC_ATOP);
                holder.fontIcon.setTextColor(context.getResources().getColor(R.color.White));
                // putColorIntoMap(value);

            } else {
                Drawable drawable = holder.fontIcon.getBackground();
                drawable.setColorFilter(context.getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
                holder.fontIcon.setTextColor(context.getResources().getColor(R.color.main_color_grey_500));
            }

            holder.fontIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String value = hashMap.get(position).toString();

                        Drawable drawable = holder.fontIcon.getBackground();
                        drawable.setColorFilter(Color.parseColor(new AddInventoryActivity().GetSelectedColor()), PorterDuff.Mode.SRC_ATOP);
                        holder.fontIcon.setTextColor(context.getResources().getColor(R.color.White));

                        if (context instanceof AddInventoryActivity) {
                            ((AddInventoryActivity) context).setIconFont(value);
                        }

                        selectedPosition = position;
                        notifyDataSetChanged();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            if (selectedPosition == -1) {
                if (StringUtils.equalsIgnoreCase(selectedIconFontIs, value)) {
                    Drawable drawable = holder.fontIcon.getBackground();
                    drawable.setColorFilter(Color.parseColor(new AddInventoryActivity().GetSelectedColor()), PorterDuff.Mode.SRC_ATOP);
                    holder.fontIcon.setTextColor(context.getResources().getColor(R.color.White));
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