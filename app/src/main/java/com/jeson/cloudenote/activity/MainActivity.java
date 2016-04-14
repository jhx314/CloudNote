package com.jeson.cloudenote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jeson.cloudenote.fragment.CollctionFragment;
import com.jeson.cloudenote.fragment.CommunityFragment;
import com.jeson.cloudenote.fragment.NoteBookFragment;
import com.jeson.cloudenote.fragment.RecycleFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,new NoteBookFragment()).commit();
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

        if (id == R.id.nav_notebook) {
            fg = new NoteBookFragment();
            getSupportActionBar().setCustomView(R.layout.toolbar_notebook_main);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
          //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if (id == R.id.nav_collection) {
            getSupportActionBar().setDisplayShowCustomEnabled(false);
            getSupportActionBar().setTitle("收藏笔记");
            fg = new CollctionFragment();
        } else if (id == R.id.nav_recycle) {
            getSupportActionBar().setDisplayShowCustomEnabled(false);
            getSupportActionBar().setTitle("回收站");
            fg = new RecycleFragment();
        } else if (id == R.id.nav_community) {
            getSupportActionBar().setDisplayShowCustomEnabled(false);
            getSupportActionBar().setTitle("笔记本社区");
            fg = new CommunityFragment();
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

}

