package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.*;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.mapper.OrderDetailMapper;
import com.greyu.ysj.mapper.OrderMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 22:14 2018/3/11.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public List<Order> getAllOrders(Integer page, Integer rows, String orderBy, Order order, Double start, Double end) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();

        // 订单金额在 [start, end] 之间
        if (null != start && null != end) {
            criteria.andAmountBetween(start, end);
        }
        // 设置查询条件 userId
        if (null != order.getUserId()) {
            criteria.andUserIdEqualTo(order.getUserId());
        }
        // 设置查询条件 status
        if (null != order.getStatus()) {
            criteria.andStatusEqualTo(order.getStatus());
        }

        List<Order> orderList = this.orderMapper.selectByExample(orderExample);

        return orderList;
    }

    @Override
    public ResultModel getOrderByUserId(Integer userId) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();

        criteria.andUserIdEqualTo(userId);
        List<Order> orders = this.orderMapper.selectByExample(orderExample);

        // 购物车详情
        for (Order order: orders) {
            OrderDetailExample orderDetailExample = new OrderDetailExample();
            OrderDetailExample.Criteria detailCriteria = orderDetailExample.createCriteria();
            detailCriteria.andOrderIdEqualTo(order.getOrderId());
            List<OrderDetail> orderDetails = this.orderDetailMapper.selectByExample(orderDetailExample);

            // 商品详细信息
            for (OrderDetail orderDetail: orderDetails) {
                Good good = this.goodMapper.selectByPrimaryKey(orderDetail.getGoodId()) ;
                orderDetail.setGood(good);
            }
            order.setOrderDetails(orderDetails);
        }

        return ResultModel.ok(orders);
    }

    @Override
    public ResultModel getOneOrder(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        return ResultModel.ok(order);
    }

    @Override
    public ResultModel create(Order order, Good good) {
        return null;
    }

    @Override
    public ResultModel updateOrder(Order order) {
        return null;
    }

    @Override
    public ResultModel delete(Integer orderId) {
        return null;
    }
}
