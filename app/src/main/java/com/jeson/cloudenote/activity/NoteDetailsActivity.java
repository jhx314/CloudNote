package com.jeson.cloudenote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etNoteTitle;
    private TextView tvNoteContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Intent intent = getIntent();
        etNoteTitle = (EditText) findViewById(R.id.et_note_detail_title);
        tvNoteContent = (TextView) findViewById(R.id.tv_note_detail_content);

        tvNoteContent.setLetterSpacing(0.1f);
        etNoteTitle.setText(intent.getStringExtra("noteTitle"));
        tvNoteContent.setText(intent.getStringExtra("noteContent"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
