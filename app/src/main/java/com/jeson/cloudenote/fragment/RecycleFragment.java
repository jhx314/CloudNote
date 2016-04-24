package com.jeson.cloudenote.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.cloudnote.core.ActionCallbackListener;
import com.cloudnote.core.note.INoteAction;
import com.cloudnote.core.note.NoteActionImpl;
import com.cloudnote.model.Note;
import com.jeson.cloudenote.activity.R;
import com.jeson.cloudenote.activity.RecycleNoteDetailsActivity;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.List;

public class RecycleFragment extends Fragment {
    private final static int LISTRECYCLE_SUCCESS = 0;
    private final static int LISTRECYCLE_FAILURE = 1;

    private GridView gv;
    private INoteAction mNoteAction;
    private String mUserId;
    private GridViewAdapter mGvAdapter;
    private CatLoadingView mLoadingView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LISTRECYCLE_SUCCESS:
                    mLoadingView.dismiss();
                    gv.setAdapter(mGvAdapter);
                    break;
                case LISTRECYCLE_FAILURE:
                    mLoadingView.dismiss();
                    String err = msg.getData().getString("error");
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //广播 实现删除和回收笔记后刷新界面
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("action.refreshRecycleNotes")){
                new loadingRecycleThread().start();
                onResume();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle, container, false);
        gv = (GridView) view.findViewById(R.id.gv_recycle);
        mNoteAction = new NoteActionImpl(Volley.newRequestQueue(getContext()));
        mUserId = getArguments().getString("userId");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshRecycleNotes");
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);

        List<Note> notes = new ArrayList<>();
        gv.setAdapter(new GridViewAdapter(getContext(), notes));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gvOnclick(view);
            }
        });
        mLoadingView = new CatLoadingView();
        mLoadingView.show(getFragmentManager(),"loadingView");
        new loadingRecycleThread().start();
        return view;
    }

    /*
    处理GridView点击事件,显示回收站笔记详细信息
     */
    private void gvOnclick(View v){
        Intent intent = new Intent(getContext(), RecycleNoteDetailsActivity.class);
        TextView tv = (TextView) v.findViewById(R.id.tv_recycle_note_id);
        intent.putExtra("noteId", tv.getText().toString());
        startActivity(intent);
    }

    class loadingRecycleThread extends Thread{
        @Override
        public void run() {
            mNoteAction.findRecycle(mUserId, new ActionCallbackListener() {
                @Override
                public void onSuccess(Object data) {
                    Message msg = new Message();
                    msg.what = LISTRECYCLE_SUCCESS;
                    List<Note> notes = (List<Note>) data;
                    mGvAdapter = new GridViewAdapter(getContext(), notes);
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onFailure(String message) {
                    Message msg = new Message();
                    msg.what = LISTRECYCLE_FAILURE;
                    Bundle bundle = new Bundle();
                    bundle.putString("error", message);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            });
        }
    }

    class GridViewAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<Note> notes;
        public GridViewAdapter(Context context, List<Note> notes){
            this.inflater = LayoutInflater.from(context);
            this.notes = notes;
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
                view = inflater.inflate(R.layout.recycle_item, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_recycle_title);
            TextView tvId = (TextView) view.findViewById(R.id.tv_recycle_note_id);
            tv.setText(notes.get(position).getCn_note_title());
            tvId.setText(notes.get(position).getCn_note_id());
            return view;
        }
    }
}
