package com.greyu.ysj.entity;

public class Address {
    private Integer addressId;

    private Integer userId;

    // 收货人姓名
    private String consignee;

    // 手机号
    private Long phone;

    // 城市
    private String city;

    // 地区
    private String address;

    // 门牌号
    private String streetNumber;

    private Boolean isDefault;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber == null ? null : streetNumber.trim();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", userId=" + userId +
                ", consignee='" + consignee + '\'' +
                ", phone=" + phone +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}