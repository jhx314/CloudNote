package com.cloudnote.core.notebook;

import android.annotation.TargetApi;
import android.os.Build;

import com.android.volley.RequestQueue;
import com.cloudnote.api.notebook.INoteBookApi;
import com.cloudnote.api.notebook.NoteBookBookApiImpl;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.model.ApiResponse;
import com.cloudnote.model.NoteBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeson on 2016/4/11.
 */
public class NoteBookActionImpl implements INoteBookAction {
    private RequestQueue requestQueue;
    private INoteBookApi noteBookApi;
    public NoteBookActionImpl(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        noteBookApi = new NoteBookBookApiImpl(requestQueue);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void getBookList(String userId, ActionCallbackListener<Void> listener) {
        ApiResponse apiResponse = noteBookApi.getBookList(userId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍后重试!");
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(apiResponse.getData().toString());
            List<NoteBook> books = new ArrayList<>();
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                NoteBook book = new NoteBook();
                book.setCn_user_id(object.getString("cn_user_id"));
                book.setCn_notebook_id(object.getString("cn_notebook_id"));
                book.setCn_notebook_name(object.getString("cn_notebook_name"));
                book.setCn_notebook_type_id(object.getString("cn_notebook_name"));
                book.setCn_notebook_desc(object.getString("cn_notebook_desc"));
                book.setCn_notebook_createtime(null);
                books.add(book);
            }
            listener.onSuccess(books);
        } catch (JSONException e) {
            listener.onFailure(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(String userId, String bookName, ActionCallbackListener<Void> listener) {
        if (bookName == "" || bookName.equals("")){
            listener.onFailure("请填写笔记本名称");
            return;
        }
        ApiResponse apiResponse = noteBookApi.addBook(userId, bookName);
        if(apiResponse == null){
            listener.onFailure("服务器异常,请稍后重试!");
            return;
        }
        //创建成功以后,笔记本主界面再次发送请求获取笔记本列表,所以此处创建成功后不需要发送数据
        listener.onSuccess(null);
    }
}
