package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragments.CiudadFragment;
import com.example.myapplication.Fragments.ConfigurationFragment;
import com.example.myapplication.Fragments.DeleteFragment;
import com.example.myapplication.Fragments.HomeFragment;
import com.example.myapplication.Fragments.KebabFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {
    private DrawerLayout drawerLayout;

    private int itemSavedId;
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (savedInstanceState == null)
            itemSavedId =0;
        else
            itemSavedId =savedInstanceState.getInt("item", 0);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(itemSavedId);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);




    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("item", itemSavedId);
    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int title;
        int itemId = item.getItemId();
        if (itemId == R.id.inicio) {
            itemSavedId = 0;
            title = R.string.home_fragment;
            Fragment fragment = HomeFragment.newInstance(getString(title));
            getSupportFragmentManager()
                    .beginTransaction()

                    .replace(R.id.home_content, fragment)
                    .commit();


            setTitle(getString(title));

            drawerLayout.close();
        } else if (itemId == R.id.ciudades) {
            itemSavedId = 1;
            title = R.string.ciudad_fragment;
            Fragment newsFragment = CiudadFragment.newInstance(getString(title));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_content, newsFragment)
                    .commit();


            setTitle(getString(title));

            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (itemId == R.id.Kebabs) {
            itemSavedId = 3;
            title = R.string.kebab_fragment;
            Fragment comedorFragment = KebabFragment.newInstance(getString(title));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_content, comedorFragment)
                    .commit();


            setTitle(getString(title));

            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (itemId == R.id.Configuracion) {
            itemSavedId = 2;
            title = R.string.configuration_fragment;
            Fragment extraFragment = ConfigurationFragment.newInstance(getString(title));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_content, extraFragment)
                    .commit();


            setTitle(getString(title));

            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (itemId == R.id.delete) {
            itemSavedId = 3;
            title = R.string.delete_fragment;
            Fragment deleteFragment = DeleteFragment.newInstance(getString(title));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_content, deleteFragment)
                    .commit();


            setTitle(getString(title));

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            throw new IllegalArgumentException("menu option not implemented!!");
        }

        return true;
    }
}
