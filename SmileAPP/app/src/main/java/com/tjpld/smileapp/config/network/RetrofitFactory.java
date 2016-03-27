package com.tjpld.smileapp.config.network;

import com.tjpld.smileapp.config.api.SmileApi;

/**
 * 接口工厂类，在此处定义需要的api
 */
public class RetrofitFactory {
    //1.需要的api
    private static SmileApi apiInstance;

    //2.通过单例方式定义一个全局的对象
    public static SmileApi getInstance() {
        if (apiInstance == null) {
            synchronized (SmileApi.class) {
                if (apiInstance == null) {
                    RetrofitClient client = new RetrofitClient();
                    apiInstance = client.getSmileApi();
                }
            }
        }
        return apiInstance;
    }
}
