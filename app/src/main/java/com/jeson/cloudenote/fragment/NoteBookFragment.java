package com.jeson.cloudenote.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeson.cloudenote.activity.NotesActivity;
import com.jeson.cloudenote.activity.R;

import java.util.ArrayList;
import java.util.List;

public class NoteBookFragment extends Fragment {

    private GridView gv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_book, container, false);
        /*
        注意: 此处获取gv控件,要使用view,不能使用getActivity().使用getActivity会获取不到view
         */
        gv = (GridView) view.findViewById(R.id.gv_notebook);
        GridViewAdapter adapter = new GridViewAdapter(view.getContext());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.show();
                alertDialog.getWindow().setContentView(R.layout.alertdialog_edit_notebook);
                alertDialog.setTitle("重命名笔记本");
                final Button btnPost = (Button) alertDialog.getWindow().findViewById(R.id.btn_alertdialog_edit_notebook_post);
                final Button btnExit = (Button) alertDialog.getWindow().findViewById(R.id.btn_alertdialog_edit_notebook_exit);

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
                return true;
            }
        });
        android.app.ActionBar actionBar =  getActivity().getActionBar();
      /*  final ImageView ivAdd = (ImageView) actionBar.getCustomView().findViewById(R.id.im_toolbar_book_main_add);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
       */
        return view;
    }

    class GridViewAdapter extends BaseAdapter {

        private List<String> noteBooks;
        private LayoutInflater inflater;

        public GridViewAdapter(Context context){
            this.inflater = LayoutInflater.from(context);
            noteBooks = new ArrayList<>();
            noteBooks.add("学习");
            noteBooks.add("工作");
            noteBooks.add("生活");
            noteBooks.add("娱乐");
            noteBooks.add("旅行");
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
                view = inflater.inflate(R.layout.gridview_item,parent,false);
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_gv);
            tv.setText(noteBooks.get(position));
            return view;
        }
    }
}
