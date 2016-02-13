package com.tjpld.yuanhangrxjava.config.network;

import com.tjpld.yuanhangrxjava.config.api.YuanHangApi;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * BaseUrl最后必须要以/结尾
 */
public class RetrofitClient {
    private YuanHangApi api;
    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.16.7:8080/safety/app/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YuanHangApi.class);
    }

    //拿到api对象
    public YuanHangApi getYuanHangApi() {
        return api;
    }
}
