package com.greyu.ysj.service.impl;

import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.*;
import com.greyu.ysj.mapper.CartDetailMapper;
import com.greyu.ysj.mapper.CartMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 23:12 2018/3/11.
 */
@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartDetailMapper cartDetailMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public Cart get(Integer userId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);

        Cart cart;
        try {
            cart = this.cartMapper.selectByExample(cartExample).get(0);
        } catch (Exception e) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setAmount(0.0);
            this.cartMapper.insert(cart);
            cart = this.cartMapper.selectByExample(cartExample).get(0);
            e.printStackTrace();
        }

        // 查找出购物车详情
        CartDetailExample cartDetailExample = new CartDetailExample();
        CartDetailExample.Criteria cartCriteria = cartDetailExample.createCriteria();
        // 购物车id
        cartCriteria.andCartIdEqualTo(cart.getCartId());
        // 不等于0
        cartCriteria.andCountNotEqualTo(0);
        List<CartDetail> cartDetails = this.cartDetailMapper.selectByExample(cartDetailExample);

        for (CartDetail c : cartDetails) {
            Good good = this.goodMapper.selectByPrimaryKey(c.getGoodId());
            c.setGood(good);
        }
        cart.setGoods(cartDetails);

        System.out.println(cart);
        return cart;
    }

    @Override
    public ResultModel getCartDetailByGoodId(Integer userId, Integer goodId) {
        // 先查出用户的购物车
        CartExample cartExample = new CartExample();
        CartExample.Criteria cartCriteria = cartExample.createCriteria();
        cartCriteria.andUserIdEqualTo(userId);
        Cart cart;
        try {
            cart = this.cartMapper.selectByExample(cartExample).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            cart = null;
        }

        if (null == cart) {
            return ResultModel.error(ResultStatus.CART_NOT_FOUND);
        }

        // 查找出 goodId 的购物车详情
        CartDetailExample cartDetailExample = new CartDetailExample();
        CartDetailExample.Criteria criteria = cartDetailExample.createCriteria();

        criteria.andGoodIdEqualTo(goodId);
        criteria.andCartIdEqualTo(cart.getCartId());

        CartDetail cartDetail;

        try {
            cartDetail = this.cartDetailMapper.selectByExample(cartDetailExample).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            cartDetail = null;
        }

        if (null == cartDetail) {
            return ResultModel.error(ResultStatus.CART_NOT_FOUND);
        }

        return ResultModel.ok(cartDetail);
    }

    @Override
    public ResultModel save(Integer userId, Integer goodId, Integer count) {
        Good good = this.goodMapper.selectByPrimaryKey(goodId);
        // 商品不能存在
        if (null == good) {
            return ResultModel.error(ResultStatus.GOOD_NOT_FOUND);
        }

        if (good.getInventory() < count) {
            return ResultModel.error(ResultStatus.GOOD_INSUFFICIENT);
        }

        // 读取数据库中该用户的的购物车信息
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        Cart cart;
        try {
            cart = this.cartMapper.selectByExample(cartExample).get(0);
        } catch (Exception e) {
            cart = null;
        }

        // 如果数据库中，该用户没有购物车，设置默认 Cart 属性
        if (null == cart) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setAmount(0.0);
            // 新建购物车
            this.cartMapper.insert(cart);
        }

        CartDetailExample cartDetailExample = new CartDetailExample();
        CartDetailExample.Criteria cartDetailCriteria1 = cartDetailExample.createCriteria();
        // 设置购物车详情的 购物车id
        cartDetailCriteria1.andCartIdEqualTo(cart.getCartId());
        // 设置购物车详情的 商品id
        cartDetailCriteria1.andGoodIdEqualTo(goodId);

        // 查出该购物车详情
        CartDetail cartDetail;
        try {
            cartDetail = this.cartDetailMapper.selectByExample(cartDetailExample).get(0);
        } catch (Exception e) {
            cartDetail = null;
        }

        // 取数 该商品的 价格
        Double price = good.getPrice();

        // 如果购物车里面还没有该商品，先插入一条新信息
        // 如果已经有了，更新商品的数量
        if (null == cartDetail) {
            if (count > 0) {
                cartDetail = new CartDetail();
                cartDetail.setCartId(cart.getCartId());
                cartDetail.setGoodId(goodId);

                // 设置购物车详情中的 商品数量
                cartDetail.setCount(count);

                // 计算购物车总价格
                cart.setAmount(cart.getAmount() + price * cartDetail.getCount());

                // 更新购物车
                this.cartMapper.updateByPrimaryKey(cart);
                // 插入购物车详情
                this.cartDetailMapper.insert(cartDetail);
            }
        } else {
            // 如果 购物车中的数量不够减小
            if (cartDetail.getCount() + count < 0) {
                // 设置 购物车详情中的 商品数量
                cartDetail.setCount(0);

                // 计算金额, 并更新数据库
                cart.setAmount(cart.getAmount() + price * cartDetail.getCount() * -1);
            } else {
                // 设置 购物车详情中的 商品数量,  添加 count
                cartDetail.setCount(cartDetail.getCount() + count);

                // 计算金额, 并更新数据库
                cart.setAmount(cart.getAmount() + price * count);
            }
            // 更新购物车
            this.cartMapper.updateByPrimaryKey(cart);
            // 更新购物车详情
            this.cartDetailMapper.updateByPrimaryKey(cartDetail);
        }

        Cart lastCart = this.cartMapper.getAllCart(userId);
        return ResultModel.ok(lastCart);
    }

    @Override
    public ResultModel delete(Integer userId, Long cartId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);

        Cart cart;
        try {
            cart = this.cartMapper.selectByExample(cartExample).get(0);
        } catch (Exception e) {
            cart = null;
        }

        if (cart == null) {
            return ResultModel.error(ResultStatus.CART_NOT_FOUND);
        }

        this.cartMapper.deleteByPrimaryKey(cart.getCartId());

        return ResultModel.ok();
    }

    @Override
    public ResultModel deleteOne(Integer goodId) {
        return null;
    }

    @Override
    public ResultModel update(Integer userId, Long cartId, Integer goodId, Integer count) {
        Cart cart = this.cartMapper.selectByPrimaryKey(cartId);
        // 未找到购物车
        if (null == cart) {
            return ResultModel.error(ResultStatus.CART_NOT_FOUND);
        }

        // 没有权限
        if (!cart.getUserId().equals(userId)) {
            return ResultModel.error(ResultStatus.USER_NOT_ALLOWED);
        }

        Good good = this.goodMapper.selectByPrimaryKey(goodId);
        // 未找到商品
        if (null == good) {
            return ResultModel.error(ResultStatus.GOOD_NOT_FOUND);
        }

        // 商品库存不足
        if ((good.getInventory() + count) < 0) {
            return ResultModel.error(ResultStatus.GOOD_INSUFFICIENT);
        }

        // 查出购物车中该商品的信息
        CartDetailExample cartDetailExample = new CartDetailExample();
        CartDetailExample.Criteria criteria = cartDetailExample.createCriteria();
        criteria.andCartIdEqualTo(cartId);
        criteria.andGoodIdEqualTo(goodId);
        List<CartDetail> cartDetailList = this.cartDetailMapper.selectByExample(cartDetailExample);

        CartDetail cartDetail;
        try {
            cartDetail = cartDetailList.get(0);
        } catch (Exception e) {
            cartDetail = null;
        }

        if (null == cartDetail) { // 购物车中没有该商品
            cartDetail = new CartDetail();
            // 如果想要减少商品数量，直接返回成功，因为购物车里本就没有这个商品
            if (count <= 0) {
                return ResultModel.ok();
            }

            cartDetail.setCartId(cartId);
            cartDetail.setGoodId(goodId);
            cartDetail.setCount(count);

            this.cartDetailMapper.insert(cartDetail);
            // 更新购物车总价格
            cart.setAmount(cart.getAmount() + good.getPrice() * count);
            this.cartMapper.updateByPrimaryKey(cart);

            // 查出更新后的购物车信息
            cart = this.cartMapper.getAllCart(userId);
            return ResultModel.ok(cart);

        } else { // 购物车中已有该商品
            // 更新后，购物车中该商品的数量 <= 0
            if ((cartDetail.getCount() + count) <= 0) {
                // 更新购物车总价
                cart.setAmount(cart.getAmount() - cartDetail.getCount() * good.getPrice());
                // 在购物车中删除该商品
                this.cartDetailMapper.deleteByPrimaryKey(cartDetail.getCartDetailId());

                cart = this.cartMapper.getAllCart(userId);
                return ResultModel.ok(cart);
            }else { // 更新购物车中该商品的数目
                cartDetail.setCount(cartDetail.getCount() + count);
                this.cartDetailMapper.updateByPrimaryKey(cartDetail);

                // 更新购物车总价
                cart.setAmount(cart.getAmount() + good.getPrice() * count);
                this.cartMapper.updateByPrimaryKey(cart);

                cart = this.cartMapper.getAllCart(userId);
                return ResultModel.ok(cart);
            }
        }
    }
}
