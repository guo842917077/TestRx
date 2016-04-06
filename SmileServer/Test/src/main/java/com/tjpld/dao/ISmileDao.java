package com.tjpld.dao;

import java.awt.List;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 调用Smile表
 * @author guo
 *
 */

import com.tjpld.entity.SmileEntity;
@Repository
public interface ISmileDao {
   public ArrayList<SmileEntity> findSmileContent();
   public void insertSmileContent(@Param("reportname")String reportname,@Param("content")String content,@Param("reporttime")String reporttime);
}
