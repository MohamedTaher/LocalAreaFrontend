package com.example.taher.localarea;



import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static UserModel user = null;
    Fragment fragment, preFragment;
    String title = "",pretitle = "";
    Stack<Fragment> stack = new Stack<Fragment>();
    Stack<String> titles = new Stack<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent myIntent = getIntent(); // gets the previously created intent
        user = (UserModel) myIntent.getSerializableExtra("userModel");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();
        MenuItem username_item = menu.findItem(R.id.nav_username);

        username_item.setTitle(user.getName());

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        HomeFragment hf = new HomeFragment();
        hf.setHome(this);
        fragment = hf;

        ft.replace(R.id.mainFrame, hf);


        fragment = hf;

        title = "Home";


        ft.commit();


    }

    @Override
    public void onBackPressed() {
/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        if (!stack.empty()){
            Fragment temp = stack.pop();
            if (temp != null){
                String temp_title = titles.pop();
                if (stack.empty())
                {
                    fragment = temp;
                    title = temp_title;
                }
                replaceFragment(temp_title,temp, false);
            }
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment (String title, Fragment fragment, boolean back)
    {
        setTitle(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }

    public void replaceFragment (String title, Fragment fragment)
    {

        preFragment = this.fragment;
        stack.add(preFragment);
        this.fragment = fragment;

        pretitle = this.title;
        titles.add(pretitle);
        this.title = title;


        setTitle(title);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

        /*
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();*/
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){

            HomeFragment hf = new HomeFragment();
            hf.setHome(this);
            replaceFragment("Home", hf);

        }else if (id == R.id.nav_username) {

            replaceFragment(user.getName(), new AccountFragment());

        } else if (id == R.id.nav_followers) {
            FollowersFragment search = new FollowersFragment();
            search.setSorceUser(user);
            search.setParent(this);
            replaceFragment("Followers", search);

        } else if (id == R.id.nav_search) {
            SearchFragment search = new SearchFragment();
            search.setSorceUser(user);
            search.setParent(this);
            replaceFragment("Search", search);

        } else if (id == R.id.nav_single) { //add new place



        } else if (id == R.id.nav_group) { // my places

        } else if (id == R.id.nav_logout) {
            stack.clear();
            titles.clear();
            Intent intent = new Intent(this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}