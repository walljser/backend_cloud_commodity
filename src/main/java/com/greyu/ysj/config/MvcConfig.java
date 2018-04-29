package com.greyu.ysj.config;

import com.greyu.ysj.authorization.interceptor.AuthorizationInterceptor;
import com.greyu.ysj.authorization.resolvers.CurrentUserIdMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Description: 配置类，增加自定义拦截器和解析器
 * @see com.greyu.ysj.authorization.resolvers.CurrentUserIdMethodArgumentResolver
 * @see com.greyu.ysj.authorization.interceptor.AuthorizationInterceptor
 * @Author: gre_yu@163.com
 * @Date: Created in 7:58 2018/2/2
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserIdMethodArgumentResolver currentUserIdMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.currentUserIdMethodArgumentResolver);
    }
}
