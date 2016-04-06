package com.tjpld.service;

import java.awt.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 
 * @author guo
 *
 */

import com.tjpld.dao.ISmileDao;
import com.tjpld.entity.SmileEntity;

@Service
public class SmileService {
	@Resource
	ISmileDao smileDao;

	public ArrayList<SmileEntity> findSmileContent() {
		return smileDao.findSmileContent();
	}
	public void submitSmileContent(String reportname,String content,String reporttime){
		smileDao.insertSmileContent(reportname, content, reporttime);
	}
}
