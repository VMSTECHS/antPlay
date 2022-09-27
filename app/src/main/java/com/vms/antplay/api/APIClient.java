package com.vms.antplay.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class APIClient {

    private static Retrofit retrofit;
   /* private static final String UAT_BASE_URL = "http://103.125.201.179:8000/api/";
    private static final String LIVE_BASE_URL = "http://103.125.201.177:8000/api/";*/


    private static final String LIVE_BASE_URL = "https://api.antplay.tech/api/";
   // OkHttpClient clients = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getRetrofitInstance() {
       // OkHttpClient clients = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(LIVE_BASE_URL)
                   // .client(clients)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
