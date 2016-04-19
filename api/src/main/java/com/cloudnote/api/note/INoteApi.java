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

    //修改笔记
    public final static String MODI_NOTE = NetValue.url + "note/modi.do";

    //将笔记放到回收站
    public final static String RECYVLE_NOTE = NetValue.url + "note/recycle.do";

    public ApiResponse<Void> getNoteList(String bookId);

    public ApiResponse<Void> addNote(String userId, String bookId,String title);

    public ApiResponse<Void> modiNote(String noteId, String title, String body);

    public ApiResponse<Void> recycleNote(String noteId);
}
