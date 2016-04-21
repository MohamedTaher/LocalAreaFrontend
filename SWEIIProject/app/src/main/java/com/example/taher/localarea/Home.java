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
        ft.replace(R.id.mainFrame, new HomeFragment());


        fragment = new HomeFragment();

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
        if (temp != null)
        replaceFragment(titles.pop(),temp);
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

    public void replaceFragment (String title, Fragment fragment)
    {
        setTitle(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){
            preFragment = fragment;
            stack.add(preFragment);
            fragment = new HomeFragment();

            pretitle = title;
            titles.add(pretitle);
            title = "Home";
            replaceFragment("Home", fragment);

        }else if (id == R.id.nav_username) {
            preFragment = fragment;
            stack.add(preFragment);
            fragment = new AccountFragment();

            pretitle = title;
            titles.add(pretitle);
            title = user.getName();

            replaceFragment(user.getName(), fragment);

        } else if (id == R.id.nav_followers) {
            FollowersFragment search = new FollowersFragment();
            search.setSorceUser(user);
            search.setParent(this);
            preFragment = fragment;
            stack.add(preFragment);
            fragment = search;

            pretitle = title;
            titles.add(pretitle);
            title = "Followers";

            replaceFragment("Followers", search);

        } else if (id == R.id.nav_search) {
            SearchFragment search = new SearchFragment();
            search.setSorceUser(user);
            search.setParent(this);
            preFragment = fragment;
            stack.add(preFragment);
            fragment = search;

            pretitle = title;
            titles.add(pretitle);
            title = "Search";

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