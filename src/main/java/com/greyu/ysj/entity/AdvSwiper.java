package com.greyu.ysj.entity;

public class AdvSwiper {
    private Integer advSwiperId;

    private Integer categorySecondId;

    private String categoryName;

    private String name;

    private String image;

    public Integer getAdvSwiperId() {
        return advSwiperId;
    }

    public void setAdvSwiperId(Integer advSwiperId) {
        this.advSwiperId = advSwiperId;
    }

    public Integer getCategorySecondId() {
        return categorySecondId;
    }

    public void setCategorySecondId(Integer categorySecondId) {
        this.categorySecondId = categorySecondId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}