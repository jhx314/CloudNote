package com.cloudnote.model;

/**
 * 返回的信息
 * Created by Jeson on 2016/4/10.
 */
public class ApiResponse<T> {
    //返回的状态
    private int status;
    //返回的消息
    private String msg;
    //返回的数据
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
