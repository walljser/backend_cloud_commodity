package com.greyu.ysj.entity;

import java.util.List;

public class CartDetail {
    private Long cartDetailId;

    private Long cartId;

    private Integer goodId;

    private Integer count;

    private Good good;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Long getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(Long cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "cartDetailId=" + cartDetailId +
                ", cartId=" + cartId +
                ", goodId=" + goodId +
                ", count=" + count +
                ", good=" + good +
                '}';
    }
}