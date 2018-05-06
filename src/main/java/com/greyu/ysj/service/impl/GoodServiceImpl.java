package com.greyu.ysj.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.entity.Good;
import com.greyu.ysj.entity.GoodExample;
import com.greyu.ysj.mapper.CategorySecondMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 16:11 2018/3/9.
 */
@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Override
    public PageInfo<Good> getAll(Integer page, Integer rows, Good good, Boolean goodStatus) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }
        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();

        // 商品是否下架   false 表示下架
        if (null != goodStatus) {
            if (goodStatus) {
                criteria.andInventoryGreaterThan(0);
            } else {
                criteria.andInventoryEqualTo(0);
            }
        }

        if (null != good.getGoodId()) {
            criteria.andGoodIdEqualTo(good.getGoodId());
        }

        if (null != good.getCategorySecondId()) {
            criteria.andCategorySecondIdEqualTo(good.getCategorySecondId());
        }

        if (null != good.getGoodName()) {
            System.out.println(good.getGoodName());
            String goodName = "%" + good.getGoodName() + "%";
            System.out.println(goodName);
            criteria.andGoodNameLike(goodName);
        }

//        List<Good> list = this.goodMapper.getAllByGood(good);
        List<Good> list = this.goodMapper.selectByExample(goodExample);
        for (Good g: list) {
            CategorySecond categorySecond = this.categorySecondMapper.selectByPrimaryKey(g.getCategorySecondId());
            g.setCategory(categorySecond);
            g.setImage(Constants.IMAGE_PREFIX_URL + g.getImage());
        }
        PageInfo<Good> pageInfo = new PageInfo<Good>(list);
        return pageInfo;
    }

    @Override
    public List<Good> getAllGoodsOnShell(Integer page, Integer rows, String orderBy, Good good) {
        if (null != page && null != rows) {
            if (null != orderBy) {
                PageHelper.startPage(page, rows, orderBy);
            } else {
                PageHelper.startPage(page, rows);
            }
        }

        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();

        if (null != good.getGoodId()) {
            criteria.andGoodIdEqualTo(good.getGoodId());
        }

        if (null != good.getCategorySecondId()) {
            criteria.andCategorySecondIdEqualTo(good.getCategorySecondId());
        }

        if (null != good.getGoodName()) {
            String goodName = "%" + good.getGoodName() + "%";
            criteria.andGoodNameLike(goodName);
        }

        // Inventory > 0
        criteria.andInventoryGreaterThan(0);
        List<Good> list = this.goodMapper.selectByExample(goodExample);
        for (Good g: list) {
            g.setImage(Constants.IMAGE_PREFIX_URL + g.getImage());
        }
        return list;
    }

    @Override
    public List<Good> getCategoryGoods(Integer page, Integer rows, Good good) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();

        if (null != good.getCategorySecondId()) {
            criteria.andCategorySecondIdEqualTo(good.getCategorySecondId());
        }

        List<Good> goodList = this.goodMapper.selectByExample(goodExample);
        for (Good g: goodList) {
            g.setImage(Constants.IMAGE_PREFIX_URL + g.getImage());
        }
        return goodList;
    }

    @Override
    public Good selectByGoodName(String name) {
        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();
        criteria.andGoodNameEqualTo(name);

        List<Good> goodList = this.goodMapper.selectByExample(goodExample);

        Good good;
        try {
            good = goodList.get(0);
        } catch(Exception e) {
            good = null;
        }

        return good;
    }


    @Override
    public Good getOne(Integer goodId) {
        Good good = this.goodMapper.selectByPrimaryKey(goodId);
        good.setImage(Constants.IMAGE_PREFIX_URL + good.getImage());
        CategorySecond category = this.categorySecondMapper.selectByPrimaryKey(good.getCategorySecondId());
        good.setCategory(category);
        return good;
    }

    @Override
    public ResultModel save(Good good) {
        System.out.println(good);
        // 除了 categoryId 其他字段都不能为空
        if (null == good.getGoodName() || null == good.getCategorySecondId() ||
                null == good.getPrice() || null == good.getImage() ||
                null == good.getOrigin() || null == good.getSpec()) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        // 判断商品名称是否已存在
        Good newGood = this.selectByGoodName(good.getGoodName());
        if (null != newGood) {
            return ResultModel.error(ResultStatus.GOOD_NAME_HAS_EXISTS);
        }

        // 默认库存为0
        if (null == good.getInventory()) {
            good.setInventory(0);
        }

        // 设置默认原价 = 现价
        if (null == good.getOriginalPrice()) {
            good.setOriginalPrice(good.getPrice());
        }

        // 设置默认已售0件
        good.setSoldCount(0);

        // 插入 good
        this.goodMapper.insert(good);

        // 重新查出来， 包括 goodId
        good = this.selectByGoodName(good.getGoodName());
        good.setImage(Constants.IMAGE_PREFIX_URL + good.getImage());
        return ResultModel.ok(good);
    }

    @Override
    public ResultModel delete(Integer goodId) {
        Good good = this.goodMapper.selectByPrimaryKey(goodId);

        // 查不到商品
        if (null == good) {
            return ResultModel.error(ResultStatus.GOOD_NOT_FOUND);
        }

        this.goodMapper.deleteByPrimaryKey(goodId);

        return ResultModel.ok();
    }

    @Override
    public ResultModel update(Integer goodId, Good good) {
        Good newGood = this.goodMapper.selectByPrimaryKey(goodId);
        if (null == newGood) {
            return ResultModel.error(ResultStatus.GOOD_NOT_FOUND);
        }

        if (null != good.getGoodName()) {
            newGood.setGoodName(good.getGoodName());
        }

        if (null != good.getCategorySecondId()) {
            CategorySecond category = this.categorySecondMapper.selectByPrimaryKey(good.getCategorySecondId());

            if (null != category) {
                newGood.setCategorySecondId(good.getCategorySecondId());
            }
        }

        if (null != good.getPrice()) {
            newGood.setPrice(good.getPrice());
        }

        if (null != good.getOriginalPrice()) {
            newGood.setOriginalPrice(good.getOriginalPrice());
        }

        if (null != good.getInventory()) {
            newGood.setInventory(good.getInventory());
        }

        if (null != good.getOrigin()) {
            newGood.setOrigin(good.getOrigin());
        }

        if (null != good.getSoldCount()) {
            newGood.setSoldCount(good.getSoldCount());
        }

        if (null != good.getSpec()) {
            newGood.setSpec(good.getSpec());
        }

        this.goodMapper.updateByPrimaryKey(newGood);

        good = this.goodMapper.selectByPrimaryKey(good.getGoodId());

        return ResultModel.ok(good);
    }

    @Override
    public ResultModel increaseInventory(Integer goodId, Integer inventory) {
        Good good = this.goodMapper.selectByPrimaryKey(goodId);

        if (null == good) {
            return ResultModel.error(ResultStatus.GOOD_NOT_FOUND);
        }

        // 出库判断
        if (inventory < 0) {
            if (good.getInventory() < inventory) {
                return ResultModel.error(ResultStatus.GOOD_INSUFFICIENT);
            }
        }

        good.setInventory(good.getInventory() + inventory);

        this.goodMapper.updateByPrimaryKey(good);

        return ResultModel.ok();
    }
}
