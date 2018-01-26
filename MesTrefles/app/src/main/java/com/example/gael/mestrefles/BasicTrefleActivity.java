package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gael.numeroserveur.NumeroServeurDataSource;
import com.example.gael.soldeactuel.SoldeDataSource;

public abstract class BasicTrefleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected SoldeDataSource soldeDataSource;
    protected NumeroServeurDataSource numeroServeurDataSource;

    public abstract Toolbar getToolbar();

    public static BasicTrefleActivity instance = null;

    public abstract DrawerLayout getMainDrawerLayout();

    public void majSoldeAffichage(double nouvSolde){
        this.getToolbar().setTitle("Solde : "+this.soldeDataSource.getSoldeActuel()+" Trèfles");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.soldeDataSource = new SoldeDataSource(this.getBaseContext());
        this.soldeDataSource.open();
        this.numeroServeurDataSource = new NumeroServeurDataSource(this.getBaseContext());
        this.numeroServeurDataSource.open();

        instance = this;

        Toolbar toolbar = this.getToolbar();

        toolbar.setTitle("Solde : "+this.soldeDataSource.getSoldeActuel()+" Trèfles");


        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);


        DrawerLayout drawer = getMainDrawerLayout();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = getMainDrawerLayout();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final Intent intentAccueil = new Intent(this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
            finish();
        }
    }

    @Override
    public void onRestart(){
        this.finish();
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
            final Intent intentParam = new Intent(this, ParametresActivity.class);
            this.startActivity(intentParam);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mAccueil) {
            final Intent intentAccueil = new Intent(this, MenuPrincipal.class);
            this.startActivity(intentAccueil);

        } else if (id == R.id.mDepenses) {
            final Intent intentDepense = new Intent(this, DepenseActivity.class);
            this.startActivity(intentDepense);

        } else if (id == R.id.mSolde) {
            final Intent intentSolde = new Intent(this, SoldeActivity.class);
            this.startActivity(intentSolde);

        } else if (id == R.id.mTransaction) {
            final Intent intentTransac = new Intent(this, TransactionActivity.class);
            this.startActivity(intentTransac);

        } else if (id == R.id.mAide) {
            final Intent intentAide = new Intent(this, AideActivity.class);
            this.startActivity(intentAide);
        } else if (id == R.id.mParametres) {
            final Intent intentParam = new Intent(this, ParametresActivity.class);
            this.startActivity(intentParam);
        }

        DrawerLayout drawer = getMainDrawerLayout();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause(){
        instance = null;
        this.numeroServeurDataSource.close();
        this.soldeDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        instance = this;
        this.numeroServeurDataSource.open();
        this.soldeDataSource.open();
        super.onResume();
    }
}
