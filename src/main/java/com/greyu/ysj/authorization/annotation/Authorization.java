package com.greyu.ysj.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 在controller的方法中使用此注解，检查是否登录，未登录返回401 un authorized
 * @see com.greyu.ysj.authorization.interceptor.AuthorizationInterceptor
 * @Author: gre_yu@163.com
 * @Date: Created in 8:51 2018/2/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}
