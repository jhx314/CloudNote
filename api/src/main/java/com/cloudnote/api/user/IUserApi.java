package com.cloudnote.api.user;

import com.cloudnote.api.util.NetValue;
import com.cloudnote.model.ApiResponse;

/**
 * 用户接口
 * Created by Jeson on 2016/4/10.
 */
public interface IUserApi {
    //登录
    public final static String USER_LOGIN = NetValue.url + "user/login.do";
    //注册
    public final static String USER_REGIST = NetValue.url + "user/regist.do";

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ApiResponse<Void> userLogin(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return
     */
    public ApiResponse<Void> userRegist(String username, String password, String nickname);
}
