package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.entity.CategorySecondExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategorySecondMapper {
    int countByExample(CategorySecondExample example);

    int deleteByExample(CategorySecondExample example);

    int deleteByPrimaryKey(Integer categorySecondId);

    int insert(CategorySecond record);

    int insertSelective(CategorySecond record);

    List<CategorySecond> selectByExample(CategorySecondExample example);

    CategorySecond selectByPrimaryKey(Integer categorySecondId);

    int updateByExampleSelective(@Param("record") CategorySecond record, @Param("example") CategorySecondExample example);

    int updateByExample(@Param("record") CategorySecond record, @Param("example") CategorySecondExample example);

    int updateByPrimaryKeySelective(CategorySecond record);

    int updateByPrimaryKey(CategorySecond record);
}