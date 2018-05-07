package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.*;
import com.greyu.ysj.mapper.CategoryFirstMapper;
import com.greyu.ysj.mapper.CategorySecondMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CategoryFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: Category Service
 * @Author: gre_yu@163.com
 * @Date: Created in 14:41 2018/3/9.
 */
@Service
public class CategoryFirstServiceImpl implements CategoryFirstService {
    @Autowired
    private CategoryFirstMapper categoryFirstMapper;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public CategoryFirst selectByCategoryName(String categoryName) {
        CategoryFirstExample categoryExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCategoryNameEqualTo(categoryName);

        List<CategoryFirst> list = this.categoryFirstMapper.selectByExample(categoryExample);

        CategoryFirst category;
        try {
            category = list.get(0);
        } catch (Exception e) {
            category = null;
        }

        return category;
    }

    @Override
    public List<CategoryFirst> getAll(Integer page, Integer rows) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        CategoryFirstExample categoryExample = new CategoryFirstExample();
        List<CategoryFirst> list = this.categoryFirstMapper.selectByExample(categoryExample);
        for (CategoryFirst category: list) {
            CategorySecondExample categorySecondExample = new CategorySecondExample();
            CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
            criteria.andCategoryFirstIdEqualTo(category.getCategoryFirstId());
            List<CategorySecond> seconds = this.categorySecondMapper.selectByExample(categorySecondExample);
            category.setCategorySeconds(seconds);
        }

        return list;
    }

    @Override
    public CategoryFirst getOne(Integer categoryId) {
        CategoryFirst category = this.categoryFirstMapper.selectByPrimaryKey(categoryId);
        return category;
    }

    /**
     * 保存category

     * @return
     */
    @Override
    public ResultModel save(String categoryName) {
        System.out.println(categoryName);
        CategoryFirst category = this.selectByCategoryName(categoryName);

        if (null != category) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

        category = new CategoryFirst();
        category.setCategoryName(categoryName);
        this.categoryFirstMapper.insert(category);

        category = this.selectByCategoryName(categoryName);
        return ResultModel.ok(category);
    }

    /**
     * 删除category
     * @param categoryId
     * @return
     */
    @Override
    public ResultModel delete(Integer categoryId) {
        CategoryFirst category = this.categoryFirstMapper.selectByPrimaryKey(categoryId);

        if (null == category) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryFirstIdEqualTo(categoryId);

        Integer count = this.categorySecondMapper.countByExample(categorySecondExample);

        if (count > 0) {
            return ResultModel.error(ResultStatus.CATEGORY_OWN_SECONDS);
        }

        this.categoryFirstMapper.deleteByPrimaryKey(categoryId);

        return ResultModel.ok();
    }

    /**
     * 根据category的id 更新category信息
     * @param categoryId
     * @param category
     * @return
     */
    @Override
    public ResultModel update(Integer categoryId, CategoryFirst category) {
        CategoryFirst newCategory = this.categoryFirstMapper.selectByPrimaryKey(categoryId);

        if (null == newCategory) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        // 分类名称已存在
        CategoryFirstExample categoryExample = new CategoryFirstExample();
        CategoryFirstExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCategoryNameEqualTo(category.getCategoryName());
        try {
            newCategory = this.categoryFirstMapper.selectByExample(categoryExample).get(0);
        } catch (Exception e) {
            newCategory = null;
        }
        if (null != newCategory && newCategory.getCategoryFirstId() != categoryId) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

        this.categoryFirstMapper.updateByPrimaryKey(category);

        return ResultModel.ok(category);
    }
}
