package com.greyu.ysj.service;

import com.greyu.ysj.entity.Administrator;
import com.greyu.ysj.model.ResultModel;

/**
 * Created by greyu on 2018/2/2.
 */
public interface AdministratorService {
    Administrator selectById(int adminId);

    Administrator selectByUserName(String username);

    ResultModel getAllAdmins(int adminId);

    ResultModel create(String userName, String passWord, String nickName, Long phone, Boolean superLevel);

    ResultModel update(Integer adminId, String passWord, String nickName, Long phone, Boolean superLevel);

    ResultModel delete(Integer adminId);
}
