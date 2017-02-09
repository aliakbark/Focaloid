package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class SingleProductResponse {

    @SerializedName("data") @Expose private SingleProduct singleproductdata;
    @SerializedName("success") @Expose private Integer success;

    public SingleProduct getSingleproductdata() {
        return singleproductdata;
    }

    public void setSingleproductdata(SingleProduct singleproductdata) {
        this.singleproductdata = singleproductdata;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
