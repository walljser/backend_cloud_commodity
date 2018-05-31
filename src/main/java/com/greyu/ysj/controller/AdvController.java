package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.entity.AdvSwiper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 20:41 2018/5/29.
 */
@RestController
public class AdvController {
    @Autowired
    private AdvService advService;

    @RequestMapping(value = "/user/v1/advs", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAll() {
        ResultModel resultModel = this.advService.getAll();

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/advs/{advId}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAdvByAdvId(@PathVariable Integer advId) {
        ResultModel resultModel = this.advService.getOne(advId);

        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        } else if(resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin/v1/advs", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> createAdv(String name, Integer categorySecondId, MultipartFile image) {
        ResultModel resultModel = this.advService.create(name, categorySecondId, image);

        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        } else if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin/v1/advs/{advId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> updateAdv(@PathVariable Integer advId, String name, Integer categorySecondId, MultipartFile image) {
        ResultModel resultModel = this.advService.update(advId, name, categorySecondId, image);
        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin/v1/advs/{advId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteByAdvId(@PathVariable Integer advId) {
        ResultModel resultModel = this.advService.delete(advId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
        }
    }
}
