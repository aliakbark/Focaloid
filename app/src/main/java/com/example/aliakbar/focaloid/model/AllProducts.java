package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/8/2017.
 */

public class AllProducts {
    @SerializedName("pro_pk_id") @Expose private String proPkId;
    @SerializedName("pro_name") @Expose private String proName;
    @SerializedName("pro_code") @Expose private String proCode;
    @SerializedName("pro_price") @Expose private String proPrice;
    @SerializedName("pro_qty") @Expose private String proQty;
    @SerializedName("pro_desc") @Expose private String proDesc;
    @SerializedName("cat_fk_id") @Expose private String catFkId;
    @SerializedName("subcat_fk_id") @Expose private String subcatFkId;
    @SerializedName("product_images") @Expose private String productImages;


    public String getProPkId() {
        return proPkId;
    }

    public void setProPkId(String proPkId) {
        this.proPkId = proPkId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProQty() {
        return proQty;
    }

    public void setProQty(String proQty) {
        this.proQty = proQty;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getCatFkId() {
        return catFkId;
    }

    public void setCatFkId(String catFkId) {
        this.catFkId = catFkId;
    }

    public String getSubcatFkId() {
        return subcatFkId;
    }

    public void setSubcatFkId(String subcatFkId) {
        this.subcatFkId = subcatFkId;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }
}
