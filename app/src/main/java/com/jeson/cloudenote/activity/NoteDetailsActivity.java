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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.note.INoteAction;
import com.cloudnote.core.note.NoteActionImpl;
import com.cloudnote.core.share.IShareAction;
import com.cloudnote.core.share.ShareActionImpl;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etNoteTitle;
    private TextView tvNoteContent;
    private String mNoteId;
    private INoteAction mNoteAction;
    private IShareAction mShareAction;
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
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        mNoteAction = new NoteActionImpl(requestQueue);
        mShareAction = new ShareActionImpl(requestQueue);
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
        new AlertDialog.Builder(this).setTitle("提示").
                setMessage("是否确定将该笔记放到回收站").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        }).setNegativeButton("取消",null).show();
    }

    public void shareNote(MenuItem item) {
        mShareAction.shareNote(mNoteId, new ActionCallbackListener() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
