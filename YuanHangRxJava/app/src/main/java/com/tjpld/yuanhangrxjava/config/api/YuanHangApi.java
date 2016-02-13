package com.tjpld.yuanhangrxjava.config.api;

import com.tjpld.yuanhangrxjava.config.model.UserModel;
import com.tjpld.yuanhangrxjava.config.utlis.jsonutils.ResultEntity;

import java.io.File;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 登录界面的api
 */
public interface YuanHangApi {
    //登录
    @GET("loginApp")
    Observable<ResultEntity<UserModel>> login(@Query("name") String name, @Query("pwd") String pwd);

    /**
     * 图文上报接口
     *
     * @param description 隐患描述
     * @param personId    上报人id
     * @param type        上报类型
     * @param file        上报图片
     * @return
     */
    @POST("report")
    Observable<String> submitHidden(@Query("personId") String personId, @Query("description") String description, @Query("type") String type, @Query("file") File file);
}
