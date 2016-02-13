package com.tjpld.yuanhangrxjava.report.presenter;

import java.util.List;

/**
 * 图文上报页面逻辑
 */
public interface ISubmitPresenter {
    /**
     * @param description 隐患的描述
     * @param mBitmapList 图片集合
     * @param type        上报的类型
     */
    public void hiddenReport(String description, List<String> mBitmapList, String type);

    public void selectPhoto();//当选择照片时

    public void takePhoto();//当拍照时
}
