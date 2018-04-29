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
}
