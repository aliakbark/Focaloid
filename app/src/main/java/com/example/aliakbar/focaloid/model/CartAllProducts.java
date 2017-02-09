package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2/8/2017.
 */

public class CartAllProducts {

    @SerializedName("crm_pk_id") @Expose private Object crmPkId;
    @SerializedName("cart_count") @Expose private String cartCount;

    public CartAllProducts(Object crmPkId, String cartCount) {
        this.crmPkId = crmPkId;
        this.cartCount = cartCount;
    }

    public Object getCrmPkId() {
        return crmPkId;
    }

    public void setCrmPkId(Object crmPkId) {
        this.crmPkId = crmPkId;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }
}
