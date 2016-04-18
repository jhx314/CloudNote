package com.jeson.cloudenote.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jeson.cloudenote.fragment.CollectionFragment;
import com.jeson.cloudenote.fragment.CommunityFragment;
import com.jeson.cloudenote.fragment.NoteBookFragment;
import com.jeson.cloudenote.fragment.RecycleFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NoteBookFragment noteBookFragment;
    private CollectionFragment collectionFragment;
    private CommunityFragment communityFragment;
    private RecycleFragment recycleFragment;

    public String getUserId() {
        SharedPreferences preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        String userId = preferences.getString("userId", "");
        return userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("笔记本");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        noteBookFragment = new NoteBookFragment();

        Bundle bundle = new Bundle();
        bundle.putString("userId", getUserId());
        noteBookFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, noteBookFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fg = null;
        Bundle bundle = new Bundle();
        bundle.putString("userId", getUserId());

        if (id == R.id.nav_notebook) {
            getSupportActionBar().setTitle("笔记本");
            fg = noteBookFragment;
        } else if (id == R.id.nav_collection) {
            getSupportActionBar().setTitle("收藏笔记");
            if (collectionFragment == null) {
                collectionFragment = new CollectionFragment();
                fg = collectionFragment;
                fg.setArguments(bundle);
            } else {
                fg = collectionFragment;
            }
        } else if (id == R.id.nav_recycle) {
            getSupportActionBar().setTitle("回收站");
            if (recycleFragment == null) {
                recycleFragment = new RecycleFragment();
                fg = recycleFragment;
                fg.setArguments(bundle);
            } else {
                fg = recycleFragment;
            }
        } else if (id == R.id.nav_community) {
            getSupportActionBar().setTitle("笔记社区");
            if (communityFragment == null) {
                communityFragment = new CommunityFragment();
                fg = communityFragment;
                fg.setArguments(bundle);
            } else {
                fg = communityFragment;
            }
        } else if (id == R.id.nav_change) {
            Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
            MainActivity.this.startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        ft.replace(R.id.fl_content,fg).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notebook, menu);
        return true;
    }

    boolean flag_notebook = false;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_notebook_add);
        if (flag_notebook) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

}

