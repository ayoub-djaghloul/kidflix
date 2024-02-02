package com.ayoub.ayoubtv.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL="https://ayoubtv.s3.eu-west-3.amazonaws.com/banners/api/";

    public static ApiInterface getRetrofitClient(){
        Retrofit builder=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return builder.create(ApiInterface.class);
    }


}
