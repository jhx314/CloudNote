package com.cloudnote.api.util;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Jeson on 2016/4/10.
 */
public class HttpEngine {

    private RequestQueue requestQueue;

    public HttpEngine(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public JSONObject postHandle(String url, Map<String, String> params){
        JSONObject object = new JSONObject();
        NormalPostRequest request = new NormalPostRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        },params);
        requestQueue.add(request);

        object = request.getResponseObject();
        return object;
    }

}
