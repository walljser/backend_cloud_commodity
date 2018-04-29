package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.CartDetail;
import com.greyu.ysj.entity.CartDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartDetailMapper {
    int countByExample(CartDetailExample example);

    int deleteByExample(CartDetailExample example);

    int deleteByPrimaryKey(Long cartDetailId);

    int insert(CartDetail record);

    int insertSelective(CartDetail record);

    List<CartDetail> selectByExample(CartDetailExample example);

    CartDetail selectByPrimaryKey(Long cartDetailId);

    int updateByExampleSelective(@Param("record") CartDetail record, @Param("example") CartDetailExample example);

    int updateByExample(@Param("record") CartDetail record, @Param("example") CartDetailExample example);

    int updateByPrimaryKeySelective(CartDetail record);

    int updateByPrimaryKey(CartDetail record);
}