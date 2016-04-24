package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.note.INoteAction;
import com.cloudnote.core.note.NoteActionImpl;
import com.cloudnote.model.Note;

public class RecycleNoteDetailsActivity extends AppCompatActivity {
    private final static int FINDNOTE_SUCCESS = 0;
    private final static int FINDNOTE_FAILURE = 1;
    private String mNoteId;
    private TextView mTvNoteTitle;
    private TextView mTvNoteContent;
    private INoteAction mNoteAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_note_details);
        getSupportActionBar().setTitle("回收站");
        mNoteId = getIntent().getStringExtra("noteId");
        mTvNoteTitle = (TextView) findViewById(R.id.tv_recycle_note_details_title);
        mTvNoteContent = (TextView) findViewById(R.id.tv_recycle_note_details_content);
        mTvNoteContent.setLetterSpacing(0.1f);
        mNoteAction = new NoteActionImpl(Volley.newRequestQueue(this));
        mThread.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINDNOTE_SUCCESS:
                    Bundle bundle = msg.getData();
                    String noteTitle = bundle.getString("noteTitle");
                    String noteContent = bundle.getString("noteContent");
                    mTvNoteTitle.setText(noteTitle);
                    mTvNoteContent.setText(noteContent);
                    break;
                case FINDNOTE_FAILURE:
                    String error = msg.getData().getString("error");
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Thread mThread = new Thread(){
        @Override
        public void run() {
            mNoteAction.findNoteById(mNoteId, new ActionCallbackListener() {
                @Override
                public void onSuccess(Object data) {
                    Note note = (Note) data;
                    Message msg = new Message();
                    msg.what = FINDNOTE_SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putString("noteTitle", note.getCn_note_title());
                    bundle.putString("noteContent", note.getCn_note_body());
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onFailure(String message) {
                    Message msg = new Message();
                    msg.what = FINDNOTE_FAILURE;
                    Bundle bundle = new Bundle();
                    bundle.putString("error", message);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycle_note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void recycleNote(MenuItem item){
        final String[] strs = new String[]{"Study","Work","Listener"};
        new AlertDialog.Builder(this).
                setTitle("将笔记回收到").
                setSingleChoiceItems(strs, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), String.valueOf(which), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

    public void deleteNote(MenuItem item){
        new AlertDialog.Builder(this).
                setTitle("提示").
                setMessage("是否彻底删除此笔记(不可恢复)").
                setNegativeButton("取消",null).
                setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNoteAction.deleteNote(mNoteId, new ActionCallbackListener() {
                            @Override
                            public void onSuccess(Object data) {
                                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setAction("action.refreshRecycleNotes");
                                sendBroadcast(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }
}
