package com.cloudnote.core.user;

import com.cloudnote.core.ActionCallbackListener;

/**
 * Created by Jeson on 2016/4/10.
 */
public interface IUserAction {
    //登录
    public void Login(String username, String password, ActionCallbackListener<Void> listener);

}
