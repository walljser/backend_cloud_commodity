package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.*;
import com.greyu.ysj.mapper.*;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.model.StatisticsOrder;
import com.greyu.ysj.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Order> getAllOrders(Integer page, Integer rows, String orderBy, Order order, String userName, String start, String end) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();

        if (null != order.getOrderId()) {
            criteria.andOrderIdEqualTo(order.getOrderId());
        }

        // 订单创建时间在 [start, end] 之间
        if (null != start && null != end) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startTime;
            Date endTime;

            try {
                startTime = sdf.parse(start);
                endTime = sdf.parse(end);
            } catch (ParseException e) {
                startTime = null;
                endTime = null;
                e.printStackTrace();
            }

            if (null != startTime && null != endTime) {
                criteria.andCreateTimeBetween(startTime, endTime);
            }
        }

        // 根据用户名查询
        if (null != userName) {
            UserExample userExample = new UserExample();
            UserExample.Criteria userCriteria = userExample.createCriteria();
            userCriteria.andUserNameEqualTo(userName);
            List<User> users = this.userMapper.selectByExample(userExample);

            User user;
            try {
                user = users.get(0);
                criteria.andUserIdEqualTo(user.getUserId());
            } catch (Exception e) {
                user = null;
            }
        }

        // 设置查询条件 status
        if (null != order.getStatus()) {
            criteria.andStatusEqualTo(order.getStatus());
        }

        orderExample.setOrderByClause("create_time desc");
        List<Order> orderList = this.orderMapper.selectByExample(orderExample);

        for (Order temp: orderList) {
            Address address = this.addressMapper.selectByPrimaryKey(temp.getAddressId());
            temp.setAddress(address);

//            OrderDetailExample orderDetailExample = new OrderDetailExample();
//            OrderDetailExample.Criteria orderDetailCriteria = orderDetailExample.createCriteria();
//            orderDetailCriteria.andOrderIdEqualTo(temp.getOrderId());

            List<OrderDetail> orderDetails = this.orderDetailMapper.getAllByOrderId(temp.getOrderId());
            temp.setOrderDetails(orderDetails);

            System.out.println(temp);
        }

        return orderList;
    }

    /**
     * 获取订单资讯
     * @return
     */
    @Override
    public ResultModel orderStatistics() {
        Integer orderWaiting = countWait();

        Integer orderWaitingToday = countWaitToday();

        Integer orderRefunding = countRefunding();

        Integer orderSuccess = countSuccess();

        Integer orderSuccessToday = countSuccessToday();

        Integer orderDispatching = countDispatching();

        Double totalSale = countTotalSale();

        Double todaySale = countTodaySale();

        Integer collection = countCollection();

        Integer userCount = countUserCount();

        StatisticsOrder statisticsOrder = new StatisticsOrder();
        // 成交订单
        statisticsOrder.setSuccess(orderSuccess);
        // 今日成交
        statisticsOrder.setSuccessToday(orderSuccessToday);
        // 待发货
        statisticsOrder.setWait(orderWaiting);
        // 今日新增待发货
        statisticsOrder.setWaitToday(orderWaitingToday);
        // 配送中
        statisticsOrder.setDispatching(orderDispatching);
        // 待处理退款
        statisticsOrder.setRefunding(orderRefunding);

        // 总销售额
        statisticsOrder.setTotalSale(totalSale);

        // 今日销售额
        statisticsOrder.setTodaySale(todaySale);

        // 收藏数量
        statisticsOrder.setCollection(collection);

        // 用户数量
        statisticsOrder.setUserCount(userCount);

        return ResultModel.ok(statisticsOrder);
    }

    private Integer countUserCount() {
        UserExample userExample = new UserExample();
        Integer userCount = this.userMapper.countByExample(userExample);

        return userCount;
    }

    private Integer countCollection() {
        CartDetailExample cartExample = new CartDetailExample();
        CartDetailExample.Criteria criteria = cartExample.createCriteria();
        Integer collection = this.cartDetailMapper.countByExample(cartExample);

        return collection;
    }

    private Double countTotalSale() {
        Double totalSale = 0.0;
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_FINISH);

        List<Order> lists = this.orderMapper.selectByExample(orderExample);

        for (Order order: lists) {
            totalSale = totalSale + order.getAmount();
        }

        return totalSale;
    }

    private Double countTodaySale() {
        Double totalSale = 0.0;
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_FINISH);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = new Date();
        Date end = new Date();

        Date startTime;
        try {
            startTime = sdf.parse(sdf.format(start));
        } catch (ParseException e) {
            startTime = null;
            e.printStackTrace();
        }

        criteria.andCreateTimeBetween(startTime, end);

        List<Order> lists = this.orderMapper.selectByExample(orderExample);

        for (Order order: lists) {
            totalSale += order.getAmount();
        }

        return totalSale;
    }

    private Integer countDispatching() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_DISPATCHING);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    private Integer countWait() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_WAIT);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    private Integer countWaitToday() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_WAIT);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = new Date();
        Date end = new Date();

        Date startTime;
        try {
            startTime = sdf.parse(sdf.format(start));
        } catch (ParseException e) {
            startTime = null;
            e.printStackTrace();
        }

        criteria.andCreateTimeBetween(startTime, end);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    private Integer countRefunding() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_REFUNDING);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    private Integer countSuccess() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_FINISH);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    private Integer countSuccessToday() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andStatusEqualTo(Constants.ORDER_FINISH);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = new Date();
        Date end = new Date();

        Date startTime;
        try {
            startTime = sdf.parse(sdf.format(start));
        } catch (ParseException e) {
            startTime = null;
            e.printStackTrace();
        }

        criteria.andCreateTimeBetween(startTime, end);
        Integer count = this.orderMapper.countByExample(orderExample);
        return count;
    }

    @Override
    public ResultModel getOrderByUserId(Integer userId, Integer status) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();

        if (null != status) {
            criteria.andStatusEqualTo(status);
        }

        criteria.andUserIdEqualTo(userId);
        orderExample.setOrderByClause("create_time DESC");
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

    /**
     * 根据orderId获取order信息
     * @param orderId
     * @return
     */
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
     * @param ids  购物车详情id
     * @return
     */
    @Override
    public ResultModel create(Integer userId, Integer addressId, String remarks, String ids) {
        String[] str = ids.split(",");
        int length = str.length;
        Long[] cartDetailIds = new Long[length];
        for (int i = 0; i < length ; i++) {
            cartDetailIds[i] = Long.parseLong(str[i]);
        }

        Long oneCartDetailId = cartDetailIds[0];
        // 获取用户购物车
        CartDetail oneCartDetail = this.cartDetailMapper.selectByPrimaryKey(oneCartDetailId);
        Cart userCart;
        if (null != oneCartDetail) {
            userCart = this.cartMapper.selectByPrimaryKey(oneCartDetail.getCartId());
        } else {
            userCart = new Cart();
        }

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
        System.out.println(666);

        for(Long cartDetailId: cartDetailIds) {
            CartDetail cartDetail = this.cartDetailMapper.selectByPrimaryKey(cartDetailId);
            // 没有找到购物车
            if (null == cartDetail) {
                System.out.println("meiyou");
                // 创建订单失败，删除已插入的购物车信息
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
        this.orderMapper.updateByPrimaryKey(order);

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
        this.orderMapper.updateByPrimaryKey(order);

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
        this.orderMapper.updateByPrimaryKey(order);

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
        this.orderMapper.updateByPrimaryKey(order);

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

            this.goodMapper.updateByPrimaryKey(good);
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
        this.orderMapper.updateByPrimaryKey(order);

        return ResultModel.ok();
    }

    @Override
    public ResultModel delete(Long orderId) {
        return null;
    }
}
