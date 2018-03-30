package com.example.groupedtut.activities;

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

import com.example.groupedtut.expediteur_message.jabber.MyXMPP;
import com.example.groupedtut.numeroserveur.NumeroServeurDataSource;
import com.example.groupedtut.soldeactuel.SoldeDataSource;

import java.util.regex.Pattern;

public abstract class BasicTrefleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected SoldeDataSource soldeDataSource;
    protected NumeroServeurDataSource numeroServeurDataSource;
    private Toolbar toolbar;

    public static BasicTrefleActivity instance = null;

    public abstract DrawerLayout getMainDrawerLayout();

    public void majSoldeAffichage(){
        this.toolbar.setTitle("Solde : "+afficherSoldeDeuxDecimal(this.soldeDataSource.getSoldeActuel())+" Trèfles");
    }

    protected String afficherSoldeDeuxDecimal(double solde) {
        if(solde%1 > 0) {
            String str = new Double(solde).toString();
            String[] tab = str.split(Pattern.quote("."));

            return tab[0] + '.' + tab[1].substring(0, 2);
        }

        return new Double(solde).toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.soldeDataSource = new SoldeDataSource(this.getBaseContext());
        this.soldeDataSource.open();
        this.numeroServeurDataSource = new NumeroServeurDataSource(this.getBaseContext());
        this.numeroServeurDataSource.open();

        instance = this;

        this.toolbar = this.findViewById(R.id.main_toolbar);

        this.majSoldeAffichage();

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
        }
        else if(this.getClass() == ParametreJabberActivity.class)
        {
            super.onBackPressed();
        }
        else{
            final Intent intentAccueil = new Intent(this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
            finish();
        }
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
            finish();
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
            finish();

        } else if (id == R.id.mDepenses) {
            final Intent intentDepense = new Intent(this, DepenseActivity.class);
            this.startActivity(intentDepense);
            finish();

        } else if (id == R.id.mSolde) {
            final Intent intentSolde = new Intent(this, SoldeActivity.class);
            this.startActivity(intentSolde);

        } else if (id == R.id.mTransaction) {
            final Intent intentTransac = new Intent(this, TransactionActivity.class);
            this.startActivity(intentTransac);
            finish();

        } else if (id == R.id.mAide) {
            final Intent intentAide = new Intent(this, AideActivity.class);
            this.startActivity(intentAide);
            finish();

        } else if (id == R.id.mParametres) {
            final Intent intentParam = new Intent(this, ParametresActivity.class);
            this.startActivity(intentParam);
            finish();
        }

        DrawerLayout drawer = getMainDrawerLayout();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
        instance = null;
        this.numeroServeurDataSource.close();
        this.soldeDataSource.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        instance = this;
        this.numeroServeurDataSource.open();
        this.soldeDataSource.open();
    }
}
