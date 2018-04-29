package com.greyu.ysj.authorization.resolvers;

import com.greyu.ysj.authorization.annotation.CurrentUserId;
import com.greyu.ysj.config.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @Description: 增加方法注入，将含有CurrentUser注解的方法参数注入当前userId
 * @See: com.greyu.ysj.authorization.annotation.CurrentUserId
 * @Author: gre_yu@163.com
 * @Date: Created in 0:57 2018/2/1
 */
@Component
public class CurrentUserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType().isAssignableFrom(Integer.class) &&
                methodParameter.hasParameterAnnotation(CurrentUserId.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Integer currentUserId = (Integer) nativeWebRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);

        if (null != currentUserId) {
            return currentUserId;
        }

        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
