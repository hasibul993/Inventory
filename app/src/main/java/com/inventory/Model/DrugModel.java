package com.inventory.Model;

import java.io.Serializable;

/**
 * Created by BookMEds on 06-02-2018.
 */

public class DrugModel implements Serializable {

    public String BatchNumber;

    public String DrugID;

    public String DrugName;

    public String DrugIcon;

    public String DrugCategory;

    public String DrugManufacturer;

    public double DrugMRP;

    public long ExpiryDateInMilliSecond;

    public String DrugMRPString;

    public String TimeStamp;

    public int DrugQuantity;

    public String DrugExpiryDate;

    public float DrugDiscount;

    public String DrugDiscountString;

    public String DrugTransactionDate;

}
