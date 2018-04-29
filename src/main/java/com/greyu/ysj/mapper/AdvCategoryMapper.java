package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.AdvCategory;
import com.greyu.ysj.entity.AdvCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvCategoryMapper {
    int countByExample(AdvCategoryExample example);

    int deleteByExample(AdvCategoryExample example);

    int deleteByPrimaryKey(Integer advCategoryId);

    int insert(AdvCategory record);

    int insertSelective(AdvCategory record);

    List<AdvCategory> selectByExample(AdvCategoryExample example);

    AdvCategory selectByPrimaryKey(Integer advCategoryId);

    int updateByExampleSelective(@Param("record") AdvCategory record, @Param("example") AdvCategoryExample example);

    int updateByExample(@Param("record") AdvCategory record, @Param("example") AdvCategoryExample example);

    int updateByPrimaryKeySelective(AdvCategory record);

    int updateByPrimaryKey(AdvCategory record);
}