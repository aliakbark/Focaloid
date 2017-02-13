package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/8/2017.
 */

public class AllProductsResults {
    @SerializedName("data") @Expose private List<AllProducts> data = new ArrayList<AllProducts>();
    @SerializedName("total_products") @Expose private Integer totalProducts;
    @SerializedName("cart_data") @Expose private List<CartAllProducts> cartData = new ArrayList<CartAllProducts>();
    @SerializedName("success")
    @Expose
    private Integer success;

    /**
     *
     * @return
     * The data
     */
    public List<AllProducts> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<AllProducts> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The totalProducts
     */
    public Integer getTotalProducts() {
        return totalProducts;
    }

    /**
     *
     * @param totalProducts
     * The total_products
     */
    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    /**
     *
     * @return
     * The cartData
     */
    public List<CartAllProducts> getCartData() {
        return cartData;
    }

    /**
     *
     * @param cartData
     * The cart_data
     */
    public void setCartData(List<CartAllProducts> cartData) {
        this.cartData = cartData;
    }

    /**
     *
     * @return
     * The success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }
}
