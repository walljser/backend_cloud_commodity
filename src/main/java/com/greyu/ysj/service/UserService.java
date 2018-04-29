package com.greyu.ysj.service;

import com.greyu.ysj.entity.User;
import com.greyu.ysj.model.ResultModel;

import java.util.List;

public interface UserService {

    User selectUserById(int userId);

    User selectByUserName(String username);

    List<User> getAllUsers(Integer page, Integer rows, User user);

    ResultModel addUser(User user);

    ResultModel deleteUser(int userId);

    ResultModel updateUser(int userId, User user);

    ResultModel uploadAvatar(int userId, String avatar);
}
