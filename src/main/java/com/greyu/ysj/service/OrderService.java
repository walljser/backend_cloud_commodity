package com.greyu.ysj.service;

import com.greyu.ysj.entity.Good;
import com.greyu.ysj.entity.Order;
import com.greyu.ysj.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 21:59 2018/3/11.
 */
public interface OrderService {
    List<Order> getAllOrders(Integer page, Integer rows, String orderBy, Order order, String userName, String start, String end);

    ResultModel getOrderByUserId(Integer userId, Integer status);

    ResultModel getOneOrder(Long orderId);

    ResultModel orderStatistics();

    ResultModel create(Integer userId, Integer addressId, String remarks, String cartDetailIds);

    ResultModel updateOrder(Order order);

    ResultModel delete(Long orderId);

    ResultModel refund(Long orderId);

    ResultModel confirm(Long orderId);

    ResultModel confirmRefund(Long orderId);

    ResultModel refuseRefund(Long orderId);

    ResultModel deliver(Long orderId);
}
