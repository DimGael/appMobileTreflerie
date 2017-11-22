package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TransactionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button boutonValider = (Button)this.findViewById(R.id.boutonValider);
        boutonValider.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mAccueil) {
            final Intent intentAccueil = new Intent(TransactionActivity.this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
        } else if (id == R.id.mDepenses) {

        } else if (id == R.id.mSolde) {
            final Intent intentSolde = new Intent(TransactionActivity.this, ParametresActivity.class);
            this.startActivity(intentSolde);

        } else if (id == R.id.mTransaction) {
            //Ne rien faire car on est déjà sur cette activité

        } else if (id == R.id.mAide) {

        } else if (id == R.id.mParametres) {
            final Intent intentParam = new Intent(TransactionActivity.this, ParametresActivity.class);
            this.startActivity(intentParam);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        final int montant;
        final int numDestinataire;

        final EditText editTextNumDestinataire = (EditText)this.findViewById(R.id.editTextNumeroDest);
        numDestinataire = Integer.valueOf(editTextNumDestinataire.getText().toString()).intValue();

        final EditText editTextMontant = (EditText)this.findViewById(R.id.editTextMontant);
        montant = Integer.valueOf(editTextMontant.getText().toString()).intValue();


        if(numDestinataire > 10000){
            Toast.makeText(TransactionActivity.this, "Numéro de destinataire invalide", Toast.LENGTH_SHORT).show();
        }
        else if(montant > 250){
            Toast.makeText(TransactionActivity.this, "Montant de la transacion trop grand", Toast.LENGTH_SHORT).show();
        }
        else{
            final String message = String.valueOf(montant)+"/"+String.valueOf(numDestinataire);
            SmsManager.getDefault().sendTextMessage("0782572437",null,message,null,null);
            editTextMontant.setText("");
            editTextNumDestinataire.setText("");
        }
    }
}
