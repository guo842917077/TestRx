package com.tjpld.smileapp.config.network;


import com.tjpld.smileapp.config.api.SmileApi;
import com.tjpld.smileapp.config.common.APPConfig;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * 网络请求类
 * 采用Retrofit网络请求库
 * 注意:BaseUrl 要以 / 结尾
 */
public class RetrofitClient {
    //1.采用Retrofit进行网络请求，API要以接口的形式定义
    private SmileApi smileApi;

    public RetrofitClient() {
        //2.配置Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APPConfig.BaseUrl)
                .build();
        smileApi = retrofit.create(SmileApi.class);
    }

    //拿到api对象
    public SmileApi getSmileApi() {
        return smileApi;
    }
}
