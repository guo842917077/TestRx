package com.tjpld.smileapp.config.api;

import com.tjpld.smileapp.config.model.ResultModel;
import com.tjpld.smileapp.config.model.SmileContentModel;
import com.tjpld.smileapp.config.model.UserModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Smile App的接口
 */
public interface SmileApi {
    //登录接口
    @GET("login.jsp")
    Observable<ResultModel<UserModel>> loginApp(@Query("username") String username, @Query("password") String password);

    //获取笑话内容的接口
    @GET("smile.jsp")
    Observable<ResultModel<List<SmileContentModel>>> smileContent();

    //上传笑话的接口
    @GET("insertsmile.jsp")
    Observable<ResultModel<String>> submitSmile(@Query("reportname")String reportname,@Query("content")String content,@Query("reporttime")String reporttime);
}
