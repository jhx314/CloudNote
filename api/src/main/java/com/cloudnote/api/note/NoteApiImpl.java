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
            return null;
        }
        return apiResponse;
    }

    @Override
    public ApiResponse<Void> addNote(String userId, String bookId, String title) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bookId", bookId);
        params.put("title", title);
        JSONObject jsonObject = httpEngine.postHandle(ADD_NOTE, params);
        if (jsonObject == null) {
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

    @Override
    public ApiResponse<Void> modiNote(String noteId, String title, String body) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("noteId", noteId);
        params.put("title", title);
        params.put("body", body);
        JSONObject jsonObject = httpEngine.postHandle(MODI_NOTE, params);
        if (jsonObject == null) {
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

    @Override
    public ApiResponse<Void> recycleNote(String noteId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("noteId", noteId);
        JSONObject jsonObject = httpEngine.postHandle(RECYVLE_NOTE, params);
        if (jsonObject == null) {
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

    @Override
    public ApiResponse<Void> findRecycle(String userId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        JSONObject jsonObject = httpEngine.postHandle(RECYCLE_LIST, params);
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

    @Override
    public ApiResponse<Void> findNoteById(String noteId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("noteId", noteId);
        JSONObject jsonObject = httpEngine.postHandle(FIND_NOTE, params);
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

    @Override
    public ApiResponse<Void> deleteNote(String noteId) {
        ApiResponse apiResponse = new ApiResponse();
        Map<String, String> params = new HashMap<>();
        params.put("noteId", noteId);
        JSONObject jsonObject = httpEngine.postHandle(DELETE_NOTE, params);
        if (jsonObject == null) {
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
