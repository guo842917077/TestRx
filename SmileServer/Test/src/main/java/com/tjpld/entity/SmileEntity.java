package com.tjpld.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

/**
 * 对应smile表中的实体类
 * 
 * @author guo
 *
 */
@Component
public class SmileEntity {
	private int id;
	private String content;// 内容
	private Date reporttime;// 上报时间
	private int love;// 点赞人数
	private int dislike;// 不喜欢的人数
    private String reportname;//上报人
    
    
    
	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReporttime() {
		return reporttime;
	}

	public void setReporttime(Date reporttime) {
		this.reporttime = reporttime;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}

}
