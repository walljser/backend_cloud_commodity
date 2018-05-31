package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.CategoryFirst;
import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.CategorySecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 23:26 2018/5/6.
 */
@RestController
public class CategorySecondController {
    @Autowired
    private CategorySecondService categorySecondService;

    /**
     * 带page和rows请求参数：分页获取category数据， 不带请求参数：获取全部category数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/category/second", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAll(Integer page, Integer rows) {
        List<CategorySecond> categoryList = this.categorySecondService.getAll(page, rows);

        return new ResponseEntity<>(ResultModel.ok(categoryList), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/category/second/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getOne(@PathVariable Integer categoryId) {
        CategorySecond category = this.categorySecondService.getOne(categoryId);

        if (null == category) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(ResultModel.ok(category), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/category/second", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> addCategory(Integer categoryFirstId, String categoryName, MultipartFile imageFile) {
        System.out.println(categoryFirstId);
        System.out.println(categoryName);
        System.out.println(imageFile);
        if (categoryName == null || categoryFirstId == null || imageFile == null) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        CategorySecond categorySecond = new CategorySecond();
        categorySecond.setCategoryFirstId(categoryFirstId);
        categorySecond.setCategoryName(categoryName);

        ResultModel resultModel = this.categorySecondService.save(categorySecond, imageFile);

        if (resultModel.getCode() == -1005 ||resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/admin/v1/category/second/{categorySecondId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> updateCategory(@PathVariable Integer categorySecondId, Integer categoryFirstId, String categoryName, MultipartFile imageFile) {
        if (null == categoryName ||
                null == categoryFirstId) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        CategorySecond categorySecond = new CategorySecond();
        categorySecond.setCategoryName(categoryName);
        categorySecond.setCategoryFirstId(categoryFirstId);
        categorySecond.setCategorySecondId(categorySecondId);

        ResultModel resultModel = this.categorySecondService.update(categorySecondId, categorySecond, imageFile);

        if (resultModel.getCode() == -1005 || resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/category/second/{categorySecondId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteCategory(@PathVariable Integer categorySecondId) {
        System.out.println("delete");
        ResultModel resultModel = this.categorySecondService.delete(categorySecondId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        if (resultModel.getCode() == -1006) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
}

