package com.greyu.ysj.entity;

import java.util.List;

public class Cart {
    private Long cartId;

    private Integer userId;

    private Double amount;

    private List<CartDetail> goods;

    public List<CartDetail> getGoods() {
        return goods;
    }

    public void setGoods(List<CartDetail> goods) {
        this.goods = goods;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", goods=" + goods +
                '}';
    }
}