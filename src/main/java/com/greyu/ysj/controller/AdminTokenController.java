package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.authorization.annotation.CurrentUserId;
import com.greyu.ysj.authorization.manager.TokenManager;
import com.greyu.ysj.authorization.model.TokenModel;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Administrator;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greyu on 2018/2/2.
 */
@RestController
@RequestMapping("/admin/v1/tokens")
public class AdminTokenController {
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultModel> login(String userName, String passWord) {
        System.out.println(userName);
        System.out.println(passWord);

        if (null == userName || null == passWord) {
            return new  ResponseEntity<>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        Administrator administrator = this.administratorService.selectByUserName(userName);

        if (null == administrator ||
                !administrator.getPassWord().equals(passWord)) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }

        TokenModel token = this.tokenManager.createToken(administrator.getAdministratorId());
        return new ResponseEntity<>(ResultModel.ok(token), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> logout(@CurrentUserId Integer userId) {
        System.out.println("userId :" + userId);
        this.tokenManager.deleteToken(userId);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
