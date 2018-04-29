package com.greyu.ysj.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:  在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的userId
 * @See: com.greyu.ysj.authorization.resolvers.CurrentUserIdMethodArgumentResolver
 * @Author: gre_yu@163.com
 * @Date: Created in 0:57 2018/2/1
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUserId {
}
