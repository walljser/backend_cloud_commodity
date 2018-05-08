package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.*;
import com.greyu.ysj.mapper.*;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartDetailMapper cartDetailMapper;

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

    /**
     * 创建新订单
     * @param userId
     * @param addressId
     * @param remarks
     * @param cartDetailIds
     * @return
     */
    @Override
    public ResultModel create(Integer userId, Integer addressId, String remarks, Long cartDetailIds[]) {
        Long cartId = cartDetailIds[0];
        // 获取用户购物车
        Cart userCart = this.cartMapper.selectByPrimaryKey(cartId);

        // 创建新订单
        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setAmount(0.0);
        order.setCreateTime(new Date());
        order.setRemarks(remarks);
        order.setStatus(Constants.ORDER_WAIT);
        // 插入数据库
        this.orderMapper.insert(order);
        long orderId = order.getOrderId();

        for(Long cartDetailId: cartDetailIds) {
            CartDetail cartDetail = this.cartDetailMapper.selectByPrimaryKey(cartDetailId);
            // 没有找到购物车
            if (null == cartDetail) {
                // 创建购物车失败，删除已插入的购物车信息
                this.orderMapper.deleteByPrimaryKey(orderId);
                return ResultModel.error(ResultStatus.CART_NOT_FOUND);
            }

            int goodId = cartDetail.getGoodId();
            Good good = this.goodMapper.selectByPrimaryKey(goodId);
            // 库存不足
            if (good.getInventory() < cartDetail.getCount()) {
                // 创建购物车失败，删除已插入的购物车信息
                this.orderMapper.deleteByPrimaryKey(orderId);
                return ResultModel.error(ResultStatus.GOOD_INSUFFICIENT);
            }

            // 计算商品总价格
            double price = good.getPrice();
            int count = cartDetail.getCount();
            double total = price * count;

            // 更新商品销量
            good.setSoldCount(good.getSoldCount() + count);
            // 更新商品库存
            good.setInventory(good.getInventory() - count);
            this.goodMapper.updateByPrimaryKey(good);

            // 插入购物车详情
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setGoodId(goodId);
            orderDetail.setCount(count);
            this.orderDetailMapper.insert(orderDetail);

            // 更新订单总金额
            order.setAmount(order.getAmount() + total);

            // 更新购物车总金额
            userCart.setAmount(userCart.getAmount() - total);
            // 删除购物车详情
            this.cartDetailMapper.deleteByPrimaryKey(cartDetailId);
        }

        // 更新购物车信息
        this.cartMapper.updateByPrimaryKey(userCart);
        // 更新订单信息
        this.orderMapper.updateByPrimaryKey(order);

        return ResultModel.ok(order);
    }

    @Override
    public ResultModel updateOrder(Order order) {
        return null;
    }

    /**
     * 申请退款
     * @param orderId
     * @return
     */
    @Override
    public ResultModel refund(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        order.setStatus(Constants.ORDER_REFUNDING);

        return ResultModel.ok();
    }

    /**
     * 发货
     * @param orderId
     * @return
     */
    @Override
    public ResultModel deliver(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        order.setStatus(Constants.ORDER_DISPATCHING);

        return ResultModel.ok();
    }

    /**
     * 确认订单配送完成
     * @param orderId
     * @return
     */
    @Override
    public ResultModel confirm(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        order.setStatus(Constants.ORDER_FINISH);

        return ResultModel.ok();
    }

    /**
     * 确认退款成功
     * @param orderId
     * @return
     */
    @Override
    public ResultModel confirmRefund(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        order.setStatus(Constants.ORDER_REFUND_SUCCESS);

        // 查出购物车id对应的购物车详情信息
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetails = this.orderDetailMapper.selectByExample(orderDetailExample);

        for (OrderDetail orderDetail: orderDetails) {
            int goodId = orderDetail.getGoodId();
            Good good = this.goodMapper.selectByPrimaryKey(goodId);
            int count = orderDetail.getCount();
            // 更新商品库存
            good.setInventory(good.getInventory() + count);
            // 更新商品销量
            good.setSoldCount(good.getSoldCount() - count);
        }

        return ResultModel.ok();
    }

    /**
     * 拒绝退款
     * @param orderId
     * @return
     */
    @Override
    public ResultModel refuseRefund(Long orderId) {
        Order order = this.orderMapper.selectByPrimaryKey(orderId);

        if (null == order) {
            return ResultModel.error(ResultStatus.ORDER_NOT_FOUND);
        }

        order.setStatus(Constants.ORDER_REFUNDING_FAILURE);

        return ResultModel.ok();
    }

    @Override
    public ResultModel delete(Long orderId) {
        return null;
    }
}
