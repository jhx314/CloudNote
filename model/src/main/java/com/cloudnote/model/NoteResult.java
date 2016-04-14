package com.cloudnote.model;

import java.io.Serializable;

/**
 * 作为服务器JSON返回的数据对象
 * Created by Jeson on 2016/4/10.
 */
public class NoteResult implements Serializable{
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
}
