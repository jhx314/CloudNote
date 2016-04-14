package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CollectionActivity extends AppCompatActivity {

    private Button btnExit;
    private ImageView ivDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        getSupportActionBar().setCustomView(R.layout.toolbar_collection_note);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ivDel = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_toolbar_collection_del);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CollectionActivity.this).
                        setTitle("系统提示").
                        setMessage("确认删除此收藏笔记?").
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

        btnExit = (Button) findViewById(R.id.btn_collection_note_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
