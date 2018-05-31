package com.greyu.ysj.service;

import com.greyu.ysj.entity.AdvSwiper;
import com.greyu.ysj.model.ResultModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 20:59 2018/5/29.
 */
public interface AdvService {
    ResultModel getAll();

    ResultModel getOne(Integer advSwiperId);

    ResultModel create(String name, Integer categorySecondId, MultipartFile image);

    ResultModel update(Integer advId, String name, Integer categorySecondId, MultipartFile image);

    ResultModel delete(Integer advSwiperId);
}
