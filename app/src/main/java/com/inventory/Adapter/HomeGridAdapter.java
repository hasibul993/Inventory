package com.inventory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inventory.Activities.InventoryActivity;
import com.inventory.Activities.MainActivity;
import com.inventory.Activities.OrdersActivity;
import com.inventory.Activities.SalesActivity;
import com.inventory.Helper.AppConstants;
import com.inventory.Helper.Utility;
import com.inventory.Model.HomeModel;
import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Hasib on 02/03/17.
 */
public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.MyViewHolder> implements AppConstants {


    private static String TAG = "HomeGridAdapter";

    MainActivity mainActivity;
    private Utility utilities;
    ArrayList<HomeModel> homeModelArrayList;
    Context context;

    public HomeGridAdapter(Context context, ArrayList<HomeModel> homeModelArrayList) {
        try {
            this.context = context;
            this.homeModelArrayList = homeModelArrayList;
            mainActivity = MainActivity.getInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        try {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_adapter_row, parent, false);
            MyViewHolder rcv = new MyViewHolder(itemView);
            return rcv;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RobotoTextView titleTV;
        RelativeLayout circleLayout;
        GradientDrawable circleLayoutBackg;

        public MyViewHolder(View view) {
            super(view);
            try {

                titleTV = (RobotoTextView) view.findViewById(R.id.titleTV);
                circleLayout = (RelativeLayout) view.findViewById(R.id.circleLayout);
                circleLayoutBackg = (GradientDrawable) circleLayout.getBackground();

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            HomeModel model = homeModelArrayList.get(position);

            holder.titleTV.setText(model.Title);

            holder.circleLayoutBackg.setColor(MainActivity.GetThemeColorInt());

            holder.circleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        HomeModel model = GetItem(position);
                        HomeItemClick(model);
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
        return homeModelArrayList.size();
    }

    public HomeModel GetItem(int position) {
        // TODO Auto-generated method stub
        return homeModelArrayList.get(position);
    }

    private void HomeItemClick(HomeModel homeModel) {
        try {
            if (StringUtils.equalsIgnoreCase(homeModel.Title, context.getString(R.string.inventory))) {
                InventoryActivity.GotoInventoryActivity(context);
            } else if (StringUtils.equalsIgnoreCase(homeModel.Title, context.getString(R.string.pos))) {
                SalesActivity.GotoSalesActivity(context,null,false);
            } else if (StringUtils.equalsIgnoreCase(homeModel.Title, context.getString(R.string.orders))) {
                OrdersActivity.GotoOrdersActivity(context);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}