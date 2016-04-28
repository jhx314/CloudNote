package com.cloudnote.core.share;

import com.cloudnote.core.ActionCallbackListener;

/**
 * Created by Jeson on 2016/4/19.
 */
public interface IShareAction {
    //分享笔记
    public void shareNote(String noteId, ActionCallbackListener listener);
}
