package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Category;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 14:35 2018/3/9.
 */
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 带page和rows请求参数：分页获取category数据， 不带请求参数：获取全部category数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/categories", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAll(Integer page, Integer rows) {
        List<Category> categoryList = this.categoryService.getAll(page, rows);

        return new ResponseEntity<>(ResultModel.ok(categoryList), HttpStatus.OK);
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getOne(@PathVariable Integer categoryId) {
        Category category = this.categoryService.getOne(categoryId);

        if (null == category) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(ResultModel.ok(category), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/categories", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> addCategory(String categoryName) {
        if (categoryName == null) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        ResultModel resultModel = this.categoryService.save(categoryName);

        if (resultModel.getCode() == -1005) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/admin/v1/categories/{categoryId}", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> updateCategory(@PathVariable Integer categoryId, Category category) {
        System.out.println(category);
        if (null == category.getCategoryName()) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        ResultModel resultModel = this.categoryService.update(categoryId, category);

        if (resultModel.getCode() == -1005) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/categories/{categoryId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteCategory(@PathVariable Integer categoryId) {
        ResultModel resultModel = this.categoryService.delete(categoryId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        if (resultModel.getCode() == -1006) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}
