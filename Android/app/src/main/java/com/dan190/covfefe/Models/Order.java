package com.dan190.covfefe.Models;

import android.support.annotation.Nullable;

/**
 * Created by david on 2017-09-16.
 */

public class Order {
    private String offerer_id;
    private String group_id;
    private Boolean is_active;

    public Order(String offerer_id, String group_id, Boolean active_order){
        this.offerer_id = offerer_id;
        this.group_id = group_id;
        this.is_active = active_order;
    }
}
