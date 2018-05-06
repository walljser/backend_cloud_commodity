package com.greyu.ysj.service;

import com.greyu.ysj.entity.CategoryFirst;
import com.greyu.ysj.model.ResultModel;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 14:38 2018/3/9.
 */
public interface CategoryFirstService {
    CategoryFirst selectByCategoryName(String categoryName);

    List<CategoryFirst> getAll(Integer page, Integer rows);

    CategoryFirst getOne(Integer categoryId);

    ResultModel save(String categoryName);

    ResultModel delete(Integer categoryId);

    ResultModel update(Integer categoryId, CategoryFirst category);
}
