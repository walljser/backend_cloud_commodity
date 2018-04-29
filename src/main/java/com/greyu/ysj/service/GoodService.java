package com.greyu.ysj.service;

import com.github.pagehelper.PageInfo;
import com.greyu.ysj.entity.Good;
import com.greyu.ysj.model.ResultModel;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 16:06 2018/3/9.
 */
public interface GoodService {
    PageInfo<Good> getAll(Integer page, Integer rows, Good good, Boolean goodStatus);

    List<Good> getAllGoodsOnShell(Integer page, Integer rows, String orderBy, Good good);

    List<Good> getCategoryGoods(Integer page, Integer rows, Good good);

    Good getOne(Integer goodId);

    Good selectByGoodName(String name);

    ResultModel save(Good good);

    ResultModel delete(Integer goodId);

    ResultModel update(Integer goodId, Good good);

    ResultModel increaseInventory(Integer goodId, Integer inventory);
}
