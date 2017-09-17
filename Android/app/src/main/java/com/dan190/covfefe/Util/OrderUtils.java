package com.dan190.covfefe.Util;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by david on 2017-09-16.
 */

public class OrderUtils {

    FirebaseDatabase database = MyApplication.getInstance().getGlobalDB();

    // called when offerer starts order
    public String start_order(String offerer_id, String group_id){
        DatabaseReference ordersRef = database.getReference("orders");

        DatabaseReference newOrderRef = ordersRef.push();

        Order new_order = new Order(offerer_id, group_id, true);
        newOrderRef.setValue(new_order);

        String order_id = newOrderRef.getKey();

        return order_id;
    }

    // called when others join order
    public String join_order(String user_id, String order_id, String item_name){
//        DatabaseReference orderItemsReference = database.getReference("order_items");
//
//        DatabaseReference newOrderItemRef = orderItemsReference.push();
//        newOrderItemRef.setValue()
        return "";
    }
}
