package com.tjpld.smileapp.smile.presenter;

import com.tjpld.smileapp.config.model.SmileContentModel;

import java.util.List;

/**
 * Created by guo on 2016/3/13.
 */
public interface ISmileView {
    /**
     * 刷新页面数据
     */
    public void refreshSmileData();

    /**
     * 加载页面数据
     */
    public void loadSmileData(List<SmileContentModel> data);
}
