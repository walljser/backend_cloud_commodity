package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.CategoryFirst;
import com.greyu.ysj.entity.CategoryFirstExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryFirstMapper {
    int countByExample(CategoryFirstExample example);

    int deleteByExample(CategoryFirstExample example);

    int deleteByPrimaryKey(Integer categoryFirstId);

    int insert(CategoryFirst record);

    int insertSelective(CategoryFirst record);

    List<CategoryFirst> selectByExample(CategoryFirstExample example);

    CategoryFirst selectByPrimaryKey(Integer categoryFirstId);

    int updateByExampleSelective(@Param("record") CategoryFirst record, @Param("example") CategoryFirstExample example);

    int updateByExample(@Param("record") CategoryFirst record, @Param("example") CategoryFirstExample example);

    int updateByPrimaryKeySelective(CategoryFirst record);

    int updateByPrimaryKey(CategoryFirst record);
}