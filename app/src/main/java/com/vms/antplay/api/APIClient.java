package com.vms.antplay.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class APIClient {

    private static Retrofit retrofit;
    private static final String UAT_BASE_URL = "http://103.125.201.179:8000/api/";
    private static final String LIVE_BASE_URL = "http://103.125.201.177:8000/api/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(UAT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
