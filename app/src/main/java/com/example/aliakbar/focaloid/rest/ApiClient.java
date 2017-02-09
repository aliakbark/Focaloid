package com.example.aliakbar.focaloid.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2/8/2017.
 */

public class ApiClient {
    private static final String ROOT_URL = Constant.Base_Url;
    private static Retrofit retrofit = null;

    public static final String BASE_URL1 = "http://api.themoviedb.org/3/";


    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiInterface getApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }
}
