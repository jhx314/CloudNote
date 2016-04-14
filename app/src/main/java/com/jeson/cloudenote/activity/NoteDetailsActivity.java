package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NoteDetailsActivity extends AppCompatActivity {

    private Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        final ActionBar ab = getSupportActionBar();
        ab.setCustomView(R.layout.toolbar_note);
        ab.setDisplayShowCustomEnabled(true);
        final ImageView iv = (ImageView) ab.getCustomView().findViewById(R.id.im_toolbar_note_delete);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NoteDetailsActivity.this).
                        setTitle("系统提示").
                        setMessage("确认删除此笔记?").
                        setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        btnExit = (Button) findViewById(R.id.btn_addnote_exit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
