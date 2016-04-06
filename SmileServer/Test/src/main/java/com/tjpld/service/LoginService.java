package com.tjpld.service;

import javax.annotation.Resource;

import org.apache.ibatis.io.ResolverUtil.IsA;
import org.springframework.stereotype.Service;
/**
 * Login表对应的Service层 需要用Service注解 用来完成login对应的具体业务操作
 * @author guo
 *
 */

import com.tjpld.dao.ILoginDao;
@Service
public class LoginService {
    @Resource
	ILoginDao dao;
    public int getUserCount(String username,String password){
    	return dao.userCount();
    }
    public int isExitsPeople(String username,String password){
    	return dao.findUser(username, password);
    }
}
