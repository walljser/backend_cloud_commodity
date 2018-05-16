package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.OrderDetail;
import com.greyu.ysj.entity.OrderDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDetailMapper {
    List<OrderDetail> getAll();

    List<OrderDetail> getAllByOrderId(Long orderId);

    int countByExample(OrderDetailExample example);

    int deleteByExample(OrderDetailExample example);

    int deleteByPrimaryKey(Long orderDetailId);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    OrderDetail selectByPrimaryKey(Long orderDetailId);

    int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}