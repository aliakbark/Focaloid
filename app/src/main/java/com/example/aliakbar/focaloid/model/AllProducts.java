package com.example.aliakbar.focaloid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/8/2017.
 */

public class AllProducts {
    @SerializedName("pro_pk_id")
    @Expose
    private String proPkId;
    @SerializedName("pro_name")
    @Expose
    private String proName;
    @SerializedName("pro_code")
    @Expose
    private String proCode;
    @SerializedName("pro_price")
    @Expose
    private String proPrice;
    @SerializedName("pro_qty")
    @Expose
    private String proQty;
    @SerializedName("pro_desc")
    @Expose
    private String proDesc;
    @SerializedName("cat_fk_id")
    @Expose
    private String catFkId;
    @SerializedName("subcat_fk_id")
    @Expose
    private String subcatFkId;
    @SerializedName("product_images")
    @Expose
    private String productImages;
    @SerializedName("offer_amt")
    @Expose
    private Object offerAmt;

    /**
     *
     * @return
     * The proPkId
     */
    public String getProPkId() {
        return proPkId;
    }

    /**
     *
     * @param proPkId
     * The pro_pk_id
     */
    public void setProPkId(String proPkId) {
        this.proPkId = proPkId;
    }

    /**
     *
     * @return
     * The proName
     */
    public String getProName() {
        return proName;
    }

    /**
     *
     * @param proName
     * The pro_name
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     *
     * @return
     * The proCode
     */
    public String getProCode() {
        return proCode;
    }

    /**
     *
     * @param proCode
     * The pro_code
     */
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    /**
     *
     * @return
     * The proPrice
     */
    public String getProPrice() {
        return proPrice;
    }

    /**
     *
     * @param proPrice
     * The pro_price
     */
    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    /**
     *
     * @return
     * The proQty
     */
    public String getProQty() {
        return proQty;
    }

    /**
     *
     * @param proQty
     * The pro_qty
     */
    public void setProQty(String proQty) {
        this.proQty = proQty;
    }

    /**
     *
     * @return
     * The proDesc
     */
    public String getProDesc() {
        return proDesc;
    }

    /**
     *
     * @param proDesc
     * The pro_desc
     */
    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    /**
     *
     * @return
     * The catFkId
     */
    public String getCatFkId() {
        return catFkId;
    }

    /**
     *
     * @param catFkId
     * The cat_fk_id
     */
    public void setCatFkId(String catFkId) {
        this.catFkId = catFkId;
    }

    /**
     *
     * @return
     * The subcatFkId
     */
    public String getSubcatFkId() {
        return subcatFkId;
    }

    /**
     *
     * @param subcatFkId
     * The subcat_fk_id
     */
    public void setSubcatFkId(String subcatFkId) {
        this.subcatFkId = subcatFkId;
    }

    /**
     *
     * @return
     * The productImages
     */
    public String getProductImages() {
        return productImages;
    }

    /**
     *
     * @param productImages
     * The product_images
     */
    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    /**
     *
     * @return
     * The offerAmt
     */
    public Object getOfferAmt() {
        return offerAmt;
    }

    /**
     *
     * @param offerAmt
     * The offer_amt
     */
    public void setOfferAmt(Object offerAmt) {
        this.offerAmt = offerAmt;
    }
}
