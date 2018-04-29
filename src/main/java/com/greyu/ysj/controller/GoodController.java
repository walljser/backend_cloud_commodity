package com.greyu.ysj.controller;

import com.github.pagehelper.PageInfo;
import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Good;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.GoodService;
import com.greyu.ysj.utils.FileUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.ws.Response;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 16:33 2018/3/9.
 */
@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/admin/v1/goods", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getAll(Integer page, Integer rows, Good good, Boolean goodStatus) {
        PageInfo<Good> goodList = this.goodService.getAll(page, rows, good, goodStatus);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }


    /**
     * 用户获取在售的 商品 列表， 只能获取 库存 > 0 的商品
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/goods", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAllGoodsOnShell(Integer page, Integer rows, String orderBy, Good good) {
        System.out.println(page);
        System.out.println(rows);
        System.out.println(orderBy);
        System.out.println(good);
        List<Good> goodList = this.goodService.getAllGoodsOnShell(page, rows, orderBy, good);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }

    /**
     * 获取一条指定 goodId 的商品信息
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/user/v1/goods/{goodId}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getOne(@PathVariable Integer goodId) {
        Good good = this.goodService.getOne(goodId);

        // 查不到商品
        if (null == good) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.GOOD_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(ResultModel.ok(good), HttpStatus.OK);
    }

    /**
     * 添加商品
     * @param good
     * @return
     */
    @RequestMapping(value = "/admin/v1/goods", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> save(HttpServletRequest request, Good good, MultipartFile imageFile) {
        if (null == imageFile) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.IMAGE_NOT_EMPTY), HttpStatus.BAD_REQUEST);
        }

//        String path = request.getServletContext().getRealPath(Constants.IAMGE_SAVE_PATH);
        String fileName = "";
        try {
            fileName = FileUtil.upload(imageFile, Constants.IMAGE_SAVE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        good.setImage(fileName);

        ResultModel resultModel = this.goodService.save(good);

        if (resultModel.getCode() == -1005 || resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    /**
     * 获取分类的商品信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/categories/{categoryId}/goods", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getCategoryGoods(@PathVariable Integer categoryId, Integer page, Integer rows, Good good) {
        System.out.println(good);

        List<Good> goodList = this.goodService.getCategoryGoods(page, rows, good);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }

    /**
     * 更新商品信息
     * @param goodId
     * @param good
     * @return
     */
    @RequestMapping(value = "/admin/v1/goods/{goodId}", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> updateCategory(@PathVariable Integer goodId, Good good) {
        System.out.println(good);
        ResultModel resultModel = this.goodService.update(goodId, good);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/goods/{goodId}/inventory", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> increaseInventory(@PathVariable Integer goodId, Integer inventory) {
        if (null == inventory) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        ResultModel resultModel = this.goodService.increaseInventory(goodId, inventory);
        if (resultModel.getCode() == -1002 || resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    /**
     * 删除商品
     * @param goodId
     * @return
     */
//    @RequestMapping(value = "/admin/v1/goods/{goodId}", method = RequestMethod.DELETE)
//    @Authorization
//    public ResponseEntity<ResultModel> deleteOne(@PathVariable Integer goodId) {
//        ResultModel resultModel = this.goodService.delete(goodId);
//
//        if (resultModel.getCode() == -1002) {
//            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
//    }
}
