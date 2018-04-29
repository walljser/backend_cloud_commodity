package com.greyu.ysj.authorization.manager;

import com.greyu.ysj.authorization.model.TokenModel;

/**
 * @Description: 对Token进行操作的接口
 * @Author: gre_yu@163.com
 * @Date: Created in 0:59 2018/2/1
 */
public interface TokenManager {
    /**
     * 创建一个token关联上指定用户id
     * @param userId
     * @return 生成的token
     */
    TokenModel createToken(int userId);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    TokenModel getToken(String authentication);

    /**
     * 检查token是否有效
     * @param model
     * @return 是否有效
     */
    boolean checkToken(TokenModel model);

    /**
     * 清除 token
     * @param userId 登录用户的userId
     */
    void deleteToken(int userId);
}
