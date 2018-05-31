package com.greyu.ysj.service.impl;

import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Administrator;
import com.greyu.ysj.entity.AdministratorExample;
import com.greyu.ysj.mapper.AdministratorMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greyu on 2018/2/2.
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public Administrator selectById(int adminId) {
        Administrator administrator = this.administratorMapper.selectByPrimaryKey(adminId);
        return administrator;
    }

    @Override
    public Administrator selectByUserName(String username) {
        AdministratorExample administratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = administratorExample.createCriteria();
        criteria.andUserNameEqualTo(username);

        List<Administrator> administrators = this.administratorMapper.selectByExample(administratorExample);

        Administrator administrator;
        try {
            administrator = administrators.get(0);
        } catch (Exception e) {
            return null;
        }
        return administrator;
    }

    @Override
    public ResultModel getAllAdmins(int adminId) {
        Administrator administrator = this.administratorMapper.selectByPrimaryKey(adminId);

        if (null == administrator) {
            return ResultModel.error(ResultStatus.ADMIN_NOT_FOUND);
        }

        if (administrator.getSuperLevel() == true) {
            AdministratorExample example = new AdministratorExample();
            AdministratorExample.Criteria criteria = example.createCriteria();
            List<Administrator> admins = this.administratorMapper.selectByExample(example);

            return ResultModel.ok(admins);
        }

        return ResultModel.error(ResultStatus.NOT_SUPER_ADMIN);
    }

    @Override
    public ResultModel create(String userName, String passWord, String nickName, Long phone, Boolean superLevel) {
        if (null == userName ||
                null == passWord ||
                null == phone) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        AdministratorExample administratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = administratorExample.createCriteria();
        criteria.andUserNameEqualTo(userName);

        List<Administrator> resultList = this.administratorMapper.selectByExample(administratorExample);
        Administrator exists;
        try {
            exists = resultList.get(0);
        } catch (Exception e) {
            exists = null;
        }

        if (null != exists) {
            return ResultModel.error(ResultStatus.USERNAME_HAS_EXISTS);
        }

        Administrator administrator = new Administrator();
        administrator.setUserName(userName);
        administrator.setPassWord(passWord);
        administrator.setPhone(phone);

        if (null != nickName) {
            administrator.setNickName(nickName);
        } else {
            administrator.setNickName("");
        }

        if (null != superLevel) {
            administrator.setSuperLevel(superLevel);
        } else {
            administrator.setSuperLevel(false);
        }

        this.administratorMapper.insert(administrator);
        return ResultModel.ok();
    }

    @Override
    public ResultModel update(Integer adminId, String passWord, String nickName, Long phone, Boolean superLevel) {
        Administrator oldAdministrator = this.administratorMapper.selectByPrimaryKey(adminId);
        if (null == oldAdministrator) {
            return ResultModel.error(ResultStatus.ADMIN_NOT_FOUND);
        }

        if (null != passWord) {
            oldAdministrator.setPassWord(passWord);
        }
        if (null != nickName) {
            oldAdministrator.setNickName(nickName);
        }
        if (null != phone) {
            oldAdministrator.setPhone(phone);
        }
        if (null != nickName) {
            oldAdministrator.setSuperLevel(superLevel);
        }

        this.administratorMapper.updateByPrimaryKey(oldAdministrator);

        return ResultModel.ok();
    }

    @Override
    public ResultModel delete(Integer adminId) {
        Administrator administrator = this.administratorMapper.selectByPrimaryKey(adminId);
        if (null == administrator) {
            return ResultModel.error(ResultStatus.ADMIN_NOT_FOUND);
        } else {
            this.administratorMapper.deleteByPrimaryKey(adminId);
            return ResultModel.ok();
        }
    }
}
