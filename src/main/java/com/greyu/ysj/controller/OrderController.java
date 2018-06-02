package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Order;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 21:57 2018/3/11.
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/admin/v1/order", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getAllOrders(Integer page, Integer rows,
                                                   String orderBy, Order order,
                                                   String start, String end,
                                                   String userName) {
        List<Order> orders = this.orderService.getAllOrders(page, rows, orderBy, order, userName, start, end);

        return new ResponseEntity<ResultModel>(ResultModel.ok(orders),HttpStatus.OK);
    }

    /**
     * 获取订单资讯
     * @return
     */
    @RequestMapping(value = "/admin/v1/statistics/order", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> orderCount() {
        ResultModel resultModel = this.orderService.orderStatistics();
        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/order/{orderId}", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getOneOrder(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.getOneOrder(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/order", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getOrderByUserId(@PathVariable Integer userId, Integer status) {
        System.out.println(status);
        ResultModel resultModel = this.orderService.getOrderByUserId(userId ,status);
        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/order", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> createOrder(@PathVariable Integer userId, Integer addressId, String remarks, String cartDetailIds) {
        if (null == userId ||
                null == addressId ||
                null == cartDetailIds) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        ResultModel resultModel = this.orderService.create(userId, addressId, remarks, cartDetailIds);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }
        if (resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 发货
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/v1/order/{orderId}/deliver", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> deliver(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.deliver(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 配送完成
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/v1/order/{orderId}/confirm", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> confirm(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.confirm(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 拒绝退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/v1/order/{orderId}/refuse", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> refuseRefund(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.refuseRefund(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 申请退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/user/v1/order/{orderId}/refund", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> refund(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.refund(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 同意退款
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/admin/v1/order/{orderId}/refund", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> confirmRefund(@PathVariable Long orderId) {
        ResultModel resultModel = this.orderService.confirmRefund(orderId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}
