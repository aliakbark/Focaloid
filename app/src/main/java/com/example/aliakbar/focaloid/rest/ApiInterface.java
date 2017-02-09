package com.example.aliakbar.focaloid.rest;

import com.example.aliakbar.focaloid.model.AllProductsResponse;
import com.example.aliakbar.focaloid.model.MovieResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 2/8/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("services/get_products")
    Call<AllProductsResponse> getProducts(@Field("usr_fk_id") String usr_fk_id,
                                          @Field("cat_fk_id") String cat_fk_id,
                                          @Field("subcat_fk_id") String pro_cat,
                                          @Field("keyword") String keyword,
                                          @Field("offset") String cart_offset);


    @FormUrlEncoded
    @POST("services/get_product_details")
    Call<JsonObject> getProductDetail(@Field("pro_pk_id") String pro_pk_id);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
