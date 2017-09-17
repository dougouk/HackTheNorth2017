package com.dan190.covfefe.Models;

/**
 * Created by david on 2017-09-17.
 */

public class OrderItem {

    private String item_name;
    private String order_id;
    private String receiver_id;
    private String offerer_id;
    private float actual_price;
    private boolean is_paid;

    public OrderItem(String item_name, String order_id, String receiver_id, String offerer_id){
        this.item_name = item_name;
        this.offerer_id = offerer_id;
        this.receiver_id = receiver_id;
        this.offerer_id = offerer_id;
    }

    public void setActualPrice(float price){
        actual_price = price;
    }

    public void payMoney(){
        is_paid = true;
    }
}
