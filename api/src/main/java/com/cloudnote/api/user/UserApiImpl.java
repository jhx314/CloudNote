package com.cloudnote.api.user;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudnote.api.util.HttpEngine;
import com.cloudnote.api.util.NormalPostRequest;
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
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("pws", password);
        JSONObject json = httpEngine.postHandle(USER_LOGIN, map);
        if (json == null){
            return null;
        }
        ApiResponse response = new ApiResponse();
        try {
            response.setStatus(json.getInt("status"));
            response.setMsg(json.getString("msg"));
            response.setData(json.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  response;
    }
}
