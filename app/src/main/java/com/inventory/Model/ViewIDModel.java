package com.inventory.Model;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Spinner;

import com.inventory.NewUi.RobotoTextView;

/**
 * Created by BookMEds on 08-02-2018.
 */

public class ViewIDModel {

    public TextInputLayout DrugNameEditTextTHint;
    public TextInputLayout MrpEditTextTHint;
    public TextInputLayout QuantityEditTextTHint;
    public TextInputLayout DiscountEditTextTHint;
    public TextInputLayout ManufacturerEditTextTHint;
    public TextInputLayout BatchNumberEditTextTHint;

    public EditText DrugNameEditText;
    public EditText MrpEditText;
    public EditText QuantityEditText;
    public EditText DiscountEditText;
    public EditText ManufacturerEditText;
    public EditText BatchNumberEditText;

    public RecyclerView DrugNameRecyclerView;

    public RecyclerView DrugManufacturerRecyclerView;


    public RobotoTextView OkTextView;
    public RobotoTextView CancelTextView;
    public RobotoTextView ExpiryDateTextView;
    public RobotoTextView TransactionDateTextView;

    public Spinner SpinnerCategory;


}
