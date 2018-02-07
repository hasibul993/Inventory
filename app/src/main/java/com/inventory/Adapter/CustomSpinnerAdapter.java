package com.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.inventory.NewUi.RobotoTextView;
import com.inventory.R;

import java.util.ArrayList;

/**
 * Created by admin on 10/21/2015.
 */
public class CustomSpinnerAdapter extends ArrayAdapter {
    public LayoutInflater inflater;
    ArrayList<String> arrayList = new ArrayList<>();
    Context context;

    public CustomSpinnerAdapter(Context cont, int resource, ArrayList<String> arrayList) {
        super(cont, resource, arrayList);
        try {
            this.arrayList = arrayList;
            context = cont;
            inflater = LayoutInflater.from(getContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.spinner_item, null);
            viewHolder.itemTextV = (RobotoTextView) convertView.findViewById(R.id.itemTextV);
            //viewHolder.color = (RobotoTextView) convertView.findViewById(R.id.colorcodeText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemTextV.setText("");
        viewHolder.itemTextV.setText(arrayList.get(position).toString());

        return convertView;
    }

    private static class ViewHolder {
        RobotoTextView itemTextV;
    }

}
