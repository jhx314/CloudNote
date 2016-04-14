package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RecycleActivity extends AppCompatActivity {

    private Button btn_exit;
    private ImageView ivDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        getSupportActionBar().setCustomView(R.layout.toolber_recycle_note);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ivDel = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_toolbar_recycle_del);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RecycleActivity.this).
                        setTitle("系统提示").
                        setMessage("确认彻底删除此笔记?").
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

        btn_exit = (Button) findViewById(R.id.btn_recycle_note_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
