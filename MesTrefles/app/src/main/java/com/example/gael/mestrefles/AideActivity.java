package com.example.gael.mestrefles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gael.montantmax.MontantMaxDataSource;


public class AideActivity extends BasicTrefleActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public Toolbar getToolbar() {
        return (Toolbar)this.findViewById(R.id.toolbar);
    }

    //Classe qui va nous servir à manipuler la table MontantMax
    private MontantMaxDataSource montantMaxDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_parametres);
        super.onCreate(savedInstanceState);

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
            final Intent intentAccueil = new Intent(AideActivity.this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mAccueil) {
            final Intent intentAccueil = new Intent(AideActivity.this, MenuPrincipal.class);
            this.startActivity(intentAccueil);
        } else if (id == R.id.mDepenses) {
            final Intent intentDepenses = new Intent(AideActivity.this, DepenseActivity.class);
            this.startActivity(intentDepenses);

        } else if (id == R.id.mSolde) {
            final Intent intentSolde = new Intent(AideActivity.this, SoldeActivity.class);
            this.startActivity(intentSolde);

        } else if (id == R.id.mTransaction) {
            final Intent intentTransac = new Intent(AideActivity.this, TransactionActivity.class);
            this.startActivity(intentTransac);

        } else if (id == R.id.mAide) {

        } else if (id == R.id.mParametres) {
            //Ne rien faire car on est déjà dans les paramètres
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.boutonValiderParametres){
            //Action lorsque l'utilisateur appuie sur le bouton valider en paramètres

            final EditText editTextMontantMax  = (EditText)this.findViewById(R.id.editTextMontantMax);

            if(editTextMontantMax.getText().toString().equals("")){
                //Si l'utilisateur n'a rien mis :
                Toast.makeText(this,"Vous n'avez rien saisi", Toast.LENGTH_SHORT).show();
            }
            else if(Double.valueOf(editTextMontantMax.getText().toString()).doubleValue() > 250.0){
                //Si l'utilisateur a mis une valeur supérieure à 250
                Toast.makeText(this,"Vous ne pouvez pas excéder 250 trèfles", Toast.LENGTH_SHORT).show();
            }
            else{
                //Si l'utilisateur a tout rempli correctement, mise à jour du montant maximal puis affichage du montant maximal
                final double nouvMontantMax = Double.valueOf(editTextMontantMax.getText().toString()).doubleValue();

                //Mise à jour du montant max
                this.montantMaxDataSource.majMontantMax(nouvMontantMax);

                //Affichage d'un message de confirmation rapelant le nouveau montant max
                Toast.makeText(this,"Modifications sauvegardées", Toast.LENGTH_SHORT).show();
                ((Button)findViewById(R.id.boutonValiderParametres)).setEnabled(false);
            }
        }
    }

    @Override
    public void onPause(){
        this.montantMaxDataSource.close();
        super.onPause();
    }

    @Override
    public void onResume(){
        this.montantMaxDataSource.open();
        super.onResume();
    }
}
