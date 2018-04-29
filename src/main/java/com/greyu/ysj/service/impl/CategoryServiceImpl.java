package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Category;
import com.greyu.ysj.entity.CategoryExample;
import com.greyu.ysj.entity.GoodExample;
import com.greyu.ysj.mapper.CategoryMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: Category Service
 * @Author: gre_yu@163.com
 * @Date: Created in 14:41 2018/3/9.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public Category selectByCategoryName(String categoryName) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCategoryNameEqualTo(categoryName);

        List<Category> list = this.categoryMapper.selectByExample(categoryExample);

        Category category;
        try {
            category = list.get(0);
        } catch (Exception e) {
            category = null;
        }

        return category;
    }

    @Override
    public List<Category> getAll(Integer page, Integer rows) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        CategoryExample categoryExample = new CategoryExample();
        List<Category> list = this.categoryMapper.selectByExample(categoryExample);

        return list;
    }

    @Override
    public Category getOne(Integer categoryId) {
        Category category = this.categoryMapper.selectByPrimaryKey(categoryId);
        return category;
    }

    /**
     * 保存category

     * @return
     */
    @Override
    public ResultModel save(String categoryName) {
        System.out.println(categoryName);
        Category category = this.selectByCategoryName(categoryName);

        if (null != category) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

        category = new Category();
        category.setCategoryName(categoryName);
        this.categoryMapper.insert(category);

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
        Category category = this.categoryMapper.selectByPrimaryKey(categoryId);

        if (null == category) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        Integer count = this.goodMapper.countByExample(goodExample);

        if (count > 0) {
            return ResultModel.error(ResultStatus.CATEGORY_OWN_GOODS);
        }

        this.categoryMapper.deleteByPrimaryKey(categoryId);

        return ResultModel.ok();
    }

    /**
     * 根据category的id 更新category信息
     * @param categoryId
     * @param category
     * @return
     */
    @Override
    public ResultModel update(Integer categoryId, Category category) {
        Category newCategory = this.categoryMapper.selectByPrimaryKey(categoryId);

        if (null == newCategory) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        // 分类名称已存在
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andCategoryNameEqualTo(category.getCategoryName());
        try {
            newCategory = this.categoryMapper.selectByExample(categoryExample).get(0);
        } catch (Exception e) {
            newCategory = null;
        }
        if (null != newCategory && newCategory.getCategoryId() != categoryId) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

        this.categoryMapper.updateByPrimaryKey(category);

        return ResultModel.ok(category);
    }
}
