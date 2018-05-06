package com.greyu.ysj.entity;

public class CategorySecond {
    private Integer categorySecondId;

    private Integer categoryFirstId;

    private String categoryName;

    private String image;

    public Integer getCategorySecondId() {
        return categorySecondId;
    }

    public void setCategorySecondId(Integer categorySecondId) {
        this.categorySecondId = categorySecondId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}