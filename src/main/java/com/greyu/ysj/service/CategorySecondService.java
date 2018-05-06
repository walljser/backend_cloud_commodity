package com.greyu.ysj.service;

import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.model.ResultModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 14:38 2018/3/9.
 */
public interface CategorySecondService {
    CategorySecond selectByCategoryName(String categoryName);

    List<CategorySecond> getAll(Integer page, Integer rows);

    CategorySecond getOne(Integer categorySecondId);

    ResultModel save(CategorySecond categorySecond, MultipartFile imageFile);

    ResultModel delete(Integer categorySecondId);

    ResultModel update(Integer categorySecondId, CategorySecond categorySecond, MultipartFile imageFile);
}
