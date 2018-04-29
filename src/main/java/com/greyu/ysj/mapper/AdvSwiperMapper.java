package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.AdvSwiper;
import com.greyu.ysj.entity.AdvSwiperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvSwiperMapper {
    int countByExample(AdvSwiperExample example);

    int deleteByExample(AdvSwiperExample example);

    int deleteByPrimaryKey(Integer advSwiperId);

    int insert(AdvSwiper record);

    int insertSelective(AdvSwiper record);

    List<AdvSwiper> selectByExample(AdvSwiperExample example);

    AdvSwiper selectByPrimaryKey(Integer advSwiperId);

    int updateByExampleSelective(@Param("record") AdvSwiper record, @Param("example") AdvSwiperExample example);

    int updateByExample(@Param("record") AdvSwiper record, @Param("example") AdvSwiperExample example);

    int updateByPrimaryKeySelective(AdvSwiper record);

    int updateByPrimaryKey(AdvSwiper record);
}