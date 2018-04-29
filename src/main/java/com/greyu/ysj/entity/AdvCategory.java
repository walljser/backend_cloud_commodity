package com.greyu.ysj.entity;

public class AdvCategory {
    private Integer advCategoryId;

    private String image;

    public Integer getAdvCategoryId() {
        return advCategoryId;
    }

    public void setAdvCategoryId(Integer advCategoryId) {
        this.advCategoryId = advCategoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}