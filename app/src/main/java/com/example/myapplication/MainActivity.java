package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.example.myapplication.Fragments.NewUserFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {
    private DrawerLayout drawerLayout;
    private Context context;
    private int itemSavedId;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        context = this;

        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);

        //Creacion de la barra del layor layout y la toolbar
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //Si no hay ninguna instancia guardada ira a la determinada
        if (savedInstanceState == null)
            //Si cityId es nulo ira al fragmentCiudades
            if (preferences.getString("cityId", "-1").equalsIgnoreCase("-1") || preferences.getString("userToken", "-1").equalsIgnoreCase("-1") ) {
                itemSavedId = 1;
            } else {
                itemSavedId = 0;
            }

        else
            itemSavedId = savedInstanceState.getInt("item", 1);

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
        SharedPreferences preferences = context.getSharedPreferences("KEBAB_PREFS", MODE_PRIVATE);
        View view;
        int itemId = item.getItemId();
        //Dependiendo de la opcion seleccionada ira a un sitio o a otro
        if (itemId == R.id.inicio) {
            //Si intenta ir a Home sin usuario no le dejara
            if (preferences.getString("userToken", "-1").equalsIgnoreCase("-1")) {
                Toast.makeText(context, "No tienes usuario", Toast.LENGTH_SHORT).show();

            } else {
                itemSavedId = 0;
                title = R.string.home_fragment;
                Fragment homefragment = HomeFragment.newInstance(getString(title));
                getSupportFragmentManager()
                        .beginTransaction()

                        .replace(R.id.home_content, homefragment)
                        .commit();


                setTitle(getString(title));

                drawerLayout.close();
            }


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
            //Si no ha elegido ciudad no le dejara entrar
            if (preferences.getString("cityId", "-1").equalsIgnoreCase("-1")) {
                Toast.makeText(context, "No has elegido ciudad", Toast.LENGTH_SHORT).show();

            } else {


                itemSavedId = 3;
                title = R.string.kebab_fragment;
                Fragment comedorFragment = KebabFragment.newInstance(getString(title));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_content, comedorFragment)
                        .commit();


                setTitle(getString(title));

                drawerLayout.closeDrawer(GravityCompat.START);
            }
        } else if (itemId == R.id.Configuracion) {

            //Si no ha elegido ciudad no le dejara entrar
            if (preferences.getString("cityId", "-1").equalsIgnoreCase("-1")) {
                Toast.makeText(context, "No has elegido ciudad", Toast.LENGTH_SHORT).show();

            } else {
                itemSavedId = 2;
                //Si no tiene usuario guardado ira a New User
                if (!preferences.getString("userToken", "-1").equalsIgnoreCase("-1")) {
                    title = R.string.configuration_fragment;
                    Fragment extraFragment = ConfigurationFragment.newInstance(getString(title));
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_content, extraFragment)
                            .commit();


                    setTitle(getString(title));

                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    title = R.string.configlogin_fragment;
                    Fragment extraFragment = NewUserFragment.newInstance(getString(title));
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_content, extraFragment)
                            .commit();


                    setTitle(getString(title));

                    drawerLayout.closeDrawer(GravityCompat.START);
                }

            }
        } else if (itemId == R.id.delete) {
            //Si no ha elegido ciudad no le dejara entrar
            if (preferences.getString("cityId", "-1").equalsIgnoreCase("-1")) {
                Toast.makeText(context, "No has elegido ciudad", Toast.LENGTH_SHORT).show();

            } else {
                itemSavedId = 4;
                title = R.string.delete_fragment;
                Fragment deleteFragment = DeleteFragment.newInstance(getString(title));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_content, deleteFragment)
                        .commit();


                setTitle(getString(title));

                drawerLayout.closeDrawer(GravityCompat.START);
           }
            } else{
                throw new IllegalArgumentException("menu option not implemented!!");
            }

            return true;
        }
    }

