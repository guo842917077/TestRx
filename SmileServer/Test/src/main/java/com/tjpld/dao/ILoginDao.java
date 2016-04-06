package com.tjpld.dao;
import org.apache.ibatis.annotations.Param;
/**
 * 持久层接口 用Repository注解
 */
import org.springframework.stereotype.Repository;
/**
 * myBatis包中对应的namespace必须指向它
 * @author guo
 *
 */
@Repository
public interface ILoginDao {
    //查找用户是否存在
	int findUser(@Param("username")String username,@Param("password")String password);
	//查找用户个数
	int userCount();

} 
