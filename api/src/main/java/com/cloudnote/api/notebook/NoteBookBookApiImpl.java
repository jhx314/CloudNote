package com.cloudnote.api.notebook;

import com.android.volley.RequestQueue;
import com.cloudnote.api.util.HttpEngine;
import com.cloudnote.model.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeson on 2016/4/11.
 */
public class NoteBookBookApiImpl implements INoteBookApi {
    private RequestQueue requestQueue;
    private HttpEngine httpEngine;
    public NoteBookBookApiImpl(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        httpEngine = new HttpEngine(requestQueue);
    }
    @Override
    public ApiResponse<Void> getBookList(String userId) {
        ApiResponse api = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        JSONObject jsonObject = httpEngine.postHandle(NOTEBOOK_LIST, params);
        if (jsonObject == null)
            return null;
        try {
            api.setStatus(jsonObject.getInt("status"));
            api.setMsg(jsonObject.getString("msg"));
            api.setData(jsonObject.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return api;
    }

    @Override
    public ApiResponse<Void> addBook(String userId, String bookName) {
        ApiResponse api = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bookName", bookName);
        JSONObject jsonObject = httpEngine.postHandle(NOTEBOOK_ADD, params);
        if(jsonObject == null){
            return null;
        }else{
            try {
                api.setStatus(jsonObject.getInt("status"));
                api.setMsg(jsonObject.getString("msg"));
                api.setData(jsonObject.getString("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return api;
    }
}
