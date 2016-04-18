package com.cloudnote.api.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 重写Request
 * 发送字符串,返回JSON数据
 * Created by Jeson on 2016/4/10.
 */
public class NormalPostRequest  extends Request<JSONObject> {
    private Map<String, String> mMap;
    private Response.Listener<JSONObject> mListener;
    private JSONObject object = null;
    private Boolean flag = false;

    public NormalPostRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Map<String, String> map){
        super(Request.Method.POST, url, errorListener);

        mListener = listener;
        mMap = map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    //此处因为response返回值需要JSON数据,和JsonObjectRequest类一样即可
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            object = new JSONObject(jsonString);
            flag = true;
            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            flag = true;
            return Response.error(new ParseError(e));
        } catch (JSONException je){
            flag = true;
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    public JSONObject getResponseObject() {
        int time = 0;
        while (true) {
            if (flag == true) {
                break;
            }
            if (time > 130) {
                JSONObject json = null;
                return json;
            }
            try {
                time++;
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
