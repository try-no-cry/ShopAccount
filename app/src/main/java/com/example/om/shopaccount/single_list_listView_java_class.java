package com.example.om.shopaccount;

import java.io.Serializable;

public class single_list_listView_java_class implements Serializable {


    private String itemName;

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    private String itemPrice;

    //this will be use by list view in both alert_window as well in main window

    public single_list_listView_java_class(){

    }

    public single_list_listView_java_class(String itemName, String itemPrice, String itemQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    private String itemQuantity;


}
