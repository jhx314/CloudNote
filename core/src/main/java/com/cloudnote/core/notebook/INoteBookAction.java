package com.cloudnote.core.notebook;

import com.cloudnote.core.ActionCallbackListener;

/**
 * Created by Jeson on 2016/4/11.
 */
public interface INoteBookAction {
    //查询笔记本列表
    public void getBookList(String userId, ActionCallbackListener<Void> listener);

    //添加笔记本
    public void addBook(String userId, String bookName, ActionCallbackListener<Void> listener);
}
