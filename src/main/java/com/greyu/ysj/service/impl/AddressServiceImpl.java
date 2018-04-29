package com.greyu.ysj.service.impl;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Address;
import com.greyu.ysj.entity.AddressExample;
import com.greyu.ysj.entity.User;
import com.greyu.ysj.mapper.AddressMapper;
import com.greyu.ysj.mapper.UserMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AddressService;
import com.greyu.ysj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 17:25 2018/3/9.
 */
@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultModel getUserAllAddress(Integer userId, Integer page, Integer rows) {
        User user = this.userMapper.selectByPrimaryKey(userId);

        // 用户为空，返回用户不存在
        if (null == user) {
            return ResultModel.error(ResultStatus.USER_NOT_FOUND);
        }

        if (null != page && null != rows) {
            PageHelper.startPage(page, rows);
        }

        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();

        criteria.andUserIdEqualTo(userId);
        addressExample.setOrderByClause("is_default desc");
        List<Address> addressList = this.addressMapper.selectByExample(addressExample);

        return ResultModel.ok(addressList);
    }

    @Override
    public ResultModel getOne(Integer userId, Integer addressId) {
        Address address = this.addressMapper.selectByPrimaryKey(addressId);

        if (null == address || !address.getUserId().equals(userId)) {
            return ResultModel.error(ResultStatus.ADDRESS_NOT_FOUND);
        }

        return ResultModel.ok(address);
    }

    @Override
    public ResultModel save(Address address) {
        // 用户id， 收件人， 手机号， 城市， 详细地址， 门牌号   不能为空
        if (null == address.getUserId() || null == address.getConsignee() ||
                null == address.getCity() || null == address.getPhone() ||
                null == address.getAddress() || null == address.getStreetNumber()) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        // 判断user是否存在
        User user = this.userMapper.selectByPrimaryKey(address.getUserId());
        if (null == user) {
            return ResultModel.error(ResultStatus.USER_NOT_FOUND);
        }

        // 设置 isDefault 默认值
        if (null == address.getIsDefault()) {
            address.setIsDefault(false);
        }

        // 添加 userId 查询条件
        List<Address> addressList;
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(address.getUserId());

        // isDefault 为 true 时，设置该用户的其他 address 的 isDefault 为 false
        if (address.getIsDefault() == true) {
            addressList  = this.addressMapper.selectByExample(addressExample);

            for (Address addr : addressList) {
                Integer addressId = addr.getAddressId();
                addr.setIsDefault(false);
                this.addressMapper.updateByPrimaryKey(addr);
            }
        }

        // 插入数据
        this.addressMapper.insert(address);

        // 把刚添加的地址信息查出来
        addressExample.setOrderByClause("address_id Desc");
        addressList = this.addressMapper.selectByExample(addressExample);
        address = addressList.get(0);

        return ResultModel.ok(address);
    }

    /**
     * 删除user的address
     * @param address
     * @return
     */
    @Override
    public ResultModel delete(Address address) {
        Address newAddress = this.addressMapper.selectByPrimaryKey(address.getAddressId());
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();

        if (null == newAddress || !newAddress.getUserId().equals(address.getUserId())) {
            return ResultModel.error(ResultStatus.ADDRESS_NOT_FOUND);
        }

        // 如果删除的是默认地址， 选该用户的一个id最大的地址为默认地址, 如果没有其他地址，就不用
        if (newAddress.getIsDefault() == true) {
            //  userId == this.userId
            criteria.andUserIdEqualTo(address.getUserId());
            // addressId != this.addressId
            criteria.andAddressIdNotEqualTo(address.getAddressId());
            // order by address_id desc
            addressExample.setOrderByClause("address_id desc");
            List<Address> addressList = this.addressMapper.selectByExample(addressExample);

            Address otherAddress;
            try {
                otherAddress = addressList.get(0);
            } catch (Exception e) {
                otherAddress = null;
            }

            // 如果该用户有其他的地址
            if (null != otherAddress) {
                otherAddress.setIsDefault(true);
                this.addressMapper.updateByPrimaryKey(otherAddress);
            }
        }

        this.addressMapper.deleteByPrimaryKey(address.getAddressId());

        return ResultModel.ok();
    }

    @Override
    public ResultModel update(Address address) {
        // 判断 address 为空 或 查出来的address的userId 与 url的userId不一致
        Address newAddress = this.addressMapper.selectByPrimaryKey(address.getAddressId());
        if (null == newAddress || !newAddress.getUserId().equals(address.getUserId())) {
            return ResultModel.error(ResultStatus.ADDRESS_NOT_FOUND);
        }

        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(address.getUserId());

        if (null != address.getConsignee()) {
            newAddress.setConsignee(address.getConsignee());
        }

        if (null != address.getPhone()) {
            newAddress.setPhone(address.getPhone());
        }

        if (null != address.getAddress()) {
            newAddress.setAddress(address.getAddress());
        }

        if (null != address.getCity()) {
            newAddress.setCity(address.getCity());
        }

        if (null != address.getStreetNumber()) {
            newAddress.setStreetNumber(address.getStreetNumber());
        }

        if (null != address.getIsDefault()) {
            // 如果 isDefault 为处， 设置其他的 isDefault 都为false
            if (address.getIsDefault() == true) {
                List<Address> addressList  = this.addressMapper.selectByExample(addressExample);

                for (Address addr : addressList) {
                    Integer addressId = addr.getAddressId();
                    addr.setIsDefault(false);
                    this.addressMapper.updateByPrimaryKey(addr);
                }
            }
            newAddress.setIsDefault(address.getIsDefault());
        }

        this.addressMapper.updateByPrimaryKey(newAddress);

        return ResultModel.ok(newAddress);
    }
}
