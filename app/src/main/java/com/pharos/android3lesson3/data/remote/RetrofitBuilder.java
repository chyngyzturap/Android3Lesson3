package com.pharos.android3lesson3.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private RetrofitBuilder(){}

    private static Android3Api instance;

    public static Android3Api getInstance(){
        if (instance == null){
            instance = createApi();
        }
        return instance;
    }

    private static Android3Api createApi() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Android3Api.class);
    }
}
