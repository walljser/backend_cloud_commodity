package com.greyu.ysj.config;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 0:42 2018/2/1
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
    ADMIN_NOT_FOUND(-1002, "管理员不存在"),
    NOT_SUPER_ADMIN(-1010, "没有超级管理员权限"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
    DATA_NOT_NULL(-1004, "字段不能为空"),
    USERNAME_HAS_EXISTS(-1005, "用户名已存在"),
    CAETGORY_NAME_HAS_EXISTS(-1005, "分类名称已存在"),
    CATEGORY_NOT_FOUND(-1002, "该分类不存在"),
    CATEGORY_OWN_SECONDS(-1006, "该分类下有子分类存在，不能删除该分类"),
    CATEGORY_OWN_GOODS(-1006, "该分类下有商品存在，不能删除该分类"),
    GOOD_NAME_HAS_EXISTS(-1005, "商品名称已存在"),
    GOOD_NOT_FOUND(-1002, "商品不存在"),
    GOOD_INSUFFICIENT(-1004, "商品库存不足"),
    ADDRESS_NOT_FOUND(-1002, "收货地址不存在"),
    ORDER_NOT_FOUND(-1002, "订单不存在"),
    GOOD_NOT_LESS_THEN_ZERO(-1004, "添加的商品数量必须大于零"),
    CART_NOT_FOUND(-1002, "购物车不存在"),
    USER_NOT_ALLOWED(-1010, "没有操作权限"),
    IMAGE_NOT_EMPTY(-1004, "图片不能为空"),
    ADV_NOT_FOUND(-1002, "广告不存在");

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
