package com.greyu.ysj.service;

import com.greyu.ysj.entity.Address;
import com.greyu.ysj.model.ResultModel;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 17:22 2018/3/9.
 */
public interface AddressService {
    ResultModel getUserAllAddress(Integer userId, Integer page, Integer rows);

    ResultModel getOne(Integer userId, Integer addressId);

    ResultModel save(Address address);

    ResultModel delete(Address address);

    ResultModel update(Address address);
}
