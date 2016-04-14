package com.cloudnote.core;

/**
 * 请求返回
 * Created by Jeson on 2016/4/10.
 */
public interface ActionCallbackListener<T> {

    public void onSuccess(Object data);

    public void onFailure(String message);

}
