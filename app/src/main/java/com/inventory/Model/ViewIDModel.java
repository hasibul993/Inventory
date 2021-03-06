package com.inventory.Model;

import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.inventory.NewUi.RobotoTextView;

/**
 * Created by BookMEds on 08-02-2018.
 */

public class ViewIDModel {

    public EditText DrugNameEditText;
    public EditText MrpEditText;
    public EditText QuantityEditText;
    public EditText DiscountEditText;
    public EditText ManufacturerEditText;
    public EditText BatchNumberEditText;

    public RecyclerView DrugNameRecyclerView;

    public RecyclerView DrugManufacturerRecyclerView;

    public LinearLayout DrugNameRecyclerViewLayout;

    public LinearLayout DrugManufacturerRecyclerViewLayout;

    public ImageView DeleteIconDrugNameRecyclerView;

    public ImageView DeleteIconManufacturerRecyclerView;


    public RobotoTextView QtyAvailableTV;

    public RobotoTextView OkTextView;
    public RobotoTextView CancelTextView;
    public RobotoTextView ExpiryDateTextView;
    public RobotoTextView TransactionDateTextView;
    public RobotoTextView ResultOfTextViewManufacturerRecyclerView;
    public RobotoTextView ResultOfTextViewDrugNameRecyclerView;

    public ViewPager viewPager;

    public TabLayout tabLayout;

}
