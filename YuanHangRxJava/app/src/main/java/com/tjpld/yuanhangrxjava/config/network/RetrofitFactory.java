package com.tjpld.yuanhangrxjava.config.network;

import com.tjpld.yuanhangrxjava.config.api.YuanHangApi;

/**
 * 拿到api对象
 */
public class RetrofitFactory {
    private static YuanHangApi apiInstance;
    public static YuanHangApi getInstance() {
        if (apiInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (apiInstance == null) {
                    RetrofitClient client = new RetrofitClient();
                    apiInstance = client.getYuanHangApi();
                }
            }
        }
        return apiInstance;
    }
}
