package com.cloudnote.api.note;

import com.cloudnote.api.util.NetValue;
import com.cloudnote.model.ApiResponse;

/**
 * Created by Jeson on 2016/4/13.
 */
public interface INoteApi {
    //获取笔记列表
    public final static String NOTE_LIST = NetValue.url + "note/find.do";
    //添加笔记
    public final static String ADD_NOTE = NetValue.url + "note/add.do";

    public ApiResponse<Void> getNoteList(String bookId);

    public ApiResponse<Void> addNote(String userId, String bookId,String title);
}
