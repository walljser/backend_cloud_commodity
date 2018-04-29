package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.User;
import com.greyu.ysj.entity.UserExample;
import com.greyu.ysj.mapper.UserMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserById(int userId) {
        User user = this.userMapper.selectByPrimaryKey(userId);
        if (null != user.getAvatar() && !user.getAvatar().equals("")) {
            user.setAvatar(Constants.AVATAR_PREFIX_URL + user.getAvatar());
        }
        return user;
    }

    public User selectByUserName(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<User> users = this.userMapper.selectByExample(userExample);

        User user;
        try {
            user = users.get(0);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers(Integer page, Integer rows, User user) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if (null != user.getUserName()) {
            criteria.andUserNameEqualTo(user.getUserName());
        }
        if (null != user.getPhone()) {
            criteria.andPhoneEqualTo(user.getPhone());
        }
        if (null != user.getNickName()) {
            criteria.andNickNameEqualTo(user.getNickName());
        }
        if (null != user.getSex()) {
            criteria.andSexEqualTo(user.getSex());
        }

        List<User> users = this.userMapper.selectByExample(userExample);

        return users;
    }

    @Override
    public ResultModel addUser(User user) {
        if (null == user.getUserName() || null == user.getPassWord() ||
                null == user.getPhone()) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        User newUser = this.selectByUserName(user.getUserName());
        if (null != newUser) {
            return ResultModel.error(ResultStatus.USERNAME_HAS_EXISTS);
        }

        // 设置默认昵称为 ""
        if (null == user.getNickName()) {
            user.setNickName("user");
        }

        // 设置默认性别为 MAN
        if (null == user.getSex()) {
            user.setSex("MAN");
        }

        this.userMapper.insert(user);

        user = this.selectByUserName(user.getUserName());

        return ResultModel.ok(user);
    }

    @Override
    public ResultModel deleteUser(int userId) {
        User user = this.userMapper.selectByPrimaryKey(userId);

        if (null == user) {
            return ResultModel.error(ResultStatus.USER_NOT_FOUND);
        }

        this.userMapper.deleteByPrimaryKey(userId);
        return ResultModel.ok();
    }

    @Override
    public ResultModel updateUser(int userId, User user) {
        User newUser = this.userMapper.selectByPrimaryKey(userId);

        // 找不到 user
        if (null == newUser) {
            return ResultModel.error(ResultStatus.USER_NOT_FOUND);
        }

        if (null != user.getPassWord()) {
            newUser.setPassWord(user.getPassWord());
        }
        if (null != user.getPhone()) {
            newUser.setPhone(user.getPhone());
        }
        if (null != user.getSex()) {
            newUser.setSex(user.getSex());
        }
        if (null != user.getNickName()) {
            newUser.setNickName(user.getNickName());
        }

        this.userMapper.updateByPrimaryKey(newUser);

        return ResultModel.ok(newUser);
    }

    @Override
    public ResultModel uploadAvatar(int userId, String avatar) {
        User user = this.userMapper.selectByPrimaryKey(userId);

        if (null == user) {
            return ResultModel.error(ResultStatus.USER_NOT_FOUND);
        }

        user.setAvatar(avatar);
        this.userMapper.updateByPrimaryKey(user);

        return ResultModel.ok();
    }
}
