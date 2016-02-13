package com.tjpld.yuanhangrxjava.config.model;

/**
 * 登录界面的model
 */
public class UserModel {
    String id;
    String username;
    String password;
    String fastCode;
    String departmentId;
    String departmentName;
    String modules;
    //文件参数
    public static String SP_ID = "SP_ID";
    public static String SP_UserName = "SP_UserName";
    public static String SP_PassWord = "SP_PassWord";
    public static String SP_PersonID = "SP_PersonID";
    public static String SP_departmentID = "SP_departmentID";

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFastCode() {
        return fastCode;
    }

    public void setFastCode(String fastCode) {
        this.fastCode = fastCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
