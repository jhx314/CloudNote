package com.cloudnote.api.util;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Jeson on 2016/4/10.
 */
public class HttpEngine {
    private RequestQueue requestQueue;
    public HttpEngine(RequestQueue queue){
        this.requestQueue = queue;
    }

    public JSONObject postHandle(String url, Map<String, String> params){
        final Boolean[] flag = {false};
        final JSONObject[] jsonObject = {new JSONObject()};
        Response.Listener<JSONObject> listener;
        listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        };
        NormalPostRequest request = new NormalPostRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject[0] = response;
                System.out.println("---------success-----------");
                flag[0] = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonObject[0] = null;
                System.out.println("---------failure-----------");
                flag[0] = true;
            }
        },params);
        requestQueue.add(request);

        while(true){
            if(flag[0] == true){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---------"+jsonObject[0]+"-----------");
        return jsonObject[0];
    }

}
