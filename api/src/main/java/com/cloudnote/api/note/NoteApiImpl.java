package com.cloudnote.api.note;

import com.android.volley.RequestQueue;
import com.cloudnote.api.util.HttpEngine;
import com.cloudnote.model.ApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeson on 2016/4/13.
 */
public class NoteApiImpl implements INoteApi{
    private HttpEngine httpEngine;
    private RequestQueue requestQueue;
    public NoteApiImpl(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        httpEngine = new HttpEngine(requestQueue);
    }

    @Override
    public ApiResponse<Void> getNoteList(String bookId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("bookId", bookId);
        JSONObject jsonObject = httpEngine.postHandle(NOTE_LIST, params);
        if (jsonObject == null){
            return null;
        }
        try {
            apiResponse.setStatus(jsonObject.getInt("status"));
            apiResponse.setMsg(jsonObject.getString("msg"));
            apiResponse.setData(jsonObject.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apiResponse;
    }
}
