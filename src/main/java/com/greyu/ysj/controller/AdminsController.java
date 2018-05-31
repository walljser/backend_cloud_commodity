package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 14:33 2018/4/23.
 */
@RestController
public class AdminsController {
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/admin/v1/admins/{adminId}")
    @Authorization
    public ResponseEntity<ResultModel> getAll(@PathVariable Integer adminId) {
        if (null == adminId) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.ADMIN_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        ResultModel resultModel = this.administratorService.getAllAdmins(adminId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        if (resultModel.getCode() == -1010) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/admins", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> createNewAdmin(String userName, String passWord, String nickName, Long phone, Boolean superLevel) {
        ResultModel resultModel = this.administratorService.create(userName, passWord, nickName, phone, superLevel);

        if (resultModel.getCode() == -1004 || resultModel.getCode() == -1005) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/admins/{adminId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> updateByAdminId(@PathVariable Integer adminId, String passWord, String nickName, Long phone, Boolean superLevel) {
        ResultModel resultModel = this.administratorService.update(adminId, passWord, nickName, phone, superLevel);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/admins/{adminId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteByAdminId(@PathVariable Integer adminId) {
        ResultModel resultModel = this.administratorService.delete(adminId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}
