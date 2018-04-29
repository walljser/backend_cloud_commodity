package com.greyu.ysj.service;

import com.greyu.ysj.entity.Cart;
import com.greyu.ysj.entity.CartDetail;
import com.greyu.ysj.model.ResultModel;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 23:12 2018/3/11.
 */
public interface CartService {
    Cart get(Integer userId);

    ResultModel getCartDetailByGoodId(Integer userId, Integer goodId);

    ResultModel save(Integer userId, Integer goodId, Integer Count);

    ResultModel delete(Integer userId, Long cartId);

    ResultModel deleteOne(Integer goodId);

    ResultModel update(Integer userId, Long cartId, Integer goodId, Integer count);
}
