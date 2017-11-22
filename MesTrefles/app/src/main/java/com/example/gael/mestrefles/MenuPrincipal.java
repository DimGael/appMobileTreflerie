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
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.ajoutEcouteursSurBoutons();
    }

    private void ajoutEcouteursSurBoutons() {
        //Rajout des écouteurs sur les boutons
        Button boutonTransaction = (Button)this.findViewById(R.id.BoutonTransaction);
        boutonTransaction.setOnClickListener(this);

        Button boutonSolde = (Button)this.findViewById(R.id.BoutonMonSolde);
        boutonSolde.setOnClickListener(this);

        Button boutonDepense = (Button)this.findViewById(R.id.BoutonMesDepenses);
        boutonDepense.setOnClickListener(this);

        Button boutonParametre = (Button)this.findViewById(R.id.BoutonParametres);
        boutonParametre.setOnClickListener(this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mAccueil) {
            // Handle the camera action
        } else if (id == R.id.mDepenses) {

        } else if (id == R.id.mSolde) {

        } else if (id == R.id.mTransaction) {

        } else if (id == R.id.mAide) {

        } else if (id == R.id.mParametres) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        //Méthode qui s'active si on appuie sur un des écouteurs de la vue
        if(view.getId() == R.id.BoutonTransaction){
            //Action à faire lors de l'utilisation du bouton Transaction
            final Intent intentTransac = new Intent(MenuPrincipal.this, TransactionActivity.class);
            this.startActivity(intentTransac);

        }

        if(view.getId() == R.id.BoutonMonSolde){
            //Action à faire lors de l'utilisation du bouton Solde
            final Intent intentTransac = new Intent(MenuPrincipal.this, SoldeActivity.class);
            this.startActivity(intentTransac);
        }

        if(view.getId() == R.id.BoutonMesDepenses){
            //Action à faire lors de l'utilisation du bouton Dépenses
        }

        if(view.getId() == R.id.BoutonParametres){
            //Action à faire lors de l'utilisation du bouton Parametres
        }
    }
}
