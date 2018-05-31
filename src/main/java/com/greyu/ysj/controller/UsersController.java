package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.User;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.UserService;
import com.greyu.ysj.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import javax.naming.spi.DirStateFactory;
import javax.xml.ws.Response;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 21:50 2018/2/8.
 */
@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/v1/user", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getUsers(Integer page, Integer rows, String orderBy, User user) {
        System.out.println(user);
        List<User> users = this.userService.getAllUsers(page, rows, user);
        return new ResponseEntity<>(ResultModel.ok(users), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getUser(@PathVariable Integer userId) {
        User user = this.userService.selectUserById(userId);

        if (null == user) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(ResultModel.ok(user), HttpStatus.OK);
    }

    @RequestMapping(value= "/user/v1/user", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> addUser(User user) {
        ResultModel resultModel = this.userService.addUser(user);

        if (resultModel.getCode() == -1005) {
            return new ResponseEntity<>(resultModel, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resultModel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/admin/v1/user/{userId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteUser(@PathVariable Integer userId) {
        ResultModel resultModel = this.userService.deleteUser(userId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> updateUser(@PathVariable Integer userId, User user) {
        ResultModel resultModel = this.userService.updateUser(userId, user);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/avatar", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> uploadAvatar(@PathVariable Integer userId, MultipartFile avatar) {
        if (null == avatar) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.IMAGE_NOT_EMPTY), HttpStatus.BAD_REQUEST);
        }

        String fileName = "";
        try {
            fileName = FileUtil.upload(avatar, Constants.IMAGE_SAVE_PATH) ;
        } catch (Exception err) {
            err.printStackTrace();
        }

        ResultModel resultModel = this.userService.uploadAvatar(userId, fileName);

        if (resultModel.getCode() < 0) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }
}
