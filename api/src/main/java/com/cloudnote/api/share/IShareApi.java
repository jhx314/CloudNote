package com.cloudnote.api.share;

import com.cloudnote.api.util.NetValue;
import com.cloudnote.model.ApiResponse;

/**
 * Created by Jeson on 2016/4/19.
 */
public interface IShareApi {
    //分享笔记
    public final static String SHARENOTE = NetValue.url + "share/toshare.do";

    public ApiResponse<Void> shareNote(String noteId);
}
