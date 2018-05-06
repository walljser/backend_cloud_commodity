package com.greyu.ysj.entity;

import java.util.List;

public class CategoryFirst {
    private Integer categoryFirstId;

    private String categoryName;

    private List<CategorySecond> categorySeconds;

    public List<CategorySecond> getCategorySeconds() {
        return categorySeconds;
    }

    public void setCategorySeconds(List<CategorySecond> categorySeconds) {
        this.categorySeconds = categorySeconds;
    }

    public Integer getCategoryFirstId() {
        return categoryFirstId;
    }

    public void setCategoryFirstId(Integer categoryFirstId) {
        this.categoryFirstId = categoryFirstId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }
}