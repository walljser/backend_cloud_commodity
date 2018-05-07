package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.CategoryFirst;
import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.entity.CategorySecondExample;
import com.greyu.ysj.entity.GoodExample;
import com.greyu.ysj.mapper.CategoryFirstMapper;
import com.greyu.ysj.mapper.CategorySecondMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CategorySecondService;
import com.greyu.ysj.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: Category Service
 * @Author: gre_yu@163.com
 * @Date: Created in 14:41 2018/3/9.
 */
@Service
public class CategorySecondServiceImpl implements CategorySecondService {
    @Autowired
    private CategoryFirstMapper categoryFirstMapper;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public CategorySecond selectByCategoryName(String categoryName) {
        CategorySecondExample categorySecondExample = new CategorySecondExample();
        CategorySecondExample.Criteria criteria = categorySecondExample.createCriteria();
        criteria.andCategoryNameEqualTo(categoryName);

        List<CategorySecond> list = this.categorySecondMapper.selectByExample(categorySecondExample);

        CategorySecond categorySecond;
        try {
            categorySecond = list.get(0);
        } catch (Exception e) {
            categorySecond = null;
        }

        return categorySecond;
    }

    @Override
    public List<CategorySecond> getAll(Integer page, Integer rows) {
        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        CategorySecondExample categoryExample = new CategorySecondExample();
        List<CategorySecond> list = this.categorySecondMapper.selectByExample(categoryExample);
        for (CategorySecond category: list) {
            category.setImage(Constants.IMAGE_PREFIX_URL + category.getImage());
            CategoryFirst categoryFirst = this.categoryFirstMapper.selectByPrimaryKey(category.getCategoryFirstId());
            category.setCategoryFirstName(categoryFirst.getCategoryName());
        }

        return list;
    }

    @Override
    public CategorySecond getOne(Integer categorySecondId) {
        CategorySecond categorySecond = this.categorySecondMapper.selectByPrimaryKey(categorySecondId);
        return categorySecond;
    }

    /**
     * 保存category

     * @return
     */
    @Override
    public ResultModel save(CategorySecond categorySecond, MultipartFile imageFile) {
        if (null == categorySecond.getCategoryFirstId() ||
                null == categorySecond.getCategoryName() ||
                null == imageFile) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        CategorySecond existsCategory = this.selectByCategoryName(categorySecond.getCategoryName());

        if (null != existsCategory) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

//        category = new Category();
//        category.setCategoryName(categoryName);
        String fileName = "";
        try {
            fileName = FileUtil.upload(imageFile, Constants.IMAGE_SAVE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        categorySecond.setImage(fileName);

        this.categorySecondMapper.insert(categorySecond);

        categorySecond = this.selectByCategoryName(categorySecond.getCategoryName());
        return ResultModel.ok(categorySecond);
    }

    /**
     * 删除category
     * @param categorySecondId
     * @return
     */
    @Override
    public ResultModel delete(Integer categorySecondId) {
        CategorySecond categorySecond = this.categorySecondMapper.selectByPrimaryKey(categorySecondId);

        if (null == categorySecond) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        GoodExample goodExample = new GoodExample();
        GoodExample.Criteria criteria = goodExample.createCriteria();
        criteria.andCategorySecondIdEqualTo(categorySecondId);

        Integer count = this.goodMapper.countByExample(goodExample);

        if (count > 0) {
            return ResultModel.error(ResultStatus.CATEGORY_OWN_GOODS);
        }

        this.categorySecondMapper.deleteByPrimaryKey(categorySecondId);

        return ResultModel.ok();
    }

    /**
     * 根据category的id 更新category信息
     * @param categorySecondId
     * @param categorySecond
     * @return
     */
    @Override
    public ResultModel update(Integer categorySecondId, CategorySecond categorySecond, MultipartFile imageFile) {
        if (null == categorySecond.getCategorySecondId() ||
                null == categorySecond.getCategoryFirstId() ||
                null == categorySecond.getCategoryName()) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        CategorySecond oldCategory = this.categorySecondMapper.selectByPrimaryKey(categorySecondId);

        if (null == oldCategory) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        // 分类名称已存在
        CategorySecond existsCategory = this.selectByCategoryName(categorySecond.getCategoryName());

        if (null != existsCategory && existsCategory.getCategorySecondId() != categorySecond.getCategorySecondId()) {
            return ResultModel.error(ResultStatus.CAETGORY_NAME_HAS_EXISTS);
        }

        // 设置图片名称
        String fileName = oldCategory.getImage();
        if (null != imageFile) {
            try {
                fileName = FileUtil.upload(imageFile, Constants.IMAGE_SAVE_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        categorySecond.setImage(fileName);

        this.categorySecondMapper.updateByPrimaryKey(categorySecond);

        return ResultModel.ok(categorySecond);
    }
}
