package com.tjpld.smileapp.config.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 笑话内容的model 使用Parcelable接口序列化
 */
public class SmileContentModel implements Parcelable {
    private int id;
    private String content;
    private String reporttime;
    private int love;
    private int dislike;
    private String reportname;

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeString(reporttime);
        dest.writeInt(love);
        dest.writeInt(dislike);
        dest.writeString(reportname);
    }

    private SmileContentModel(Parcel in) {
        id = in.readInt();
        content = in.readString();
        reporttime = in.readString();
        love = in.readInt();
        dislike = in.readInt();
        reportname=in.readString();
    }

    /**
     * 反序列化
     */
    public static final Parcelable.Creator<SmileContentModel> CREATOR = new Parcelable.Creator<SmileContentModel>() {
        //从序列化后的对象中创建原始对象
        @Override
        public SmileContentModel createFromParcel(Parcel source) {
            return new SmileContentModel(source);
        }

        //创建指定长度的原始对象数组
        @Override
        public SmileContentModel[] newArray(int size) {
            return new SmileContentModel[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }
}
