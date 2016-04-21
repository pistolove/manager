package com.lib.mysql;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class FacadeMySqlDao {

    @Resource(name = "UserMysqlDao")
    private UserMysqlDao userMysqlDao;

    public UserMysqlDao getUserMysqlDao() {
        return userMysqlDao;
    }

    public void setUserMysqlDao(UserMysqlDao userMysqlDao) {
        this.userMysqlDao = userMysqlDao;
    }

}
