package com.cloudnote.core.share;

import com.android.volley.RequestQueue;
import com.cloudnote.api.share.IShareApi;
import com.cloudnote.api.share.ShareApiImpl;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.model.ApiResponse;

/**
 * Created by Jeson on 2016/4/19.
 */
public class ShareActionImpl implements IShareAction {
    private RequestQueue mRequestQueue;
    private IShareApi mShareApi;

    public ShareActionImpl(RequestQueue requestQueue){
        this.mRequestQueue = requestQueue;
        this.mShareApi = new ShareApiImpl(requestQueue);
    }

    @Override
    public void shareNote(String noteId, ActionCallbackListener listener) {
        ApiResponse apiResponse = mShareApi.shareNote(noteId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 1){
            listener.onFailure(apiResponse.getMsg());
            return;
        }
        if (apiResponse.getStatus() == 0){
            listener.onSuccess(apiResponse.getMsg());
            return;
        }
    }
}
