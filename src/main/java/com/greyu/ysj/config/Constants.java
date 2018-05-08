package com.greyu.ysj.config;

/**
 * @Description: 常量
 * @Author: gre_yu@163.com
 * @Date: Created in 0:51 2018/2/1
 */
public class Constants {
    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

    /**
     * 图片地址 前缀
     */
    public static final String IMAGE_PREFIX_URL = "http://119.29.161.228/cloudimg/goods/";

    public static final String AVATAR_PREFIX_URL = "http://119.29.161.228/cloudimg/avatars";

//    public static final String IMAGE_SAVE_PATH = "D:\\test_image";
    public static final String IMAGE_SAVE_PATH = "/var/www/html/cloudimg/goods/";
//    public static final String IMAGE_SAVE_PATH = "~/cloudimg/goods/";

    /**
     * 订单待发货状态
     */
    public static final int ORDER_WAIT = 0;

    /**
     * 订单配送中状态
     */
    public static final int ORDER_DISPATCHING = 1;

    /**
     * 订单确认送达状态
     */
    public static final int ORDER_FINISH = 2;

    /**
     * 订单退款中状态
     */
    public static final int ORDER_REFUNDING = 3;

    /**
     * 订单退款完成
     */
    public static final int ORDER_REFUND_SUCCESS = -1;

    /**
     * 订单退款失败
     */
    public static final int ORDER_REFUNDING_FAILURE = -2;
}
