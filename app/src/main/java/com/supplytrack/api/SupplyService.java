package com.supplytrack.api;

import com.supplytrack.model.Department;
import com.supplytrack.model.Hospital;
import com.supplytrack.model.KResponse;
import com.supplytrack.model.Supply;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kene on 25/01/2019.
 */

public interface SupplyService {

    @FormUrlEncoded
    @POST("login/initialize")
    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    Call<KResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    @GET("supply/fetch_all_supplies/{user_id}")
    Call<List<Supply>> listSupplies(@Path("user_id") String id);

    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    @GET("hospitals/fetch/{user_id}")
    Call<List<Hospital>> listHospitals(@Path("user_id") String id);

    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    @GET("departments/fetch/{hospital_id}")
    Call<List<Department>> listDepartments(@Path("hospital_id") String id);

    @FormUrlEncoded
    @POST("supply/create")
    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    Call<KResponse> createSupply(
            @Field("user_id") String userID,
            @Field("hospital_id") String hospitalID,
            @Field("department_id") String departmentID,
            @Field("week") String week,
            @Field("month") String month,
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("supply/create_new")
    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    Call<KResponse> createNewSupply(
            @Field("user_id") String userID,
            @Field("hospital_id") String hospitalID,
            @Field("department_id") String departmentID,
            @Field("week") String week,
            @Field("month") String month,
            @Field("year") String year,
            @Field("item1") String item1,
            @Field("item2") String item2,
            @Field("item3") String item3,
            @Field("item4") String item4,
            @Field("item5") String item5,
            @Field("item6") String item6,
            @Field("item7") String item7,
            @Field("item8") String item8
    );

    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    @GET("supply/fetch_supply_by_id/{supply_id}")
    Call<Supply> getSupply(@Path("supply_id") String id);

    @FormUrlEncoded
    @POST("supply/update_items")
    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    Call<KResponse> updateSupplyItems(
            @Field("user_id") String userID,
            @Field("supply_id") String supplyID,
            @Field("item1") String item1,
            @Field("item2") String item2,
            @Field("item3") String item3,
            @Field("item4") String item4,
            @Field("item5") String item5,
            @Field("item6") String item6,
            @Field("item7") String item7,
            @Field("item8") String item8
    );

    @FormUrlEncoded
    @POST("supply/close_supply")
    @Headers("X-API-KEY: NESTLE_sk_jTjkuui5gcZw8svkpWMQBQZioh07jyuz")
    Call<KResponse> closeSupply(
            @Field("user_id") String userID,
            @Field("supply_id") String supplyID
    );


}



