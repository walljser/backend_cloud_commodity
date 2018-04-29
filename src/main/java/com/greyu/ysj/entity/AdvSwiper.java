package com.greyu.ysj.entity;

public class AdvSwiper {
    private Integer advSwiperId;

    private String image;

    public Integer getAdvSwiperId() {
        return advSwiperId;
    }

    public void setAdvSwiperId(Integer advSwiperId) {
        this.advSwiperId = advSwiperId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
}