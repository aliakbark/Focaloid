package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/8/2017.
 */

public class AllProductsResponse {

    @SerializedName("data") @Expose private List<AllProducts> data = new ArrayList<AllProducts>();
    @SerializedName("total_products") @Expose private Integer totalProducts;;
    @SerializedName("success") @Expose private Integer success;
    @SerializedName("data") @Expose private List<CartAllProducts> cartData = new ArrayList<CartAllProducts>();

    public List<AllProducts> getData() {return data;}

    public void setData(List<AllProducts> data) {
        this.data = data;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<CartAllProducts> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartAllProducts> cartData) {
        this.cartData = cartData;
    }
}
