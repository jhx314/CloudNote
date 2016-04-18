package com.jeson.cloudenote.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.notebook.INoteBookAction;
import com.cloudnote.core.notebook.NoteBookActionImpl;
import com.cloudnote.model.NoteBook;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.jeson.cloudenote.activity.NotesActivity;
import com.jeson.cloudenote.activity.R;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.List;

public class NoteBookFragment extends Fragment {

    private final static int LISTBOOK_SUCCESS = 0;
    private final static int LISTBOOK_FAILURE = 1;
    private final static int RENAMEBOOK_SUCCESS = 2;
    private final static int RENAMEBOOK_FAILURE = 3;
    private final static int ADDBOOK_SUCCESS = 4;
    private final static int ADDBOOK_FAILURE = 5;

    private GridView gv;
    private GridViewAdapter gvAdapter;
    private String userId;
    private INoteBookAction noteBookAction;
    private CatLoadingView loadingView;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LISTBOOK_SUCCESS:
                    loadingView.dismiss();
                    gv.setAdapter(gvAdapter);
                    break;
                case LISTBOOK_FAILURE:
                    loadingView.dismiss();
                    String err = msg.getData().getString("error");
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                    break;
                case RENAMEBOOK_SUCCESS:
                    break;
                case RENAMEBOOK_FAILURE:
                    break;
                case ADDBOOK_SUCCESS:
                    loadingView.dismiss();
                    new ListBookThread().start();
                    break;
                case ADDBOOK_FAILURE:
                    loadingView.dismiss();
                    String error = msg.getData().getString("error");
                    Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_note_book, container, false);
        final AddFloatingActionButton addBtn = (AddFloatingActionButton) view.findViewById(R.id.actionbtn_addbook);
        userId = getArguments().getString("userId");
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        noteBookAction = new NoteBookActionImpl(requestQueue);
        loadingView = new CatLoadingView();
        loadingView.show(getFragmentManager(), "loadingView");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et_addBookName = new EditText(getContext());
                new AlertDialog.Builder(getContext()).setTitle("添加笔记本").
                        setIcon(R.mipmap.inote).
                        setView(et_addBookName).
                        setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadingView.show(getFragmentManager(), "addBook");
                                new Thread() {
                                    @Override
                                    public void run() {
                                        noteBookAction.addBook(userId, et_addBookName.getText().toString(), new ActionCallbackListener<Void>() {
                                            @Override
                                            public void onSuccess(Object data) {
                                                Message msg = new Message();
                                                msg.what = ADDBOOK_SUCCESS;
                                                handler.sendMessage(msg);
                                            }

                                            @Override
                                            public void onFailure(String message) {
                                                Message msg = new Message();
                                                msg.what = ADDBOOK_FAILURE;
                                                Bundle bundle = new Bundle();
                                                bundle.putString("error", message);
                                                msg.setData(bundle);
                                                handler.sendMessage(msg);
                                            }
                                        });
                                    }
                                }.start();
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

        /*
        注意: 此处获取gv控件,要使用view,不能使用getActivity().使用getActivity会获取不到view
         */
        gv = (GridView) view.findViewById(R.id.gv_notebook);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvNoteBookId = (TextView) view.findViewById(R.id.tv_notebook_id);
                TextView tvNoteBookName = (TextView) view.findViewById(R.id.tv_gv);
                String noteBookId = tvNoteBookId.getText().toString();
                String bookName = tvNoteBookName.getText().toString();
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                intent.putExtra("bookId", noteBookId);
                intent.putExtra("userId", userId);
                intent.putExtra("bookName", bookName);
                startActivity(intent);
            }
        });

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final EditText et_reNameBook = new EditText(getContext());
                new AlertDialog.Builder(getContext()).setTitle("重命名").
                        setMessage("笔记本名称").
                        setView(et_reNameBook).
                        setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).
                        setNegativeButton("取消", null).show();
                return true;
            }
        });
        new ListBookThread().start();
        return view;
    }

    class ListBookThread extends Thread {
        @Override
        public void run() {
            noteBookAction.getBookList(userId, new ActionCallbackListener<Void>() {
                @Override
                public void onSuccess(Object data) {
                    Message msg = new Message();
                    msg.what = LISTBOOK_SUCCESS;
                    List<NoteBook> books = (List<NoteBook>) data;
                    gvAdapter = new GridViewAdapter(getView().getContext(), books);
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(String message) {
                    Message msg = new Message();
                    msg.what = LISTBOOK_FAILURE;
                    Bundle bundle = new Bundle();
                    bundle.putString("error", message);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
        }
    }

    class GridViewAdapter extends BaseAdapter {

        private List<NoteBook> noteBooks;
        private LayoutInflater inflater;

        public GridViewAdapter(Context context, List<NoteBook> noteBooks) {
            this.inflater = LayoutInflater.from(context);
            this.noteBooks = noteBooks;
        }

        @Override
        public int getCount() {
            return noteBooks.size();
        }

        @Override
        public Object getItem(int position) {
            return noteBooks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView ;
            if(view == null){
                view = inflater.inflate(R.layout.item_notebook, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_gv);
            TextView tvNoteBookId = (TextView) view.findViewById(R.id.tv_notebook_id);
            tv.setText(noteBooks.get(position).getCn_notebook_name());
            tvNoteBookId.setText(noteBooks.get(position).getCn_notebook_id());
            return view;
        }
    }
}
