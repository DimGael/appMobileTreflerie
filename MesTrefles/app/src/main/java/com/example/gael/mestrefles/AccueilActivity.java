package com.example.gael.mestrefles;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by Alexandre and Quentin on 30/11/2017.
 */

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMenu = new Intent(AccueilActivity.this, MenuPrincipal.class);
                startActivity(intentMenu);
                finish();
            }
        }, 3000);
    }


}
