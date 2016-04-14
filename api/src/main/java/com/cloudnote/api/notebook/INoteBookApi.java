package com.cloudnote.api.notebook;

import com.cloudnote.api.util.NetValue;
import com.cloudnote.model.ApiResponse;

/**
 * Created by Jeson on 2016/4/11.
 */
public interface INoteBookApi {
    //获取笔记本列表
    public final static String NOTEBOOK_LIST = NetValue.url + "book/list.do";

    //添加笔记本
    public final static String NOTEBOOK_ADD = NetValue.url + "book/save.do";

    public ApiResponse<Void> getBookList(String userId);

    public ApiResponse<Void> addBook(String userId, String bookName);
}
