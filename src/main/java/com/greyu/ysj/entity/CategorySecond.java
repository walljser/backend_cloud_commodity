package com.greyu.ysj.entity;

public class CategorySecond {
    private Integer categorySecondId;

    private Integer categoryFirstId;

    private String categoryName;

    private String image;

    private String categoryFirstName;

    public String getCategoryFirstName() {
        return categoryFirstName;
    }

    public void setCategoryFirstName(String categoryFirstName) {
        this.categoryFirstName = categoryFirstName;
    }

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
        this.image = image;
    }

    @Override
    public String toString() {
        return "CategorySecond{" +
                "categorySecondId=" + categorySecondId +
                ", categoryFirstId=" + categoryFirstId +
                ", categoryName='" + categoryName + '\'' +
                ", image='" + image + '\'' +
                ", categoryFirstName='" + categoryFirstName + '\'' +
                '}';
    }
}