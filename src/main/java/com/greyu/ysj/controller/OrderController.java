package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.entity.Good;
import com.greyu.ysj.entity.Order;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
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
                                                   Double start, Double end ) {
        List<Order> orders = this.orderService.getAllOrders(page, rows, orderBy, order, start, end);

        return new ResponseEntity<ResultModel>(ResultModel.ok(orders),HttpStatus.OK);
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
    public ResponseEntity<ResultModel> getOrderByUserId(@PathVariable Integer userId) {
        ResultModel resultModel = this.orderService.getOrderByUserId(userId);
        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/order", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> createOrder(@PathVariable Integer userId, Integer addressId, Integer[] goodIds) {
        for (Integer id : goodIds) {
            System.out.println(id);
        }
        return null;
    }
}
