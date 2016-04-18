package com.jeson.cloudenote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.note.INoteAction;
import com.cloudnote.core.note.NoteActionImpl;
import com.cloudnote.model.Note;

import java.util.List;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {
    private final static int LISTNOTE_SUCCESS = 0;
    private final static int LISTNOTE_FAILURE = 1;

    private String bookId;
    private String userId;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private INoteAction noteAction;
    private List<Note> mNotes;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LISTNOTE_SUCCESS:
                    recycleViewAdapter = new RecycleViewAdapter(mNotes, getApplicationContext());
                    recyclerView.setAdapter(recycleViewAdapter);
                    break;
                case LISTNOTE_FAILURE:
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle(getIntent().getStringExtra("bookName"));

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        noteAction = new NoteActionImpl(requestQueue);
        bookId = getIntent().getStringExtra("bookId");
        userId = getIntent().getStringExtra("userId");
        noteAction = new NoteActionImpl(Volley.newRequestQueue(this));


        mNotes = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_notes);
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new RecycleViewAdapter(mNotes, this);
        recyclerView.setAdapter(recycleViewAdapter);
        new getBookList().start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_notes_deletebook) {
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
        } else if (id == R.id.menu_notes_addnote) {
            final EditText etNoteName = new EditText(getApplicationContext());
            new AlertDialog.Builder(NotesActivity.this).
                    setTitle("添加笔记").
                    setMessage("笔记本名称").
                    setView(etNoteName, 50, 10, 50, 20).
                    setNegativeButton("取消", null).
                    setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            noteAction.addNote(userId, bookId, etNoteName.getText().toString(), new ActionCallbackListener() {
                                @Override
                                public void onSuccess(Object data) {
                                    Toast.makeText(getApplicationContext(), (String) data, Toast.LENGTH_SHORT).show();
                                    new getBookList().start();
                                    onResume();
                                }

                                @Override
                                public void onFailure(String message) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    class getBookList extends Thread {
        @Override
        public void run() {
            noteAction.getNoteList(bookId, new ActionCallbackListener<Void>() {
                @Override
                public void onSuccess(Object data) {
                    mNotes = (List<Note>) data;
                    Message msg = new Message();
                    msg.what = LISTNOTE_SUCCESS;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(String message) {
                    Log.d("listNote", message);
                }
            });
        }
    }

    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.NewViewHolder> {

        private List<Note> notes;
        private Context context;

        public RecycleViewAdapter(List<Note> notes, Context context) {
            this.notes = notes;
            this.context = context;
        }

        class NewViewHolder extends RecyclerView.ViewHolder {
            private TextView tvNotesTitle;
            private TextView tvNotesContent;
            private TextView tvNotesTime;
            private TextView tvNotesId;
            private CardView cardView;

            public NewViewHolder(View itemView) {
                super(itemView);
                tvNotesTitle = (TextView) itemView.findViewById(R.id.tv_notes_title);
                tvNotesContent = (TextView) itemView.findViewById(R.id.tv_notes_content);
                tvNotesTime = (TextView) itemView.findViewById(R.id.tv_notes_time);
                cardView = (CardView) itemView.findViewById(R.id.cv_notes);
                tvNotesId = (TextView) findViewById(R.id.tv_notes_id);
                tvNotesTitle.setLetterSpacing(0.1f);
                tvNotesContent.setLetterSpacing(0.1f);
                tvNotesTime.setLetterSpacing(0.1f);
                cardView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("NotesActivity", "单击recycleView");
                        TextView tvTitle = (TextView) v.findViewById(R.id.tv_notes_title);
                        TextView tvContent = (TextView) v.findViewById(R.id.tv_notes_content);
                        TextView tvId = (TextView) v.findViewById(R.id.tv_notes_id);
                        Intent intent = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                        intent.putExtra("noteTitle", tvTitle.getText().toString());
                        intent.putExtra("noteContent", tvContent.getText().toString());
                        intent.putExtra("noteId", tvId.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_notes, parent, false);
            NewViewHolder newViewHolder = new NewViewHolder(v);
            return newViewHolder;
        }

        @Override
        public void onBindViewHolder(NewViewHolder holder, int position) {
            holder.tvNotesTitle.setText(notes.get(position).getCn_note_title());
            holder.tvNotesContent.setText(notes.get(position).getCn_note_body());
            holder.tvNotesTime.setText(notes.get(position).getCreateTime());
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }
}
