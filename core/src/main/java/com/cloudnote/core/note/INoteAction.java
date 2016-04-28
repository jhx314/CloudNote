package com.cloudnote.core.note;

import com.cloudnote.core.ActionCallbackListener;

/**
 * Created by Jeson on 2016/4/13.
 */
public interface INoteAction {
    // 查询笔记列表
    public void getNoteList(String bookId, ActionCallbackListener<Void> listener);

    // 添加笔记
    public void addNote(String userId, String bookId, String title, ActionCallbackListener listener);

    //保存修改笔记
    public void modiNote(String noteId, String title, String body, ActionCallbackListener listener);

    //将笔记放到回收站
    public void recycleNote(String noteId, ActionCallbackListener listener);

    //查询回收站笔记
    public void findRecycle(String userId, ActionCallbackListener listener);

    //根据笔记Id查询笔记
    public void findNoteById(String noteId, ActionCallbackListener listener);

    //彻底删除笔记
    public void deleteNote(String noteId, ActionCallbackListener listener);

}
