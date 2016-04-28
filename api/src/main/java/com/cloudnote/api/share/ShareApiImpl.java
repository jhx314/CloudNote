package com.cloudnote.api.share;

import com.android.volley.RequestQueue;
import com.cloudnote.api.util.HttpEngine;
import com.cloudnote.model.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeson on 2016/4/19.
 */
public class ShareApiImpl implements IShareApi{
    private RequestQueue mRequestQueue;
    private HttpEngine mHttpEngine;
    public ShareApiImpl(RequestQueue requestQueue){
        this.mRequestQueue = requestQueue;
        this.mHttpEngine = new HttpEngine(mRequestQueue);
    }

    @Override
    public ApiResponse<Void> shareNote(String noteId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("noteId", noteId);
        JSONObject jsonObject = mHttpEngine.postHandle(SHARENOTE, params);
        if (jsonObject == null){
            return null;
        }
        try {
            apiResponse.setStatus(jsonObject.getInt("status"));
            apiResponse.setMsg(jsonObject.getString("msg"));
            apiResponse.setData(jsonObject.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return apiResponse;
    }
}
