package com.greyu.ysj.entity;

public class AdvSwiper {
    private Integer advSwiperId;

    private String name;

    private Integer order;

    private String image;

    public Integer getAdvSwiperId() {
        return advSwiperId;
    }

    public void setAdvSwiperId(Integer advSwiperId) {
        this.advSwiperId = advSwiperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}