package com.jeson.cloudenote.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.jeson.cloudenote.activity.CollectionActivity;
import com.jeson.cloudenote.activity.R;

import java.util.ArrayList;
import java.util.List;


public class CollectionFragment extends Fragment {

    private GridView gv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collction, container, false);

        gv = (GridView) view.findViewById(R.id.gv_collection);
        gv.setAdapter(new GridViewAdapter(view.getContext()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    class GridViewAdapter extends BaseAdapter{

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
