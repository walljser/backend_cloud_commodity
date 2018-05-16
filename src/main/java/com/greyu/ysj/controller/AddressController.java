package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.entity.Address;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 17:48 2018/3/9.
 */
@RestController
@RequestMapping("/user/v1/user")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 获取用户的全部address
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/{userId}/address", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getAll(@PathVariable Integer userId, Integer page, Integer rows) {
        ResultModel resultModel = this.addressService.getUserAllAddress(userId, page, rows);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 获取用户的一个 address
     * @param userId
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/{userId}/address/{addressId}", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getOne(@PathVariable Integer userId, @PathVariable Integer addressId) {
        System.out.println(userId);
        System.out.println(addressId);
        ResultModel resultModel = this.addressService.getOne(userId, addressId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 添加address    post
     * @param userId
     * @param newAddress
     * @return
     */
    @RequestMapping(value = "/{userId}/address", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> addAddress(@PathVariable Integer userId, Address newAddress) {
        System.out.println(newAddress);
        ResultModel resultModel = this.addressService.save(newAddress);

        if (resultModel.getCode() == -1002) {
            if (resultModel.getMessage() == "字段不能为空") {
                // 返回字段不能为空
                return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
            } else {
//                返回用户不存在
                return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    /**
     * 修改地址信息
     * @param userId
     * @param addressId
     * @param address
     * @return
     */
    @RequestMapping(value = "/{userId}/address/{addressId}", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> updateAddress(@PathVariable Integer userId, @PathVariable Integer addressId, Address address) {
        System.out.println(address);
        ResultModel resultModel = this.addressService.update(address);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<ResultModel> deleteAddress(@PathVariable Integer userId, @PathVariable Integer addressId, Address address){
        System.out.println(address);
        ResultModel resultModel = this.addressService.delete(address);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}
