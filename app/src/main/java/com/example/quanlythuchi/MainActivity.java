package com.example.quanlythuchi;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.quanlythuchi.fragment.ChiFragment;
import com.example.quanlythuchi.fragment.GioithieuFragment;
import com.example.quanlythuchi.fragment.ThongkeFragment;
import com.example.quanlythuchi.fragment.ThuFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----toolbar-----
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //-----DrawerLayout-----
        drawerLayout = findViewById(R.id.activity_main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.left_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-----mở app vào fragment thống kê đầu tiên-----
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, new ThuFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //-----Tạo OptionItem-----
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //-----Option Item-----
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-----click navigation-----
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        if (id == R.id.left_nav_khoanthu) {
            fragmentTransaction.replace(R.id.frame_layout, new ThuFragment()).commit();
        } else if (id == R.id.left_nav_khoanchi) {
            fragmentTransaction.replace(R.id.frame_layout, new ChiFragment()).commit();
        } else if (id == R.id.left_nav_thongke) {
            fragmentTransaction.replace(R.id.frame_layout, new ThongkeFragment()).commit();
        } else if (id == R.id.left_nav_gioithieu) {
            fragmentTransaction.replace(R.id.frame_layout, new GioithieuFragment()).commit();
        } else if (id == R.id.left_nav_thoat) {
            finish();
        }

        drawerLayout= findViewById(R.id.activity_main_drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
