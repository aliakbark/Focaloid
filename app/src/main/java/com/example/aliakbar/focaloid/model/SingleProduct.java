package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SingleProduct {

    @SerializedName("pro_pk_id") @Expose private Object proPkId;
    @SerializedName("pro_name") @Expose private Object proName;
    @SerializedName("category_id") @Expose private Object categoryId;
    @SerializedName("sub_category_id") @Expose private Object subCategoryId;
    @SerializedName("pro_price") @Expose private Object proPrice;
    @SerializedName("pro_qty") @Expose private Object proQty;
    @SerializedName("pro_desc") @Expose private Object proDesc;
    @SerializedName("pro_code") @Expose private Object proCode;
    @SerializedName("product_images") @Expose private Object productImages;
}
