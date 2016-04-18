package com.cloudnote.api.user;

import com.android.volley.RequestQueue;
import com.cloudnote.api.util.HttpEngine;
import com.cloudnote.model.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeson on 2016/4/10.
 */
public class UserApiImpl implements IUserApi {
    private RequestQueue requestQueue;
    private HttpEngine httpEngine;
    public UserApiImpl(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        httpEngine = new HttpEngine(requestQueue);
    }

    @Override
    public ApiResponse<Void> userLogin(String username, String password) {
        ApiResponse response = new ApiResponse();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("pwd", password);

        JSONObject json = httpEngine.postHandle(USER_LOGIN, map);
        if (json == null){
            response = null;
        } else {
            try {
                response.setStatus(json.getInt("status"));
                response.setMsg(json.getString("msg"));
                response.setData(json.get("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public ApiResponse<Void> userRegist(String username, String password, String nickname) {
        ApiResponse response = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("pwd", password);
        params.put("nickname", nickname);

        JSONObject json = httpEngine.postHandle(USER_REGIST, params);
        if (json == null)
            return null;
        try {
            response.setStatus(json.getInt("status"));
            response.setMsg(json.getString("msg"));
            response.setData(json.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
