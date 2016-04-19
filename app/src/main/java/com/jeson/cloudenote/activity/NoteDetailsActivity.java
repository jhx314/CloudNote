package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.note.INoteAction;
import com.cloudnote.core.note.NoteActionImpl;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etNoteTitle;
    private TextView tvNoteContent;
    private String mNoteId;
    private INoteAction mNoteAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Intent intent = getIntent();
        mNoteId = intent.getStringExtra("noteId");
        etNoteTitle = (EditText) findViewById(R.id.et_note_detail_title);
        tvNoteContent = (TextView) findViewById(R.id.tv_note_detail_content);

        tvNoteContent.setLetterSpacing(0.1f);
        etNoteTitle.setText(intent.getStringExtra("noteTitle"));
        tvNoteContent.setText(intent.getStringExtra("noteContent"));
        mNoteAction = new NoteActionImpl(Volley.newRequestQueue(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //保存修改笔记
    public void modiNote(MenuItem item) {
        String title = etNoteTitle.getText().toString();
        String body = tvNoteContent.getText().toString();
        mNoteAction.modiNote(mNoteId, title, body, new ActionCallbackListener() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(getApplicationContext(), "保存笔记成功!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("action.refreshNotes");
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //将笔记放到回收站
    public void recycleNote(MenuItem item) {
        mNoteAction.recycleNote(mNoteId, new ActionCallbackListener() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(getApplicationContext(), "将笔记删除成功!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("action.refreshNotes");
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
