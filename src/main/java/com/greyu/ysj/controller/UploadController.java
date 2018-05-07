package com.greyu.ysj.controller;

import com.greyu.ysj.config.Constants;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.utils.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 18:56 2018/5/7.
 */
@RestController
public class UploadController {
    @RequestMapping(value = "/api/v1/fileUpload", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> upload(MultipartFile file) {
        String fileName = "";
//        try {
//            fileName = FileUtil.upload(file, Constants.IMAGE_SAVE_PATH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(fileName);
//        String url = Constants.IMAGE_PREFIX_URL + fileName;
//        Map result = new HashMap();
//        result.put("url", url);
//        return new ResponseEntity<ResultModel>(ResultModel.ok(result), HttpStatus.OK);
        return new ResponseEntity<ResultModel>(ResultModel.ok(), HttpStatus.OK);
    }
}
