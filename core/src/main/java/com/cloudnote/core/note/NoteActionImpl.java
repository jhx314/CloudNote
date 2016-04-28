package com.cloudnote.core.note;

import com.android.volley.RequestQueue;
import com.cloudnote.api.note.INoteApi;
import com.cloudnote.api.note.NoteApiImpl;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.model.ApiResponse;
import com.cloudnote.model.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeson on 2016/4/13.
 */
public class NoteActionImpl implements INoteAction{
    private RequestQueue requestQueue;
    private INoteApi noteApi;
    public NoteActionImpl(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
        noteApi = new NoteApiImpl(requestQueue);
    }
    @Override
    public void getNoteList(String bookId, ActionCallbackListener<Void> listener) {
        ApiResponse apiResponse = noteApi.getNoteList(bookId);
        if(apiResponse == null){
            listener.onFailure("服务器异常,请稍后重试!");
            return;
        }
        try {
            JSONArray jsonArray = new JSONArray(apiResponse.getData().toString());
            List<Note> notes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Note note = new Note();
                note.setCn_note_body(object.getString("cn_note_body"));
                note.setCn_note_create_time(object.getLong("cn_note_create_time"));
                note.setCn_note_id(object.getString("cn_note_id"));
                note.setCn_note_last_modify_time(object.getLong("cn_note_last_modify_time"));
                note.setCn_note_status_id(object.getString("cn_note_status_id"));
                note.setCn_note_title(object.getString("cn_note_title"));
                note.setCn_note_type_id(object.getString("cn_note_type_id"));
                note.setCn_notebook_id(object.getString("cn_notebook_id"));
                note.setCn_user_id(object.getString("cn_user_id"));
                notes.add(note);
            }
            listener.onSuccess(notes);
        } catch (JSONException e) {
            listener.onFailure(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addNote(String userId, String bookId, String title, ActionCallbackListener listener) {
        if(title == "" || title.equals("")){
            listener.onFailure("请填写笔记本名称");
            return;
        }
        ApiResponse apiResponse = noteApi.addNote(userId, bookId, title);
        if(apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 0){
            listener.onSuccess(apiResponse.getMsg());
            return;
        }
    }

    @Override
    public void modiNote(String noteId, String title, String body, ActionCallbackListener listener) {
        if(title == "" || title.equals("")){
            listener.onFailure("请填写笔记本名称!");
            return;
        }
        ApiResponse apiResponse = noteApi.modiNote(noteId, title, body);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 0){
            listener.onSuccess(apiResponse.getMsg());
            return;
        }
    }

    @Override
    public void recycleNote(String noteId, ActionCallbackListener listener) {
        ApiResponse apiResponse = noteApi.recycleNote(noteId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 0){
            listener.onSuccess(apiResponse.getMsg());
            return;
        }
    }

    @Override
    public void findRecycle(String userId, ActionCallbackListener listener) {
        ApiResponse apiResponse = noteApi.findRecycle(userId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 0){
            try {
                JSONArray jsonArray = new JSONArray(apiResponse.getData().toString());
                List<Note> notes = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Note note = new Note();
                    note.setCn_note_body(object.getString("cn_note_body"));
                    note.setCn_note_create_time(object.getLong("cn_note_create_time"));
                    note.setCn_note_id(object.getString("cn_note_id"));
                    note.setCn_note_last_modify_time(object.getLong("cn_note_last_modify_time"));
                    note.setCn_note_status_id(object.getString("cn_note_status_id"));
                    note.setCn_note_title(object.getString("cn_note_title"));
                    note.setCn_note_type_id(object.getString("cn_note_type_id"));
                    note.setCn_notebook_id(object.getString("cn_notebook_id"));
                    note.setCn_user_id(object.getString("cn_user_id"));
                    notes.add(note);
                }
                listener.onSuccess(notes);
            } catch (JSONException e) {
                listener.onFailure(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void findNoteById(String noteId, ActionCallbackListener listener) {
        ApiResponse apiResponse = noteApi.findNoteById(noteId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍后重试!");
            return;
        }
        if (apiResponse.getStatus() == 0){
            try {
                JSONObject object = new JSONObject(apiResponse.getData().toString());
                Note note = new Note();
                note.setCn_note_body(object.getString("cn_note_body"));
                note.setCn_note_create_time(object.getLong("cn_note_create_time"));
                note.setCn_note_id(object.getString("cn_note_id"));
                note.setCn_note_last_modify_time(object.getLong("cn_note_last_modify_time"));
                note.setCn_note_status_id(object.getString("cn_note_status_id"));
                note.setCn_note_title(object.getString("cn_note_title"));
                note.setCn_note_type_id(object.getString("cn_note_type_id"));
                note.setCn_notebook_id(object.getString("cn_notebook_id"));
                note.setCn_user_id(object.getString("cn_user_id"));
                listener.onSuccess(note);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onFailure(e.toString());
            }
        }
    }

    @Override
    public void deleteNote(String noteId, ActionCallbackListener listener) {
        ApiResponse apiResponse = noteApi.deleteNote(noteId);
        if (apiResponse == null){
            listener.onFailure("服务器异常,请稍候重试!");
            return;
        }
        if (apiResponse.getStatus() == 0) {
            listener.onSuccess(apiResponse.getMsg());
        }
    }
}
