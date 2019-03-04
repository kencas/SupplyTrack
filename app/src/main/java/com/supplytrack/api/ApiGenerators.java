package com.supplytrack.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nosakhare on 15/09/2017.
 */

public final class ApiGenerators {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static SupplyService generateSupplyAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.lakesamfoods.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SupplyService.class);
    }


}
