package com.greyu.ysj.service;

import com.greyu.ysj.entity.Category;
import com.greyu.ysj.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 14:38 2018/3/9.
 */
public interface CategoryService {
    Category selectByCategoryName(String categoryName);

    List<Category> getAll(Integer page, Integer rows);

    Category getOne(Integer categoryId);

    ResultModel save(String categoryName);

    ResultModel delete(Integer categoryId);

    ResultModel update(Integer categoryId, Category category);
}
