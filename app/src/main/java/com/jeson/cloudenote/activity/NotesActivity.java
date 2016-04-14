package com.jeson.cloudenote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setCustomView(R.layout.toolbar_notebook);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        final ImageView ivDel = (ImageView) actionBar.getCustomView().findViewById(R.id.iv_toolbar_notebook);
        final ImageView ivAdd = (ImageView) actionBar.getCustomView().findViewById(R.id.iv_toolbar_notebook_add);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NotesActivity.this).
                        setTitle("系统提示").
                        setMessage("确认删除此笔记本?").setNegativeButton("确认", new DialogInterface.OnClickListener() {
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
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class);
                startActivity(intent);
            }
        });

        gv = (GridView) findViewById(R.id.gv_notes);
        gv.setAdapter(new GridViewAdapter(this));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    class GridViewAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<String> notes;
        public GridViewAdapter(Context context){
            this.inflater = LayoutInflater.from(context);
            notes = new ArrayList<>();
            notes.add("英雄志");
            notes.add("昆仑");
            notes.add("沧海");
            notes.add("缺月梧桐");
            notes.add("诛仙");
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int position) {
            return notes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                view = inflater.inflate(R.layout.note_item, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_note_title);
            tv.setText(notes.get(position));
            return view;
        }
    }

}
