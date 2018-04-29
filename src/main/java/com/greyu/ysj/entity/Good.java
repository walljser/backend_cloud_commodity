package com.greyu.ysj.entity;

public class Good {
    private Integer goodId;

    private Integer categoryId;

    private String goodName;

    private String image;

    private Double price;

    private Double originalPrice;

    private Integer inventory;

    private Integer soldCount;

    private String spec;

    private String origin;

    private Boolean goodStatus;

    private Category category;

    public Boolean getGoodStatus() {
        return goodStatus;
    }

    public void setGoodStatus(Boolean goodStatus) {
        this.goodStatus = goodStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId=" + goodId +
                ", categoryId=" + categoryId +
                ", goodName='" + goodName + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", inventory=" + inventory +
                ", soldCount=" + soldCount +
                ", spec='" + spec + '\'' +
                ", origin='" + origin + '\'' +
                ", category=" + category +
                '}';
    }
}