package com.cloudnote.core.user;

import com.android.volley.RequestQueue;
import com.cloudnote.api.user.IUserApi;
import com.cloudnote.api.user.UserApiImpl;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.model.ApiResponse;

import org.json.JSONObject;

/**
 * Created by Jeson on 2016/4/10.
 */
public class UserActionImpl implements IUserAction {
    private RequestQueue requestQueue;
    private IUserApi userApi;

    public UserActionImpl(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        userApi = new UserApiImpl(requestQueue);
    }

    @Override
    public void Login(final String username, final String password, final ActionCallbackListener<Void> listener){
        if(username.equals("") && username == ""){
            listener.onFailure("用户名为空");
            return;
        }
        if(password.equals("") && password == ""){
            listener.onFailure("密码为空");
            return;
        }
        ApiResponse<Void> response = userApi.userLogin(username, password);
        if(response==null){
            listener.onFailure("登录异常");
            return;
        }
        if (response.getStatus() == 1){
            listener.onFailure(response.getMsg());
            return;
        }
        if(response.getStatus() == 2){
            listener.onFailure(response.getMsg());
            return;
        }
        if (response.getStatus() == 0){
            listener.onSuccess(response.getData());
            return;
        }
    }

}
