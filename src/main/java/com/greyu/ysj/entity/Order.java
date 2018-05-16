package com.greyu.ysj.entity;

import java.util.Date;
import java.util.List;

public class Order {
    private Long orderId;

    private Integer userId;

    private Integer addressId;

    private Double amount;

    private Date createTime;

    private String remarks;

    /**
     * 订单状态
     * 0  待发货
     * 1  已发货
     * 2  确认收货
     */
    private Integer status;

    private List<OrderDetail> orderDetails;

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                ", orderDetails=" + orderDetails +
                '}';
    }
}