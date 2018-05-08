package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Cart;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 23:11 2018/3/11.
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/user/v1/user/{userId}/cart", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getCart(@PathVariable Integer userId) {
        Cart cart = this.cartService.get(userId);
        return new ResponseEntity<ResultModel>(ResultModel.ok(cart), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/cartDetail/good/{goodId}")
    @Authorization
    public ResponseEntity<ResultModel> getCartByGooId(@PathVariable Integer userId, @PathVariable Integer goodId) {
        ResultModel resultModel = this.cartService.getCartDetailByGoodId(userId, goodId);

        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/cart", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> addCart(@PathVariable Integer userId, Integer goodId, Integer count) {
        System.out.println(count);
        System.out.println(goodId);
        if (null == userId) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

//        if (count <= 0) {
//            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.GOOD_NOT_LESS_THEN_ZERO), HttpStatus.BAD_REQUEST);
//        }

        ResultModel resultModel = this.cartService.save(userId, goodId, count);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }
        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/cart/{cartId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteAllCart(@PathVariable Integer userId, @PathVariable Long cartId) {
        ResultModel resultModel = this.cartService.delete(userId, cartId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/cart/{cartId}", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> updateCart(@PathVariable Integer userId, @PathVariable Long cartId, Integer goodId, Integer count) {
        if (null == count) {
            return new ResponseEntity<ResultModel>(ResultModel.ok(), HttpStatus.OK);
        }

        ResultModel resultModel = this.cartService.update(userId, cartId, goodId, count);

        if (resultModel.getCode() == -1010) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.FORBIDDEN);
        }

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}
