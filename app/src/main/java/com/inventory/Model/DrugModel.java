package com.inventory.Model;

import java.io.Serializable;

/**
 * Created by BookMEds on 06-02-2018.
 */

public class DrugModel implements Serializable {

    public String PharmacyID;

    public String BatchNumber;

    public String DrugID;

    public String DrugName;

    public int DrugIcon;

    public String DrugCategory;

    public String DrugManufacturer;

    public double DrugTotalMRPWithoutDisc;

    public double DrugTotalMRP;

    public String DrugTotalMRPString;

    public double DrugMRP;

    public String DrugMRPString;

    public double OrderTotal;

    public String OrderTotalString;

    public long DateInMilliSecond;

    public String TimeStamp;

    public int DrugQuantity;

    public String DrugExpiryDate;

    public float OrderTotalDiscount;

    public String OrderTotalDiscountString;

    public float DrugDiscount;

    public String DrugDiscountString;

    public String DrugTransactionDate;

    public String OrderNo;

    public String OrderCreatedOn;

    public String CustomerMobile;

    public String CustomerName;

    public String PatientName;

    public String Gender;

    public String Age;

    public boolean IsNeedSync;


}
