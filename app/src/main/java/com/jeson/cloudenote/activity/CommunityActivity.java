package com.jeson.cloudenote.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private Button btnExit;
    private ImageView ivCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        getSupportActionBar().setCustomView(R.layout.toolbar_community_note);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        ivCollection = (ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_toolbar_community_collection);
        ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(CommunityActivity.this).create();
                alertDialog.show();
                alertDialog.getWindow().setContentView(R.layout.alertdialog_collection_note);
                final Spinner spinner = (Spinner) alertDialog.getWindow().findViewById(R.id.spinner);
                final Button btnPost = (Button)alertDialog.getWindow().findViewById(R.id.btn_alertdialog_collection_post);
                final Button btnExit = (Button)alertDialog.getWindow().findViewById(R.id.btn_alertdialog_collection_exit);

                List<String> list = new ArrayList<String>();
                list.add("日常");
                list.add("学习");
                list.add("生活");
                list.add("随记");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(alertDialog.getContext(),android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       // alertDialog.dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                       // alertDialog.dismiss();
                    }
                });

                btnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        btnExit = (Button) findViewById(R.id.btn_community_note_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
