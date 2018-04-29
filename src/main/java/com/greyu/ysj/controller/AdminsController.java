package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
