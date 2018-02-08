package com.inventory.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.inventory.Activities.MainActivity;
import com.inventory.R;

import java.util.ArrayList;

public class CircleColorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> colorList;
    LayoutInflater inflater;
    int selectedPosition = -1;
    String colorcode;
    MainActivity mainActivity;


    public CircleColorAdapter(Context context, ArrayList<String> activityList, String colorcode) {
        this.context = context;
        this.colorList = activityList;
        this.colorcode = colorcode;
        mainActivity = MainActivity.getInstance();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public String getItem(int position) {
        return colorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.circle_color_grid_item, null);
            viewHolder.circleLayout = (RelativeLayout) convertView.findViewById(R.id.circleLayout);
            viewHolder.tickIcon = (ImageView) convertView.findViewById(R.id.tickIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (selectedPosition == position) {
            viewHolder.tickIcon.setVisibility(View.VISIBLE);
        } else
            viewHolder.tickIcon.setVisibility(View.INVISIBLE);

        if (selectedPosition == -1) {
            if (mainActivity.GetColorCodePosition(colorcode, colorList) == position) {
                selectedPosition = position;
                viewHolder.tickIcon.setVisibility(View.VISIBLE);
            }

        }

        try {
            String item = colorList.get(position);
            GradientDrawable chatbackground = (GradientDrawable) viewHolder.circleLayout.getBackground();

            if (item.isEmpty()) {
                item = "#F4804C";
            }
            chatbackground.setColor(Color.parseColor(item));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }


    public void SetSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }


    class ViewHolder {
        ImageView tickIcon;
        RelativeLayout circleLayout;

    }


}