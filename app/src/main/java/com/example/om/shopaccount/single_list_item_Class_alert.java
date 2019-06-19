package com.example.om.shopaccount;

import java.io.Serializable;

public class single_list_item_Class_alert  {


//this java class is used for autocomplete
    private String itemName;
    private String itemPrice;

    public single_list_item_Class_alert(String itemName, String itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }


    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }
}
